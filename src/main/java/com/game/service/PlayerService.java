package com.game.service;

import com.game.model.Player;
import com.game.model.dto.PlayerDTO;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    Optional<PlayerDTO> findById(Long id);
    Long save(PlayerDTO playerDTO);
    List<PlayerDTO> findAll();
    PlayerDTO update(Long id, PlayerDTO dto);

    void delete(Long id);

    Long create(PlayerDTO dto);

    List<PlayerDTO> findByQuery(String queryOne, String queryTwo);

    PlayerDTO converterEntityToDto(Player player);
}
