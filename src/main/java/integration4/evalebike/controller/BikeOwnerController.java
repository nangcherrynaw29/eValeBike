package integration4.evalebike.controller;

import integration4.evalebike.service.BikeOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BikeOwnerController {
    private final BikeOwnerService bikeOwnerService;

    public BikeOwnerController(BikeOwnerService bikeOwnerService) {
        this.bikeOwnerService = bikeOwnerService;
    }
}
