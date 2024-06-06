package edu.hit.testsheet.repository;


import edu.hit.testsheet.bean.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
    List<Question> findByCreatedBy(String createdBy);
}


