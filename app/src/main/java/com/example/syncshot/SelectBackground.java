package com.example.syncshot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SelectBackground extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_background_);


        Button button7 =  findViewById(R.id.back_button);
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SelectBackground.this, InvitePeople.class));
            }
        });

        Button button6 = findViewById(R.id.next_button);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SelectBackground.this, SelectPose.class));
            }
        });

        Button button9 = findViewById(R.id.skip_button);
        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SelectBackground.this, SelectPose.class));
            }
        });
    }
}