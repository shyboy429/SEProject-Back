package edu.hit.testsheet.impl;

import edu.hit.testsheet.TestsheetApplication;
import edu.hit.testsheet.bean.AnswerRecord;
import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.exception.AnswerRecordNotFoundException;
import edu.hit.testsheet.repository.AnswerRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import edu.hit.testsheet.service.QuestionService;
import edu.hit.testsheet.util.CalculateQuestionsScoreUtil;

/**
 * ClassName:AnswerRecordServiceImplTest
 * Package:edu.hit.testsheet.impl
 * Description:
 *
 * @date:2024/6/28 16:22
 * @author:shyboy
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestsheetApplication.class)
public class AnswerRecordServiceImplTest {

    @Mock
    private AnswerRecordRepository answerRecordRepository;

    @Mock
    private QuestionService questionService;

    @Mock
    private CalculateQuestionsScoreUtil calculateQuestionsScoreUtil;

    @InjectMocks
    private AnswerRecordServiceImpl answerRecordService;

    private AnswerRecord answerRecord1;
    private AnswerRecord answerRecord2;

    @BeforeEach
    public void setUp() {
        answerRecord1 = new AnswerRecord();
        answerRecord1.setStudentName("student1");
        answerRecord1.setExamId(1L);
        answerRecord1.setQuestionId(101L);
        answerRecord1.setStudentAnswer("A");

        answerRecord2 = new AnswerRecord();
        answerRecord2.setStudentName("student1");
        answerRecord2.setExamId(1L);
        answerRecord2.setQuestionId(102L);
        answerRecord2.setStudentAnswer("B");
    }

    @Test
    public void testGetAnswerRecordByStudentNameAndExamId() {
        when(answerRecordRepository.findByStudentNameAndExamId(anyString(), anyLong()))
                .thenReturn(Arrays.asList(answerRecord1, answerRecord2));

        List<AnswerRecord> result = answerRecordService.getAnswerRecordByStudentNameAndExamId("student1", 1L);

        assertEquals(2, result.size());
        verify(answerRecordRepository, times(1)).findByStudentNameAndExamId("student1", 1L);
    }

    @Test
    public void testGetAnswerRecordByStudentNameAndExamId_NotFound() {
        when(answerRecordRepository.findByStudentNameAndExamId(anyString(), anyLong()))
                .thenReturn(Arrays.asList());

        assertThrows(AnswerRecordNotFoundException.class, () -> {
            answerRecordService.getAnswerRecordByStudentNameAndExamId("student1", 1L);
        });
    }

    @Test
    public void testAutomaticMarking() {
        // 设置 AnswerRecord 的模拟返回值
        AnswerRecord answerRecord1 = new AnswerRecord();
        answerRecord1.setStudentName("student1");
        answerRecord1.setExamId(1L);
        answerRecord1.setQuestionId(101L);
        answerRecord1.setStudentAnswer("A");

        AnswerRecord answerRecord2 = new AnswerRecord();
        answerRecord2.setStudentName("student1");
        answerRecord2.setExamId(1L);
        answerRecord2.setQuestionId(102L);
        answerRecord2.setStudentAnswer("B");

        AnswerRecord answerRecord3 = new AnswerRecord();
        answerRecord3.setStudentName("student1");
        answerRecord3.setExamId(1L);
        answerRecord3.setQuestionId(103L);
        answerRecord3.setStudentAnswer("C");

        AnswerRecord answerRecord4 = new AnswerRecord();
        answerRecord4.setStudentName("student1");
        answerRecord4.setExamId(1L);
        answerRecord4.setQuestionId(104L);
        answerRecord4.setStudentAnswer("D");

        List<AnswerRecord> answerRecords = Arrays.asList(answerRecord1, answerRecord2, answerRecord3, answerRecord4);

        // 设置 answerRecordRepository 的模拟返回值
        when(answerRecordRepository.findByStudentNameAndExamId("student1", 1L)).thenReturn(answerRecords);
        when(answerRecordRepository.findByStudentNameAndExamIdAndQuestionId("student1", 1L, 101L)).thenReturn(Optional.of(answerRecord1));
        when(answerRecordRepository.findByStudentNameAndExamIdAndQuestionId("student1", 1L, 102L)).thenReturn(Optional.of(answerRecord2));
        when(answerRecordRepository.findByStudentNameAndExamIdAndQuestionId("student1", 1L, 103L)).thenReturn(Optional.of(answerRecord3));
        when(answerRecordRepository.findByStudentNameAndExamIdAndQuestionId("student1", 1L, 104L)).thenReturn(Optional.of(answerRecord4));
        
        // 设置 Question 的模拟返回值
        Question question1 = new Question();
        question1.setId(101L);
        question1.setType("选择题");
        question1.setAnswer("A");
        question1.setDescription("desc");

        Question question2 = new Question();
        question2.setId(102L);
        question2.setType("填空题");
        question2.setAnswer("B");
        question2.setDescription("desc");

        Question question3 = new Question(); // 正确设置对象为 question3
        question3.setId(103L);
        question3.setType("判断题");
        question3.setAnswer("C");
        question3.setDescription("desc");

        Question question4 = new Question(); // 正确设置对象为 question4
        question4.setId(104L);
        question4.setType("选择题");
        question4.setAnswer("K");
        question4.setDescription("desc");

        when(questionService.selectQuestionById(101L)).thenReturn(question1);
        when(questionService.selectQuestionById(102L)).thenReturn(question2);
        when(questionService.selectQuestionById(103L)).thenReturn(question3);
        when(questionService.selectQuestionById(104L)).thenReturn(question4);

        // 调用 automaticMarking 方法
        answerRecordService.automaticMarking("student1", 1L);

        // 验证方法调用
        verify(answerRecordRepository, times(4)).save(any(AnswerRecord.class));
    }




    @Test
    public void testCalculateObjAndSubGrades() {
        when(answerRecordRepository.findByStudentNameAndExamId(anyString(), anyLong()))
                .thenReturn(Arrays.asList(answerRecord1, answerRecord2));
        when(calculateQuestionsScoreUtil.calculateScore(anyList()))
                .thenReturn(new long[]{80, 20});

        long[] result = answerRecordService.calculateObjAndSubGrades("student1", 1L);

        assertArrayEquals(new long[]{80, 20}, result);
        verify(answerRecordRepository, times(1)).findByStudentNameAndExamId("student1", 1L);
    }

    @Test
    public void testUpdateGrade() {
        // 模拟 findBy 方法的返回值
        when(answerRecordRepository.findByStudentNameAndExamIdAndQuestionId(anyString(), anyLong(), anyLong()))
                .thenReturn(Optional.of(answerRecord1));

        // 模拟 save 方法的行为
        when(answerRecordRepository.save(answerRecord1)).thenReturn(answerRecord1);

        // 调用 updateGrade 方法
        AnswerRecord result = answerRecordService.updateGrade("student1", 1L, 101L, "5");

        // 验证结果
        assertEquals("5", result.getGrade());
        verify(answerRecordRepository, times(1)).findByStudentNameAndExamIdAndQuestionId("student1", 1L, 101L);
        verify(answerRecordRepository, times(1)).save(answerRecord1);
    }


    @Test
    public void testUpdateGrade_NotFound() {
        when(answerRecordRepository.findByStudentNameAndExamIdAndQuestionId(anyString(), anyLong(), anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(AnswerRecordNotFoundException.class, () -> {
            answerRecordService.updateGrade("student1", 1L, 101L, "5");
        });
    }

    @Test
    public void testSubmitAnswer() {
        List<AnswerRecord> answerRecords = Arrays.asList(answerRecord1, answerRecord2);

        List<AnswerRecord> result = answerRecordService.submitAnswer(answerRecords);

        verify(answerRecordRepository, times(1)).saveAll(answerRecords);
        assertEquals(2, result.size());
    }

    @Test
    public void testExistsByStudentNameAndExamId() {
        when(answerRecordRepository.existsByStudentNameAndExamId(anyString(), anyLong()))
                .thenReturn(true);

        boolean result = answerRecordService.existsByStudentNameAndExamId("student1", 1L);

        assertTrue(result);
        verify(answerRecordRepository, times(1)).existsByStudentNameAndExamId("student1", 1L);
    }

    @Test
    public void testExistsByExamId() {
        when(answerRecordRepository.existsByExamId(anyLong()))
                .thenReturn(true);

        boolean result = answerRecordService.existsByExamId(1L);

        assertTrue(result);
        verify(answerRecordRepository, times(1)).existsByExamId(1L);
    }
    
    
}
