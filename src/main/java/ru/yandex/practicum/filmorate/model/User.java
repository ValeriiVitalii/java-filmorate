package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private final long id;

    @Email
    private final String email;

    @NotNull
    private final String login;

    private final String name = "";

    private final LocalDate birthday;


    Set<Long> friends = new HashSet<>();

    public Long addFriends(Long id) {
        friends.add(id);
        return id;
    }

    public Long removeFriend(Long id) {
        friends.remove(id);
        return id;
    }
}
