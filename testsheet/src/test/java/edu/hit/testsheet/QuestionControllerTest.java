package edu.hit.testsheet;

/**
 * ClassName:QuestionControllerTest
 * Package:edu.hit.testsheet
 * Description:
 *
 * @date:2024/6/3 16:57
 * @author:shyboy
 */

import edu.hit.testsheet.Dto.QuestionUpdateDto;
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

@ActiveProfiles(value = "test")
@SpringBootTest(classes = TestsheetApplication.class)
@WebAppConfiguration

public class QuestionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    QuestionService questionService;

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
        when(questionService.getAllQuestions()).thenReturn(questions);
        // Perform GET request
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
                        .content("{\"content\":\"What is the capital of America?\"}"))
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
        QuestionUpdateDto updateDto = new QuestionUpdateDto();
        updateDto.setDescription("Updated content");

        Question updatedQuestion = new Question();
        updatedQuestion.setId(7L);
        updatedQuestion.setDescription("Updated content");

        when(questionService.updateQuestion(eq(7L), any(QuestionUpdateDto.class))).thenReturn(updatedQuestion);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/questions/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Updated content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.description").value("Updated content"));
    }
}

