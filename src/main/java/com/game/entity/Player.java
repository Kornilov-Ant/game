package com.game.entity;

import javax.persistence.*;

import java.util.Date;

import static com.game.entity.DbConstants.DB_SCHEMA;

@Entity
//@Table(name = "Player", schema = DB_SCHEMA)
@Table(name = "Player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Date birthday;

    @Column(name = "Banned")
    private Boolean banned;

    @Column(name = "Experience")
    private Long experience;

    @Column(name = "Level")
    private Integer level;

    @Column(name = "UntilNextLevel")
    private Long untilNextLevel;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date data) {
        this.birthday = data;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Long untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }
}
