package com.app.service;

import com.app.exception.MovieNotFoundException;
import com.app.entity.Movie;
import com.app.model.movie.MovieRequestDTO;
import com.app.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
        * Сервис для управления фильмами.
        * Реализует бизнес-логику CRUD-операций с сущностью Movie.
 **/
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    @Override
    public Page<Movie> getAllMoviesPageable(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Movie getMovieById(Long movieId) {
        return repository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));
    }

    @Override
    public Movie createMovie(Movie movie) {
        return repository.save(movie);
    }

    /**
     * Обновляет данные о фильме по ID.
     *
     * @param movieId ID фильма, который нужно обновить
     * @param movieRequestDTO DTO с новыми данными
     * @return обновлённый объект фильма
     * @throws MovieNotFoundException если фильм с таким ID не найден
     */
    @Override
    @Transactional
    public Movie updateMovie(Long movieId, MovieRequestDTO movieRequestDTO) {

        Movie movie = repository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(movieId));

        movie.setTitle(movieRequestDTO.title());
        movie.setDescription(movieRequestDTO.description());
        movie.setDuration(movieRequestDTO.duration());
        movie.setPosterUrl(movieRequestDTO.posterUrl());

        return repository.save(movie);
    }

    @Override
    public void deleteMovie(Long movieId) {

        if (!repository.existsById(movieId)) {
            throw new MovieNotFoundException(movieId);
        }

        repository.deleteById(movieId);
    }

    @Override
    public Page<Movie> searchMovies(int page, int size, String query) {

        if (query == null || query.isEmpty()) {
            return getAllMoviesPageable(page, size);
        }

        return repository.findByTitleContainingIgnoreCase(query, PageRequest.of(page, size));
    }
}
