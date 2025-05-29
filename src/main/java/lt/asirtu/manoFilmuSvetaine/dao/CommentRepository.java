package lt.asirtu.manoFilmuSvetaine.dao;

import lt.asirtu.manoFilmuSvetaine.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
