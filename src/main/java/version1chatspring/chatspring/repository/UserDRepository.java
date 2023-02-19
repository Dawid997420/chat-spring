package version1chatspring.chatspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import version1chatspring.chatspring.model.UserD;

import java.util.Optional;

@Repository
public interface UserDRepository extends JpaRepository<UserD,Integer> {

    boolean existsByUsername(String username) ;

    UserD findByUsername(String username);
}
