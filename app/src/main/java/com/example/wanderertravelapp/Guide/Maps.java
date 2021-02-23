package com.example.wanderertravelapp.Guide;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wanderertravelapp.Class.Location;
import com.example.wanderertravelapp.MainActivity;
import com.example.wanderertravelapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;
import com.mapbox.mapboxsdk.style.light.Position;

import java.util.ArrayList;

public class Maps extends AppCompatActivity {
 private MapView mapView;
 DatabaseReference reference;
 ArrayList<Location> locations = new ArrayList<>();

 private PermissionsManager permissionsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this,"sk.eyJ1Ijoid2FuZGVyZXJ0cmF2ZWxhcHAiLCJhIjoiY2tnaHdwbjdmMW1vOTJ0czF6ajVkbHpqOCJ9.eVdTBKNDmJrHl1kGyD7Qeg");
        setContentView(R.layout.activity_maps);
        reference = FirebaseDatabase.getInstance().getReference().child("Location");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Double latitude = Double.valueOf(dsp.child("latitude").getValue().toString());
                    Double longitude = Double.valueOf(dsp.child("longitude").getValue().toString());
                    String name = dsp.child("name").getValue().toString();
                    Location loc = new Location();
                    loc.setLatitude(latitude);
                    loc.setLongitude(longitude);
                    loc.setName(name);
                    locations.add(loc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mapView = findViewById(R.id.mapbox);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                for (Location l : locations)
                {
                    MarkerOptions options = new MarkerOptions();
                    options.title(l.getName());
                    options.position(new LatLng(l.getLatitude(),l.getLongitude()));
                    mapboxMap.addMarker(options);
                    //Toast.makeText(Maps.this, String.valueOf(l.getLatitude()), Toast.LENGTH_SHORT).show();
                    
                }
               /* MarkerOptions options = new MarkerOptions();
                options.title("current location");
                options.position(new LatLng(7.8731,80.7718));
                mapboxMap.addMarker(options);*/
              mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                  @Override
                  public void onStyleLoaded(@NonNull Style style) {


                  }
              });
            }
        });

// Map is set up and the style has loaded. Now you can add data or make other map adjustments


        }



    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}


