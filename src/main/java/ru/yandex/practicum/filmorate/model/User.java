package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private int id;

    @Email
    private String email;

    @NotNull
    private final String login;

    private String name;

    private LocalDate birthday;


    Set<Integer> friends = new HashSet<>();

    public int addFriends(int id) {
        friends.add(id);
        return id;
    }

    public int removeFriend(int id) {
        friends.remove(id);
        return id;
    }


    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
