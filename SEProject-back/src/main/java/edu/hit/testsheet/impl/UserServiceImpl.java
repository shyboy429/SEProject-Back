package edu.hit.testsheet.impl;

import edu.hit.testsheet.Exception.UserAlreadyExistsException;
import edu.hit.testsheet.Exception.UserLoginFailedException;
import edu.hit.testsheet.Exception.UserNotFoundException;
import edu.hit.testsheet.bean.User;
import edu.hit.testsheet.repository.UserRepository;
import edu.hit.testsheet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            throw new UserAlreadyExistsException("该用户名: \"" + user.getUsername()+"\" 已经存在!");
        }
        // 保存新用户
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public User selectUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User updateUser(Long id, String username, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }
}
