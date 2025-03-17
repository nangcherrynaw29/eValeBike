package integration4.evalebike.controller.superAdmin;

import integration4.evalebike.controller.viewmodel.AdminsViewModel;
import integration4.evalebike.service.AdminService;
import integration4.evalebike.service.SuperAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/super-admin")
public class SuperAdminController {
    private final SuperAdminService superAdminService;
    private final AdminService adminService;

    public SuperAdminController(SuperAdminService superAdminService, AdminService adminService) {
        this.superAdminService = superAdminService;
        this.adminService = adminService;
    }

    @GetMapping("/admins")
    public ModelAndView index() {
        final ModelAndView modelAndView = new ModelAndView("superAdmin/admin-management");
        modelAndView.addObject("superAdmins", AdminsViewModel.from(adminService.getAllAdmins()));
        return modelAndView;
    }

    @GetMapping("/admins/add")
    public ModelAndView add() {
        return new ModelAndView("superAdmin/add-admin");
    }

}






