package fam_jam.fam_jam;

import fam_jam.fam_jam.model.*;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static FirebaseUser user;
    public String mUsername;
    private static int RC_SIGN_IN = 1;
    private static String ANONYMOUS = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        // Checks if user is signed in
        if (user != null){
            onSignedInInitialize(user);
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setTheme(R.style.FirebaseLogin)
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                    new AuthUI.IdpConfig.EmailBuilder().build()))
                            .setTosAndPrivacyPolicyUrls(
                                    "https://firebase.google.com/docs/android/setup#available-libraries",
                                    "https://firebase.google.com/docs/android/setup#available-libraries")
                            .build(),
                    RC_SIGN_IN);
        }

    }

    // Checks if user is properly signed in
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                user = mAuth.getCurrentUser();
                onSignedInInitialize(user);
            } else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Please sign in again", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    // Signs in the user
    private void onSignedInInitialize(FirebaseUser user){
        FirebaseUserMetadata metadata = user.getMetadata();
        if (Math.abs(metadata.getCreationTimestamp() - metadata.getLastSignInTimestamp()) < 100) {
            // creates new user
            Intent i = new Intent(this, OnboardingActivity.class);
            this.startActivity(i);
            this.finish();

            Toast.makeText(getApplicationContext(), "Signed in as " + user.getDisplayName(), Toast.LENGTH_SHORT).show();

            Member u = new Member(user.getUid(), user.getDisplayName(), user.getEmail());
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
        } else {
            // Existing user
            Intent i = new Intent(this, MainActivity.class);
            this.startActivity(i);
            this.finish();
            Toast.makeText(getApplicationContext(), "Signed in as " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void signOut(final Activity c){
        AuthUI.getInstance()
                .signOut(c)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(c, LoginActivity.class);
                        c.startActivity(i);
                        c.finish();
                    }
                });
        user = null;
    }
}