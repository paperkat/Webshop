package com.demo.webshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Profile("security")
@Configuration
public class SecurityEnabledConfig {
// Security enabled via 'security' profile

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Stateless REST API (HTTP Basic)
                                                                        // CSRF not applicable
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login.disable()) // Disable default login page
                .httpBasic(Customizer.withDefaults());

        return http.build();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // In-memory users for PoC/demo purposes during development
    @Bean
    UserDetailsService userDetailsService(PasswordEncoder encoder) {
        String password = encoder.encode("password");
        UserDetails user = User.withUsername("user").password(password).roles("USER").build();
        UserDetails admin = User.withUsername("admin").password(password).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user,admin);
    }
}
