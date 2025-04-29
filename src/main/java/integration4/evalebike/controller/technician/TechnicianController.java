package integration4.evalebike.controller.technician;

import integration4.evalebike.controller.technician.dto.BikeDto;
import integration4.evalebike.controller.testBench.dto.TestReportDTO;
import integration4.evalebike.controller.testBench.dto.TestResponseDTO;
import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.service.BikeOwnerService;
import integration4.evalebike.service.BikeService;
import integration4.evalebike.service.QrCodeService;
import integration4.evalebike.service.TestBenchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/technician")
public class TechnicianController {
    private final BikeOwnerService bikeOwnerService;
    private final BikeService bikeService;
    private final QrCodeService qrCodeService;
    private final TestBenchService testBenchService;
    public TechnicianController(BikeOwnerService bikeOwnerService, BikeService bikeService, QrCodeService qrCodeService, TestBenchService testBenchService) {
        this.bikeOwnerService = bikeOwnerService;
        this.bikeService = bikeService;
        this.qrCodeService = qrCodeService;
        this.testBenchService = testBenchService;
    }

    // Show all bikes owned by a specific bike owner
    @GetMapping("/bikes/owner/{id}")
    public String showBikesForOwner(@PathVariable("id") Integer ownerId, Model model) {
        List<BikeDto> bikeOwnerBikes = bikeOwnerService.getAllBikes(ownerId);
        model.addAttribute("bikes", bikeOwnerBikes);
        return "technician/bike-dashboard";
    }

    @GetMapping("/bikes")
    public String logBikes(Model model) {
        List<Bike> bikes = bikeService.getAll();
        for (Bike bike : bikes) {
            String qrCodeImage = qrCodeService.generateQrCodeBase64(bike.getBikeQR(), 200, 200);
            bike.setQrCodeImage(qrCodeImage);
        }
        model.addAttribute("bikes", bikes);
        return "technician/bike-dashboard";
    }

    @GetMapping("/bike-owners")
    public String logBikeOwners(Model model) {
        List<BikeOwner> bikeOwners = bikeOwnerService.getAll();
        model.addAttribute("bikeOwners", bikeOwners);
        return "technician/bike-owner-dashboard";
    }

    @GetMapping("/bikes/add")
    public String showAddBikeForm(Model model) {
        model.addAttribute("bike", new Bike());
        return "technician/add-bike";
    }

    @GetMapping("/bike-owners/add")
    public String showAddBikeOwnerForm(Model model) {
        model.addAttribute("bikeOwner", new BikeOwner());
        return "technician/add-bike-owner";
    }

    @PostMapping("/bikes/add")
    public String addBike() {
        return "technician/add-bike";
    }

    @PostMapping("/bike-owners/add")
    public String addBikeOwner() {
        return "technician/add-bike-owner";
    }

    // Show details for a specific bike
    @GetMapping("/bikes/{bikeQR}")
    public String showBikeDetails(@PathVariable String bikeQR, Model model) {
        Bike bike = bikeService.getByQR(bikeQR);
        model.addAttribute("bike", bike);
        return "technician/bike-dashboard";
    }

    @GetMapping("bikes/test-types/{bikeQR}")
    public String testTypes(@PathVariable String bikeQR,Model model) {
        Bike bike = bikeService.getByQR(bikeQR);
        model.addAttribute("bike", bike);
        return "technician/test-types";
    }

    @GetMapping("/loading")
    public String loading(@RequestParam("testId") String testId, Model model) {
        model.addAttribute("testId", testId);
        return "technician/loading";
    }

    @GetMapping("/test-status/{testId}")
    public Mono<TestResponseDTO> getTestResultById(@PathVariable String testId) {
        return testBenchService.getTestResultById(testId);
    }

    @GetMapping("/test-result/{testId}")
    public String getTestResult(@PathVariable String testId, Model model) {
        TestReportDTO report = testBenchService.getTestReportById(testId).block(); // Blocking for simplicity
        if (report == null) {
            return "redirect:/technician/bike-dashboard?error=No+report+found+for+test+ID+" + testId;
        }
        model.addAttribute("report", report);
        return "report";
    }

}

