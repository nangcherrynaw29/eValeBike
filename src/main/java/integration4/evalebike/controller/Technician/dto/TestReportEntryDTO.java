package integration4.evalebike.controller.technician.dto;

import java.time.LocalDateTime;

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
) {}
