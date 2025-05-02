package integration4.evalebike.controller.technician;

import integration4.evalebike.controller.technician.dto.BikeDto;
import integration4.evalebike.controller.technician.dto.TestResponseDTO;
import integration4.evalebike.controller.viewModel.ReportViewModel;
import integration4.evalebike.controller.viewModel.ReportsViewModel;
import integration4.evalebike.controller.viewModel.TestReportEntryViewModel;
import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.domain.TestReport;
import integration4.evalebike.domain.TestReportEntry;
import integration4.evalebike.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/technician")
public class TechnicianController {
    private final BikeOwnerService bikeOwnerService;
    private final BikeService bikeService;
    private final QrCodeService qrCodeService;
    private final TestBenchService testBenchService;
    private final TestReportService testReportService;
    private static final Logger logger = LoggerFactory.getLogger(TechnicianController.class);

    public TechnicianController(BikeOwnerService bikeOwnerService, BikeService bikeService, QrCodeService qrCodeService, TestBenchService testBenchService, TestReportService testReportService) {
        this.bikeOwnerService = bikeOwnerService;
        this.bikeService = bikeService;
        this.qrCodeService = qrCodeService;
        this.testBenchService = testBenchService;
        this.testReportService = testReportService;
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
    public String testTypes(@PathVariable String bikeQR, Model model) {
        Bike bike = bikeService.getByQR(bikeQR);
        model.addAttribute("bike", bike);
        return "technician/test-types";
    }

    @GetMapping("/loading")
    public String loading(@RequestParam("testId") String testId, Model model) {
        model.addAttribute("testId", testId);
        return "technician/loading";
    }


    @GetMapping("/status/{testId}")
    @ResponseBody
    public Mono<TestResponseDTO> getTestStatus(@PathVariable String testId) {
        logger.info("Fetching status for testId: {}", testId);
        return testBenchService.getTestStatusById(testId)
                .doOnNext(response -> logger.debug("Status for testId {}: {}", testId, response.getState()))
                .doOnError(e -> logger.error("Error fetching status for testId {}: {}", testId, e.getMessage()));
    }

    @GetMapping("/report/{testId}")
    public Mono<String> showReportByTestId(@PathVariable("testId") String testId, Model model) {
        logger.info("Fetching report for testId: {}", testId);

        return Mono.fromCallable(() -> testReportService.getTestReportWithEntriesById(testId))
                .flatMap(optionalReport -> Mono.justOrEmpty(optionalReport)
                        .map(report -> {
                            ReportViewModel reportVm = ReportViewModel.from(report);
                            List<TestReportEntry> entries = report.getReportEntries();
                            TestReportEntryViewModel summaryVm = entries != null && !entries.isEmpty()
                                    ? TestReportEntryViewModel.summarize(entries)
                                    : null;

                            model.addAttribute("report", reportVm);
                            model.addAttribute("summary", summaryVm);
                            return "technician/test-report-details";
                        })
                        .switchIfEmpty(Mono.error(new RuntimeException("TestReport not found for testId: " + testId))))
                .onErrorResume(e -> {
                    logger.error("Error fetching report for testId {}: {}", testId, e.getMessage(), e);
                    model.addAttribute("error", e.getMessage());
                    return Mono.just("technician/error");
                });
    }

    //this shows a list of test report
    @GetMapping("/test-report-dashboard")
    public ModelAndView showReportDashboard(Model model) {
        final ModelAndView modelAndView = new ModelAndView("technician/test-report-dashboard");
        modelAndView.addObject("reports", ReportsViewModel.from(testReportService.getAllReports()));
        return modelAndView;
    }
}









