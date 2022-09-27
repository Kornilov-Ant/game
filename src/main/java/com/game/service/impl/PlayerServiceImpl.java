package com.game.service.impl;

import com.game.model.Player;
import com.game.model.dto.PlayerDTO;
import com.game.repository.PlayerRepository;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;
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
        player.setBirthday(new Date(playerDTO.getBirthday()));
        player.setBanned(playerDTO.getBanned());
        player.setExperience(playerDTO.getExperience());
        player.setLevel(level(player.getExperience())); // - уровень
        player.setUntilNextLevel(nextLevel(player.getLevel(), player.getExperience())); // - опыта до следующего уровня

        return playerRepository.save(player).getId();
    }

    @Override
    public Long create(PlayerDTO dto) {
        if (!Optional.ofNullable(dto.getBanned()).isPresent()) {
            dto.setBanned(false);
        }
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
    public PlayerDTO update(Long id, PlayerDTO dto) {
        Player player = Optional.ofNullable(playerRepository.findById(id)).get().get();
        if (Optional.ofNullable(dto.getName()).isPresent()) player.setName(dto.getName());
        if (Optional.ofNullable(dto.getTitle()).isPresent()) player.setTitle(dto.getTitle());
        if (Optional.ofNullable(dto.getRace()).isPresent()) player.setRace(dto.getRace());
        if (Optional.ofNullable(dto.getProfession()).isPresent()) player.setProfession(dto.getProfession());
        if (Optional.ofNullable(dto.getBirthday()).isPresent()) player.setBirthday(new Date(dto.getBirthday()));
        if (Optional.ofNullable(dto.getBanned()).isPresent()) player.setBanned(dto.getBanned());
        if (Optional.ofNullable(dto.getExperience()).isPresent()) {
            player.setExperience(dto.getExperience());
            player.setLevel(level(player.getExperience())); // - уровень
            player.setUntilNextLevel(nextLevel(player.getLevel(), player.getExperience())); // - опыта до следующего уровня
        }
        playerRepository.save(player);
        return converterEntityToDto(playerRepository.findById(id).get());
    }

    @Override
    public List<PlayerDTO> findByQuery(
            String name, String title, String race, String profession,
            String after, String before, String banned, String minExperience,
            String maxExperience, String minLevel, String maxLevel,
            String order, String pageNumber, String pageSize) {
        System.out.println("!!!!!findByQuery!!!!!");
        return playerRepository.findByQuery(Sort.by(Sort.Direction.ASC, order.toLowerCase()),
                        name == null ? "" : name,
                        title == null ? "" : title,
                        race,
                        profession,
                        after == null ?
                                new Date(new GregorianCalendar(1970, 0, 1).getTimeInMillis())
                                : new Date(Long.valueOf(after)),
                        before == null ?
                                new Date(new GregorianCalendar(3000, 11, 31).getTimeInMillis())
                                : new Date(Long.valueOf(before)),
                        banned == null ? null : Boolean.valueOf(banned),
                        minExperience == null ? 0 : Long.valueOf(minExperience),
                        maxExperience == null ? 10_000_000 : Long.valueOf(maxExperience),
                        minLevel == null ? 0 : Integer.valueOf(minLevel),
                        maxLevel == null ? 500 : Integer.valueOf(maxLevel)
//                        pageNumber == null ? 0L : Long.valueOf(pageNumber),
//                        pageSize == null ? 3L : Long.valueOf(pageSize)
                )
                .stream()
                .map(player -> converterEntityToDto(player)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        playerRepository.delete(playerRepository.findById(id).get());
    }

    @Override
    public PlayerDTO converterEntityToDto(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setName(player.getName());
        dto.setTitle(player.getTitle());
        dto.setRace(player.getRace());
        dto.setProfession(player.getProfession());
        dto.setData(player.getBirthday());
        dto.setBirthday(dto.getData().getTime());
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

//    private Sort sortByNameAsc(String line) {
//        return new Sort(Sort.Direction.ASC, line);
//    }
}
