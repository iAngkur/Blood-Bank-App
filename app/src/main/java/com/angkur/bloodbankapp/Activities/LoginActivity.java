package com.angkur.bloodbankapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.angkur.bloodbankapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText editTextPhone;
    TextView textViewRegister;
    Button buttonLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);

        mAuth = FirebaseAuth.getInstance();

        editTextPhone = findViewById(R.id.et_phone);
        textViewRegister = findViewById(R.id.tv_register);
        buttonLogin = findViewById(R.id.btn_login);

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEnteredPhone = editTextPhone.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("donors").child(userEnteredPhone);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        editTextPhone.setError(null);

                        if (snapshot.getKey().equals(userEnteredPhone)) {
                            String nameFromDB = snapshot.child("name").getValue(String.class);
                            String cityFromDB = snapshot.child("city").getValue(String.class);
                            String phoneFromDB = snapshot.child("phone").getValue(String.class);
                            String bloodGroupFromDB = snapshot.child("bloodGroup").getValue(String.class);

                            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                            intent.putExtra("name", nameFromDB);
                            intent.putExtra("city", cityFromDB);
                            intent.putExtra("phone", phoneFromDB);
                            intent.putExtra("bloodGroup", bloodGroupFromDB);
                            startActivity(intent);
                            finish();
                        } else {
                            editTextPhone.setError("No such User exist");
                            editTextPhone.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

}