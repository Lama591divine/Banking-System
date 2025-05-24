package com.github.lama591divine.controller;

import com.github.lama591divine.service.AccountService;
import com.github.lama591divine.service.UserService;
import com.github.lama591divine.service.serviceDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final AccountService accountService;

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto dto) {
        userService.createUser(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accounts")
    public ResponseEntity<Void> openAccount(@RequestBody OpenAccountDto dto) {
        accountService.openAccount(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admins")
    public ResponseEntity<Void> createAdmin(@RequestBody CreateUserDto dto) {
        userService.createAdmin(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam String gender,
                                                  @RequestParam String hairColor) {
        return ResponseEntity.ok(userService.getUsers(gender, hairColor));
    }

    @GetMapping("/users/{login}")
    public ResponseEntity<UserDto> getUser(@PathVariable String login) {
        return ResponseEntity.ok(userService.getUser(login));
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/users/{login}/accounts")
    public ResponseEntity<List<AccountDto>> getAccountsOfUser(@PathVariable String login) {
        return ResponseEntity.ok(accountService.getAccountsOfUser(login));
    }

    @GetMapping("/accounts/{num}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable String num) {
        return ResponseEntity.ok(accountService.getAccount(num));
    }
}
