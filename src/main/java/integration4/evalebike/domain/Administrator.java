package integration4.evalebike.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Administrator extends User {
    private String companyName;

    public Administrator(Integer id, String name, String email, String password, String companyName) {
        super(id, name, email, password);
        this.companyName = companyName;
    }

    public Administrator() {}

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
