package integration4.evalebike.domain;

import java.time.LocalDate;
import java.util.List;

public class BikeOwner extends User {
    private String phoneNumber;
    private LocalDate birthDate;
    private List<Bike> bikes;

    public BikeOwner(Integer id, String name, String email, String password, String phoneNumber, LocalDate birthDate, List<Bike> bikes) {
        super(id, name, email, password);
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.bikes = bikes;
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

    public List<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(List<Bike> bikes) {
        this.bikes = bikes;
    }
}
