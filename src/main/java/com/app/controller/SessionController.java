package com.app.controller;

import com.app.entity.Session;
import com.app.model.MessageCreateResponseDTO;
import com.app.model.MessageResponseDTO;
import com.app.model.session.SessionRequestDTO;
import com.app.model.session.SessionResponseDTO;
import com.app.model.session.SessionUpdateRequestDTO;
import com.app.service.SessionService;
import com.app.util.SessionMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final SessionMapper mapper;

    @GetMapping
    public ResponseEntity<List<SessionResponseDTO>> getAllSessions() {

        List<SessionResponseDTO> sessions = sessionService.getAllSessions()
                .stream()
                .map(mapper :: toDto)
                .toList();

        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> getSessionById(@PathVariable Long id) {

        Session session = sessionService.getSessionById(id);
        return ResponseEntity.ok(mapper.toDto(session));
    }

    @PostMapping
    public ResponseEntity<MessageCreateResponseDTO> createSession(@Valid @RequestBody SessionRequestDTO sessionRequestDTO) {

        SessionResponseDTO session = sessionService.createSession(sessionRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageCreateResponseDTO("Сеанс успешно создан", session.id()));
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
}
