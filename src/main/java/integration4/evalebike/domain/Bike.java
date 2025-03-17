package integration4.evalebike.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "bike")
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bikeQR;
    private String brand;
    private String model;
    private String chassisNumber;
    private int productionYear;
    @Enumerated(EnumType.STRING)
    private BikeSize bikeSize;
    private int mileage;
    private String gearType;
    private String engineType;
    private String powerTrain;
    private float accuCapacity;
    private float maxSupport;
    private float maxEnginePower;
    private float nominalEnginePower;
    private float engineTorque;
    private LocalDate lastTestDate;

    public Bike(String bikeQR, String brand, String model, String chassisNumber, int productionYear, BikeSize bikeSize, int mileage, String gearType, String engineType, String powerTrain, float accuCapacity, float maxSupport, float maxEnginePower, float nominalEnginePower, float engineTorque, LocalDate lastTestDate) {
        this.bikeQR = bikeQR;
        this.brand = brand;
        this.model = model;
        this.chassisNumber = chassisNumber;
        this.productionYear = productionYear;
        this.bikeSize = bikeSize;
        this.mileage = mileage;
        this.gearType = gearType;
        this.engineType = engineType;
        this.powerTrain = powerTrain;
        this.accuCapacity = accuCapacity;
        this.maxSupport = maxSupport;
        this.maxEnginePower = maxEnginePower;
        this.nominalEnginePower = nominalEnginePower;
        this.engineTorque = engineTorque;
        this.lastTestDate = lastTestDate;
    }

    public Bike() {

    }

    public Bike(String brand, String model, String chassisNumber, int productionYear, BikeSize bikeSize, int mileage, String gearType, String engineType, String powerTrain, float accuCapacity, float maxSupport, float maxEnginePower, float nominalEnginePower, float engineTorque, LocalDate lastTestDate) {
        this.brand = brand;
        this.model = model;
        this.chassisNumber = chassisNumber;
        this.productionYear = productionYear;
        this.bikeSize = bikeSize;
        this.mileage = mileage;
        this.gearType = gearType;
        this.engineType = engineType;
        this.powerTrain = powerTrain;
        this.accuCapacity = accuCapacity;
        this.maxSupport = maxSupport;
        this.maxEnginePower = maxEnginePower;
        this.nominalEnginePower = nominalEnginePower;
        this.engineTorque = engineTorque;
        this.lastTestDate = lastTestDate;
    }

    public String getBikeQR() {
        return bikeQR;
    }

    public void setBikeQR(String bikeQR) {
        this.bikeQR = bikeQR;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String motorType) {
        this.engineType = motorType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getLastTestDate() {
        return lastTestDate;
    }

    public void setLastTestDate(LocalDate lastTestDate) {
        this.lastTestDate = lastTestDate;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public BikeSize getBikeSize() {
        return bikeSize;
    }

    public void setBikeSize(BikeSize bikeSize) {
        this.bikeSize = bikeSize;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getGearType() {
        return gearType;
    }

    public void setGearType(String gearType) {
        this.gearType = gearType;
    }

    public String getPowerTrain() {
        return powerTrain;
    }

    public void setPowerTrain(String powerTrain) {
        this.powerTrain = powerTrain;
    }

    public float getAccuCapacity() {
        return accuCapacity;
    }

    public void setAccuCapacity(float accuCapacity) {
        this.accuCapacity = accuCapacity;
    }

    public float getMaxSupport() {
        return maxSupport;
    }

    public void setMaxSupport(float maxSupport) {
        this.maxSupport = maxSupport;
    }

    public float getMaxEnginePower() {
        return maxEnginePower;
    }

    public void setMaxEnginePower(float maxEnginePower) {
        this.maxEnginePower = maxEnginePower;
    }

    public float getNominalEnginePower() {
        return nominalEnginePower;
    }

    public void setNominalEnginePower(float nominalEnginePower) {
        this.nominalEnginePower = nominalEnginePower;
    }

    public float getEngineTorque() {
        return engineTorque;
    }

    public void setEngineTorque(float engineTorque) {
        this.engineTorque = engineTorque;
    }
}
