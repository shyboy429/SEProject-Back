package edu.hit.testsheet.impl;

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
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
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
    public void deleteUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new UserNotFoundException(username);
        }
    }

    @Override
    public User selectUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User selectUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
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
