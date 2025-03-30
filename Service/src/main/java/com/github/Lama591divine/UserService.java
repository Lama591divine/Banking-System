package com.github.Lama591divine;

import java.util.HashSet;
import java.util.Set;

public class UserService {
    private final Dao<User> userDao;

    public UserService(Dao<User> userDao) {

        this.userDao = userDao;
    }

    public void createUser(String login, String name, int age, String genderStr, String hairColorStr) {
        Gender gender = Gender.valueOf(genderStr.toUpperCase());
        HairColor hairColor = HairColor.valueOf(hairColorStr.toUpperCase());

        User user = new User(login, name, age, gender, hairColor, new HashSet<>());

        userDao.create(user);
        System.out.println("User created successfully!");
    }

    public void showUserInfo(String login) {
        User user = userDao.getObjectById(login);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("\nUser Info:");
        System.out.println("Login: " + user.getLogin());
        System.out.println("Name: " + user.getName());
        System.out.println("Age: " + user.getAge());
        System.out.println("Gender: " + user.getGender());
        System.out.println("Hair Color: " + user.getHairColor());

        if (user.getFriends().isEmpty()) {
            System.out.println(user.getLogin() + " has no friends.");
        }
        else {
            System.out.println("Friends of " + user.getLogin() + ":");
            for (User friend : user.getFriends()) {
                System.out.println("- " + friend.getLogin());
            }
        }

        Set<Account> accounts = user.getAccounts();
        System.out.println("Accounts:");
        accounts.forEach(account -> System.out.println(account.getId()));
    }

    public void manageFriends(String login, String friendLogin, String action) {
        User user = userDao.getObjectById(login);
        User friend = userDao.getObjectById(friendLogin);

        if (user == null || friend == null) {
            System.out.println("User or friend not found.");
            return;
        }

        if (action.equalsIgnoreCase("add")) {
            user.getFriends().add(friend);
            friend.getFriends().add(user);
            System.out.println(friendLogin + " added as a friend for " + login);

        }
        else if (action.equalsIgnoreCase("remove")) {
            user.getFriends().remove(friend);
            friend.getFriends().remove(user);
            System.out.println(friendLogin + " removed from friends of " + login);

        }
        else {
            System.out.println("Invalid action.");
        }

        userDao.update(user);
        userDao.update(friend);
    }
}
