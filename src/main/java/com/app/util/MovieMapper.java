package com.app.util;

import com.app.entity.Movie;
import com.app.model.MovieRequestDTO;
import com.app.model.MovieResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MovieMapper {

    public MovieResponseDTO toDto(Movie movie) {
        return new MovieResponseDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getDuration(),
                movie.getPosterUrl(),
                movie.getCreatedAt()
        );
    }

    public Movie toEntity(MovieRequestDTO dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.title());
        movie.setDescription(dto.description());
        movie.setDuration(dto.duration());
        movie.setPosterUrl(dto.posterUrl());
        movie.setCreatedAt(LocalDateTime.now());
        return movie;
    }
}
