package com.github.Lama591divine.service;

import com.github.Lama591divine.service.serviceDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ApiGatewayService {

    private final RestTemplate restTemplate;

    public ResponseEntity<Void> createUser(CreateUserDto createUserDto) {
        HttpEntity<Object> request = new HttpEntity<>(createUserDto);
        return restTemplate.postForEntity("http://localhost:8080/users", request, Void.class);
    }

    public ResponseEntity<String> getUser(String login) {
        return restTemplate.getForEntity(
                "http://localhost:8080/users/{login}",
                String.class,
                login
        );
    }

    public ResponseEntity<Set<String>> getFriends(String login) {
        return restTemplate.exchange(
                "http://localhost:8080/users/{login}/getFriends",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<String>>() {},
                login
        );
    }

    public ResponseEntity<List<CreateUserDto>> getByFilter(String hairColor, String gender) {
        return restTemplate.exchange(
                "http://localhost:8080/users/getByHairColorAndGender/{hairColor}/{gender}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CreateUserDto>>() {},
                hairColor, gender
        );
    }

    public ResponseEntity<String> getAccount(String id) {
        return restTemplate.getForEntity(
                "http://localhost:8080/accounts/{id}",
                String.class,
                id
        );
    }

    public ResponseEntity<Void> openAccount(OpenAccountRequestDto requestDto) {
        HttpEntity<OpenAccountRequestDto> request = new HttpEntity<>(requestDto);
        return restTemplate.postForEntity("http://localhost:8080/accounts", request, Void.class);
    }
}
