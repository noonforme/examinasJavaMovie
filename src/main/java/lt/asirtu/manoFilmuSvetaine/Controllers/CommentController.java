package lt.asirtu.manoFilmuSvetaine.Controllers;

import lt.asirtu.manoFilmuSvetaine.dto.CommentRequestDTO;
import lt.asirtu.manoFilmuSvetaine.dto.CommentResponseDTO;
import lt.asirtu.manoFilmuSvetaine.entity.Comment;
import lt.asirtu.manoFilmuSvetaine.entity.Movie;
import lt.asirtu.manoFilmuSvetaine.entity.User;
import lt.asirtu.manoFilmuSvetaine.mapper.CommentMapper;
import lt.asirtu.manoFilmuSvetaine.security.services.UserDetailsImpl;
import lt.asirtu.manoFilmuSvetaine.services.CommentService;
import lt.asirtu.manoFilmuSvetaine.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private CommentService commentService;
    private MovieService movieService;

    @Autowired
    public CommentController(CommentService commentService, MovieService movieService) {
        this.commentService = commentService;
        this.movieService = movieService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<CommentResponseDTO> addComment(@Valid @RequestBody CommentRequestDTO dto) {
        Movie movie = movieService.findById(dto.getMovie());
        Comment comment = CommentMapper.toEntity(dto, movie);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = new User(userDetails.getUsername(), userDetails.getPassword());
        user.setId(userDetails.getId());
        comment.setUser(user);
        Comment savedComment = commentService.save(comment);

        return ResponseEntity.ok(CommentMapper.toResponse(savedComment));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Map<String, Object>> findAll() {
        List<CommentResponseDTO> comments = commentService.findAll().stream().map(CommentMapper::toResponse).toList();
        Map<String, Object> response = Map.of("status", "success",
                "results", comments.size(),
                "data", comments);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<CommentResponseDTO> getComment(@PathVariable Integer id) {
        Optional<Comment> result = Optional.ofNullable(commentService.findById(id));
        return result.map(comment -> ResponseEntity.ok(CommentMapper.toResponse(comment))).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<String> deleteComment(@PathVariable Integer id) {
        Comment comment = commentService.findById(id);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!Objects.equals(comment.getUser().getId(), userDetails.getId())) {
            throw new RuntimeException("Missing permissions.");
        }
        commentService.deleteById(id);

        return ResponseEntity.ok("Deleted comment, id - " + id);
    }
}
