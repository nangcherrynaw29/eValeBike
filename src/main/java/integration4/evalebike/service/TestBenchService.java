
package integration4.evalebike.service;

import integration4.evalebike.controller.testBench.dto.TestReportDTO;
import integration4.evalebike.controller.testBench.dto.TestReportEntryDTO;
import integration4.evalebike.controller.testBench.dto.TestRequestDTO;
import integration4.evalebike.controller.testBench.dto.TestResponseDTO;
import integration4.evalebike.domain.*;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.TechnicianRepository;
import integration4.evalebike.repository.TestBenchRepository;
import integration4.evalebike.repository.TestReportRepository;
import io.netty.handler.timeout.TimeoutException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class TestBenchService {
    private static final Logger logger = LoggerFactory.getLogger(TestBenchService.class);

    private final WebClient testBenchClient;
    private final TestReportRepository testReportRepository;
    private final TestBenchRepository testBenchRepository;
    private final TechnicianRepository technicianRepository;
    private final String apiKey = "9e8dffd7-f6e1-45b4-b4aa-69fd257ca200";

    public TestBenchService(WebClient.Builder webClientBuilder, TestReportRepository testReportRepository, TestBenchRepository testBenchRepository, TechnicianRepository technicianRepository) {
        this.testBenchClient = webClientBuilder
                .baseUrl("https://testbench.raoul.dev")
                .defaultHeader("X-Api-Key", apiKey)
                .build();
        this.testReportRepository = testReportRepository;
        this.testBenchRepository = testBenchRepository;
        this.technicianRepository = technicianRepository;
    }

    public Mono<TestResponseDTO> startTest(TestRequestDTO request, String technicianUsername) {
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
                                    result -> logger.info("Background task completed: {}", result.id()),
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
                .doOnNext(status -> logger.info("Test {} status: {}", testId, status.getState()))
                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(5)))
                .takeUntil(status -> "COMPLETED".equalsIgnoreCase(status.getState()))
                .timeout(Duration.ofMinutes(30))
                .onErrorResume(TimeoutException.class, e -> Mono.error(new RuntimeException("Test timed out")));
    }

    //this is to check the status of the test
    public Mono<TestResponseDTO> getTestResultById(String testId) {
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

//    public Mono<TestReportDTO> getTestReportById(String testId) {
//        logger.info("Fetching report for testId: {}", testId);
//        return testBenchClient.get()
//                .uri("/api/test/{id}/report", testId)
//                .header("X-Api-Key", apiKey)
//                .retrieve()
//                .onStatus(HttpStatusCode::isError, response ->
//                        response.bodyToMono(String.class)
//                                .doOnNext(errorBody -> logger.error("Report fetch error for testId {}: {} - {}", testId, response.statusCode(), errorBody))
//                                .flatMap(errorBody -> Mono.error(new RuntimeException("Failed to fetch test report: " + errorBody))))
//                .bodyToMono(String.class)
//                .doOnNext(csvData -> logger.info("Received CSV data for testId {}: {}", testId, csvData.substring(0, Math.min(csvData.length(), 100)) + "..."))
//                .flatMap(csvData -> {
//                    List<TestReportEntryDTO> reportEntries;
//                    try {
//                        reportEntries = parseCsvToReportEntries(csvData);
//                        logger.info("Parsed {} report entries for testId {}", reportEntries.size(), testId);
//                    } catch (Exception e) {
//                        logger.error("Failed to parse CSV for testId {}: {}", testId, e.getMessage());
//                        return Mono.error(new RuntimeException("Failed to parse CSV data", e));
//                    }
//                    return getTestResultById(testId)
//                            .map(testResponse -> {
//                                if (testResponse.getId() == null) {
//                                    logger.error("TestResponse ID is null for testId: {}", testId);
//                                    throw new RuntimeException("TestResponse ID is null");
//                                }
//                                if (!"COMPLETED".equalsIgnoreCase(testResponse.getState())) {
//                                    logger.warn("Test not completed for testId {}: state = {}", testId, testResponse.getState());
//                                    throw new RuntimeException("Test is not completed yet");
//                                }
//                                TestReport testReport = new TestReport(
//                                        testResponse.getId(),
//                                        testResponse.getExpiryDate(),
//                                        testResponse.getState(),
//                                        testResponse.getType(),
//                                        testResponse.getBatteryCapacity(),
//                                        testResponse.getMaxSupport(),
//                                        testResponse.getEnginePowerMax(),
//                                        testResponse.getEnginePowerNominal(),
//                                        testResponse.getEngineTorque(),
//                                        null
//                                );
//                                List<TestReportEntry> entries = reportEntries.stream()
//                                        .map(dto -> new TestReportEntry(
//                                                testReport,
//                                                dto.timestamp(),
//                                                dto.batteryVoltage(),
//                                                dto.batteryCurrent(),
//                                                dto.batteryCapacity(),
//                                                dto.batteryTemperatureCelsius(),
//                                                dto.chargeStatus(),
//                                                dto.assistanceLevel(),
//                                                dto.torqueCrankNm(),
//                                                dto.bikeWheelSpeedKmh(),
//                                                dto.cadanceRpm(),
//                                                dto.engineRpm(),
//                                                dto.enginePowerWatt(),
//                                                dto.wheelPowerWatt(),
//                                                dto.rollTorque(),
//                                                dto.loadcellN(),
//                                                dto.rolHz(),
//                                                dto.horizontalInclination(),
//                                                dto.verticalInclination(),
//                                                dto.loadPower(),
//                                                dto.statusPlug()
//                                        ))
//                                        .collect(Collectors.toList());
//                                testReport.setReportEntries(entries);
//                                try {
//                                    TestReport savedReport = testReportRepository.save(testReport);
//                                    logger.info("Saved TestReport with ID: {} for testId {}", savedReport.getId(), testId);
//                                    return new TestReportDTO(
//                                            testResponse.getId(),
//                                            testResponse.getExpiryDate(),
//                                            testResponse.getState(),
//                                            testResponse.getType(),
//                                            testResponse.getBatteryCapacity(),
//                                            testResponse.getMaxSupport(),
//                                            testResponse.getEnginePowerMax(),
//                                            testResponse.getEnginePowerNominal(),
//                                            testResponse.getEngineTorque(),
//                                            reportEntries
//                                    );
//                                } catch (Exception e) {
//                                    logger.error("Failed to save TestReport for testId {}: {}", testId, e.getMessage());
//                                    throw new RuntimeException("Failed to save TestReport", e);
//                                }
//                            });
//                })
//                .doOnError(e -> logger.error("Error fetching report for testId {}: {}", testId, e.getMessage()));
//    }


    //to get the whole test report (including test entries)
    public Mono<TestReportDTO> getTestReportById(String testId) {
        logger.info("Fetching report for testId: {}", testId);

        return fetchCsvReport(testId)
                .flatMap(csv -> parseCsvToEntries(csv, testId))
                .flatMap(entries -> fetchAndValidateTest(testId)
                        .flatMap(test -> saveTestReport(test, entries))
                )
                .doOnError(e -> logger.error("Error fetching report for testId {}: {}", testId, e.getMessage()));
    }

    private Mono<String> fetchCsvReport(String testId) {
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

    private Mono<List<TestReportEntryDTO>> parseCsvToEntries(String csvData, String testId) {
        try {
            List<TestReportEntryDTO> entries = parseCsvToReportEntries(csvData);
            logger.info("Parsed {} report entries for testId {}", entries.size(), testId);
            return Mono.just(entries);
        } catch (Exception e) {
            logger.error("CSV parsing failed for {}: {}", testId, e.getMessage());
            return Mono.error(new RuntimeException("Failed to parse CSV", e));
        }
    }

    private Mono<TestResponseDTO> fetchAndValidateTest(String testId) {
        return getTestResultById(testId)
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

    private Mono<TestReportDTO> saveTestReport(TestResponseDTO test, List<TestReportEntryDTO> entryDTOs) {
        TestReport testReport = new TestReport(
                test.getId(),
                test.getExpiryDate(),
                test.getState(),
                test.getType(),
                test.getBatteryCapacity(),
                test.getMaxSupport(),
                test.getEnginePowerMax(),
                test.getEnginePowerNominal(),
                test.getEngineTorque(),
                null
        );

        List<TestReportEntry> entries = entryDTOs.stream()
                .map(dto -> new TestReportEntry(
                        testReport,
                        dto.timestamp(),
                        dto.batteryVoltage(),
                        dto.batteryCurrent(),
                        dto.batteryCapacity(),
                        dto.batteryTemperatureCelsius(),
                        dto.chargeStatus(),
                        dto.assistanceLevel(),
                        dto.torqueCrankNm(),
                        dto.bikeWheelSpeedKmh(),
                        dto.cadanceRpm(),
                        dto.engineRpm(),
                        dto.enginePowerWatt(),
                        dto.wheelPowerWatt(),
                        dto.rollTorque(),
                        dto.loadcellN(),
                        dto.rolHz(),
                        dto.horizontalInclination(),
                        dto.verticalInclination(),
                        dto.loadPower(),
                        dto.statusPlug()
                ))
                .collect(Collectors.toList());

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
                    entryDTOs
            ));
        } catch (Exception e) {
            logger.error("Failed to save report for testId {}: {}", test.getId(), e.getMessage());
            return Mono.error(new RuntimeException("Failed to save TestReport", e));
        }
    }

    private List<TestReportEntryDTO> parseCsvToReportEntries(String csvData) {
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
        return testBenchRepository.findAll();
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

    public List<TestBench> findByTechnicianIdAndStatus(Integer technicianId, Status status) {
        return testBenchRepository.findByTechnicianIdAndStatus(technicianId, status);
    }
}