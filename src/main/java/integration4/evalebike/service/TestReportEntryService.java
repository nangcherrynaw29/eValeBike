package integration4.evalebike.service;

import integration4.evalebike.domain.TestReportEntry;
import integration4.evalebike.repository.TestReportEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestReportEntryService {
    private final TestReportEntryRepository testReportEntryRepository;

    public TestReportEntryService(TestReportEntryRepository testReportEntryRepository) {
        this.testReportEntryRepository = testReportEntryRepository;
    }

    public List<TestReportEntry> getEntriesByReportId(String reportId) {
        return testReportEntryRepository.findTestReportEntriesByTestReportId(reportId);
    }


}
