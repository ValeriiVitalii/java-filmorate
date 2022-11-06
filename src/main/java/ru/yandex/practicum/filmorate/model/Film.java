package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@FieldDefaults(makeFinal = true, level= AccessLevel.PRIVATE)
@Data
public class Film {
    @NonFinal
    int id;

    @NotNull
    String name;

    String description;
    LocalDate releaseDate;
    long duration;
}
