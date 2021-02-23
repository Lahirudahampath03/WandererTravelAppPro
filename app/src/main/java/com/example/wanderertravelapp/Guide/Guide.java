package com.example.wanderertravelapp.Guide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanderertravelapp.Class.Location;
import com.example.wanderertravelapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;

public class Guide extends AppCompatActivity {
TextView heading , displaydes;
ImageView imgdisplay;
 private DatabaseReference ref ;
SearchView searchView;
Button button;
private RecyclerView recyclerView;
private
ArrayList<Location> locations = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide2);

        heading= (TextView)findViewById(R.id.txtheadingname);
        displaydes=(TextView) findViewById(R.id.txtdisdesc);
        imgdisplay = (ImageView) findViewById(R.id.imgdisplay);
        searchView=(SearchView)findViewById(R.id.searchView);
        button=(Button)findViewById(R.id.button);
        ref = FirebaseDatabase.getInstance().getReference().child("Location");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String name=dsp.child("name").getValue().toString();
                    String desc=dsp.child("descrip").getValue().toString();
                    Location loc = new Location();
                    loc.setName(name);
                    loc.setDescrip(desc);
                    locations.add(loc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             OpenImage();

            }
        });
    }
    private void OpenImage(){
        Intent intent = new Intent(this, ViewGuide.class);
        startActivity(intent);
    }
}