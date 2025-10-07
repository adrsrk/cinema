package com.app.service;

import com.app.entity.*;
import com.app.exception.InvalidSeatPositionException;
import com.app.exception.SeatAlreadyReservedException;
import com.app.exception.SessionNotFoundException;
import com.app.model.booking.SeatDTO;
import com.app.repository.BookingRepository;
import com.app.repository.BookingSeatRepository;
import com.app.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final SessionRepository repository;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final SeatUpdateNotifier seatUpdateNotifier;

    @Override
    @Transactional
    public Booking createBooking(User user, Long sessionId, List<SeatDTO> requestedSeats) {

        Session session = repository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException(sessionId));

        List<BookingSeat> alreadyBookedSeats = bookingSeatRepository.findBySessionId(sessionId);
        Hall hall = session.getHall();

        validateSeatsInRange(hall, requestedSeats); // проверяем запрашиваемые места в диапазоне мест зала и выбрасываем исключение

        validateSeatAvailability(alreadyBookedSeats, requestedSeats); // проверяем свободны ли запрашиваемые места и выбрасываем исключение

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSession(session);

        List<BookingSeat> bookingSeats = requestedSeats
                .stream()
                .map(seat -> BookingSeat.builder()
                        .booking(booking)
                        .session(session)
                        .rowNumber(seat.row())
                        .seatNumber(seat.seat())
                        .build())
                .toList();

        booking.setSeats(bookingSeats);

        Booking savedBooking = bookingRepository.save(booking);

        List<BookingSeat> updateSeats = bookingSeatRepository.findBySessionId(sessionId);
        List<SeatDTO> takenSeats = updateSeats.stream()
                .map(bs -> new SeatDTO(bs.getRowNumber(), bs.getSeatNumber()))
                .toList();

        seatUpdateNotifier.notifySeatUpdate(sessionId, takenSeats);

        return savedBooking;
    }

    @Override
    public List<Booking> getBookingsForUser(Long userId) {
        return bookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    private void validateSeatsInRange(Hall hall, List<SeatDTO> requestedSeats) {

        for (SeatDTO seatDTO : requestedSeats) {

            if (seatDTO.row() < 1 || seatDTO.row() > hall.getRows()) {
                throw new InvalidSeatPositionException("Ряд вне диапазона: " + seatDTO.row() + "." + " Разрешенный ряд от " + "1 до " + hall.getRows() + ".");
            }

            if (seatDTO.seat() < 1 || seatDTO.seat() > hall.getSeatsPerRow()) {
                throw new InvalidSeatPositionException("Место вне диапазона: " + seatDTO.seat() + "." + " Разрешенное место от " + "1 до " + hall.getSeatsPerRow() + ".");
            }
        }
    }

    private void validateSeatAvailability(List<BookingSeat> alreadyBookedSeats, List<SeatDTO> requestedSeats) {

        for (SeatDTO seatDTO : requestedSeats) {
            boolean taken = alreadyBookedSeats
                    .stream()
                    .anyMatch(bs -> bs.getRowNumber() == seatDTO.row() && bs.getSeatNumber() == seatDTO.seat());

            if (taken) {
                throw new SeatAlreadyReservedException(seatDTO.row(), seatDTO.seat());
            }
        }

    }
}
