package integration4.evalebike.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
//not using this
@Entity
@Table(name="report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer reportId;
    private String reportName;
    private String bikeQR;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_result_id", referencedColumnName = "id", nullable = false)
    private TestResult testResult;
    private LocalDate date;

    public Report(Integer reportId, String reportName, String bikeQR, TestResult testResult, LocalDate date) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.bikeQR = bikeQR;
        this.testResult = testResult;
        this.date = date;
    }

    public Report() {

    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getBikeQR() {
        return bikeQR;
    }

    public void setBikeQR(String bikeQR) {
        this.bikeQR = bikeQR;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
