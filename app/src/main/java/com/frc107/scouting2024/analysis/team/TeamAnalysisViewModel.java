package com.frc107.scouting2024.analysis.team;

import com.frc107.scouting2024.IUIListener;

public class TeamAnalysisViewModel {
    private TeamAnalysisModel model;

    public TeamAnalysisViewModel(IUIListener listener) {
        model = new TeamAnalysisModel(listener);
    }

    public void loadData() {
        model.loadData();
    }

    public void setTeamNumber(String teamNumber) {
        model.setTeamNumber(teamNumber);
    }

    public double getAverageCargo() {
        return model.getAverageCargo();
    }

    public double getAverageHatchPanels() {
        return model.getAverageHatchPanels();
    }

    public int getDefenseCount() {
        return model.getDefenseCount();
    }

    public int getEffectiveDefenseCount() {
        return model.getEffectiveDefenseCount();
    }

    public boolean canAccessRocketOne() {
        return model.canAccessRocketOne();
    }

    public boolean canAccessRocketTwo() {
        return model.canAccessRocketTwo();
    }

    public boolean canAccessRocketThree() {
        return model.canAccessRocketThree();
    }

    public boolean canClimbHabOne() {
        return model.canClimbHabOne();
    }

    public boolean canClimbHabTwo() {
        return model.canClimbHabTwo();
    }

    public boolean canClimbHabThree() {
        return model.canClimbHabThree();
    }

    public String[] getTeamNumbers() {
        return model.getTeamNumbers();
    }

    public double getOPR() {
        return model.getOPR();
    }

    public double getDPR() {
        return model.getDPR();
    }
}
