package com.app.util;

import com.app.entity.Booking;
import com.app.model.booking.BookingResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = SeatMapper.class)
public interface BookingMapper {

    @Mapping(target = "sessionId", source = "session.id")
    @Mapping(target = "seats", source = "seats")
    BookingResponseDTO toDto(Booking booking);
}
