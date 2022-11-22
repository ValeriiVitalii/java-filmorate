package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Long addFriend(Long id, Long friendId) throws NotFoundException {
        if(userStorage.getUser(friendId) == null) {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
        userStorage.getUser(id).addFriends(friendId);
       userStorage.getUser(friendId).addFriends(id);
       return id;
    }

    public Long removeFriend(Long id, Long friendId) throws NotFoundException {
        if(userStorage.getUser(friendId) == null) {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
        userStorage.getUser(id).removeFriend(friendId);
        userStorage.getUser(friendId).removeFriend(id);
        return id;
    }

    public List<Long> getMutualFriends(Long id, Long otherId) throws NotFoundException {
        if(userStorage.getUser(otherId) == null) {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
        Set<Long> friendsUser = userStorage.getUser(id).getFriends();
        Set<Long> friendsUser2 = userStorage.getUser(otherId).getFriends();
        List<Long> mutualFriends = new ArrayList<>();
        for (Long i : friendsUser) {
            if(friendsUser2.contains(i)) {
                mutualFriends.add(i);
            }
        }
        return mutualFriends;
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUser(Long id) {
        return userStorage.getUser(id);
    }

    public User create(User user) throws ValidationException {
        return userStorage.create(user);
    }

    public User edit(User user) throws ValidationException, NotFoundException {
        return userStorage.edit(user);
    }
}
