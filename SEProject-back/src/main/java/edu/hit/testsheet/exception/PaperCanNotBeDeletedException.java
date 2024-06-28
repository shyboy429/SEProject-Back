package edu.hit.testsheet.exception;

/**
 * ClassName:PaperCanNotBeDeletedException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/27 21:47
 * @author:shyboy
 */
public class PaperCanNotBeDeletedException extends RuntimeException{
    public PaperCanNotBeDeletedException(String message){
        super(message);
    }
}
