package com.app.service;

import com.app.model.booking.SeatDTO;
import com.app.websocket.SeatWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SeatUpdateNotifier {

    private final SeatWebSocketHandler webSocketHandler;

    public void notifySeatUpdate(Long sessionId, List<SeatDTO> takenSeats) {
        Map<String, Object> message = Map.of(
                "type", "seat_update",
                "sessionId", sessionId,
                "takenSeats", takenSeats
        );

        webSocketHandler.sendSeatUpdate(sessionId, message);
    }
}
