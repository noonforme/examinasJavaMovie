package lt.asirtu.manoFilmuSvetaine.Controllers;

import lt.asirtu.manoFilmuSvetaine.dto.MovieRequestDTO;
import lt.asirtu.manoFilmuSvetaine.dto.MovieResponseDTO;
import lt.asirtu.manoFilmuSvetaine.entity.Category;
import lt.asirtu.manoFilmuSvetaine.entity.Movie;
import lt.asirtu.manoFilmuSvetaine.entity.User;
import lt.asirtu.manoFilmuSvetaine.mapper.MovieMapper;
import lt.asirtu.manoFilmuSvetaine.security.services.UserDetailsImpl;
import lt.asirtu.manoFilmuSvetaine.services.CategoryService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private MovieService movieService;
    private CategoryService categoryService;

    @Autowired
    public MovieController(MovieService movieService, CategoryService categoryService) {
        this.movieService = movieService;
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponseDTO> addmovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        Category category = categoryService.findById(movieRequestDTO.getCategory());
        Movie movie = MovieMapper.toEntity(movieRequestDTO, category);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = new User(userDetails.getUsername(), userDetails.getPassword());
        user.setId(userDetails.getId());
        movie.setUser(user);
        Movie savedmovie = movieService.save(movie);

        return ResponseEntity.ok(MovieMapper.mapToDto(savedmovie));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Map<String, Object>> findAll() {
        List<MovieResponseDTO> movies = movieService.findAll().stream().map(MovieMapper::mapToDto).toList();
        Map<String, Object> response = Map.of("status", "success",
                "results", movies.size(),
                "data", movies);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<MovieResponseDTO> getmovie(@PathVariable Integer id) {
        Optional<Movie> result = Optional.ofNullable(movieService.findById(id));
        return result.map(movie -> ResponseEntity.ok(MovieMapper.mapToDto(movie))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<MovieResponseDTO> getmovieByName(@PathVariable String name) {
        Optional<Movie> result = Optional.ofNullable(movieService.findByName(name));
        return result.map(movie -> ResponseEntity.ok(MovieMapper.mapToDto(movie))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Map<String, Object>> getmoviesByCategory(@PathVariable Integer id) {
        Category category = categoryService.findById(id);
        List<MovieResponseDTO> movies = movieService.findByCategory(category).stream().map(MovieMapper::mapToDto).toList();
        Map<String, Object> response = Map.of("status", "success",
                "results", movies.size(),
                "data", movies);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<MovieResponseDTO> updatemovie(@PathVariable Integer id, @RequestBody MovieRequestDTO dto) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Movie updatedMovie = MovieMapper.toEntity(dto, movie.getCategory());
        updatedMovie.setId(id);
        updatedMovie.setUser(movie.getUser());
        Movie savedMovie = movieService.save(updatedMovie);
        return ResponseEntity.ok(MovieMapper.mapToDto(savedMovie));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<String> deletemovie(@PathVariable Integer id) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        movieService.deleteById(id);

        return ResponseEntity.ok("Deleted movie, id - " + id);
    }
}
