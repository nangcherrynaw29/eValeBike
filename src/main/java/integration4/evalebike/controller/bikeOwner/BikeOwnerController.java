package integration4.evalebike.controller.bikeOwner;

import integration4.evalebike.controller.bikeOwner.dto.BikeOwnerProfileDto;
import integration4.evalebike.security.CustomUserDetails;
import integration4.evalebike.service.BikeOwnerService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BikeOwnerController {
    private final BikeOwnerService bikeOwnerService;

    public BikeOwnerController(BikeOwnerService bikeOwnerService) {
        this.bikeOwnerService = bikeOwnerService;
    }

    @GetMapping({"/bikeOwner/page", "/edit"})
    public String showEditForm(Model model, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        BikeOwnerProfileDto dto = bikeOwnerService.getProfile(userDetails.getUserId());
        model.addAttribute("profile", dto);
        return "bikeOwner/bikeOwner";
    }

    @PostMapping("/bikeOwner/edit")
    public String submitEditForm(@ModelAttribute("profile") BikeOwnerProfileDto dto,
                                 Authentication auth,
                                 Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        bikeOwnerService.updateProfile(userDetails.getUserId(), dto);

        model.addAttribute("modified", true);
        return "bikeOwner/bikeOwner";
    }
}