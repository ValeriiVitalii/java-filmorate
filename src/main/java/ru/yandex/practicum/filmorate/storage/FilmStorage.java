package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.*;

public interface FilmStorage {

    Map<Long, Film> films = new HashMap<>();
    Collection<Film> findAll();
    Film create(Film film) throws ValidationException;
    Film edit(Film film) throws Throwable;
    Film getFilm(Long id) throws NotFoundException;
    List<Set<Genres>> getGenres();
    Set<Genres> getGenres(Long id) throws Throwable;
    List<Mpa> getMpa();
    Mpa getMpa(int id) throws Throwable;

}
