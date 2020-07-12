package fam_jam.fam_jam;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import fam_jam.fam_jam.model.Member;

import static fam_jam.fam_jam.LoginActivity.famId;
import static fam_jam.fam_jam.MainActivity.fireRef;
import static fam_jam.fam_jam.MainActivity.member;

public class FamilyMemberProfile extends AppCompatActivity {

    private Button logoutButton;
    private ImageView pfp;
    private TextView nameTv, ptsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familymemberprofile);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("UID");

        fireRef.child("members").child(id).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 Member m = dataSnapshot.getValue(Member.class);
                 // sets views with member info
                 nameTv = findViewById(R.id.member_name);
                 nameTv.setText(m.getName());
                 pfp = findViewById(R.id.member_pfp);
                 String url = m.getImgUrl();
                 if (url!=null){
                     Picasso.get().load(m.getImgUrl()).into(pfp);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });



    }
}
