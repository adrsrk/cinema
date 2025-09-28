package com.app.util;

import com.app.entity.Movie;
import com.app.model.movie.MovieRequestDTO;
import com.app.model.movie.MovieResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface MovieMapper {

    MovieResponseDTO toDto(Movie movie);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Movie toEntity(MovieRequestDTO dto);
}
