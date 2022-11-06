package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@FieldDefaults(makeFinal = true, level= AccessLevel.PRIVATE)
public class User {
    @NonFinal
    int id;

    @Email
    String email;

    @NotNull
    String login;

    @NonFinal
    String name = "";

    LocalDate birthday;
}
