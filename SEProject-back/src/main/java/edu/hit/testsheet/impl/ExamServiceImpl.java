package edu.hit.testsheet.impl;

import edu.hit.testsheet.Exception.ExamNotFoundException;
import edu.hit.testsheet.bean.Exam;
import edu.hit.testsheet.repository.ExamRepository;
import edu.hit.testsheet.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();
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
        return examRepository.save(exam);
    }

    @Override
    public Exam updateExam(Long id, Exam examDetails) {
        Exam exam = getExamById(id);
        exam.setName(examDetails.getName());
        exam.setPaperId(examDetails.getPaperId());
        exam.setPublisher(examDetails.getPublisher());
        exam.setStartTime(examDetails.getStartTime());
        exam.setEndTime(examDetails.getEndTime());
        exam.setDurationTime(examDetails.getDurationTime());
        return examRepository.save(exam);
    }

    @Override
    public void deleteExam(Long id) {
        Exam exam = getExamById(id);
        examRepository.delete(exam);
    }
}
