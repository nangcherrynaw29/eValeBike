package integration4.evalebike.controller;

import integration4.evalebike.controller.testBench.dto.TestReportDTO;
import integration4.evalebike.service.TestBenchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/technician")
public class TestResultController {

    private final TestBenchService testBenchService;

    public TestResultController(TestBenchService testBenchService) {
        this.testBenchService = testBenchService;
    }

    @GetMapping("/report")
    public Mono<String> showReport(@RequestParam("testId") String testId, Model model) {
        return testBenchService.getTestReportById(testId)
                .map(report -> {
                    model.addAttribute("report", report);
                    return "technician/report";
                })
                .onErrorResume(e -> {
                    model.addAttribute("error", e.getMessage());
                    return Mono.just("technician/bike-dashboard");
                });
    }
}