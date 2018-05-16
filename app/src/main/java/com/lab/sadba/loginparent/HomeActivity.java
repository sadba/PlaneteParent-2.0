package com.lab.sadba.loginparent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.lab.sadba.loginparent.Model.Temps;
import com.lab.sadba.loginparent.Remote.ApiClient3;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private String ien;
    android.support.v7.widget.Toolbar toolbar;
    private Realm realm;
    private CardView tempsCard, notesCard, evalCard, infosCard;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

        toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");

         ien = getIntent().getStringExtra("ien_enfant");
        //Toast.makeText(this, ien, Toast.LENGTH_SHORT).show();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ien_enfant", ien);
        editor.apply();


        Realm.init(getApplicationContext());
        getEmploi(ien);

    }

    private void getEmploi(String ien) {
        realm = Realm.getDefaultInstance();
        IMyAPI service = ApiClient3.getRetrofit().create(IMyAPI.class);
        service.getTemps(ien)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<List<Temps>>() {
                    @Override
                    public void onNext(List<Temps> temps) {
                        realm = Realm.getDefaultInstance();
                       // Toast.makeText(HomeActivity.this, String.valueOf(temps.size()), Toast.LENGTH_SHORT).show();

                        try{
                            realm = Realm.getDefaultInstance();
                            realm.executeTransaction(realm1 -> {
                                for (Temps temp: temps){
                                    Temps temps1 = new Temps();
                                    temps1.setLibelle_discipline(temp.getLibelle_discipline());
                                    temps1.setHeure_debut(temp.getHeure_debut());
                                    temps1.setHeure_fin(temp.getHeure_fin());
                                    temps1.setLibelle_classe(temp.getLibelle_classe());
                                    temps1.setLibelle_classe_physique(temp.getLibelle_classe_physique());
                                    temps1.setNum_jour(temp.getNum_jour());
                                    temps1.setId_planing_horaire(temp.getId_planing_horaire());
                                    temps1.setCode_classe(temp.getCode_classe());
                                    temps1.setCouleur_discipline(temp.getCouleur_discipline());
                                    temps1.setId_classe_physique(temp.getId_classe_physique());
                                    temps1.setCouleur_discipline(temp.getCouleur_discipline());
                                    temps1.setJour_planning(temp.getJour_planning());


                                    realm.copyToRealmOrUpdate(temps1);
                                }
                            });
                        } catch (Exception e){
                            realm.close();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.temps_card :
                i = new Intent(this, EmploiActivity.class);
                i.putExtra("ien_bis", ien);
                startActivity(i);
            break;

            case R.id.notes_card : i =
                    new Intent(this, NoteActivity.class);
                    i.putExtra("ien_bis", ien);
                    startActivity(i);
                    break;

            case R.id.eval_card : i =
                    new Intent(this, EvalActivity.class);
                    i.putExtra("ien_bis", ien);
                    startActivity(i);
                    break;
            case R.id.infos_card : i =
                    new Intent(this, InfoEtabActivity.class);
                    i.putExtra("ien_bis", ien);
                    startActivity(i);
            break;
            default: break;
        }
    }
}
