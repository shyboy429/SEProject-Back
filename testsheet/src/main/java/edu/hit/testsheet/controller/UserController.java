package edu.hit.testsheet.controller;

import edu.hit.testsheet.Dto.LoginDto;
import edu.hit.testsheet.Exception.UserAlreadyExistsException;
import edu.hit.testsheet.Exception.UserLoginFailedException;
import edu.hit.testsheet.bean.User;
import edu.hit.testsheet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:UserController
 * Package:edu.hit.testsheet.controller
 * Description:
 *
 * @date:2024/6/3 15:40
 * @author:shyboy
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // 用户登录
    @PostMapping("/login")
    public User userLogin(@RequestBody LoginDto loginDto) {
        User user = userService.userLogin(loginDto.getUsername(), loginDto.getPassword());
        if (user == null) {
            throw new UserLoginFailedException("Invalid username or password");
        }
        return user;
    }

    // 用户注册
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // 获取所有用户
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    // 根据ID获取用户
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.selectUserById(id);
    }

    // 根据用户名获取用户
    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.selectUserByUsername(username);
    }

    // 添加用户
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // 根据ID删除用户
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    // 根据用户名删除用户
    @DeleteMapping("/username/{username}")
    public void deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
    }

    // 更新用户
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String password) {
        return userService.updateUser(id, username, password);
    }
}
