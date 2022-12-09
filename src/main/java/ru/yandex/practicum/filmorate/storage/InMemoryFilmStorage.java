package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    public Map<Long, Film> films = new HashMap<>();
    private static final LocalDate MIN_BIRTH_FILM = LocalDate.of(1895, 12, 28);
    final static long MIN_DURATION_FILM = 0;
    private long id = 1;

    public Collection<Film> findAll() {
        return films.values();
    }


    public Film create(Film film) throws ValidationException {
        validation(film);
        film.setId(id++);
        films.put(film.getId(),film);
        log.info("Добавлен новый объект Film " + film.toString());
        return film;
    }

    public Film edit(Film film) throws Throwable {
        if (!films.containsKey(film.getId())) {
            throw new Throwable("Такого фильма несуществует");
        }
        validation(film);
        films.put(film.getId(), film);
        log.info("Добавлен или изменен новый объект Film " + film.toString());
        return film;
    }

    public Map<Long, Film> getAllFilms() {
        return films;
    }

    public Film getFilm(Long id) throws NotFoundException {
        if(!films.containsKey(id)) {
            throw new NotFoundException("Такого фильма нет");
        }
        return films.get(id);
    }

    public Film validation(Film film) throws ValidationException {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым");
        } if(film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        } if(film.getReleaseDate().isBefore(MIN_BIRTH_FILM)) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        } if(film.getDuration() <= MIN_DURATION_FILM) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        return film;
    }
}
