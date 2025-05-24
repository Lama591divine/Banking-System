package com.github.lama591divine.controller;

import com.github.lama591divine.dto.request.*;
import com.github.lama591divine.dto.response.AccountDto;
import com.github.lama591divine.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Управление счетами")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @Operation(
            summary = "Открыть счёт для пользователя",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Счёт успешно открыт"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос ‒ пустой/невалидный login"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    public ResponseEntity<Void> openAccount(@RequestBody @Valid CreateAccountDto dto) {
        accountService.createAccountForUser(dto.userLogin());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{accountNumber}")
    @Operation(
            summary = "Получить счёт по номеру",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Счёт найден",
                            content = @Content(schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный формат UUID"),
                    @ApiResponse(responseCode = "404", description = "Счёт не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    public ResponseEntity<AccountDto> getAccount(@PathVariable @NotBlank String accountNumber) {
        return ResponseEntity.ok(accountService.get(accountNumber));
    }

    @GetMapping
    @Operation(
            summary = "Получить все счета",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/transactions")
    @Operation(
            summary = "Получить транзакции счёта с фильтром по типу",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный UUID или отсутствует параметр"),
                    @ApiResponse(responseCode = "404", description = "Счёт не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    public ResponseEntity<List<String>> getTransactions(@RequestParam @NotBlank String accountNumber,
                                                        @RequestParam @NotBlank String type) {
        return ResponseEntity.ok(accountService.getTransactionsByFilter(accountNumber, type));
    }

    @PostMapping("/deposit")
    @Operation(
            summary = "Пополнить счёт",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Счёт пополнен",
                            content = @Content(schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "400", description = "Отрицательная сумма или неверный UUID"),
                    @ApiResponse(responseCode = "404", description = "Счёт не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    public ResponseEntity<AccountDto> deposit(@RequestBody @Valid RequestMoney requestMoney) {
        return ResponseEntity.ok(accountService.deposit(requestMoney.id(), requestMoney.amount()));
    }

    @PostMapping("/withdraw")
    @Operation(
            summary = "Снять средства со счёта",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Средства сняты",
                            content = @Content(schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "400", description = "Отрицательная сумма или недостаточно средств"),
                    @ApiResponse(responseCode = "404", description = "Счёт не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    public ResponseEntity<AccountDto> withdraw(@RequestBody @Valid RequestMoney requestMoney) {
        return ResponseEntity.ok(accountService.withdraw(requestMoney.id(), requestMoney.amount()));
    }

    @PostMapping("/transfer")
    @Operation(
            summary = "Перевести средства между счетами",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Перевод выполнен"),
                    @ApiResponse(responseCode = "400", description = "Неверные данные / недостаточно средств"),
                    @ApiResponse(responseCode = "404", description = "Один из счетов не найден"),
                    @ApiResponse(responseCode = "409", description = "Попытка перевода на тот же счёт"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferRequest transferRequest) {
        accountService.transfer(transferRequest.fromId(), transferRequest.toId(), transferRequest.amount());
        return ResponseEntity.noContent().build(); // 204
    }
}
