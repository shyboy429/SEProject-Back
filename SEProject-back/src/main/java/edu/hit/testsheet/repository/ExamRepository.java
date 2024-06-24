package edu.hit.testsheet.repository;

import edu.hit.testsheet.bean.Exam;
import edu.hit.testsheet.bean.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ClassName:ExamRepository
 * Package:edu.hit.testsheet.repository
 * Description:
 *
 * @date:2024/6/22 17:13
 * @author:shyboy
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> findByName(String name);
}
