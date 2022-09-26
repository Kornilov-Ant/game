package com.game.repository;

import com.game.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    @Query("SELECT c FROM Player c WHERE c.name LIKE %:q% OR c.title LIKE %:z%")
    List<Player> findByQuery(@Param("q") String queryOne, @Param("z") String queryTwo);

    @Query("SELECT c FROM Player c WHERE c.name LIKE %:q%")
    List<Player> findByQueryName(@Param("q") String query);

    @Query("SELECT c FROM Player c WHERE c.title LIKE %:q%")
    List<Player> findByQueryTitle(@Param("q") String query);

}

// не работало