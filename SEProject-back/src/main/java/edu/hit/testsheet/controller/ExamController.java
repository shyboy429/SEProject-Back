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

    @GetMapping
    public List<ExamReturnDto> getAllExams(@RequestParam(required = false, defaultValue = "1") String pageNum) {
        return examService.getAllExams(Integer.parseInt(pageNum) - 1,10);
    }

    @GetMapping("/not-started")
    public List<ExamReturnDto> getNotStartedExam(@RequestParam(required = false, defaultValue = "1") String pageNum){
        return examService.getNotStartedExam(Integer.parseInt(pageNum) - 1,10);
    }
    @GetMapping("/in-progress")
    public List<ExamReturnDto> getInProgressExam(@RequestParam(required = false, defaultValue = "1") String pageNum){
        return examService.getInProgressExam(Integer.parseInt(pageNum) - 1,10);
    }
    @GetMapping("/finished")
    public List<ExamReturnDto> getFinishedExam(@RequestParam(required = false, defaultValue = "1") String pageNum){
        return examService.getFinishedExam(Integer.parseInt(pageNum) - 1,10);
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
}

