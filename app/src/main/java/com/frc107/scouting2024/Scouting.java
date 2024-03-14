package com.frc107.scouting2024;

import com.frc107.scouting2024.utils.FileUtils;

import java.util.ArrayList;

public class Scouting {
    public static final String VERSION_DATE = "4/21/2019 - 1:25";
    public static final String PREFERENCES_NAME = "ScoutingPreferences";
    public static final String EVENT_KEY_PREFERENCE = "eventKey";

    public static final int AUTON_LEFT_START = 0,
                            AUTON_MIDDLE_START = 1,
                            AUTON_RIGHT_START = 2,
                            AUTON_STARTING_GAME_PIECE = 1,
                            AUTON_NOT_STARTING_GAME_PIECE = 0,
                            AUTON_SPEAKER_PLACED = 1,
                            AUTON_AMP_PLACED = 2,
                            AUTON_FLOOR_PLACED = 3,
                            AUTON_NOTHING_PLACED = 4;
    public static final int CYCLE_SOURCE_PICKUP = 0,
                            CYCLE_FLOOR_PICKUP = 1,
                            CYCLE_STARTED_WITH_ITEM = 2,
                            CYCLE_SPEAKER = 1,
                            CYCLE_AMPLIFIED_SPEAKER = 2,
                            CYCLE_AMP = 3,
                            CYCLE_FLOOR = 4,
                            CYCLE_NOTHING_PLACED = 0;
    public static final int ENDGAME_SLOW_SPEED = 1,
                            ENDGAME_MEDIUM_SPEED = 2,
                            ENDGAME_FAST_SPEED = 3,
                            ENDGAME_DEAD_SPEED = 0,
                            ENDGAME_PARKED_ROBOT = 1,
                            ENDGAME_ATTEMPT_ONSTAGE = 2,
                            ENDGAME_SUCCESS_ONSTAGE = 3,
                            ENDGAME_HARMONIZED = 4,
                            ENDGAME_NONE = 0,
                            ENDGAME_DEFENSE_EFFECTIVE = 1,
                            ENDGAME_DEFENSE_INEFFECTIVE = 2,
                            ENDGAME_DEFENSE_NONE = 0;

    public static final FileUtils FILE_UTILS = new FileUtils();
    public static final boolean SAVE_QUESTION_NAMES_AS_ANSWERS = false;


    private static Scouting scouting;
    public static Scouting getInstance() {
        if (scouting == null)
            scouting = new Scouting();
        return scouting;
    }

    private Scouting() {
        cycles = new ArrayList<String>();
    }

    private String uniqueId;
    public void setUniqueId(String newUniqueId) {
        uniqueId = newUniqueId;
    }
    public String getUniqueId() {
        return uniqueId;
    }

    private int teamNumber = -1;
    public void setTeamNumber(int newTeamNumber) {
        teamNumber = newTeamNumber;
    }
    public int getTeamNumber() {
        return teamNumber;
    }

    private int matchNumber = -1;
    public void setMatchNumber(int newMatchNumber) {
        matchNumber = newMatchNumber;
    }
    public int getMatchNumber() {
        return matchNumber;
    }

    private String autonData;
    public void setAutonData(String newAutonData) {
        autonData = newAutonData;
    }
    public String getAutonData() {
        return autonData;
    }

    private ArrayList<String> cycles;
    public void addCycle(String cycle) {
        cycles.add(cycle);
    }
    public ArrayList<String> getCycles() {
        return cycles;
    }
    public void clearCycles() {
        cycles.clear();
    }

    private String initials;
    public void setInitials(String initials) {
        this.initials = initials;
    }
    public String getInitials() { return initials; }

    private String eventKey;
    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
    public String getEventKey() {
        return eventKey;
    }

    // TODO: Go through radiogroups and fix formatting
    // TODO: naming conventions
    // TODO: update button strings, example being save button in cycle
}
