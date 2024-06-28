package edu.hit.testsheet.exception;

/**
 * ClassName:ExamCanNotBeDeletedException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/27 21:32
 * @author:shyboy
 */
public class ExamCanNotBeDeletedException extends RuntimeException{
    public ExamCanNotBeDeletedException(String message){
        super(message);
    }
}
