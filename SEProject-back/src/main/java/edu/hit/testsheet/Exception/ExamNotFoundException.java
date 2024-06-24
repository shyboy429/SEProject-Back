package edu.hit.testsheet.Exception;

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
        super("Paper not found with id: " + id);
    }
    public ExamNotFoundException(String name) {
        super("Paper not found with name: " + name);
    }
}
