package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
public class GenreController {

    final private FilmService filmService;

    @Autowired
    public GenreController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public Genres getGenres(@PathVariable int id) throws Throwable {
        return filmService.getGenres(id);
    }

    @GetMapping()
    public Collection<Genres> getGenres() {
        return filmService.getGenres();
    }

}