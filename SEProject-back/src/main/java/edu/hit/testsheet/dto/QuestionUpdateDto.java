package edu.hit.testsheet.dto;

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
    private String tag;
    private String analysis;

    private String difficultLevel;

    public String getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(String difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    // Getters and setters
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }


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

