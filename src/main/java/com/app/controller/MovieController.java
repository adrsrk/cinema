package com.app.controller;

import com.app.entity.Movie;
import com.app.model.MessageResponseDTO;
import com.app.model.MessageCreateResponseDTO;
import com.app.model.movie.MovieRequestDTO;
import com.app.model.movie.MovieResponseDTO;
import com.app.service.MovieServiceImpl;
import com.app.util.MovieMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieServiceImpl service;
    private final MovieMapper mapper;

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> getMovies(@RequestParam(required = false, name = "query") String query) {
        List<MovieResponseDTO> movies;

        if (query != null && !query.isEmpty()) {
            movies = service.getAllMovies()
                    .stream()
                    .map(mapper::toDto)
                    .toList();

        } else {

            movies = service.searchMovies(query)
                    .stream()
                    .map(mapper::toDto)
                    .toList();
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> getMovieById(@PathVariable Long id) {
        Movie movie = service.getMovieById(id);
        return ResponseEntity.ok(mapper.toDto(movie));
    }

    @PostMapping
    public ResponseEntity<MessageCreateResponseDTO> createMovie(@Valid @RequestBody MovieRequestDTO request) {
        Movie movie = mapper.toEntity(request);
        Movie savedMovie = service.createMovie(movie);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageCreateResponseDTO("Фильм успешно создан", savedMovie.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateMovie(@Valid @PathVariable Long id, @RequestBody MovieRequestDTO request) {
        service.updateMovie(id, request);
        return ResponseEntity.ok(new MessageResponseDTO("Фильм успешно обновлён"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteMovie(@PathVariable Long id) {
        service.deleteMovie(id);
        return ResponseEntity.ok(new MessageResponseDTO("Фильм успешно удалён"));
    }
}
