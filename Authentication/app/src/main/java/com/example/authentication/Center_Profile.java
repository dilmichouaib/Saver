package com.example.authentication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Center_Profile extends AppCompatActivity {

    TextView Hname, Hemail_textview, Hphone_textview,PLocation_wilaya, LastDonation, NbrDonators,
            Ptxt1, Ptxt2;
    String email;
    FirebaseAuth BAuth;
    FirebaseFirestore Bstore;
    String CenterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_profile);

        Ptxt2 = findViewById(R.id.Ptxt2_id2);
        Hname = findViewById(R.id.Ptxt1_id2);
        Hemail_textview = findViewById(R.id.Pemail_textview_id2);
        Hphone_textview = findViewById(R.id.Pphone_textview_id2);
        PLocation_wilaya = findViewById(R.id.place_id2);
        LastDonation = findViewById(R.id.Plastdonation_textview_id2);
        NbrDonators = findViewById(R.id.Pnbrdonation_textview_id2);


        Intent intent = getIntent();
        email = intent.getStringExtra("HEmail");

        BAuth      = FirebaseAuth.getInstance();
        Bstore     = FirebaseFirestore.getInstance();

        // ID of current user
        CenterID = BAuth.getCurrentUser().getUid();
        DocumentReference DR = Bstore.collection("Hcenters").document(CenterID);
        DR.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                //PUsername.setText(documentSnapshot.getString("fName"));
                Hname.setText(documentSnapshot.getString("HName"));
                Ptxt2.setText(documentSnapshot.getString("HLocation"));
                Hemail_textview.setText(documentSnapshot.getString("HEmail"));
                Hphone_textview.setText(documentSnapshot.getString("HPhone"));
                PLocation_wilaya.setText(documentSnapshot.getString("HLocation"));
                LastDonation.setText("45 Days Ago");
                NbrDonators.setText("20");
                Log.d("diplaying", String.valueOf(Hname));
            }
        });
    }
}