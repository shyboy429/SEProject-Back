package edu.hit.testsheet.util;

import edu.hit.testsheet.bean.Paper;
import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.dto.PaperReturnDto;
import edu.hit.testsheet.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * ClassName:PaperToDtoUtilTest
 * Package:edu.hit.testsheet.util
 * Description:
 *
 * @date:2024/6/28 17:12
 * @author:shyboy
 */
@ExtendWith(MockitoExtension.class)
public class PaperToDtoUtilTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private PaperToDtoUtil paperToDtoUtil;

    private Paper paper;

    @BeforeEach
    public void setUp() {
        paper = new Paper();
        paper.setId(1L);
        paper.setTitle("Test Paper");
        paper.setIntroduction("This is a test paper.");
        paper.setCreateTime("2014年1月11日 13:12");
        paper.setUpdateTime("2014年1月13日 13:12");
        paper.setCreatedBy("admin");
        paper.setContent("1 2 invalidId 3");

        // Mock questions
        Question question1 = new Question();
        question1.setId(1L);
        question1.setDescription("Question 1 description");

        Question question2 = new Question();
        question2.setId(2L);
        question2.setDescription("Question 2 description");

        Question question3 = new Question();
        question3.setId(3L);
        question3.setDescription("Question 3 description");

        when(questionService.selectQuestionByIdInPaper(1L)).thenReturn(question1);
        when(questionService.selectQuestionByIdInPaper(2L)).thenReturn(question2);
        when(questionService.selectQuestionByIdInPaper(3L)).thenReturn(question3);
    }

    @Test
    public void testConvertPaperToDto() {
        PaperReturnDto result = paperToDtoUtil.convertPaperToDto(paper);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Paper", result.getTitle());
        assertEquals("This is a test paper.", result.getIntroduction());
        assertEquals("2014年1月11日 13:12", result.getCreateTime());
        assertEquals("2014年1月13日 13:12", result.getUpdateTime());
        assertEquals("admin", result.getCreatedBy());

        String expectedSpecificContent =
                "第1题: Question 1 description\n" +
                        "第2题: Question 2 description\n" +
                        "第3题: Question 3 description";

        assertEquals(expectedSpecificContent, result.getSpecificContent());
    }
}