package integration4.evalebike.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception{
        return security
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login", "/home").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/superAdmin/**").hasRole("SUPER_ADMIN")
                        .requestMatchers("/technician/**").hasRole("TECHNICIAN")
                        .requestMatchers("/bikeOwner/**").hasRole("BIKE_OWNER")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> {
                    login.loginPage("/login").defaultSuccessUrl("/technician/bike-owners").permitAll();
                })
                .build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
