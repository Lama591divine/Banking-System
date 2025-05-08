package com.github.lama591divine.controller;

import com.github.lama591divine.dto.request.CreateAccount;
import com.github.lama591divine.dto.request.RequestMoney;
import com.github.lama591divine.dto.request.RequestTransaction;
import com.github.lama591divine.dto.response.AccountDto;
import com.github.lama591divine.dto.badStatus.NotFoundResponse;
import com.github.lama591divine.dto.badStatus.NotValidResponse;
import com.github.lama591divine.dto.request.TransferRequest;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@Validated
@RequiredArgsConstructor
@Tag(name = "Управление счетами")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Открыть счёт для пользователя",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Счёт успешно открыт",
                            content = @Content(schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос (например, пустой логин)",
                            content = @Content(schema = @Schema(implementation = NotValidResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public void openAccount(@RequestBody @Valid CreateAccount createAccount) {
        accountService.createAccountForUser(createAccount.userLogin());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получить счёт по ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос (например, пустой или неверный формат ID)",
                            content = @Content(schema = @Schema(implementation = NotValidResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Счёт не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public AccountDto getAccount(@PathVariable @NotBlank String id) {
        return accountService.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получить все счета пользователей",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос (например, пустой или неверный формат ID)",
                            content = @Content(schema = @Schema(implementation = NotValidResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Счёт не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public List<AccountDto> getAllAccounts() {
        return accountService.getAll();
    }

    @GetMapping("/getTransactions")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получить список всех транзакций по id и типу транзакции",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос (например, пустой или неверный формат ID)",
                            content = @Content(schema = @Schema(implementation = NotValidResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Счёт не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public List<String> getTransactions(@RequestBody @Valid RequestTransaction request) {
        return accountService.getTransactionsByFilter(request.id(), request.typeTransaction());
    }

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Пополнить счёт",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Счёт успешно пополнен",
                            content = @Content(schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос (например, отрицательная сумма)",
                            content = @Content(schema = @Schema(implementation = NotValidResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Счёт не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public AccountDto deposit(
            @RequestBody @Valid RequestMoney request
    ) {
        return accountService.deposit(request.id(), request.amount());
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Снять средства со счёта",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Средства успешно сняты",
                            content = @Content(schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос (например, отрицательная сумма или недостаточно средств)",
                            content = @Content(schema = @Schema(implementation = NotValidResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Счёт не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public AccountDto withdraw(
            @RequestBody @Valid RequestMoney request
    ) {
        return accountService.withdraw(request.id(), request.amount());
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Перевести средства между счетами",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Перевод выполнен успешно"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос (например, отрицательная сумма или недостаточно средств)",
                            content = @Content(schema = @Schema(implementation = NotValidResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Один из счетов не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public void transfer(@RequestBody @Valid TransferRequest request) {
        accountService.transfer(request.fromId(), request.toId(), request.amount());
    }
}
