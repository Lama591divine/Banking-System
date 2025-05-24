package com.github.lama591divine.controller;

import com.github.lama591divine.service.AccountService;
import com.github.lama591divine.service.UserService;
import com.github.lama591divine.service.serviceDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CLIENT')")
public class ClientController {

    private final AccountService accountService;
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getUser(userDetails.getUsername()));
    }

    @GetMapping("/me/friends")
    public ResponseEntity<Set<String>> myFriends(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getFriends(userDetails.getUsername()));
    }

    @GetMapping("/me/accounts")
    public ResponseEntity<List<AccountDto>> myAccounts(@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(accountService.getAccountsOfUser(ud.getUsername()));
    }

    @GetMapping("/accounts/{num}")
    public ResponseEntity<AccountDto> account(@PathVariable String num) {
        return ResponseEntity.ok(accountService.getAccount(num));
    }

    @PostMapping("/friends")
    public ResponseEntity<Void> addFriend(@AuthenticationPrincipal UserDetails ud,
                                          @RequestBody FriendRequestDto dto) {
        userService.addFriend(new FriendRequestDto(ud.getUsername(), dto.friendLogin()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/friends")
    public ResponseEntity<Void> removeFriend(@AuthenticationPrincipal UserDetails userDetails, @RequestBody FriendRequestDto dto) {
        userService.removeFriend(new FriendRequestDto(userDetails.getUsername(), dto.friendLogin()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accounts/deposit")
    public ResponseEntity<Void> deposit(@RequestBody MoneyDto dto) {
        accountService.deposit(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accounts/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody MoneyDto dto) {
        accountService.withdraw(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accounts/transfer")
    public ResponseEntity<Void> transfer(@RequestBody TransferDto dto) {
        accountService.transfer(dto);
        return ResponseEntity.ok().build();
    }
}
