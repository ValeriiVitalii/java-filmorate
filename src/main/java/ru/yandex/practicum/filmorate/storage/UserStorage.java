package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.Valid;
import java.util.Collection;

public interface UserStorage {
    Collection<User> getAllUsers();
    User getUser(Long id);
    User create(@Valid @RequestBody User user) throws ValidationException;
    User edit(@Valid @RequestBody User user) throws ValidationException, NotFoundException;
}
