package integration4.evalebike.controller;

import integration4.evalebike.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SuperAdminController {
    private final AdminService adminService;

    @Autowired
    public SuperAdminController(AdminService adminService) {
        this.adminService = adminService;
    }
}
