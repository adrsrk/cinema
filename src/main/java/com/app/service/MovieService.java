package com.app.service;

import com.app.entity.Movie;
import com.app.model.movie.MovieRequestDTO;

import java.util.List;

public interface MovieService {

    List<Movie> getAllMovies();

    Movie getMovieById(Long movieId);

    Movie createMovie(Movie movie);

    Movie updateMovie(Long movieId, MovieRequestDTO movieRequestDTO);

    void deleteMovie(Long movieId);
}
