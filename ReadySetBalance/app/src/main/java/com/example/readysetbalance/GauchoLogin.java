package com.example.readysetbalance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class GauchoLogin extends AppCompatActivity implements View.OnClickListener{

    private TextView signUp, forgotPass;
    private EditText editTextEmail, editTextPassword;
    private Button login;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaucho_login);


        forgotPass = (TextView) findViewById(R.id.forgotPassword);
        forgotPass.setOnClickListener(GauchoLogin.this);

        login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(GauchoLogin.this);

        editTextEmail = (EditText) findViewById(R.id.editText_username);
        editTextPassword = (EditText) findViewById(R.id.editText_password);


        signUp = (TextView) findViewById(R.id.register);
        signUp.setOnClickListener(GauchoLogin.this);

        mAuth = FirebaseAuth.getInstance();



    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                startActivity(new Intent(this,SignUp.class));
                break;

            case R.id.button_login:

                login();
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(this,ForgotPass.class));
                break;
        }
    }

    private void login() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() <6){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(GauchoLogin.this,UserPage.class));
                }else{
                    Toast.makeText(GauchoLogin.this,"Failed to login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}






