package lt.asirtu.manoFilmuSvetaine.services;

import lt.asirtu.manoFilmuSvetaine.dao.MovieRepository;
import lt.asirtu.manoFilmuSvetaine.entity.Category;
import jakarta.transaction.Transactional;
import lt.asirtu.manoFilmuSvetaine.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(Integer id) {
        Optional<Movie> result = movieRepository.findById(id);
        Movie movie = null;
        if (result.isPresent()) {
            movie = result.get();
        } else {
            throw new RuntimeException("Can't find movie, id - " + id);
        }
        return movie;
    }

    @Transactional
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Transactional
    public void deleteById(Integer id) {
        movieRepository.deleteById(id);
    }

    public Movie findByName(String name) {
        Optional<Movie> result = movieRepository.findByName(name);
        Movie movie = null;
        if (result.isPresent()) {
            movie = result.get();
        } else {
            throw new RuntimeException("Can't find movie, name - " + name);
        }
        return movie;
    }

    public List<Movie> findByCategory(Category category) {
        return movieRepository.findByCategory(category);
    }

}
