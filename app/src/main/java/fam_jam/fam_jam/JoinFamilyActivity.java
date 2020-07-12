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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import fam_jam.fam_jam.model.Family;
import fam_jam.fam_jam.model.Member;

import static fam_jam.fam_jam.LoginActivity.user;

public class JoinFamilyActivity extends AppCompatActivity {

    private EditText nicknameET, familyET, passwordET;
    private Button done;

    // firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();
    static DatabaseReference famRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_family);

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
                final String finalNickname = nickname;

                final String family = familyET.getText().toString();
                final String password = passwordET.getText().toString();

                // gets the generated id from firebase
                Query q = fireRef.child("families").orderByChild("code").equalTo(family);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        DataSnapshot child = dataSnapshot.getChildren().iterator().next();
                        if (child.exists()){
                        Family f = child.getValue(Family.class);
                        famRef = fireRef.child("families").child(f.getfId());

                        if (password.equals(f.getPassword())){
                            Member u = new Member(user.getUid(), finalNickname, f.getfId(), user.getPhotoUrl().toString());
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("members");
                            userRef.child(user.getUid()).setValue(u)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            // adds member to family
                                            famRef.child("members").push().setValue(user.getUid())
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            // successfully saved
                                                            Intent i = new Intent(JoinFamilyActivity.this, MainActivity.class);
                                                            JoinFamilyActivity.this.startActivity(i);
                                                            JoinFamilyActivity.this.finish();
                                                            Toast.makeText(getApplicationContext(),"Welcome to the family",Toast.LENGTH_SHORT).show();
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
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // failed to save
                                        }
                                    });
                        }
                        else {
                                // if passwords don't match
                                Toast.makeText(getApplicationContext(), "Incorrect password, please try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            // if child does not exist
                            Toast.makeText(getApplicationContext(), "Code not found, please try again!", Toast.LENGTH_SHORT);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } // end onclick
        }); // end onclick listener
    } // end oncreate

}