package com.app.controller;

import com.app.entity.BookingSeat;
import com.app.entity.Session;
import com.app.model.MessageCreateResponseDTO;
import com.app.model.MessageResponseDTO;
import com.app.model.booking.SeatDTO;
import com.app.model.booking.TakenSeatsResponseDTO;
import com.app.model.session.SessionRequestDTO;
import com.app.model.session.SessionResponseDTO;
import com.app.model.session.SessionUpdateRequestDTO;
import com.app.service.SessionService;
import com.app.util.SeatMapper;
import com.app.util.SessionMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final SessionMapper mapper;
    private final SeatMapper seatMapper;

    @GetMapping
    public ResponseEntity<Page<SessionResponseDTO>> getFilteredSessions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) Long movieId,
            @RequestParam(required = false) Long hallId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {

        Page<Session> sessions = sessionService.getFilteredSession(movieId, hallId, date, page, size);
        Page<SessionResponseDTO> sessionByPage = sessions.map(mapper::toDto);

        return ResponseEntity.ok(sessionByPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> getSessionById(@PathVariable Long id) {

        Session session = sessionService.getSessionById(id);
        return ResponseEntity.ok(mapper.toDto(session));
    }

    @PostMapping
    public ResponseEntity<MessageCreateResponseDTO> createSession(@Valid @RequestBody SessionRequestDTO sessionRequestDTO) {

        Session session = mapper.toEntity(sessionRequestDTO);

        Session savedSession = sessionService.createSession(session);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageCreateResponseDTO("Сеанс успешно создан", savedSession.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateSession(@PathVariable Long id, @Valid @RequestBody SessionUpdateRequestDTO sessionUpdateRequestDTO) {

        sessionService.updateSession(id, sessionUpdateRequestDTO);
        return ResponseEntity.ok(new MessageResponseDTO("Информация о сеансе обновлена"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteSession(@PathVariable Long id) {

        sessionService.deleteSession(id);
        return ResponseEntity.ok(new MessageResponseDTO("Сеанс успешно удалён"));
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<TakenSeatsResponseDTO> getTakenSeats(@PathVariable Long id) {

        List<BookingSeat> seats = sessionService.getTakenSeats(id);

        List<SeatDTO> seatDTOS = seats.stream()
                .map(seatMapper::toDto)
                .toList();

        return ResponseEntity.ok(new TakenSeatsResponseDTO(id, seatDTOS));
    }
}
