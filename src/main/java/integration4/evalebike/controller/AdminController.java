package integration4.evalebike.controller;

import integration4.evalebike.service.BikeOwnerService;
import integration4.evalebike.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdminController {
    private final BikeOwnerService bikeOwnerService;
    private final TechnicianService technicianService;

    @Autowired
    public AdminController(BikeOwnerService bikeOwnerService, TechnicianService technicianService) {
        this.bikeOwnerService = bikeOwnerService;
        this.technicianService = technicianService;
    }
}
