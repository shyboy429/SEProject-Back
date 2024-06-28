package edu.hit.testsheet.service;

import edu.hit.testsheet.bean.User;

import java.util.List;

/**
 * ClassName:UserService
 * Package:edu.hit.testsheet.service
 * Description:
 *
 * @date:2024/6/3 11:29
 * @author:shyboy
 */
public interface UserService {

    User userLogin(String username, String password);

    User registerUser(User user);

    List<User> getAllUser();

    void deleteUserById(Long id);

    User selectUserById(Long id);

    User updateUser(Long id, String username, String role);
}
