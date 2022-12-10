package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@FieldDefaults(makeFinal = true, level= AccessLevel.PRIVATE)
public class User {
    @NonFinal
    long id;

    @Email
    String email;

    @NotNull
    String login;

    @NonFinal
    String name = "";

    LocalDate birthday;

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
