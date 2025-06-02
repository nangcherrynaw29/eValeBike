package integration4.evalebike.service;

import integration4.evalebike.controller.TestBenchConfig;
import integration4.evalebike.controller.technician.dto.TestReportDTO;
import integration4.evalebike.controller.technician.dto.TestReportEntryDTO;
import integration4.evalebike.controller.technician.dto.TestRequestDTO;
import integration4.evalebike.controller.technician.dto.TestResponseDTO;
import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.TestReport;
import integration4.evalebike.domain.TestReportEntry;
import integration4.evalebike.repository.BikeRepository;
import integration4.evalebike.domain.*;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.TechnicianRepository;
import integration4.evalebike.repository.TestBenchRepository;
import integration4.evalebike.repository.TestReportRepository;
import io.netty.handler.timeout.TimeoutException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatusCode;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static integration4.evalebike.controller.technician.dto.TestReportDTO.convertToTestReportDTO;
import static integration4.evalebike.controller.technician.dto.TestReportDTO.convertToTestReportWithNoEntries;
import static integration4.evalebike.controller.technician.dto.TestReportEntryDTO.convertToTestReportEntries;

@Service
public class TestBenchService {
    private static final Logger logger = LoggerFactory.getLogger(TestBenchService.class);

    private final WebClient testBenchClient;
    private final TestReportRepository testReportRepository;
    private final BikeRepository bikeRepository;
    private final TestBenchRepository testBenchRepository;
    private final TechnicianRepository technicianRepository;

    public TestBenchService(WebClient.Builder webClientBuilder, TestBenchConfig testBenchConfig, TestReportRepository testReportRepository, TestBenchRepository testBenchRepository, TechnicianRepository technicianRepository, BikeRepository bikeRepository) {
        this.testBenchClient = webClientBuilder
                .baseUrl(testBenchConfig.getBaseUrl())
                .defaultHeader("X-Api-Key", testBenchConfig.getApiKey())
                .build();
        this.testReportRepository = testReportRepository;
        this.bikeRepository = bikeRepository;
        this.testBenchRepository = testBenchRepository;
        this.technicianRepository = technicianRepository;
    }

    public Mono<TestResponseDTO> processTest(TestRequestDTO request, String technicianUsername, String bikeQR) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("type", request.getTestType());
        requestBody.put("batteryCapacity", request.getBatteryCapacity());
        requestBody.put("maxSupport", request.getMaxSupport());
        requestBody.put("enginePowerMax", request.getEnginePowerMax());
        requestBody.put("enginePowerNominal", request.getEnginePowerNominal());
        requestBody.put("engineTorque", request.getEngineTorque());

        logger.info("Sending request body: {}", requestBody);

