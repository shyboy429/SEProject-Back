package edu.hit.testsheet;
import edu.hit.testsheet.Dto.PaperUpdateDto;
import edu.hit.testsheet.bean.Paper;
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
import edu.hit.testsheet.service.PaperService;

/**
 * ClassName:PaperControllerTest
 * Package:edu.hit.testsheet
 * Description:
 *
 * @date:2024/6/3 18:07
 * @author:shyboy
 */
@ActiveProfiles(value = "test")
@SpringBootTest(classes = TestsheetApplication.class)
@WebAppConfiguration
public class PaperControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private PaperService paperService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllPapers() throws Exception {
        List<Paper> papers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            papers.add(new Paper());
        }
        when(paperService.getAllPapers()).thenReturn(papers);
        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/papers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    public void testAddPaper() throws Exception {
        Paper paper = new Paper();
        paper.setId(1L);
        paper.setTitle("Sample Paper");
        paper.setCreatedBy(1L);

        when(paperService.addPaper(any(Paper.class))).thenReturn(paper);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/papers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Sample Paper\", \"createdBy\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Sample Paper"))
                .andExpect(jsonPath("$.createdBy").value(1));
    }

    @Test
    public void testDeletePaperById() throws Exception {
        doNothing().when(paperService).deletePaperById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/papers/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPaperById() throws Exception {
        Paper paper = new Paper();
        paper.setId(1L);
        paper.setTitle("Sample Paper");
        paper.setCreatedBy(1L);

        when(paperService.selectPaperById(1L)).thenReturn(paper);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/papers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Sample Paper"))
                .andExpect(jsonPath("$.createdBy").value(1));
    }

    @Test
    public void testUpdatePaper() throws Exception {
        PaperUpdateDto paperUpdateDTO = new PaperUpdateDto();
        paperUpdateDTO.setTitle("Updated Paper");

        Paper updatedPaper = new Paper();
        updatedPaper.setId(1L);
        updatedPaper.setTitle("Updated Paper");
        updatedPaper.setCreatedBy(1L);

        when(paperService.updatePaper(eq(1L), any(PaperUpdateDto.class))).thenReturn(updatedPaper);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/papers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Paper\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Paper"))
                .andExpect(jsonPath("$.createdBy").value(1));
    }
}
