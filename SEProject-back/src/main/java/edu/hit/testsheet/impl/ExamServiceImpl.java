package edu.hit.testsheet.impl;

import edu.hit.testsheet.dto.ExamReturnDto;
import edu.hit.testsheet.exception.*;
import edu.hit.testsheet.bean.Exam;
import edu.hit.testsheet.repository.ExamRepository;
import edu.hit.testsheet.service.AnswerRecordService;
import edu.hit.testsheet.service.ExamService;
import edu.hit.testsheet.util.DateFormatterUtil;
import edu.hit.testsheet.util.ExamListToDtoListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ClassName:ExamServiceImpl
 * Package:edu.hit.testsheet.impl
 * Description:
 *
 * @date:2024/6/22 17:18
 * @author:shyboy
 */
@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private ExamListToDtoListUtil examListToDtoListUtil;
    @Autowired
    private AnswerRecordService answerRecordService;

    @Override
    public List<ExamReturnDto> getAllExams(String studentName, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Exam> paperPage = examRepository.findAll(pageable);
        List<Exam> allExams = paperPage.getContent();
        return examListToDtoListUtil.convertExamListToDtoList(studentName, allExams, null);
    }

    @Override
    public List<ExamReturnDto> getNotStartedExam(String studentName, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        String currentTime = DateFormatterUtil.getCurrentTimeString();
        Page<Exam> paperPage = examRepository.findNotStartedExams(currentTime, pageable);
        return examListToDtoListUtil.convertExamListToDtoList(studentName, paperPage.getContent(), "未开始");
    }

    @Override
    public List<ExamReturnDto> getInProgressExam(String studentName, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        String currentTime = DateFormatterUtil.getCurrentTimeString();
        Page<Exam> paperPage = examRepository.findInProgressExams(currentTime, pageable);
        return examListToDtoListUtil.convertExamListToDtoList(studentName, paperPage.getContent(), "进行中");
    }

    @Override
    public List<ExamReturnDto> getFinishedExam(String studentName, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        String currentTime = DateFormatterUtil.getCurrentTimeString();
        Page<Exam> paperPage = examRepository.findFinishedExams(currentTime, pageable);
        return examListToDtoListUtil.convertExamListToDtoList(studentName, paperPage.getContent(), "已结束");
    }

    @Override
    public long getAllExamsPagesNum() {
        List<Exam> allExams = examRepository.findAll();
        List<ExamReturnDto> exams = examListToDtoListUtil.convertExamListToDtoList(null, allExams, null);
        return exams.size();
    }

    @Override
    public long getNotStartedPagesNum() {
        List<Exam> allExams = examRepository.findAll();
        List<ExamReturnDto> exams = examListToDtoListUtil.convertExamListToDtoList(null, allExams, null);
        long ret = 0;
        for (ExamReturnDto e : exams) {
            if (e.getStatus().equals("未开始")) {
                ret++;
            }
        }
        return ret;
    }

    @Override
    public long getInProgressPagesNum() {
        List<Exam> allExams = examRepository.findAll();
        List<ExamReturnDto> exams = examListToDtoListUtil.convertExamListToDtoList(null, allExams, null);
        long ret = 0;
        for (ExamReturnDto e : exams) {
            if (e.getStatus().equals("进行中")) {
                ret++;
            }
        }
        return ret;
    }

    @Override
    public long getFinishedPagesNum() {
        List<Exam> allExams = examRepository.findAll();
        List<ExamReturnDto> exams = examListToDtoListUtil.convertExamListToDtoList(null, allExams, null);
        long ret = 0;
        for (ExamReturnDto e : exams) {
            if (e.getStatus().equals("已结束")) {
                ret++;
            }
        }
        return ret;
    }

    @Override
    public Exam getExamById(Long id) {
        Optional<Exam> exam = examRepository.findById(id);
        return exam.orElseThrow(() -> new ExamNotFoundException(id));
    }


    @Override
    public Exam createExam(Exam exam) {
        if (exam.getName() == null || exam.getName().isEmpty()) {
            throw new InvalidExamException("考试名称不可为空！");
        }
        if (exam.getPaperId() == null) {
            throw new InvalidExamException("试卷不可为空！");
        }
        if (exam.getPublisher() == null || exam.getPublisher().isEmpty()) {
            throw new InvalidExamException("发布人不可为空！");
        }
        if (exam.getStartTime() == null || exam.getStartTime().isEmpty()) {
            throw new InvalidExamException("开始时间不可为空！");
        }
        if (exam.getEndTime() == null || exam.getEndTime().isEmpty()) {
            throw new InvalidExamException("结束时间不可为空！");
        }
        if (exam.getDurationTime() == null || exam.getDurationTime().isEmpty()) {
            throw new InvalidExamException("考试限时不可为空！");
        }
        exam.setStartTime(DateFormatterUtil.frontFormatDate(exam.getStartTime()));
        exam.setEndTime(DateFormatterUtil.frontFormatDate(exam.getEndTime()));
        if (DateFormatterUtil.isBeforeCurrentTime(exam.getStartTime())) {
            throw new InvalidExamStartTimeException("考试开始时间不可早于当前时间！");
        }
        long durationInMinutes = DateFormatterUtil.calculateDurationInMinutes(exam.getStartTime(), exam.getEndTime());
        try {
            int duration = Integer.parseInt(exam.getDurationTime());
            if (duration <= 0) {
                throw new InvalidDurationTimeException("考试限时必须是一个正整数！");
            }
            if (duration > durationInMinutes) {
                throw new DurationExceedsExamTimeException("限时超出了考试持续时间！");
            }
        } catch (NumberFormatException e) {
            throw new InvalidDurationTimeException("考试限时必须是一个正整数！");
        }

        return examRepository.save(exam);
    }

    @Override
    public void deleteExam(Long id) {
        Exam exam = getExamById(id);
        if (answerRecordService.existsByExamId(id)) {
            throw new ExamCanNotBeDeletedException("\"" + exam.getName() + "\" 已有考试记录，不可删除！");
        }
        examRepository.delete(exam);
    }
}
