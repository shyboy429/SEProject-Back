package edu.hit.testsheet.util;

import edu.hit.testsheet.Dto.ExamReturnDto;
import edu.hit.testsheet.bean.Exam;
import edu.hit.testsheet.service.AnswerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ExamToDtoUtil
 * Package:edu.hit.testsheet.util
 * Description:
 *
 * @date:2024/6/25 21:34
 * @author:shyboy
 */
@Component
public class ExamListToDtoListUtil {
    @Autowired
    private AnswerRecordService answerRecordService;

    public List<ExamReturnDto> convertExamListToDtoList(String studentName, List<Exam> exams, String status) {
        List<ExamReturnDto> ret = new ArrayList<>();
        for (Exam exam : exams) {
            ExamReturnDto e = new ExamReturnDto();
            e.setId(exam.getId());
            e.setName(exam.getName());
            e.setPaperId(exam.getPaperId());
            e.setPublisher(exam.getPublisher());
            e.setStartTime(exam.getStartTime());
            e.setEndTime(exam.getEndTime());
            e.setDurationTime(exam.getDurationTime());
            if (status == null) {
                if (DateFormatterUtil.isBeforeCurrentTime(exam.getEndTime())) {
                    e.setStatus("已结束");
                } else if (DateFormatterUtil.isBeforeCurrentTime(exam.getStartTime())) {
                    e.setStatus("进行中");
                } else {
                    e.setStatus("未开始");
                }
            } else {
                e.setStatus(status);
            }
            if (studentName != null) {
                if (answerRecordService.existsByStudentNameAndExamId(studentName, exam.getId())) {
                    e.setAnswerStatus("已完成");
                    long[] objAndSubGrades = answerRecordService.calculateObjAndSubGrades(studentName, exam.getId());
                    e.setObjAndSubGrade(objAndSubGrades);
                }
            } else {
                e.setAnswerStatus("当前为管理员或老师");
            }
            ret.add(e);
        }
        return ret;
    }
}
