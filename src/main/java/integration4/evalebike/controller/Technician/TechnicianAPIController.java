package integration4.evalebike.controller.technician;

import integration4.evalebike.controller.technician.dto.*;
import integration4.evalebike.controller.technician.dto.TestRequestDTO;
import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.domain.TestReport;
import integration4.evalebike.repository.TestReportRepository;
import integration4.evalebike.domain.*;
import integration4.evalebike.security.CustomUserDetails;
import integration4.evalebike.service.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/technician")
public class TechnicianAPIController {

    private final BikeService bikeService;
    private final BikeOwnerService bikeOwnerService;
    private final BikeMapper bikeMapper;
    private final BikeOwnerMapper bikeOwnerMapper;
    private final TestBenchService testBenchService;
    private final TestReportRepository testReportRepository;
    private static final Logger logger = LoggerFactory.getLogger(TechnicianAPIController.class);
    private final RecentActivityService recentActivityService;
    private final TestReportEntryService testReportEntryService;


    public TechnicianAPIController(BikeService bikeService, BikeOwnerService bikeOwnerService, BikeMapper bikeMapper,
                                   BikeOwnerMapper bikeOwnerMapper, TestBenchService testBenchService,
                                   TestReportRepository testReportRepository, RecentActivityService recentActivityService, TestReportEntryService testReportEntryService) {
        this.bikeService = bikeService;
        this.bikeOwnerService = bikeOwnerService;
        this.bikeMapper = bikeMapper;
        this.testBenchService = testBenchService;
        this.bikeOwnerMapper = bikeOwnerMapper;
        this.testReportRepository = testReportRepository;
        this.recentActivityService = recentActivityService;
        this.testReportEntryService = testReportEntryService;
    }

    @GetMapping("/bikeOwners")
    public ResponseEntity<List<BikeOwnerDto>> getAllAdmins() {
        final List<BikeOwnerDto> bikeOwners = bikeOwnerService.getAll()
                .stream().map(bikeOwnerMapper::toBikeOwnerDto)
                .toList();
        return ResponseEntity.ok(bikeOwners);
    }

