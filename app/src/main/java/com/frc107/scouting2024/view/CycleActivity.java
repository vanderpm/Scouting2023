package com.frc107.scouting2024.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.frc107.scouting2024.R;
import com.frc107.scouting2024.ScoutingStrings;
import com.frc107.scouting2024.utils.PermissionUtils;
import com.frc107.scouting2024.utils.ViewUtils;
import com.frc107.scouting2024.view.wrappers.RadioWrapper;
import com.frc107.scouting2024.viewmodel.CycleViewModel;

import androidx.appcompat.app.AppCompatActivity;

public class CycleActivity extends AppCompatActivity {
    private RadioWrapper pickupLocationWrapper;

    private RadioWrapper itemPlacedWrapper;

    private RadioGroup pickupLocationRadioGroup;

    private RadioGroup itemPlacedRadioGroup;

    private CheckBox defenseCheckbox;
    private CheckBox allDefenseCheckbox;

    private CycleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle);

        int teamNumber = getIntent().getIntExtra(ScoutingStrings.EXTRA_TEAM_NUM, -1);
        getSupportActionBar().setTitle("Team: " + teamNumber);

        viewModel = new CycleViewModel(teamNumber);

        pickupLocationWrapper = new RadioWrapper(findViewById(R.id.pickupLocationRadioQuestion), viewModel);
        itemPlacedWrapper = new RadioWrapper(findViewById(R.id.itemPlacedRadioQuestion), viewModel);

        pickupLocationRadioGroup = findViewById(R.id.pickupLocationRadioQuestion);
        itemPlacedRadioGroup = findViewById(R.id.itemPlacedRadioQuestion);

        defenseCheckbox = findViewById(R.id.defense_chkbx);
        allDefenseCheckbox = findViewById(R.id.allDefense_chkbx);

        boolean shouldAllowStartingPiece = getIntent().getBooleanExtra(ScoutingStrings.EXTRA_SHOULD_ALLOW_STARTING_PIECE_AUTON, true);
        if (shouldAllowStartingPiece) {
            enableStartedWithItem();
        } else {
            disableStartedWithItem();
        }

        pickupLocationRadioGroup.setOnCheckedChangeListener((group, checkedId) -> viewModel.setAnswer(R.id.pickupLocationRadioQuestion, checkedId));
        itemPlacedRadioGroup.setOnCheckedChangeListener((group, checkedId) -> viewModel.setAnswer(R.id.itemPlacedRadioQuestion, checkedId));
        defenseCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.setAnswer(R.id.defense_chkbx, isChecked));
        allDefenseCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setAnswer(R.id.allDefense_chkbx, isChecked);
            setAllDefense(isChecked);
        });
    }

    private void setAllDefense(boolean allDefense) {
        boolean questionsEnabled = !allDefense;
        ViewUtils.setRadioGroupEnabled(pickupLocationRadioGroup, questionsEnabled);
        ViewUtils.setRadioGroupEnabled(itemPlacedRadioGroup, questionsEnabled);

        defenseCheckbox.setEnabled(questionsEnabled);
        defenseCheckbox.setChecked(allDefense);

        if (allDefense) {
            pickupLocationRadioGroup.clearCheck();
            itemPlacedRadioGroup.clearCheck();
        }

        viewModel.setAllDefense(allDefense);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        pickupLocationWrapper.cleanUp();
        itemPlacedWrapper.cleanUp();
        defenseCheckbox.setOnCheckedChangeListener(null);

        viewModel = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cycle_teleop_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_activity:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.send_data:
                startActivity(new Intent(this, AdminActivity.class));
                return true;
            case R.id.go_to_endgame:
                goToEndGame();
                return true;
            case R.id.clear_cycle:
                clearAnswers();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToEndGame() {
        boolean cycleCanBeFinished = viewModel.cycleCanBeFinished();
        int unfinishedQuestionId = viewModel.getFirstUnfinishedQuestionId();
        if (!cycleCanBeFinished && unfinishedQuestionId != -1) {
            ViewUtils.requestFocusToUnfinishedQuestion(findViewById(unfinishedQuestionId), this);
            return;
        }

        if (!PermissionUtils.verifyWritePermissions(this))
            return;

        if (cycleCanBeFinished && unfinishedQuestionId == -1)
            viewModel.finishCycle();

        int teamNumber = viewModel.getTeamNumber();

        final Intent intent = new Intent(this, EndGameActivity.class);
        intent.putExtra(ScoutingStrings.EXTRA_TEAM_NUM, teamNumber);
        startActivity(intent);

        finish();
    }

    public void goToNextCycle(View view) {
        boolean cycleCanBeFinished = viewModel.cycleCanBeFinished();
        int unfinishedQuestionId = viewModel.getFirstUnfinishedQuestionId();
        /**
         * TODO: I think you don't need to check unfinishedQuestionId. Just check cycleCanBeFinished
         * and then use unfinishedQuestionId inside the if block
         */
        if (!cycleCanBeFinished && unfinishedQuestionId != -1) {
            ViewUtils.requestFocusToUnfinishedQuestion(findViewById(unfinishedQuestionId), this);
            return;
        }

        if (!PermissionUtils.verifyWritePermissions(this))
            return;

        if (unfinishedQuestionId == -1)
            viewModel.finishCycle();

        if (viewModel.hasUsedStartingItem()) {
            disableStartedWithItem();
        }

        clearAnswers();
    }

    private void enableStartedWithItem() {
        RadioButton portRadioButton = findViewById(R.id.sourcePickupLocation_Radiobtn);
        portRadioButton.setEnabled(false);
        portRadioButton.setVisibility(View.GONE);

        RadioButton floorRadioButton = findViewById(R.id.floorPickupLocation_Radiobtn);
        floorRadioButton.setEnabled(false);
        floorRadioButton.setVisibility(View.GONE);
    }

    private void disableStartedWithItem() {
        RadioButton portRadioButton = findViewById(R.id.sourcePickupLocation_Radiobtn);
        portRadioButton.setEnabled(true);
        portRadioButton.setVisibility(View.VISIBLE);

        RadioButton floorRadioButton = findViewById(R.id.floorPickupLocation_Radiobtn);
        floorRadioButton.setEnabled(true);
        floorRadioButton.setVisibility(View.VISIBLE);

        RadioButton startedWithItemButton = findViewById(R.id.startedWithItem_Radiobtn);
        startedWithItemButton.setVisibility(View.GONE);
        startedWithItemButton.setEnabled(false);

        viewModel.disableStartingItem();
    }

    private void clearAnswers() {
        pickupLocationWrapper.clear();
        itemPlacedWrapper.clear();
        defenseCheckbox.setChecked(false);
        allDefenseCheckbox.setChecked(false);
    }
}
