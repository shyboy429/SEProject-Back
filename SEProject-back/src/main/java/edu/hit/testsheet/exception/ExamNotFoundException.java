package edu.hit.testsheet.exception;

/**
 * ClassName:ExamNotFoundException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/22 17:19
 * @author:shyboy
 */
public class ExamNotFoundException extends RuntimeException{
    public ExamNotFoundException(Long id) {
        super("Exam not found with id: " + id);
    }
}
