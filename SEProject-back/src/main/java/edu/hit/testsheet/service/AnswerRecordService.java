package edu.hit.testsheet.service;

import edu.hit.testsheet.bean.AnswerRecord;
import edu.hit.testsheet.repository.AnswerRecordRepository;

import java.util.List;

/**
 * ClassName:AnswerRecordService
 * Package:edu.hit.testsheet.service
 * Description:
 *
 * @date:2024/6/22 17:53
 * @author:shyboy
 */
public interface AnswerRecordService {

    List<AnswerRecord> getAnswerRecordByStudentNameAndExamId(String studentName, Long examId);
    
    List<AnswerRecord> automaticMarking(String studentName, Long examId);

    long[] calculateObjAndSubGrades(String studentName, Long examId);

    AnswerRecord updateGrade(String studentName, Long examId, Long questionId, String grade);

    List<AnswerRecord> submitAnswer(List<AnswerRecord> answerRecords);

    boolean existsByStudentNameAndExamId(String studentName, Long examId);

    boolean existsByExamId(Long examId);
}

