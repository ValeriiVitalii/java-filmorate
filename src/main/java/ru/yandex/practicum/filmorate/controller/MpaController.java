package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;


@RestController
@RequestMapping("/mpa")
public class MpaController {

    final private FilmService filmService;

    @Autowired
    public MpaController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public Rating getMpa(@PathVariable int id) throws Throwable {
        return filmService.getMpa(id);
    }

    @GetMapping()
    public List<Rating> getMpa() {
        return filmService.getMpa();
    }
}