package com.lab.sadba.loginparent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AbscenceActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abscence);


        toolbar =  findViewById(R.id.toolbar_emploi);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);


        toolbar.setTitle("Abscences et Retards");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Your code
                finish();
            }
        });
    }
}
