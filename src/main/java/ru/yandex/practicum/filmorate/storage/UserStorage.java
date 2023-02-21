package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> getAllUsers();
    User getUser(int id) throws NotFoundException;
    User create(User user) throws ValidationException;
    User edit(User user) throws ValidationException, NotFoundException;
    User validation(User user) throws ValidationException;
    int addFriend(int id, int friendId) throws NotFoundException;
    Collection<User> getFriends(int id) throws NotFoundException;
    int removeFriend(int id, int friendId) throws NotFoundException;
    Collection<User> getMutualFriends(int id, int friendId) throws NotFoundException;
}
