package edu.hit.testsheet.impl;

import edu.hit.testsheet.exception.ModifyOwnRoleException;
import edu.hit.testsheet.exception.UserAlreadyExistsException;
import edu.hit.testsheet.exception.UserLoginFailedException;
import edu.hit.testsheet.exception.UserNotFoundException;
import edu.hit.testsheet.bean.User;
import edu.hit.testsheet.repository.UserRepository;
import edu.hit.testsheet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:UserServiceImpl
 * Package:edu.hit.testsheet.impl
 * Description:
 *
 * @date:2024/6/3 11:34
 * @author:shyboy
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User userLogin(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password).orElse(null);
        if(user == null){
            throw new UserLoginFailedException("用户名或密码错误！");
        }
        return user;
    }

    @Override
    public User registerUser(User user) {
        // 检查用户名是否已存在
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("该用户名: \"" + user.getUsername()+"\" 已经存在！");
        }
        // 保存新用户
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(String adminName, String username, String role) {
        if(adminName.equals(username)){
            throw new ModifyOwnRoleException("不可修改自身权限！");
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        try {
            User.Role roleEnum = User.Role.valueOf(role.toUpperCase());
            user.setRole(roleEnum);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
        return userRepository.save(user);
    }
}
