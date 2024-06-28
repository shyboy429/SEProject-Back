package edu.hit.testsheet.util;

import edu.hit.testsheet.bean.AnswerRecord;
import edu.hit.testsheet.bean.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import edu.hit.testsheet.service.QuestionService;
import org.mockito.junit.jupiter.MockitoExtension;


/**
 * ClassName:CalculateQuestionsScoreUtilTest
 * Package:edu.hit.testsheet.util
 * Description:
 *
 * @date:2024/6/28 17:00
 * @author:shyboy
 */
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CalculateQuestionsScoreUtilTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private CalculateQuestionsScoreUtil calculateQuestionsScoreUtil;

    private List<AnswerRecord> records;

    @BeforeEach
    public void setUp() {
        // Create and set up mock AnswerRecords
        AnswerRecord record1 = new AnswerRecord();
        record1.setQuestionId(1L);
        record1.setGrade("5");

        AnswerRecord record2 = new AnswerRecord();
        record2.setQuestionId(2L);
        record2.setGrade("10");

        AnswerRecord record3 = new AnswerRecord();
        record3.setQuestionId(3L);
        record3.setGrade("7");

        AnswerRecord record4 = new AnswerRecord();
        record4.setQuestionId(4L);
        record4.setGrade("8");

        records = Arrays.asList(record1, record2, record3, record4);

        // Create and set up mock Questions
        Question question1 = new Question();
        question1.setId(1L);
        question1.setType("选择题");

        Question question2 = new Question();
        question2.setId(2L);
        question2.setType("填空题");

        Question question3 = new Question();
        question3.setId(3L);
        question3.setType("判断题");

        Question question4 = new Question();
        question4.setId(4L);
        question4.setType("主观题");

        // Mock the behavior of questionService
        when(questionService.selectQuestionById(1L)).thenReturn(question1);
        when(questionService.selectQuestionById(2L)).thenReturn(question2);
        when(questionService.selectQuestionById(3L)).thenReturn(question3);
        when(questionService.selectQuestionById(4L)).thenReturn(question4);
    }

    @Test
    public void testCalculateScore() {
        long[] expectedScores = {22, 8}; // objectiveScore = 5 + 10 + 7, subjectiveScore = 8
        long[] actualScores = calculateQuestionsScoreUtil.calculateScore(records);
        assertArrayEquals(expectedScores, actualScores);
    }
}