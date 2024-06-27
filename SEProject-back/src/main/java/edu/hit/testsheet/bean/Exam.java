package edu.hit.testsheet.bean;

import jakarta.persistence.*;

/**
 * ClassName:Exam
 * Package:edu.hit.testsheet.bean
 * Description:
 *
 * @date:2024/6/22 16:07
 * @author:shyboy
 */
@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "paper_id",nullable = false)
    private Long paperId;

    @Column(name = "publisher",nullable = false)
    private String publisher;

    @Column(name = "start_time",nullable = false)
    private String startTime;

    @Column(name = "end_time",nullable = false)
    private String endTime;
    
    @Column(name = "duration_Time",nullable = false)
    private String durationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }
}
