package integration4.evalebike.controller.technician;

import integration4.evalebike.controller.technician.dto.BikeDto;
import integration4.evalebike.controller.technician.dto.TestResponseDTO;
import integration4.evalebike.controller.viewModel.ReportViewModel;
import integration4.evalebike.controller.viewModel.ReportsViewModel;
import integration4.evalebike.controller.viewModel.TestReportEntryViewModel;
import integration4.evalebike.controller.viewModel.VisualInspectionViewModel;
import integration4.evalebike.domain.*;
import integration4.evalebike.security.CustomUserDetails;
import integration4.evalebike.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/technician")
public class TechnicianController {
    private final BikeOwnerService bikeOwnerService;
    private final BikeService bikeService;
    private final QrCodeService qrCodeService;
    private final TestBenchService testBenchService;
    private final TestReportService testReportService;
    private final TestReportEntryService testReportEntryService;
    private final VisualInspectionService visualInspectionService;
    private static final Logger logger = LoggerFactory.getLogger(TechnicianController.class);

    public TechnicianController(BikeOwnerService bikeOwnerService, BikeService bikeService, QrCodeService qrCodeService, TestBenchService testBenchService, TestReportService testReportService, TestReportEntryService testReportEntryService, VisualInspectionService visualInspectionService) {
        this.bikeOwnerService = bikeOwnerService;
        this.bikeService = bikeService;
        this.qrCodeService = qrCodeService;
        this.testBenchService = testBenchService;
        this.testReportService = testReportService;
        this.testReportEntryService = testReportEntryService;
        this.visualInspectionService = visualInspectionService;
    }

    // Show all bikes owned by a specific bike owner
    @GetMapping("/bikes/owner/{id}")
    public String showBikesForOwner(@PathVariable("id") Integer ownerId, Model model) {
        List<BikeDto> bikeOwnerBikes = bikeOwnerService.getAllBikes(ownerId);
        model.addAttribute("bikes", bikeOwnerBikes);
        return "technician/bike-dashboard";
    }

    @GetMapping("/bikes")
    public String logBikes(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<Bike> bikes = bikeService.getAll();
        for (Bike bike : bikes) {
            String qrCodeImage = qrCodeService.generateQrCodeBase64(bike.getBikeQR(), 200, 200);
            bike.setQrCodeImage(qrCodeImage);
        }
        model.addAttribute("bikes", bikes);
        return "technician/bike-dashboard";
    }

    @GetMapping("/bike-owners")
    public String logBikeOwners(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<BikeOwner> bikeOwners = bikeOwnerService.getAll(userDetails);
        long totalBikes = bikeService.countAllBikes();
        long birthdayCount = bikeOwnerService.countOwnersWithBirthdayToday();

        String role = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("UNKNOWN");
        String userRole = role.replace("ROLE_", "");

        model.addAttribute("bikeOwners", bikeOwners);
        model.addAttribute("totalBikes", totalBikes);
        model.addAttribute("birthdayCount", birthdayCount);
        model.addAttribute("userRole", userRole);
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
    public String showReportByTestId(@PathVariable("testId") String testId, Model model) {
        logger.info("Fetching report for testId: {}", testId);

        try {
            TestReport report = testReportService.getTestReportWithEntriesById(testId);

            ReportViewModel reportVm = ReportViewModel.from(report);
            List<TestReportEntry> entries = report.getReportEntries();
            TestReportEntryViewModel summaryVm = (entries != null && !entries.isEmpty())
                    ? TestReportEntryViewModel.summarize(entries)
                    : null;

            VisualInspection inspection = visualInspectionService.getInspectionByReportID(testId);
            VisualInspectionViewModel inspectionViewModel = VisualInspectionViewModel.toViewModel(inspection);

            model.addAttribute("inspection", inspectionViewModel);
            model.addAttribute("report", reportVm);
            model.addAttribute("summary", summaryVm);

            return "technician/test-report-details";
        } catch (RuntimeException e) {
            logger.error("Error fetching report for testId {}: {}", testId, e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }



    //this shows a list of test report
    @GetMapping("/test-report-dashboard")
    public ModelAndView showReportDashboard(Model model) {
        final ModelAndView modelAndView = new ModelAndView("technician/test-report-dashboard");
        modelAndView.addObject("reports", ReportsViewModel.from(testReportService.getAllReports()));
        return modelAndView;
    }

    @GetMapping("/visualization-for-test-entry/{testId}")
    public ModelAndView showVisualizationForTestEntry(@PathVariable("testId") String testId, Model model) {
        final ModelAndView modelAndView = new ModelAndView("technician/visualization-for-test-entry");

        // Convert the entries into the view model format
        List<TestReportEntryViewModel> entryViewModels =
                testReportEntryService.getEntriesByReportId(testId).stream()
                        .map(TestReportEntryViewModel::from)
                        .collect(Collectors.toList());

        modelAndView.addObject("entries", entryViewModels);

        return modelAndView;
    }


    @GetMapping("/manual-test-form/{bikeQR}")
    public String showManualTestForm(@PathVariable String bikeQR, Model model) {
        Bike bike = bikeService.getByQR(bikeQR);
        model.addAttribute("bike", bike);
        return "technician/manual-test";
    }

    @GetMapping("/visual-inspection/{testId}")
    public ModelAndView showVisualInspection(@PathVariable String testId) {

        ModelAndView modelAndView = new ModelAndView("technician/visual-inspection");
        modelAndView.addObject("testId", testId);
        return modelAndView;
    }



    @GetMapping("/reports-by-bike/{qr}")
    public String getReportsByBike(@PathVariable("qr") String bikeQr, Model model) {
        List<ReportViewModel> reports = testReportService.getTestReportsByBikeQR(bikeQr) ;
        model.addAttribute("bikeQr", bikeQr);
        model.addAttribute("reports", reports);

        return "/technician/reports-by-qr";
    }

    @GetMapping("/compare/{id1}/{id2}")
    public String compare(@PathVariable String id1, @PathVariable String id2, Model model) {
        try {
            List<ReportViewModel> reportViewModels = new ArrayList<>();
            List<TestReportEntryViewModel> summaries = new ArrayList<>();

            // Add each report based on the IDs
            for (String id : new String[]{id1, id2}) {  // Loop over id1 and id2
                TestReport report = testReportService.getTestReportWithEntriesById(id);
                ReportViewModel reportVm = ReportViewModel.from(report);
                reportViewModels.add(reportVm);

                List<TestReportEntry> entries = report.getReportEntries();
                TestReportEntryViewModel summaryVm = (entries != null && !entries.isEmpty())
                        ? TestReportEntryViewModel.summarize(entries)
                        : null;
                summaries.add(summaryVm);
            }

            // Add the reports and summaries to the model
            model.addAttribute("reports", reportViewModels);
            model.addAttribute("summaries", summaries);

            return "technician/compare";
        } catch (RuntimeException e) {
            logger.error("Error fetching reports for testIds {} and {}: {}", id1, id2, e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }



}


















