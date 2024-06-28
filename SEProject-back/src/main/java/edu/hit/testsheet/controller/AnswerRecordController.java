package edu.hit.testsheet.controller;

import edu.hit.testsheet.bean.AnswerRecord;
import edu.hit.testsheet.service.AnswerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:AnswerRecordController
 * Package:edu.hit.testsheet.controller
 * Description:
 *
 * @date:2024/6/23 23:00
 * @author:shyboy
 */
@RestController
@RequestMapping("/api/answer-record")
public class AnswerRecordController {
    @Autowired
    private AnswerRecordService answerRecordService;


    @PostMapping("/submit-answer")
    List<AnswerRecord> submitAnswer(@RequestBody List<AnswerRecord> answerRecords) {
        answerRecordService.submitAnswer(answerRecords);
        return answerRecordService.automaticMarking(answerRecords.get(0).getStudentName(),
                answerRecords.get(0).getExamId());
    }

    @PutMapping("/grade")
    public AnswerRecord updateGrade(
            @RequestParam String studentName,
            @RequestParam Long examId,
            @RequestParam Long questionId,
            @RequestParam String grade) {
        return answerRecordService.updateGrade(studentName, examId, questionId, grade);
    }
}
