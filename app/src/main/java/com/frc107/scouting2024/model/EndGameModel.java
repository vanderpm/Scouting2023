package com.frc107.scouting2024.model;

import com.frc107.scouting2024.R;
import com.frc107.scouting2024.Scouting;
import com.frc107.scouting2024.model.question.Question;
import com.frc107.scouting2024.model.question.RadioQuestion;
import com.frc107.scouting2024.model.question.TextQuestion;
import com.frc107.scouting2024.model.question.ToggleQuestion;

import java.util.ArrayList;

public class EndGameModel extends ScoutModel {
    private static final String FILE_NAME_HEADER = "Match";

    @Override
    public Question[] getQuestions() {
        Question[] questions = {

                new RadioQuestion("endgameHabitatLevel", R.id.endGameRobotSpeedRadioQuestion, true,
                        new RadioQuestion.Option(R.id.slowRobotEndGame_Radiobtn, Scouting.ENDGAME_SLOW_SPEED),
                        new RadioQuestion.Option(R.id.mediumRobotSpeedEndGame_Radiobtn, Scouting.ENDGAME_MEDIUM_SPEED),
                        new RadioQuestion.Option(R.id.fastRobotSpeedEndGame_Radiobtn, Scouting.ENDGAME_FAST_SPEED),
                        new RadioQuestion.Option(R.id.deadRobotSpeedEndGame_Radiobtn, Scouting.ENDGAME_DEAD_SPEED)),
                new RadioQuestion("endgameRobotPlacement", R.id.endGameRobotPlacementRadioQuestion, true,
                        new RadioQuestion.Option(R.id.parkedRobotEndGame_Radiobtn, Scouting.ENDGAME_PARKED_ROBOT),
                        new RadioQuestion.Option(R.id.AttemptedOnStageEndGame_Radiobtn, Scouting.ENDGAME_ATTEMPT_ONSTAGE),
                        new RadioQuestion.Option(R.id.successOnstageEndGame_Radiobtn, Scouting.ENDGAME_SUCCESS_ONSTAGE),
                        new RadioQuestion.Option(R.id.harmonizedEndGame_Radiobtn, Scouting.ENDGAME_HARMONIZED),
                        new RadioQuestion.Option(R.id.noEndGame_Radiobtn, Scouting.ENDGAME_NONE)),
                new ToggleQuestion("CoopertitionBonus", R.id.endGameCoopertitionBonus_chkbx),
                new ToggleQuestion("spotlightBonus", R.id.endGameSpotlight_chkbx),
                new ToggleQuestion("endgameDefenseAllMatch", R.id.endGameDefenseAllMatch_chkbx),
                new RadioQuestion("endgameDefense", R.id.endGameDefenseRadioQuestion, true,
                        new RadioQuestion.Option(R.id.endGameDefenseEffective_Radiobtn, Scouting.ENDGAME_DEFENSE_EFFECTIVE),
                        new RadioQuestion.Option(R.id.endGameDefenseIneffective_Radiobtn, Scouting.ENDGAME_DEFENSE_INEFFECTIVE),
                        new RadioQuestion.Option(R.id.endGameDefenseNone_Radiobtn, Scouting.ENDGAME_DEFENSE_NONE)),
                new ToggleQuestion("endgameFouls", R.id.endGameFouls_chkbx),
                new TextQuestion("Comments", R.id.matchComments_editText, true)

        };
        return questions;
    }

    @Override
    public void onNumberQuestionAnswered(int questionId, Integer answer) { }

    @Override
    public void onTextQuestionAnswered(int questionId, String answer) { }

    @Override
    public void onRadioQuestionAnswered(int questionId, int answerId) { }

    public String finish() {
        StringBuilder builder = new StringBuilder();

        String sandstormData = Scouting.getInstance().getAutonData();
        ArrayList<String> cycles = Scouting.getInstance().getCycles();
        if (cycles.size() == 0) {
            String cycle = "-1,-1,-1,-1,-1";
            String maxCycles = cycles.size() + "";
            if (Scouting.SAVE_QUESTION_NAMES_AS_ANSWERS)
                maxCycles = "maxCycles";

            String row = sandstormData + "," + cycle + "," + getAnswerCSVRow() + "," + maxCycles + "," + Scouting.getInstance().getInitials();
            builder.append(row);
            builder.append("\n");
        }

        for (int i = 0; i < cycles.size(); i++) {
            String cycle = cycles.get(i);
            String maxCycles = cycles.size() + "";
            if (Scouting.SAVE_QUESTION_NAMES_AS_ANSWERS)
                maxCycles = "maxCycles";

            String row = sandstormData + "," + cycle + "," + getAnswerCSVRow() + "," + maxCycles + "," + Scouting.getInstance().getInitials();
            builder.append(row);
            builder.append("\n");
        }

        Scouting.getInstance().clearCycles();
        int matchNum = Scouting.getInstance().getMatchNumber();
        Scouting.getInstance().setMatchNumber(matchNum + 1);

        return Scouting.FILE_UTILS.writeData(FILE_NAME_HEADER, builder.toString());
    }
}
