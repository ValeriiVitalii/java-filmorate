package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmoRateApplicationTests {
	private final UserDbStorage userStorage;
	private final FilmDbStorage filmDbStorage;

	@Test
	public void TestGetMutualFriends() throws NotFoundException, ValidationException {
		User user = new User("ddima@mail.ru", "dimas2211", "Дмитрий", LocalDate.now());
		User user2 = new User("2dd@mail.ru", "valval77", "Валерий777", LocalDate.now());
		User user3 = new User("sashs@asash", "ssaasha222", "Саша", LocalDate.now());
		userStorage.createUser(user);
		userStorage.createUser(user2);
		userStorage.createUser(user3);
		userStorage.addFriend(1, 2);
		userStorage.addFriend(1, 3);
		userStorage.addFriend(2, 3);
		Collection<User> users = userStorage.getMutualFriends(1, 2);
		AssertionsForClassTypes.assertThat(users.size()).isEqualTo(1);
	}
	@Test
	public void testGetAndCreateUser() throws NotFoundException, ValidationException {
		User user = new User("ddima@mail.ru", "dimas2211", "Дмитрий", LocalDate.now());
		userStorage.createUser(user);
		User user1 = userStorage.getUser(1);
		AssertionsForClassTypes.assertThat(user1).hasFieldOrPropertyWithValue("id", 1);
	}

	@Test
	public void testGetAllUsers() throws NotFoundException, ValidationException {
		User user = new User("ddima@mail.ru", "dimas2211", "Дмитрий", LocalDate.now());
		User user2 = new User("2dd@mail.ru", "valval77", "Валерий777", LocalDate.now());
		User user3 = new User("sas@hsasash", "ssaasha222", "Саша", LocalDate.now());
		userStorage.createUser(user);
		userStorage.createUser(user2);
		userStorage.createUser(user3);
		List<User> users = userStorage.getAllUsers();
		AssertionsForClassTypes.assertThat(users.size()).isEqualTo(3);
	}

	@Test
	public void testAddAndGetAndRemoveFriends() throws NotFoundException, ValidationException {
		User user = new User("ddima@mail.ru", "dimas2211", "Дмитрий", LocalDate.now());
		User user2 = new User("2dd@mail.ru", "valval77", "Валерий777", LocalDate.now());
		User user3 = new User("sas@hsasash", "ssaasha222", "Саша", LocalDate.now());
		userStorage.createUser(user);
		userStorage.createUser(user2);
		userStorage.createUser(user3);
		userStorage.addFriend(1, 2);

		Collection<User> friends = userStorage.getFriends(1);
		AssertionsForClassTypes.assertThat(friends.size()).isEqualTo(1);
	}

	@Test
	public void testEditUser() throws NotFoundException, ValidationException {
		User user = new User("2dd@mail.ru", "valval77", "Валерий777", LocalDate.now());
		userStorage.createUser(user);
		User user2 = new User("2dd@mail.ru", "valval77", "Fghfdfdf", LocalDate.now());
		user2.setId(1);
		userStorage.editUser(user2);
		User user3 = userStorage.getUser(1);
		AssertionsForClassTypes.assertThat(user3).hasFieldOrPropertyWithValue("name", "Fghfdfdf");

	}

	@Test
	public void testGetAndCreateFilm() throws ValidationException, NotFoundException {
		Film film = new Film("Гарри Поттер", "Magic", LocalDate.now(), 90, new Rating());
		Film film2 = new Film("Гарри Поттер", "Magic", LocalDate.now(), 90, new Rating());
		film.getMpa().setId(1);
		filmDbStorage.createFilm(film);
		Film film1 = filmDbStorage.getFilm(1);
		AssertionsForClassTypes.assertThat(film1).hasFieldOrPropertyWithValue("name", "Гарри Поттер");
	}

	@Test
	public void testFindAllFilm() throws ValidationException {
		Film film = new Film("Гарри Поттер", "Magic", LocalDate.now(), 90, new Rating());
		Film film2 = new Film("Один дома", "Новый год", LocalDate.now(), 90, new Rating());
		Film film3 = new Film("Титаник",
				"Корабельные истории",LocalDate.now(), 93, new Rating());
		film.getMpa().setId(1);
		film2.getMpa().setId(1);
		film3.getMpa().setId(1);
		filmDbStorage.createFilm(film);
		filmDbStorage.createFilm(film2);
		filmDbStorage.createFilm(film3);
		List<Film> films = filmDbStorage.getAllFilms();
		AssertionsForClassTypes.assertThat(films.size()).isEqualTo(3);
	}

	@Test
	public void testEditFilm() throws Throwable {
		Film film = new Film("Гарри Поттер", "Magic", LocalDate.now(), 90, new Rating());
		film.getMpa().setId(1);
		filmDbStorage.createFilm(film);
		Film film2 = new Film("Потный Гарри", "Magic", LocalDate.now(), 90, new Rating());
		film2.getMpa().setId(1);
		film2.setId(1);
		filmDbStorage.editFilm(film2);
	 	Film film3 = filmDbStorage.getFilm(1);
		AssertionsForClassTypes.assertThat(film3).hasFieldOrPropertyWithValue("name", "Потный Гарри");
	}

	@Test
	public void testGetMpaAndGetAllMpa() throws ValidationException {
		Rating rating = filmDbStorage.getMpa(1);
		AssertionsForClassTypes.assertThat(rating).hasFieldOrPropertyWithValue("id", 1);
		List<Rating> ratings = filmDbStorage.getMpa();
		AssertionsForClassTypes.assertThat(ratings.size()).isEqualTo(5);
	}

	@Test
	public void testGetGenreAndGetAllGenres() {
		Genres genre = filmDbStorage.getGenres(1);
		AssertionsForClassTypes.assertThat(genre).hasFieldOrPropertyWithValue("id", 1);
		Collection<Genres> genres = filmDbStorage.getGenres();
		AssertionsForClassTypes.assertThat(genres.size()).isEqualTo(6);
	}

	@Test
	public void testAddLikeAndRemoveAndGetPopularFilm() throws ValidationException, NotFoundException {
		Film film = new Film("Гарри Поттер", "Magic", LocalDate.now(), 90, new Rating());
		Film film2 = new Film("Один дома", "Новый год", LocalDate.now(), 90, new Rating());
		Film film3 = new Film("Титаник",
				"Корабельные истории",LocalDate.now(), 93, new Rating());
		film.getMpa().setId(1);
		film2.getMpa().setId(1);
		film3.getMpa().setId(1);
		filmDbStorage.createFilm(film);
		filmDbStorage.createFilm(film2);
		filmDbStorage.createFilm(film3);

		User user = new User("ddima@mail.ru", "dimas2211", "Дмитрий", LocalDate.now());
		userStorage.createUser(user);

		filmDbStorage.addLike(3, 1);
		filmDbStorage.removeLike(3, 1);
		Collection<Film> films = filmDbStorage.getPopularFilm(10);
		AssertionsForClassTypes.assertThat(films.size()).isEqualTo(3);
	}
}
