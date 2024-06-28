package edu.hit.testsheet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hit.testsheet.TestsheetApplication;
import edu.hit.testsheet.bean.AnswerRecord;
import edu.hit.testsheet.service.AnswerRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName:AnswerRecordControllerTest
 * Package:edu.hit.testsheet
 * Description:
 *
 * @date:2024/6/28 14:07
 * @author:shyboy
 */
@ActiveProfiles(value = "test")
@SpringBootTest(classes = TestsheetApplication.class)
@WebAppConfiguration
public class AnswerRecordControllerTest {
    
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private AnswerRecordService answerRecordService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSubmitAnswer() throws Exception {
        AnswerRecord answerRecord = new AnswerRecord();
        answerRecord.setStudentName("John Doe");
        answerRecord.setExamId(1L);
        answerRecord.setQuestionId(1L);
        answerRecord.setStudentAnswer("Sample answer");

        List<AnswerRecord> answerRecords = Arrays.asList(answerRecord);

        Mockito.when(answerRecordService.automaticMarking("John Doe", 1L))
                .thenReturn(answerRecords);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/answer-record/submit-answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerRecords)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateGrade() throws Exception {
        Mockito.when(answerRecordService.updateGrade("John Doe", 1L, 1L, "A"))
                .thenReturn(new AnswerRecord());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/answer-record/grade")
                        .param("studentName", "John Doe")
                        .param("examId", "1")
                        .param("questionId", "1")
                        .param("grade", "A"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
