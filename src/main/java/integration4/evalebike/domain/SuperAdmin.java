package integration4.evalebike.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class SuperAdmin extends User {

    public SuperAdmin(Integer id, String name, String email, String password) {
        super(id, name, email, password);
    }

    public SuperAdmin() {

    }



}
