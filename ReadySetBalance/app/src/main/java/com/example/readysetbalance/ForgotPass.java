package com.example.readysetbalance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass extends AppCompatActivity {

    EditText emailEditText;
    Button reset;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

         emailEditText = (EditText) findViewById(R.id.editTextEmailAddress2);
         reset = (Button) findViewById(R.id.reset_button);

         auth = FirebaseAuth.getInstance();

         reset.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 resetPass();
                 startActivity(new Intent(ForgotPass.this, GauchoLogin.class));
             }
         });



    }
    private void resetPass(){
        String email = emailEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please enter a valid email");
            emailEditText.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPass.this, "Check your email to reset", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ForgotPass.this, "Try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}