package fam_jam.fam_jam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fam_jam.fam_jam.model.Member;

import static fam_jam.fam_jam.LoginActivity.famId;
import static fam_jam.fam_jam.MainActivity.fireRef;

public class LeaderboardFragment extends Fragment {

    LayoutInflater layoutInflater;
    LinearLayout leaders;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_leaderboard, null);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        leaders = getView().findViewById(R.id.leaderboard);

        class CircleTransform implements Transformation {
            @Override
            public Bitmap transform(Bitmap source) {
                int size = Math.min(source.getWidth(), source.getHeight());

                int x = (source.getWidth() - size) / 2;
                int y = (source.getHeight() - size) / 2;

                Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
                if (squaredBitmap != source) {
                    source.recycle();
                }

                Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

                Canvas canvas = new Canvas(bitmap);
                Paint paint = new Paint();
                BitmapShader shader = new BitmapShader(squaredBitmap,
                        Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                paint.setShader(shader);
                paint.setAntiAlias(true);

                float r = size / 2f;
                canvas.drawCircle(r, r, r, paint);

                squaredBitmap.recycle();
                return bitmap;
            }

            @Override
            public String key() {
                return "circle";
            }
        }

        fireRef.child("families").child(famId).child("members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                leaders.removeAllViews();
                int i =0;
                for (DataSnapshot s : dataSnapshot.getChildren()){

                    String m = (String) s.getValue();
                    fireRef.child("members").child(m).addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             Member m = snapshot.getValue(Member.class);
                             // creates view for each member
                             layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                             View myview = layoutInflater.inflate(R.layout.leaderboardcard, null, false);
                             TextView name = myview.findViewById(R.id.name_tv);
                             TextView pts = myview.findViewById(R.id.score_tv);
                             ImageView iconImg = myview.findViewById(R.id.leader_icon);
                             if (iconImg!=null){
                                 Picasso.get().load(m.getImgUrl()).transform(new CircleTransform()).into(iconImg);
                             }
                             name.setText(m.getName());
                             String ranking = m.getPoints() + " pts";
                             pts.setText(ranking);
                             leaders.addView(myview);
                         }
                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     }
                    );
                    i++;
                    if (i>=4){
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}