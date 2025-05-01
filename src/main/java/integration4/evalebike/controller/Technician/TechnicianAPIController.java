package integration4.evalebike.controller.technician;

import integration4.evalebike.controller.technician.dto.*;
import integration4.evalebike.controller.technician.dto.TestRequestDTO;
import integration4.evalebike.controller.technician.dto.TestResponseDTO;
import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.domain.TestReport;
import integration4.evalebike.repository.TestReportRepository;
import integration4.evalebike.service.BikeOwnerService;
import integration4.evalebike.service.BikeService;
import integration4.evalebike.service.TestBenchService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

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


    public TechnicianAPIController(BikeService bikeService, BikeOwnerService bikeOwnerService, BikeMapper bikeMapper,
                                   BikeOwnerMapper bikeOwnerMapper, TestBenchService testBenchService,
                                   TestReportRepository testReportRepository) {
        this.bikeService = bikeService;
        this.bikeOwnerService = bikeOwnerService;
        this.bikeMapper = bikeMapper;
        this.bikeOwnerMapper = bikeOwnerMapper;
        this.testBenchService = testBenchService;
        this.testReportRepository = testReportRepository;
    }

    @PostMapping("/bikes")
    public ResponseEntity<BikeDto> createBike(@RequestBody @Valid final AddBikeDto addBikeDto) throws Exception {
        final Bike bike = bikeService.addBike(addBikeDto.brand(), addBikeDto.model(), addBikeDto.chassisNumber(), addBikeDto.productionYear(),
                addBikeDto.bikeSize(), addBikeDto.mileage(), addBikeDto.gearType(), addBikeDto.engineType(), addBikeDto.powerTrain(),
                addBikeDto.accuCapacity(), addBikeDto.maxSupport(), addBikeDto.maxEnginePower(), addBikeDto.nominalEnginePower(),
                addBikeDto.engineTorque(), addBikeDto.lastTestDate());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bikeMapper.toBikeDto(bike));
    }

    @PostMapping("/bikeOwners")
    public ResponseEntity<BikeOwnerDto> createBikeOwner(@RequestBody @Valid final AddBikeOwnerDto addBikeOwnerDto) {
        final BikeOwner bikeOwner = bikeOwnerService.addBikeOwner(addBikeOwnerDto.name(), addBikeOwnerDto.email(), addBikeOwnerDto.phoneNumber(), addBikeOwnerDto.birthDate());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bikeOwnerMapper.toBikeOwnerDto(bikeOwner));
    }

    @PostMapping("/start/{bikeQR}")
    public Mono<String> startTest(@PathVariable String bikeQR, @RequestParam("testType") String testType, Principal principal) {

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



}