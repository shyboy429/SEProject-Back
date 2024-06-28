package edu.hit.testsheet.repository;

import edu.hit.testsheet.bean.AnswerRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ClassName:AnswerRecordRepository
 * Package:edu.hit.testsheet.repository
 * Description:
 *
 * @date:2024/6/22 17:51
 * @author:shyboy
 */
@Repository
public interface AnswerRecordRepository extends JpaRepository<AnswerRecord, Long> {
    Optional<AnswerRecord> findByStudentNameAndExamIdAndQuestionId(String studentName, Long examId, Long questionId);
    List<AnswerRecord> findByStudentNameAndExamId(String studentName, Long examId);
    boolean existsByStudentNameAndExamId(String studentName,Long examId);
    
    boolean existsByExamId(Long examId);
}

