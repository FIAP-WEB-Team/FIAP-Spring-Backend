package br.com.fiap.spring.configuration;

import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.spring.repository.FirebaseRepository;
import br.com.fiap.spring.security.JwtUserDetailService;

@Configuration
public class AuthConfig {

    FirebaseRepository repository;

    public AuthConfig(FirebaseRepository repository) {
        this.repository = repository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailService() {
        return new JwtUserDetailService(repository);
    }
}