    @PostMapping("/bikes")
    public ResponseEntity<BikeDto> createBike(@RequestBody @Valid final AddBikeDto addBikeDto, @AuthenticationPrincipal final CustomUserDetails userDetails) throws Exception {
        final Bike bike = bikeService.add(addBikeDto.brand(), addBikeDto.model(), addBikeDto.chassisNumber(), addBikeDto.productionYear(),
                addBikeDto.bikeSize(), addBikeDto.mileage(), addBikeDto.gearType(), addBikeDto.engineType(), addBikeDto.powerTrain(),
                addBikeDto.accuCapacity(), addBikeDto.maxSupport(), addBikeDto.maxEnginePower(), addBikeDto.nominalEnginePower(),
                addBikeDto.engineTorque(), addBikeDto.lastTestDate());
        recentActivityService.save(new RecentActivity(Activity.BIKE_ADDED, "Created bike with chassis number: " + addBikeDto.chassisNumber(), LocalDateTime.now(), userDetails.getUserId()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bikeMapper.toBikeDto(bike));
    }

    @PostMapping("/bikeOwners")
    public ResponseEntity<BikeOwnerDto> createBikeOwner(@RequestBody @Valid final AddBikeOwnerDto addBikeOwnerDto, @AuthenticationPrincipal final CustomUserDetails userDetails) throws Exception {
        final BikeOwner bikeOwner = bikeOwnerService.add(addBikeOwnerDto.name(), addBikeOwnerDto.email(), addBikeOwnerDto.phoneNumber(), addBikeOwnerDto.birthDate());
        recentActivityService.save(new RecentActivity(Activity.CREATED_USER, "Created bike owner " + addBikeOwnerDto.name(), LocalDateTime.now(), userDetails.getUserId()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bikeOwnerMapper.toBikeOwnerDto(bikeOwner));
    }

    @DeleteMapping("/bikeOwners/{bikeOwnerId}")
    public ResponseEntity<Void> deleteBikeOwner(@PathVariable Integer bikeOwnerId, @AuthenticationPrincipal final CustomUserDetails userDetails) {
        bikeOwnerService.delete(bikeOwnerId);
        recentActivityService.save(new RecentActivity(Activity.DELETED_USER, "Deleted bike owner with id: " + bikeOwnerId, LocalDateTime.now(), userDetails.getUserId()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/bikes/{bikeId}")
    public ResponseEntity<Void> deleteBike(@PathVariable String bikeId, @AuthenticationPrincipal final CustomUserDetails userDetails) {
        bikeService.delete(bikeId);
        recentActivityService.save(new RecentActivity(Activity.BIKE_REMOVED, "Deleted bike with id: " + bikeId, LocalDateTime.now(), userDetails.getUserId()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/start/{bikeQR}")
    public Mono<String> startTest(@PathVariable String bikeQR, @RequestParam("testType") String testType, Principal principal, @AuthenticationPrincipal final CustomUserDetails userDetails) {

        recentActivityService.save(new RecentActivity(Activity.INITIALIZED_TEST, "Test started successfully.", LocalDateTime.now(), userDetails.getUserId()));
        String technicianUsername = principal != null ? principal.getName() : "anonymous";

        return Mono.fromCallable(() -> bikeService.findById(bikeQR)) // Convert Optional to Mono
                .flatMap(optionalBike -> optionalBike
                        .map(Mono::just)
                        .orElseGet(() -> Mono.error(new RuntimeException("Bike not found with QR: " + bikeQR))))
                .flatMap(bike -> {
                    TestRequestDTO testRequestDTO = new TestRequestDTO(
                            testType.toUpperCase(),
                            bike.getAccuCapacity(),
                            bike.getMaxSupport(),
                            bike.getMaxEnginePower(),
                            bike.getNominalEnginePower(),
                            bike.getEngineTorque()
                    );
                    return testBenchService.processTest(testRequestDTO, technicianUsername, bikeQR)
                            .flatMap(response -> {
                                // Save partial TestReport
                                TestReport partialReport = new TestReport();
                                partialReport.setId(response.getId());
                                partialReport.setBike(bike); // Set the Bike entity
                                partialReport.setTechnicianName(technicianUsername);
                                try {
                                    testReportRepository.save(partialReport);
                                } catch (Exception e) {
                                    return Mono.error(new RuntimeException("Failed to save partial TestReport", e));
                                }
                                return Mono.just("redirect:/technician/loading?testId=" + response.getId());
                            });
                })
                .onErrorResume(e -> {
                    String errorMessage = "Failed to start test";
                    if (e.getMessage().contains("Bike not found")) {
                        errorMessage = "Bike not found with QR: " + bikeQR;
                    } else if (e.getMessage().contains("Bad Request")) {
                        errorMessage = "Invalid test parameters";
                    }
                    String encodedError = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);
                    return Mono.just("redirect:/technician/bikes?error=" + encodedError);
                });
    }

    @GetMapping("/test-report-entries/{testId}")
    public ResponseEntity<List<TestReportEntryDTO>> getTestReport(@PathVariable String testId) {
        // Assuming service returns entities; map to DTO
        List<TestReportEntryDTO> entries = testReportEntryService.getEntriesByReportId(testId).stream()
                .map(entity -> new TestReportEntryDTO(
                        entity.getTimestamp(),
                        entity.getBatteryVoltage(),
                        entity.getBatteryCurrent(),
                        entity.getBatteryCapacity(),
                        entity.getBatteryTemperatureCelsius(),
                        entity.getChargeStatus(),
                        entity.getAssistanceLevel(),
                        entity.getTorqueCrankNm(),
                        entity.getBikeWheelSpeedKmh(),
                        entity.getCadanceRpm(),
                        entity.getEngineRpm(),
                        entity.getEnginePowerWatt(),
                        entity.getWheelPowerWatt(),
                        entity.getRollTorque(),
                        entity.getLoadcellN(),
                        entity.getRolHz(),
                        entity.getHorizontalInclination(),
                        entity.getVerticalInclination(),
                        entity.getLoadPower(),
                        entity.isStatusPlug()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(entries);
    }


    @GetMapping("/normalized-test-report-entries/{testId}")
    @ResponseBody
    public List<NormalizedTestReportEntryDTO> getNormalizedTestReportEntries(@PathVariable String testId) {
        return testReportEntryService.getNormalizedEntriesByReportId(testId);
    }


}