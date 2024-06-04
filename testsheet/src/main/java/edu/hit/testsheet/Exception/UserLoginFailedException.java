package edu.hit.testsheet.Exception;

/**
 * ClassName:handleUserLoginFailedException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/3 22:16
 * @author:shyboy
 */
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class UserLoginFailedException extends RuntimeException {
    public UserLoginFailedException(String message) {
        super(message);
    }
}

@RestControllerAdvice
class UserExceptionHandler {
    @ExceptionHandler(UserLoginFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleUserLoginFailedException(UserLoginFailedException ex) {
    }
}

