package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    public Map<Integer, Film> films = new HashMap<>();
    private static final LocalDate MIN_BIRTH_FILM = LocalDate.of(1895, 12, 28);
    final static long MIN_DURATION_FILM = 0;
    private int id = 1;


    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        validate(film);
        film.setId(id++);
        films.put(film.getId(),film);

        log.info("Добавлен новый объект Film " + film.toString());
        return film;
    }

    @PutMapping
    public Film createOrEdit(@Valid @RequestBody Film film) throws ValidationException {
        if (!films.containsKey(film.getId())) {
            log.info("Объект Film не изменен т.к. нету Film с таким id");
            throw new ValidationException("Такого фильма несуществует");
        }
        validate(film);
        films.put(film.getId(), film);

        log.info("Добавлен или изменен новый объект Film " + film.toString());
        return film;
    }

    public void validate(@Valid Film film) throws ValidationException {
        if (film.getName().isBlank()) {
            log.info("В объекте Film пустое название");
            throw new ValidationException("Название не может быть пустым");
        } if(film.getDescription().length() > 200) {
            log.info("В объекте Film больше 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        } if(film.getReleaseDate().isBefore(MIN_BIRTH_FILM)) {
            log.info("В объекте Film неверная дата релиза");
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        } if(film.getDuration() <= MIN_DURATION_FILM) {
            log.info("В объекте Film невенрная продолжительности фильма");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
