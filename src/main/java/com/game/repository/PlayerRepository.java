package com.game.repository;

import com.game.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    @Query("SELECT c FROM Player c" +
            "WHERE c.name LIKE %:a%" +
            "AND c.title LIKE %:b%" +
            "AND c.race LIKE %:d%" +
            "AND c.profession LIKE %:e%" +
            "AND c.banned LIKE :h" +
            "AND c.birthday BETWEEN :f AND :g" +
            "AND c.level BETWEEN :k AND :l" +
            "")
    List<Player> findByQuery(
            @Param("a") String name, @Param("b") String title,
            @Param("d") String race, @Param("e") String professional,
            @Param("f") Date after, @Param("g") Date before,
            @Param("h") Boolean banned, @Param("i") String minExperience,
            @Param("j") String maxExperience, @Param("k") String minLevel,
            @Param("l") String maxLevel, @Param("m") String order,
            @Param("n") String pageNumber, @Param("o") String pageSize
    );

}