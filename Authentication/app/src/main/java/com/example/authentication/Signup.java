package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.EGLExt;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText sName, sEmail, sPhone, sPassword;
    Spinner sBloodType;
    Button sSignup;
    TextView sLogin;
    FirebaseAuth sAuth;
    FirebaseFirestore fstore;
    ProgressBar sProgress;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("SignUp");

        sName      = findViewById(R.id.name_id);
        sEmail     = findViewById(R.id.email_id);
        sBloodType = findViewById(R.id.bloodType_id);
        sPhone     = findViewById(R.id.phone_id);
        sPassword  = findViewById(R.id.password_id);
        sSignup    = findViewById(R.id.login_id);
        sLogin     = findViewById(R.id.textsignup_id);

        sAuth      = FirebaseAuth.getInstance();
        fstore     = FirebaseFirestore.getInstance();
        sProgress  = findViewById(R.id.progressBar2);



        // spinner blood types
        final String[] bloodtypes_array = getResources().getStringArray(R.array.bloodtype_array);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodtypes_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sBloodType.setAdapter(adapter);


        if(sAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Profile.class));
            finish();
        }

        sSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = sEmail.getText().toString().trim();
                String password = sPassword.getText().toString();
                final String phone = sPhone.getText().toString();
                final String name = sName.getText().toString();
                final String bloodSelected = sBloodType.getSelectedItem().toString();

                if(TextUtils.isEmpty(email)){
                    sEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    sPassword.setError("Password is Required");
                    return;
                }

                if(password.length() <2 ){
                    sPassword.setError("Password Must be >= 8 Characters");
                    return;
                }

                sProgress.setVisibility(View.VISIBLE);
                //Log.d("degub1", "afterProgress");

                // check user on firebase
                sAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //
                        if(task.isSuccessful()){
                            Toast.makeText(Signup.this, "User Created", Toast.LENGTH_LONG).show();

                            // creating user database on firebase
                            UserID = sAuth.getCurrentUser().getUid();
                            DocumentReference DR = fstore.collection("users").document(UserID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName", name);
                            user.put("fEmail", email);
                            user.put("fPhone", phone);
                            user.put("fBloodType", bloodSelected);

                            // check if creating been successful
                            DR.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"user Profile is created "+ UserID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                        }else{
                            Toast.makeText(Signup.this, "Error occured ! "+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });

        sLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}