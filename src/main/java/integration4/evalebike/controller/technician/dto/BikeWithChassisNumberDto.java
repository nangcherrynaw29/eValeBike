package integration4.evalebike.controller.technician.dto;

import integration4.evalebike.domain.BikeOwnerBike;
import integration4.evalebike.domain.BikeSize;

import java.time.LocalDate;

public record BikeWithChassisNumberDto(String qrCodeImage, String brand, String model, String chassisNumber,
                                       int productionYear, BikeSize bikeSize,
                                       int mileage, String gearType, String engineType, String powerTrain,
                                       float accuCapacity, float maxSupport,
                                       float maxEnginePower, float nominalEnginePower, float engineTorque,
                                       LocalDate lastTestDate) {
    public static BikeWithChassisNumberDto fromBikeOwnerBike(final BikeOwnerBike bikeOwnerBike) {
        return new BikeWithChassisNumberDto(bikeOwnerBike.getQrImage(), bikeOwnerBike.getBike().getBrand(), bikeOwnerBike.getBike().getModel(),
                bikeOwnerBike.getChassisNumber(), bikeOwnerBike.getBike().getProductionYear(), bikeOwnerBike.getBike().getBikeSize(),
                bikeOwnerBike.getBike().getMileage(), bikeOwnerBike.getBike().getGearType(), bikeOwnerBike.getBike().getEngineType(),
                bikeOwnerBike.getBike().getPowerTrain(), bikeOwnerBike.getBike().getAccuCapacity(), bikeOwnerBike.getBike().getMaxSupport(),
                bikeOwnerBike.getBike().getMaxEnginePower(), bikeOwnerBike.getBike().getNominalEnginePower(), bikeOwnerBike.getBike().getEngineTorque(),
                bikeOwnerBike.getBike().getLastTestDate());
    }
}