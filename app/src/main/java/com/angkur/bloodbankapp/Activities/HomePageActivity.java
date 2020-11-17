package com.angkur.bloodbankapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.angkur.bloodbankapp.Adapters.AllPostsAdapter;
import com.angkur.bloodbankapp.Adapters.RequestAdapter;
import com.angkur.bloodbankapp.Models.RequestDataModel;
import com.angkur.bloodbankapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.transform.ErrorListener;

public class HomePageActivity extends AppCompatActivity {

    TextView textViewName, textViewPhone, textViewCity, textViewBloodGroup, buttonMakeRequest;
    Button buttonLogout;

    private RecyclerView recyclerView;
    private List<RequestDataModel> postList;
    private AllPostsAdapter allPostsAdapter;
    Toolbar toolbar;

    String nameFromDB, cityFromDB, phoneFromDB, bloodGroupFromDB;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("posts");
        postList = new ArrayList<>();


        // Getting Data from Login Page and MakeRequest Activity
        nameFromDB = getIntent().getStringExtra("name");
        cityFromDB = getIntent().getStringExtra("city");
        phoneFromDB = getIntent().getStringExtra("phone");
        bloodGroupFromDB = getIntent().getStringExtra("bloodGroup");

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        buttonMakeRequest = findViewById(R.id.btn_make_request);

        buttonMakeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MakeRequestActivity.class);
                intent.putExtra("posterName", nameFromDB);
                intent.putExtra("posterCity", cityFromDB);
                intent.putExtra("posterPhone", phoneFromDB);
                intent.putExtra("posterBloodGroup", bloodGroupFromDB);
                startActivity(intent);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.search_button) {
                    //open search
                    startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                }
                return false;
            }
        });



        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        RequestDataModel post = ds.getValue(RequestDataModel.class);
                        postList.add(post);
                    }
                }
                allPostsAdapter = new AllPostsAdapter(HomePageActivity.this, postList);
                recyclerView.setAdapter(allPostsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}