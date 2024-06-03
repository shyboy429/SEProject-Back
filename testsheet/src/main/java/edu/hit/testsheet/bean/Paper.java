package edu.hit.testsheet.bean;

import jakarta.persistence.*;

/**
 * ClassName:Paper
 * Package:edu.hit.testsheet.bean
 * Description:
 *
 * @date:2024/6/3 11:09
 * @author:shyboy
 */
@Entity
@Table(name = "papers")
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "q1")
    private Long q1;
    @Column(name = "q2")
    private Long q2;
    @Column(name = "q3")
    private Long q3;
    @Column(name = "q4")
    private Long q4;
    @Column(name = "q5")
    private Long q5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getQ1() {
        return q1;
    }

    public void setQ1(Long q1) {
        this.q1 = q1;
    }

    public Long getQ2() {
        return q2;
    }

    public void setQ2(Long q2) {
        this.q2 = q2;
    }

    public Long getQ3() {
        return q3;
    }

    public void setQ3(Long q3) {
        this.q3 = q3;
    }

    public Long getQ4() {
        return q4;
    }

    public void setQ4(Long q4) {
        this.q4 = q4;
    }

    public Long getQ5() {
        return q5;
    }

    public void setQ5(Long q5) {
        this.q5 = q5;
    }
}
