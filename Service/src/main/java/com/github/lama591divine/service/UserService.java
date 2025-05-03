package com.github.lama591divine.service;

import com.github.lama591divine.dao.UserDao;
import com.github.lama591divine.dto.response.UserDto;
import com.github.lama591divine.entitys.User;
import com.github.lama591divine.enums.Gender;
import com.github.lama591divine.enums.HairColor;
import com.github.lama591divine.exception.*;
import com.github.lama591divine.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserDao userDao;
    private final DtoMapper mapper;

    @Transactional
    public UserDto create(String login, String name, Integer age, Gender gender, HairColor hairColor) {
        User user = new User(login, name, age, gender, hairColor, new HashSet<>(), new HashSet<>());
        return mapper.toDto(userDao.save(user));
    }

    public UserDto get(String login) {
        return mapper.toDto(find(login));
    }

    public List<UserDto> getAllByHairColorAndGender(HairColor hairColor, Gender gender) {
        List<User> users = userDao.findByHairColorAndGender(hairColor, gender);
        return users.stream().map(mapper::toDto).toList();
    }

    @Transactional
    public void addFriend(String login, String friendLogin) {
        manageFriendship(login, friendLogin, true);
    }

    @Transactional
    public void removeFriend(String login, String friendLogin) {
        manageFriendship(login, friendLogin, false);
    }

    private User find(String login) {
        return userDao.findById(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    private void manageFriendship(String loginUser, String loginFriend, boolean add) {
        User user = find(loginUser);
        User friend = find(loginFriend);
        if (add) {
            user.getFriends().add(friend);
            friend.getFriends().add(user);
        }
        else {
            user.getFriends().remove(friend);
            friend.getFriends().remove(user);
        }
        userDao.save(user);
        userDao.save(friend);
    }
}
