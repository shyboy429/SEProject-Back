package edu.hit.testsheet.dto;

/**
 * ClassName:ExamPaperDto
 * Package:edu.hit.testsheet.Dto
 * Description:
 *
 * @date:2024/6/25 22:28
 * @author:shyboy
 */
public class ExamPaperDto {
    private Long id;
    private String description;
    
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
