package com.github.lama591divine.service;

import com.github.lama591divine.service.serviceDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CoreServiceProxy {

    private final RestTemplate restTemplate;

    @Value("${core-service.base-url:http://localhost:8080}")
    private String base;

    public void createUser(CreateUserDto dto) {
        restTemplate.postForLocation(base + "/users", dto);
    }

    public void createAdmin(CreateUserDto dto) {
        restTemplate.postForLocation(base + "/admins", dto);
    }

    public UserDto getUser(String login) {
        return restTemplate.getForObject(base + "/users/{login}", UserDto.class, login);
    }

    public List<UserDto> getUsers(String hairColor, String gender) {
        ParameterizedTypeReference<List<UserDto>> ptr = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<UserDto>> resp = restTemplate.exchange(
                base + "/users/getByHairColorAndGender/{hair}/{gender}",
                HttpMethod.GET, null, ptr, hairColor, gender);
        return resp.getBody();
    }

    public Set<String> getFriends(String login) {
        ParameterizedTypeReference<Set<String>> ptr = new ParameterizedTypeReference<>() {};
        ResponseEntity<Set<String>> resp = restTemplate.exchange(
                base + "/users/{login}/friends",
                HttpMethod.GET, null, ptr, login);
        return resp.getBody();
    }

    public void addFriend(FriendRequestDto dto) {
        restTemplate.postForLocation(base + "/users/friends", dto);
    }

    public void removeFriend(FriendRequestDto dto) {
        HttpEntity<FriendRequestDto> entity = new HttpEntity<>(dto, json());
        restTemplate.exchange(base + "/users/friends", HttpMethod.DELETE, entity, Void.class);
    }

    public List<AccountDto> getAllAccounts() {
        ParameterizedTypeReference<List<AccountDto>> ptr = new ParameterizedTypeReference<>() {};
        return restTemplate.exchange(base + "/accounts", HttpMethod.GET, null, ptr).getBody();
    }

    public List<AccountDto> getAccountsOfUser(String login) {
        ParameterizedTypeReference<List<AccountDto>> ptr = new ParameterizedTypeReference<>() {};
        return restTemplate.exchange(
                base + "/users/{login}/accounts", HttpMethod.GET, null, ptr, login).getBody();
    }

    public AccountDto getAccount(String num) {
        return restTemplate.getForObject(base + "/accounts/{num}", AccountDto.class, num);
    }

    public void openAccount(OpenAccountDto dto) {
        restTemplate.postForLocation(base + "/accounts", dto);
    }

    public void deposit(MoneyDto dto) {
        restTemplate.postForLocation(base + "/accounts/deposit", dto);
    }

    public void withdraw(MoneyDto dto) {
        restTemplate.postForLocation(base + "/accounts/withdraw", dto);
    }

    public void transfer(TransferDto dto) {
        restTemplate.postForLocation(base + "/accounts/transfer", dto);
    }

    private static HttpHeaders json() {
        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_JSON);
        return h;
    }
}
