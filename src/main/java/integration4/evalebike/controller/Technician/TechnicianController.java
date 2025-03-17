package integration4.evalebike.controller.Technician;

import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.service.BikeOwnerService;
import integration4.evalebike.service.BikeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/technician")
public class TechnicianController {
    private final BikeOwnerService bikeOwnerService;
    private final BikeService bikeService;

    public TechnicianController(BikeOwnerService bikeOwnerService, BikeService bikeService) {
        this.bikeOwnerService = bikeOwnerService;
        this.bikeService = bikeService;
    }

    @GetMapping("/bike-dashboard")
    public String logBikes(Model model) {
        List<Bike> bikes = bikeService.getBikes();
        model.addAttribute("bikes", bikes);
        return "technician/bike-dashboard";
    }

    @GetMapping("/bike-owner-dashboard")
    public String logBikeOwners(Model model) {
        List<BikeOwner> bikeOwners = bikeOwnerService.getAllBikeOwners();
        model.addAttribute("bikeOwners", bikeOwners);
        return "technician/bike-owner-dashboard";
    }

    @GetMapping("/add-bike")
    public String showAddBikeForm(Model model) {
        model.addAttribute("bike", new Bike());
        return "technician/add-bike";
    }

    @GetMapping("/add-bike-owner")
    public String showAddBikeOwnerForm(Model model) {
        model.addAttribute("bikeOwner", new BikeOwner());
        return "technician/add-bike-owner";
    }

    @PostMapping("/add-bike")
    public String addBike(){
        return "technician/add-bike";
    }

    @PostMapping("/add-bike-owner")
    public String addBikeOwner(){
        return "technician/add-bike-owner";
    }
}
