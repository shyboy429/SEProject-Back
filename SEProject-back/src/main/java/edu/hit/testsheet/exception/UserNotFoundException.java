package edu.hit.testsheet.exception;

/**
 * ClassName:UserNotFoundExeption
 * Package:edu.hit.testsheet.Exception
 * Description:
 *
 * @date:2024/6/3 11:35
 * @author:shyboy
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User not found with username: " + username);
    }
}

