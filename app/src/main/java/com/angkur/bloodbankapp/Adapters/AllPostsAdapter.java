package com.angkur.bloodbankapp.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.angkur.bloodbankapp.Models.RequestDataModel;
import com.angkur.bloodbankapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class AllPostsAdapter extends RecyclerView.Adapter<AllPostsAdapter.PostViewHolder> {

    private static final int REQUEST_CALL = 1;
    String patinetPhoneNumber;
    private List<RequestDataModel> postList = new ArrayList<>();
    private Context context;


    public AllPostsAdapter(Context context, List<RequestDataModel> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item_layout, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        RequestDataModel post = postList.get(position);

        holder.textViewNeedBloodGroup.setText("Need " + post.getPatientBloodGroup() + " Blood");
        holder.textViewPatientLocation.setText(post.getPatientCity());
        holder.textViewPatientName.setText("Patient Name: " + post.getPatientName());
        holder.textViewMessage.setText(post.getMessage());

        patinetPhoneNumber = post.getPatientPhone();
        //makePhoneCall();
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView posterImage;
        TextView textViewNeedBloodGroup, textViewPatientLocation, textViewPatientName, textViewMessage;
        AppCompatImageView make_call;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImage = (ImageView) itemView.findViewById(R.id.userimage);
            textViewNeedBloodGroup = (TextView)itemView.findViewById(R.id.tv_need_blood_group);
            textViewPatientLocation = (TextView)itemView.findViewById(R.id.tv_patient_location);
            textViewPatientName = (TextView)itemView.findViewById(R.id.tv_patient_name);
            textViewMessage = (TextView)itemView.findViewById(R.id.tv_message);
            make_call = (AppCompatImageView) itemView.findViewById(R.id.call_button);


        }


    }

//    private void makePhoneCall() {
//        if (patinetPhoneNumber.length() > 0) {
//            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
//            } else {
//                String dial = "tel:" + patinetPhoneNumber;
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(dial));
//                context.startActivity(intent);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_CALL) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                makePhoneCall();
//            } else {
//
//            }
//        }
//    }
}
