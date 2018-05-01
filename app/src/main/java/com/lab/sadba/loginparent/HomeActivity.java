package com.lab.sadba.loginparent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toolbar;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    private CardView tempsCard, notesCard, evalCard, infosCard;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tempsCard = findViewById(R.id.temps_card);
        notesCard = findViewById(R.id.notes_card);
        evalCard = findViewById(R.id.eval_card);
        infosCard = findViewById(R.id.infos_card);

        //Add Click Listener to the cards
        tempsCard.setOnClickListener(this);
        notesCard.setOnClickListener(this);
        evalCard.setOnClickListener(this);
        infosCard.setOnClickListener(this);

       /* toolbar =  findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Dashboard");
        *//*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("Dashboard");
        }*/
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.temps_card : i = new Intent(this, EmploiActivity.class);startActivity(i); break;
            case R.id.notes_card : i = new Intent(this, NoteActivity.class);startActivity(i); break;
            case R.id.eval_card : i = new Intent(this, EvalActivity.class);startActivity(i); break;
            case R.id.infos_card : i = new Intent(this, InfoEtabActivity.class);startActivity(i); break;
            default: break;
        }
    }
}
