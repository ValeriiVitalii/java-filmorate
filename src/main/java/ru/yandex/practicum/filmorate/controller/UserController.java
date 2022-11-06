package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    public final Map<Integer, User> users = new HashMap<>();
    private int id = 1;


    @GetMapping
    public Collection<User> getUser() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        if (!user.getEmail().contains("@")) {
            log.info("Объект User не добавлен из-за неверной почты");
            throw new ValidationException("Неверно указана почта");
        } if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.info("Объект User не добавлен из-за неверного логина");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        } if(user.getName().isBlank()) {
            user.setName(user.getLogin());
        } if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Объект User не добавлен  из-за неверной даты рождения");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        user.setId(id++);
        users.put(user.getId(), user);
        log.info("Добавлен новый объект User " + user.toString());
        return user;
    }

    @PutMapping
    public User edit(@Valid @RequestBody User user) throws ValidationException {
        if (!users.containsKey(user.getId())) {
            log.info("Объект User не изменен, тк нету User с таким id");
            throw new ValidationException("Такого пользователя несуществует");
        }
        if (!user.getEmail().contains("@")) {
            log.info("Объект User не изменен из-за неверной почты");
            throw new ValidationException("Неверно указана почта");
        } if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.info("Объект User не изменен из-за неверного логина");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        } if(user.getName().isBlank()) {
            user.setName(user.getLogin());
        } if (user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Объект User не изменен из-за неверной даты рождения");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        users.put(user.getId(), user);
        log.info("Изменен объект User " + user.toString());
        return user;
    }
}