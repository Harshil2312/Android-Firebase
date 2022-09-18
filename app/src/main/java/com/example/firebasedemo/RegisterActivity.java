package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText edit_email, edit_password1, edit_password2;

    android.widget.Button btnMakeRegistration;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_email = findViewById(R.id.edit_email);
        edit_password1 = findViewById(R.id.edit_password1);
        edit_password2 = findViewById(R.id.edit_password2);

        btnMakeRegistration = findViewById(R.id.btnMakeRegistration);

        firebaseAuth = FirebaseAuth.getInstance();

        btnMakeRegistration.setOnClickListener(view -> {
            String txtEmail = Objects.requireNonNull(edit_email.getText()).toString();
            String txtPassword1 = Objects.requireNonNull(edit_password1.getText()).toString();
            String txtPassword2 = Objects.requireNonNull(edit_password2.getText()).toString();

            if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword1) || TextUtils.isEmpty(txtPassword2)) {
                Toast.makeText(this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (txtPassword1.equals(txtPassword2) && txtPassword1.length() >= 8) {
                registerUser(txtEmail, txtPassword1);
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void registerUser(String txtEmail, String txtPassword1) {

        firebaseAuth.createUserWithEmailAndPassword(txtEmail, txtPassword1).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}