package com.app.service;

import com.app.entity.BookingSeat;
import com.app.entity.Session;
import com.app.exception.SessionNotFoundException;
import com.app.model.session.SessionUpdateRequestDTO;
import com.app.repository.BookingRepository;
import com.app.repository.BookingSeatRepository;
import com.app.repository.SessionRepository;
import com.app.util.SessionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final BookingSeatRepository bookingSeatRepository;

    @Override
    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException(sessionId));
    }

    /**
     * Обновляет данные о сеансе по ID.
     *
     * @param sessionId ID зала, который нужно обновить
     * @param sessionUpdateRequestDTO DTO с новыми данными
     * @return обновлённый объект сеанса
     * @throws SessionNotFoundException если зал с таким ID не найден
     */
    @Override
    public Session updateSession(Long sessionId, SessionUpdateRequestDTO sessionUpdateRequestDTO) {

        Session session = getSessionById(sessionId);

        session.setStartTime(sessionUpdateRequestDTO.startTime());
        session.setPrice(sessionUpdateRequestDTO.price());

        return sessionRepository.save(session);
    }

    @Override
    public void deleteSession(Long sessionId) {

        if (!sessionRepository.existsById(sessionId)) {
            throw new SessionNotFoundException(sessionId);
        }

        sessionRepository.deleteById(sessionId);
    }

    @Override
    public Page<Session> getFilteredSession(Long movieId, Long hallId, LocalDate date, int page, int size) {

        if (date != null) {
            Instant start = date.atStartOfDay(ZoneOffset.UTC).toInstant();
            Instant end = date.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();
            return sessionRepository.findFiltered(movieId, hallId, start, end, PageRequest.of(page, size));
        }
        return sessionRepository.findFiltered(movieId, hallId, null, null, PageRequest.of(page, size));
    }

    @Override
    public List<BookingSeat> getTakenSeats(Long sessionId) {

        Session session = getSessionById(sessionId);
        return bookingSeatRepository.findBySessionId(sessionId);
    }


}
