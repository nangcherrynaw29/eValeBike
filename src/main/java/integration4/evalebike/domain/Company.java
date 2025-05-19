package integration4.evalebike.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    private String email;
    private String phone;

    public Company(String companyName, String companyAddress, String companyEmail, String companyPhone) {
        this.name = companyName;
        this.address = companyAddress;
        this.email = companyEmail;
        this.phone = companyPhone;
    }

    public Company() {
    }
}
