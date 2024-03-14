package com.frc107.scouting2024.viewmodel;

import com.frc107.scouting2024.model.CycleModel;

public class CycleViewModel extends ScoutViewModel {
    public CycleViewModel(int teamNumber) {
        model = new CycleModel(teamNumber);
    }

    public void finishCycle() {
        ((CycleModel) model).finishCycle();
    }

    public void setAllDefense(boolean allDefense) {
        ((CycleModel) model).setAllDefense(allDefense);
    }

    public boolean cycleCanBeFinished() {
        return ((CycleModel) model).cycleCanBeFinished();
    }

    public boolean hasUsedStartingItem() {
        return ((CycleModel) model).hasUsedStartingItem();
    }

    public void disableStartingItem() {
        ((CycleModel) model).disableStartingItem();
    }

    public int getTeamNumber() {
        return ((CycleModel) model).getTeamNumber();
    }
}
