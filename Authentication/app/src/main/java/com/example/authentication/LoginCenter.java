package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginCenter extends AppCompatActivity {

    EditText LEmail, LPassword;
    Button Login;
    TextView Lsignup;

    FirebaseAuth LAuth;
    ProgressBar LProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_center);

        LEmail     = findViewById(R.id.name_id4);
        LPassword  = findViewById(R.id.password_id4);
        Login      = findViewById(R.id.login_id4);

        LAuth      = FirebaseAuth.getInstance();
        LProgress  = findViewById(R.id.progressBar5);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = LEmail.getText().toString().trim();
                String password = LPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    LEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    LPassword.setError("Password is Required");
                    return;
                }

                if(password.length() <6 ){
                    LPassword.setError("Password Must be >= 8 Characters");
                    return;
                }

                LProgress.setVisibility(View.VISIBLE);

                // Authenticate user
                LAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //if(email == "center1@gmail.com")
                                Toast.makeText(LoginCenter.this, "Logged In Successfuly", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Center_Profile.class));
                        }else{
                            Toast.makeText(LoginCenter.this, "Error occured ! "+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            LProgress.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }
}