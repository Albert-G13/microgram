package kg.attractor.microgram.exception;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {
    public UserNotFoundException(){super("Пользователь не найден");}
}