        return testBenchClient.post()
                .uri("/api/test")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .doOnNext(errorBody -> logger.error("Error response: {}", errorBody))
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Failed to start test: " + errorBody))))
                .bodyToMono(TestResponseDTO.class)
                .doOnNext(response -> {
                    logger.info("Started test with ID: {}", response.getId());
                    pollTestStatus(response.getId())
                            .filter(status -> "COMPLETED".equalsIgnoreCase(status.getState()))
                            .next()
                            .flatMap(status -> getTestReportById(response.getId()))
                            .doOnNext(report -> logger.info("Fetched and saved report for testId: {}", response.getId()))
                            .subscribe(
                                    result -> logger.info("Background task completed: {}", result.getId()),
                                    error -> logger.error("Background task failed: {}", error.getMessage())
                            );
                });
    }

    public Flux<TestResponseDTO> pollTestStatus(String testId) {
        return Mono.defer(() -> testBenchClient.get()
                        .uri("/api/test/{id}", testId)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, response ->
                                response.bodyToMono(String.class)
                                        .flatMap(errorBody -> Mono.error(new RuntimeException("Failed to fetch test status: " + errorBody))))
                        .bodyToMono(TestResponseDTO.class))
                .doOnNext(status -> {
                    logger.info("Test {} status: {}", testId, status.getState());
                    if ("COMPLETED".equalsIgnoreCase(status.getState())) {
                        // Find and unassign the test bench used for this test
                        handleTestCompletion(testId);
                    }
                })
                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(2)))
                .takeUntil(status -> "COMPLETED".equalsIgnoreCase(status.getState()))
                .timeout(Duration.ofMinutes(30))
                .onErrorResume(TimeoutException.class, e -> Mono.error(new RuntimeException("Test timed out")));
    }

    //this is to check the status of the test
    public Mono<TestResponseDTO> getTestStatusById(String testId) {
        return testBenchClient.get()
                .uri("/api/test/{id}", testId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .doOnNext(errorBody -> logger.error("Failed to fetch test result for testId {}: {}", testId, errorBody))
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Failed to fetch test result: " + errorBody))))
                .bodyToMono(TestResponseDTO.class)
                .doOnNext(response -> logger.info("Fetched test result for testId {}: ID = {}", testId, response.getId()))
                .doOnError(e -> logger.error("Error fetching test result for testId {}: {}", testId, e.getMessage()));
    }

    //to get the whole test report (including test entries)
    public Mono<TestReportDTO> getTestReportById(String testId) {
        logger.info("Fetching report for testId: {}", testId);

        return Mono.fromCallable(() -> testReportRepository.findBikeByID(testId))
                .flatMap(optionalReport -> {
                    if (optionalReport.isEmpty()) {
                        logger.error("No TestReport found for testId: {}", testId);
                        return Mono.error(new RuntimeException("No TestReport found for testId: " + testId));
                    }
                    TestReport report = optionalReport.get();
                    String bikeQR = report.getBike() != null ? report.getBike().getBikeQR() : null;
                    String technicianUsername = report.getTechnicianName();
                    return fetchCsvReport(testId)
                            .flatMap(csv -> parseCsvToEntries(csv, testId))
                            .flatMap(entries -> {
                                if (entries.isEmpty()) {
                                    logger.warn("No entries parsed for testId: {}", testId);
                                    return Mono.error(new RuntimeException("No entries found for testId: " + testId));
                                }
                                return fetchAndValidateTest(testId)
                                        .flatMap(test -> saveTestReport(test, entries, bikeQR, technicianUsername))
                                        .map(testReportDTO -> convertToTestReportDTO(testReportDTO, entries, bikeQR));

                            });
                })
                .doOnError(e -> logger.error("Error fetching report for testId {}: {}", testId, e.getMessage()));
    }

    public Mono<String> fetchCsvReport(String testId) {
        return testBenchClient.get()
                .uri("/api/test/{id}/report", testId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .doOnNext(body -> logger.error("Report fetch error: {} - {}", response.statusCode(), body))
                                .flatMap(body -> Mono.error(new RuntimeException("Failed to fetch test report: " + body))))
                .bodyToMono(String.class)
                .doOnNext(csv -> logger.info("Received CSV for {}: {}", testId, csv.substring(0, Math.min(csv.length(), 100)) + "..."));
    }

    public Mono<List<TestReportEntryDTO>> parseCsvToEntries(String csvData, String testId) {
        try {
            List<TestReportEntryDTO> entries = parseCsvToReportEntries(csvData);
            logger.info("Parsed {} report entries for testId {}", entries.size(), testId);
            return Mono.just(entries);
        } catch (Exception e) {
            logger.error("CSV parsing failed for {}: {}", testId, e.getMessage());
            return Mono.error(new RuntimeException("Failed to parse CSV", e));
        }
    }

    public Mono<TestResponseDTO> fetchAndValidateTest(String testId) {
        return getTestStatusById(testId)
                .flatMap(response -> {
                    if (response.getId() == null) {
                        logger.error("Null test ID for {}", testId);
                        return Mono.error(new RuntimeException("Test ID is null"));
                    }
                    if (!"COMPLETED".equalsIgnoreCase(response.getState())) {
                        logger.warn("Test not completed for {}: state={}", testId, response.getState());
                        return Mono.error(new RuntimeException("Test is not completed yet"));
                    }
                    return Mono.just(response);
                });
    }

    public Mono<TestReportDTO> saveTestReport(TestResponseDTO test, List<TestReportEntryDTO> entryDTOs,
                                              String bikeQR, String technicianUsername) {
        return Mono.fromCallable(() -> bikeRepository.findByBikeQR(bikeQR)) // Fetch Bike by bikeQR
                .flatMap(optionalBike -> {
                    if (optionalBike.isEmpty()) {
                        logger.error("No Bike found for bikeQR: {}", bikeQR);
                        return Mono.error(new RuntimeException("No Bike found for bikeQR: " + bikeQR));
                    }
                    Bike bike = optionalBike.get();

                    TestReport testReport = convertToTestReportWithNoEntries(test, bike, technicianUsername);
                    List<TestReportEntry> entries = convertToTestReportEntries(entryDTOs, testReport);

                    testReport.setReportEntries(entries);

                    try {
                        TestReport saved = testReportRepository.save(testReport);
                        logger.info("Saved TestReport with ID: {} for testId {}", saved.getId(), test.getId());
                        return Mono.just(new TestReportDTO(
                                test.getId(),
                                test.getExpiryDate(),
                                test.getState(),
                                test.getType(),
                                test.getBatteryCapacity(),
                                test.getMaxSupport(),
                                test.getEnginePowerMax(),
                                test.getEnginePowerNominal(),
                                test.getEngineTorque(),
                                entryDTOs,
                                bikeQR, // Use the provided bikeQR
                                saved.getTechnicianName()
                        ));
                    } catch (Exception e) {
                        logger.error("Failed to save report for testId {}: {}", test.getId(), e.getMessage());
                        return Mono.error(new RuntimeException("Failed to save TestReport", e));
                    }
                })
                .switchIfEmpty(Mono.error(new RuntimeException("No Bike found for bikeQR: " + bikeQR)));
    }

    public List<TestReportEntryDTO> parseCsvToReportEntries(String csvData) {
        String[] lines = csvData.trim().split("\n");
        return java.util.Arrays.stream(lines)
                .skip(1)
                .map(line -> {
                    String[] values = line.split(",");
                    try {
                        return new TestReportEntryDTO(
                                LocalDateTime.parse(values[0]),
                                Double.parseDouble(values[1]),
                                Double.parseDouble(values[2]),
                                Double.parseDouble(values[3]),
                                Double.parseDouble(values[4]),
                                Integer.parseInt(values[5]),
                                Integer.parseInt(values[6]),
                                Double.parseDouble(values[7]),
                                Double.parseDouble(values[8]),
                                Integer.parseInt(values[9]),
                                Integer.parseInt(values[10]),
                                Double.parseDouble(values[11]),
                                Double.parseDouble(values[12]),
                                Double.parseDouble(values[13]),
                                Double.parseDouble(values[14]),
                                Double.parseDouble(values[15]),
                                Double.parseDouble(values[16]),
                                Double.parseDouble(values[17]),
                                Double.parseDouble(values[18]),
                                Boolean.parseBoolean(values[19])
                        );
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse CSV line: " + line, e);
                    }
                })
                .collect(Collectors.toList());
    }

    public List<TestBench> getAllTestBenches() {
        return testBenchRepository.findAll(Sort.by(Sort.Direction.ASC, "testBenchName")
        );
    }

    @Transactional
    public void assignTestBench(Integer testBenchId, Integer technicianId) {

        TestBench testBench = testBenchRepository.findById(testBenchId)
                .orElseThrow(() -> NotFoundException.forTestBench(testBenchId));

        Technician technician = technicianRepository.findById(technicianId)
                .orElseThrow(() -> NotFoundException.forTechnician(technicianId));

        // Check if the test bench is already assigned to a different technician
        if (testBench.getTechnician() != null &&
                !testBench.getTechnician().getId().equals(technicianId)) {
            throw new IllegalStateException(
                    "Test bench is already assigned to another technician");
        }

        // Set technician and update status to ACTIVE
        testBench.setTechnician(technician);
        testBench.setStatus(Status.ACTIVE);

        testBenchRepository.save(testBench);
    }

    @Transactional
    public void unassignTestBench(Integer testBenchId) {
        try {
            TestBench testBench = testBenchRepository.findById(testBenchId)
                    .orElseThrow(() -> NotFoundException.forTestBench(testBenchId));

            if (testBench.getTechnician() != null) {
                Integer technicianId = testBench.getTechnician().getId();
                Technician technician = technicianRepository.findById(technicianId)
                        .orElseThrow(() -> NotFoundException.forTechnician(technicianId));

                testBench.setTechnician(null);
                testBench.setStatus(Status.INACTIVE);
                testBenchRepository.save(testBench);

                // Update technician's test bench list
                List<TestBench> updatedBenches = technician.getAssignedTestBench();
                updatedBenches.removeIf(tb -> tb.getTestBenchId().equals(testBenchId));
                technician.setAssignedTestBench(updatedBenches);
                technicianRepository.save(technician);
            } else {
                // If no technician is assigned, just update the test bench
                testBench.setStatus(Status.INACTIVE);
                testBenchRepository.save(testBench);
            }

        } catch (Exception e) {
            logger.error("Error unassigning test bench {}: {}", testBenchId, e.getMessage());
            throw new RuntimeException("Failed to unassign test bench", e);
        }
    }

    @Transactional
    protected void handleTestCompletion(String testId) {
        try {
            TestReport report = testReportRepository.findBikeByID(testId)
                    .orElseThrow(() -> new RuntimeException("No TestReport found for testId: " + testId));

            String technicianUsername = report.getTechnicianName();
            if (technicianUsername == null || technicianUsername.isEmpty()) {
                logger.warn("No technician username found for test ID: {}", testId);
                return;
            }

            Technician technician = technicianRepository.findByEmail(technicianUsername)
                    .orElseThrow(() -> new RuntimeException("Technician not found with email (username): " + technicianUsername));

            List<TestBench> activeTestBenches = findByTechnicianIdAndStatus(technician.getId(), Status.ACTIVE);
            for (TestBench testBench : activeTestBenches) {
                unassignTestBench(testBench.getTestBenchId());
                logger.info("Unassigned test bench {} from technician {} after test completion",
                        testBench.getTestBenchId(), technicianUsername);
            }

        } catch (Exception e) {
            logger.error("Error handling test completion for testId {}: {}", testId, e.getMessage());
            throw new RuntimeException("Failed to handle test completion", e);
        }
    }

    public List<TestBench> findByTechnicianIdAndStatus(Integer technicianId, Status status) {
        return testBenchRepository.findByTechnicianIdAndStatus(technicianId, status);
    }

    public long countActiveTestBenches() {
        return testBenchRepository.findAll().stream()
                .filter(tb -> tb.getStatus() == Status.ACTIVE)
                .count();
    }
}