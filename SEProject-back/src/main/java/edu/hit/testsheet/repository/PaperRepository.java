package edu.hit.testsheet.repository;

import edu.hit.testsheet.bean.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ClassName:PaperRepository
 * Package:edu.hit.testsheet.repository
 * Description:
 *
 * @date:2024/6/3 11:43
 * @author:shyboy
 */
@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {
}
