package integration4.evalebike.service;

import integration4.evalebike.domain.TestReport;
import integration4.evalebike.repository.TestReportEntryRepository;
import integration4.evalebike.repository.TestReportRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TestReportService {
    private final TestReportRepository testReportRepository;
    private final TestReportEntryRepository repoTestReportEntry;

    public TestReportService(TestReportRepository testReportRepository, TestReportEntryRepository repoTestReportEntry) {
        this.testReportRepository = testReportRepository;
        this.repoTestReportEntry = repoTestReportEntry;
    }

    public List<TestReport> getAllReports() {
        return testReportRepository.findAllWithBike();
    }


    public TestReport getTestReportWithEntriesById(String testId) {
        var testReport = testReportRepository.findEntriesByID(testId);
        if (testReport.isEmpty()) {
            throw new RuntimeException("No TestReport found for testId: " + testId);
        }
        return testReport.get();
    }

    public void saveTestReport(TestReport testReport) {
        testReportRepository.save(testReport);
    }









}





