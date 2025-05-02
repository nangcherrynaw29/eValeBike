package integration4.evalebike.controller.technician.dto;

import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.TestReport;
import integration4.evalebike.domain.TestReportEntry;

import java.util.List;

//public class TestReportDTO(
//        String id,
//        String expiryDate,
//        String state,
//        String type,
//        double batteryCapacity,
//        double maxSupport,
//        double enginePowerMax,
//        double enginePowerNominal,
//        double engineTorque,
//        List<TestReportEntryDTO> reportEntries,
//        String bikeQR,
//        String technicianName
//) {}


import java.util.List;

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

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(double batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getBikeQR() {
        return bikeQR;
    }

    public void setBikeQR(String bikeQR) {
        this.bikeQR = bikeQR;
    }

    public double getEnginePowerMax() {
        return enginePowerMax;
    }

    public void setEnginePowerMax(double enginePowerMax) {
        this.enginePowerMax = enginePowerMax;
    }

    public double getEnginePowerNominal() {
        return enginePowerNominal;
    }

    public void setEnginePowerNominal(double enginePowerNominal) {
        this.enginePowerNominal = enginePowerNominal;
    }

    public double getEngineTorque() {
        return engineTorque;
    }

    public void setEngineTorque(double engineTorque) {
        this.engineTorque = engineTorque;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMaxSupport() {
        return maxSupport;
    }

    public void setMaxSupport(double maxSupport) {
        this.maxSupport = maxSupport;
    }

    public List<TestReportEntryDTO> getReportEntries() {
        return reportEntries;
    }

    public void setReportEntries(List<TestReportEntryDTO> reportEntries) {
        this.reportEntries = reportEntries;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}



//We do summarize for all the test entries in the service,which will result one report entry.
