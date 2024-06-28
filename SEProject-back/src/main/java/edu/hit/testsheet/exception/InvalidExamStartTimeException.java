package edu.hit.testsheet.exception;

/**
 * ClassName:InvalidExamStartTimeException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/25 20:21
 * @author:shyboy
 */
public class InvalidExamStartTimeException extends RuntimeException {
    public InvalidExamStartTimeException(String message) {
        super(message);
    }
}