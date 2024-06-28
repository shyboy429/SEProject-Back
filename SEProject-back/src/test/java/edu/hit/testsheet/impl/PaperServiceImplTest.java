package edu.hit.testsheet.impl;

import edu.hit.testsheet.TestsheetApplication;
import edu.hit.testsheet.bean.Exam;
import edu.hit.testsheet.bean.Paper;
import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.dto.ExamPaperDto;
import edu.hit.testsheet.dto.PaperReturnDto;
import edu.hit.testsheet.dto.PaperUpdateDto;
import edu.hit.testsheet.exception.*;
import edu.hit.testsheet.repository.ExamRepository;
import edu.hit.testsheet.repository.PaperRepository;
import edu.hit.testsheet.repository.QuestionRepository;
import edu.hit.testsheet.util.PaperToDtoUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * ClassName:PaperServiceImpl
 * Package:edu.hit.testsheet.impl
 * Description:
 *
 * @date:2024/6/28 15:16
 * @author:shyboy
 */
@ActiveProfiles(value = "test")
@SpringBootTest(classes = TestsheetApplication.class)
@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
public class PaperServiceImplTest {

    @Mock
    private PaperRepository paperRepository;

    @Mock
    private PaperToDtoUtil paperToDtoUtil;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private ExamRepository examRepository;

    @InjectMocks
    private PaperServiceImpl paperService;

