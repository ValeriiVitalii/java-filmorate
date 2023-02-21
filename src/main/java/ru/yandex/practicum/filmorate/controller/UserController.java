package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws Throwable {
        return userService.createUser(user);
    }

    @PutMapping
    public User editUser(@Valid @RequestBody User user) throws ValidationException, NotFoundException {
        return userService.editUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) throws NotFoundException {
        return userService.getUser(id);
    }

    @GetMapping
    public Collection<User> getAllUsers(Long id) {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public int addFriend(@PathVariable int id,
                         @PathVariable int friendId) throws NotFoundException {
        return userService.addFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable int id) throws NotFoundException {
        return userService.getFriends(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public int removeFriend(@PathVariable int id,
                            @PathVariable int friendId) throws NotFoundException {
        return userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getMutualFriends(@PathVariable int id,
                                             @PathVariable int otherId) throws NotFoundException {
        return userService.getMutualFriends(id, otherId);
    }
}