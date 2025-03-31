package integration4.evalebike.service;

import integration4.evalebike.controller.testBench.dto.TestReportDTO;
import integration4.evalebike.controller.testBench.dto.TestReportEntryDTO;
import integration4.evalebike.controller.testBench.dto.TestRequestDTO;
import integration4.evalebike.controller.testBench.dto.TestResponseDTO;
import integration4.evalebike.domain.TestReport;
import integration4.evalebike.domain.TestReportEntry;
import integration4.evalebike.repository.TestReportRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatusCode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//It would make sense more if the name is just TestService.
@Service
public class TestBenchService {
    private final WebClient webClient;
    private final TestReportRepository testReportRepository;

    public TestBenchService( WebClient webClient, TestReportRepository testReportRepository) {
        this.webClient = webClient;
        this.testReportRepository = testReportRepository;
    }

//    public TestResponseDTO startTest(TestRequestDTO request, String technicianUsername) {
//        // Prepare API request
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("type", request.getTestType());
//        requestBody.put("batteryCapacity", request.getBatteryCapacity());
//        requestBody.put("maxSupport", request.getMaxSupport());
//        requestBody.put("enginePowerMax", request.getEnginePowerMax());
//        requestBody.put("enginePowerNominal", request.getEnginePowerNominal());
//        requestBody.put("engineTorque", request.getEngineTorque());
//
//        System.out.println("Sending request body: " + requestBody);
//        // Call external API
//        TestResponseDTO responseDto = webClient.post()
//                .uri("https://testbench.raoul.dev/api/test")
//                .header("X-Api-Key", "9e8dffd7-f6e1-45b4-b4aa-69fd257ca200")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(requestBody)
//                .retrieve()
//                .onStatus(HttpStatusCode::isError, response -> {
//                    return response.bodyToMono(String.class)
//                            .flatMap(errorBody -> {
//                                System.err.println("Error response body: " + errorBody);
//                                return Mono.error(new RuntimeException("Failed to start test: " + errorBody));
//                            });
//                })
//                .bodyToMono(TestResponseDTO.class)
//                .block();
//
//        return responseDto;
//
//    }

    public Mono<TestResponseDTO> startTest(TestRequestDTO request, String technicianUsername) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("type", request.getTestType());
        requestBody.put("batteryCapacity", request.getBatteryCapacity());
        requestBody.put("maxSupport", request.getMaxSupport());
        requestBody.put("enginePowerMax", request.getEnginePowerMax());
        requestBody.put("enginePowerNominal", request.getEnginePowerNominal());
        requestBody.put("engineTorque", request.getEngineTorque());
        requestBody.put("technicianUsername", technicianUsername);

        return webClient.post()
                .uri("/api/tests")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                return Mono.error(new RuntimeException("Failed to start test: " + errorBody));
                            });
                })
                .bodyToMono(TestResponseDTO.class);
    }

    public Mono<TestResponseDTO> getTestResultById(String testId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/test/{id}").build(testId))
                .header("X-Api-Key", "9e8dffd7-f6e1-45b4-b4aa-69fd257ca200")
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                return Mono.error(new RuntimeException("Failed to fetch test result: " + errorBody));
                            });
                })
                .bodyToMono(TestResponseDTO.class);
    }

    public Mono<TestReportDTO> getTestReportById(String testId) {
        return webClient.get()
                .uri("/api/test/{id}/report", testId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                return Mono.error(new RuntimeException("Failed to fetch test report: " + errorBody));
                            });
                })
                .bodyToMono(String.class)
                .flatMap(csvData -> {
                    List<TestReportEntryDTO> reportEntries = parseCsvToReportEntries(csvData);

                    return getTestResultById(testId)
                            .map(testResponse -> {
                                // Map DTO to entity
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
                                        null // Set reportEntries later
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

                                // Set bidirectional relationship
                                testReport.setReportEntries(entries);

                                // Save to database
                                testReportRepository.save(testReport);
                                // Entries are saved automatically due to CascadeType.ALL

                                // Return DTO
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
                            });
                })
                .filter(report -> "COMPLETED".equals(report.state()))
                .switchIfEmpty(Mono.error(new RuntimeException("Test is not completed yet")));
    }

    private List<TestReportEntryDTO> parseCsvToReportEntries(String csvData) {
        // Split CSV data into lines, skipping header
        String[] lines = csvData.trim().split("\n");
        return java.util.Arrays.stream(lines)
                .skip(1) // Skip header row
                .map(line -> {
                    String[] values = line.split(",");
                    return new TestReportEntryDTO(
                            LocalDateTime.parse(values[0]), // timestamp
                            Double.parseDouble(values[1]),  // batteryVoltage
                            Double.parseDouble(values[2]),  // batteryCurrent
                            Double.parseDouble(values[3]),  // batteryCapacity
                            Double.parseDouble(values[4]),  // batteryTemperatureCelsius
                            Integer.parseInt(values[5]),    // chargeStatus
                            Integer.parseInt(values[6]),    // assistanceLevel
                            Double.parseDouble(values[7]),  // torqueCrankNm
                            Double.parseDouble(values[8]),  // bikeWheelSpeedKmh
                            Integer.parseInt(values[9]),    // cadanceRpm
                            Integer.parseInt(values[10]),   // engineRpm
                            Double.parseDouble(values[11]), // enginePowerWatt
                            Double.parseDouble(values[12]), // wheelPowerWatt
                            Double.parseDouble(values[13]), // rollTorque
                            Double.parseDouble(values[14]), // loadcellN
                            Double.parseDouble(values[15]), // rolHz
                            Double.parseDouble(values[16]), // horizontalInclination
                            Double.parseDouble(values[17]), // verticalInclination
                            Double.parseDouble(values[18]), // loadPower
                            Boolean.parseBoolean(values[19]) // statusPlug
                    );
                })
                .collect(Collectors.toList());
    }
}
