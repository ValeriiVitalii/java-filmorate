package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public Collection<User> getAllUsers(Long id) {
        return userService.getAllUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        return userService.create(user);
    }

    @PutMapping
    public User edit(@Valid @RequestBody User user) throws ValidationException, NotFoundException {
        return userService.edit(user);
    }

    @PostMapping("/users/{id}/friends/{friendId}")
    public Long addFriend(@PathVariable Long id,
                          @PathVariable Long friendId) throws NotFoundException {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public Long removeFriend(@PathVariable Long id,
                             @PathVariable Long friendId) throws NotFoundException {
        return userService.removeFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<Long> getMutualFriends(@PathVariable Long id,
                                       @PathVariable Long otherId) throws NotFoundException {
        return userService.getMutualFriends(id, otherId);
    }
}