package integration4.evalebike.controller.technician.dto;

import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeSize;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record BikeDto(
        String bikeQR,
        String qrCodeImage,
        String brand,
        String model,
        String chassisNumber,
        Integer productionYear,
        BikeSize bikeSize,
        Integer mileage,
        String gearType,
        String engineType,
        String powerTrain,
        @NotNull(message = "Battery capacity is required")
        @DecimalMin(value = "0.01", message = "Battery capacity must be at least 0.01")
        @DecimalMax(value = "2000", message = "Battery capacity cannot exceed 2000.00")
        double accuCapacity,
        @NotNull(message = "Max support is required")
        @DecimalMin(value = "0.01", message = "Max support must be at least 0.01")
        @DecimalMax(value = "2000", message = "Max support cannot exceed 2000")
        double maxSupport,
        @NotNull(message = "Max engine power is required")
        @DecimalMin(value = "0.01", message = "Max engine power must be at least 0.01")
        @DecimalMax(value = "2000", message = "Max engine power cannot exceed 2000")
        double maxEnginePower,
        @NotNull(message = "Nominal engine power is required")
        @DecimalMin(value = "0.01", message = "Nominal engine power must be at least 0.01")
        @DecimalMax(value = "2000", message = "Nominal engine power cannot exceed 2000")
        double nominalEnginePower,
        @NotNull(message = "Engine torque is required")
        @DecimalMin(value = "0.01", message = "Engine torque must be at least 1")
        @DecimalMax(value = "2000", message = "Engine torque cannot exceed 2000")
        double engineTorque,
        LocalDate lastTestDate
) {
    public static BikeDto fromBike(final Bike bike) {
        return new BikeDto(bike.getBikeQR(), bike.getQrCodeImage(), bike.getBrand(), bike.getModel(), bike.getChassisNumber(), bike.getProductionYear(), bike.getBikeSize(),
                bike.getMileage(), bike.getGearType(), bike.getEngineType(), bike.getPowerTrain(), bike.getAccuCapacity(),
                bike.getMaxSupport(), bike.getMaxEnginePower(), bike.getNominalEnginePower(), bike.getEngineTorque(), bike.getLastTestDate());
    }



}