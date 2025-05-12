package integration4.evalebike.domain;

import jakarta.persistence.*;

import static integration4.evalebike.domain.UserStatus.PENDING;

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
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = PENDING;
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    public User(Integer id, String name, String email, String password, UserStatus userStatus) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userStatus = userStatus;
        this.role = assignRole();
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.role = assignRole();
    }

    public User(String name, String email, UserStatus userStatus) {
        this.name = name;
        this.email = email;
        this.userStatus = userStatus;
    }

    public User(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isActive() {
        return this.userStatus == UserStatus.APPROVED;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
