package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.model.Player;
import com.game.model.dto.PlayerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Date;
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
