package integration4.evalebike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(Model model) {
        return "/home";
    }

    @GetMapping("/technician/pre-check")
    public String preCheck(Model model) {
        return "/technician/pre-check";
    }
}
