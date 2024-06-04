package edu.hit.testsheet.controller;

import edu.hit.testsheet.Dto.QuestionUpdateDto;
import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<Question> getAllQuestions(@RequestParam(required = false, defaultValue = "1") String pageNum) {
        int pageSize = 6; // 每页显示的数据条数
        int pageIndex = Integer.parseInt(pageNum) - 1; // 计算页码
        System.out.println("Request Page Num: " + pageNum);
        System.out.println("Calculated Page Index: " + pageIndex);
        return questionService.getQuestionsByPage(pageIndex, pageSize);
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

    // 搜索问题
    @GetMapping("/search")
    public List<Question> searchQuestions(@RequestParam(required = false) String keywords,
                                          @RequestParam(required = false) String type,
                                          @RequestParam(required = false) String difficultLevel,
                                          @RequestParam(required = false, defaultValue = "1") String pageNum) {
        int pageSize = 6; // 每页显示的数据条数
        int pageIndex = Integer.parseInt(pageNum) - 1; // 计算页码
        System.out.println("Request Page Num: " + pageNum);
        System.out.println("Calculated Page Index: " + pageIndex);
        return questionService.selectQuestion(keywords, type, difficultLevel, pageIndex, pageSize);
    }
}
