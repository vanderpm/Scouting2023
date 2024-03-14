package com.frc107.scouting2024.viewmodel;

import com.frc107.scouting2024.model.EndGameModel;

public class EndGameViewModel extends ScoutViewModel {
    public EndGameViewModel() {
        model = new EndGameModel();
    }

    public String finish() {
        return ((EndGameModel) model).finish();
    }
}
