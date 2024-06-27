package edu.hit.testsheet.Exception;

/**
 * ClassName:QuestionCanNotBeDeleteException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/27 20:18
 * @author:shyboy
 */
public class QuestionCanNotBeDeleteException extends RuntimeException{
    public QuestionCanNotBeDeleteException(Long id,String title) {
        super("Question could not be deleted with id: " + id + ", because it is in the paper: " + title);
    }
}
