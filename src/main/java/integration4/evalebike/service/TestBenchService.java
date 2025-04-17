//package integration4.evalebike.service;
//
//import integration4.evalebike.controller.testBench.dto.TestReportDTO;
//import integration4.evalebike.controller.testBench.dto.TestReportEntryDTO;
//import integration4.evalebike.controller.testBench.dto.TestRequestDTO;
//import integration4.evalebike.controller.testBench.dto.TestResponseDTO;
//import integration4.evalebike.domain.TestReport;
//import integration4.evalebike.domain.TestReportEntry;
//import integration4.evalebike.repository.TestReportRepository;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//import org.springframework.http.HttpStatusCode;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
////It would make sense more if the name is just TestService.
//@Service
//public class TestBenchService {
//    private final WebClient webClient;
//    private final TestReportRepository testReportRepository;
//
//    public TestBenchService( WebClient webClient, TestReportRepository testReportRepository) {
//        this.webClient = webClient;
//        this.testReportRepository = testReportRepository;
//    }
//
//
//    public Mono<TestResponseDTO> startTest(TestRequestDTO request, String technicianUsername) {
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("type", request.getTestType());
//        requestBody.put("batteryCapacity", request.getBatteryCapacity());
//        requestBody.put("maxSupport", request.getMaxSupport());
//        requestBody.put("enginePowerMax", request.getEnginePowerMax());
//        requestBody.put("enginePowerNominal", request.getEnginePowerNominal());
//        requestBody.put("engineTorque", request.getEngineTorque());
//        requestBody.put("technicianUsername", technicianUsername);
//
//        return webClient.post()
//                .uri("/api/test")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(requestBody)
//                .retrieve()
//                .onStatus(HttpStatusCode::isError, response -> {
//                    return response.bodyToMono(String.class)
//                            .flatMap(errorBody -> {
//                                return Mono.error(new RuntimeException("Failed to start test: " + errorBody));
//                            });
//                })
//                .bodyToMono(TestResponseDTO.class);
//    }
//
//    public Mono<TestResponseDTO> getTestResultById(String testId) {
//        return webClient.get()
//                .uri(uriBuilder -> uriBuilder.path("/api/test/{id}").build(testId))
//                .header("X-Api-Key", "9e8dffd7-f6e1-45b4-b4aa-69fd257ca200")
//                .retrieve()
//                .onStatus(HttpStatusCode::isError, response -> {
//                    return response.bodyToMono(String.class)
//                            .flatMap(errorBody -> {
//                                return Mono.error(new RuntimeException("Failed to fetch test result: " + errorBody));
//                            });
//                })
//                .bodyToMono(TestResponseDTO.class);
//    }
//
//    public Mono<TestReportDTO> getTestReportById(String testId) {
//        return webClient.get()
//                .uri("/api/test/{id}/report", testId)
//                .retrieve()
//                .onStatus(HttpStatusCode::isError, response -> {
//                    return response.bodyToMono(String.class)
//                            .flatMap(errorBody -> {
//                                return Mono.error(new RuntimeException("Failed to fetch test report: " + errorBody));
//                            });
//                })
//                .bodyToMono(String.class)
//                .flatMap(csvData -> {
//                    List<TestReportEntryDTO> reportEntries = parseCsvToReportEntries(csvData);
//
//                    return getTestResultById(testId)
//                            .map(testResponse -> {
//                                // Map DTO to entity
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
//                                        null // Set reportEntries later
//                                );
//
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
//
//                                // Set bidirectional relationship
//                                testReport.setReportEntries(entries);
//
//                                // Save to database
//                                testReportRepository.save(testReport);
//                                // Entries are saved automatically due to CascadeType.ALL
//
//                                // Return DTO
//                                return new TestReportDTO(
//                                        testResponse.getId(),
//                                        testResponse.getExpiryDate(),
//                                        testResponse.getState(),
//                                        testResponse.getType(),
//                                        testResponse.getBatteryCapacity(),
//                                        testResponse.getMaxSupport(),
//                                        testResponse.getEnginePowerMax(),
//                                        testResponse.getEnginePowerNominal(),
//                                        testResponse.getEngineTorque(),
//                                        reportEntries
//                                );
//                            });
//                })
//                .filter(report -> "COMPLETED".equals(report.state()))
//                .switchIfEmpty(Mono.error(new RuntimeException("Test is not completed yet")));
//    }
//
//    private List<TestReportEntryDTO> parseCsvToReportEntries(String csvData) {
//        // Split CSV data into lines, skipping header
//        String[] lines = csvData.trim().split("\n");
//        return java.util.Arrays.stream(lines)
//                .skip(1) // Skip header row
//                .map(line -> {
//                    String[] values = line.split(",");
//                    return new TestReportEntryDTO(
//                            LocalDateTime.parse(values[0]), // timestamp
//                            Double.parseDouble(values[1]),  // batteryVoltage
//                            Double.parseDouble(values[2]),  // batteryCurrent
//                            Double.parseDouble(values[3]),  // batteryCapacity
//                            Double.parseDouble(values[4]),  // batteryTemperatureCelsius
//                            Integer.parseInt(values[5]),    // chargeStatus
//                            Integer.parseInt(values[6]),    // assistanceLevel
//                            Double.parseDouble(values[7]),  // torqueCrankNm
//                            Double.parseDouble(values[8]),  // bikeWheelSpeedKmh
//                            Integer.parseInt(values[9]),    // cadanceRpm
//                            Integer.parseInt(values[10]),   // engineRpm
//                            Double.parseDouble(values[11]), // enginePowerWatt
//                            Double.parseDouble(values[12]), // wheelPowerWatt
//                            Double.parseDouble(values[13]), // rollTorque
//                            Double.parseDouble(values[14]), // loadcellN
//                            Double.parseDouble(values[15]), // rolHz
//                            Double.parseDouble(values[16]), // horizontalInclination
//                            Double.parseDouble(values[17]), // verticalInclination
//                            Double.parseDouble(values[18]), // loadPower
//                            Boolean.parseBoolean(values[19]) // statusPlug
//                    );
//                })
//                .collect(Collectors.toList());
//    }
//}
package integration4.evalebike.service;

