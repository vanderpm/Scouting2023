package com.frc107.scouting2024.viewmodel;

import com.frc107.scouting2024.model.InitialsModel;

import androidx.lifecycle.ViewModel;

public class InitialsViewModel extends ViewModel {
    private InitialsModel model;

    public InitialsViewModel() {
        model = new InitialsModel();
    }

    public void setInitials(String initials) {
        model.setInitials(initials);
    }

    public String getInitials() {
        return model.getInitials();
    }
}
