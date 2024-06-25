package edu.hit.testsheet.service;

import edu.hit.testsheet.Dto.ExamReturnDto;
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
    List<ExamReturnDto> getAllExams(int pageIndex, int pageSize);
    Exam getExamById(Long id);
    Exam getExamByName(String name);
    Exam createExam(Exam exam);
    void deleteExam(Long id);
    List<ExamReturnDto> getNotStartedExam(int pageIndex, int pageSize);
    List<ExamReturnDto> getInProgressExam(int pageIndex, int pageSize);
    List<ExamReturnDto> getFinishedExam(int pageIndex, int pageSize);
}
