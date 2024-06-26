package edu.hit.testsheet.controller;

import edu.hit.testsheet.dto.QuestionUpdateDto;
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

    /**
     * 获取所有题目详细信息
     *
     * @param pageNum
     * @return 所有题目详细信息
     */
    @GetMapping
    public List<Question> getAllQuestions(@RequestParam(required = false, defaultValue = "1") String pageNum) {
        return questionService.getQuestionsByPage(Integer.parseInt(pageNum) - 1, 10);
    }

    /**
     * 添加新的 question
     *
     * @param question
     * @return 新的 question
     */
    @PostMapping
    public Question addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    /**
     * 根据 id删除问题
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Long deleteQuestionById(@PathVariable Long id) {
        questionService.deleteQuestionById(id);
        return id;
    }

    /**
     * 根据 id获取问题
     *
     * @param id
     * @return id对应的的问题
     */
    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable Long id) {
        return questionService.selectQuestionById(id);
    }

    /**
     * 根据 id更新 question的 description、type、answer、tag、analysis、difficultLevel;
     *
     * @param id
     * @param updateRequest
     * @return 更新后的 question
     */
    @PostMapping("/{id}")
    public Question updateQuestion(@PathVariable Long id,
                                   @RequestBody QuestionUpdateDto updateRequest) {
        return questionService.updateQuestion(id, updateRequest);
    }

    /**
     * 根据筛选条件查询相应问题
     *
     * @param keywords
     * @param type
     * @param difficultLevel
     * @param username
     * @param pageNum
     * @return 满足条件的问题列表
     */
    @GetMapping("/search")
    public List<Question> searchQuestions(@RequestParam(required = false) String keywords,
                                          @RequestParam(required = false) String type,
                                          @RequestParam(required = false) String difficultLevel,
                                          @RequestParam(required = false) String username,
                                          @RequestParam(required = false, defaultValue = "0") String pageNum,
                                          @RequestParam(required = false) String orderAttribute,
                                          @RequestParam(required = false) String order) {
        return questionService.selectQuestion(keywords, type, difficultLevel, username,
                Integer.parseInt(pageNum) - 1, 10, orderAttribute, order);
    }

    /**
     * 获取当前问题共有多少条。
     *
     * @return
     */
    @GetMapping("/pageNum")
    public long getQuestionsTotalPagesNum() {
        return questionService.getQuestionsCount();
    }
}
