package integration4.evalebike.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Administrator extends User {
    private String companyName;

    public Administrator(String name, String email, String companyName) {
        super(name, email);
        this.companyName = companyName;
    }

    public Administrator() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}