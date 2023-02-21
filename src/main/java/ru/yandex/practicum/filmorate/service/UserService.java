package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public int addFriend(int id, int friendId) throws NotFoundException {
        userStorage.addFriend(id, friendId);
        return id;
    }

    public Collection<User> getFriends(int id) throws NotFoundException {
        return userStorage.getFriends(id);
    }

    public int removeFriend(int id, int friendId) throws NotFoundException {
        return userStorage.removeFriend(id, friendId);
    }

    public Collection<User> getMutualFriends(int id, int friendId) throws NotFoundException {
        return userStorage.getMutualFriends(id, friendId);
    }

    /*public Collection<User> getMutualFriends(int id, int friendId) throws NotFoundException {
        if(userStorage.getUser(friendId) == null) {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
        Set<Integer> friendsUser = userStorage.getUser(id).getFriends();
        Set<Integer> friendsUser2 = userStorage.getUser(friendId).getFriends();
        Map<Integer, User> mutualFriends = new HashMap<>();

        for (int i : friendsUser) {
            if(friendsUser2.contains(i)) {
                mutualFriends.put(i, userStorage.getUser(i));
            }
        }
        return mutualFriends.values();
    }*/

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUser(int id) throws NotFoundException {
        return userStorage.getUser(id);
    }

    public User create(User user) throws Throwable {
        return userStorage.create(user);
    }

    public User edit(User user) throws ValidationException, NotFoundException {
        return userStorage.edit(user);
    }
}
