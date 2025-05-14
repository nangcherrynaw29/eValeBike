package integration4.evalebike.controller.viewModel;

import integration4.evalebike.domain.TestReportEntry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public record TestReportEntryViewModel(LocalDateTime timestamp, double batteryVoltage, double batteryCurrent,
                                       double batteryCapacity, double batteryTemperatureCelsius, int chargeStatus,
                                       int assistanceLevel, double torqueCrankNm, double bikeWheelSpeedKmh,
                                       int cadanceRpm, int engineRpm, double enginePowerWatt, double wheelPowerWatt,
                                       double rollTorque, double loadcellN, double rolHz, double horizontalInclination,
                                       double verticalInclination, double loadPower, boolean statusPlug) {
    public static TestReportEntryViewModel from(TestReportEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("TestReportEntry cannot be null");
        }
        return new TestReportEntryViewModel(entry.getTimestamp(), entry.getBatteryVoltage(), entry.getBatteryCurrent(), entry.getBatteryCapacity(), entry.getBatteryTemperatureCelsius(), entry.getChargeStatus(), entry.getAssistanceLevel(), entry.getTorqueCrankNm(), entry.getBikeWheelSpeedKmh(), entry.getCadanceRpm(), entry.getEngineRpm(), entry.getEnginePowerWatt(), entry.getWheelPowerWatt(), entry.getRollTorque(), entry.getLoadcellN(), entry.getRolHz(), entry.getHorizontalInclination(), entry.getVerticalInclination(), entry.getLoadPower(), entry.isStatusPlug());
    }
    // Helper method to round doubles to 4 decimal places

    static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static TestReportEntryViewModel summarize(List<TestReportEntry> entries) {
        if (entries == null || entries.isEmpty()) {
            throw new IllegalArgumentException("Test report entries cannot be null or empty");
        }

        // Use the latest timestamp
        LocalDateTime timestamp = entries.stream().map(TestReportEntry::getTimestamp).filter(Objects::nonNull).max(LocalDateTime::compareTo).orElse(null);

        // Average and round numeric fields
        double batteryVoltage = round(entries.stream().map(TestReportEntry::getBatteryVoltage).mapToDouble(Double::doubleValue).average().orElse(0.0));

        double batteryCurrent = round(entries.stream().map(TestReportEntry::getBatteryCurrent).mapToDouble(Double::doubleValue).average().orElse(0.0));

        double batteryCapacity = round(entries.stream().map(TestReportEntry::getBatteryCapacity).mapToDouble(Double::doubleValue).average().orElse(0.0));

        double batteryTemperatureCelsius = round(entries.stream().map(TestReportEntry::getBatteryTemperatureCelsius).mapToDouble(Double::doubleValue).average().orElse(0.0));

        int chargeStatus = (int) entries.stream().map(TestReportEntry::getChargeStatus).mapToInt(Integer::intValue).average().orElse(0);

        int assistanceLevel = (int) entries.stream().map(TestReportEntry::getAssistanceLevel).mapToInt(Integer::intValue).average().orElse(0);

        double torqueCrankNm = round(entries.stream().map(TestReportEntry::getTorqueCrankNm).mapToDouble(Double::doubleValue).average().orElse(0.0));

        double bikeWheelSpeedKmh = round(entries.stream().map(TestReportEntry::getBikeWheelSpeedKmh).mapToDouble(Double::doubleValue).average().orElse(0.0));

        int cadanceRpm = (int) entries.stream().map(TestReportEntry::getCadanceRpm).mapToInt(Integer::intValue).average().orElse(0);

        int engineRpm = (int) entries.stream().map(TestReportEntry::getEngineRpm).mapToInt(Integer::intValue).average().orElse(0);

        double enginePowerWatt = round(entries.stream().map(TestReportEntry::getEnginePowerWatt).mapToDouble(Double::doubleValue).average().orElse(0.0));

        double wheelPowerWatt = round(entries.stream().map(TestReportEntry::getWheelPowerWatt).mapToDouble(Double::doubleValue).average().orElse(0.0));

        double rollTorque = round(entries.stream().map(TestReportEntry::getRollTorque).mapToDouble(Double::doubleValue).average().orElse(0.0));

        double loadcellN = round(entries.stream().map(TestReportEntry::getLoadcellN).mapToDouble(Double::doubleValue).average().orElse(0.0));

        double rolHz = round(entries.stream().map(TestReportEntry::getRolHz).mapToDouble(Double::doubleValue).average().orElse(0.0));

        double horizontalInclination = round(entries.stream().map(TestReportEntry::getHorizontalInclination).mapToDouble(Double::doubleValue).average().orElse(0.0));

        double verticalInclination = round(entries.stream().map(TestReportEntry::getVerticalInclination).mapToDouble(Double::doubleValue).average().orElse(0.0));

        double loadPower = round(entries.stream().map(TestReportEntry::getLoadPower).mapToDouble(Double::doubleValue).average().orElse(0.0));

        // Majority vote for statusPlug
        boolean statusPlug = entries.stream().map(TestReportEntry::isStatusPlug).filter(Objects::nonNull).filter(Boolean::booleanValue).count() > entries.size() / 2;

        return new TestReportEntryViewModel(timestamp, batteryVoltage, batteryCurrent, batteryCapacity, batteryTemperatureCelsius, chargeStatus, assistanceLevel, torqueCrankNm, bikeWheelSpeedKmh, cadanceRpm, engineRpm, enginePowerWatt, wheelPowerWatt, rollTorque, loadcellN, rolHz, horizontalInclination, verticalInclination, loadPower, statusPlug);

    }
}