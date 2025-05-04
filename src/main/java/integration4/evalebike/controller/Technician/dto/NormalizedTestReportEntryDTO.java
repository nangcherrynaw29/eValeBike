package integration4.evalebike.controller.technician.dto;

import java.time.LocalDateTime;

public record NormalizedTestReportEntryDTO(
        LocalDateTime timestamp,
        double batteryVoltage,
        double batteryCurrent,
        double batteryCapacity,
        double batteryTemperatureCelsius,
        double chargeStatus,
        double assistanceLevel,
        double torqueCrankNm,
        double bikeWheelSpeedKmh,
        double cadanceRpm,
        double engineRpm,
        double enginePowerWatt,
        double wheelPowerWatt,
        double rollTorque,
        double loadcellN,
        double rolHz,
        double horizontalInclination,
        double verticalInclination,
        double loadPower,
        double statusPlug
) {}
