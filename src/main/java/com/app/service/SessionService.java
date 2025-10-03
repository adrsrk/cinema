package com.app.service;

import com.app.entity.BookingSeat;
import com.app.entity.Session;
import com.app.model.booking.TakenSeatsResponseDTO;
import com.app.model.session.SessionUpdateRequestDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface SessionService {

    Session createSession(Session session);

    Session getSessionById(Long sessionId);

    Session updateSession(Long sessionId, SessionUpdateRequestDTO sessionUpdateRequestDTO);

    void deleteSession(Long sessionId);

    Page<Session> getFilteredSession(Long movieId, Long hallId, LocalDate date, int page, int size);

    List<BookingSeat> getTakenSeats(Long sessionId);
}
