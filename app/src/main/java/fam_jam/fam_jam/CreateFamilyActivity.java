package fam_jam.fam_jam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fam_jam.fam_jam.model.Family;
import fam_jam.fam_jam.model.Member;

import static fam_jam.fam_jam.LoginActivity.user;

public class CreateFamilyActivity extends AppCompatActivity {

    private EditText nicknameET, familyET, passwordET;
    private Button done;

    // firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_family);

        nicknameET = findViewById(R.id.et_nickname);
        familyET = findViewById(R.id.et_family);
        passwordET = findViewById(R.id.et_password);
        done = findViewById(R.id.button_done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO - add validation for checking unique codes and nickname validation

                String nickname = nicknameET.getText().toString();
                if (TextUtils.isEmpty(nickname)) {
                    nickname = user.getDisplayName();
                }
                String family = familyET.getText().toString();
                String password = passwordET.getText().toString();

                // gets the generated id from firebase
                DatabaseReference famRef = fireRef.child("families");
                final String fId = famRef.push().getKey();

                // creates new family object
                Family f = new Family(fId, family, password, user.getUid());
                famRef.child(fId).setValue(f)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // successfully saved
                                Toast.makeText(getApplicationContext(),"Your family has been created!",Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // failed to save
                                Toast.makeText(getApplicationContext(),"Uh-oh! something went wrong, please try again.",Toast.LENGTH_SHORT).show();
                            }
                        });
                famRef.child(fId).child("members").push().setValue(user.getUid());

                String photo = user.getPhotoUrl().toString();
                Member u = new Member(user.getUid(), nickname, fId, photo);
                // adds to firebase
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("members");

                userRef.child(user.getUid()).setValue(u)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // successfully saved
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // failed to save
                            }
                        });

                Intent i = new Intent(CreateFamilyActivity.this, MainActivity.class);
                CreateFamilyActivity.this.startActivity(i);
                CreateFamilyActivity.this.finish();
                finish();
            }
        });
    }
}