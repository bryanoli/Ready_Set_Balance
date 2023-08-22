package com.example.readysetbalance;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class UserPage extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    TextView tv_steps;
    Button logout;
    ImageButton imgExe, imgBMI, imgMaps;
    boolean running = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        tv_steps = (TextView) findViewById(R.id.textViewSteps);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        logout = (Button) findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(UserPage.this,GauchoLogin.class);
                startActivity(intent);
            }
        });


        imgBMI = (ImageButton) findViewById(R.id.BMIC);
        imgBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPage.this, BmiCalculator.class);
                startActivity(intent);
            }
        });

        imgExe = (ImageButton) findViewById(R.id.Exer);
        imgExe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPage.this, ExerciseTab.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){
            sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(this,"Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int initial = (int) event.values[0];
        int current = (int) (initial - event.values[0]);
        if(running){

            tv_steps.setText(String.valueOf(initial));
        }
        Button reset_btn = (Button) findViewById(R.id.button_reset);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_steps.setText(String.valueOf(current));

            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }




}


