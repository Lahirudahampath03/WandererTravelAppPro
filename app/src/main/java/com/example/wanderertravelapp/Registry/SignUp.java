package com.example.wanderertravelapp.Registry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wanderertravelapp.Class.Admin;
import com.example.wanderertravelapp.MainActivity;
import com.example.wanderertravelapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText txtemail,txtusername,txtpassword;
    Button btnregister;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtemail = findViewById(R.id.txtEmail);
        txtusername = findViewById(R.id.txtUsername);
        txtpassword = findViewById(R.id.txtPassword);
        btnregister = findViewById(R.id.btnRegister);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if (txtemail.getText().toString().isEmpty())
                    {
                        Toast.makeText(SignUp.this, " Input name", Toast.LENGTH_SHORT).show();
                    }
                    else if(txtusername.getText().toString().isEmpty())
                    {
                        Toast.makeText(SignUp.this, " Input username", Toast.LENGTH_SHORT).show();
                    }
                    else if(txtpassword.getText().toString().isEmpty())
                    {
                        Toast.makeText(SignUp.this, " Input password", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {   //if user registers in firebase(on complete)
                        firebaseAuth.createUserWithEmailAndPassword(txtemail.getText().toString(), txtpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) //if user is created
                                {
                                    Toast.makeText(SignUp.this, " User is registered", Toast.LENGTH_SHORT).show();

                                    String type = "User";
                                    String uID = firebaseAuth.getCurrentUser().getUid();
                                    Admin admin = new Admin(txtemail.getText().toString(),txtusername.getText().toString(),txtpassword.getText().toString(),type);
                                    reference.child(uID).setValue(admin);

                                    Intent intent = new Intent(getApplication(), MainActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(SignUp.this, " User is not registered" + task.getException(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
                catch (Exception ex)
                {
                        Toast.makeText(SignUp.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}