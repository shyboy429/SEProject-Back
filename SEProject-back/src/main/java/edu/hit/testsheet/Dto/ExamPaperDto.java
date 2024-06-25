package edu.hit.testsheet.Dto;

/**
 * ClassName:ExamPaperDto
 * Package:edu.hit.testsheet.Dto
 * Description:
 *
 * @date:2024/6/25 22:28
 * @author:shyboy
 */
public class ExamPaperDto {
    private Long questionId;
    private String specificContent;
    
    private String questionType;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getSpecificContent() {
        return specificContent;
    }

    public void setSpecificContent(String specificContent) {
        this.specificContent = specificContent;
    }
}
