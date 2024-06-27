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
    List<List<AnswerRecord>> getAnswerRecordByStudentName(String studentName);

    List<AnswerRecord> getAnswerRecordByStudentNameAndExamId(String studentName, Long examId);

    AnswerRecord getAnswerRecordByStudentNameAndExamIdAndQuestionId(String studentName, Long examId, Long questionId);

    List<AnswerRecord> automaticMarking(String studentName, Long examId);

    long[] calculateObjAndSubGrades(String studentName, Long examId);

    AnswerRecord createAnswerRecord(AnswerRecord answerRecord);

    AnswerRecord updateGrade(String studentName, Long examId, Long questionId, String grade);

    AnswerRecord deleteAnswerRecord(Long id);
    List<AnswerRecord> submitAnswer(List<AnswerRecord> answerRecords);

    boolean existsByStudentNameAndExamId(String studentName,Long examId);
}

