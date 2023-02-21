package ru.yandex.practicum.filmorate.exception;

public class UserDoesNotExistException extends Exception {

    public UserDoesNotExistException(final String message) {
        super(message);
    }
}
