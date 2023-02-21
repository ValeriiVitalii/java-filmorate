package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film createFilm(Film film) throws ValidationException {
        return filmStorage.createFilm(film);
    }

    public Film editFilm(Film film) throws Throwable {
        return filmStorage.editFilm(film);
    }

    public Film getFilm(int id) throws NotFoundException {
        return filmStorage.getFilm(id);
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }


    public int addLike(int id, int userId) throws NotFoundException {
        return filmStorage.addLike(id, userId);
    }


    public int removeLike(int id, int userId) throws NotFoundException {
        return filmStorage.removeLike(id, userId);
    }

    public Collection<Film> getPopularFilm(int count) throws ValidationException {
        return filmStorage.getPopularFilm(count);
    }

    public Genres getGenres(int id) throws Throwable {
        return filmStorage.getGenres(id);
    }

    public Collection<Genres> getGenres() {
        return filmStorage.getGenres();
    }

    public List<Rating> getMpa() {
        return filmStorage.getMpa();
    }

    public Rating getMpa(int idMpa) throws Throwable {
        return filmStorage.getMpa(idMpa);
    }
}