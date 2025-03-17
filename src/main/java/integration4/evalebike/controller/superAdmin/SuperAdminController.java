package integration4.evalebike.controller.superAdmin;

import integration4.evalebike.controller.viewModel.AdminsViewModel;
import integration4.evalebike.domain.Administrator;
import integration4.evalebike.domain.SuperAdmin;
import integration4.evalebike.service.AdminService;
import integration4.evalebike.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
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






