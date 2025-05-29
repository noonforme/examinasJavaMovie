package lt.asirtu.manoFilmuSvetaine.mapper;

import lt.asirtu.manoFilmuSvetaine.dto.CommentResponseDTO;
import lt.asirtu.manoFilmuSvetaine.dto.MovieRequestDTO;
import lt.asirtu.manoFilmuSvetaine.dto.MovieResponseDTO;
import lt.asirtu.manoFilmuSvetaine.entity.Category;
import lt.asirtu.manoFilmuSvetaine.entity.Movie;

import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {

    public static Movie toEntity(MovieRequestDTO dto, Category category) {
        Movie movie = new Movie();
        movie.setName(dto.getName());
        movie.setCategory(category);
        movie.setRating(dto.getRating());
        movie.setDescription(dto.getDescription());
        return movie;
    }

    public static MovieResponseDTO mapToDto(Movie movie) {
        MovieResponseDTO dto = new MovieResponseDTO();
        dto.setId(movie.getId());
        dto.setName(movie.getName());
        dto.setDescription(movie.getDescription());
        dto.setRating(movie.getRating());
        dto.setUser(movie.getUser().getId());
        dto.setCategory(movie.getCategory().getId());
        List<CommentResponseDTO> commentResponseDTOList = movie.getComments().stream().map(CommentMapper::toResponse).collect(Collectors.toList());
        dto.setComments(commentResponseDTOList);
        return dto;
    }
}
