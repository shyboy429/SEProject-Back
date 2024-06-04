package edu.hit.testsheet.controller;

import edu.hit.testsheet.Dto.PaperUpdateDto;
import edu.hit.testsheet.Exception.PaperNotFoundException;
import edu.hit.testsheet.bean.Paper;
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

    // 获取所有试卷
    @GetMapping
    public List<Paper> getAllPapers() {
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
    public Paper getPaperById(@PathVariable Long id) {
        return paperService.selectPaperById(id);
    }

    // 更新试卷
    @PutMapping("/{id}")
    public Paper updatePaper(@PathVariable Long id, @RequestBody PaperUpdateDto paperUpdateDto) {
        return paperService.updatePaper(id, paperUpdateDto);
    }
}
