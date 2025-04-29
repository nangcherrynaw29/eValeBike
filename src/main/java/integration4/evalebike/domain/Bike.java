package integration4.evalebike.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "bike")
public class Bike {
    @Id
    @Column(name = "bikeqr", nullable = false, unique = true, updatable = false)
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
    private double accuCapacity;
    private double maxSupport;
    private double maxEnginePower;
    private double nominalEnginePower;
    private double engineTorque;
    private LocalDate lastTestDate;
    @Transient
    private String qrCodeImage;

    @OneToMany(mappedBy = "bike", fetch = FetchType.LAZY)
    private List<BikeOwnerBike> bikeList = new ArrayList<>();

    public Bike() {

    }

    public Bike(double accuCapacity, List<BikeOwnerBike> bikeList, String bikeQR, BikeSize bikeSize, String brand, String chassisNumber, double engineTorque, String engineType, String gearType, LocalDate lastTestDate, double maxEnginePower, double maxSupport, int mileage, String model, double nominalEnginePower, String powerTrain, int productionYear, String qrCodeImage) {
        this.accuCapacity = accuCapacity;
        this.bikeList = bikeList;
        this.bikeQR = bikeQR;
        this.bikeSize = bikeSize;
        this.brand = brand;
        this.chassisNumber = chassisNumber;
        this.engineTorque = engineTorque;
        this.engineType = engineType;
        this.gearType = gearType;
        this.lastTestDate = lastTestDate;
        this.maxEnginePower = maxEnginePower;
        this.maxSupport = maxSupport;
        this.mileage = mileage;
        this.model = model;
        this.nominalEnginePower = nominalEnginePower;
        this.powerTrain = powerTrain;
        this.productionYear = productionYear;
        this.qrCodeImage = qrCodeImage;
    }

    public Bike(String brand, String model, String chassisNumber, int productionYear, BikeSize bikeSize, int mileage, String gearType, String engineType,
                String powerTrain,
                double accuCapacity,
                double maxSupport,
                double maxEnginePower,
                double nominalEnginePower,
                double engineTorque,
                LocalDate lastTestDate) {
    }

    @PrePersist
    public void generateQR() {
        if (this.bikeQR == null) {
            this.bikeQR = UUID.randomUUID().toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return bikeQR.equals(bike.bikeQR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bikeQR);
    }

    public List<BikeOwnerBike> getBikeList() {
        return bikeList;
    }

    public void setBikeList(List<BikeOwnerBike> bikeList) {
        this.bikeList = bikeList;
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

    public double getAccuCapacity() {
        return accuCapacity;
    }

    public void setAccuCapacity(float accuCapacity) {
        this.accuCapacity = accuCapacity;
    }

    public double getMaxSupport() {
        return maxSupport;
    }

    public void setMaxSupport(float maxSupport) {
        this.maxSupport = maxSupport;
    }

    public double getMaxEnginePower() {
        return maxEnginePower;
    }

    public void setMaxEnginePower(float maxEnginePower) {
        this.maxEnginePower = maxEnginePower;
    }

    public double getNominalEnginePower() {
        return nominalEnginePower;
    }

    public void setNominalEnginePower(float nominalEnginePower) {
        this.nominalEnginePower = nominalEnginePower;
    }

    public double getEngineTorque() {
        return engineTorque;
    }

    public void setEngineTorque(float engineTorque) {
        this.engineTorque = engineTorque;
    }

    public String getQrCodeImage() {
        return qrCodeImage;
    }

    public void setQrCodeImage(String qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "bikeQR='" + bikeQR + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }
}