package integration4.evalebike.controller.Technician.dto;

import integration4.evalebike.domain.BikeSize;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AddBikeDto(String brand, String model, String chassisNumber, @Positive int productionYear, BikeSize bikeSize,
                         @Positive int mileage, String gearType, String engineType, String powerTrain, float accuCapacity, float maxSupport,
                         float maxEnginePower, float nominalEnginePower, float engineTorque, @PastOrPresent LocalDate lastTestDate) {
}
