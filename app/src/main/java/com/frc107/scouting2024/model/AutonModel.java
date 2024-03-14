package com.frc107.scouting2024.model;

import com.frc107.scouting2024.R;
import com.frc107.scouting2024.Scouting;
import com.frc107.scouting2024.model.question.NumberQuestion;
import com.frc107.scouting2024.model.question.Question;
import com.frc107.scouting2024.model.question.RadioQuestion;
import com.frc107.scouting2024.model.question.ToggleQuestion;

public class AutonModel extends ScoutModel {
    private boolean startedWithGamePiece;
    private boolean placedStartingGamePiece;

    public AutonModel() {
        super();
    }

    @Override
    public Question[] getQuestions() {
        NumberQuestion matchNumQuestion = new NumberQuestion("autonMatchNumber", R.id.matchNumberEditText, true);
        NumberQuestion teamNumQuestion = new NumberQuestion("autonTeamNumber", R.id.teamNumberEditText, true);
        NumberQuestion autonNoteQuestion = new NumberQuestion("autonNoteNumber", R.id.autonNoteEditText, true);
        matchNumQuestion.addIllegalValue(0);
        teamNumQuestion.addIllegalValue(0);

        Question[] questions = {
                matchNumQuestion,
                teamNumQuestion,
                new RadioQuestion("autonStartPos", R.id.autonStartingPositionRadioQuestion, true,
                        new RadioQuestion.Option(R.id.leftStartPositionAuton_Radiobtn, Scouting.AUTON_LEFT_START),
                        new RadioQuestion.Option(R.id.middleStartPostionAuton_Radiobtn, Scouting.AUTON_MIDDLE_START),
                        new RadioQuestion.Option(R.id.rightStartPostionAuton_Radiobtn, Scouting.AUTON_RIGHT_START)),
                new RadioQuestion("autonStartPiece", R.id.autonStartingGamePieceRadioQuestion, true,
                        new RadioQuestion.Option(R.id.autonStartingGamePiece_Radiobtn, Scouting.AUTON_STARTING_GAME_PIECE),
                        new RadioQuestion.Option(R.id.autonNotStartingGamePiece_Radiobtn, Scouting.AUTON_NOT_STARTING_GAME_PIECE)),
                new RadioQuestion("autonItemPlaced", R.id.autonItemPlacedRadioQuestion, true,
                        new RadioQuestion.Option(R.id.autonSpeakerItemPlaced_Radiobtn, Scouting.AUTON_SPEAKER_PLACED),
                        new RadioQuestion.Option(R.id.autonAmpItemPlaced_Radiobtn, Scouting.AUTON_AMP_PLACED),
                        new RadioQuestion.Option(R.id.autonFloorItemPlaced_Radiobtn, Scouting.AUTON_FLOOR_PLACED),
                        new RadioQuestion.Option(R.id.autonNothingPlacedItemPlaced_Radiobtn, Scouting.AUTON_NOTHING_PLACED)),
                new ToggleQuestion("autonBaseline", R.id.autonBaseline_chkbx),
                autonNoteQuestion
        };
        return questions;
    }

    @Override
    public void onNumberQuestionAnswered(int questionId, Integer answer) {
        if (questionId == R.id.matchNumberEditText) {
            int matchNum = answer == null ? -1 : answer;
            Scouting.getInstance().setMatchNumber(matchNum);
        }
    }

    @Override
    public void onTextQuestionAnswered(int questionId, String answer) { }

    @Override
    public void onRadioQuestionAnswered(int questionId, int answerId) {
        switch (questionId) {
//            case R.id.autonStartingGamePieceRadioQuestion:
//                startedWithGamePiece = answerId != R.id.noSandstormStartingGamePiece_Radiobtn;
//                break;
            case R.id.autonItemPlacedRadioQuestion:
                placedStartingGamePiece = answerId != R.id.autonNothingPlacedItemPlaced_Radiobtn;
                break;
        }
    }

    public int getTeamNumber() {
        Integer teamNumber = (Integer) getRawAnswerForQuestion(R.id.teamNumberEditText);
        if (teamNumber == null)
            return -1;

        return teamNumber;
    }

    public void finish() {
        String sandstormData = getAnswerCSVRow();
        Scouting.getInstance().setAutonData(sandstormData);
    }

    public boolean shouldAllowStartingPiece() {
        return startedWithGamePiece && !placedStartingGamePiece;
    }
}
