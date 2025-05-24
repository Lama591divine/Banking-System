package com.github.lama591divine.config;

import com.github.lama591divine.dao.UserRepository;
import com.github.lama591divine.entity.User;
import com.github.lama591divine.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    @Bean
    public ApplicationRunner initAdmin() {
        return args -> {
            if (repo.findByLogin("admin").isEmpty()) {
                User admin = new User();
                admin.setLogin("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                repo.save(admin);
            }
        };
    }
}
