package com.frc107.scouting2024.viewmodel;

import com.frc107.scouting2024.model.AutonModel;

public class AutonViewModel extends ScoutViewModel {
    public AutonViewModel() {
        model = new AutonModel();
    }

    public void finish() {
        ((AutonModel) model).finish();
    }

    public int getTeamNumber() {
        return ((AutonModel) model).getTeamNumber();
    }

    public boolean shouldAllowStartingPiece() {
        return ((AutonModel) model).shouldAllowStartingPiece();
    }
}
