package integration4.evalebike.domain;

import java.time.LocalDate;

public class TestBench {
    private Integer testBenchId;
    private String testBenchName;
    private String location;
    private Status status;
    private LocalDate lastCalibrationDate;

    public TestBench(Integer testBenchId, String testBenchName, String location, Status status, LocalDate lastCalibrationDate) {
        this.testBenchId = testBenchId;
        this.testBenchName = testBenchName;
        this.location = location;
        this.status = status;
        this.lastCalibrationDate = lastCalibrationDate;
    }

    public Integer getTestBenchId() {
        return testBenchId;
    }

    public void setTestBenchId(Integer testBenchId) {
        this.testBenchId = testBenchId;
    }

    public String getTestBenchName() {
        return testBenchName;
    }

    public void setTestBenchName(String testBenchName) {
        this.testBenchName = testBenchName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getLastCalibrationDate() {
        return lastCalibrationDate;
    }

    public void setLastCalibrationDate(LocalDate lastCalibrationDate) {
        this.lastCalibrationDate = lastCalibrationDate;
    }
}
