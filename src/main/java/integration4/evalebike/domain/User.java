package integration4.evalebike.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String companyName;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = assignRole();
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.role = assignRole();
    }

    private Role assignRole() {
        if (this instanceof BikeOwner) {
            return Role.BIKE_OWNER;
        } else if (this instanceof Technician) {
            return Role.TECHNICIAN;
        } else if (this instanceof Administrator) {
            return Role.ADMIN;
        } else if (this instanceof SuperAdmin) {
            return Role.SUPER_ADMIN;
        } else {
            return null;
        }
    }

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
