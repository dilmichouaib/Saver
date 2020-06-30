package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity {

    TextView PUsername, Ptxt1, Ptxt2,Pemail_textview,Pphone_textview,
            Pbloodtype_textview,Plastdonation_textview,Pnbrdonation_textview,Points_textview,place;

    FirebaseAuth PAuth;
    FirebaseFirestore Pstore;
    String UserID;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        email = intent.getStringExtra("fEmail");
        //Log.d("TAG01",email);


        PUsername = findViewById(R.id.PUsername_id);
        Ptxt1     = findViewById(R.id.Ptxt1_id);
        Ptxt2     = findViewById(R.id.Ptxt2_id);
        Pemail_textview     = findViewById(R.id.Pemail_textview_id);
        Pphone_textview     = findViewById(R.id.Pphone_textview_id);
        Pbloodtype_textview     = findViewById(R.id.Pbloodtype_textview_id);
        Plastdonation_textview     = findViewById(R.id.Plastdonation_textview_id);
        Pnbrdonation_textview     = findViewById(R.id.Pnbrdonation_textview_id);
        Points_textview     = findViewById(R.id.Points_textview_id);
        place     = findViewById(R.id.place_id);

        PAuth = FirebaseAuth.getInstance();
        Pstore = FirebaseFirestore.getInstance();

        // ID of current user
        UserID = PAuth.getCurrentUser().getUid();

        // getting the documentation
        DocumentReference DR = Pstore.collection("users").document(UserID);
        DR.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                //PUsername.setText(documentSnapshot.getString("fName"));
                Ptxt1.setText(documentSnapshot.getString("fName"));
                Ptxt2.setText(documentSnapshot.getString("fBloodType"));
                Pemail_textview.setText(documentSnapshot.getString("fEmail"));
                Pphone_textview.setText(documentSnapshot.getString("fPhone"));
                Pbloodtype_textview.setText(documentSnapshot.getString("fBloodType"));
                Plastdonation_textview.setText("45 Days Ago");
                Pnbrdonation_textview.setText("2 Times");
                Points_textview.setText("200 Points");
                place.setText("Batna");

            }
        });



    }




    public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}