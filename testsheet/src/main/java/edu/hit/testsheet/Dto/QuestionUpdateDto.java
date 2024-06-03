package edu.hit.testsheet.Dto;

/**
 * ClassName:QuestionUpdateDto
 * Package:edu.hit.testsheet.Dto
 * Description:
 *
 * @date:2024/6/3 16:23
 * @author:shyboy
 */
public class QuestionUpdateDto {
    private String description;
    private String type;
    private String answer;

    // Getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

