package com.example.syncshot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class InvitePeople extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_people);


        ImageButton button4 = (ImageButton) findViewById(R.id.back_button);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(InvitePeople.this, MainActivity.class));
            }
        });

        Button button5 = findViewById(R.id.next_button);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(InvitePeople.this, SelectBackground.class));
            }
        });
        Button button6 = findViewById(R.id.skip_button);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(InvitePeople.this, SelectBackground.class));
            }
        });

    }
}