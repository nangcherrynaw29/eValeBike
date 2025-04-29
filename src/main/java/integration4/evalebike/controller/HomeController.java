package integration4.evalebike.controller;

import integration4.evalebike.domain.Status;
import integration4.evalebike.domain.Technician;
import integration4.evalebike.domain.TestBench;
import integration4.evalebike.security.CustomUserDetails;
import integration4.evalebike.service.TechnicianService;
import integration4.evalebike.service.TestBenchService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private final TechnicianService technicianService;
    private final TestBenchService testBenchService;

    public HomeController(TechnicianService technicianService, TestBenchService testBenchService) {
        this.technicianService = technicianService;
        this.testBenchService = testBenchService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "/home";
    }

    @GetMapping("/technician/pre-check/{bikeQR}")
    public String technicianPreCheck(@PathVariable String bikeQR, Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        int technicianId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        Technician technician = technicianService.getTechnicianById(technicianId);
        model.addAttribute("bikeQR", bikeQR);
        model.addAttribute("technicianId", technician.getId());
        return "/technician/pre-check";
    }

    @PostMapping("/technician/pre-check")
    public String handlePreCheck(
            @RequestParam Integer testBenchNumber,
            @RequestParam Integer technicianId,
            @RequestParam String bikeQR) {

        testBenchService.assignTestBench(testBenchNumber, technicianId);
        return "redirect:/technician/bikes/test-types/" + bikeQR;
    }

    @GetMapping("/admin/admin-dashboard")
    public String technicianDashboard(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        int technicianId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        List<TestBench> activeTestBenches = testBenchService.findByTechnicianIdAndStatus(
                technicianId, Status.ACTIVE);
        model.addAttribute("activeTestBenches", activeTestBenches);
        return "/admin/admin-dashboard";
    }
}