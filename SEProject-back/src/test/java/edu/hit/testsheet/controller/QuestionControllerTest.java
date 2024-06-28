package edu.hit.testsheet.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hit.testsheet.dto.QuestionUpdateDto;
import edu.hit.testsheet.TestsheetApplication;
import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.service.QuestionService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * ClassName:QuestionControllerTest
 * Package:edu.hit.testsheet
 * Description:
 *
 * @date:2024/6/3 16:57
 * @author:shyboy
 */



@ActiveProfiles(value = "test")
@SpringBootTest(classes = TestsheetApplication.class)
@WebAppConfiguration

public class QuestionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllQuestions() throws Exception {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            questions.add(new Question());
        }
        when(questionService.getQuestionsByPage(0, 10)).thenReturn(questions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(7));
    }

    @Test
    public void testAddQuestion() throws Exception {
        Question question = new Question();
        question.setId(8L);
        question.setDescription("What is the capital of America?");

        when(questionService.addQuestion(any(Question.class))).thenReturn(question);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.description").value("What is the capital of America?"));
    }

    @Test
    public void testDeleteQuestionById() throws Exception {
        doNothing().when(questionService).deleteQuestionById(7L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/questions/7"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetQuestionById() throws Exception {
        Question question = new Question();
        question.setId(1L);
        question.setDescription("What is the capital of China?");

        when(questionService.selectQuestionById(1L)).thenReturn(question);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/questions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("What is the capital of China?"));
    }

    @Test
    public void testUpdateQuestion() throws Exception {
        Question updatedQuestion = new Question();
        updatedQuestion.setId(7L);
        updatedQuestion.setDescription("Updated content");

        when(questionService.updateQuestion(eq(7L), any(QuestionUpdateDto.class))).thenReturn(updatedQuestion);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/questions/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Updated content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.description").value("Updated content"));
    }

    @Test
    public void testSearchQuestions() throws Exception {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question());
        questions.get(0).setId(1L);
        questions.get(0).setDescription("Question 1");
        questions.add(new Question());
        questions.get(1).setId(2L);
        questions.get(1).setDescription("Question 2");

        when(questionService.selectQuestion("keyword", "type", "difficultLevel", "username", 0, 10,null,null))
                .thenReturn(questions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/questions/search?pageNum=1")
                        .param("keywords", "keyword")
                        .param("type", "type")
                        .param("difficultLevel", "difficultLevel")
                        .param("username", "username")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("Question 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].description").value("Question 2"));
    }

    @Test
    public void testGetQuestionsTotalPagesNum() throws Exception {
        long totalQuestions = 27L;

        when(questionService.getQuestionsCount()).thenReturn(totalQuestions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/questions/pageNum")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(27));
    }
}

