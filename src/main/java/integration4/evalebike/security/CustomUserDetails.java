package integration4.evalebike.security;

import java.util.Collection;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;


public class CustomUserDetails extends User {
    private final int userId;

    public CustomUserDetails(final String username, final String password, final Collection<? extends GrantedAuthority> authorities, final int userId) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}


