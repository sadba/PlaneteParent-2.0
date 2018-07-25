package com.lab.sadba.loginparent.Ui;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.R;

import io.realm.Realm;

public class DossierActivity extends AppCompatActivity {


    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sad);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Your code
                finish();
            }
        });

        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String value = sharedPreferences1.getString("ien_enfant", "");

        realm = Realm.getDefaultInstance();
        Enfant enfant = realm.where(Enfant.class).equalTo("ien_eleve" ,value).findFirst();
        String prenom = enfant.getPrenom_eleve();
        String nom = enfant.getNom_eleve();
        realm.close();



        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbar);
        collapsingToolbar.setTitle(prenom + " " + nom);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
