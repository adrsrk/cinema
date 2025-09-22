package com.app.service;

import com.app.entity.Hall;
import com.app.exception.HallNotFoundException;
import com.app.model.hall.HallRequestDTO;
import com.app.repository.HallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;


    @Override
    public Hall createHall(Hall hall) {
        return hallRepository.save(hall);
    }

    @Override
    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    @Override
    public Hall getHallById(Long hallId) {
        return hallRepository.findById(hallId)
                .orElseThrow(() -> new HallNotFoundException(hallId));
    }

    /**
     * Обновляет данные о зале по ID.
     *
     * @param hallId ID зала, который нужно обновить
     * @param hallRequestDTO DTO с новыми данными
     * @return обновлённый объект зала
     * @throws HallNotFoundException если зал с таким ID не найден
     */
    @Override
    @Transactional
    public Hall updateHall(Long hallId, HallRequestDTO hallRequestDTO) {

        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new HallNotFoundException(hallId));

        hall.setName(hallRequestDTO.name());
        hall.setRows(hallRequestDTO.rows());
        hall.setSeatsPerRow(hallRequestDTO.seatsPerRow());
        hall.setUpdatedAt(LocalDateTime.now());

        return hallRepository.save(hall);
    }

    @Override
    public void deleteHall(Long hallId) {

        if (!hallRepository.existsById(hallId)) {
            throw new HallNotFoundException(hallId);
        }

        hallRepository.deleteById(hallId);
    }
}
