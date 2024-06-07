package edu.hit.testsheet.controller;

import edu.hit.testsheet.Dto.PaperReturnDto;
import edu.hit.testsheet.Dto.PaperUpdateDto;
import edu.hit.testsheet.bean.Paper;
import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:PaperController
 * Package:edu.hit.testsheet.controller
 * Description:
 *
 * @date:2024/6/3 16:12
 * @author:shyboy
 */
@RestController
@RequestMapping("/api/papers")
public class PaperController {

    @Autowired
    private PaperService paperService;

    // 查看所有试卷
    @GetMapping
    public List<PaperReturnDto> getAllPapers() {
        return paperService.getAllPapers();
    }

    // 添加试卷
    @PostMapping
    public Paper addPaper(@RequestBody Paper paper) {
        return paperService.addPaper(paper);
    }

    // 根据ID删除试卷
    @DeleteMapping("/{id}")
    public void deletePaperById(@PathVariable Long id) {
        paperService.deletePaperById(id);
    }

    // 根据ID获取试卷
    @GetMapping("/{id}")
    public PaperReturnDto getPaperById(@PathVariable Long id) {
        return paperService.selectPaperById(id);
    }

    // 更新试卷
    @PostMapping("/{id}")
    public Paper updatePaper(@PathVariable Long id, @RequestBody PaperUpdateDto paperUpdateDto) {
        return paperService.updatePaper(id, paperUpdateDto);
    }

    // 删除试卷中的某道题
    @PostMapping("/{id}/question/{dqId}")
    public List<Question> deleteQuestionInPaper(@PathVariable Long id, @PathVariable Long dqId) {
        return paperService.deleteQuestionInPaper(id, dqId);
    }

    // admin根据ID获取试卷题目详细具体内容
    @GetMapping("/admin/{id}")
    public List<Question> getPaperByIdForAdmin(@PathVariable Long id) {
        return paperService.selectPaperByIdForAdmin(id);
    }
}
