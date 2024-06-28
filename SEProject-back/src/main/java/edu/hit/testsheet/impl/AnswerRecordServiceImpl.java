package edu.hit.testsheet.impl;

import edu.hit.testsheet.exception.AnswerRecordNotFoundException;
import edu.hit.testsheet.bean.AnswerRecord;
import edu.hit.testsheet.repository.AnswerRecordRepository;
import edu.hit.testsheet.service.AnswerRecordService;
import edu.hit.testsheet.service.QuestionService;
import edu.hit.testsheet.util.CalculateQuestionsScoreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:AnswerRecordImpl
 * Package:edu.hit.testsheet.impl
 * Description:
 *
 * @date:2024/6/22 21:36
 * @author:shyboy
 */
@Service
public class AnswerRecordServiceImpl implements AnswerRecordService {

    @Autowired
    private AnswerRecordRepository answerRecordRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CalculateQuestionsScoreUtil calculateQuestionsScoreUtil;

    @Override
    public List<AnswerRecord> getAnswerRecordByStudentNameAndExamId(String studentName, Long examId) {
        List<AnswerRecord> answerRecords = answerRecordRepository.findByStudentNameAndExamId(studentName, examId);
        if (answerRecords.isEmpty()) {
            throw new AnswerRecordNotFoundException(studentName, examId);
        }
        return answerRecords;
    }

    @Override
    public List<AnswerRecord> automaticMarking(String studentName, Long examId) {
        List<AnswerRecord> answerRecords = getAnswerRecordByStudentNameAndExamId(studentName, examId);
        for (AnswerRecord a : answerRecords) {
            String questionType = questionService.selectQuestionById(a.getQuestionId()).getType();
            String rightAnswer = questionService.selectQuestionById(a.getQuestionId()).getAnswer();
            if (questionType.equals("选择题") || questionType.equals("填空题") || questionType.equals("判断题")) {
                if (rightAnswer.equals(a.getStudentAnswer())) {
                    if (questionType.equals("选择题")) {
                        updateGrade(studentName, examId, a.getQuestionId(), "3");
                    } else if (questionType.equals("填空题")) {
                        updateGrade(studentName, examId, a.getQuestionId(), "5");
                    } else {
                        updateGrade(studentName, examId, a.getQuestionId(), "3");
                    }
                } else {
                    updateGrade(studentName, examId, a.getQuestionId(), "0");
                }
            }
        }
        return getAnswerRecordByStudentNameAndExamId(studentName, examId);
    }

    @Override
    public long[] calculateObjAndSubGrades(String studentName, Long examId) {
        List<AnswerRecord> answerRecords = getAnswerRecordByStudentNameAndExamId(studentName, examId);
        return calculateQuestionsScoreUtil.calculateScore(answerRecords);
    }

    @Override
    public AnswerRecord updateGrade(String studentName, Long examId, Long questionId, String grade) {
        AnswerRecord answerRecord = answerRecordRepository.
                findByStudentNameAndExamIdAndQuestionId(studentName, examId, questionId).
                orElseThrow(() -> new AnswerRecordNotFoundException(studentName, examId, questionId));
        answerRecord.setGrade(grade);
        return answerRecordRepository.save(answerRecord);
    }

    @Override
    public List<AnswerRecord> submitAnswer(List<AnswerRecord> answerRecords) {
        answerRecordRepository.saveAll(answerRecords);
        return answerRecords;
    }

    @Override
    public boolean existsByStudentNameAndExamId(String studentName, Long examId) {
        return answerRecordRepository.existsByStudentNameAndExamId(studentName, examId);
    }

    @Override
    public boolean existsByExamId(Long examId) {
        return answerRecordRepository.existsByExamId(examId);
    }
}

