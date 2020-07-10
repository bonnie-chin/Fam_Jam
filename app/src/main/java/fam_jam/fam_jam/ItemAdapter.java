//package fam_jam.fam_jam;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.location.Location;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.text.Format;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import fam_jam.fam_jam.model.Mission;
//
//// Generates the cards based on data from the Firebase (any requests within the last 30 minutes)
//
//public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
//    private static final String TAG = ItemAdapter.class.getSimpleName();
//    private List<Mission> missionList;
//    private Location location;
//    public MainActivity main;
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        // views in card
//        public TextView topTv, messageTv, productTv, timeTv;
//        public ImageView iconImg;
//        public android.widget.Button acceptButton;
//
//        public TextView textView;
//        public MyViewHolder(View v) {
//            super(v);
//            topTv = v.findViewById(R.id.card_distance);
//            messageTv = v.findViewById(R.id.card_message);
//            productTv = v.findViewById(R.id.card_product);
//            iconImg = v.findViewById(R.id.product_icon);
//            timeTv = v.findViewById(R.id.card_time);
//            acceptButton = v.findViewById(R.id.accept_button);
//        }
//    }
//
//    // constructor
//    public ItemAdapter(List<Mission> requests, Location l, MainActivity m) {
//        missionList = requests;
//        location = l;
//        main = m;
//    }
//
//    // create new views (invoked by the layout manager)
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent,
//                                                       int viewType) {
//        // create a new card view
//        View card = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.mission_card, parent, false);
//        MyViewHolder vh = new MyViewHolder(card);
//        return vh;
//    }
//
//    // replaces the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        final Mission r = missionList.get(position);
//
//        long time = (long) r.getMeetTime();
//        String message = "";
//        Format format;
//        Date recordedTime = new Date(time);
//        if (recordedTime.before(new Date())){
//            message = "ASAP";
//        } else {
//            format = new SimpleDateFormat("h:mm a");
//            message = format.format(recordedTime);
//        }
//
//        holder.productTv.setText(Mission.products[r.getProduct()] + "   |   " + message);
//        holder.iconImg.setImageResource(Mission.productIcons[r.getProduct()]);
//
//            String u = "Anonymous";
//            int secAgo = Mission.secAgo((long)r.getTimestamp());
//            String timeSent = "Posted by "  + u + " ";
//            if (secAgo < 60){
//                timeSent +=  secAgo + " sec ago";
//            } else {
//                timeSent += (secAgo / 60) + " min ago";
//            }
//            holder.timeTv.setText(timeSent);
//
//        String distance = "5 m away";
//        holder.topTv.setText(distance);
//
//        if (r.getMessage() != null){
//            holder.messageTv.setText(r.getMessage());
//        } else {
//            holder.messageTv.setVisibility(View.GONE);
//        }
//
//        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(main)
//                        .setTitle("Mark complete")
//                        .setMessage("Did you complete the mission?")
//                        .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // TODO - change status here
//                            }
//                        })
//                        .setNegativeButton("Cancel", null)
//                        .show();
//            }
//        });
//    }
//
//
//    // returns size of list
//    @Override
//    public int getItemCount() {
//        return missionList.size();
//    }
//
//
//
//}