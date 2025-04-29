package integration4.evalebike.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "test_bench")
public class TestBench {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer testBenchId;
    @Column(name = "test_bench_name", unique = true)
    private String testBenchName;
    private String location;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate lastCalibrationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id")
    private Technician technician;

    public TestBench(LocalDate lastCalibrationDate, String location, Status status, Technician technician, Integer testBenchId, String testBenchName) {
        this.lastCalibrationDate = lastCalibrationDate;
        this.location = location;
        this.status = status;
        this.technician = technician;
        this.testBenchId = testBenchId;
        this.testBenchName = testBenchName;
    }

    public TestBench() {

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

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public LocalDate getLastCalibrationDate() {
        return lastCalibrationDate;
    }

    public void setLastCalibrationDate(LocalDate lastCalibrationDate) {
        this.lastCalibrationDate = lastCalibrationDate;
    }
}
