package edu.hit.testsheet.impl;

/**
 * ClassName:QuestionRecordControllerTest
 * Package:edu.hit.testsheet.impl
 * Description:
 *
 * @date:2024/6/28 15:01
 * @author:shyboy
 */

import edu.hit.testsheet.TestsheetApplication;
import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.bean.Paper;
import edu.hit.testsheet.dto.QuestionUpdateDto;
import edu.hit.testsheet.exception.*;
import edu.hit.testsheet.repository.QuestionRepository;
import edu.hit.testsheet.repository.PaperRepository;
import edu.hit.testsheet.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@SpringBootTest(classes = TestsheetApplication.class)
@WebAppConfiguration
public class QuestionServiceImplTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private PaperRepository paperRepository;

    private QuestionService questionService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        questionService = new QuestionServiceImpl(questionRepository, paperRepository);
    }

    @Test
    public void testGetQuestionsByPage() {
        Question question1 = new Question();
        Question question2 = new Question();
        List<Question> questions = Arrays.asList(question1, question2);
        Page<Question> questionPage = new PageImpl<>(questions);
        when(questionRepository.findAll(any(Pageable.class))).thenReturn(questionPage);

        List<Question> result = questionService.getQuestionsByPage(0, 2);

        assertEquals(questions, result);
    }

    @Test
    public void testAddQuestionSuccess() {
        Question question = new Question();
        question.setType("选择题");
        question.setTag("tag1");
        question.setDifficultLevel("easy");
        question.setAnswer("answer");
        question.setDescription("description");
        question.setCreatedBy("user1");
        when(questionRepository.save(question)).thenReturn(question);

        Question result = questionService.addQuestion(question);

        assertEquals(question, result);
    }

    @Test
    public void testAddQuestionInvalidQuestionException() {
        Question question = new Question();
        question.setType("选择题");

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.addQuestion(question);
        });

        assertEquals("标签不可为空！", thrown.getMessage());
    }

    @Test
    public void testDeleteQuestionByIdSuccess() {
        when(questionRepository.existsById(1L)).thenReturn(true);

        Paper paper = new Paper();
        paper.setContent("2 3 4");  // 确保 content 字段不为 null 并且不包含要删除的 ID
        when(paperRepository.findAll()).thenReturn(Arrays.asList(paper));

        questionService.deleteQuestionById(1L);

        verify(questionRepository, times(1)).deleteById(1L);
    }
    @Test
    public void testDeleteQuestionByIdCanNotBeDeleted() {
        when(questionRepository.existsById(1L)).thenReturn(true);

        Paper paper = new Paper();
        paper.setContent("1 2 3");  // 确保 content 字段包含要删除的 ID
        paper.setTitle("Sample Paper");
        when(paperRepository.findAll()).thenReturn(Arrays.asList(paper));

        assertThrows(QuestionCanNotBeDeletedException.class, () -> {
            questionService.deleteQuestionById(1L);
        });

        verify(questionRepository, never()).deleteById(1L);  // 确保未删除
    }


    @Test
    public void testDeleteQuestionByIdNotFound() {
        when(questionRepository.existsById(1L)).thenReturn(false);

        assertThrows(QuestionNotFoundException.class, () -> {
            questionService.deleteQuestionById(1L);
        });
    }

    @Test
    public void testSelectQuestionByIdSuccess() {
        Question question = new Question();
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        Question result = questionService.selectQuestionById(1L);

        assertEquals(question, result);
    }

    @Test
    public void testSelectQuestionByIdNotFound() {
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(QuestionNotFoundException.class, () -> {
            questionService.selectQuestionById(1L);
        });
    }

    @Test
    public void testSelectQuestionByIdInPaper() {
        Question question = new Question();
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        Question result = questionService.selectQuestionByIdInPaper(1L);

        assertEquals(question, result);
    }

    @Test
    public void testUpdateQuestionSuccess() {
        Question question = new Question();
        question.setDescription("old description");
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        QuestionUpdateDto updateDto = new QuestionUpdateDto();
        updateDto.setDescription("new description");
        updateDto.setType("选择题");
        updateDto.setTag("tag1");
        updateDto.setDifficultLevel("easy");
        updateDto.setAnswer("answer");
        updateDto.setAnalysis("analysis");
        when(questionRepository.save(question)).thenReturn(question);

        Question result = questionService.updateQuestion(1L, updateDto);

        assertEquals("new description", result.getDescription());
    }

    @Test
    public void testUpdateQuestionInvalidQuestionException() {
        QuestionUpdateDto updateDto = new QuestionUpdateDto();

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.updateQuestion(1L, updateDto);
        });

        assertEquals("题型不可为空！", thrown.getMessage());
    }
    @Test
    public void testAddQuestionInvalidType() {
        Question question = new Question();
        question.setTag("tag1");
        question.setDifficultLevel("easy");
        question.setAnswer("answer");
        question.setDescription("description");
        question.setCreatedBy("user1");

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.addQuestion(question);
        });

        assertEquals("类型不可为空！", thrown.getMessage());
    }

    @Test
    public void testAddQuestionInvalidTag() {
        Question question = new Question();
        question.setType("选择题");
        question.setDifficultLevel("easy");
        question.setAnswer("answer");
        question.setDescription("description");
        question.setCreatedBy("user1");

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.addQuestion(question);
        });

        assertEquals("标签不可为空！", thrown.getMessage());
    }

    @Test
    public void testAddQuestionInvalidDifficultLevel() {
        Question question = new Question();
        question.setType("选择题");
        question.setTag("tag1");
        question.setAnswer("answer");
        question.setDescription("description");
        question.setCreatedBy("user1");

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.addQuestion(question);
        });

        assertEquals("难度等级不可为空！", thrown.getMessage());
    }

    @Test
    public void testAddQuestionInvalidAnswer() {
        Question question = new Question();
        question.setType("选择题");
        question.setTag("tag1");
        question.setDifficultLevel("easy");
        question.setDescription("description");
        question.setCreatedBy("user1");

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.addQuestion(question);
        });

        assertEquals("正确选项不可为空！", thrown.getMessage());
    }

    @Test
    public void testAddOtherQuestionInvalidAnswer() {
        Question question = new Question();
        question.setType("填空题");
        question.setTag("tag1");
        question.setDifficultLevel("easy");
        question.setDescription("description");
        question.setCreatedBy("user1");

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.addQuestion(question);
        });

        assertEquals("答案不可为空！", thrown.getMessage());
    }

    @Test
    public void testAddQuestionInvalidDescription() {
        Question question = new Question();
        question.setType("选择题");
        question.setTag("tag1");
        question.setDifficultLevel("easy");
        question.setAnswer("answer");
        question.setCreatedBy("user1");

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.addQuestion(question);
        });

        assertEquals("题目和选项不可有空！", thrown.getMessage());
    }

    @Test
    public void testAddQuestionInvalidCreatedBy() {
        Question question = new Question();
        question.setType("选择题");
        question.setTag("tag1");
        question.setDifficultLevel("easy");
        question.setAnswer("answer");
        question.setDescription("description");

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.addQuestion(question);
        });

        assertEquals("创建人不可为空！", thrown.getMessage());
    }

    @Test
    public void testUpdateQuestionNotFound() {
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        QuestionUpdateDto updateDto = new QuestionUpdateDto();
        updateDto.setType("选择题");
        updateDto.setTag("tag1");
        updateDto.setDifficultLevel("easy");
        updateDto.setAnswer("answer");
        updateDto.setDescription("description");

        assertThrows(QuestionNotFoundException.class, () -> {
            questionService.updateQuestion(1L, updateDto);
        });
    }

    @Test
    public void testUpdateQuestionInvalidType() {
        QuestionUpdateDto updateDto = new QuestionUpdateDto();
        updateDto.setTag("tag1");
        updateDto.setDifficultLevel("easy");
        updateDto.setAnswer("answer");
        updateDto.setDescription("description");

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.updateQuestion(1L, updateDto);
        });

        assertEquals("题型不可为空！", thrown.getMessage());
    }

    @Test
    public void testUpdateQuestionInvalidTag() {
        QuestionUpdateDto updateDto = new QuestionUpdateDto();
        updateDto.setType("选择题");
        updateDto.setDifficultLevel("easy");
        updateDto.setAnswer("answer");
        updateDto.setDescription("description");

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.updateQuestion(1L, updateDto);
        });

        assertEquals("标签不可为空！", thrown.getMessage());
    }

    @Test
    public void testUpdateQuestionInvalidDifficultLevel() {
        QuestionUpdateDto updateDto = new QuestionUpdateDto();
        updateDto.setType("选择题");
        updateDto.setTag("tag1");
        updateDto.setAnswer("answer");
        updateDto.setDescription("description");

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.updateQuestion(1L, updateDto);
        });

        assertEquals("难度不可为空！", thrown.getMessage());
    }

    @Test
    public void testUpdateQuestionInvalidAnswer() {
        QuestionUpdateDto updateDto = new QuestionUpdateDto();
        updateDto.setType("选择题");
        updateDto.setTag("tag1");
        updateDto.setDifficultLevel("easy");
        updateDto.setDescription("description");

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.updateQuestion(1L, updateDto);
        });

        assertEquals("答案不可为空！", thrown.getMessage());
    }

    @Test
    public void testUpdateQuestionInvalidDescription() {
        QuestionUpdateDto updateDto = new QuestionUpdateDto();
        updateDto.setType("选择题");
        updateDto.setTag("tag1");
        updateDto.setDifficultLevel("easy");
        updateDto.setAnswer("answer");

        InvalidQuestionException thrown = assertThrows(InvalidQuestionException.class, () -> {
            questionService.updateQuestion(1L, updateDto);
        });

        assertEquals("问题不可为空！", thrown.getMessage());
    }



    @Test
    public void testSelectQuestion() {
        Question question1 = new Question();
        Question question2 = new Question();
        List<Question> questions = Arrays.asList(question1, question2);
        when(questionRepository.searchQuestions(anyString(), anyString(), anyString(), anyString(),
                anyInt(), anyInt(), anyString(), anyString())).thenReturn(questions);

        List<Question> result = questionService.selectQuestion("keyword", "type", "easy", "user",
                0, 2, "id", "asc");

        assertEquals(questions, result);
    }

    @Test
    public void testGetQuestionsCount() {
        when(questionRepository.count()).thenReturn(5L);

        long count = questionService.getQuestionsCount();

        assertEquals(5L, count);
    }
    @Test
    public void testAddQuestion_ValidQuestion() {
        // Mock data
        Question question = new Question();
        question.setType("选择题");
        question.setTag("标签");
        question.setDifficultLevel("简单");
        question.setAnswer("A");
        question.setDescription("问题描述");
        question.setCreatedBy("user");

        when(questionRepository.save(any(Question.class))).thenReturn(question);

        // Test addQuestion
        Question savedQuestion = questionService.addQuestion(question);

        // Verify the save method is called once
        verify(questionRepository, times(1)).save(any(Question.class));

        // Assert the returned question matches the input question
        assertEquals(question.getType(), savedQuestion.getType());
        assertEquals(question.getTag(), savedQuestion.getTag());
        assertEquals(question.getDifficultLevel(), savedQuestion.getDifficultLevel());
        assertEquals(question.getAnswer(), savedQuestion.getAnswer());
        assertEquals(question.getDescription(), savedQuestion.getDescription());
        assertEquals(question.getCreatedBy(), savedQuestion.getCreatedBy());
    }

    @Test
    public void testAddOtherQuestion_ValidQuestion() {
        // Mock data
        Question question = new Question();
        question.setType("填空题");
        question.setTag("标签");
        question.setDifficultLevel("简单");
        question.setAnswer("A");
        question.setDescription("问题描述");
        question.setCreatedBy("user");

        when(questionRepository.save(any(Question.class))).thenReturn(question);

        // Test addQuestion
        Question savedQuestion = questionService.addQuestion(question);

        // Verify the save method is called once
        verify(questionRepository, times(1)).save(any(Question.class));

        // Assert the returned question matches the input question
        assertEquals(question.getType(), savedQuestion.getType());
        assertEquals(question.getTag(), savedQuestion.getTag());
        assertEquals(question.getDifficultLevel(), savedQuestion.getDifficultLevel());
        assertEquals(question.getAnswer(), savedQuestion.getAnswer());
        assertEquals(question.getDescription(), savedQuestion.getDescription());
        assertEquals(question.getCreatedBy(), savedQuestion.getCreatedBy());
    }

    @Test
    public void testAddQuestion_EmptyDescription() {
        // Mock data with empty description
        Question question = new Question();
        question.setType("选择题");
        question.setTag("标签");
        question.setDifficultLevel("简单");
        question.setAnswer("A");
        question.setDescription(""); // Empty description

        // Test addQuestion and expect InvalidQuestionException
        InvalidQuestionException exception = assertThrows(InvalidQuestionException.class, () -> {
            questionService.addQuestion(question);
        });

        // Assert the exception message
        assertEquals("题目和选项不可有空！", exception.getMessage());
    }

    @Test
    public void testAddOtherQuestion_EmptyDescription() {
        // Mock data with empty description
        Question question = new Question();
        question.setType("填空题");
        question.setTag("标签");
        question.setDifficultLevel("简单");
        question.setAnswer("A");
        question.setDescription(""); // Empty description

        // Test addQuestion and expect InvalidQuestionException
        InvalidQuestionException exception = assertThrows(InvalidQuestionException.class, () -> {
            questionService.addQuestion(question);
        });

        // Assert the exception message
        assertEquals("题目不可为空！", exception.getMessage());
    }

    @Test
    public void testUpdateQuestion_ValidQuestion() {
        // Mock data
        Long questionId = 1L;
        QuestionUpdateDto updateDto = new QuestionUpdateDto();
        updateDto.setType("问答题");
        updateDto.setTag("标签");
        updateDto.setDifficultLevel("简单");
        updateDto.setAnswer("A");
        updateDto.setDescription("更新后的问题描述");

        Question existingQuestion = new Question();
        existingQuestion.setId(questionId);
        existingQuestion.setType("问答题");
        existingQuestion.setTag("标签");
        existingQuestion.setDifficultLevel("简单");
        existingQuestion.setAnswer("A");
        existingQuestion.setDescription("原始问题描述");
        existingQuestion.setCreatedBy("user");

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(existingQuestion));
        when(questionRepository.save(any(Question.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Test updateQuestion
        Question updatedQuestion = questionService.updateQuestion(questionId, updateDto);

        // Verify findById and save methods are called once
        verify(questionRepository, times(1)).findById(questionId);
        verify(questionRepository, times(1)).save(any(Question.class));

        // Assert the updated fields
        assertEquals(questionId, updatedQuestion.getId());
        assertEquals(updateDto.getType(), updatedQuestion.getType());
        assertEquals(updateDto.getTag(), updatedQuestion.getTag());
        assertEquals(updateDto.getDifficultLevel(), updatedQuestion.getDifficultLevel());
        assertEquals(updateDto.getAnswer(), updatedQuestion.getAnswer());
        assertEquals(updateDto.getDescription(), updatedQuestion.getDescription());
    }

    @Test
    public void testUpdateQuestion_EmptyAnswer() {
        // Mock data with empty answer
        Long questionId = 1L;
        QuestionUpdateDto updateDto = new QuestionUpdateDto();
        updateDto.setType("问答题");
        updateDto.setTag("标签");
        updateDto.setDifficultLevel("简单");
        updateDto.setAnswer(""); // Empty answer

        Question existingQuestion = new Question();
        existingQuestion.setId(questionId);
        existingQuestion.setType("问答题");
        existingQuestion.setTag("标签");
        existingQuestion.setDifficultLevel("简单");
        existingQuestion.setAnswer("A");
        existingQuestion.setDescription("问题描述");
        existingQuestion.setCreatedBy("user");

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(existingQuestion));

        // Test updateQuestion and expect InvalidQuestionException
        InvalidQuestionException exception = assertThrows(InvalidQuestionException.class, () -> {
            questionService.updateQuestion(questionId, updateDto);
        });
        
        verify(questionRepository, never()).save(any(Question.class));

        // Assert the exception message
        assertEquals("答案不可为空！", exception.getMessage());
    }
    
}

