package com.example.foodexpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;

    Button btnLogin;

    TextView txtRegister;

    DatabaseHelper databaseHelper;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);

        txtRegister = findViewById(R.id.txtRegister);

        databaseHelper = new DatabaseHelper(this);

        sessionManager = new SessionManager(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString().trim();

                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {

                    Toast.makeText(
                            LoginActivity.this,
                            "Please fill all fields",
                            Toast.LENGTH_SHORT
                    ).show();

                } else {

                    boolean validUser = databaseHelper.checkUser(
                            email,
                            password
                    );

                    if (validUser) {

                        sessionManager.setLogin(true);

                        sessionManager.saveUserEmail(email);

                        Toast.makeText(
                                LoginActivity.this,
                                "Login Successful",
                                Toast.LENGTH_SHORT
                        ).show();

                        startActivity(new Intent(
                                LoginActivity.this,
                                HomeActivity.class
                        ));

                        finish();

                    } else {

                        Toast.makeText(
                                LoginActivity.this,
                                "Invalid email or password",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(
                        LoginActivity.this,
                        RegisterActivity.class
                ));
            }
        });
    }
}