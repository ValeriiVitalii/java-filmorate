package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Film {

    private int id;
    @NotNull
    private final String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;
    private Rating mpa;

    List<Genres> genres = new ArrayList<>();
    Set<Integer> likes = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, long duration, Rating mpa) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }

    public int addLike(int idUser) {
        likes.add(idUser);
        return idUser;
    }

    public int removeLike(int idUser) {
        likes.remove(idUser);
        return idUser;
    }
}