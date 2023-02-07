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
import java.util.Collection;

@Component
@Primary
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<User> getAllUsers() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, this::MapRowToUser);
    }
    @Override
    public User getUser(Long id) throws NotFoundException {
        String sql = "select * from users where user_id = ?";
        return jdbcTemplate.queryForObject(sql, this::MapRowToUser, id);
    }

    @Override
    public User create(User user) throws ValidationException {
        String sql = "insert into users(email, login, name, birthday) values(?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        return user;
    }

    @Override
    public User edit(User user) throws ValidationException, NotFoundException {
        String sql = "update users set email = ?, login = ?, name = ?, birthday = ? where user_id = ?";
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        return user;
    }

    private User MapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(resultSet.getInt("user_id"),
                        resultSet.getString("email"),
                        resultSet.getString("login"),
                        resultSet.getDate("birthday").toLocalDate());
    }
}
