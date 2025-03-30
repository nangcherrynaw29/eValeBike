package integration4.evalebike.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.time.LocalDate;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class BikeOwner extends User {
    private String phoneNumber;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "bikeOwner", fetch = FetchType.LAZY)
    private List<BikeOwnerBike> bikeList;

    public BikeOwner(Integer id, String name, String email, String password, String phoneNumber, LocalDate birthDate) {
        super(id, name, email, password);
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public BikeOwner(String name, String email, String phoneNumber, LocalDate birthDate) {
        super(name, email);
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public BikeOwner() {
    }

    public List<BikeOwnerBike> getBikeList() {
        return bikeList;
    }

    public void setBikeList(List<BikeOwnerBike> bikeList) {
        this.bikeList = bikeList;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
