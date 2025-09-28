package com.app.util;

import com.app.entity.Hall;
import com.app.entity.Movie;
import com.app.entity.Session;
import com.app.model.session.SessionRequestDTO;
import com.app.model.session.SessionResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface SessionMapper {

    @Mapping(target = "movieId", source = "movie.id")
    @Mapping(target = "hallId", source = "hall.id")
    SessionResponseDTO toDto(Session session);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "movie", source = "movieId")
    @Mapping(target = "hall", source = "hallId")
    Session toEntity(SessionRequestDTO dto);

    default Movie mapMovie(Long movieId) {
        if (movieId == null) return null;
        Movie movie = new Movie();
        movie.setId(movieId);
        return movie;
    }

    default Hall mapHall(Long hallId) {
        if (hallId == null) return null;
        Hall hall = new Hall();
        hall.setId(hallId);
        return hall;
    }
}
