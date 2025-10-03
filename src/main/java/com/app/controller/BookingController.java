package com.app.controller;

import com.app.entity.Booking;
import com.app.entity.User;
import com.app.model.MessageCreateResponseDTO;
import com.app.model.booking.BookingRequestDTO;
import com.app.model.booking.BookingResponseDTO;
import com.app.service.BookingService;
import com.app.service.UserService;
import com.app.util.BookingMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;
    private final BookingMapper mapper;

    @PostMapping
    public ResponseEntity<MessageCreateResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {

        User user = userService.getCurrentUser();
        Booking booking = bookingService.createBooking(user, bookingRequestDTO.sessionId(), bookingRequestDTO.seats());
        return ResponseEntity.ok(new MessageCreateResponseDTO("Бронирование успешно создано", booking.getId()));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getMyBookings() {

        User user = userService.getCurrentUser();
        List<BookingResponseDTO> myBookings = bookingService.getBookingsForUser(user.getId())
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(myBookings);
    }
}
