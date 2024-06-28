package edu.hit.testsheet.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hit.testsheet.TestsheetApplication;
import edu.hit.testsheet.bean.User;
import edu.hit.testsheet.exception.ModifyOwnRoleException;
import edu.hit.testsheet.exception.UserAlreadyExistsException;
import edu.hit.testsheet.exception.UserLoginFailedException;
import edu.hit.testsheet.exception.UserNotFoundException;
import edu.hit.testsheet.repository.UserRepository;
import edu.hit.testsheet.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ActiveProfiles(value = "test")
@SpringBootTest(classes = TestsheetApplication.class)
@WebAppConfiguration
public class UserServiceImplTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testUserLoginSuccess() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setRole(User.Role.ADMIN);
        when(userRepository.findByUsernameAndPassword("testUser", "password")).thenReturn(Optional.of(user));

        User result = userService.userLogin("testUser", "password");

        assertEquals(user, result);
    }

    @Test
    public void testUserLoginFailure() {
        when(userRepository.findByUsernameAndPassword("testUser", "wrongPassword")).thenReturn(Optional.empty());

        assertThrows(UserLoginFailedException.class, () -> {
            userService.userLogin("testUser", "wrongPassword");
        });
    }

    @Test
    public void testRegisterUserSuccess() {
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("password");
        user.setRole(User.Role.ADMIN);
        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.registerUser(user);

        assertEquals(user, result);
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        User user = new User();
        user.setUsername("existingUser");
        user.setPassword("password");
        user.setRole(User.Role.ADMIN);
        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.registerUser(user);
        });
    }

    @Test
    public void testGetAllUser() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password");
        user1.setRole(User.Role.ADMIN);
        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password");
        user2.setRole(User.Role.ADMIN);
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUser();

        assertEquals(users, result);
    }

    @Test
    public void testUpdateUserSuccess() {
        User userToUpdate = new User();
        userToUpdate.setUsername("userToUpdate");
        userToUpdate.setPassword("password");
        userToUpdate.setRole(User.Role.ADMIN);
        when(userRepository.findByUsername("userToUpdate")).thenReturn(Optional.of(userToUpdate));
        when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);

        User result = userService.updateUser("admin", "userToUpdate", "ADMIN");

        assertEquals(User.Role.ADMIN, result.getRole());
    }

    @Test
    public void testUpdateUserModifyOwnRole() {
        assertThrows(ModifyOwnRoleException.class, () -> {
            userService.updateUser("admin", "admin", "USER");
        });
    }

    @Test
    public void testUpdateUserNotFound() {
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser("admin", "nonExistentUser", "ADMIN");
        });
    }

    @Test
    public void testUpdateUserInvalidRole() {
        User user = new User();
        user.setUsername("userToUpdate");
        user.setPassword("password");
        user.setRole(User.Role.ADMIN);
        when(userRepository.findByUsername("userToUpdate")).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser("admin", "userToUpdate", "INVALID_ROLE");
        });
    }
}
