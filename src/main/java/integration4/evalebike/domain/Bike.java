package integration4.evalebike.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "bike", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TestReport> testReports = new ArrayList<>();


    public Bike() {

    }

    public Bike(double accuCapacity, List<BikeOwnerBike> bikeList, String bikeQR, BikeSize bikeSize,
                String brand, String chassisNumber, double engineTorque, String engineType,
                String gearType, LocalDate lastTestDate, double maxEnginePower, double maxSupport,
                int mileage, String model, double nominalEnginePower, String powerTrain, int productionYear, String qrCodeImage) {
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

    public Bike(String brand, String model, String chassisNumber,
                int productionYear, BikeSize bikeSize, int mileage, String gearType, String engineType,
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



    @Override
    public String toString() {
        return "Bike{" +
                "bikeQR='" + bikeQR + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }


}