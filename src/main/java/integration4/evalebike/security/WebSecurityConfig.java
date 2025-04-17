package integration4.evalebike.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception{
        return security
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login", "/home").permitAll()
                        .requestMatchers("/api/test/**").hasAnyRole("ADMIN", "TECHNICIAN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/superAdmin/**").hasRole("SUPER_ADMIN")
                        .requestMatchers("/technician/**").hasAnyRole("TECHNICIAN", "ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/bikeOwner/**").hasAnyRole("BIKE_OWNER", "TECHNICIAN", "ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/api/bike-owner/**").hasAnyRole("BIKE_OWNER", "TECHNICIAN", "ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/api/technician/**").hasAnyRole("TECHNICIAN", "ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/api/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/api/super-admin/**").hasRole("SUPER_ADMIN")
                        .requestMatchers(
                                antMatcher(HttpMethod.GET, "/js/**"),
                                antMatcher(HttpMethod.GET, "/css/**"),
                                antMatcher(HttpMethod.GET, "/images/**"),
                                antMatcher(HttpMethod.GET, "/webjars/**"),
                                regexMatcher(HttpMethod.GET, ".+\\.ico"))
                        .permitAll()

                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(login -> {
                    login.loginPage("/login").failureUrl("/login").defaultSuccessUrl("/technician/bike-owners").permitAll();
                })
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
