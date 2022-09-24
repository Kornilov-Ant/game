package com.game.controller;

import com.game.model.dto.PlayerDTO;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "/players", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerDTO>> playerList(String query) {
        if (query == null) return new ResponseEntity<>(playerService.findAll(), HttpStatus.OK);

        return new ResponseEntity<>(playerService.findByQuery(query), HttpStatus.OK);
    }

    @GetMapping(value = "/players/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countPlayers() {
        return new ResponseEntity<>(Long.valueOf(playerService.findAll().size()), HttpStatus.OK);
    }

    @PostMapping(value = "/players", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerDTO> create(PlayerDTO dto) {
        Long result = playerService.create(dto);
        if (result > 0) {
            return new ResponseEntity<>(playerService.findById(playerService.save(dto)).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/players/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PlayerDTO getPlayerToId(@PathVariable("id") Long id) {
        Optional<PlayerDTO> dto = playerService.findById(id);
        return null;
    }

}

// не работало