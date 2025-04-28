package integration4.evalebike.controller.testBench;

import integration4.evalebike.service.TestBenchService;
import org.springframework.stereotype.Controller;

@Controller
public class TestBenchController {
    private final TestBenchService testBenchService;


    public TestBenchController(TestBenchService testBenchService) {
        this.testBenchService = testBenchService;
    }
}
