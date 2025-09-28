package com.app.service;

import com.app.entity.Movie;
import com.app.model.movie.MovieRequestDTO;
import org.springframework.data.domain.Page;

public interface MovieService {

    Page<Movie> getAllMoviesPageable(int page, int size);

    Movie getMovieById(Long movieId);

    Movie createMovie(Movie movie);

    Movie updateMovie(Long movieId, MovieRequestDTO movieRequestDTO);

    void deleteMovie(Long movieId);

    Page<Movie> searchMovies(int page, int size, String query);
}
