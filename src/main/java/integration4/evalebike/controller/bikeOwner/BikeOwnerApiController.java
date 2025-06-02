package integration4.evalebike.controller.bikeOwner;

import integration4.evalebike.service.BikeOwnerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bikeOwner")
public class BikeOwnerApiController {
    private final BikeOwnerService bikeOwnerService;

    public BikeOwnerApiController(BikeOwnerService bikeOwnerService) {
        this.bikeOwnerService = bikeOwnerService;
    }
}

