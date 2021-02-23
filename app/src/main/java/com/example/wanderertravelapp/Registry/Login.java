package com.example.wanderertravelapp.Registry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wanderertravelapp.MainActivity;
import com.example.wanderertravelapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {
    Button btnLogin, btnRegister;
    EditText txtEmail, txtPass;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnlogRegister);
        txtEmail = findViewById(R.id.txtlogEmail);
        txtPass = findViewById(R.id.txtlogPassword);
        firebaseAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtEmail.getText().toString().isEmpty())
                {
                    Toast.makeText(Login.this, " Input Email", Toast.LENGTH_SHORT).show();
                }
                else if(txtPass.getText().toString().isEmpty())
                {
                    Toast.makeText(Login.this, " Input password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())//used to check if login is successful
                            {
                                Toast.makeText(Login.this,"Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(Login.this,"Incorrect Username or Password, Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), SignUp.class);
                startActivity(intent);
            }
        });

    }


}
