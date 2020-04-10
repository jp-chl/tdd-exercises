package com.jpvr.tddexercises.tdd.streams;

import java.time.Duration;

public class Winner {

    private int year;
    private String nationality;
    private String name;
    private String team;
    private int lengthKm;
    private Duration winningTime;
    private int stageWins;
    private int daysInYellow;

    public Winner(int year, String nationality, String name, String team, int lengthKm, Duration winningTime, int daysInYellow) {
        this.year = year;
        this.nationality = nationality;
        this.name = name;
        this.team = team;
        this.lengthKm = lengthKm;
        this.winningTime = winningTime;
        this.daysInYellow = daysInYellow;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getLengthKm() {
        return lengthKm;
    }

    public void setLengthKm(int lengthKm) {
        this.lengthKm = lengthKm;
    }

    public Duration getWinningTime() {
        return winningTime;
    }

    public void setWinningTime(Duration winningTime) {
        this.winningTime = winningTime;
    }

    public int getStageWins() {
        return stageWins;
    }

    public void setStageWins(int stageWins) {
        this.stageWins = stageWins;
    }

    public int getDaysInYellow() {
        return daysInYellow;
    }

    public void setDaysInYellow(int daysInYellow) {
        this.daysInYellow = daysInYellow;
    }
} // end class Winner
