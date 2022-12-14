package com.game.service;

import com.game.entity.Player;
import com.game.entity.dto.PlayerDTO;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    Optional<PlayerDTO> findById(Long id);

    Long save(PlayerDTO playerDTO);

    List<PlayerDTO> findAll();

    PlayerDTO update(Long id, PlayerDTO dto);

    List<PlayerDTO> findByQuery(
                                String name, String title, String race, String professional,
                                String after, String before, String banned, String minExperience,
                                String maxExperience, String minLevel, String maxLevel,
                                String order, String pageNumber, String pageSize);

    void delete(Long id);

    Long create(PlayerDTO dto);

    PlayerDTO converterEntityToDto(Player player);
}
