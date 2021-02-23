package com.example.wanderertravelapp.Guide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.wanderertravelapp.Class.ImageAdapter;
import com.example.wanderertravelapp.Class.Location;
import com.example.wanderertravelapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ViewGuide extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private DatabaseReference reff;
    private StorageReference imgref;
    private List<Location> mloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_guide);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mloc = new ArrayList<>();

        reff = FirebaseDatabase.getInstance().getReference("Location");
        imgref= FirebaseStorage.getInstance().getReference("Location");



        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Location location = postSnapshot.getValue(Location.class);
                    mloc.add(location);
                }
                imageAdapter = new ImageAdapter(ViewGuide.this,mloc);
                recyclerView.setAdapter(imageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ViewGuide.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
}