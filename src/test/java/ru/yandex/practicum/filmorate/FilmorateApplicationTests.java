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
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;

import java.time.LocalDate;
import java.util.Collection;


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
		userStorage.create(user);
		userStorage.create(user2);
		userStorage.create(user3);
		userStorage.addFriend(1, 2);
		userStorage.addFriend(1, 3);
		userStorage.addFriend(2, 3);
		Collection<User> users = userStorage.getMutualFriends(1, 2);
		AssertionsForClassTypes.assertThat(users.size()).isEqualTo(1);
	}
	/*@Test
	public void testGetAndCreateUser() throws NotFoundException, ValidationException {
		User user = new User(1, "ddima@mail.ru", "dimas2211", "Дмитрий", LocalDate.now());
		userStorage.create(user);
		User user1 = userStorage.getUser(1);
		AssertionsForClassTypes.assertThat(user1).hasFieldOrPropertyWithValue("id", 1);
	}

	@Test
	public void testGetAllUsers() throws NotFoundException, ValidationException {
		User user = new User(1, "ddima@mail.ru", "dimas2211", "Дмитрий", LocalDate.now());
		User user2 = new User(2, "2dd@mail.ru", "valval77", "Валерий777", LocalDate.now());
		User user3 = new User(3, "sashsasash", "ssaasha222", "Саша", LocalDate.now());
		userStorage.create(user);
		userStorage.create(user2);
		userStorage.create(user3);
		List<User> users = userStorage.getAllUsers();
		AssertionsForClassTypes.assertThat(users.size()).isEqualTo(4);
	}

	@Test
	public void testEditUser() throws NotFoundException, ValidationException {
		User user = new User(1, "2dd@mail.ru", "valval77", "Валерий777", LocalDate.now());
		userStorage.create(user);
		User user2 = new User(1, "2dd@mail.ru", "valval77", "Fghfdfdf", LocalDate.now());
		userStorage.edit(user2);
		User user3 = userStorage.getUser(1);
		AssertionsForClassTypes.assertThat(user3).hasFieldOrPropertyWithValue("name", "Fghfdfdf");

		User user9999 = new User(9999, "2dd@mail.ru", "valval773333", "ATattat", LocalDate.now());
		User userTest = userStorage.edit(user9999);
	//	User user4 = userStorage.getUser(1);
	//	AssertionsForClassTypes.assertThat(user4).hasFieldOrPropertyWithValue("name", "Fghfdfdf");
	}*/

	@Test
	public void testGetAndCreateFilm() throws ValidationException, NotFoundException {
		Film film = new Film("Гарри Поттер", "Magic", LocalDate.now(), 90, new Rating(1));
		filmDbStorage.create(film);
		/*Film film1 = filmDbStorage.getFilm(1);
		AssertionsForClassTypes.assertThat(film1).hasFieldOrPropertyWithValue("id", 1);
		AssertionsForClassTypes.assertThat(film1).hasFieldOrPropertyWithValue("mpa", 1);*/
	}

	/*@Test
	public void testFindAllFilm() throws ValidationException {
		Film film = new Film(1, "Гарри Поттер", "Magic", LocalDate.now(), 90, Mpa.G);
		Film film2 = new Film(2, "Один дома", "Новый год", LocalDate.now(), 90, Mpa.PG);
		Film film3 = new Film(3, "Титаник",
				"Корабельные истории",LocalDate.now(), 93, Mpa.NC_17);
		filmDbStorage.create(film);
		filmDbStorage.create(film2);
		filmDbStorage.create(film3);
		List<Film> films = filmDbStorage.findAll();
		AssertionsForClassTypes.assertThat(films.size()).isEqualTo(3);
	}

	@Test
	public void testEditFilm() throws Throwable {
		Film film = new Film(1, "Гарри Поттер", "Magic", LocalDate.now(), 90, Mpa.G);
		filmDbStorage.create(film);
		Film film2 = new Film(1, "Потный Гарри", "Magic", LocalDate.now(), 90, Mpa.G);
		filmDbStorage.edit(film2);
	 	Film film3 = filmDbStorage.getFilm(1);
		AssertionsForClassTypes.assertThat(film3).hasFieldOrPropertyWithValue("name", "Потный Гарри");
	}

	@Test
	public void testGetMpa() throws ValidationException {
		Film film = new Film(1, "Гарри Поттер", "Magic", LocalDate.now(), 90, Mpa.G);
		filmDbStorage.create(film);
		Mpa mpa = filmDbStorage.getMpa(1);

	}*/
}
