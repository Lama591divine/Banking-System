package com.github.lama591divine.controller;

import com.github.lama591divine.dto.request.CreateUserRequest;
import com.github.lama591divine.dto.request.RequestFriend;
import com.github.lama591divine.dto.response.UserDto;
import com.github.lama591divine.enums.Gender;
import com.github.lama591divine.enums.HairColor;
import com.github.lama591divine.service.UserService;
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
import java.util.Set;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(
            summary = "Создать нового пользователя",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Пользователь создан"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос / валидация"),
                    @ApiResponse(responseCode = "409", description = "Логин уже существует"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserRequest req) {
        userService.create(req.login(), req.name(), req.age(), req.gender(), req.hairColor());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{login}")
    @Operation(
            summary = "Получить пользователя по логину",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный логин"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    public ResponseEntity<UserDto> getUser(@PathVariable @NotBlank String login) {
        return ResponseEntity.ok(userService.get(login));
    }

    @GetMapping("/{login}/friends")
    @Operation(
            summary = "Получить друзей пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный логин"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    public ResponseEntity<Set<String>> getFriends(@PathVariable @NotBlank String login) {
        return ResponseEntity.ok(userService.get(login).friends());
    }

    @GetMapping("/getByHairColorAndGender/{hairColor}/{gender}")
    @Operation(
            summary = "Получить пользователей по цвету волос и полу",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные параметры перечислений"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    public ResponseEntity<List<UserDto>> getByFilter(@PathVariable HairColor hairColor,
                                                     @PathVariable Gender gender) {
        return ResponseEntity.ok(userService.getAllByHairColorAndGender(hairColor, gender));
    }

    @PostMapping("/friends")
    @Operation(
            summary = "Добавить друга (обе стороны)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Друг добавлен",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос / один логин пуст"),
                    @ApiResponse(responseCode = "404", description = "Пользователь или друг не найден"),
                    @ApiResponse(responseCode = "409", description = "Пользователи уже друзья"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    public ResponseEntity<UserDto> addFriend(@RequestBody @Valid RequestFriend requestFriend) {
        userService.addFriend(requestFriend.login(), requestFriend.friendLogin());
        return ResponseEntity.ok(userService.get(requestFriend.login()));
    }

    @DeleteMapping("/friends")
    @Operation(
            summary = "Удалить друга (обе стороны)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Друг удалён",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос / один логин пуст"),
                    @ApiResponse(responseCode = "404", description = "Пользователь или друг не найден"),
                    @ApiResponse(responseCode = "409", description = "Пользователи и так не друзья"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            })
    public ResponseEntity<UserDto> removeFriend(@RequestBody @Valid RequestFriend requestFriend) {
        userService.removeFriend(requestFriend.login(), requestFriend.friendLogin());
        return ResponseEntity.ok(userService.get(requestFriend.login()));
    }
}