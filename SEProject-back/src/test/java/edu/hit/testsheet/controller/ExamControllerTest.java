package edu.hit.testsheet.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hit.testsheet.dto.ExamReturnDto;
import edu.hit.testsheet.TestsheetApplication;
import edu.hit.testsheet.bean.Exam;
import edu.hit.testsheet.service.ExamService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * ClassName:ExamControllerTest
 * Package:edu.hit.testsheet
 * Description:
 *
 * @date:2024/6/26 23:12
 * @author:shyboy
 */

@ActiveProfiles(value = "test")
@SpringBootTest(classes = TestsheetApplication.class)
@WebAppConfiguration
public class ExamControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExamService examService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllExams() throws Exception {
        // 创建两个模拟的 ExamReturnDto 对象
        ExamReturnDto exam1 = new ExamReturnDto();
        exam1.setId(1L);
        exam1.setName("Exam 1");

        ExamReturnDto exam2 = new ExamReturnDto();
        exam2.setId(2L);
        exam2.setName("Exam 2");

        // 模拟 examService.getAllExams() 方法的返回值
        List<ExamReturnDto> exams = Arrays.asList(exam1, exam2);
        Mockito.when(examService.getAllExams(null, 0, 10)).thenReturn(exams);

        // 执行 GET 请求，并验证返回的 JSON 结果
        mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/student-name")
                        .param("pageIndex","0")
                        .param("pageSize","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Exam 1")))
                .andExpect(jsonPath("$[1].name", is("Exam 2")));
    }


    @Test
    public void testCreateExam() throws Exception {
        Exam exam = new Exam();
        exam.setName("Sample Exam");

        Mockito.when(examService.createExam(Mockito.any(Exam.class))).thenReturn(exam);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/exams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exam)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sample Exam"));
    }

    @Test
    public void testDeleteExamById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/exams/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllExamsPagesNum() throws Exception {
        Mockito.when(examService.getAllExamsPagesNum()).thenReturn(25L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/page-num"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(25)));
    }

    @Test
    public void testGetNotStartedExam() throws Exception {
        ExamReturnDto exam1 = new ExamReturnDto();
        exam1.setId(1L);
        exam1.setName("Not Started Exam 1");

        ExamReturnDto exam2 = new ExamReturnDto();
        exam2.setId(2L);
        exam2.setName("Not Started Exam 2");

        List<ExamReturnDto> exams = Arrays.asList(exam1, exam2);
        Mockito.when(examService.getNotStartedExam(null, 0, 10)).thenReturn(exams);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/student-name/not-started")
                        .param("pageIndex", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Not Started Exam 1")))
                .andExpect(jsonPath("$[1].name", is("Not Started Exam 2")));
    }

    @Test
    public void testGetInProgressExam() throws Exception {
        ExamReturnDto exam1 = new ExamReturnDto();
        exam1.setId(1L);
        exam1.setName("In Progress Exam 1");

        ExamReturnDto exam2 = new ExamReturnDto();
        exam2.setId(2L);
        exam2.setName("In Progress Exam 2");

        List<ExamReturnDto> exams = Arrays.asList(exam1, exam2);
        Mockito.when(examService.getInProgressExam(null, 0, 10)).thenReturn(exams);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/student-name/in-progress")
                        .param("pageIndex", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("In Progress Exam 1")))
                .andExpect(jsonPath("$[1].name", is("In Progress Exam 2")));
    }

    @Test
    public void testGetFinishedExam() throws Exception {
        ExamReturnDto exam1 = new ExamReturnDto();
        exam1.setId(1L);
        exam1.setName("Finished Exam 1");

        ExamReturnDto exam2 = new ExamReturnDto();
        exam2.setId(2L);
        exam2.setName("Finished Exam 2");

        List<ExamReturnDto> exams = Arrays.asList(exam1, exam2);
        Mockito.when(examService.getFinishedExam(null, 0, 10)).thenReturn(exams);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/student-name/finished")
                        .param("pageIndex", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Finished Exam 1")))
                .andExpect(jsonPath("$[1].name", is("Finished Exam 2")));
    }

    @Test
    public void testGetNotStartedPagesNum() throws Exception {
        Mockito.when(examService.getNotStartedPagesNum()).thenReturn(5L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/not-started/page-num"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(5)));
    }

    @Test
    public void testGetInProgressPagesNum() throws Exception {
        Mockito.when(examService.getInProgressPagesNum()).thenReturn(3L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/in-progress/page-num"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(3)));
    }

    @Test
    public void testGetFinishedPagesNum() throws Exception {
        Mockito.when(examService.getFinishedPagesNum()).thenReturn(7L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/finished/page-num"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(7)));
    }
}

