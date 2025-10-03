package com.app.util;

import com.app.entity.BookingSeat;
import com.app.model.booking.SeatDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeatMapper {

    @Mapping(target = "row", source = "rowNumber")
    @Mapping(target = "seat", source = "seatNumber")
    SeatDTO toDto(BookingSeat bookingSeat);
}
