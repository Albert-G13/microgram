package kg.attractor.microgram.exception;

public class UserNotAuthorizedException extends IllegalArgumentException {
    public UserNotAuthorizedException(){super("Пользователь не авторизован");}
}
