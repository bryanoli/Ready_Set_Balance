package com.example.readysetbalance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private TextView title, create;
    private FirebaseAuth mAuth;
    private EditText editTextName, editTextAge, editTextEmail, editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        title = (TextView) findViewById(R.id.banner_register);
        title.setOnClickListener(this);

        create = (Button) findViewById(R.id.register_button);
        create.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextAge = (EditText) findViewById(R.id.editTextTextPersonAge);
        editTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner_register:
                startActivity(new Intent(this,GauchoLogin.class));
                break;


            case R.id.register_button:
                create();
                startActivity(new Intent(this,GauchoLogin.class));
                break;
        }

    }

    private void create() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if(name.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }
        if(age.isEmpty()){
            editTextAge.setError("Age is required");
            editTextAge.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Provide valid email");
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

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(name,age,email);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUp.this,"User has been registered", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(SignUp.this,"Failed to register",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(SignUp.this,"Failed to register",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}