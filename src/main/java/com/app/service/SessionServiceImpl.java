package com.app.service;

import com.app.entity.Hall;
import com.app.entity.Movie;
import com.app.entity.Session;
import com.app.exception.HallNotFoundException;
import com.app.exception.MovieNotFoundException;
import com.app.exception.SessionNotFoundException;
import com.app.model.session.SessionRequestDTO;
import com.app.model.session.SessionResponseDTO;
import com.app.model.session.SessionUpdateRequestDTO;
import com.app.repository.HallRepository;
import com.app.repository.MovieRepository;
import com.app.repository.SessionRepository;
import com.app.util.SessionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;
    private final SessionMapper mapper;


    @Override
    public SessionResponseDTO createSession(SessionRequestDTO sessionRequestDTO) {

        Movie movie = movieRepository.findById(sessionRequestDTO.movieId())
                .orElseThrow(() -> new MovieNotFoundException(sessionRequestDTO.movieId()));

        Hall hall = hallRepository.findById(sessionRequestDTO.hallId())
                .orElseThrow(() -> new HallNotFoundException(sessionRequestDTO.hallId()));

        Session session = mapper.toEntity(sessionRequestDTO);
        session.setMovie(movie);
        session.setHall(hall);
        session.setCreatedAt(LocalDateTime.now());

        Session savedSession = sessionRepository.save(session);

        return mapper.toDto(savedSession);
    }

    @Override
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
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
        session.setUpdatedAt(LocalDateTime.now());

        return sessionRepository.save(session);
    }

    @Override
    public void deleteSession(Long sessionId) {

        if (!sessionRepository.existsById(sessionId)) {
            throw new SessionNotFoundException(sessionId);
        }

        sessionRepository.deleteById(sessionId);
    }
}
