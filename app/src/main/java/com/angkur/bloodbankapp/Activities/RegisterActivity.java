package com.angkur.bloodbankapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.angkur.bloodbankapp.Models.Donor;
import com.angkur.bloodbankapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextName, editTextPhone, editTextCity, editTextBloodGroup;
    TextView textViewLogin;
    Button buttonRegister;

    private String name, phone, city, bloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.et_name);
        editTextPhone = findViewById(R.id.et_phone);
        editTextCity = findViewById(R.id.et_city);
        editTextBloodGroup = findViewById(R.id.et_bloodGroup);
        textViewLogin = findViewById(R.id.tv_login);
        buttonRegister = findViewById(R.id.btn_register);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = editTextName.getText().toString().trim();
                phone = editTextPhone.getText().toString().trim();
                city = editTextCity.getText().toString().trim();
                bloodGroup = editTextBloodGroup.getText().toString().trim();

                if (isInputValid()){
                    Intent intent = new Intent(getApplicationContext(), VerifyPhoneActivity.class);
                    intent.putExtra("nameKey", name);
                    intent.putExtra("phoneKey", phone);
                    intent.putExtra("cityKey", city);
                    intent.putExtra("bloodGroupKey", bloodGroup);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean isInputValid() {
        List<String> valid_blood_groups = new ArrayList<>();
        valid_blood_groups.add("A+");
        valid_blood_groups.add("A-");
        valid_blood_groups.add("B+");
        valid_blood_groups.add("B-");
        valid_blood_groups.add("AB+");
        valid_blood_groups.add("AB-");
        valid_blood_groups.add("O+");
        valid_blood_groups.add("O-");

        if (name.isEmpty()) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return false;
        } else if (city.isEmpty()) {
            editTextCity.setError("City name is required");
            editTextCity.requestFocus();
            return false;
        } else if (bloodGroup.isEmpty()) {
            editTextBloodGroup.setError("Blood Group is required");
            editTextBloodGroup.requestFocus();
            return false;
        } else if (!valid_blood_groups.contains(bloodGroup)) {
            showMessage("Blood group invalid choose from " + valid_blood_groups);
            return false;
        } else if (phone.length() != 11) {
            editTextPhone.setError("Invalid mobile number, number should be of 11 digits");
            editTextPhone.requestFocus();
            return false;
        }
        return true;
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}