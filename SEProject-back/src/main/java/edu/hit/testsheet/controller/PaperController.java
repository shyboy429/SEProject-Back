package edu.hit.testsheet.controller;

import edu.hit.testsheet.Dto.ExamPaperDto;
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

    /**
     * 查看所有试卷
     * @return List<PaperReturnDto> 所有试卷信息
     */
    @GetMapping
    public List<PaperReturnDto> getAllPapers(@RequestParam(required = false, defaultValue = "1") String pageNum) {
        return paperService.getAllPapers(Integer.parseInt(pageNum) - 1, 12);
    }

    
    /**
     * 添加试卷
     * @param paper
     * @return Paper 添加的试卷信息
     */
    @PostMapping
    public Paper addPaper(@RequestBody Paper paper) {
        return paperService.addPaper(paper);
    }

    /**
     * 根据id删除试卷
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deletePaperById(@PathVariable Long id) {
        paperService.deletePaperById(id);
    }

    /**
     * 根据id获取试卷，并将试卷的题目从题号转化为具体的 description 内容
     * @param id
     * @return PaperReturnDto 返回的 Paper类型，包含具体的题目 description 信息。
     */
    @GetMapping("/{id}")
    public PaperReturnDto getPaperById(@PathVariable Long id) {
        return paperService.selectPaperById(id);
    }

    /**
     * 根据id获取考试试卷，其中各类型题目顺序排列，且不返回答案信息，使得学生无法在控制台根据响应JSON串查看到答案。
     * @param id
     * @return List<ExamPaperDto> 考试试卷中的题目信息列表。
     */
    @GetMapping("/exam-paper/{id}")
    public List<ExamPaperDto> getExamPaper(@PathVariable Long id){
        return paperService.getExamPaper(id);
    }
    /**
     * 根据id更新试卷的 title content和 introduction
     * @param id
     * @param paperUpdateDto
     * @return Paper 更新后的 paper
     */
    @PostMapping("/{id}")
    public Paper updatePaper(@PathVariable Long id, @RequestBody PaperUpdateDto paperUpdateDto) {
        return paperService.updatePaper(id, paperUpdateDto);
    }

    /**
     * 删除id对应的 paper中的id为 dpId的题目
     * @param id
     * @param dqId
     * @return 更新后的题目详细信息
     */
    @PostMapping("/{id}/question/{dqId}")
    public List<Question> deleteQuestionInPaper(@PathVariable Long id, @PathVariable Long dqId) {
        return paperService.deleteQuestionInPaper(id, dqId);
    }

    /**
     * 管理员编辑试卷时获取的试卷的题目详细信息
     * @param id
     * @return 题目详细信息
     */
    @GetMapping("/admin/{id}")
    public List<Question> getPaperByIdForAdmin(@PathVariable Long id) {
        return paperService.selectPaperByIdForAdmin(id);
    }

    /**
     * 获取试卷共有多少记录。
     * @return
     */
    @GetMapping("/pageNum")
    public long getPapersTotalPagesNum(){
        return paperService.getPapersCount();
    }
}
