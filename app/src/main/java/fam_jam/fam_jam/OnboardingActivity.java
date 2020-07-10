package fam_jam.fam_jam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OnboardingActivity extends AppCompatActivity {

    private EditText nicknameET, familyET, passwordET;
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        nicknameET = findViewById(R.id.et_nickname);
        familyET = findViewById(R.id.et_family);
        passwordET = findViewById(R.id.et_family);
        done = findViewById(R.id.button_done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = nicknameET.getText().toString();
                String family = familyET.getText().toString();
                String password = passwordET.getText().toString();
            }
        });
    }
}