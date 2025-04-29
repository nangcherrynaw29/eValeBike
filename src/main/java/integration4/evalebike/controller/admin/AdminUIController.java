package integration4.evalebike.controller.admin;


import integration4.evalebike.controller.viewModel.TechniciansViewModel;
import integration4.evalebike.service.TechnicianService;
import integration4.evalebike.service.TestBenchService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/technicians")
public class AdminUIController {
    private final TechnicianService technicianService;
    private final TestBenchService testBenchService;

    public AdminUIController(TechnicianService technicianService, TestBenchService testBenchService) {
        this.technicianService = technicianService;
        this.testBenchService = testBenchService;
    }

    @GetMapping()
    @Transactional(readOnly = true)
    public ModelAndView index() {
        final ModelAndView modelAndView = new ModelAndView("admin/admin-dashboard");
        modelAndView.addObject("technicians", TechniciansViewModel.fromTechnician(technicianService.getAll()));
        modelAndView.addObject("testBenches", testBenchService.getAllTestBenches());
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView add() {
        return new ModelAndView("admin/add-technician");
    }
}
