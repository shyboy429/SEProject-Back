package edu.hit.testsheet.exception;

/**
 * ClassName:QuestionCanNotBeDeleteException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/27 20:18
 * @author:shyboy
 */
public class QuestionCanNotBeDeletedException extends RuntimeException{
    public QuestionCanNotBeDeletedException(Long id,String title) {
        super("Question could not be deleted with id: " + id + ", because it is in the paper: " + title);
    }
}