    @BeforeEach
    public void setUp() {
        // 初始化mock对象
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPapersWithPagination() {
        List<Paper> papers = Arrays.asList(new Paper(), new Paper());
        when(paperRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(papers));
        when(paperToDtoUtil.convertPaperToDto(any(Paper.class))).thenReturn(new PaperReturnDto());

        List<PaperReturnDto> result = paperService.getAllPapers(0, 10);

        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllPapersWithoutPagination() {
        List<Paper> papers = Arrays.asList(new Paper(), new Paper());
        when(paperRepository.findAll()).thenReturn(papers);
        when(paperToDtoUtil.convertPaperToDto(any(Paper.class))).thenReturn(new PaperReturnDto());

        List<PaperReturnDto> result = paperService.getAllPapers(-2, 10);

        assertEquals(2, result.size());
    }

    @Test
    public void testAddPaperInvalidContent() {
        Paper paper = new Paper();
        paper.setTitle("Sample Paper");

        InvalidPaperException exception = assertThrows(InvalidPaperException.class, () -> {
            paperService.addPaper(paper);
        });

        assertEquals("试题篮中没有题目！", exception.getMessage());
    }

    @Test
    public void testAddPaperInvalidTitle() {
        Paper paper = new Paper();
        paper.setContent("Sample Content");

        InvalidPaperException exception = assertThrows(InvalidPaperException.class, () -> {
            paperService.addPaper(paper);
        });

        assertEquals("试卷名称不可为空！", exception.getMessage());
    }

    @Test
    public void testAddPaperSuccess() {
        Paper paper = new Paper();
        paper.setContent("Sample Content");
        paper.setTitle("Sample Title");

        when(paperRepository.save(any(Paper.class))).thenReturn(paper);

        Paper result = paperService.addPaper(paper);

        assertNotNull(result);
    }

    @Test
    public void testDeletePaperByIdPaperNotFound() {
        when(paperRepository.existsById(1L)).thenReturn(false);

        PaperNotFoundException exception = assertThrows(PaperNotFoundException.class, () -> {
            paperService.deletePaperById(1L);
        });

        assertEquals("Paper not found with id: 1", exception.getMessage());
    }

    @Test
    public void testDeletePaperByIdPaperInUse() {
        when(paperRepository.existsById(1L)).thenReturn(true);
        when(examRepository.existsByPaperId(1L)).thenReturn(true);
        when(examRepository.findByPaperId(1L)).thenReturn(Arrays.asList(new Exam("Exam1"), new Exam("Exam2")));

        PaperCanNotBeDeletedException exception = assertThrows(PaperCanNotBeDeletedException.class, () -> {
            paperService.deletePaperById(1L);
        });

        assertTrue(exception.getMessage().contains("考试：\"Exam1, Exam2\" 使用当前试卷，不可删除！"));
    }

    @Test
    public void testDeletePaperByIdSuccess() {
        // Mocking the dependencies
        when(paperRepository.existsById(1L)).thenReturn(true);
        when(examRepository.existsByPaperId(1L)).thenReturn(false);

        // Call the method to be tested
        paperService.deletePaperById(1L);
        // Verify that the deleteById method was called once with the correct ID
        verify(paperRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testSelectPaperByIdNotFound() {
        when(paperRepository.findById(1L)).thenReturn(Optional.empty());

        PaperNotFoundException exception = assertThrows(PaperNotFoundException.class, () -> {
            paperService.selectPaperById(1L);
        });

        assertEquals("Paper not found with id: 1", exception.getMessage());
    }

    @Test
    public void testSelectPaperByIdSuccess() {
        Paper paper = new Paper();
        when(paperRepository.findById(1L)).thenReturn(Optional.of(paper));
        when(paperToDtoUtil.convertPaperToDto(paper)).thenReturn(new PaperReturnDto());

        PaperReturnDto result = paperService.selectPaperById(1L);

        assertNotNull(result);
    }

    @Test
    public void testUpdatePaperNotFound() {
        when(paperRepository.findById(1L)).thenReturn(Optional.empty());

        PaperNotFoundException exception = assertThrows(PaperNotFoundException.class, () -> {
            paperService.updatePaper(1L, new PaperUpdateDto());
        });

        assertEquals("Paper not found with id: 1", exception.getMessage());
    }

    @Test
    public void testUpdatePaperSuccess() {
        Paper paper = new Paper();
        when(paperRepository.findById(1L)).thenReturn(Optional.of(paper));
        when(paperRepository.save(any(Paper.class))).thenReturn(paper);

        PaperUpdateDto updateDto = new PaperUpdateDto();
        updateDto.setTitle("Updated Title");
        updateDto.setContent("Updated Content");
        updateDto.setIntroduction("Updated Introduction");

        Paper result = paperService.updatePaper(1L, updateDto);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Content", result.getContent());
        assertEquals("Updated Introduction", result.getIntroduction());
    }

    @Test
    public void testSelectPaperByIdForAdminNotFound() {
        when(paperRepository.findById(1L)).thenReturn(Optional.empty());

        PaperNotFoundException exception = assertThrows(PaperNotFoundException.class, () -> {
            paperService.selectPaperByIdForAdmin(1L);
        });

        assertEquals("Paper not found with id: 1", exception.getMessage());
    }

    @Test
    public void testSelectPaperByIdForAdminNoQuestions() {
        Paper paper = new Paper();
        paper.setContent("");
        when(paperRepository.findById(1L)).thenReturn(Optional.of(paper));

        QuestionNotFoundException exception = assertThrows(QuestionNotFoundException.class, () -> {
            paperService.selectPaperByIdForAdmin(1L);
        });

        assertEquals("Question not found with id: -1", exception.getMessage());
    }

    @Test
    public void testSelectPaperByIdForAdminSuccess() {
        Paper paper = new Paper();
        paper.setContent("1 2 3");
        when(paperRepository.findById(1L)).thenReturn(Optional.of(paper));
        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(new Question()));

        List<Question> result = paperService.selectPaperByIdForAdmin(1L);

        assertEquals(3, result.size());
    }

    @Test
    public void testDeleteQuestionInPaperNotFound() {
        when(paperRepository.findById(1L)).thenReturn(Optional.empty());

        PaperNotFoundException exception = assertThrows(PaperNotFoundException.class, () -> {
            paperService.deleteQuestionInPaper(1L, 1L);
        });

        assertEquals("Paper not found with id: 1", exception.getMessage());
    }

    @Test
    public void testDeleteQuestionInPaperLastQuestion() {
        Paper paper = new Paper();
        paper.setContent("1");
        when(paperRepository.findById(1L)).thenReturn(Optional.of(paper));

        DeleteLastQuestionInPaperException exception = assertThrows(DeleteLastQuestionInPaperException.class, () -> {
            paperService.deleteQuestionInPaper(1L, 1L);
        });

        assertEquals("该题是当前试卷的最后一题。如果需要，请直接删除试卷。", exception.getMessage());
    }

    @Test
    public void testDeleteQuestionInPaperSuccess() {
        Paper paper = new Paper();
        paper.setContent("1 2");
        when(paperRepository.findById(1L)).thenReturn(Optional.of(paper));
        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(new Question()));

        List<Question> result = paperService.deleteQuestionInPaper(1L, 2L);

        assertEquals(1, result.size());
        assertFalse(paper.getContent().contains("2"));
    }

    @Test
    public void testGetPapersCount() {
        when(paperRepository.count()).thenReturn(5L);

        long count = paperService.getPapersCount();

        assertEquals(5L, count);
    }

    @Test
    public void testGetExamPaper() {
        Paper paper = new Paper();
        paper.setContent("1 2 3");
        Question q1 = new Question();
        q1.setType("选择题");
        Question q2 = new Question();
        q2.setType("判断题");
        Question q3 = new Question();
        q3.setType("填空题");

        when(paperRepository.findById(1L)).thenReturn(Optional.of(paper));
        when(questionRepository.findById(1L)).thenReturn(Optional.of(q1));
        when(questionRepository.findById(2L)).thenReturn(Optional.of(q2));
        when(questionRepository.findById(3L)).thenReturn(Optional.of(q3));

        List<ExamPaperDto> result = paperService.getExamPaper(1L);

        assertEquals(3, result.size());
        assertEquals("选择题", result.get(0).getType());
        assertEquals("判断题", result.get(1).getType());
        assertEquals("填空题", result.get(2).getType());
    }

    @Test
    public void testDeleteQuestionInPaper() {
        Paper paper = new Paper();
        paper.setId(1L);
        paper.setContent("1 2 3");

        when(paperRepository.findById(anyLong())).thenReturn(Optional.of(paper));

        paperService.deleteQuestionInPaper(1L, 2L);

        verify(paperRepository, times(1)).save(paper);
        assertEquals("1 3", paper.getContent());
    }
}
