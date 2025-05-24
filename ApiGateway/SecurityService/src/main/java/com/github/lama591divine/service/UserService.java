package com.github.lama591divine.service;

import com.github.lama591divine.service.serviceDto.CreateUserDto;
import com.github.lama591divine.service.serviceDto.FriendRequestDto;
import com.github.lama591divine.service.serviceDto.UserDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CoreServiceProxy proxy;

    public void createUser(CreateUserDto dto) {
        proxy.createUser(dto);
    }

    public void createAdmin(CreateUserDto dto) {
        proxy.createAdmin(dto);
    }

    public UserDto getUser(String login) {
        return proxy.getUser(login);
    }

    public List<UserDto> getUsers(String g, String h) {
        return proxy.getUsers(h, g);
    }

    public Set<String> getFriends(String login) {
        return proxy.getFriends(login);
    }

    public void addFriend(FriendRequestDto dto) {
        proxy.addFriend(dto);
    }

    public void removeFriend(FriendRequestDto dto) {
        proxy.removeFriend(dto);
    }
}

