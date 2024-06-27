package edu.hit.testsheet.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @ExceptionHandler(ExamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleExamNotFoundException(ExamNotFoundException ex) {
        return createErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(AnswerRecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleAnswerRecordNotFoundException(AnswerRecordNotFoundException ex) {
        return createErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidExamStartTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidExamTimeException(InvalidExamStartTimeException ex) {
        return createErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(QuestionCanNotBeDeletedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleQuestionCanNotBeDeletedException(QuestionCanNotBeDeletedException ex) {
        return createErrorResponse(ex.getMessage());
    }

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("success", "error");
        errorMap.put("error", message);
        return errorMap;
    }
}
