package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.*;

public interface FilmStorage {

    Map<Integer, Film> films = new HashMap<>();

    Film createFilm(Film film) throws ValidationException;
    Film editFilm(Film film) throws Throwable;
    Film getFilm(int id) throws NotFoundException;
    Collection<Film> getAllFilms();
    Genres getGenres(int id) throws Throwable;
    Collection<Genres> getGenres();
    List<Rating> getMpa();
    Rating getMpa(int id) throws Throwable;
    int addLike(int id, int userId) throws NotFoundException;
    Collection<Film> getPopularFilm(int count) throws ValidationException;
    int removeLike(int idFilm, int userId) throws NotFoundException;

}