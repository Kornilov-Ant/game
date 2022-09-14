package com.game.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

import static com.game.model.DbConstants.DB_SCHEMA;

@Entity
@Table(name = "Player", schema = DB_SCHEMA)
public class Player {

    @Id
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Title")
    private String title;

    @Column(name = "Race")
    private String race;

    @Column(name = "Profession")
    private String profession;

    @Column(name = "Birthday")
    private Date data;

    @Column(name = "Banned")
    private Boolean banned;

    @Column(name = "experience")
    private Long experience;

    @Column(name = "Level")
    private Long level;

    @Column(name = "UntilNextLevel")
    private Long untilNextLevel;
}
