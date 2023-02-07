package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;

@Slf4j
@Service
public class FilmService {

    private final InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    Comparator<Film> comparator = new Comparator<>() {
        @Override
        public int compare(Film o1, Film o2) {
            return o2.getLikes().size() - o1.getLikes().size();
        }
    };

    public Long addLike(Long id, Long userId) throws NotFoundException {
        if (inMemoryFilmStorage.getFilm(id) == null || userId <= 0) {
            throw new NotFoundException("Такого фильма не существует");
        }
        Film film = inMemoryFilmStorage.getFilm(id);
        return film.addLike(userId);
    }

    public Film getFilm(Long id) throws NotFoundException {
        return inMemoryFilmStorage.getFilm(id);
    }

    public Long removeLike(Long id, Long userId) throws NotFoundException {
        if (inMemoryFilmStorage.getFilm(id) == null || userId <= 0) {
            throw new NotFoundException("Такого фильма не существует");
        }
        return inMemoryFilmStorage.getFilm(id).removeLike(userId);
    }

    public Collection<Film> getPopularFilm(int count) throws ValidationException {
        if (count <= 0) {
            throw new ValidationException("count должен быть больше нуля");
        }
        Set<Film> popularFilm = new HashSet<>();

        List<Film> films = new ArrayList<>(inMemoryFilmStorage.getAllFilms().values());
        Collections.reverse(films);
        for (Film f : films) {
            if (count > 0) {
                popularFilm.add(f);
                count--;
            }
        }
        return popularFilm;
    }

    public Collection<Film> findAll() {
        return inMemoryFilmStorage.findAll();
    }

    public Film create(Film film) throws ValidationException {
        return inMemoryFilmStorage.create(film);
    }

    public Film edit(Film film) throws Throwable {
        return inMemoryFilmStorage.edit(film);
    }

    public List<Set<Genres>> getGenres() {
        return inMemoryFilmStorage.getGenres();
    }

    public Set<Genres> getGenres(Long id) throws Throwable {
        return inMemoryFilmStorage.getGenres(id);
    }

    public List<Mpa> getMpa() {
        return inMemoryFilmStorage.getMpa();
    }

    public Mpa getMpa(int id) throws Throwable {
        return inMemoryFilmStorage.getMpa(id);
    }
}

