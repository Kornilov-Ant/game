package com.game.service;

import com.game.model.dto.PlayerDTO;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    Optional<PlayerDTO> findById(Long id);
    Long save(PlayerDTO playerDTO);
    List<PlayerDTO> findAll();
    void update(Long id, PlayerDTO dto);

    List<PlayerDTO> findByQuery(String query);

}
