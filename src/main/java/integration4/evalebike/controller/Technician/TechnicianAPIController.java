package integration4.evalebike.controller.technician;

import integration4.evalebike.controller.technician.dto.*;
import integration4.evalebike.controller.testBench.dto.TestRequestDTO;
import integration4.evalebike.controller.testBench.dto.TestResponseDTO;
import integration4.evalebike.domain.*;
import integration4.evalebike.repository.TestEventRepository;
import integration4.evalebike.security.CustomUserDetails;
import integration4.evalebike.service.BikeOwnerService;
import integration4.evalebike.service.BikeService;
import integration4.evalebike.service.RecentActivityService;
import integration4.evalebike.service.TestBenchService;
import jakarta.validation.Valid;
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

@Controller
@RequestMapping("/api/technician")
public class TechnicianAPIController {

    private final BikeService bikeService;
    private final BikeOwnerService bikeOwnerService;
    private final BikeMapper bikeMapper;
    private final BikeOwnerMapper bikeOwnerMapper;
    private final TestBenchService  testBenchService;
    private final TestEventRepository testEventRepository;
    private final RecentActivityService recentActivityService;

    public TechnicianAPIController(BikeService bikeService, BikeOwnerService bikeOwnerService, BikeMapper bikeMapper, BikeOwnerMapper bikeOwnerMapper, TestBenchService testBenchService, TestEventRepository testEventRepository, RecentActivityService recentActivityService) {
        this.bikeService = bikeService;
        this.bikeOwnerService = bikeOwnerService;
        this.bikeMapper = bikeMapper;
        this.bikeOwnerMapper = bikeOwnerMapper;
        this.testBenchService = testBenchService;
        this.testEventRepository = testEventRepository;
        this.recentActivityService = recentActivityService;
    }

    @PostMapping("/bikes")
    public ResponseEntity<BikeDto> createBike(@RequestBody @Valid final AddBikeDto addBikeDto, @AuthenticationPrincipal final CustomUserDetails userDetails) throws Exception {
        final Bike bike = bikeService.addBike(addBikeDto.brand(), addBikeDto.model(), addBikeDto.chassisNumber(), addBikeDto.productionYear(),
                addBikeDto.bikeSize(), addBikeDto.mileage(), addBikeDto.gearType(), addBikeDto.engineType(), addBikeDto.powerTrain(),
                addBikeDto.accuCapacity(), addBikeDto.maxSupport(), addBikeDto.maxEnginePower(), addBikeDto.nominalEnginePower(),
                addBikeDto.engineTorque(), addBikeDto.lastTestDate());
        recentActivityService.save(new RecentActivity(Activity.BIKE_ADDED, "Created bike with chassis number: " + addBikeDto.chassisNumber(), LocalDateTime.now(), userDetails.getUserId()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bikeMapper.toBikeDto(bike));
    }

    @PostMapping("/bikeOwners")
    public ResponseEntity<BikeOwnerDto> createBikeOwner(@RequestBody @Valid final AddBikeOwnerDto addBikeOwnerDto, @AuthenticationPrincipal final CustomUserDetails userDetails) throws Exception {
        final BikeOwner bikeOwner = bikeOwnerService.addBikeOwner(addBikeOwnerDto.name(), addBikeOwnerDto.email(), addBikeOwnerDto.phoneNumber(), addBikeOwnerDto.birthDate());
        recentActivityService.save(new RecentActivity(Activity.CREATED_USER, "Created bike owner " + addBikeOwnerDto.name(), LocalDateTime.now(), userDetails.getUserId()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bikeOwnerMapper.toBikeOwnerDto(bikeOwner));
    }

    @PostMapping("/start/{bikeQR}")
    public Mono<String> startTest(@PathVariable String bikeQR, @RequestParam("testType") String testType, Principal principal, @AuthenticationPrincipal final CustomUserDetails userDetails) throws Exception {
        System.out.println("Received bikeQR: " + bikeQR + ", testType: " + testType);

        String technicianUsername = principal != null ? principal.getName() : "anonymous";
        recentActivityService.save(new RecentActivity(Activity.INITIALIZED_TEST, "All tests started successfully", LocalDateTime.now(), userDetails.getUserId()));
        return Mono.justOrEmpty(bikeService.findById(bikeQR))
                .switchIfEmpty(Mono.error(new RuntimeException("Bike not found with QR: " + bikeQR)))
                .doOnNext(bike -> System.out.println("Found bike: " + bike))
                .map(bike -> new TestRequestDTO(
                        testType.toUpperCase(),
                        bike.getAccuCapacity(),
                        bike.getMaxSupport(),
                        bike.getMaxEnginePower(),
                        bike.getNominalEnginePower(),
                        bike.getEngineTorque()
                ))
                .flatMap(testRequestDTO -> testBenchService.startTest(testRequestDTO, technicianUsername))
                .flatMap(response -> {
                    String testId = response.getId();
                    System.out.println("Creating TestEvent with bikeQR: " + bikeQR + ", testId: " + testId + ", technicianUsername: " + technicianUsername);

                    // Create TestEvent
                    TestEvent testEvent = new TestEvent(bikeQR, testId, technicianUsername);
                    return Mono.fromCallable(() -> {
                                TestEvent savedEvent = testEventRepository.save(testEvent);
                                System.out.println("Saved TestEvent: " + savedEvent);
                                return savedEvent;
                            })
                            .thenReturn("redirect:/technician/loading?testId=" + testId);
                })
                .onErrorResume(e -> {
                    System.err.println("Error starting test: " + e.getMessage());
                    e.printStackTrace();
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

    @GetMapping("/status/{testId}")
    @ResponseBody
    public Mono<TestResponseDTO> getTestStatus(@PathVariable String testId) {
        return testBenchService.getTestResultById(testId);
    }

}