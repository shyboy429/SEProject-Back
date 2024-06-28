//package edu.hit.testsheet;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import edu.hit.testsheet.bean.User;
//import edu.hit.testsheet.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * ClassName:UserControllerTest
// * Package:edu.hit.testsheet
// * Description:
// *
// * @date:2024/6/3 17:46
// * @author:shyboy
// */
//
//
//@ActiveProfiles(value = "test")
//@SpringBootTest(classes = TestsheetApplication.class)
//@WebAppConfiguration
//
//public class UserControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @MockBean
//    private UserService userService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setUp() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    @Test
//    public void testUserLogin() throws Exception {
//        User user = new User();
//        user.setId(1L);
//        user.setUsername("username");
//        user.setPassword("password");
//
//        when(userService.userLogin("username", "password")).thenReturn(user);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"username\":\"username\",\"password\":\"password\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.username").value("username"))
//                .andExpect(jsonPath("$.password").value("password"));
//    }
//
//    @Test
//    public void testRegisterUser() throws Exception {
//        User user = new User();
//        user.setId(1L);
//        user.setUsername("username");
//        user.setPassword("password");
//        user.setRole(User.Role.ADMIN);
//
//        when(userService.registerUser(Mockito.any(User.class))).thenReturn(user);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.username").value("username"))
//                .andExpect(jsonPath("$.password").value("password"));
//    }
//
//    @Test
//    public void testGetAllUsers() throws Exception {
//        List<User> users = new ArrayList<>();
//        users.add(new User());
//        users.add(new User());
//        users.get(0).setId(1L);
//        users.get(0).setUsername("username1");
//        users.get(0).setPassword("password1");
//        users.get(1).setId(2L);
//        users.get(1).setUsername("username2");
//        users.get(1).setPassword("password2");
//
//        when(userService.getAllUser()).thenReturn(users);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(1L))
//                .andExpect(jsonPath("$[0].username").value("username1"))
//                .andExpect(jsonPath("$[0].password").value("password1"))
//                .andExpect(jsonPath("$[1].id").value(2L))
//                .andExpect(jsonPath("$[1].username").value("username2"))
//                .andExpect(jsonPath("$[1].password").value("password2"));
//    }
//
//    @Test
//    public void testUpdateUser() throws Exception {
//        User user = new User();
//        user.setUsername("username");
//        user.setPassword("password");
//        user.setRole(User.Role.STUDENT);
//
//        when(userService.updateUser("admin", "username", "STUDENT")).thenReturn(user);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/users")
//                        .param("adminName", "admin")
//                        .param("username", "username")
//                        .param("role", "STUDENT"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value("username"))
//                .andExpect(jsonPath("$.role").value("STUDENT"));
//    }
//}
//
