package com.example.admin.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignupActivity extends AppCompatActivity {
    EditText edemail, edpwd;
    Button btnsignup;
    TextView txtsignin;
    FirebaseAuth mAuth;
    String email, pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edemail = findViewById(R.id.input_email);
        edpwd = findViewById(R.id.input_password);
        btnsignup = findViewById(R.id.btn_signup);
        txtsignin = findViewById(R.id.text_signin);

        mAuth = FirebaseAuth.getInstance();

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(i);
            }
        });

    }

    private void submitForm() {
        email = edemail.getText().toString().trim();
        pwd = edpwd.getText().toString().trim();

        if (email.isEmpty()) {
            edemail.setError("Email is required");
            edemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edemail.setError("Please enter a valid email");
            edemail.requestFocus();
            return;
        }

        if (pwd.isEmpty()) {
            edpwd.setError("Password is required");
            edpwd.requestFocus();
            return;
        }

        if (pwd.length() < 6) {
            edpwd.setError("Minimum lenght of password should be 6");
            edpwd.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_LONG).show();
                    finish();
                    Intent i = new Intent(getApplicationContext(), DetailsActivity.class);
                    startActivity(i);
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


    }
}
