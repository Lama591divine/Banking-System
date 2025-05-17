package com.github.lama591divine.controller;

import com.github.lama591divine.dto.request.CreateUserRequest;
import com.github.lama591divine.dto.badStatus.NotFoundResponse;
import com.github.lama591divine.dto.badStatus.NotValidResponse;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "Пользователи")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создать нового пользователя",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Пользователь успешно создан",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос",
                            content = @Content(schema = @Schema(implementation = NotValidResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public void createUser(@RequestBody @Valid CreateUserRequest request) {
        userService.create(
                request.login(),
                request.name(),
                request.age(),
                request.gender(),
                request.hairColor()
        );
    }

    @GetMapping("/{login}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получить пользователя по логину",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос",
                            content = @Content(schema = @Schema(implementation = NotValidResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public UserDto getUser(@PathVariable @NotBlank String login) {
        return userService.get(login);
    }

    @GetMapping("/{login}/getFriends")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получить список друзей пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос",
                            content = @Content(schema = @Schema(implementation = NotValidResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public Set<String> getFriends(@PathVariable @NotBlank String login) {
        return userService.get(login).friends();
    }

    @GetMapping("/getByHairColorAndGender/{hairColor}/{gender}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получить всех пользователей системы по фильтру",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос",
                            content = @Content(schema = @Schema(implementation = NotValidResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public List<UserDto> getByFilter(@PathVariable HairColor hairColor, @PathVariable Gender gender) {
        return userService.getAllByHairColorAndGender(hairColor, gender);
    }

    @PostMapping("/friends")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Добавить друга (в обе стороны)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Друг добавлен",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос",
                            content = @Content(schema = @Schema(implementation = NotValidResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь или друг не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public UserDto addFriend(
            @RequestBody @Valid RequestFriend requestFriend) {
        userService.addFriend(requestFriend.login(), requestFriend.friendlogin());
        return userService.get(requestFriend.login());
    }

    @DeleteMapping("/friends")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Удалить друга (в обе стороны)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Друг удалён",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос",
                            content = @Content(schema = @Schema(implementation = NotValidResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь или друг не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
            }
    )
    public UserDto removeFriend(
            @RequestBody @Valid RequestFriend requestFriend) {
        userService.removeFriend(requestFriend.login(), requestFriend.friendlogin());
        return userService.get(requestFriend.login());
    }
}
