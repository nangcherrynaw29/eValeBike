package integration4.evalebike.controller.superAdmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/super-admin/companies")
public class CompanyController {
    @GetMapping("/add")
    public String showAddForm() {
        return "superAdmin/add-company";
    }
}
