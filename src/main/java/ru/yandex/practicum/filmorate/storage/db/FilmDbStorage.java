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
import java.util.Collection;
import java.util.List;

@Component
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Film createFilm(Film film) throws ValidationException {
        validation(film);
        String sql = "insert into film(name, description, release_date, duration, MPA_ID) " +
                "values (?, ?, ?, ?, (SELECT MPA.MPA_ID FROM MPA WHERE MPA_ID = " + film.getMpa().getId() + "))";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration() );

        Film lastFilm = getLastFilm();
        addGenres(lastFilm.getId(), film.getGenres());

        film.setId(lastFilm.getId());
        return film;
    }

    @Override
    public Film editFilm(Film film) throws Throwable {
        getFilm(film.getId());
        String sql = "update film set name = ?, description = ?, RELEASE_DATE = ?, duration = ?, MPA_ID = "
                + film.getMpa().getId() + " where FILM_ID = ?";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getId());

        removeGenres(film.getId());
        addGenres(film.getId(), film.getGenres());

        List<Genres> genres = getGenreFilm(film.getId());
        film.setGenres(genres);
        return film;
    }
    @Override
    public Film getFilm(int id) throws NotFoundException {
        String sql = "select * FROM FILM where film_id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToFilm, id);
    }

    public Film getLastFilm () throws ValidationException {
        String sql = "SELECT * FROM FILM WHERE FILM_ID =(SELECT max(FILM_ID) FROM FILM)";
        return jdbcTemplate.queryForObject(sql, this::mapRowToFilm);
    }

    @Override
    public List<Film> getAllFilms() {
        String sql = "select FILM_ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID FROM FILM";
        return jdbcTemplate.query(sql, this::mapRowToFilm);
    }

    public int addGenres(int filmId, Collection<Genres> genre) {
        for (Genres g : genre) {
            String sql = "insert into genre_film (film_id, genre_id) VALUES(" + filmId + ", " + g.getId() + ")";
            jdbcTemplate.update(sql);
        }
        return filmId;
    }

    public List<Genres> getGenreFilm(int idFilm) {
        String sql = "select distinct g.GENRE_ID, g.GENRE_NAME from GENRE_FILM as gf " +
                "inner join GENRE as g on gf.GENRE_ID = G.GENRE_ID where gf.FILM_ID = " + idFilm;
        return jdbcTemplate.query(sql, this::mapRowToGenre);
    }

    @Override
    public Genres getGenres(int idGenre) {
        String sql = "select * from GENRE where GENRE_ID = " + idGenre;
        return jdbcTemplate.queryForObject(sql, this::mapRowToGenre);
    }

    @Override
    public Collection<Genres> getGenres() {
        String sql = "select * from GENRE";
        return jdbcTemplate.query(sql, this::mapRowToGenre);
    }

    public int removeGenres(int filmId) {
        String sql = "delete from GENRE_FILM where FILM_ID = ?";
        jdbcTemplate.update(sql, filmId);
        return filmId;
    }

    @Override
    public Rating getMpa(int idMpa) {
        String sql = "select * From MPA where MPA_ID = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToRating, idMpa);
    }

    @Override
    public List<Rating> getMpa() {
        String sql = "select * From MPA ";
        return jdbcTemplate.query(sql, this::mapRowToRating);
    }

    @Override
    public int addLike(int filmId, int userId) throws NotFoundException {
        String sql = "insert into film_likes (film_id, user_id) VALUES(" + filmId + ", " + userId + ")";
        jdbcTemplate.update(sql);
        return filmId;
    }
    @Override
    public Collection<Film> getPopularFilm(int count) throws ValidationException {
        String sql = "Select * from FILM AS f LEFT JOIN FILM_LIKES FL on f.FILM_ID = FL.FILM_ID" +
                " GROUP BY f.FILM_ID order by count(fl.FILM_ID) desc LIMIT ?";
        return jdbcTemplate.query(sql, this::mapRowToFilm, count);
    }

    @Override
    public int removeLike(int idFilm, int userId) throws NotFoundException {
        if(idFilm < 1 || userId < 1) {
            throw new NotFoundException("Неверные данные");
        }
        String sql = "DELETE FROM FILM_LIKES WHERE FILM_ID = ? and USER_ID = ?";
        jdbcTemplate.update(sql, idFilm, userId);
        return idFilm;
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
                getMpa(resultSet.getInt("mpa_id")));

        film.setId(resultSet.getInt("film_id"));
        film.setGenres(getGenreFilm(film.getId()));
        return film;
    }

    private Genres mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        Genres genre = new Genres();
        genre.setId(resultSet.getInt("genre_id"));
        genre.setName(resultSet.getNString("genre_name"));
        return genre;
    }

    private Rating mapRowToRating(ResultSet resultSet, int rowNum) throws SQLException {
        Rating rating = new Rating();
        rating.setId(resultSet.getInt("mpa_id"));
        rating.setName(resultSet.getString("name"));
        return rating;
    }
}