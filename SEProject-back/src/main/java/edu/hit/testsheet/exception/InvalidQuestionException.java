package edu.hit.testsheet.exception;

/**
 * ClassName:InvalidQuestionException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/27 20:53
 * @author:shyboy
 */
public class InvalidQuestionException extends RuntimeException {
    public InvalidQuestionException(String message) {
        super(message);
    }
}

