package integration4.evalebike.controller.technician.dto;

import integration4.evalebike.domain.Bike;
import integration4.evalebike.domain.BikeSize;

import java.time.LocalDate;

public record BikeDto(String bikeQR,String qrCodeImage, String brand, String model,String chassisNumber, int productionYear, BikeSize bikeSize,
                      int mileage, String gearType, String engineType, String powerTrain, double accuCapacity,
                      double maxSupport, double maxEnginePower, double nominalEnginePower, double engineTorque, LocalDate lastTestDate) {
    public static BikeDto fromBike(final Bike bike) {
        return new BikeDto(bike.getBikeQR(),bike.getQrCodeImage(), bike.getBrand(), bike.getModel(), bike.getChassisNumber(),bike.getProductionYear(), bike.getBikeSize(),
                bike.getMileage(), bike.getGearType(), bike.getEngineType(), bike.getPowerTrain(), bike.getAccuCapacity(),
                bike.getMaxSupport(), bike.getMaxEnginePower(), bike.getNominalEnginePower(), bike.getEngineTorque(), bike.getLastTestDate());
    }
}
