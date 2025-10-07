package com.app.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SeatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<Long, Set<WebSocketSession>> sessionsByCinemaSession = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long sessionId = extractSessionId(session);
        sessionsByCinemaSession
                .computeIfAbsent(sessionId, k -> ConcurrentHashMap.newKeySet())
                .add(session);

        log.info("✅ WebSocket connected: sessionId={}, clients={}", sessionId, sessionsByCinemaSession.get(sessionId).size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long sessionId = extractSessionId(session);
        Set<WebSocketSession> sessions = sessionsByCinemaSession.get(sessionId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                sessionsByCinemaSession.remove(sessionId);
            }
        }
        log.info("❌ WebSocket disconnected: sessionId={}, remaining clients={}", sessionId, sessionsByCinemaSession.getOrDefault(sessionId, Set.of()).size());
    }

    public void sendSeatUpdate(Long sessionId, Object payload) {
        Set<WebSocketSession> sessions = sessionsByCinemaSession.get(sessionId);
        if (sessions == null || sessions.isEmpty()) return;

        try {
            String json = objectMapper.writeValueAsString(payload);
            TextMessage message = new TextMessage(json);

            for (WebSocketSession ws : sessions) {
                if (ws.isOpen()) {
                    ws.sendMessage(message);
                }
            }

            log.info("📤 Sent seat update to {} clients for session {}", sessions.size(), sessionId);
        } catch (Exception e) {
            log.error("❗ Error sending WebSocket message", e);
        }
    }

    private Long extractSessionId(WebSocketSession session) {
        String path = Objects.requireNonNull(session.getUri()).getPath();

        String[] parts = path.split("/");
        return Long.parseLong(parts[3]);
    }
}
