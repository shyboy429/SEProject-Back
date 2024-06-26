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

    @GetMapping("/student-name/{student-name}")
    public List<ExamReturnDto> getAllExams(@PathVariable String studentName,
                                           @RequestParam(required = false, defaultValue = "1") String pageNum) {
        return examService.getAllExams(studentName,Integer.parseInt(pageNum) - 1, 10);
    }

    @GetMapping("/student-name/{student-name}/not-started")
    public List<ExamReturnDto> getNotStartedExam(@PathVariable String studentName,
                                                 @RequestParam(required = false, defaultValue = "1") String pageNum) {
        return examService.getNotStartedExam(studentName,Integer.parseInt(pageNum) - 1, 10);
    }

    @GetMapping("/student-name/{student-name}/in-progress")
    public List<ExamReturnDto> getInProgressExam(@PathVariable String studentName,
                                                 @RequestParam(required = false, defaultValue = "1") String pageNum) {
        return examService.getInProgressExam(studentName,Integer.parseInt(pageNum) - 1, 10);
    }

    @GetMapping("/student-name/{student-name}/finished")
    public List<ExamReturnDto> getFinishedExam(@PathVariable String studentName,
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

    @PostMapping
    public Exam createExam(@RequestBody Exam exam) {
        return examService.createExam(exam);
    }

    @DeleteMapping("/{id}")
    public void deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
    }

    @GetMapping("/page-num")
    public long getAllExamsPagesNum() {
        return examService.getAllExamsPagesNum();
    }

    @GetMapping("/not-started/page-num")
    public long getNotStartedPagesNum() {
        return examService.getNotStartedPagesNum();
    }

    @GetMapping("/in-progress/page-num")
    public long getInProgressPagesNum() {
        return examService.getInProgressPagesNum();
    }

    @GetMapping("/finished/page-num")
    public long getFinishedPagesNum() {
        return examService.getFinishedPagesNum();
    }
}

