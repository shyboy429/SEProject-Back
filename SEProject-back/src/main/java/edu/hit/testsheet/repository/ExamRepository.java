package edu.hit.testsheet.repository;

import edu.hit.testsheet.bean.Exam;
import edu.hit.testsheet.bean.Paper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("SELECT e FROM Exam e WHERE e.startTime > :currentTime")
    Page<Exam> findNotStartedExams(@Param("currentTime") String currentTime, Pageable pageable);

    @Query("SELECT e FROM Exam e WHERE e.startTime <= :currentTime AND e.endTime > :currentTime")
    Page<Exam> findInProgressExams(@Param("currentTime") String currentTime, Pageable pageable);

    @Query("SELECT e FROM Exam e WHERE e.endTime <= :currentTime")
    Page<Exam> findFinishedExams(@Param("currentTime") String currentTime, Pageable pageable);
}
