package com.angkur.bloodbankapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.angkur.bloodbankapp.R;

public class SearchActivity extends AppCompatActivity {

    EditText editTextFindCity, editTextFindBloodGroup;
    Button buttonFindDonors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        editTextFindCity = findViewById(R.id.et_find_city);
        editTextFindBloodGroup = findViewById(R.id.et_find_blood_group);
        buttonFindDonors = findViewById(R.id.btn_find_donors);

        buttonFindDonors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                finish();
            }
        });


    }
}