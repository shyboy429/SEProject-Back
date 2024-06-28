package edu.hit.testsheet.exception;

/**
 * ClassName:UserAlreadyExistsException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/4 16:47
 * @author:shyboy
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
