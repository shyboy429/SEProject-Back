package edu.hit.testsheet.service;

import edu.hit.testsheet.bean.Exam;

import java.util.List;

/**
 * ClassName:ExamService
 * Package:edu.hit.testsheet.service
 * Description:
 *
 * @date:2024/6/22 17:16
 * @author:shyboy
 */
public interface ExamService {
    List<Exam> getAllExams();
    Exam getExamById(Long id);
    Exam getExamByName(String name);
    Exam createExam(Exam exam);
    Exam updateExam(Long id, Exam examDetails);
    void deleteExam(Long id);
}
