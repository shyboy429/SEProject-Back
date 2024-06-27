package edu.hit.testsheet.controller;

import edu.hit.testsheet.Dto.LoginDto;
import edu.hit.testsheet.Exception.UserLoginFailedException;
import edu.hit.testsheet.bean.User;
import edu.hit.testsheet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    /**
     * 用户登录
     * @param loginDto
     * @return 登录用户的详细信息
     */
    @PostMapping("/login")
    public User userLogin(@RequestBody LoginDto loginDto) {
        return userService.userLogin(loginDto.getUsername(), loginDto.getPassword());
    }

    /**
     * 用户注册
     * @param user
     * @return 用户注册的详细信息
     */
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    /**
     * 获取所有用户
     * @return 所有用户详细信息
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    /**
     * 根据 id获取用户
     * @param id
     * @return 对应用户的详细信息
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.selectUserById(id);
    }

    /**
     * 根据 id删除用户
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    /**
     * 根据 id更新用户信息
     * @param id
     * @param username
     * @param password
     * @return 更新后的用户信息
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,
                           @RequestParam(required = false) String username,
                           @RequestParam(required = false) String password) {
        return userService.updateUser(id, username, password);
    }
}
