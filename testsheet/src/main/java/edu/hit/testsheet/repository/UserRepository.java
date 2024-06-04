package edu.hit.testsheet.repository;

import edu.hit.testsheet.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ClassName:UserRepository
 * Package:edu.hit.testsheet.repository
 * Description:
 *
 * @date:2024/6/3 11:34
 * @author:shyboy
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
