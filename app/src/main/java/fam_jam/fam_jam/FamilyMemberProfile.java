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

import com.squareup.picasso.Picasso;

import static fam_jam.fam_jam.MainActivity.member;

public class FamilyMemberProfile extends AppCompatActivity {

    private Button logoutButton;
    private ImageView pfp;
    private TextView nameTv, ptsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familymemberprofile);

        // sets views with member info
        nameTv = findViewById(R.id.member_name);
        nameTv.setText(member.getName());
//        ptsTv = findViewById(R.id.profile_pts);
//        String pts = member.getPoints() + " pts";
//        ptsTv.setText(pts);
        pfp = findViewById(R.id.member_pfp);
        String url = member.getImgUrl();
        if (url!=null){
//            Picasso.get().load(member.getImgUrl()).transform(new CropCircleTransformation()).into(pfp);
            Picasso.get().load(member.getImgUrl()).into(pfp);
        }

    }
}
