package integration4.evalebike.controller.technician.dto;

import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeSize;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record BikeDto(
        @NotBlank(message = "Bike QR cannot be blank")
        String bikeQR,
        String qrCodeImage,
        @NotBlank(message = "Brand cannot be blank")
        String brand,
        @NotBlank(message = "Model cannot be blank")
        String model,
        @NotBlank(message = "Chassis number cannot be blank")
        String chassisNumber,
        @Min(value = 1900, message = "Production year must be at least 1900")
        @Max(value = 2100, message = "Production year cannot exceed 2100")
        int productionYear,
        @NotNull(message = "Bike size cannot be null")
        BikeSize bikeSize,
        @Min(value = 0, message = "Mileage cannot be negative")
        int mileage,
        String gearType,
        String engineType,
        String powerTrain,
        @NotNull(message = "Battery capacity is required")
        @DecimalMin(value = "0.01", message = "Battery capacity must be at least 0.01")
        @DecimalMax(value = "1000.00", message = "Battery capacity cannot exceed 1000.00")
        double accuCapacity,
        @NotNull(message = "Max support is required")
        @DecimalMin(value = "0.00", message = "Max support must be at least 0.00")
        @DecimalMax(value = "500.00", message = "Max support cannot exceed 500.00")
        double maxSupport,
        @NotNull(message = "Max engine power is required")
        @DecimalMin(value = "0.10", message = "Max engine power must be at least 0.10")
        @DecimalMax(value = "200.00", message = "Max engine power cannot exceed 200.00")
        double maxEnginePower,
        @NotNull(message = "Nominal engine power is required")
        @DecimalMin(value = "0.10", message = "Nominal engine power must be at least 0.10")
        @DecimalMax(value = "150.00", message = "Nominal engine power cannot exceed 150.00")
        double nominalEnginePower,
        @NotNull(message = "Engine torque is required")
        @Min(value = 1, message = "Engine torque must be at least 1")
        @Max(value = 150, message = "Engine torque cannot exceed 150")
        double engineTorque,
        LocalDate lastTestDate
) {
    public static BikeDto fromBike(final Bike bike) {
        return new BikeDto(bike.getBikeQR(), bike.getQrCodeImage(), bike.getBrand(), bike.getModel(), bike.getChassisNumber(), bike.getProductionYear(), bike.getBikeSize(),
                bike.getMileage(), bike.getGearType(), bike.getEngineType(), bike.getPowerTrain(), bike.getAccuCapacity(),
                bike.getMaxSupport(), bike.getMaxEnginePower(), bike.getNominalEnginePower(), bike.getEngineTorque(), bike.getLastTestDate());
    }
}