package com.jpvr.tddexercises.tdd.hamcrest;

public class Fellowship {

    public enum race {
        ORC,
        HUMAN,
        ELF
    }

    public Fellowship() {
    }

    private race fellowshipRace;

    public race getFellowshipRace() {
        return fellowshipRace;
    }

    public void setFellowshipRace(race fellowshipRace) {
        this.fellowshipRace = fellowshipRace;
    }
} // end class Fellowship
