package com.github.Lama591divine.controller;

import com.github.Lama591divine.service.ApiGatewayService;
import com.github.Lama591divine.service.serviceDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiGatewayController {

    private final ApiGatewayService apiGatewayService;

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        return apiGatewayService.createUser(createUserDto);
    }

    @GetMapping("/users/{login}")
    public ResponseEntity<String> getUser(@PathVariable String login) {
        return apiGatewayService.getUser(login);
    }

    @GetMapping("/users/{login}/friends")
    public ResponseEntity<Set<String>> getFriends(@PathVariable String login) {
        return apiGatewayService.getFriends(login);
    }

    @GetMapping("/users/getByHairColorAndGender/{hairColor}/{gender}")
    public ResponseEntity<List<CreateUserDto>> getByFilter(@RequestParam String hairColor, @RequestParam String gender) {
        return apiGatewayService.getByFilter(hairColor, gender);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<String> getAccount(@PathVariable String id) {
        return apiGatewayService.getAccount(id);
    }

    @PostMapping("/accounts")
    public ResponseEntity<Void> openAccount(@RequestBody OpenAccountRequestDto openAccountRequestDto) {
        return apiGatewayService.openAccount(openAccountRequestDto);
    }

    @RequestMapping("/**")
    public ResponseEntity<String> handleUnknownRequests() {
        return ResponseEntity.status(404).body("Запрошенный маршрут не найден в ApiGatewayController");
    }
}