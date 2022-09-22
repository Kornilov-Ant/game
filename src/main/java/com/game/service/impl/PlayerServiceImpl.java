package com.game.service.impl;

import com.game.model.Player;
import com.game.model.dto.PlayerDTO;
import com.game.repository.PlayerRepository;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Optional<PlayerDTO> findById(Long id) {
        return playerRepository.findById(id).map(this::map);
    }

    @Override
    public Long save(PlayerDTO playerDTO) {
        return null;
    }

    @Override
    public List<PlayerDTO> findAll() {
        return Streamable.of(playerRepository.findAll()).map(player -> map(player)).toList();
    }

    @Override
    public void update(Long id, PlayerDTO dto) {

    }

    @Override
    public List<PlayerDTO> findByQuery(String query) {
        return playerRepository.findByQuery(query).stream().map(player -> map(player)).collect(Collectors.toList());
    }

    private PlayerDTO map(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setName(player.getName());
        dto.setTitle(player.getTitle());
        dto.setRace(player.getRace());
        dto.setProfession(player.getProfession());
        dto.setData(player.getData());
        dto.setBanned(player.getBanned());
        dto.setExperience(player.getExperience());
        dto.setLevel(player.getLevel());
        dto.setUntilNextLevel(player.getUntilNextLevel());
        return dto;
    }
}
