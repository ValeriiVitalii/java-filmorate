package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Rating {
    int id;

    String name;

    public Rating(int id) {
        this.id = id;
    }
}
