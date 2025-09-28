package com.app.util;

import com.app.entity.Hall;
import com.app.model.hall.HallRequestDTO;
import com.app.model.hall.HallResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface HallMapper {

    HallResponseDTO toDTO(Hall hall);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Hall toEntity(HallRequestDTO dto);
}
