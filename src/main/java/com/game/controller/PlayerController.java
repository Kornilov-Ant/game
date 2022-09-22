package com.game.controller;

import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.model.dto.PlayerDTO;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "/players", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PlayerDTO> playerList(String query) {
        if (query == null) return playerService.findAll();

        return playerService.findByQuery(query);
    }

    @GetMapping(value = "/players/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer countPlayers() {
        return playerService.findAll().size();
    }

    @PostMapping(value = "/players")
    public ResponseEntity<?> create(String name, String title,
                                    Race race, Profession profession,
                                    Long birthday, Boolean banned,
                                    Integer experience) {
        if (0 > 1) {
            return null;
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

// не работало