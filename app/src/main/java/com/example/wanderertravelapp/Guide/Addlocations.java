package com.example.wanderertravelapp.Guide;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.wanderertravelapp.Class.Location;
import com.example.wanderertravelapp.MainActivity;
import com.example.wanderertravelapp.R;
import com.example.wanderertravelapp.Registry.SignUp;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;
import java.util.Locale;


public class Addlocations extends AppCompatActivity {

    private static final int GALLERY_INTENT = 2;
    public Uri imgUri;
    DatabaseReference reff;
    StorageReference mimg;
    Location location;
    private ImageButton mGallery;
    private ImageView mImageview;
    private ImageButton mUpload;
    private TextView locname , locaddress , locdes;

    TextView txtLoc;
    Button btnLoc;
    FusedLocationProviderClient fusedLocationProviderClient;
    List<Address> addressList;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlocations);

        mGallery = (ImageButton) findViewById(R.id.Adgallary);

        mUpload = (ImageButton) findViewById(R.id.btnadd);

        mImageview = (ImageView) findViewById(R.id.viewimg);

        locname=findViewById(R.id.locname);
        locaddress=findViewById(R.id.locAddress);
        locdes=findViewById(R.id.locdescription);

        txtLoc = findViewById(R.id.txtLocation);
        btnLoc = findViewById(R.id.btnGetLoc);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        location = new Location();
        reff = FirebaseDatabase.getInstance().getReference("Location");
        mimg = FirebaseStorage.getInstance().getReference("Location");





        mGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Select();
            }
        });


        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
               uploader();
            }
        });



        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(Addlocations.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    currentLocation();
                }
                else
                {
                    ActivityCompat.requestPermissions(Addlocations.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });

    }

    @SuppressLint("MissingPermission")
    private void currentLocation()
    {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
            @Override
            public void onComplete(@NonNull Task<android.location.Location> task) {
                android.location.Location current = task.getResult();
                if(current != null)
                {
                    Geocoder geocoder = new Geocoder(Addlocations.this, Locale.getDefault());
                    try
                    {
                        addressList = geocoder.getFromLocation(current.getLatitude(), current.getLongitude(),1);

                        txtLoc.setText(addressList.get(0).getCountryName() +"  "+ addressList.get(0).getLatitude() + " " +addressList.get(0).getLongitude() +" "+addressList.get(0).getAddressLine(0) );
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(Addlocations.this,ex.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }


    private void Select()
    {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);


    }
    private String getExtenstion(Uri uri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mp =MimeTypeMap.getSingleton();
        return mp.getExtensionFromMimeType(cr.getType(uri));

    }
    private void uploader(){

        if(checkValid())
        {
            String Imgid;
            Imgid = System.currentTimeMillis()+"."+getExtenstion(imgUri);
            location.setName(locname.getText().toString().trim());
            location.setAddress(locaddress.getText().toString().trim());
            location.setDescrip(locdes.getText().toString().trim());
            location.setLatitude(addressList.get(0).getLatitude());
            location.setLongitude(addressList.get(0).getLongitude());
            location.setAddressLine(addressList.get(0).getAddressLine(0));
            location.setCountry(addressList.get(0).getCountryName());

            location.setImgID(Imgid);

            reff.push().setValue(location);


            StorageReference ref = mimg.child(Imgid);

            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            Toast.makeText(Addlocations.this,"Added Successfully",Toast.LENGTH_LONG).show();



                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK&& data!=null && data.getData()!=null){

            imgUri = data.getData();
            mImageview.setImageURI(imgUri);
        }
    }


    private boolean checkValid(){
        if (locname.getText().toString().isEmpty())
        {
            Toast.makeText(Addlocations.this, " Input Location name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(locaddress.getText().toString().isEmpty())
        {
            Toast.makeText(Addlocations.this, " Input Location", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(locdes.getText().toString().isEmpty())
        {
            Toast.makeText(Addlocations.this, " Input Description", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(txtLoc.getText().toString().isEmpty())
        {
            Toast.makeText(Addlocations.this, " Press the button to enter coordinates", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(mImageview.getDrawable()==null)
        {
            Toast.makeText(Addlocations.this, " Insert Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }


}