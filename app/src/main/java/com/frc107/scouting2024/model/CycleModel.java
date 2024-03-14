package com.frc107.scouting2024.model;

import com.frc107.scouting2024.R;
import com.frc107.scouting2024.Scouting;
import com.frc107.scouting2024.model.question.Question;
import com.frc107.scouting2024.model.question.RadioQuestion;
import com.frc107.scouting2024.model.question.ToggleQuestion;

public class CycleModel extends ScoutModel {
    private int cycleNum;
    private boolean isFirstCycle = true;
    private boolean hasUsedStartingItem;
    private int teamNumber;

    public CycleModel(int teamNumber) {
        super();
        this.teamNumber = teamNumber;
    }

    @Override
    public Question[] getQuestions() {
        ToggleQuestion allDefenseQuestion = new ToggleQuestion("cycleAllDefense", R.id.allDefense_chkbx);
        allDefenseQuestion.setIgnoreAnswer(true);

        Question[] questions = {
                new RadioQuestion("cyclePickupLoc", R.id.pickupLocationRadioQuestion, true,
                        new RadioQuestion.Option(R.id.sourcePickupLocation_Radiobtn, Scouting.CYCLE_SOURCE_PICKUP),
                        new RadioQuestion.Option(R.id.floorPickupLocation_Radiobtn, Scouting.CYCLE_FLOOR_PICKUP),
                        new RadioQuestion.Option(R.id.startedWithItem_Radiobtn, Scouting.CYCLE_STARTED_WITH_ITEM)),
                new RadioQuestion("cycleItemPlaced", R.id.itemPlacedRadioQuestion, true,
                        new RadioQuestion.Option(R.id.speakerItemPlaced_Radiobtn, Scouting.CYCLE_SPEAKER),
                        new RadioQuestion.Option(R.id.amplifiedSpeakerItemPlaced_Radiobtn, Scouting.CYCLE_AMPLIFIED_SPEAKER),
                        new RadioQuestion.Option(R.id.ampItemPlaced_Radiobtn, Scouting.CYCLE_AMP),
                        new RadioQuestion.Option(R.id.floorItemPlaced_Radiobtn, Scouting.CYCLE_FLOOR),
                        new RadioQuestion.Option(R.id.nothingPlacedItemPlaced_Radiobtn, Scouting.CYCLE_NOTHING_PLACED)),
                new ToggleQuestion("cycleDefense", R.id.defense_chkbx),
                allDefenseQuestion
        };
        return questions;
    }

    @Override
    public void onNumberQuestionAnswered(int questionId, Integer answer) { }

    @Override
    public void onTextQuestionAnswered(int questionId, String answer) { }

    @Override
    public void onRadioQuestionAnswered(int questionId, int answerId) {
        if (questionId == R.id.pickupLocationRadioQuestion) {
            if (answerId == R.id.startedWithItem_Radiobtn) {
                hasUsedStartingItem = true;
            } else {
                hasUsedStartingItem = false;
            }
        }
    }

    public void finishCycle() {
        cycleNum++;
        saveCycle();
        isFirstCycle = false;
    }

    private void saveCycle() {
        // TODO: This would be cleaner if you:
        // 1: renamed Questions to Fields
        // 2: for type and cycleNum, made a kind of Field that's just called VariableField or something, something that's just controlled in-code, not outside of code
        String csvRow = getAnswerCSVRow();
        String cycle = cycleNum + "," + csvRow;

        if (Scouting.SAVE_QUESTION_NAMES_AS_ANSWERS) {
            cycle = "cycleNum," + csvRow;
        }

        Scouting.getInstance().addCycle(cycle);
    }

    public void setAllDefense(boolean allDefense) {
        Question pickupLocationQuestion = getQuestion(R.id.pickupLocationRadioQuestion);
        Question itemPlacedRadioQuestion = getQuestion(R.id.itemPlacedRadioQuestion);

        boolean needsAnswers = !allDefense;
        pickupLocationQuestion.setNeedsAnswer(needsAnswers);
        itemPlacedRadioQuestion.setNeedsAnswer(needsAnswers);
    }

    public boolean cycleCanBeFinished() {
        if (areNoQuestionsAnswered() || isFormComplete())
            return true;

        return false;
    }

    public boolean hasUsedStartingItem() {
        return hasUsedStartingItem;
    }

    public void disableStartingItem() {
        hasUsedStartingItem = true;
    }

    public int getTeamNumber() {
        return teamNumber;
    }
}
