package com.github.lama591divine.service;

import com.github.lama591divine.dao.UserDao;
import com.github.lama591divine.dto.response.UserDto;
import com.github.lama591divine.entities.User;
import com.github.lama591divine.enums.Gender;
import com.github.lama591divine.enums.HairColor;
import com.github.lama591divine.exception.*;
import com.github.lama591divine.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserDao   userDao;
    private final DtoMapper mapper;

    public void create(String login, String name, Integer age, Gender gender, HairColor hairColor) {
        userDao.save(new User(login, name, age, gender, hairColor));
    }

    @Transactional(readOnly = true)
    public UserDto get(String login) {
        return mapper.toDto(find(login));
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllByHairColorAndGender(HairColor hc, Gender g) {
        return userDao.findByHairColorAndGender(hc, g)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public void addFriend(String login, String friendLogin) {
        manageFriendship(login, friendLogin, true);
    }
    public void removeFriend(String login, String friendLogin) {
        manageFriendship(login, friendLogin, false);
    }

    private User find(String login) {
        return userDao.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    private void manageFriendship(String u1, String u2, boolean add) {
        User user = find(u1);
        User friend = find(u2);

        if (add) {
            user.addFriend(friend);
        }
        else {
            user.removeFriend(friend);
        }

        userDao.save(user);
    }
}
