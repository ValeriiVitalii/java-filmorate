package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {
    final private FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }
    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findAll();
    }
    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) throws NotFoundException {
        return filmService.getFilm(id);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.create(film);
    }

    @PutMapping
    public Film edit(@Valid @RequestBody Film film) throws Throwable {
        return filmService.edit(film);
    }
    @PutMapping("/{id}/like/{userId}")
    public Long addLike(@PathVariable Long id,
                        @PathVariable Long userId) throws NotFoundException {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Long removeLike(@PathVariable Long id,
                           @PathVariable Long userId) throws NotFoundException {
        return filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilm(@RequestParam(defaultValue = "10")
                                               int count) throws ValidationException {
        return filmService.getPopularFilm(count);
    }

    @GetMapping("/genres")
    public List<Set<Genres>> getGenres() {
        return filmService.getGenres();
    }

    @GetMapping("/genres{id}")
    public Set<Genres> getGenres(@PathVariable Long id) throws Throwable {
        return filmService.getGenres(id);
    }

    @GetMapping("/mpa")
    public List<Mpa> getMpa() {
        return filmService.getMpa();
    }

    @GetMapping("/mpa{id}")
    public Mpa getMpa(@PathVariable int id) throws Throwable {
        return filmService.getMpa(id);
    }
}
