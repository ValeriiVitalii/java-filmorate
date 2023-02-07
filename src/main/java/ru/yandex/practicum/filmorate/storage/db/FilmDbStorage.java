package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Film> findAll() {
        String sql = "select * from film";
        return jdbcTemplate.query(sql, this::mapRowToFilm);
    }


    @Override
    public Film create(Film film) throws ValidationException {
        String sql = "insert into film(name, description, RELEASE_DATE, duration, rating_id) " +
                "values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getRating().name());
        return film;
    }

    @Override
    public Film edit(Film film) throws Throwable {
        String sql = "update film set name = ?, description = ?, RELEASE_DATE = ?, duration = ?, rating_id = ?" +
                "where FILM_ID = ?";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getRating().name());
        return film;
    }

    @Override
    public Film getFilm(Long id) throws NotFoundException {
        String sql = "select * from film where film_id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToFilm, id);
    }

    @Override
    public List<Set<Genres>> getGenres() {
        String sql = "select film_id, name from film_genre";
        List<Film> film = jdbcTemplate.query(sql, this::mapRowToFilm);
        List<Set<Genres>> genres = new ArrayList<>();
        for (Film film1 : film) {
            genres.add(film1.getGenre());
        }
            return genres;
    }

    @Override
    public Set<Genres> getGenres(Long id) {
        String sql = "select film_id, name from film_genre where FILM_ID = ?";
        Film film = jdbcTemplate.queryForObject(sql, this::mapRowToFilm, id);
        return film.getGenre();
    }

    @Override
    public List<Mpa> getMpa() {
        String sql = "select f.film_id, r.name from rating AS r inner join film AS f ON r.rating_id = f.rating_id";
        List<Film> film = jdbcTemplate.query(sql, this::mapRowToFilm);
        List<Mpa> mpa = new ArrayList<>();
        for (Film film1 : film) {
            mpa.add(film1.getRating());
        }
        return mpa;
    }

    @Override
    public Mpa getMpa(int id) {
        String sql = "select f.film_id, r.name from rating AS r inner join film AS f ON r.rating_id = f.rating_id " +
                "where f.rating_id = ?";
        Film film = jdbcTemplate.queryForObject(sql, this::mapRowToFilm, id);
        return film.getRating();
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        // This method is an implementation of the functional interface RowMapper.
        // It is used to map each row of a ResultSet to an object.
        return new Film(resultSet.getLong("film_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDate("RELEASE_DATE").toLocalDate(),
                        resultSet.getLong("duration"));
    }
}
