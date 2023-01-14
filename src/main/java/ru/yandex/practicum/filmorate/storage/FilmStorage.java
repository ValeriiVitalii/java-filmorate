package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface FilmStorage {

    Map<Long, Film> films = new HashMap<>();
    Collection<Film> findAll();
    Film create(@Valid @RequestBody Film film) throws ValidationException;

    Film edit(@Valid @RequestBody Film film) throws Throwable;

    Map<Long, Film> getAllFilms();

    Film getFilm(Long id) throws NotFoundException;
}
