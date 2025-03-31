package integration4.evalebike.controller.testBench;


import integration4.evalebike.controller.testBench.dto.TestReportDTO;
import integration4.evalebike.controller.testBench.dto.TestRequestDTO;
import integration4.evalebike.controller.testBench.dto.TestResponseDTO;
import integration4.evalebike.service.TestBenchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/api/test")
public class TestBenchApiController {
    private final TestBenchService testBenchService;

    public TestBenchApiController(TestBenchService testBenchService) {
        this.testBenchService = testBenchService;
    }

    @PostMapping("/start")
    public ResponseEntity<TestResponseDTO> startTest(
            @RequestBody TestRequestDTO requestDTO, Principal principa) {
        String username = principa.getName();
        return ResponseEntity.ok(testBenchService.startTest(requestDTO,username).block());

    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResponseDTO> getTestResult(@PathVariable String id) {
        TestResponseDTO result = testBenchService.getTestResultById(id).block();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/report")
    public Mono<ResponseEntity<TestReportDTO>> getTestReport(@PathVariable String id) {
        return testBenchService.getTestReportById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
