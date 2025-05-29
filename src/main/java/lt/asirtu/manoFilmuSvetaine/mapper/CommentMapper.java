package lt.asirtu.manoFilmuSvetaine.mapper;

import lt.asirtu.manoFilmuSvetaine.dto.CommentRequestDTO;
import lt.asirtu.manoFilmuSvetaine.dto.CommentResponseDTO;
import lt.asirtu.manoFilmuSvetaine.entity.Comment;
import lt.asirtu.manoFilmuSvetaine.entity.Movie;

public class CommentMapper {

    public static Comment toEntity(CommentRequestDTO dto, Movie movie) {
        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setMovie(movie);
        return comment;
    }

    public static CommentResponseDTO toResponse(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setUser(comment.getUser().getId());
        dto.setMovie(comment.getMovie().getId());
        return dto;
    }
}
