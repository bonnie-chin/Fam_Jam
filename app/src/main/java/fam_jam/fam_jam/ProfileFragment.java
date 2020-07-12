package fam_jam.fam_jam;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static fam_jam.fam_jam.MainActivity.member;

public class ProfileFragment extends Fragment {

    private Button logoutButton;
    private ImageView pfp;
    private TextView nameTv, ptsTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, null);

        // sets views with member info
        nameTv = v.findViewById(R.id.profile_name);
        nameTv.setText(member.getName());
        ptsTv = v.findViewById(R.id.profile_pts);
        String pts = member.getPoints() + " pts";
        ptsTv.setText(pts);
        pfp = v.findViewById(R.id.profile_pfp);
        String url = member.getImgUrl();
        if (url!=null){
//            Picasso.get().load(member.getImgUrl()).transform(new CropCircleTransformation()).into(pfp);
            Picasso.get().load(member.getImgUrl()).into(pfp);
        }

        // logs out the user
        logoutButton = v.findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.signOut(getActivity());
            }
        });

        return v;
    }


}

