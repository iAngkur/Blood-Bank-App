package com.angkur.bloodbankapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.angkur.bloodbankapp.Models.Donor;
import com.angkur.bloodbankapp.Models.RequestDataModel;
import com.angkur.bloodbankapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MakeRequestActivity extends AppCompatActivity {

    EditText editTextPatientName, editTextPatientCity, editTextPatientPhone, editTextPatientBloodGroup, editTextMessage;
    Button buttonPost;
    TextView buttonCancel;
    ProgressBar postProgressBar;

    String patientName, patientCity, patientPhone, patientBloodGroup, patientMessage;
    String userName, userCity, userPhone, userBloodGroup;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);

        editTextPatientName = findViewById(R.id.et_patient_name);
        editTextPatientPhone = findViewById(R.id.et_patient_phone);
        editTextPatientCity = findViewById(R.id.et_patient_city);
        editTextPatientBloodGroup = findViewById(R.id.et_patient_bloodGroup);
        editTextMessage = findViewById(R.id.et_message);
        postProgressBar = findViewById(R.id.post_progress_bar);


        buttonPost = findViewById(R.id.btn_post);
        buttonCancel = findViewById(R.id.btn_cancel_post);

        // Getting Info from Homepage
        userName = getIntent().getStringExtra("posterName");
        userCity = getIntent().getStringExtra("posterCity");
        userPhone = getIntent().getStringExtra("posterPhone");
        userBloodGroup = getIntent().getStringExtra("posterBloodGroup");


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextPatientName.setText("");
                editTextPatientCity.setText("");
                editTextPatientPhone.setText("");
                editTextPatientBloodGroup.setText("");
                editTextMessage.setText("");

                startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                finish();
            }
        });

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patientName = editTextPatientName.getText().toString();
                patientPhone = editTextPatientPhone.getText().toString();
                patientCity = editTextPatientCity.getText().toString();
                patientBloodGroup = editTextPatientBloodGroup.getText().toString();
                patientMessage = editTextMessage.getText().toString();

                if(isUserInputValid()) {
                    postProgressBar.setVisibility(View.VISIBLE);

                    // Storing Post to Database
                    storePost();

                    // Going Back to Homepage
                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                    intent.putExtra("name", userName);
                    intent.putExtra("city", userCity);
                    intent.putExtra("phone", userPhone);
                    intent.putExtra("bloodGroup", userBloodGroup);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void storePost() {
        RequestDataModel post = new RequestDataModel(userPhone, patientName, patientCity, patientPhone, patientBloodGroup, patientMessage);

        databaseReference = FirebaseDatabase.getInstance().getReference("posts").child(userPhone);
        try {
            databaseReference.push().setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        showMessage("Post Successful");
                        postProgressBar.setVisibility(View.GONE);
                    } else {
                        showMessage("Post Failed");
                        postProgressBar.setVisibility(View.GONE);
                    }
                }
            });
        } catch (Exception e) {
            showMessage("Something went wrong.");
            postProgressBar.setVisibility(View.GONE);
        }
    }

    private boolean isUserInputValid() {
        List<String> valid_blood_groups = new ArrayList<>();
        valid_blood_groups.add("A+");
        valid_blood_groups.add("A-");
        valid_blood_groups.add("B+");
        valid_blood_groups.add("B-");
        valid_blood_groups.add("AB+");
        valid_blood_groups.add("AB-");
        valid_blood_groups.add("O+");
        valid_blood_groups.add("O-");

        if (patientName.isEmpty()) {
            editTextPatientName.setError("Name is required");
            editTextPatientName.requestFocus();
            return false;
        } else if (patientCity.isEmpty()) {
            editTextPatientCity.setError("City name is required");
            editTextPatientCity.requestFocus();
            return false;
        } else if (patientBloodGroup.isEmpty()) {
            editTextPatientBloodGroup.setError("Blood Group is required");
            editTextPatientBloodGroup.requestFocus();
            return false;
        } else if (!valid_blood_groups.contains(patientBloodGroup)) {
            showMessage("Blood group invalid choose from " + valid_blood_groups);
            return false;
        } else if (patientPhone.length() != 11) {
            editTextPatientPhone.setError("Invalid mobile number, number should be of 11 digits");
            editTextPatientPhone.requestFocus();
            return false;
        } else if (patientMessage.isEmpty()) {
            editTextMessage.setError("Message can't be Empty.");
            editTextMessage.requestFocus();
            return false;
        } else if (patientMessage.length() > 200) {
            editTextMessage.setError("Message can't be more than 300 characters");
            editTextMessage.requestFocus();
            return false;
        }
        return true;
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}