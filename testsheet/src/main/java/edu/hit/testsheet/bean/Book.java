package edu.hit.testsheet.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
public class Book {

    @Id
    private String song_id;
    private String name;
    private String artist;
}
