package com.frc107.scouting2024.analysis.attribute;

public class AnalysisElement {
    private String teamNumber;
    private double attribute;

    public AnalysisElement(String teamNumber, double attribute) {
        this.teamNumber = teamNumber;
        this.attribute = attribute;
    }

    public String getTeamNumber() {
        return teamNumber;
    }

    public double getAttribute() {
        return attribute;
    }
}
