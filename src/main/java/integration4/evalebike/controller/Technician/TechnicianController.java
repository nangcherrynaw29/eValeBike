package integration4.evalebike.controller.Technician;

import integration4.evalebike.controller.testBench.dto.TestReportDTO;
import integration4.evalebike.controller.testBench.dto.TestRequestDTO;
import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.service.BikeOwnerService;
import integration4.evalebike.service.BikeService;
import integration4.evalebike.service.TestBenchService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/technician")
public class TechnicianController {
    private final BikeOwnerService bikeOwnerService;
    private final BikeService bikeService;
    private final TestBenchService testBenchService;

    public TechnicianController(BikeOwnerService bikeOwnerService, BikeService bikeService, TestBenchService testBenchService) {
        this.bikeOwnerService = bikeOwnerService;
        this.bikeService = bikeService;
        this.testBenchService = testBenchService;
    }

    @GetMapping("/bike-dashboard")
    public String logBikes(Model model) {
        List<Bike> bikes = bikeService.getBikes();
        model.addAttribute("bikes", bikes);
        return "technician/bike-dashboard";
    }

    @GetMapping("/bike-owner-dashboard")
    public String logBikeOwners(Model model) {
        List<BikeOwner> bikeOwners = bikeOwnerService.getAllBikeOwners();
        model.addAttribute("bikeOwners", bikeOwners);
        return "technician/bike-owner-dashboard";
    }

    @GetMapping("/add-bike")
    public String showAddBikeForm(Model model) {
        model.addAttribute("bike", new Bike());
        return "technician/add-bike";
    }

    @GetMapping("/add-bike-owner")
    public String showAddBikeOwnerForm(Model model) {
        model.addAttribute("bikeOwner", new BikeOwner());
        return "technician/add-bike-owner";
    }

    @PostMapping("/add-bike")
    public String addBike(){
        return "technician/add-bike";
    }

    @PostMapping("/add-bike-owner")
    public String addBikeOwner(){
        return "technician/add-bike-owner";
    }

    @GetMapping("/test-types/{bikeQR}")
    public String getTestTypes(@PathVariable String bikeQR, Model model) {
        Bike bike = bikeService.getBikes().stream()
                .filter(b -> b.getBikeQR().equals(bikeQR))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Bike not found with QR: " + bikeQR));
        model.addAttribute("bike", bike);
        return "technician/test-types"; // Maps to test-types.html
    }
//this is not working.. this method is supposed to hit the endpoint till we get the completed test
//    @PostMapping("/start/{bikeQR}")
//    public Mono<String> startTest(@PathVariable String bikeQR, @RequestParam("testType") String testType, Principal principal) {
//        String technicianUsername = principal != null ? principal.getName() : "anonymous";
//
//        return Mono.justOrEmpty(bikeService.findById(bikeQR))
//                .switchIfEmpty(Mono.error(new RuntimeException("Bike not found with QR: " + bikeQR)))
//                .map(bike -> new TestRequestDTO(
//                        testType,
//                        bike.getAccuCapacity(),
//                        bike.getMaxSupport(),
//                        bike.getMaxEnginePower(),
//                        bike.getNominalEnginePower(),
//                        bike.getEngineTorque()
//                ))
//                .flatMap(testRequestDTO -> testBenchService.startTest(testRequestDTO, technicianUsername))
//                .map(response -> "redirect:/technician/loading?testId=" + response.getId())
//                .onErrorResume(e -> {
//                    String encodedError = java.net.URLEncoder.encode(e.getMessage(), java.nio.charset.StandardCharsets.UTF_8);
//                    return Mono.just("redirect:/technician/bike-dashboard?error=" + encodedError);
//                });
//    }

    @GetMapping("/loading")
    public String showLoadingPage(@RequestParam("testId") String testId, Model model) {
        model.addAttribute("testId", testId);
        return "technician/loading";
    }

    @GetMapping("/test-result/{testId}")
    public String getTestResult(@PathVariable String testId, Model model) {
        TestReportDTO report = testBenchService.getTestReportById(testId).block(); // Blocking for simplicity
        model.addAttribute("report", report);
        return "technician/test-result";
    }
}

