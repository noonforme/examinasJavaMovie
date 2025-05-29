package lt.asirtu.manoFilmuSvetaine.dao;

import lt.asirtu.manoFilmuSvetaine.entity.Category;
import lt.asirtu.manoFilmuSvetaine.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByName(String name);
    List<Movie> findByCategory(Category category);
}
