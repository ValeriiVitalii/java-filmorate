package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<Film> findAll() {
        String sql = "select FILM_ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID FROM FILM";
        return jdbcTemplate.query(sql, this::mapRowToFilm);
    }


    @Override
    public Film create(Film film) throws ValidationException {
        validation(film);
        String sql = "insert into film(name, description, release_date, duration, MPA_ID) " +
                "values (?, ?, ?, ?, " +
                "(SELECT MPA_ID from MPA AS m where m.MPA_ID = " + film.getRating() + "))";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration());
        return getLastFilm();
    }

    public Film getLastFilm () throws ValidationException {
        String sql = "SELECT * FROM FILM WHERE FILM_ID =(SELECT max(FILM_ID) FROM FILM)";
        return jdbcTemplate.queryForObject(sql, this::mapRowToFilm);
    }
    @Override
    public Film edit(Film film) throws Throwable {
        getFilm(film.getId());
        String sql = "update film set name = ?, description = ?, RELEASE_DATE = ?, duration = ?, MPA_ID = " + 1 +
                " where FILM_ID = ?";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getId());
        return film;
    }

    @Override
    public Film getFilm(int id) throws NotFoundException {
        String sql = "select * FROM FILM where film_id = ?";
             //   "from film AS f inner join RATING AS r ON f.RATING_ID = r.RATING_ID where f.film_id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToFilm, id);
    }

    @Override
    public List<Set<Genres>> getGenres() {
        String sql = "select g.GENRE_NAME from FILM AS f inner join GENRE_FILM AS gf ON f.FILM_ID = gf.FILM_ID " +
                "inner join genre AS g ON gf.GENRE_ID = g.GENRE_ID";
        List<Film> film = jdbcTemplate.query(sql, this::mapRowToFilm);
        List<Set<Genres>> genres = new ArrayList<>();
        for (Film film1 : film) {
            genres.add(film1.getGenre());
        }
            return genres;
    }

    @Override
    public Set<Genres> getGenres(int id) {
        String sql = "select g.GENRE_NAME from FILM AS f inner join GENRE_FILM AS gf ON f.FILM_ID = gf.FILM_ID " +
                "inner join genre AS g ON gf.GENRE_ID = g.GENRE_ID WHERE f.FILM_ID = ?";
        Film film = jdbcTemplate.queryForObject(sql, this::mapRowToFilm, id);
        return film.getGenre();
    }

    @Override
    public List<Rating> getMpa() {
        /*String sql = "select f.film_id, r.name from rating AS r inner join film AS f ON r.rating_id = f.rating_id";
        List<Film> film = jdbcTemplate.query(sql, this::mapRowToFilm);
        List<Mpa> mpa = new ArrayList<>();
        for (Film film1 : film) {
            mpa.add(film1.getRating());
        }*/
        List<Rating> mpa = new ArrayList<>();  //удалить!!!!!!!!!
        return mpa;
    }

    @Override
    public Rating getMpa(int id) {
        /*String sql = "select f.FILM_ID, f.NAME, f.DESCRIPTION, f.RELEASE_DATE, f.DURATION, f.RATING_ID " +
                "FROM RATING AS r join FILM AS f ON r.RATING_ID = f.RATING_ID where r.RATING_ID = ?";
        Film film = jdbcTemplate.queryForObject(sql, this::mapRowToFilm, id);
        return film.getRating();*/
        return new Rating(1);  //удалить!!!!!!
    }

    public Film validation(Film film) throws ValidationException {
        final LocalDate MIN_BIRTH_FILM = LocalDate.of(1895, 12, 28);
        final long MIN_DURATION_FILM = 0;
        if (film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым");
        } if(film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        } if(film.getReleaseDate().isBefore(MIN_BIRTH_FILM)) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        } if(film.getDuration() <= MIN_DURATION_FILM) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        return film;
    }
    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film(resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDate("RELEASE_DATE").toLocalDate(),
                resultSet.getLong("duration"),
                // Mpa.valueOf(resultSet.getString("f.NAME")));
                1);
        film.setId(resultSet.getInt("film_id"));
        return film;
    }

    /*public Mpa GetMpaByCode(int number){
        switch (number) {
            case 1: return Mpa.G;
            case 33: return Mpa.PG;
            case 34: return Mpa.PG_13;
            case 35: return Mpa.R;
            case 36: return Mpa.NC_17;
        }
        return null;
    }*/

}
