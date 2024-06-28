package edu.hit.testsheet.Exception;

/**
 * ClassName:DurationExceedsExamTimeException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/28 12:20
 * @author:shyboy
 */
public class DurationExceedsExamTimeException extends RuntimeException{
    public DurationExceedsExamTimeException(String message){
        super(message);
    }
}
