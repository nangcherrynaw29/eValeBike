package integration4.evalebike.controller;

import integration4.evalebike.service.BikeOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TechnicianController {
    private final BikeOwnerService bikeOwnerService;

    @Autowired
    public TechnicianController(BikeOwnerService bikeOwnerService) {
        this.bikeOwnerService = bikeOwnerService;
    }
}
