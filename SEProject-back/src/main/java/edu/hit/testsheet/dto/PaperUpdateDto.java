package edu.hit.testsheet.dto;

/**
 * ClassName:PaperUpdateDTO
 * Package:edu.hit.testsheet.Dto
 * Description:
 *
 * @date:2024/6/3 16:18
 * @author:shyboy
 */
public class PaperUpdateDto {
    private String title;
    private String introduction;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

