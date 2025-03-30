package integration4.evalebike.controller.technician;

import integration4.evalebike.controller.technician.dto.BikeWithChassisNumberDto;
import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeOwner;
import integration4.evalebike.service.BikeOwnerService;
import integration4.evalebike.service.BikeService;
import integration4.evalebike.service.QrCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // Show all bikes owned by a specific bike owner
    @GetMapping("/bikes/owner/{id}")
    public String showBikesForOwner(@PathVariable("id") Integer ownerId, Model model) {
        List<BikeWithChassisNumberDto> bikeOwnerBikes = bikeOwnerService.getAllBikeWithChassisNumbersOfOwner(ownerId);
        model.addAttribute("bikes", bikeOwnerBikes);
        return "technician/bike-dashboard";
    }

    @GetMapping("/bikes")
    public String logBikes(Model model) {
        List<Bike> bikes = bikeService.getBikes();
        for (Bike bike : bikes) {
            String qrCodeImage = qrCodeService.generateQrCodeBase64(bike.getBikeQR(), 200, 200);
            bike.setQrCodeImage(qrCodeImage);
        }
        model.addAttribute("bikes", bikes);
        return "technician/bike-dashboard";
    }

    @GetMapping("/bike-owners")
    public String logBikeOwners(Model model) {
        List<BikeOwner> bikeOwners = bikeOwnerService.getAllBikeOwners();
        model.addAttribute("bikeOwners", bikeOwners);
        return "technician/bike-owner-dashboard";
    }

    @GetMapping("/bikes/add")
    public String showAddBikeForm(Model model) {
        model.addAttribute("bike", new Bike());
        return "technician/add-bike";
    }

    @GetMapping("/bike-owners/add")
    public String showAddBikeOwnerForm(Model model) {
        model.addAttribute("bikeOwner", new BikeOwner());
        return "technician/add-bike-owner";
    }

    @PostMapping("/bikes/add")
    public String addBike() {
        return "technician/add-bike";
    }

    @PostMapping("/bike-owners/add")
    public String addBikeOwner() {
        return "technician/add-bike-owner";
    }

    // Show details for a specific bike
    @GetMapping("/bikes/{bikeQR}")
    public String showBikeDetails(@PathVariable String bikeQR, Model model) {
        Bike bike = bikeService.getBikeByQR(bikeQR);
        model.addAttribute("bike", bike);
        return "technician/bike-dashboard";
    }
}