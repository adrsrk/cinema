package com.app.service;

import com.app.entity.Hall;
import com.app.model.hall.HallRequestDTO;
import com.app.model.hall.HallResponseDTO;

import java.util.List;

public interface HallService {

    Hall createHall(Hall hall);

    List<Hall> getAllHalls();

    Hall getHallById(Long hallId);

    Hall updateHall(Long hallId, HallRequestDTO hallRequestDTO);

    void deleteHall(Long hallId);
}
