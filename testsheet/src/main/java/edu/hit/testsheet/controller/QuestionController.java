package edu.hit.testsheet.controller;

import edu.hit.testsheet.Dto.QuestionUpdateDto;
import edu.hit.testsheet.Exception.QuestionNotFoundException;
import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.service.QuestionService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // 获取所有问题
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // 添加问题
    @PostMapping
    public Question addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    // 根据ID删除问题
    @DeleteMapping("/{id}")
    public void deleteQuestionById(@PathVariable Long id) {
        questionService.deleteQuestionById(id);
    }

    // 根据ID获取问题
    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable Long id) {
        return questionService.selectQuestionById(id);
    }

    // 更新问题
    @PutMapping("/{id}")
    public Question updateQuestion(@PathVariable Long id,
                                   @RequestBody QuestionUpdateDto updateRequest) {
        return questionService.updateQuestion(id, updateRequest);
    }

//    // 异常处理
//    @ExceptionHandler(QuestionNotFoundException.class)
//    public String handleQuestionNotFoundException(QuestionNotFoundException ex) {
//        return ex.getMessage();
//    }
}
