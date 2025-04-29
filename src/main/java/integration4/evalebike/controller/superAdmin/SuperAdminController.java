package integration4.evalebike.controller.superAdmin;

import integration4.evalebike.controller.viewModel.AdminsViewModel;
import integration4.evalebike.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/super-admin/admins")
public class SuperAdminController {
    private final AdminService adminService;

    public SuperAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping()
    public ModelAndView index() {
        final ModelAndView modelAndView = new ModelAndView("superAdmin/admin-dashboard");
        modelAndView.addObject("superAdmins", AdminsViewModel.from(adminService.getAllAdmins()));
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView add() {
        return new ModelAndView("superAdmin/add-admin");
    }

}






