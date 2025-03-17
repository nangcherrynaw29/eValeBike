package integration4.evalebike.controller.technician;

import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.service.BikeOwnerService;
import integration4.evalebike.service.BikeService;
import integration4.evalebike.service.QrCodeService;
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
    private final QrCodeService qrCodeService;

    public TechnicianController(BikeOwnerService bikeOwnerService, BikeService bikeService, QrCodeService qrCodeService) {
        this.bikeOwnerService = bikeOwnerService;
        this.bikeService = bikeService;
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/bike-dashboard")
    public String logBikes(Model model) {
        List<Bike> bikes = bikeService.getBikes();
        for (Bike bike : bikes) {
            String qrCodeImage = qrCodeService.generateQrCodeBase64(bike.getBikeQR(), 200, 200);
            bike.setQrCodeImage(qrCodeImage);
        }
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
