package integration4.evalebike.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "bike_owner_bike")
public class BikeOwnerBike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Many BikeOwnerBike entries can belong to one BikeOwner
    @ManyToOne
    @JoinColumn(name = "bike_owner_id", nullable = false)
    private BikeOwner bikeOwner;

    // Many BikeOwnerBike entries can refer to one Bike
    @ManyToOne
    @JoinColumn(name = "bike_qr", referencedColumnName = "bikeqr", nullable = false)
    private Bike bike;

    // Chassis number is stored in this intermediate table
//    @Column(nullable = false, unique = true)
//    private String chassisNumber;

    @Transient
    private String qrImage;

    public BikeOwnerBike() {
    }

    public BikeOwnerBike(Bike bike, BikeOwner bikeOwner) {
        this.bike = bike;
        this.bikeOwner = bikeOwner;

    }



    public Bike getBike() {
        return bike;
    }

    public BikeOwner getBikeOwner() {
        return bikeOwner;
    }



    public Integer getId() {
        return id;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }


    public void setBikeOwner(BikeOwner bikeOwner) {
        this.bikeOwner = bikeOwner;
    }

    public String getQrImage() {
        return qrImage;
    }

    public void setQrImage(String qrImage) {
        this.qrImage = qrImage;
    }
}