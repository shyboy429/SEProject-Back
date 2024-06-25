package edu.hit.testsheet.util;

import edu.hit.testsheet.Dto.ExamReturnDto;
import edu.hit.testsheet.bean.Exam;

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
public class ExamListToDtoListUtil {
    public static List<ExamReturnDto> convertExamListToDtoList(List<Exam> exams, String status) {
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
            e.setStatus(status);
            ret.add(e);
        }
        return ret;
    }
}
