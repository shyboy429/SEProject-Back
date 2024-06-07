package edu.hit.testsheet.util;

import edu.hit.testsheet.Dto.PaperReturnDto;
import edu.hit.testsheet.Exception.QuestionNotFoundException;
import edu.hit.testsheet.bean.Paper;
import edu.hit.testsheet.bean.Question;
import edu.hit.testsheet.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName:PaperToDto
 * Package:edu.hit.testsheet.util
 * Description:
 *
 * @date:2024/6/5 20:22
 * @author:shyboy
 */

@Component
public class PaperToDtoUtil {

    @Autowired
    private QuestionService questionService;

    public PaperReturnDto convertPaperToDto(Paper paper) {
        PaperReturnDto pret = new PaperReturnDto();
        pret.setId(paper.getId());
        pret.setTitle(paper.getTitle());
        pret.setIntroduction(paper.getIntroduction());
        pret.setCreateTime(paper.getCreateTime());
        pret.setUpdateTime(paper.getUpdateTime());
        pret.setCreatedBy(paper.getCreatedBy());

        String content = paper.getContent();
        String[] questionIds = content.split(" ");
        StringBuilder specificContent = new StringBuilder();
        int questionNumber = 1;

        for (String idStr : questionIds) {
            try {
                Long questionId = Long.parseLong(idStr);
                Question question = questionService.selectQuestionByIdInPaper(questionId);
                if (question != null) {
                    specificContent.append("第").append(questionNumber).append("题: ").append(question.getDescription()).append("\n");
                    questionNumber++;
                } else {
                    System.err.println("Question not found for ID: " + idStr);
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid question ID format: " + idStr);
            }
        }
        pret.setSpecificContent(specificContent.toString().trim());
        return pret;
    }
}

