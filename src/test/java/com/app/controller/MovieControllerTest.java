package com.app.controller;

import com.app.entity.Movie;
import com.app.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
    }

    @Test
    void createMovie_success() throws Exception {

        String jsonRequest = """
                {
                "title" : "Inception",
                "description" : "Science fiction action heist film",
                "duration" : 120,
                "posterUrl" : "https://test.com/inception.jpg"
                }
                """;

        mockMvc.perform(post("/api/v1/movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Фильм успешно создан"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void createMovie_shouldReturn400_whenTitleisBlank() throws Exception {

        String json = """
                {
                "title" : "",
                "description" : "Science fiction action heist film",
                "duration" : 120,
                "posterUrl" : "https://test.com/",
                }
                """;

        mockMvc.perform(post("/api/v1/movie")
        .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createMovie_shouldReturn400_whenDurationNotPositive() throws Exception {

        String json = """
                {
                "title" : "Inception",
                "description" : "Science fiction action heist film",
                "duration" : 0,
                "posterUrl" : "https://test.com/",
                }
                """;

        mockMvc.perform(post("/api/v1/movie")
        .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createMovie_shouldReturn400_whenPosterUrlInvalid() throws Exception {

        String json = """
                {
                "title" : "Inception",
                "description" : "Science fiction action heist film",
                "duration" : 120,
                "posterUrl" : "not_a_url",
                }
                """;

        mockMvc.perform(post("/api/v1/movie")
        .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createMovie_shouldReturn400_whenTitleTooLong() throws Exception {

        String longTitle = "a".repeat(256);

        String json = """
                {
                "title" : "%s",
                "description" : "Science fiction action heist film",
                "duration" : 120,
                "posterUrl" : "https://test.com/",
                }
                """.formatted(longTitle);

        mockMvc.perform(post("/api/v1/movie")

        .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllMovies_shouldReturnEmptyList_whenNoMovies() throws Exception {

        mockMvc.perform(get("/api/v1/movie"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllMovies_shouldReturnList_whenMoviesExist() throws Exception {

        Movie movieOne = new Movie();
        movieOne.setTitle("A Movie");
        movieOne.setDescription("desc A");
        movieOne.setDuration(100);
        movieOne.setPosterUrl("https://test.com/a.jpg");
        movieOne.setCreatedAt(LocalDateTime.now());

        Movie movieTwo = new Movie();
        movieTwo.setTitle("B Movie");
        movieTwo.setDescription("desc B");
        movieTwo.setDuration(110);
        movieTwo.setPosterUrl("https://test.com/b.jpg");
        movieTwo.setCreatedAt(LocalDateTime.now());

        movieRepository.saveAll(List.of(movieOne, movieTwo));

        mockMvc.perform(get("/api/v1/movie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].title", containsInAnyOrder("A Movie", "B Movie")));
    }

    @Test
    void getMovieByID_shouldReturnMovie_whenExists() throws Exception {

        Movie movie = new Movie();
        movie.setTitle("Inception");
        movie.setDescription("sci-fi action");
        movie.setDuration(120);
        movie.setPosterUrl("https://test.com/inception.jpg");
        movie.setCreatedAt(LocalDateTime.now());

        Movie saved = movieRepository.save(movie);

        mockMvc.perform(get("/api/v1/movie/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(movie.getId()))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.description").value("sci-fi action"))
                .andExpect(jsonPath("$.duration").value(120));
    }

    @Test
    void getMovieByID_shouldReturn404_whenMovieDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/movie/{id}", 9999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Movie with id 9999 not found"));
    }

    @Test
    void updateMovie_shouldUpdateAndReturn200_whenExists() throws Exception {

        Movie movie = new Movie();
        movie.setTitle("Inception");
        movie.setDescription("Sci-fi action");
        movie.setDuration(120);
        movie.setPosterUrl("https://test.com/inception.jpg");
        movie.setCreatedAt(LocalDateTime.now());

        Movie savedMovie = movieRepository.save(movie);

        String updateJson = """
                {
                "title" : "Interstellar",
                "description" : "Epic space movie",
                "duration" : 150,
                "posterUrl" : "https://test.com/interstellar.jpg"
                }
                """;

        mockMvc.perform(put("/api/v1/movie/{id}", savedMovie.getId())
        .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Фильм успешно обновлён"));

        Movie updated = movieRepository.findById(savedMovie.getId()).orElseThrow();
        assertEquals("Interstellar", updated.getTitle());
        assertEquals("Epic space movie", updated.getDescription());
        assertEquals(150, updated.getDuration());
        assertEquals("https://test.com/interstellar.jpg", updated.getPosterUrl());
    }

    @Test
    void updateMovie_shouldReturn404_whenMovieNotExists() throws Exception {

        String updateJson = """
        {
          "title": "NonExistent",
          "description": "No movie here",
          "duration": 100,
          "posterUrl": "https://test.com/nomovie.jpg"
        }
        """;

        mockMvc.perform(put("/api/v1/movie/{id}", 9999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Movie with id 9999 not found"));
    }

    @Test
    void deleteMovie_shouldReturn200_whenExists() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Inception");
        movie.setDescription("Sci-fi action");
        movie.setDuration(120);
        movie.setPosterUrl("https://test.com/inception.jpg");
        movie.setCreatedAt(LocalDateTime.now());

        Movie saved = movieRepository.save(movie);

        mockMvc.perform(delete("/api/v1/movie/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Фильм успешно удалён"));

        assertTrue(movieRepository.findById(saved.getId()).isEmpty());
    }

    @Test
    void deleteMovie_shouldReturn404_whenNotExists() throws Exception {
        mockMvc.perform(delete("/api/v1/movie/{id}", 9999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Movie with id 9999 not found"));
    }
}
