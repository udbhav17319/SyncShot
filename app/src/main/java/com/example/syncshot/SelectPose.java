package com.example.syncshot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SelectPose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pose);



        Button button9 = findViewById(R.id.back_button);
        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SelectPose.this, SelectBackground.class));
            }
        });

        Button button8 = findViewById(R.id.next_button);
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SelectPose.this, CameraActivity.class));
            }
        });


        Button skipbutton = findViewById(R.id.skip_button);
        skipbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SelectPose.this, CameraActivity.class));
            }
        });
    }
}