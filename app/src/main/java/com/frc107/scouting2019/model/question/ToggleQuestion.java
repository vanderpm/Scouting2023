package com.frc107.scouting2019.model.question;

public class ToggleQuestion extends Question<Boolean> {
    private boolean answer;

    public ToggleQuestion(int id) {
        super(id);
    }

    @Override
    public boolean needsAnswer() {
        // Since it's a toggle, it'll always have an answer: true or false.
        return false;
    }

    @Override
    public boolean hasAnswer() {
        // Since it's a toggle, we can't exactly differentiate between an intentional or unintentional empty selection.
        return true;
    }

    @Override
    public void setAnswer(Boolean answer) {
        this.answer = answer == null ? false : answer;
    }

    @Override
    public Boolean getAnswer() {
        return answer;
    }

    @Override
    public String getAnswerAsString() {
        return String.valueOf(answer);
    }
}
