package kg.attractor.microgram.exception;

import java.util.NoSuchElementException;

public class PublicationNotFoundException extends NoSuchElementException {
    public PublicationNotFoundException(){super("Публикация не найдена");}
}
