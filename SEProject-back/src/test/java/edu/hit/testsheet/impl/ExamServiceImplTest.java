package edu.hit.testsheet.impl;

import edu.hit.testsheet.TestsheetApplication;
import edu.hit.testsheet.repository.ExamRepository;
import edu.hit.testsheet.service.AnswerRecordService;
import edu.hit.testsheet.util.ExamListToDtoListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import edu.hit.testsheet.bean.Exam;
import edu.hit.testsheet.dto.ExamReturnDto;
import edu.hit.testsheet.exception.*;

import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * ClassName:ExamServiceImplTest
 * Package:edu.hit.testsheet.impl
 * Description:
 *
 * @date:2024/6/28 15:29
 * @author:shyboy
 */

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestsheetApplication.class)
public class ExamServiceImplTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private ExamListToDtoListUtil examListToDtoListUtil;

    @Mock
    private AnswerRecordService answerRecordService;

    @InjectMocks
    private ExamServiceImpl examService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllExams() {
        Page<Exam> examPage = new PageImpl<>(Arrays.asList(new Exam(), new Exam()));
        when(examRepository.findAll(any(Pageable.class))).thenReturn(examPage);
        when(examListToDtoListUtil.convertExamListToDtoList(anyString(), anyList(), isNull())).thenReturn(Arrays.asList(new ExamReturnDto(), new ExamReturnDto()));

        List<ExamReturnDto> result = examService.getAllExams("studentName", 0, 10);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(examRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testGetNotStartedExam() {
        Page<Exam> examPage = new PageImpl<>(Arrays.asList(new Exam(), new Exam()));
        when(examRepository.findNotStartedExams(anyString(), any(Pageable.class))).thenReturn(examPage);
        when(examListToDtoListUtil.convertExamListToDtoList(anyString(), anyList(), eq("未开始"))).thenReturn(Arrays.asList(new ExamReturnDto(), new ExamReturnDto()));

        List<ExamReturnDto> result = examService.getNotStartedExam("studentName", 0, 10);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(examRepository, times(1)).findNotStartedExams(anyString(), any(Pageable.class));
    }

    @Test
    public void testGetInProgressExam() {
        Page<Exam> examPage = new PageImpl<>(Arrays.asList(new Exam(), new Exam()));
        when(examRepository.findInProgressExams(anyString(), any(Pageable.class))).thenReturn(examPage);
        when(examListToDtoListUtil.convertExamListToDtoList(anyString(), anyList(), eq("进行中"))).thenReturn(Arrays.asList(new ExamReturnDto(), new ExamReturnDto()));

        List<ExamReturnDto> result = examService.getInProgressExam("studentName", 0, 10);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(examRepository, times(1)).findInProgressExams(anyString(), any(Pageable.class));
    }

    @Test
    public void testGetFinishedExam() {
        Page<Exam> examPage = new PageImpl<>(Arrays.asList(new Exam(), new Exam()));
        when(examRepository.findFinishedExams(anyString(), any(Pageable.class))).thenReturn(examPage);
        when(examListToDtoListUtil.convertExamListToDtoList(anyString(), anyList(), eq("已结束"))).thenReturn(Arrays.asList(new ExamReturnDto(), new ExamReturnDto()));

        List<ExamReturnDto> result = examService.getFinishedExam("studentName", 0, 10);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(examRepository, times(1)).findFinishedExams(anyString(), any(Pageable.class));
    }

    @Test
    public void testGetExamByIdSuccess() {
        Exam exam = new Exam();
        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        Exam result = examService.getExamById(1L);

        assertNotNull(result);
        assertEquals(exam, result);
        verify(examRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetExamByIdNotFound() {
        when(examRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ExamNotFoundException.class, () -> {
            examService.getExamById(1L);
        });

        assertEquals("Exam not found with id: 1", exception.getMessage());
    }

    @Test
    public void testCreateExamInvalidName() {
        Exam exam = new Exam();
        exam.setPaperId(1L);
        exam.setPublisher("publisher");
        exam.setStartTime("startTime");
        exam.setEndTime("endTime");
        exam.setDurationTime("60");

        Exception exception = assertThrows(InvalidExamException.class, () -> {
            examService.createExam(exam);
        });

        assertEquals("考试名称不可为空！", exception.getMessage());
    }

    @Test
    public void testDeleteExamSuccess() {
        Exam exam = new Exam();
        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));
        when(answerRecordService.existsByExamId(1L)).thenReturn(false);

        examService.deleteExam(1L);

        verify(examRepository, times(1)).delete(exam);
    }

    @Test
    public void testDeleteExamHasRecords() {
        Exam exam = new Exam();
        exam.setName("examName"); // 设置考试名称
        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));
        when(answerRecordService.existsByExamId(1L)).thenReturn(true);

        Exception exception = assertThrows(ExamCanNotBeDeletedException.class, () -> {
            examService.deleteExam(1L);
        });

        assertEquals("\"examName\" 已有考试记录，不可删除！", exception.getMessage());
    }

    @Test
    public void testCreateExamSuccess() {
        Exam exam = new Exam();
        exam.setName("Example Exam");
        exam.setPaperId(1L);
        exam.setPublisher("John Doe");
        exam.setStartTime("2024-06-29T15:44:34.995+08:00");
        exam.setEndTime("2024-06-30T15:44:34.995+08:00");
        exam.setDurationTime("60");

        when(examRepository.save(any())).thenReturn(exam);

        Exam createdExam = examService.createExam(exam);

        assertNotNull(createdExam);
        assertEquals(exam.getName(), createdExam.getName());
        assertEquals(exam.getPaperId(), createdExam.getPaperId());
        assertEquals(exam.getPublisher(), createdExam.getPublisher());
        assertEquals(exam.getStartTime(), createdExam.getStartTime());
        assertEquals(exam.getEndTime(), createdExam.getEndTime());
        assertEquals(exam.getDurationTime(), createdExam.getDurationTime());
    }

    @Test
    public void testCreateExamMissingName() {
        Exam exam = new Exam();
        exam.setPaperId(1L);
        exam.setPublisher("John Doe");
        exam.setStartTime("2024-07-01 09:00:00");
        exam.setEndTime("2024-07-01 10:00:00");
        exam.setDurationTime("60");

        Exception exception = assertThrows(InvalidExamException.class, () -> {
            examService.createExam(exam);
        });

        assertEquals("考试名称不可为空！", exception.getMessage());
    }

    @Test
    public void testCreateExamInvalidDuration() {
        Exam exam = new Exam();
        exam.setName("Example Exam");
        exam.setPaperId(1L);
        exam.setPublisher("John Doe");
        exam.setStartTime("2024-06-29T15:44:34.995+08:00");
        exam.setEndTime("2024-06-30T15:44:34.995+08:00");
        exam.setDurationTime("-60");

        Exception exception = assertThrows(InvalidDurationTimeException.class, () -> {
            examService.createExam(exam);
        });

        assertEquals("考试限时必须是一个正整数！", exception.getMessage());
    }

    @Test
    public void testCreateExamStartTimeBeforeCurrentTime() {
        Exam exam = new Exam();
        exam.setName("Example Exam");
        exam.setPaperId(1L);
        exam.setPublisher("John Doe");
        exam.setStartTime("2024-06-28T15:44:34.995+08:00"); 
        exam.setEndTime("2024-06-28T15:44:34.995+08:00");
        exam.setDurationTime("60");

        Exception exception = assertThrows(InvalidExamStartTimeException.class, () -> {
            examService.createExam(exam);
        });

        assertEquals("考试开始时间不可早于当前时间！", exception.getMessage());
    }
    
    @Test
    public void testDeleteNonExistingExam() {
        when(examRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(ExamNotFoundException.class, () -> {
            examService.deleteExam(1L);
        });
        assertEquals("Exam not found with id: 1", exception.getMessage());
    }

    @Test
    public void testCreateExamEmptyStartTime() {
        Exam exam = new Exam();
        exam.setName("Example Exam");
        exam.setPaperId(1L);
        exam.setPublisher("John Doe");
        exam.setStartTime(""); // Empty start time
        exam.setEndTime("2024-06-30T15:44:34.995+08:00");
        exam.setDurationTime("60");

        Exception exception = assertThrows(InvalidExamException.class, () -> {
            examService.createExam(exam);
        });

        assertEquals("开始时间不可为空！", exception.getMessage());
    }

    @Test
    public void testCreateExamEmptyEndTime() {
        Exam exam = new Exam();
        exam.setName("Example Exam");
        exam.setPaperId(1L);
        exam.setPublisher("John Doe");
        exam.setStartTime("2024-06-29T15:44:34.995+08:00");
        exam.setEndTime(""); // Empty end time
        exam.setDurationTime("60");

        Exception exception = assertThrows(InvalidExamException.class, () -> {
            examService.createExam(exam);
        });

        assertEquals("结束时间不可为空！", exception.getMessage());
    }

    @Test
    public void testCreateExamEmptyDurationTime() {
        Exam exam = new Exam();
        exam.setName("Example Exam");
        exam.setPaperId(1L);
        exam.setPublisher("John Doe");
        exam.setStartTime("2024-06-29T15:44:34.995+08:00");
        exam.setEndTime("2024-06-30T15:44:34.995+08:00");
        exam.setDurationTime(""); // Empty duration time

        Exception exception = assertThrows(InvalidExamException.class, () -> {
            examService.createExam(exam);
        });

        assertEquals("考试限时不可为空！", exception.getMessage());
    }

    @Test
    public void testCreateExamNegativeDurationTime() {
        Exam exam = new Exam();
        exam.setName("Example Exam");
        exam.setPaperId(1L);
        exam.setPublisher("John Doe");
        exam.setStartTime("2024-06-29T15:44:34.995+08:00");
        exam.setEndTime("2024-06-30T15:44:34.995+08:00");
        exam.setDurationTime("-60"); // Negative duration time

        Exception exception = assertThrows(InvalidDurationTimeException.class, () -> {
            examService.createExam(exam);
        });

        assertEquals("考试限时必须是一个正整数！", exception.getMessage());
    }

    @Test
    public void testCreateExamDurationExceedsExamTime() {
        Exam exam = new Exam();
        exam.setName("Example Exam");
        exam.setPaperId(1L);
        exam.setPublisher("John Doe");
        exam.setStartTime("2024-06-29T15:44:34.995+08:00");
        exam.setEndTime("2024-06-30T15:44:34.995+08:00");
        exam.setDurationTime("36000"); // Duration exceeds exam time (24 hours)

        Exception exception = assertThrows(DurationExceedsExamTimeException.class, () -> {
            examService.createExam(exam);
        });

        assertEquals("限时超出了考试持续时间！", exception.getMessage());
    }

    @Test
    public void testCreateExamInvalidDurationTimeFormat() {
        Exam exam = new Exam();
        exam.setName("Example Exam");
        exam.setPaperId(1L);
        exam.setPublisher("John Doe");
        exam.setStartTime("2024-06-29T15:44:34.995+08:00");
        exam.setEndTime("2024-06-30T15:44:34.995+08:00");
        exam.setDurationTime("sixty"); // Invalid duration time format

        Exception exception = assertThrows(InvalidDurationTimeException.class, () -> {
            examService.createExam(exam);
        });

        assertEquals("考试限时必须是一个正整数！", exception.getMessage());
    }

    @Test
    public void testGetAllExamsEmptyResult() {
        Page<Exam> emptyPage = new PageImpl<>(Arrays.asList());
        when(examRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        List<ExamReturnDto> result = examService.getAllExams("studentName", 0, 10);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(examRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testGetNotStartedExamEmptyResult() {
        Page<Exam> emptyPage = new PageImpl<>(Arrays.asList());
        when(examRepository.findNotStartedExams(anyString(), any(Pageable.class))).thenReturn(emptyPage);

        List<ExamReturnDto> result = examService.getNotStartedExam("studentName", 0, 10);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(examRepository, times(1)).findNotStartedExams(anyString(), any(Pageable.class));
    }

    @Test
    public void testGetInProgressExamEmptyResult() {
        Page<Exam> emptyPage = new PageImpl<>(Arrays.asList());
        when(examRepository.findInProgressExams(anyString(), any(Pageable.class))).thenReturn(emptyPage);

        List<ExamReturnDto> result = examService.getInProgressExam("studentName", 0, 10);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(examRepository, times(1)).findInProgressExams(anyString(), any(Pageable.class));
    }

    @Test
    public void testGetFinishedExamEmptyResult() {
        Page<Exam> emptyPage = new PageImpl<>(Arrays.asList());
        when(examRepository.findFinishedExams(anyString(), any(Pageable.class))).thenReturn(emptyPage);

        List<ExamReturnDto> result = examService.getFinishedExam("studentName", 0, 10);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(examRepository, times(1)).findFinishedExams(anyString(), any(Pageable.class));
    }

    @Test
    public void testGetAllExamsPagesNumEmptyResult() {
        when(examRepository.findAll()).thenReturn(Arrays.asList());

        long result = examService.getAllExamsPagesNum();

        assertEquals(0, result);
        verify(examRepository, times(1)).findAll();
    }

    @Test
    public void testGetNotStartedPagesNumEmptyResult() {
        when(examRepository.findAll()).thenReturn(Arrays.asList());

        long result = examService.getNotStartedPagesNum();

        assertEquals(0, result);
        verify(examRepository, times(1)).findAll();
    }

    @Test
    public void testGetInProgressPagesNumEmptyResult() {
        when(examRepository.findAll()).thenReturn(Arrays.asList());

        long result = examService.getInProgressPagesNum();

        assertEquals(0, result);
        verify(examRepository, times(1)).findAll();
    }

    @Test
    public void testGetFinishedPagesNumEmptyResult() {
        when(examRepository.findAll()).thenReturn(Arrays.asList());

        long result = examService.getFinishedPagesNum();

        assertEquals(0, result);
        verify(examRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteExamNotFound() {
        when(examRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ExamNotFoundException.class, () -> {
            examService.deleteExam(1L);
        });

        assertEquals("Exam not found with id: 1", exception.getMessage());
    }

    @Test
    public void testDeleteExamNullId() {
        Exception exception = assertThrows(ExamNotFoundException.class, () -> {
            examService.deleteExam(null);
        });

        assertEquals("Exam not found with id: null", exception.getMessage());
    }

    // 测试 "试卷不可为空！" 异常抛出
    @Test
    public void testCreateExamInvalidPaperId() {
        Exam exam = new Exam();
        exam.setName("Example Exam");
        exam.setPublisher("John Doe");
        exam.setStartTime("2024-06-29T15:44:34.995+08:00");
        exam.setEndTime("2024-06-30T15:44:34.995+08:00");
        exam.setDurationTime("60");

        Exception exception = assertThrows(InvalidExamException.class, () -> {
            examService.createExam(exam);
        });

        assertEquals("试卷不可为空！", exception.getMessage());
    }

    // 测试 "发布人不可为空！" 异常抛出
    @Test
    public void testCreateExamInvalidPublisher() {
        Exam exam = new Exam();
        exam.setName("Example Exam");
        exam.setPaperId(1L);
        exam.setStartTime("2024-06-29T15:44:34.995+08:00");
        exam.setEndTime("2024-06-30T15:44:34.995+08:00");
        exam.setDurationTime("60");

        Exception exception = assertThrows(InvalidExamException.class, () -> {
            examService.createExam(exam);
        });

        assertEquals("发布人不可为空！", exception.getMessage());
    }

    // 测试 "已结束" 状态计数逻辑
    @Test
    public void testGetFinishedPagesNumCount() {
        ExamReturnDto exam1 = new ExamReturnDto();
        exam1.setStatus("已结束");
        ExamReturnDto exam2 = new ExamReturnDto();
        exam2.setStatus("进行中");
        ExamReturnDto exam3 = new ExamReturnDto();
        exam3.setStatus("未开始");
        ExamReturnDto exam4 = new ExamReturnDto();
        exam4.setStatus("已结束");

        List<ExamReturnDto> exams = Arrays.asList(exam1, exam2, exam3, exam4);
        List<Exam> e = new ArrayList<>();
        when(examListToDtoListUtil.convertExamListToDtoList(null, e, null)).thenReturn(exams);
        long result = examService.getFinishedPagesNum();

        assertEquals(2, result);
    }

    // 测试 "进行中" 状态计数逻辑
    @Test
    public void testGetInProgressPagesNumCount() {
        ExamReturnDto exam1 = new ExamReturnDto();
        exam1.setStatus("已结束");
        ExamReturnDto exam2 = new ExamReturnDto();
        exam2.setStatus("进行中");
        ExamReturnDto exam3 = new ExamReturnDto();
        exam3.setStatus("未开始");
        ExamReturnDto exam4 = new ExamReturnDto();
        exam4.setStatus("进行中");

        List<ExamReturnDto> exams = Arrays.asList(exam1, exam2, exam3, exam4);
        List<Exam> e = new ArrayList<>();
        when(examListToDtoListUtil.convertExamListToDtoList(null, e, null)).thenReturn(exams);

        long result = examService.getInProgressPagesNum();

        assertEquals(2, result);
    }

    // 测试 "未开始" 状态计数逻辑
    @Test
    public void testGetNotStartedPagesNumCount() {
        ExamReturnDto exam1 = new ExamReturnDto();
        exam1.setStatus("已结束");
        ExamReturnDto exam2 = new ExamReturnDto();
        exam2.setStatus("进行中");
        ExamReturnDto exam3 = new ExamReturnDto();
        exam3.setStatus("未开始");
        ExamReturnDto exam4 = new ExamReturnDto();
        exam4.setStatus("已结束");

        List<ExamReturnDto> exams = Arrays.asList(exam1, exam2, exam3, exam4);
        List<Exam> e = new ArrayList<>();
        when(examListToDtoListUtil.convertExamListToDtoList(null, e, null)).thenReturn(exams);

        long result = examService.getNotStartedPagesNum();

        assertEquals(1, result);
    }
}

