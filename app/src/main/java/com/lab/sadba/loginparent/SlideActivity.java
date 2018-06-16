package com.lab.sadba.loginparent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lab.sadba.loginparent.Ui.EnfantActivity;
import com.lab.sadba.loginparent.Ui.MainActivity;

public class SlideActivity extends AppCompatActivity {

    Button testing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        testing = findViewById(R.id.testing);

        testing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
