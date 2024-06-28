package edu.hit.testsheet.util;

import edu.hit.testsheet.bean.Exam;
import edu.hit.testsheet.dto.ExamReturnDto;
import edu.hit.testsheet.service.AnswerRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
/**
 * ClassName:ExamListToDtoListUtilTest
 * Package:edu.hit.testsheet.util
 * Description:
 *
 * @date:2024/6/28 17:10
 * @author:shyboy
 */
@ExtendWith(MockitoExtension.class)
public class ExamListToDtoListUtilTest {

    @Mock
    private AnswerRecordService answerRecordService;

    @InjectMocks
    private ExamListToDtoListUtil examListToDtoListUtil;

    private Exam exam1;
    private Exam exam2;
    
    private Exam exam3;

    @BeforeEach
    public void setUp() {
        exam1 = new Exam();
        exam1.setId(1L);
        exam1.setName("Exam 1");
        exam1.setPaperId(101L);
        exam1.setPublisher("Publisher 1");
        exam1.setStartTime("2028-06-30 10:00:00");
        exam1.setEndTime("2028-06-30 12:00:00");
        exam1.setDurationTime(String.valueOf(120));

        exam2 = new Exam();
        exam2.setId(2L);
        exam2.setName("Exam 2");
        exam2.setPaperId(102L);
        exam2.setPublisher("Publisher 2");
        exam2.setStartTime("2024-06-01 14:00:00");
        exam2.setEndTime("2028-06-30 16:00:00");
        exam2.setDurationTime(String.valueOf(120));

        exam3 = new Exam();
        exam3.setId(3L);
        exam3.setName("Exam 3");
        exam3.setPaperId(103L);
        exam3.setPublisher("Publisher 3");
        exam3.setStartTime("2024-06-01 14:00:00");
        exam3.setEndTime("2024-06-01 16:00:00");
        exam3.setDurationTime(String.valueOf(120));
    }

    @Test
    public void testConvertExamListToDtoList_WithStudentNameAndStatus() {
        List<Exam> exams = Arrays.asList(exam1, exam2,exam3);

        when(answerRecordService.existsByStudentNameAndExamId(anyString(), anyLong())).thenReturn(true);
        when(answerRecordService.calculateObjAndSubGrades(anyString(), anyLong())).thenReturn(new long[]{90, 85});

        List<ExamReturnDto> result = examListToDtoListUtil.convertExamListToDtoList("student1", exams, "进行中");

        assertEquals(3, result.size());

        ExamReturnDto dto1 = result.get(0);
        assertEquals(Long.valueOf(1L), dto1.getId());
        assertEquals("Exam 1", dto1.getName());
        assertEquals(Long.valueOf(101L), dto1.getPaperId());
        assertEquals("Publisher 1", dto1.getPublisher());
        assertEquals("2028-06-30 10:00:00", dto1.getStartTime());
        assertEquals("2028-06-30 12:00:00", dto1.getEndTime());
        assertEquals("120", dto1.getDurationTime());
        assertEquals("进行中", dto1.getStatus());
        assertEquals("已完成", dto1.getAnswerStatus());
        assertArrayEquals(new long[]{90, 85}, dto1.getObjAndSubGrade());

        ExamReturnDto dto2 = result.get(1);
        assertEquals(Long.valueOf(2L), dto2.getId());
        assertEquals("Exam 2", dto2.getName());
        assertEquals(Long.valueOf(102L), dto2.getPaperId());
        assertEquals("Publisher 2", dto2.getPublisher());
        assertEquals("2024-06-01 14:00:00", dto2.getStartTime());
        assertEquals("2028-06-30 16:00:00", dto2.getEndTime());
        assertEquals("120", dto2.getDurationTime());
        assertEquals("进行中", dto2.getStatus());
        assertEquals("已完成", dto2.getAnswerStatus());
        assertArrayEquals(new long[]{90, 85}, dto2.getObjAndSubGrade());
    }

    @Test
    public void testConvertExamListToDtoList_WithoutStudentNameAndStatus() {
        List<Exam> exams = Arrays.asList(exam1, exam2,exam3);

        List<ExamReturnDto> result = examListToDtoListUtil.convertExamListToDtoList(null, exams, null);

        assertEquals(3, result.size());

        ExamReturnDto dto1 = result.get(0);
        assertEquals(Long.valueOf(1L), dto1.getId());
        assertEquals("Exam 1", dto1.getName());
        assertEquals(Long.valueOf(101L), dto1.getPaperId());
        assertEquals("Publisher 1", dto1.getPublisher());
        assertEquals("2028-06-30 10:00:00", dto1.getStartTime());
        assertEquals("2028-06-30 12:00:00", dto1.getEndTime());
        assertEquals("120", dto1.getDurationTime()); // Changed to Integer to match method return type
        assertEquals("未开始", dto1.getStatus());
        assertEquals("当前为管理员或老师", dto1.getAnswerStatus());

        ExamReturnDto dto2 = result.get(1);
        assertEquals(Long.valueOf(2L), dto2.getId());
        assertEquals("Exam 2", dto2.getName());
        assertEquals(Long.valueOf(102L), dto2.getPaperId());
        assertEquals("Publisher 2", dto2.getPublisher());
        assertEquals("2024-06-01 14:00:00", dto2.getStartTime());
        assertEquals("2028-06-30 16:00:00", dto2.getEndTime());
        assertEquals("120", dto2.getDurationTime()); // Changed to Integer to match method return type
        assertEquals("进行中", dto2.getStatus());
        assertEquals("当前为管理员或老师", dto2.getAnswerStatus());

        ExamReturnDto dto3 = result.get(2);
        assertEquals(Long.valueOf(3L), dto3.getId());
        assertEquals("Exam 3", dto3.getName());
        assertEquals(Long.valueOf(103L), dto3.getPaperId());
        assertEquals("Publisher 3", dto3.getPublisher());
        assertEquals("2024-06-01 14:00:00", dto3.getStartTime());
        assertEquals("2024-06-01 16:00:00", dto3.getEndTime());
        assertEquals("120", dto3.getDurationTime()); // Changed to Integer to match method return type
        assertEquals("已结束", dto3.getStatus());
        assertEquals("当前为管理员或老师", dto3.getAnswerStatus());
    }

}
