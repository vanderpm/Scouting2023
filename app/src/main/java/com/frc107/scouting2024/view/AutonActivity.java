package com.frc107.scouting2024.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.frc107.scouting2024.R;
import com.frc107.scouting2024.Scouting;
import com.frc107.scouting2024.ScoutingStrings;
import com.frc107.scouting2024.utils.ViewUtils;
import com.frc107.scouting2024.view.wrappers.RadioWrapper;
import com.frc107.scouting2024.view.wrappers.TextWrapper;
import com.frc107.scouting2024.viewmodel.AutonViewModel;

public class AutonActivity extends BaseActivity {
    private RadioWrapper startingPosWrapper;
    private RadioWrapper startingPieceWrapper;
    private RadioWrapper itemPlacedWrapper;
    private TextWrapper teamNumWrapper;
    private TextWrapper matchNumWrapper;

    private EditText matchNumberEditText;

    private CheckBox crossedBaselineCheckbox;

    private TextWrapper autonNotesWrapper;
    private EditText autonNotesEditText;

    private AutonViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auton);

        viewModel = new AutonViewModel();

        matchNumberEditText = findViewById(R.id.matchNumberEditText);

        startingPosWrapper = new RadioWrapper(findViewById(R.id.autonStartingPositionRadioQuestion), viewModel);
        startingPieceWrapper = new RadioWrapper(findViewById(R.id.autonStartingGamePieceRadioQuestion), viewModel);
        itemPlacedWrapper = new RadioWrapper(findViewById(R.id.autonItemPlacedRadioQuestion), viewModel);

        teamNumWrapper = new TextWrapper(findViewById(R.id.teamNumberEditText), viewModel);
        matchNumWrapper = new TextWrapper(matchNumberEditText, viewModel);
        autonNotesWrapper = new TextWrapper(findViewById(R.id.autonNoteEditText), viewModel);

        crossedBaselineCheckbox = findViewById(R.id.autonBaseline_chkbx);
        crossedBaselineCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setAnswer(R.id.autonBaseline_chkbx, isChecked));
    }

    @Override
    protected void onResume() {
        super.onResume();
        int matchNum = Scouting.getInstance().getMatchNumber();
        String matchNumToShow = matchNum == -1 ? "" : matchNum + "";
        matchNumberEditText.setText(matchNumToShow);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        startingPosWrapper.cleanUp();
        startingPieceWrapper.cleanUp();
        itemPlacedWrapper.cleanUp();
        teamNumWrapper.cleanUp();
        matchNumWrapper.cleanUp();
        autonNotesWrapper.cleanUp();;

        viewModel = null;
    }

    public void goToCycle(View view) {
        int unfinishedQuestionId = viewModel.getFirstUnfinishedQuestionId();
        if (unfinishedQuestionId != -1) {
            ViewUtils.requestFocusToUnfinishedQuestion(findViewById(unfinishedQuestionId), this);
            return;
        }
        viewModel.finish();

        int teamNumber = viewModel.getTeamNumber();
        boolean shouldAllowStartingPiece = viewModel.shouldAllowStartingPiece();

        clearAnswers();

        ViewUtils.requestFocus(teamNumWrapper.getEditText(), this);

        Intent intent = new Intent(this, CycleActivity.class);
        intent.putExtra(ScoutingStrings.EXTRA_TEAM_NUM, teamNumber);
        intent.putExtra(ScoutingStrings.EXTRA_SHOULD_ALLOW_STARTING_PIECE_AUTON, shouldAllowStartingPiece);
        startActivity(intent);

    }

    private void clearAnswers() {
        teamNumWrapper.clear();
        startingPosWrapper.clear();
        startingPieceWrapper.clear();
        itemPlacedWrapper.clear();
        autonNotesWrapper.clear();
        crossedBaselineCheckbox.setChecked(false);
    }
}
