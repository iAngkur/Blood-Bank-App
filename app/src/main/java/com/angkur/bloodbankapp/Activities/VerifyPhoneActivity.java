package com.angkur.bloodbankapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.angkur.bloodbankapp.Models.Donor;
import com.angkur.bloodbankapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    String verificationCodeBySystem;
    String name, phone, city, bloodGroup;

    EditText editTextPhoneNoEnteredByUser;
    Button buttonVerify;
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("donors");

        editTextPhoneNoEnteredByUser = findViewById(R.id.et_verification_entered_by_user);
        buttonVerify = findViewById(R.id.btn_verify);
        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.GONE);

        // Getting User Info from Registration Activity
        name = getIntent().getStringExtra("nameKey");
        phone = getIntent().getStringExtra("phoneKey");
        city = getIntent().getStringExtra("cityKey");
        bloodGroup = getIntent().getStringExtra("bloodGroupKey");


        sendVerificationCodeToUser(phone);

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = editTextPhoneNoEnteredByUser.getText().toString();

                if (code.isEmpty() || code.length() < 6) {
                    editTextPhoneNoEnteredByUser.setError("Wrong OTP");
                    editTextPhoneNoEnteredByUser.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });

    }

    private void sendVerificationCodeToUser(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+88" + phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
//            signInWithPhoneAuthCredential(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            showMessage(e.getMessage());
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
        }

    };

    private void verifyCode(String userCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, userCode);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    // Inserting Data into Database
                    storeUserInfo();

                    // Go to Login Activity
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    showMessage("OTP Didn't work");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

        private void storeUserInfo() {
        Donor donor = new Donor(name, phone, city, bloodGroup);
        try {
            databaseReference.child(donor.getPhone()).setValue(donor).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        showMessage("Registration Successful");
                        progressBar.setVisibility(View.GONE);
                    } else {
                        showMessage("Registration Failed");
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        } catch (Exception e) {
            showMessage("Something went wrong.");
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}