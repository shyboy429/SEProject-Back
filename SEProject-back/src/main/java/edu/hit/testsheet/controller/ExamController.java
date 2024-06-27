package edu.hit.testsheet.controller;

import edu.hit.testsheet.Dto.ExamReturnDto;
import edu.hit.testsheet.bean.Exam;
import edu.hit.testsheet.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:ExamController
 * Package:edu.hit.testsheet.controller
 * Description:
 *
 * @date:2024/6/22 17:30
 * @author:shyboy
 */
@RestController
@RequestMapping("/api/exams")
public class ExamController {
    @Autowired
    private ExamService examService;


    /**
     * 获取该学生所有考试的信息
     * @param studentName
     * @param pageNum
     * @return
     */
    @GetMapping("/student-name")
    public List<ExamReturnDto> getAllExams(@RequestParam(required = false) String studentName,
                                           @RequestParam(required = false, defaultValue = "1") String pageNum) {
        return examService.getAllExams(studentName,Integer.parseInt(pageNum) - 1, 10);
    }

    /**
     * 获取该学生未开始考试的信息
     * @param studentName
     * @param pageNum
     * @return
     */
    @GetMapping("/student-name/not-started")
    public List<ExamReturnDto> getNotStartedExam(@RequestParam(required = false) String studentName,
                                                 @RequestParam(required = false, defaultValue = "1") String pageNum) {
        return examService.getNotStartedExam(studentName,Integer.parseInt(pageNum) - 1, 10);
    }

    /**
     * 获取该学生进行中考试的信息
     * @param studentName
     * @param pageNum
     * @return
     */
    @GetMapping("/student-name/in-progress")
    public List<ExamReturnDto> getInProgressExam(@RequestParam(required = false) String studentName,
                                                 @RequestParam(required = false, defaultValue = "1") String pageNum) {
        return examService.getInProgressExam(studentName,Integer.parseInt(pageNum) - 1, 10);
    }

    /**
     * 获取该学生已结束考试的信息
     * @param studentName
     * @param pageNum
     * @return
     */
    @GetMapping("/student-name/finished")
    public List<ExamReturnDto> getFinishedExam(@RequestParam(required = false) String studentName,
                                               @RequestParam(required = false, defaultValue = "1") String pageNum) {
        return examService.getFinishedExam(studentName,Integer.parseInt(pageNum) - 1, 10);
    }

    @GetMapping("/id/{id}")
    public Exam getExamById(@PathVariable Long id) {
        return examService.getExamById(id);
    }

    @GetMapping("/name/{name}")
    public Exam getExamByName(@PathVariable String name) {
        return examService.getExamByName(name);
    }

    /**
     * 管理员发布一场新的考试
     * @param exam
     * @return
     */
    @PostMapping
    public Exam createExam(@RequestBody Exam exam) {
        return examService.createExam(exam);
    }
    
    
    @DeleteMapping("/{id}")
    public void deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
    }

    /**
     * 获取所有考试记录条数
     * @return
     */
    @GetMapping("/page-num")
    public long getAllExamsPagesNum() {
        return examService.getAllExamsPagesNum();
    }

    /**
     * 获取未开始考试记录条数
     * @return
     */
    @GetMapping("/not-started/page-num")
    public long getNotStartedPagesNum() {
        return examService.getNotStartedPagesNum();
    }

    /**
     * 获取进行中考试记录条数
     * @return
     */
    @GetMapping("/in-progress/page-num")
    public long getInProgressPagesNum() {
        return examService.getInProgressPagesNum();
    }

    /**
     * 获取已结束考试记录条数
     * @return
     */
    @GetMapping("/finished/page-num")
    public long getFinishedPagesNum() {
        return examService.getFinishedPagesNum();
    }
}

