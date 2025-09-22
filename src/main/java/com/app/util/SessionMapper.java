package com.app.util;

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
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", ignore = true)
    Session toEntity(SessionRequestDTO dto);


}
