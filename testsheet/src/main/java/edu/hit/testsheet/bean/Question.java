package edu.hit.testsheet.bean;

import jakarta.persistence.*;

import java.util.HashMap;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "answer")
    private String answer;

    @Column(name = "difficult_level")
    private String difficultLevel;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(String difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}