package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
@Primary
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "select * from users";
        return  jdbcTemplate.query(sql, this::MapRowToUser);
    }

    @Override
    public User getUser(int id) throws NotFoundException {
        String sql = "select * from users where user_id = " + id;
           return jdbcTemplate.queryForObject(sql, this::MapRowToUser);
    }

    @Override
    public User create(User user) throws ValidationException {
        validation(user);
        String sql = "insert into users(email, login, name, birthday) values(?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        return getLastUser();
    }

    public User getLastUser () throws ValidationException {
        String sql = "SELECT * FROM Users WHERE USER_ID =(SELECT max(USER_ID) FROM USERS)";
        return jdbcTemplate.queryForObject(sql, this::MapRowToUser);
    }

    @Override
    public User edit(User user) throws ValidationException, NotFoundException {
        validation(user);
        getUser(user.getId());
        String sql = "update users set email = ?, login = ?, name = ?, birthday = ? " +
                "where user_id = ?";
        int userID = jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(),
                user.getBirthday(), user.getId());
        user.setId(userID);
        return user;
    }

    @Override
    public int addFriend(int id, int friendId) throws NotFoundException {
        String sql = "insert into FRIENDSHIP(USER_ID, OTHER_USER_ID, FRIENDS_STATUS) values(?, ?, ?)";
        jdbcTemplate.update(sql, id, friendId, true);
        return id;
    }

    @Override
    public Collection<User> getFriends(int id) throws NotFoundException {
        String sql = "select u.* from FRIENDSHIP AS f inner join USERS U" +
                " on U.USER_ID = f.OTHER_USER_ID where F.USER_ID = ? AND FRIENDS_STATUS = true";
        return  jdbcTemplate.query(sql, this::MapRowToUser, id);
    }

    @Override
    public int removeFriend(int id, int friendId) throws NotFoundException {
        String sql = "update FRIENDSHIP set FRIENDS_STATUS = false where USER_ID = ? and OTHER_USER_ID = ?";
        jdbcTemplate.update(sql, id, friendId);
        return friendId;
    }

    @Override
    public Collection<User> getMutualFriends(int id, int friendId) throws NotFoundException {
        if(getUser(friendId) == null) {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
        Collection<User> friendsUser = getFriends(id);
        Collection<User> friendsUser2 = getFriends(friendId);
        Map<Integer, User> mutualFriends = new HashMap<>();

        for (User user : friendsUser) {
            if(friendsUser2.contains(user)) {
                mutualFriends.put(user.getId(), getUser(user.getId()));
            }
        }
        return mutualFriends.values();
    }

    private User MapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User(
                        resultSet.getString("email"),
                        resultSet.getString("login"),
                        resultSet.getString("name"),
                        resultSet.getDate("birthday").toLocalDate());
        user.setId(resultSet.getInt("user_id"));
        return user;
    }

    @Override
    public User validation(User user) throws ValidationException {
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Неверно указана почта");
        } if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        } if(user.getName().isBlank()) {
         user.setName(user.getLogin()); }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        return user;
    }
}
