package com.game.controller;

import com.game.entity.dto.PlayerDTO;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<PlayerDTO>> playerList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) String race,
            @RequestParam(value = "profession", required = false) String profession,
            @RequestParam(value = "after", required = false) String after,
            @RequestParam(value = "before", required = false) String before,
            @RequestParam(value = "banned", required = false) String banned,
            @RequestParam(value = "minExperience", required = false) String minExperience,
            @RequestParam(value = "maxExperience", required = false) String maxExperience,
            @RequestParam(value = "minLevel", required = false) String minLevel,
            @RequestParam(value = "maxLevel", required = false) String maxLevel,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "pageNumber", required = false) String pageNumber,
            @RequestParam(value = "pageSize", required = false) String pageSize
    ) {
        return new ResponseEntity<>(
                playerService.findByQuery(
                        name, title, race, profession,
                        after, before, banned, minExperience, maxExperience,
                        minLevel, maxLevel, order, pageNumber, pageSize
                ),
                HttpStatus.OK);
    }

    @GetMapping(value = "/players/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countPlayers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) String race,
            @RequestParam(value = "profession", required = false) String profession,
            @RequestParam(value = "after", required = false) String after,
            @RequestParam(value = "before", required = false) String before,
            @RequestParam(value = "banned", required = false) String banned,
            @RequestParam(value = "minExperience", required = false) String minExperience,
            @RequestParam(value = "maxExperience", required = false) String maxExperience,
            @RequestParam(value = "minLevel", required = false) String minLevel,
            @RequestParam(value = "maxLevel", required = false) String maxLevel
    ) {
        return new ResponseEntity<>(Long.valueOf(
                playerService.findByQuery(
                        name, title, race,
                        profession, after, before,
                        banned, minExperience, maxExperience,
                        minLevel, maxLevel, "id", "0", "10000000"
                ).size()),
                HttpStatus.OK);
    }

    @PostMapping(value = "/players")
    public ResponseEntity<PlayerDTO> create(@RequestBody PlayerDTO dto) {
        Long result = playerService.create(dto);
        if (result > 0) {
            return new ResponseEntity<>(playerService.findById(result).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/players/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerDTO> getPlayerToId(@PathVariable("id") Long id) {
        try {
            Long back = (Long) id; // проверка на число
            if (id < 1) throw new RuntimeException(); // проверка что больше нуля
            if (id % 1 != 0) throw new RuntimeException(); // проверка что целое число
            playerService.findById(id).orElseThrow(() -> new Exception());
            return new ResponseEntity<>(playerService.findById(id).get(), HttpStatus.OK);
        } catch (NumberFormatException nfe) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException re) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/players/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerDTO> updatePlayerToId(@PathVariable("id") Long id, @RequestBody PlayerDTO dto) {
        try {
            Long back = (Long) id; // проверка на число
            if (id < 1) throw new RuntimeException(); // проверка что больше нуля
            if (id % 1 != 0) throw new RuntimeException(); // проверка что целое число
            playerService.findById(id).orElseThrow(() -> new Exception());
            return new ResponseEntity<>(playerService.update(id, dto), HttpStatus.OK);
        } catch (NumberFormatException nfe) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException re) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/players/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePlayerToId(@PathVariable("id") Long id) {
        try {
            Long back = (Long) id; // проверка на число
            if (id < 1) throw new RuntimeException(); // проверка что больше нуля
            if (id % 1 != 0) throw new RuntimeException(); // проверка что целое число
            playerService.findById(id).orElseThrow(() -> new Exception());
            playerService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NumberFormatException nfe) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException re) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}