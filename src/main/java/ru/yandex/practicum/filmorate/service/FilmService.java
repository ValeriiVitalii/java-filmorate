package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import java.util.*;

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
            return o1.getLikes().size() - o2.getLikes().size();
        }
    };
    TreeSet<Film> popularFilm = new TreeSet<>(comparator);

    public Long addLike(Long id, Long userId) throws NotFoundException {
        if(filmStorage.getFilm(id) == null) {
            throw new NotFoundException("Такого фильма не существует");
        }
        Film film = filmStorage.getFilm(id);
        if(!popularFilm.contains(film)){
            popularFilm.add(film);
        }
        return film.addLike(userId);
    }

    public Long removeLike(Long id, Long userId) throws NotFoundException {
        if(filmStorage.getFilm(id) == null) {
            throw new NotFoundException("Такого фильма не существует");
        }
        return filmStorage.getFilm(id).removeLike(userId);
    }

    public Collection<Film> getPopularFilm(Integer count) throws ValidationException {
        if(count <= 0) {
            throw new ValidationException("count должен быть больше нуля");
        }

        Map<Long, Film> film = new HashMap();
        int countFilm = 0;

        for(Film f : popularFilm) {
            if(countFilm <= count) {
                film.put(f.getId(), f);
                countFilm++;
            }
        }
            return film.values();
        }

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) throws ValidationException {
        return filmStorage.create(film);
    }

    public Film edit(Film film) throws ValidationException {
        return filmStorage.edit(film);
    }
}


