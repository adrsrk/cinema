package com.app.service;

import com.app.entity.Session;
import com.app.model.session.SessionRequestDTO;
import com.app.model.session.SessionResponseDTO;
import com.app.model.session.SessionUpdateRequestDTO;

import java.util.List;

public interface SessionService {

    SessionResponseDTO createSession(SessionRequestDTO sessionRequestDTO);

    List<Session> getAllSessions();

    Session getSessionById(Long sessionId);

    Session updateSession(Long sessionId, SessionUpdateRequestDTO sessionUpdateRequestDTO);

    void deleteSession(Long sessionId);
}
