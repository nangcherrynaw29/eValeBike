package integration4.evalebike.security;

import integration4.evalebike.exception.AccountRejectedException;
import integration4.evalebike.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .map(user -> {
                    if (!user.isActive()) {
                        throw new AccountRejectedException("Your account is " + user.getUserStatus() + ". Please contact admin.");
                    }
                    return new CustomUserDetails(
                            user.getEmail(),
                            user.getPassword(),
                            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())),
                            user.getId(),
                            user.getRole(),
                            user.getCompany()
                    );
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}