package integration4.evalebike.service;

import integration4.evalebike.repository.TestBenchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestBenchService {
    private final TestBenchRepository testBenchRepository;

    @Autowired
    public TestBenchService(TestBenchRepository testBenchRepository) {
        this.testBenchRepository = testBenchRepository;
    }
}
