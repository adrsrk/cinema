package com.app.controller;

import com.app.entity.Hall;
import com.app.model.MessageCreateResponseDTO;
import com.app.model.MessageResponseDTO;
import com.app.model.hall.HallRequestDTO;
import com.app.model.hall.HallResponseDTO;
import com.app.service.HallService;
import com.app.util.HallMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hall")
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;
    private final HallMapper mapper;

    @GetMapping
    public ResponseEntity<Page<HallResponseDTO>> getAllHalls(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<Hall> halls = hallService.getAllHallsPageable(page, size);
        Page<HallResponseDTO> hallsByPage = halls.map(mapper::toDTO);

        return ResponseEntity.ok(hallsByPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallResponseDTO> getHallById(@PathVariable Long id) {

        Hall hall = hallService.getHallById(id);
        return ResponseEntity.ok(mapper.toDTO(hall));
    }

    @PostMapping
    public ResponseEntity<MessageCreateResponseDTO> createHall(@Valid @RequestBody HallRequestDTO hallRequestDTO) {

        Hall hall = mapper.toEntity(hallRequestDTO);
        Hall createdHall = hallService.createHall(hall);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageCreateResponseDTO("Зал успешно создан", createdHall.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateHall(@Valid @PathVariable Long id, @RequestBody HallRequestDTO hallRequestDTO) {

        hallService.updateHall(id, hallRequestDTO);
        return ResponseEntity.ok(new MessageResponseDTO("Информация о зале обновлена"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteHall(@PathVariable Long id) {

        hallService.deleteHall(id);
        return ResponseEntity.ok(new MessageResponseDTO("Зал успешно удалён"));
    }
}
