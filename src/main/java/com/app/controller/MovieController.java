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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieServiceImpl service;
    private final MovieMapper mapper;

    @GetMapping
    public ResponseEntity<Page<MovieResponseDTO>> getMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false, name = "query") String query) {

        Page<Movie> movies = service.searchMovies(page, size, query);
        Page<MovieResponseDTO> movieDtoByPage = movies.map(mapper::toDto);
        return ResponseEntity.ok(movieDtoByPage);
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
