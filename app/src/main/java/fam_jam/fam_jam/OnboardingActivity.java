package fam_jam.fam_jam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OnboardingActivity extends AppCompatActivity {

    private Button joinButton, createButton, missionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        joinButton = findViewById(R.id.button_join);
        createButton = findViewById(R.id.button_create);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnboardingActivity.this, JoinFamilyActivity.class);
                startActivity(i);
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnboardingActivity.this, CreateFamilyActivity.class);
                startActivity(i);
            }
        });
        missionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnboardingActivity.this, MissionsFragment.class);
                startActivity(i);
            }
        });
    }


}