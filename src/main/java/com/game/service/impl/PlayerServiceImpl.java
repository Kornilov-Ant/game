package com.game.service.impl;

import com.game.model.Player;
import com.game.model.dto.PlayerDTO;
import com.game.repository.PlayerRepository;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.GregorianCalendar;
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
        return playerRepository.findById(id).map(this::converterEntityToDto);
    }

    @Override
    public Long save(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setName(playerDTO.getName());
        player.setTitle(playerDTO.getTitle());
        player.setRace(playerDTO.getRace());
        player.setProfession(playerDTO.getProfession());
        player.setData(new Date(playerDTO.getBirthday()));
        player.setBanned(playerDTO.getBanned());
        player.setExperience(playerDTO.getExperience());
        player.setLevel(level(player.getExperience())); // - уровень
        player.setUntilNextLevel(nextLevel(player.getLevel(), player.getExperience())); // - опыта до следующего уровня

        return playerRepository.save(player).getId();
    }

    @Override
    public Long create(PlayerDTO dto) {
        try {
            if (
                    dto.getName() == null ||
                            dto.getTitle() == null ||
                            dto.getRace() == null ||
                            dto.getProfession() == null ||
                            dto.getBirthday() == null ||
                            dto.getExperience() == null
            ) {
                new RuntimeException();
            } else if (dto.getName().length() > 12 || dto.getTitle().length() > 30) {
                new RuntimeException();
            } else if (dto.getName().equals("")) {
                new RuntimeException();
            } else if (dto.getExperience() > 10_000_000L) {
                new RuntimeException();
            } else if (dto.getBirthday() < 0) {
                new RuntimeException();
            } else if (dto.getBirthday() < new GregorianCalendar(2000, 0, 1).getTimeInMillis() ||
                    dto.getBirthday() > new GregorianCalendar(3000, 11, 31).getTimeInMillis()) {
                new RuntimeException();
            }
            return save(dto);
        } catch (Exception e) {
            return -1L;
        }
    }

    @Override
    public List<PlayerDTO> findAll() {
        return Streamable.of(playerRepository.findAll()).map(player -> converterEntityToDto(player)).toList();
    }

    @Override
    public void update(Long id, PlayerDTO dto) {

    }

    @Override
    public List<PlayerDTO> findByQuery(String query) {
        return playerRepository.findByQuery(query).stream().map(player -> converterEntityToDto(player)).collect(Collectors.toList());
    }

    @Override
    public PlayerDTO converterEntityToDto(Player player) {
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

    private Integer level(Long exp) {
        Double sqrt = ((Math.sqrt(2500 + (200 * exp))) - 50) / 100;
        return sqrt.intValue();
    }

    private Long nextLevel(int level, Long exp) {
        return 50 * (level + 1) * (level + 2) - exp;
    }
}
