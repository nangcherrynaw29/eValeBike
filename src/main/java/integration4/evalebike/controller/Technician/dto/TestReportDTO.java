package integration4.evalebike.controller.technician.dto;

import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.TestReport;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TestReportDTO {

    private String id;
    private String expiryDate;
    private String state;
    private String type;
    private double batteryCapacity;
    private double maxSupport;
    private double enginePowerMax;
    private double enginePowerNominal;
    private double engineTorque;
    private List<TestReportEntryDTO> reportEntries;
    private String bikeQR;
    private String technicianName;

    // Constructor
    public TestReportDTO(String id, String expiryDate, String state, String type,
                         double batteryCapacity, double maxSupport, double enginePowerMax,
                         double enginePowerNominal, double engineTorque,
                         List<TestReportEntryDTO> reportEntries, String bikeQR, String technicianName) {
        this.id = id;
        this.expiryDate = expiryDate;
        this.state = state;
        this.type = type;
        this.batteryCapacity = batteryCapacity;
        this.maxSupport = maxSupport;
        this.enginePowerMax = enginePowerMax;
        this.enginePowerNominal = enginePowerNominal;
        this.engineTorque = engineTorque;
        this.reportEntries = reportEntries;
        this.bikeQR = bikeQR;
        this.technicianName = technicianName;
    }


    public static TestReportDTO convertToTestReportDTO(TestReportDTO testReportDTO, List<TestReportEntryDTO> entries, String bikeQR) {
        return new TestReportDTO(
                testReportDTO.getId(),
                testReportDTO.getExpiryDate(),
                testReportDTO.getState(),
                testReportDTO.getType(),
                testReportDTO.getBatteryCapacity(),
                testReportDTO.getMaxSupport(),
                testReportDTO.getEnginePowerMax(),
                testReportDTO.getEnginePowerNominal(),
                testReportDTO.getEngineTorque(),
                entries,
                bikeQR,
                testReportDTO.getTechnicianName()
        );
    }

    public static TestReport convertToTestReportWithNoEntries(TestResponseDTO testResponseDTO, Bike bike, String technicianUsername) {
        TestReport testReport = new TestReport(
                testResponseDTO.getId(),
                testResponseDTO.getExpiryDate(),
                testResponseDTO.getState(),
                testResponseDTO.getType(),
                testResponseDTO.getBatteryCapacity(),
                testResponseDTO.getMaxSupport(),
                testResponseDTO.getEnginePowerMax(),
                testResponseDTO.getEnginePowerNominal(),
                testResponseDTO.getEngineTorque()
        );
        testReport.setBike(bike);
        testReport.setTechnicianName(technicianUsername);
        return testReport;
    }
}

//We do summarize for all the test entries in the service,which will result one report entry.
