package com.app.service;

import com.app.exception.MovieNotFoundException;
import com.app.entity.Movie;
import com.app.model.MovieRequestDTO;
import com.app.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    public Movie getMovieById(Long movieId) {
        return repository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
    }

    public Movie createMovie(Movie movie) {
        return repository.save(movie);
    }

    public Movie updateMovie(Long movieId, MovieRequestDTO movieRequestDTO) {

        Movie movie = repository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));

        movie.setTitle(movieRequestDTO.title());
        movie.setDescription(movieRequestDTO.description());
        movie.setDuration(movieRequestDTO.duration());
        movie.setPosterUrl(movieRequestDTO.posterUrl());
        movie.setUpdatedAt(LocalDateTime.now());

        return repository.save(movie);
    }

    public void deleteMovie(Long movieId) {

        if (!repository.existsById(movieId)) {
            throw new MovieNotFoundException(movieId);
        }

        repository.deleteById(movieId);
    }
}
