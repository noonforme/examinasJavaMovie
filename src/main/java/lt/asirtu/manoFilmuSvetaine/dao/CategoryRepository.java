package lt.asirtu.manoFilmuSvetaine.dao;

import lt.asirtu.manoFilmuSvetaine.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);
}
