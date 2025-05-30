package integration4.evalebike.controller.admin;

import integration4.evalebike.controller.viewModel.TechniciansViewModel;
import integration4.evalebike.security.CustomUserDetails;
import integration4.evalebike.service.CompanyService;
import integration4.evalebike.service.TechnicianService;
import integration4.evalebike.service.TestBenchService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/technicians")
public class AdminUIController {
    private final TechnicianService technicianService;
    private final TestBenchService testBenchService;
    private final CompanyService companyService;

    public AdminUIController(TechnicianService technicianService, TestBenchService testBenchService, CompanyService companyService) {
        this.technicianService = technicianService;
        this.testBenchService = testBenchService;
        this.companyService = companyService;
    }

    @GetMapping()
    @Transactional(readOnly = true)
    public ModelAndView index(@AuthenticationPrincipal CustomUserDetails userDetails) {
        final ModelAndView modelAndView = new ModelAndView("admin/technician-dashboard");

        String role = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("UNKNOWN");
        String userRole = role.replace("ROLE_", "");

        modelAndView.addObject("technicians", TechniciansViewModel.fromTechnician(technicianService.getAll(userDetails)));
        modelAndView.addObject("testBenches", testBenchService.getAllTestBenches());
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("activeTestBenches", testBenchService.countActiveTestBenches());
        return modelAndView;
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("companies", companyService.getAll());
        return "admin/add-technician";
    }

    @GetMapping("/pending-approvals")
    public String showPendingApprovals(Model model) {
        return "admin/pending-approvals";
    }
}