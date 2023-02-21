package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    private int id;
    @NotNull
    private final String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;

    private Rating rating;

    Set<Genres> genre = new HashSet<>();
    Set<Integer> likes = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, long duration, int rating) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        Rating m = new Rating(rating);
        this.rating = m;
        //this.rating = rating;
    }

    public int addLike(int idUser) {
        likes.add(idUser);
        return idUser;
    }

    public int removeLike(int idUser) {
        likes.remove(idUser);
        return idUser;
    }
    public Genres addGenre(Genres genre) {
        this.genre.add(genre);
        return genre;
    }

    public int getRating() {
       return rating.getId();
    }

    public void setRating(int id) {
        rating.setId(id);
    }

    /*public String getMpa() {
        return rating.getMpa();
    }

    public void setMpa(String name) {
        rating.setMpa(name);
    }*/
}
