package com.app.service;

import com.app.entity.Booking;
import com.app.entity.User;
import com.app.model.booking.SeatDTO;

import java.util.List;

public interface BookingService {

    Booking createBooking(User user, Long sessionId, List<SeatDTO> seats);

    List<Booking> getBookingsForUser(Long userId);
}
