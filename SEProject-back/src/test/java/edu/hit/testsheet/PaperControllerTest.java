package edu.hit.testsheet;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hit.testsheet.bean.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.hamcrest.Matchers.is;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import edu.hit.testsheet.Dto.PaperReturnDto;
import edu.hit.testsheet.Dto.PaperUpdateDto;
import edu.hit.testsheet.bean.Paper;
import edu.hit.testsheet.service.PaperService;;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "test")
@SpringBootTest(classes = TestsheetApplication.class)
@WebAppConfiguration
public class PaperControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper用于将对象转换为JSON字符串

    @MockBean
    private PaperService paperService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllPapers() throws Exception {
        PaperReturnDto paper1 = new PaperReturnDto();
        paper1.setId(1L);
        paper1.setTitle("Paper 1");

        PaperReturnDto paper2 = new PaperReturnDto();
        paper2.setId(2L);
        paper2.setTitle("Paper 2");

        List<PaperReturnDto> papers = Arrays.asList(paper1, paper2);

        Mockito.when(paperService.getAllPapers(0, 10)).thenReturn(papers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/papers?pageNum=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Paper 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Paper 2")));
    }



    @Test
    public void testAddPaper() throws Exception {
        // 创建一个Paper对象作为请求主体
        Paper paper = new Paper();
        paper.setTitle("Sample Paper");
        paper.setIntroduction("This is a sample paper");

        // 模拟PaperService的addPaper方法的行为，返回创建的Paper对象
        Mockito.when(paperService.addPaper(Mockito.any(Paper.class))).thenReturn(paper);

        // 发送POST请求，并期望返回状态码为200
        mockMvc.perform(MockMvcRequestBuilders.post("/api/papers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paper)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 验证响应的JSON主体是否与预期一致
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Sample Paper"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.introduction").value("This is a sample paper"));
    }

    @Test
    public void testDeletePaperById() throws Exception {
        doNothing().when(paperService).deletePaperById(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/papers/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPaperById() throws Exception {
        PaperReturnDto paper = new PaperReturnDto();
        when(paperService.selectPaperById(1L)).thenReturn(paper);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/papers/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePaper() throws Exception {
        PaperUpdateDto updateDto = new PaperUpdateDto();
        Paper updatedPaper = new Paper();
        when(paperService.updatePaper(anyLong(), any(PaperUpdateDto.class))).thenReturn(updatedPaper);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/papers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Title\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteQuestionInPaper() throws Exception {
        List<Question> questions = Arrays.asList(new Question(), new Question());
        when(paperService.deleteQuestionInPaper(1L, 2L)).thenReturn(questions);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/papers/1/question/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetPaperByIdForAdmin() throws Exception {
        List<Question> questions = Arrays.asList(new Question(), new Question());
        when(paperService.selectPaperByIdForAdmin(1L)).thenReturn(questions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/papers/admin/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetQuestionsTotalPagesNum() throws Exception {
        Mockito.when(paperService.getPapersCount()).thenReturn(25L);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/papers/pageNum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(3)));
    }
}
