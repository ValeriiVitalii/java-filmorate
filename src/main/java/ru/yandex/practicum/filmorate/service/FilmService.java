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
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    Comparator<Film> comparator = new Comparator<>() {
        @Override
        public int compare(Film o1, Film o2) {
            return o2.getLikes().size() - o1.getLikes().size();
        }
    };

    public int addLike(int id, int userId) throws NotFoundException {
        if (filmStorage.getFilm(id) == null || userId <= 0) {
            throw new NotFoundException("Такого фильма не существует");
        }
        Film film = filmStorage.getFilm(id);
        return film.addLike(userId);
    }

    public Film getFilm(int id) throws NotFoundException {
        return filmStorage.getFilm(id);
    }

    public int removeLike(int id, int userId) throws NotFoundException {
        if (filmStorage.getFilm(id) == null || userId <= 0) {
            throw new NotFoundException("Такого фильма не существует");
        }
        return filmStorage.getFilm(id).removeLike(userId);
    }

    /*public Collection<Film> getPopularFilm(int count) throws ValidationException {
        if (count <= 0) {
            throw new ValidationException("count должен быть больше нуля");
        }
        Set<Film> popularFilm = new HashSet<>();

        List<Film> films = new ArrayList<>(filmStorage.getAllFilms().values());
        Collections.reverse(films);
        for (Film f : films) {
            if (count > 0) {
                popularFilm.add(f);
                count--;
            }
        }
        return popularFilm;
    }*/

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) throws ValidationException {
        return filmStorage.create(film);
    }

    public Film edit(Film film) throws Throwable {
        return filmStorage.edit(film);
    }

    public List<Set<Genres>> getGenres() {
        return filmStorage.getGenres();
    }

    public Set<Genres> getGenres(int id) throws Throwable {
        return filmStorage.getGenres(id);
    }

    public List<Rating> getMpa() {
        return filmStorage.getMpa();
    }

    public Rating getMpa(int id) throws Throwable {
        return filmStorage.getMpa(id);
    }
}

