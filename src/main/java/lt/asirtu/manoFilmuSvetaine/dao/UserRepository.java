package lt.asirtu.manoFilmuSvetaine.dao;

import lt.asirtu.manoFilmuSvetaine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
