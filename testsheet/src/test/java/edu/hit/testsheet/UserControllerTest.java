package edu.hit.testsheet;

/**
 * ClassName:UserControllerTest
 * Package:edu.hit.testsheet
 * Description:
 *
 * @date:2024/6/3 17:46
 * @author:shyboy
 */
import edu.hit.testsheet.Exception.UserNotFoundException;
import edu.hit.testsheet.bean.User;
import edu.hit.testsheet.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(value = "test")
@SpringBootTest(classes = TestsheetApplication.class)
@WebAppConfiguration
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Prepare mock data
        when(userService.getAllUser()).thenReturn(new ArrayList<>());

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetUserById() throws Exception {
        // Prepare mock data
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("123");
        when(userService.selectUserById(1L)).thenReturn(user);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.password").value("123"));
    }

    @Test
    public void testGetUserByUsername() throws Exception {
        // Prepare mock data
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("123");
        when(userService.selectUserByUsername("testuser")).thenReturn(user);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/username/testuser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.password").value("123"));
    }

    @Test
    public void testAddUser() throws Exception {
        // Prepare mock data
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("123");
        when(userService.addUser(any(User.class))).thenReturn(user);

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.password").value("123"));
    }

    @Test
    public void testDeleteUserById() throws Exception {
        // Perform DELETE request
        doNothing().when(userService).deleteUserById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserByUsername() throws Exception {
        // Perform DELETE request
        doNothing().when(userService).deleteUserByUsername("testuser");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/username/testuser"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Prepare mock data
        User user = new User();
        user.setId(1L);
        user.setUsername("olduser");
        user.setPassword("123");
        when(userService.updateUser(eq(1L), anyString(), anyString())).thenReturn(user);
        // Perform PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", "newusername")
                        .param("password", "newpassword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("olduser"));
    }
}

