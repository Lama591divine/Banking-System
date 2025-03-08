package com.github.Lama591divine.systems;

import com.github.Lama591divine.ConverterDTO;
import com.github.Lama591divine.enums.Gender;
import com.github.Lama591divine.enums.HairColor;
import com.github.Lama591divine.entities.User;
import com.github.Lama591divine.repositoryes.Repository;
import com.github.Lama591divine.DTO.DtoUser;

import java.util.HashSet;

public class UserSystem extends ConverterDTO {
    private final Repository<DtoUser> userRepository;

    public UserSystem(Repository<DtoUser> userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String login, String name, int age, String genderStr, String hairColorStr) {
        Gender gender = Gender.valueOf(genderStr.toUpperCase());
        HairColor hairColor = HairColor.valueOf(hairColorStr.toUpperCase());

        DtoUser dtoUser = new DtoUser(login, name, age, gender, hairColor, new HashSet<>());
        userRepository.add(dtoUser);

        System.out.println("User created successfully!");
    }

    public void showUserInfo(String login) {
        DtoUser dtoUser = userRepository.getObject(login);
        if (dtoUser == null) {
            System.out.println("User not found.");
            return;
        }

        User user = toUser(dtoUser);

        System.out.println("\nUser Info:");
        System.out.println("Login: " + dtoUser.getLogin());
        System.out.println("Name: " + dtoUser.getName());
        System.out.println("Age: " + dtoUser.getAge());
        System.out.println("Gender: " + dtoUser.getGender());
        System.out.println("Hair Color: " + dtoUser.getHairColor());

        System.out.println("Friends:");
        user.showFriends();
    }

    public void manageFriends(String login, String friendLogin, String action) {
        DtoUser dtoUser = userRepository.getObject(login);
        DtoUser dtoFriend = userRepository.getObject(friendLogin);

        if (dtoUser == null || dtoFriend == null) {
            System.out.println("User or friend not found.");
            return;
        }

        User user = toUser(dtoUser);
        User friend = toUser(dtoFriend);

        if (action.equalsIgnoreCase("add")) {
            user.addFriend(friend.getLogin());
            friend.addFriend(user.getLogin());
            System.out.println(friendLogin + " added as a friend.");
        } else if (action.equalsIgnoreCase("remove")) {
            user.removeFriend(friend.getLogin());
            friend.removeFriend(user.getLogin());
            System.out.println(friendLogin + " removed from friends.");
        } else {
            System.out.println("Invalid action.");
        }

        userRepository.remove(dtoUser);
        userRepository.remove(dtoFriend);
        userRepository.add(toDtoUser(user));
        userRepository.add(toDtoUser(friend));
    }
}
