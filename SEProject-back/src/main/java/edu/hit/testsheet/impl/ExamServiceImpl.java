package edu.hit.testsheet.impl;

import edu.hit.testsheet.Dto.ExamReturnDto;
import edu.hit.testsheet.Exception.ExamNotFoundException;
import edu.hit.testsheet.Exception.InvalidExamStartTimeException;
import edu.hit.testsheet.bean.Exam;
import edu.hit.testsheet.bean.Paper;
import edu.hit.testsheet.repository.ExamRepository;
import edu.hit.testsheet.service.ExamService;
import edu.hit.testsheet.util.DateFormatterUtil;
import edu.hit.testsheet.util.ExamListToDtoListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    

    @Override
    public List<ExamReturnDto> getAllExams(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Exam> paperPage = examRepository.findAll(pageable);
        List<Exam> allExams = paperPage.getContent();
        List<ExamReturnDto> ret = new ArrayList<>();
        for(Exam exam : allExams){
            ExamReturnDto e = new ExamReturnDto();
            e.setId(exam.getId());
            e.setName(exam.getName());
            e.setPaperId(exam.getPaperId());
            e.setPublisher(exam.getPublisher());
            e.setStartTime(exam.getStartTime());
            e.setEndTime(exam.getEndTime());
            e.setDurationTime(exam.getDurationTime());
            if(DateFormatterUtil.isBeforeCurrentTime(exam.getEndTime())){
                e.setStatus("已结束");
            }else if(DateFormatterUtil.isBeforeCurrentTime(exam.getStartTime())){
                e.setStatus("进行中");
            }else{
                e.setStatus("未开始");
            }
            ret.add(e);
        }
        return ret;
    }

    @Override
    public List<ExamReturnDto> getNotStartedExam(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        String currentTime = DateFormatterUtil.getCurrentTimeString();
        Page<Exam> paperPage = examRepository.findNotStartedExams(currentTime, pageable);
        return ExamListToDtoListUtil.convertExamListToDtoList(paperPage.getContent(), "未开始");
    }

    @Override
    public List<ExamReturnDto> getInProgressExam(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        String currentTime = DateFormatterUtil.getCurrentTimeString();
        Page<Exam> paperPage = examRepository.findInProgressExams(currentTime, pageable);
        return ExamListToDtoListUtil.convertExamListToDtoList(paperPage.getContent(), "进行中");
    }

    @Override
    public List<ExamReturnDto> getFinishedExam(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        String currentTime = DateFormatterUtil.getCurrentTimeString();
        Page<Exam> paperPage = examRepository.findFinishedExams(currentTime, pageable);
        return ExamListToDtoListUtil.convertExamListToDtoList(paperPage.getContent(), "已结束");
    }
    
    @Override
    public Exam getExamById(Long id) {
        Optional<Exam> exam = examRepository.findById(id);
        return exam.orElseThrow(() -> new ExamNotFoundException(id));
    }

    @Override
    public Exam getExamByName(String name) {
        Optional<Exam> exam = examRepository.findByName(name);
        return exam.orElseThrow(() -> new ExamNotFoundException(name));
    }

    @Override
    public Exam createExam(Exam exam) {
        if(DateFormatterUtil.isBeforeCurrentTime(exam.getStartTime())){
            throw new InvalidExamStartTimeException("Exam start time cannot be before the current time.");
        }
        exam.setStartTime(DateFormatterUtil.frontFormatDate(exam.getStartTime()));
        exam.setEndTime(DateFormatterUtil.frontFormatDate(exam.getEndTime()));
        return examRepository.save(exam);
    }

    @Override
    public void deleteExam(Long id) {
        Exam exam = getExamById(id);
        examRepository.delete(exam);
    }
}
