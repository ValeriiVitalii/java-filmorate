package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    private final long id;

    @NotNull
    private final String name;

    private final String description;
    private final LocalDate releaseDate;
    private final long duration;
    private Mpa rating;
    Set<Genres> genre = new HashSet<>();
    Set<Long> likes = new HashSet<>();



    public Long addLike(Long idUser) {
        likes.add(idUser);
        return idUser;
    }

    public Long removeLike(Long idUser) {
        likes.remove(idUser);
        return idUser;
    }
    public Genres addGenre(Genres genre) {
        this.genre.add(genre);
        return genre;
    }
}
