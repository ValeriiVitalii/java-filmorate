package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    public final Map<Long, User> users = new HashMap<>();

    private long id = 1;

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public User getUser(Long id) {
        return users.get(id);
    }

    public User create(@Valid @RequestBody User user) throws ValidationException {
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Неверно указана почта");
        } if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        } if(user.getName().isBlank()) {
            user.setName(user.getLogin());
        } if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        user.setId(id++);
        users.put(user.getId(), user);
        return user;
    }

    public User edit(@Valid @RequestBody User user) throws ValidationException, NotFoundException {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Такого пользователя несуществует");
        }
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Неверно указана почта");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        users.put(user.getId(), user);
        return user;
    }
}
