package com.example.readysetbalance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BmiCalculator extends AppCompatActivity {

    EditText height;
    EditText weight;
    TextView result;
    Button back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        result = (TextView) findViewById(R.id.result);

        back_btn = (Button) findViewById(R.id.back_button);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BmiCalculator.this, UserPage.class);
                startActivity(intent);
            }
        });

    }

    public void calculateBMI(View view){
        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();

        if(heightStr != null && !"".equals(heightStr)&& weightStr != null && !"".equals(weightStr)){
            float heightVal = Float.parseFloat(heightStr)/100;
            float weightVal = Float.parseFloat(weightStr);
            float bmi = weightVal/(heightVal*heightVal);
            displayBMI(bmi);
        }

    }

    private void displayBMI(float bmi) {
        String label = "";
        if(Float.compare(bmi,15f)<=0){
            label = getString(R.string.underweight);
        }else if(Float.compare(bmi, 15f) > 0 && Float.compare(bmi, 18.5f) <= 0) {
            label = getString(R.string.underweight);
        } else if (Float.compare(bmi, 18.5f) > 0 && Float.compare(bmi, 25f) <= 0) {
            label = getString(R.string.normal);
        }else if(Float.compare(bmi, 25f) > 0 && Float.compare(bmi, 30f) <= 0){
            label = getString(R.string.overweight);
        }else{
            label = getString(R.string.obese);
        }
        label = bmi + "\n\n" + label;
        result.setText(label);
    }



}