package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.*;

public interface FilmStorage {

    Map<Integer, Film> films = new HashMap<>();
    Collection<Film> findAll();
    Film create(Film film) throws ValidationException;
    Film edit(Film film) throws Throwable;
    Film getFilm(int id) throws NotFoundException;
    List<Set<Genres>> getGenres();
    Set<Genres> getGenres(int id) throws Throwable;
    List<Rating> getMpa();
    Rating getMpa(int id) throws Throwable;

}
