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
import java.util.Set;


@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    public final Map<Integer, User> users = new HashMap<>();

    private int id = 1;

    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public User getUser(int id) throws NotFoundException {
        if(!users.containsKey(id)) {
            throw new NotFoundException("Этого пользователя несуществует");
        }

        return users.get(id);
    }

    @Override
    public User createUser(@Valid @RequestBody User user) throws ValidationException {
        validation(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User editUser(@Valid @RequestBody User user) throws ValidationException, NotFoundException {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Такого пользователя несуществует");
        }
        validation(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public int addFriend(int id, int friendId) throws NotFoundException {
        if(getUser(friendId) == null || getUser(id) == null) {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
        getUser(id).addFriends(friendId);
        getUser(friendId).addFriends(id);
        return id;
    }
    @Override
    public Collection<User> getFriends(int id) throws NotFoundException {
        Set<Integer> idFriends = getUser(id).getFriends();
        Map<Integer, User> friends = new HashMap<>();
        for (int idF : idFriends) {
            friends.put(idF, getUser(idF));
        }
        return friends.values();
    }
    @Override
    public int removeFriend(int id, int friendId) throws NotFoundException {
        if(getUser(friendId) == null) {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
        getUser(id).removeFriend(friendId);
        getUser(friendId).removeFriend(id);
        return id;
    }

    @Override
    public Collection<User> getMutualFriends(int id, int friendId) throws NotFoundException {
        if(getUser(friendId) == null) {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
        Set<Integer> friendsUser = getUser(id).getFriends();
        Set<Integer> friendsUser2 = getUser(friendId).getFriends();
        Map<Integer, User> mutualFriends = new HashMap<>();

        for (int i : friendsUser) {
            if(friendsUser2.contains(i)) {
                mutualFriends.put(i, getUser(i));
            }
        }
        return mutualFriends.values();
    }

    @Override
    public User validation(User user) throws ValidationException {
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Неверно указана почта");
        } if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        } //if(user.getName().isBlank()) {
        //  user.setName(user.getLogin()); }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        return user;
    }
}