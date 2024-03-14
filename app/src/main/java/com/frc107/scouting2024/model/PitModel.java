package com.frc107.scouting2024.model;

import android.os.Environment;
import android.util.Log;

import com.frc107.scouting2024.R;
import com.frc107.scouting2024.Scouting;
import com.frc107.scouting2024.model.question.Question;
import com.frc107.scouting2024.model.question.RadioQuestion;
import com.frc107.scouting2024.model.question.TextQuestion;

import java.io.File;
import java.io.IOException;

public class PitModel extends ScoutModel {
    public PitModel() {
        super();
        setFileNameHeader("Pit");
    }

    private String getTeamNumber() {
        return getAnswerForQuestion(R.id.pit_teamNumber_editText);
    }

    public File createPhotoFile() {
        File dir = new File(Environment.getExternalStorageDirectory() + "/Scouting/Photos");
        dir.mkdirs();

        File file = new File(dir, getTeamNumber() + ".jpg");

        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.d("Scouting", e.getMessage());
            return null;
        }

        return file;
    }

    public boolean rotateAndCompressPhoto() {
        return Scouting.FILE_UTILS.rotateAndCompressPhoto(getTeamNumber());
    }

    @Override
    public Question[] getQuestions() {
        Question[] questions = {
                new TextQuestion("pitTeamNum", R.id.pit_teamNumber_editText, true),
                new RadioQuestion("pitAutonOp", R.id.autonOperationsRadioQuestion, true,
                        new RadioQuestion.Option(R.id.visionSystemAuton_Radiobtn, 0),
                        new RadioQuestion.Option(R.id.cameraDrivingAuton_Radiobtn, 1),
                        new RadioQuestion.Option(R.id.blindDrivingAuton_Radiobtn, 2),
                        new RadioQuestion.Option(R.id.noDrivingAuton_Radiobtn, 3)),
                new RadioQuestion("pitAutonPref", R.id.AutonPreferenceRadioQuestion, true,
                        new RadioQuestion.Option(R.id.cargoshipPreferenceAuton_Radiobtn, 0),
                        new RadioQuestion.Option(R.id.rocketshipPreferenceAuton_Radiobtn, 1),
                        new RadioQuestion.Option(R.id.noPreferenceAuton_Radiobtn, 2)),
                new RadioQuestion("pitHighestRocketLevel", R.id.highestRocketLevelAutonRadioQuestion, true,
                        new RadioQuestion.Option(R.id.topRocketLevelAuton_Radiobtn, 0),
                        new RadioQuestion.Option(R.id.middleRocketLevelAuton_Radiobtn, 1),
                        new RadioQuestion.Option(R.id.bottomRocketLevelAuton_Radiobtn, 2),
                        new RadioQuestion.Option(R.id.noRocketLevelAuton_Radiobtn, 3)),
                new RadioQuestion("pitHighestHabLevel", R.id.highestHabitatLevelRadioQuestion, true,
                        new RadioQuestion.Option(R.id.topHabitatLevel_Radiobtn, 0),
                        new RadioQuestion.Option(R.id.middleHabitatLevel_Radiobtn, 1),
                        new RadioQuestion.Option(R.id.bottomHabitatLevel_Radiobtn, 2),
                        new RadioQuestion.Option(R.id.noHabitatLevel_Radiobtn, 3)),
                new TextQuestion("pitHabTime", R.id.pit_habitatTime_editText, true),
                new RadioQuestion("pitLanguage", R.id.programmingLanguageRadioQuestion, true,
                        new RadioQuestion.Option(R.id.javaProgrammingLanguage_Radiobtn, 0),
                        new RadioQuestion.Option(R.id.cppProgrammingLanguage_Radiobtn, 1),
                        new RadioQuestion.Option(R.id.labviewProgrammingLanguage_Radiobtn, 2),
                        new RadioQuestion.Option(R.id.otherProgrammingLanguage_Radiobtn, 3)),

                new TextQuestion("pitBonus", R.id.pit_bonusQuestion_editText, true),
                new TextQuestion("pitComments", R.id.pit_comments_editText, true)
        };
        return questions;
    }

    @Override
    public void onNumberQuestionAnswered(int questionId, Integer answer) { }

    @Override
    public void onTextQuestionAnswered(int questionId, String answer) { }

    @Override
    public void onRadioQuestionAnswered(int questionId, int answerId) { }
}
