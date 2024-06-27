package edu.hit.testsheet.util;

import edu.hit.testsheet.bean.AnswerRecord;
import edu.hit.testsheet.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName:CaculateObjectiveQuestionScoreUtil
 * Package:edu.hit.testsheet.util
 * Description:
 *
 * @date:2024/6/22 21:44
 * @author:shyboy
 */
@Component
public class CalculateQuestionsScoreUtil {
    @Autowired
    private QuestionService questionService;

    public long[] calculateScore(List<AnswerRecord> records) {
        long objectiveScore = 0;
        long subjectiveScore = 0;
        for (AnswerRecord a : records) {
            if (questionService.selectQuestionById(a.getQuestionId()).getType().equals("选择题")
                    || questionService.selectQuestionById(a.getQuestionId()).getType().equals("填空题")
                    || questionService.selectQuestionById(a.getQuestionId()).getType().equals("判断题")) {
                objectiveScore += Long.parseLong(a.getGrade());
            } else {
                subjectiveScore += Long.parseLong(a.getGrade());
            }
        }
        return new long[]{objectiveScore, subjectiveScore};
    }
}
