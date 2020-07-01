package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button to_center, to_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void ToUser(View v){
        startActivity(new Intent(getApplicationContext(), Signup.class));
        finish();
    }

    public void ToCenter(View v) {
        startActivity(new Intent(getApplicationContext(), LoginCenter.class));
        finish();
    }

    /*public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }*/
}