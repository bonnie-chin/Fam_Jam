package fam_jam.fam_jam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.Cache;

public class ProfileFragment extends Fragment {

    private Button logoutButton;
    public ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, null);

        // logs out the user
        logoutButton = v.findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.signOut(getActivity());
            }
        });



        /*URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        urlTest.setImageBitmap(bmp); */

        /*InputStream is = null;
        try {
            is = (InputStream) new URL("https://external-preview.redd.it/KgNuAcsy0x59BmJu_dnEKaNPrP309fLkKYWxIOa0wBo.png?auto=webp&s=886ccb8afdf7abb69118293234ebcd995c3808e2").getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable d = Drawable.createFromStream(is, "ihatelife");



        urlTest.setBackground(d);*/
        imageView = v.findViewById(R.id.urltest);
        String potato = "https://external-preview.redd.it/KgNuAcsy0x59BmJu_dnEKaNPrP309fLkKYWxIOa0wBo.png?auto=webp&s=886ccb8afdf7abb69118293234ebcd995c3808e2";
        Picasso.get().load(potato).into(imageView);
        return v;


    }

}

