package com.frc107.scouting2019.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.frc107.scouting2019.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.frc107.scouting2019.utils.StringUtils;

public class ScouterInitialsActivity extends AppCompatActivity implements View.OnKeyListener {

    @BindView(R.id.scouterInitials_input_layout)
    public TextInputLayout scouterInitialsInputLayout;
    private static String initials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scouter_initials);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_activity:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.send_data:
                startActivity(new Intent(this, SendDataActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_SPACE && keyCode != KeyEvent.KEYCODE_TAB) {
            TextInputEditText inputEditText = (TextInputEditText) v;

            if (inputEditText != null) {

                switch (inputEditText.getId()) {
                    case R.id.scouterInitials_input_layout:
                        scouterInitialsInputLayout.setError(null);
                        break;
                }
            }
        }
        return false;
    }

    public static String getInitials() {
        return initials;
    }

    public void submitInitials(View view) {
        initials = getTextInputLayoutString(scouterInitialsInputLayout);

        if(!StringUtils.isEmptyOrNull(initials))
            startActivity(new Intent(this, AutonActivity.class));
        else
            scouterInitialsInputLayout.setError(getText(R.string.scouterInitialsError));
    }

    private String getTextInputLayoutString(@NonNull TextInputLayout textInputLayout) {
        final EditText editText = textInputLayout.getEditText();
        return editText != null && editText.getText() != null ? editText.getText().toString() : "";
    }
}