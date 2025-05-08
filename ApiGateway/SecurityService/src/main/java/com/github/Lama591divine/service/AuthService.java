package com.github.Lama591divine.service;

import com.github.Lama591divine.Role;
import com.github.Lama591divine.User;
import com.github.Lama591divine.UserRepository;
import com.github.Lama591divine.service.serviceDto.AuthRequest;
import com.github.Lama591divine.service.serviceDto.TokenResponse;
import com.github.Lama591divine.utility.JwtUtility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtility jwtUtility;

    @Transactional
    public TokenResponse login(AuthRequest request) {
        var authToken = new UsernamePasswordAuthenticationToken(
                request.username(), request.password()
        );
        authenticationManager.authenticate(authToken);

        User user = userRepository.findByLogin(request.username())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Пользователь не найден"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();

        String token = jwtUtility.generateJwt(userDetails);
        return new TokenResponse(token);
    }

    @Transactional
    public User createClient(User user) {
        user.setRole(Role.CLIENT);
        encodePassword(user);
        return userRepository.save(user);
    }

    @Transactional
    public User createAdmin(User user) {
        user.setRole(Role.ADMIN);
        encodePassword(user);
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Пользователь не найден"));
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
