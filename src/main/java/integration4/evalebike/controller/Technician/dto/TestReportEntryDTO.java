package integration4.evalebike.controller.technician.dto;

import integration4.evalebike.domain.TestReport;
import integration4.evalebike.domain.TestReportEntry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record TestReportEntryDTO(
        LocalDateTime timestamp,
        double batteryVoltage,
        double batteryCurrent,
        double batteryCapacity,
        double batteryTemperatureCelsius,
        int chargeStatus,
        int assistanceLevel,
        double torqueCrankNm,
        double bikeWheelSpeedKmh,
        int cadanceRpm,
        int engineRpm,
        double enginePowerWatt,
        double wheelPowerWatt,
        double rollTorque,
        double loadcellN,
        double rolHz,
        double horizontalInclination,
        double verticalInclination,
        double loadPower,
        boolean statusPlug
) {
    public static List<TestReportEntry> convertToTestReportEntries(List<TestReportEntryDTO> entryDTOs, TestReport testReport) {
        return entryDTOs.stream()
                .map(dto -> new TestReportEntry(
                        testReport,
                        dto.timestamp(),
                        dto.batteryVoltage(),
                        dto.batteryCurrent(),
                        dto.batteryCapacity(),
                        dto.batteryTemperatureCelsius(),
                        dto.chargeStatus(),
                        dto.assistanceLevel(),
                        dto.torqueCrankNm(),
                        dto.bikeWheelSpeedKmh(),
                        dto.cadanceRpm(),
                        dto.engineRpm(),
                        dto.enginePowerWatt(),
                        dto.wheelPowerWatt(),
                        dto.rollTorque(),
                        dto.loadcellN(),
                        dto.rolHz(),
                        dto.horizontalInclination(),
                        dto.verticalInclination(),
                        dto.loadPower(),
                        dto.statusPlug()
                ))
                .collect(Collectors.toList());
    }

}
