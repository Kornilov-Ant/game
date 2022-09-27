package com.game.repository;

import com.game.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.Date;
import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    @Query("SELECT c FROM Player c" +
            " WHERE c.name LIKE %:a%" +
            " AND c.title LIKE %:b%" +
            " AND (:d IS NULL OR c.race = :d)" +
            " AND (:e IS NULL OR c.profession = :e)" +
            " AND (:h IS NULL OR c.banned = :h)" +
            " AND (c.data BETWEEN :f AND :g)" +
            " AND (c.experience BETWEEN :i AND :j)" +
            " AND (c.level >= :k) AND (c.level <= :l)" +
            " ORDER BY :m"
    )

    List<Player> findByQuery(
            @Param("a") String name, @Param("b") String title,
            @Param("d") String race, @Param("e") String profession,
            @Param("f") Date after, @Param("g") Date before,
            @Param("h") Boolean banned,
            @Param("i") Long minExperience, @Param("j") Long maxExperience,
            @Param("k") Integer minLevel, @Param("l") Integer maxLevel, @Param("m") String order
//            @Param("n") Long pageNumber, @Param("o") Long pageSize
    );

}