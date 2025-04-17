package integration4.evalebike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(Model model) {
        return "/home";
    }

    @GetMapping("/technician/pre-check/{bikeQR}")
    public String technicianPreCheck(@PathVariable String bikeQR, Model model)
     {
         model.addAttribute("bikeQR", bikeQR);
        return "/technician/pre-check";
    }
}
