package com.app.controller;

import com.app.entity.Movie;
import com.app.model.MessageResponseDTO;
import com.app.model.MovieCreateResponseDTO;
import com.app.model.MovieRequestDTO;
import com.app.model.MovieResponseDTO;
import com.app.service.MovieService;
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

    private final MovieService service;
    private final MovieMapper mapper;

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> getAllMovies() {
        List<MovieResponseDTO> movies = service.getAllMovies()
                .stream()
                .map(mapper :: toDto)
                .toList();

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> getMovieById(@RequestParam Long id) {
        Movie movie = service.getMovieById(id);
        return ResponseEntity.ok(mapper.toDto(movie));
    }

    @PostMapping
    public ResponseEntity<MovieCreateResponseDTO> createMovie(@Valid @RequestBody MovieRequestDTO request) {
        Movie movie = mapper.toEntity(request);
        Movie savedMovie = service.createMovie(movie);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MovieCreateResponseDTO("Фильм успешно создан", savedMovie.getId()));
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
