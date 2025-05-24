package com.github.lama591divine.serviceImp;

import com.github.lama591divine.entity.User;
import com.github.lama591divine.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String login) {
        User u = repo.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(login));
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getLogin())
                .password(u.getPassword())
                .roles(u.getRole().name())
                .build();
    }
}

