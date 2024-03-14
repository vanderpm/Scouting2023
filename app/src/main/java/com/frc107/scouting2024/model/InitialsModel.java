package com.frc107.scouting2024.model;

import com.frc107.scouting2024.Scouting;

public class InitialsModel {
    private String initials;

    public InitialsModel() {
        initials = "";
    }

    public void setInitials(String initals) {
        this.initials = initals;
        Scouting.getInstance().setInitials(initials);
    }

    public String getInitials() {
        return initials;
    }
}
