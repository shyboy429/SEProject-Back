package edu.hit.testsheet.Exception;

/**
 * ClassName:handleUserLoginFailedException
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/3 22:16
 * @author:shyboy
 */

public class UserLoginFailedException extends RuntimeException {
    public UserLoginFailedException(String message) {
        super(message);
    }
}

