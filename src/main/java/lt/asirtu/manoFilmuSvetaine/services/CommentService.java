package lt.asirtu.manoFilmuSvetaine.services;

import lt.asirtu.manoFilmuSvetaine.dao.CommentRepository;
import lt.asirtu.manoFilmuSvetaine.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(Integer id) {
        Optional<Comment> result = commentRepository.findById(id);
        Comment comment = null;
        if (result.isPresent()) {
            comment = result.get();
        } else {
            throw new RuntimeException("Can't find comment, id - " + id);
        }
        return comment;
    }

    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }
}
