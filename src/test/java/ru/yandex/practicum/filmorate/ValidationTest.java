package ru.yandex.practicum.filmorate;

public class ValidationTest {

   /* FilmController filmController = new FilmController();
    UserController userController = new UserController();

    @Test
    void validateFilmTest() throws ValidationException {
        Film filmTest1 = new Film("", "Пройти квест",
                LocalDate.of(2000, 11, 11), 60);
        assertThrows(ValidationException.class, () -> filmController.create(filmTest1));
        Film filmTest2 = new Film("W", "Пройти квест",
                LocalDate.of(2000, 11, 11), 60);
        filmController.create(filmTest2);
        assertEquals(filmTest2.getName(), "W");

        String description = "вввввввввввввввввввввввввввввввввввввввввввввввввв";  // 50 символов
        description = description + description + description + description; // 200 символов
        Film filmTest3 = new Film("Кошмары на улице Java", description,
                LocalDate.of(2000, 11, 11), 60);
        filmController.create(filmTest3);
        assertEquals(filmTest3.getDescription(), description);
        Film filmTest4 = new Film("Кошмары на улице Java", description + "1",
                LocalDate.of(2000, 11, 11), 60);
        assertThrows(ValidationException.class, () -> filmController.create(filmTest4));

        Film filmTest5 = new Film("Кошмары на улице Java", "Пройти квест",
                LocalDate.of(1895, 12, 28), 60);
        filmController.create(filmTest5);
        Film filmTest6 = new Film("Кошмары на улице Java", "Пройти квест",
                LocalDate.of(1895, 12, 27), 60);
        assertThrows(ValidationException.class, () -> filmController.create(filmTest6));

        Film filmTest7 = new Film("Кошмары на улице Java", "Пройти квест",
                LocalDate.of(2000, 11, 11), 1);
        filmController.create(filmTest7);
        Film filmTest8 = new Film("Кошмары на улице Java", "Пройти квест",
                LocalDate.of(2000, 11, 11), 0);
        assertThrows(ValidationException.class, () -> filmController.create(filmTest8));
    }

    @Test
    void validateUserTest() throws ValidationException {
        User userTest1 = new User("valval@mail.ru", "Login",
                LocalDate.of(2000, 10, 13));
        userController.create(userTest1);
        assertEquals(userTest1.getName(), userTest1.getLogin());

        User userTest2 = new User("", "Login",
                LocalDate.of(2000, 10, 13));
        assertThrows(ValidationException.class, () -> userController.create(userTest2));

        User userTest3 = new User("valval@mail.ru", "L",
                LocalDate.of(2000, 10, 13));
        userController.create(userTest1);
        User userTest4 = new User("valval@mail.ru", "L gol",
                LocalDate.of(2000, 10, 13));
        assertThrows(ValidationException.class, () -> userController.create(userTest4));

        User userTest5 = new User("valval@mail.ru", "Login",
                LocalDate.now().minusDays(1));
        userController.create(userTest5);
        User userTest6 = new User("valval@mail.ru", "Login",
                LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, () -> userController.create(userTest6));
    }*/
}
