package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.db.UserDbStorage;



@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmoRateApplicationTests {
	private final UserDbStorage userStorage;
	private final FilmDbStorage filmDbStorage;

	/*@Test
	public void testFindUserById() {

		Optional<User> userOptional = userStorage.findUserById(1);

		AssertionsForClassTypes.assertThat(userOptional)
				.isPresent()
				.hasValueSatisfying(user ->
						AssertionsForClassTypes.assertThat(user).hasFieldOrPropertyWithValue("id", 1)
				);
	}

	@Test
	public void testGetAllUsers() {
		Collection<ru.yandex.practicum.filmorate.model.User> users = userStorage.getAllUsers();

		AssertionsForClassTypes.assertThat(users).hasFieldOrPropertyWithValue("")
				);
	}*/

	@Test
	public void testGetUser() throws NotFoundException {
		long id = 1;
		User user = userStorage.getUser(id);

		AssertionsForClassTypes.assertThat(user).hasFieldOrPropertyWithValue("id", 1);
	}



}
