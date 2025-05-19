package integration4.evalebike.security;

import java.util.Collection;

import integration4.evalebike.domain.Company;
import integration4.evalebike.domain.Role;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;


public class CustomUserDetails extends User {
    private final int userId;
    private transient final Role role;
    private transient final Company company;

    public CustomUserDetails(final String username, final String password, final Collection<? extends GrantedAuthority> authorities, final int userId, final Role role, final Company company) {
        super(username, password, authorities);
        this.userId = userId;
        this.role = role;
        this.company = company;
    }

    public int getUserId() {
        return userId;
    }
    public Role getRole() {return role;}
    public Company getCompany() {return company;}
}


