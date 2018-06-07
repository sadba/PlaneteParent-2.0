package com.lab.sadba.loginparent;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lab.sadba.loginparent.Ui.MainActivity;
import com.lab.sadba.loginparent.Ui.VerifActivity;

public class SLideActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    TextView txtSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        btnLogin = findViewById(R.id.btn_signup);
        btnRegister = findViewById(R.id.btn_signin);

        txtSlogan = findViewById(R.id.txtSlogan);

       // Typeface face = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
       // txtSlogan.setTypeface(face);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VerifActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
