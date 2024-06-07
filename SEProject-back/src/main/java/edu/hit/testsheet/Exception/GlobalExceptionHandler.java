package edu.hit.testsheet.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:GlobalExceptionHandler
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/4 16:58
 * @author:shyboy
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return createErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UserLoginFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleUserLoginFailedException(UserLoginFailedException ex) {
        return createErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFoundException(UserNotFoundException ex) {
        return createErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(PaperNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handlePaperNotFoundException(PaperNotFoundException ex) {
        return createErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleQuestionNotFoundException(QuestionNotFoundException ex) {
        return createErrorResponse(ex.getMessage());
    }

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return errorResponse;
    }
}
