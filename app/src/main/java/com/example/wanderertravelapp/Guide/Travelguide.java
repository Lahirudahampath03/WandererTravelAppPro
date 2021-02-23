package com.example.wanderertravelapp.Guide;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.view.View;
import android.widget.Toast;

import com.example.wanderertravelapp.MainActivity;
import com.example.wanderertravelapp.R;
import com.google.firebase.auth.FirebaseAuth;


public class Travelguide extends AppCompatActivity {

   CardView enterguide, Addloc;
   FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelguide);
        enterguide= (CardView) findViewById(R.id.viewguide);
        Addloc=(CardView) findViewById(R.id.addloc);
        firebaseAuth = FirebaseAuth.getInstance();

        enterguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplication(), ViewGuide.class);
                startActivity(intent);

            }
        });

        Addloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    Toast.makeText(Travelguide.this, "You have to login to Access this Page", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent =new Intent(getApplication(), Addlocations.class);
                    startActivity(intent);
                }

            }
        });
    }
}