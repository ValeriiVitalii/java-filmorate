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
        if (film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым");
        } if(film.getDescription().length() > 200) {
            log.info("Объект Film не добавлен т.к. в описании больше 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        } if(film.getReleaseDate().isBefore(MIN_BIRTH_FILM)) {
            log.info("Объект Film не добавлен из-за неверной даты релиза");
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        } if(film.getDuration() <= MIN_DURATION_FILM) {
            log.info("Объект Film не добавлен из-за неверной продолжительности фильма");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        film.setId(id++);
        films.put(film.getId(),film);
        log.info("Добавлен новый объект Film " + film.toString());
        return film;
    }

    public Film edit(Film film) throws Throwable {
        if (!films.containsKey(film.getId())) {
            log.info("Объект Film не изменен т.к. нету Film с таким id");
            throw new Throwable("Такого фильма несуществует");
        }
        if (film.getName().isBlank()) {
            log.info("Объект Film не изменен из-за пустого названия");
            throw new ValidationException("Название не может быть пустым");
        } if(film.getDescription().length() > 200) {
            log.info("Объект Film не изменен т.к. в описании больше 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        } if(film.getReleaseDate().isBefore(MIN_BIRTH_FILM)) {
            log.info("Объект Film не изменен из-за неверной даты релиза");
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        } if(film.getDuration() <= MIN_DURATION_FILM) {
            log.info("Объект Film не изменен из-за неверной продолжительности фильма");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
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
}
