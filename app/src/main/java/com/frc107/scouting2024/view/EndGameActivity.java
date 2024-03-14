package com.frc107.scouting2024.view;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.frc107.scouting2024.R;
import com.frc107.scouting2024.ScoutingStrings;
import com.frc107.scouting2024.utils.PermissionUtils;
import com.frc107.scouting2024.utils.ViewUtils;
import com.frc107.scouting2024.view.wrappers.RadioWrapper;
import com.frc107.scouting2024.view.wrappers.TextWrapper;
import com.frc107.scouting2024.viewmodel.EndGameViewModel;

public class EndGameActivity extends BaseActivity {
    private RadioWrapper robotSpeedWrapper;
    private RadioWrapper robotPlacementWrapper;
    private RadioWrapper defenseWrapper;
    private CheckBox coopertitionCheckbox;
    private CheckBox spotlightCheckbox;
    private CheckBox defenseAllMatchCheckbox;
    private CheckBox foulsCheckbox;
    private TextWrapper commentsWrapper;

    private EndGameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);

        viewModel = new EndGameViewModel();

        int teamNumber = getIntent().getIntExtra(ScoutingStrings.EXTRA_TEAM_NUM, -1);
        getSupportActionBar().setTitle("Team: " + teamNumber);

        robotSpeedWrapper = new RadioWrapper(findViewById(R.id.endGameRobotSpeedRadioQuestion), viewModel);
        robotPlacementWrapper = new RadioWrapper(findViewById(R.id.endGameRobotPlacementRadioQuestion), viewModel);
        defenseWrapper = new RadioWrapper(findViewById(R.id.endGameDefenseRadioQuestion), viewModel);

        spotlightCheckbox = findViewById(R.id.endGameSpotlight_chkbx);
        spotlightCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setAnswer(R.id.endGameSpotlight_chkbx, isChecked));

        coopertitionCheckbox = findViewById(R.id.endGameCoopertitionBonus_chkbx);
        coopertitionCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setAnswer(R.id.endGameCoopertitionBonus_chkbx, isChecked));


        defenseAllMatchCheckbox = findViewById(R.id.endGameDefenseAllMatch_chkbx);
        defenseAllMatchCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setAnswer(R.id.endGameDefenseAllMatch_chkbx, isChecked));

        foulsCheckbox = findViewById(R.id.endGameFouls_chkbx);
        foulsCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setAnswer(R.id.endGameFouls_chkbx, isChecked));

        commentsWrapper = new TextWrapper(findViewById(R.id.matchComments_editText), viewModel);

        checkForPermissions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        robotSpeedWrapper.cleanUp();
        robotPlacementWrapper.cleanUp();
        defenseWrapper.cleanUp();
        defenseAllMatchCheckbox.setOnCheckedChangeListener(null);
        foulsCheckbox.setOnCheckedChangeListener(null);
        commentsWrapper.cleanUp();

        viewModel = null;
    }

    public void saveData(View view) {
        int unfinishedQuestionId = viewModel.getFirstUnfinishedQuestionId();
        if (unfinishedQuestionId != -1) {
            ViewUtils.requestFocusToUnfinishedQuestion(findViewById(unfinishedQuestionId), this);
            return;
        }

        boolean hasWritePermissions = PermissionUtils.getPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!hasWritePermissions) {
            Toast.makeText(getApplicationContext(), "No write permissions.", Toast.LENGTH_LONG).show();
            return;
        }

        String saveResponse = viewModel.finish();

        Toast.makeText(getApplicationContext(), saveResponse, Toast.LENGTH_LONG).show();

        finish();
    }
}
