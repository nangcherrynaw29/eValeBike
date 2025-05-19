package integration4.evalebike.controller.superAdmin;

import integration4.evalebike.controller.viewModel.AdminsViewModel;
import integration4.evalebike.controller.viewModel.CompaniesViewModel;
import integration4.evalebike.service.AdminService;
import integration4.evalebike.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/super-admin/admins")
public class SuperAdminController {
    private final AdminService adminService;
    private final CompanyService companyService;

    public SuperAdminController(AdminService adminService, CompanyService companyService) {
        this.adminService = adminService;
        this.companyService = companyService;
    }

    @GetMapping()
    public ModelAndView index() {
        final ModelAndView modelAndView = new ModelAndView("superAdmin/admin-dashboard");
        modelAndView.addObject("superAdmins", AdminsViewModel.from(adminService.getAllAdmins()));
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView add() {
        final ModelAndView modelAndView = new ModelAndView("superAdmin/add-admin");
        CompaniesViewModel wrapper = CompaniesViewModel.from(companyService.findAll());
        modelAndView.addObject("companies", wrapper.companies());
        return modelAndView;
    }

    @GetMapping("/pending-approvals")
    public String showPendingApprovals(Model model) {
        return "superAdmin/pending-approvals";
    }
}