import integration4.evalebike.controller.testBench.dto.TestReportDTO;
import integration4.evalebike.controller.testBench.dto.TestReportEntryDTO;
import integration4.evalebike.controller.testBench.dto.TestRequestDTO;
import integration4.evalebike.controller.testBench.dto.TestResponseDTO;
import integration4.evalebike.domain.TestReport;
import integration4.evalebike.domain.TestReportEntry;
import integration4.evalebike.repository.TestReportRepository;
import io.netty.handler.timeout.TimeoutException;
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
    private final String apiKey = "9e8dffd7-f6e1-45b4-b4aa-69fd257ca200";

    public TestBenchService(WebClient.Builder webClientBuilder, TestReportRepository testReportRepository) {
        this.testBenchClient = webClientBuilder
                .baseUrl("https://testbench.raoul.dev")
                .defaultHeader("X-Api-Key", apiKey)
                .build();
        this.testReportRepository = testReportRepository;
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

    public Mono<TestReportDTO> getTestReportById(String testId) {
        logger.info("Fetching report for testId: {}", testId);
        return testBenchClient.get()
                .uri("/api/test/{id}/report", testId)
                .header("X-Api-Key", apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .doOnNext(errorBody -> logger.error("Report fetch error for testId {}: {} - {}", testId, response.statusCode(), errorBody))
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Failed to fetch test report: " + errorBody))))
                .bodyToMono(String.class)
                .doOnNext(csvData -> logger.info("Received CSV data for testId {}: {}", testId, csvData.substring(0, Math.min(csvData.length(), 100)) + "..."))
                .flatMap(csvData -> {
                    List<TestReportEntryDTO> reportEntries;
                    try {
                        reportEntries = parseCsvToReportEntries(csvData);
                        logger.info("Parsed {} report entries for testId {}", reportEntries.size(), testId);
                    } catch (Exception e) {
                        logger.error("Failed to parse CSV for testId {}: {}", testId, e.getMessage());
                        return Mono.error(new RuntimeException("Failed to parse CSV data", e));
                    }
                    return getTestResultById(testId)
                            .map(testResponse -> {
                                if (testResponse.getId() == null) {
                                    logger.error("TestResponse ID is null for testId: {}", testId);
                                    throw new RuntimeException("TestResponse ID is null");
                                }
                                if (!"COMPLETED".equalsIgnoreCase(testResponse.getState())) {
                                    logger.warn("Test not completed for testId {}: state = {}", testId, testResponse.getState());
                                    throw new RuntimeException("Test is not completed yet");
                                }
                                TestReport testReport = new TestReport(
                                        testResponse.getId(),
                                        testResponse.getExpiryDate(),
                                        testResponse.getState(),
                                        testResponse.getType(),
                                        testResponse.getBatteryCapacity(),
                                        testResponse.getMaxSupport(),
                                        testResponse.getEnginePowerMax(),
                                        testResponse.getEnginePowerNominal(),
                                        testResponse.getEngineTorque(),
                                        null
                                );
                                List<TestReportEntry> entries = reportEntries.stream()
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
                                    TestReport savedReport = testReportRepository.save(testReport);
                                    logger.info("Saved TestReport with ID: {} for testId {}", savedReport.getId(), testId);
                                    return new TestReportDTO(
                                            testResponse.getId(),
                                            testResponse.getExpiryDate(),
                                            testResponse.getState(),
                                            testResponse.getType(),
                                            testResponse.getBatteryCapacity(),
                                            testResponse.getMaxSupport(),
                                            testResponse.getEnginePowerMax(),
                                            testResponse.getEnginePowerNominal(),
                                            testResponse.getEngineTorque(),
                                            reportEntries
                                    );
                                } catch (Exception e) {
                                    logger.error("Failed to save TestReport for testId {}: {}", testId, e.getMessage());
                                    throw new RuntimeException("Failed to save TestReport", e);
                                }
                            });
                })
                .doOnError(e -> logger.error("Error fetching report for testId {}: {}", testId, e.getMessage()));
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
}