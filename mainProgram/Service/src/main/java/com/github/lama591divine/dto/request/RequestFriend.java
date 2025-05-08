package com.github.lama591divine.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RequestFriend (@NotBlank String login, @NotBlank String friendlogin) {}
