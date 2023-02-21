package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.model.Rating;

import java.time.LocalDate;
import java.util.*;


@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    public Map<Integer, Film> films = new HashMap<>();
    private static final LocalDate MIN_BIRTH_FILM = LocalDate.of(1895, 12, 28);
    final static long MIN_DURATION_FILM = 0;
    private int id = 1;


    Comparator<Film> comparator = new Comparator<>() {
        @Override
        public int compare(Film o1, Film o2) {
            return o2.getLikes().size() - o1.getLikes().size();
        }
    };

    @Override
    public Film createFilm(Film film) throws ValidationException {
        validation(film);
        films.put(film.getId(),film);
        log.info("Добавлен новый объект Film " + film.toString());
        return film;
    }

    @Override
    public Film editFilm(Film film) throws Throwable {
        if (!films.containsKey(film.getId())) {
            throw new Throwable("Такого фильма несуществует");
        }
        validation(film);
        films.put(film.getId(), film);
        log.info("Добавлен или изменен новый объект Film " + film.toString());
        return film;
    }

    @Override
    public Film getFilm(int id) throws NotFoundException {
        if(!films.containsKey(id)) {
            throw new NotFoundException("Такого фильма нет");
        }
        return films.get(id);
    }

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @Override
    public Genres getGenres(int id) throws Throwable {
        if (!films.containsKey(films.get(id))) {
            throw new Throwable("Такого фильма несуществует");
        }
        return films.get(id).getGenres().get(1);
    }

    @Override
    public Collection<Genres> getGenres() {
        return films.get(id).getGenres();
    }

    @Override
    public Rating getMpa(int id) throws Throwable {
        return new Rating();
    }

    @Override
    public List<Rating> getMpa() {
        return new ArrayList<>();
    }

    @Override
    public int addLike(int id, int userId) throws NotFoundException {
        if (getFilm(id) == null || userId <= 0) {
            throw new NotFoundException("Такого фильма не существует");
        }
        Film film = getFilm(id);
        return film.addLike(userId);
    }

    public Collection<Film> getPopularFilm(int count) throws ValidationException {
        if (count <= 0) {
            throw new ValidationException("count должен быть больше нуля");
        }
        Set<Film> popularFilm = new HashSet<>();

        List<Film> films = new ArrayList<>(getAllFilms());
        Collections.reverse(films);
        for (Film f : films) {
            if (count > 0) {
                popularFilm.add(f);
                count--;
            }
        }
        return popularFilm;
    }

    public int removeLike(int id, int userId) throws NotFoundException {
        if (getFilm(id) == null || userId <= 0) {
            throw new NotFoundException("Такого фильма не существует");
        }
        return getFilm(id).removeLike(userId);
    }

    public Film validation(Film film) throws ValidationException {
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
}