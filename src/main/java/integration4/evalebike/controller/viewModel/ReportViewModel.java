package integration4.evalebike.controller.viewModel;

import integration4.evalebike.domain.TestReport;
import integration4.evalebike.domain.TestReportEntry;

import java.util.List;

public record ReportViewModel(String id, String expiryDate, String state, String type, double batteryCapacity,
                              double maxSupport, double enginePowerMax, double enginePowerNominal, double engineTorque,
                              String bikeQR, String technicianName, List<TestReportEntry> testReportEntries) {

    public static ReportViewModel from(final TestReport report) {
        return new ReportViewModel(report.getId(), report.getExpiryDate(), report.getState(), report.getType(), report.getBatteryCapacity(), report.getMaxSupport(), report.getEnginePowerMax(), report.getEnginePowerNominal(), report.getEngineTorque(), report.getBike() != null ? report.getBike().getBikeQR() : null, // Access bikeQR via Bike
                report.getTechnicianName(),report.getReportEntries());
    }
}