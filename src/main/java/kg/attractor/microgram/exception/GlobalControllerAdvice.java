package kg.attractor.microgram.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public String notFound(Model model, HttpServletRequest request){
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(PublicationNotFoundException.class)
    public String publicationNotFound(Model model){
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        return "errors/error";
    }
    @ExceptionHandler(UserNotFoundException.class)
    public String UserNotFound(Model model){
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        return "errors/error";
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public String RoleNotFound(Model model){
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        return "errors/error";
    }
    @ExceptionHandler(UserNotAuthorizedException.class)
    public String userNotAuthorized(Model model){
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", HttpStatus.FORBIDDEN.getReasonPhrase());
        return "errors/error";
    }
}
