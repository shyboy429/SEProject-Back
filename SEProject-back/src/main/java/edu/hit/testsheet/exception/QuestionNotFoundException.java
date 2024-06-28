package edu.hit.testsheet.exception;

/**
 * ClassName:QuestionNotFoundException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/3 11:25
 * @author:shyboy
 */

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) {
        super("Question not found with id: " + id);
    }
}
