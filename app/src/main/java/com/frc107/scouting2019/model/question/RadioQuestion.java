package com.frc107.scouting2019.model.question;

import java.util.ArrayList;
import java.util.Arrays;

public class RadioQuestion extends Question<Integer> {
    private ArrayList<Option> options;
    private Option selectedOption;
    private boolean needsAnswer;

    public RadioQuestion(int id, boolean needsAnswer, Option... options) {
        super(id);
        this.needsAnswer = needsAnswer;
        this.options = new ArrayList<>(Arrays.asList(options));
    }

    @Override
    public boolean needsAnswer() {
        return needsAnswer;
    }

    @Override
    public boolean hasAnswer() {
        return selectedOption != null;
    }

    @Override
    public void setAnswer(Integer answerId) {
        // We check for -1 here because when the check is cleared from the RadioGroup, it sets it as being checked to id -1. It's
        // more efficient to just set the answer to null when the id is -1 because then we avoid looping through the options.
        if (answerId == null || answerId == -1) {
            selectedOption = null;
            return;
        }

        for (Option option : options) {
            if (option.getId() == answerId) {
                selectedOption = option;
            }
        }
    }

    @Override
    public Integer getAnswer() {
        return selectedOption.getId();
    }

    @Override
    public String getAnswerAsString() {
        if (selectedOption == null)
            return "";

        return selectedOption.getText();
    }

    public static class Option {
        private int id;
        private String text;

        public Option(int id, String text) {
            this.id = id;
            this.text = text;
        }

        public int getId() {
            return id;
        }

        public String getText() {
            return text;
        }
    }
}

