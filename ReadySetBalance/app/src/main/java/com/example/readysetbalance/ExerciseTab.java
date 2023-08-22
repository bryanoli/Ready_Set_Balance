package com.example.readysetbalance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;

public class ExerciseTab extends AppCompatActivity {
    Button menu_btn;
    ListView listView;
    VideoView videoView;
    ArrayList<String>videoList;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_tab);


        videoView = findViewById(R.id.videoview);
        listView = (ListView) findViewById(R.id.lvideo);
        videoList = new ArrayList<>();

        videoList.add("Push Ups (Beginner)");
        videoList.add("Triangle Push Ups (Intermediate)");
        videoList.add("Birpies (Advanced)");
        videoList.add("Back to Main Menu");

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,videoList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch(position){
                case 0:
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pushupsbeg));
                    break;
                case 1:
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.trianglepush));
                    break;
                case 2:
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.birpies));
                    break;
                case 3:
                    Intent intent = new Intent(ExerciseTab.this,UserPage.class);
                    startActivity(intent);
                default:
                    break;
            }
            videoView.setMediaController(new MediaController(ExerciseTab.this));
            videoView.requestFocus();

            }
            });

        }

    }
