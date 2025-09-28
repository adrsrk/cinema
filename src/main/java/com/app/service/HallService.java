package com.app.service;

import com.app.entity.Hall;
import com.app.model.hall.HallRequestDTO;
import org.springframework.data.domain.Page;


public interface HallService {

    Hall createHall(Hall hall);

    Page<Hall> getAllHallsPageable(int page, int size);

    Hall getHallById(Long hallId);

    Hall updateHall(Long hallId, HallRequestDTO hallRequestDTO);

    void deleteHall(Long hallId);
}
