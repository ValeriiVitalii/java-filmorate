package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(makeFinal = true, level= AccessLevel.PRIVATE)
@Data
public class Film {
    @NonFinal
    long id;

    @NotNull
    String name;

    String description;
    LocalDate releaseDate;
    long duration;

    Set<Long> likes = new HashSet<>();

    public Long addLike(Long idUser) {
        likes.add(idUser);
        return idUser;
    }

    public Long removeLike(Long idUser) {
        likes.remove(idUser);
        return idUser;
    }
}
