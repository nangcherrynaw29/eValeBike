package integration4.evalebike.service;

import integration4.evalebike.controller.viewModel.ReportViewModel;
import integration4.evalebike.domain.TestReport;
import integration4.evalebike.repository.TestReportEntryRepository;
import integration4.evalebike.repository.TestReportRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
public class TestReportService {
    private final TestReportRepository testReportRepository;
    private final TestReportEntryRepository repoTestReportEntry;

    @Autowired
    private JavaMailSender mailSender;

    public TestReportService(TestReportRepository testReportRepository, TestReportEntryRepository repoTestReportEntry) {
        this.testReportRepository = testReportRepository;
        this.repoTestReportEntry = repoTestReportEntry;
    }

    public List<TestReport> getAllReports() {
        return testReportRepository.findAllWithBikeAndReportEntries();
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

    public List<ReportViewModel> getTestReportsByBikeQR(String bikeQr) {
        return testReportRepository.findTestReportsByBikeQrWithBikeAndEntries(bikeQr)
                .stream()
                .map(ReportViewModel::from)
                .toList();
    }

    public void sendTestReportEmail(String reportId) throws Exception {
        TestReport report = testReportRepository.findById(reportId)
                .orElseThrow(() -> new Exception("Report not found"));

        // Get bike owner's email
        String email = report.getBike()
                .getBikeList()
                .stream()
                .findFirst()
                .map(bikeOwnerBike -> bikeOwnerBike.getBikeOwner().getEmail())
                .orElseThrow(() -> new Exception("Bike owner email not found"));

        // Read the uploaded PDF from disk
        Path pdfPath = Paths.get("uploads", reportId + ".pdf");
        if (!Files.exists(pdfPath)) {
            throw new Exception("PDF file not found for report: " + reportId);
        }
        byte[] pdfBytes = Files.readAllBytes(pdfPath);

        // Prepare and send email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("Your Test Report");
        helper.setText("Please find attached the test report for your bike.");
        helper.addAttachment("TestReport.pdf", new ByteArrayResource(pdfBytes));

        mailSender.send(message);
    }

    public long getTotalTestCount() {
        return testReportRepository.count();
    }

    public long getCompletedTestCount() {
        return testReportRepository.countByStateIgnoreCase("completed");
    }

    public long getIncompleteTestCount() {
        return testReportRepository.count() - testReportRepository.countByStateIgnoreCase("completed");
    }
}