package com.lab.sadba.loginparent.Ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.sadba.loginparent.AbscenceActivity;
import com.lab.sadba.loginparent.Adapter.BulletinAdapter;
import com.lab.sadba.loginparent.Adapter.EvalAdapter;
import com.lab.sadba.loginparent.Adapter.TempsAdapter;
import com.lab.sadba.loginparent.Model.Bulletin;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.Model.Evaluation;
import com.lab.sadba.loginparent.Model.InfosEleves;
import com.lab.sadba.loginparent.Model.Note;
import com.lab.sadba.loginparent.Model.Temps;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Remote.ApiClient3;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private String ien;
    android.support.v7.widget.Toolbar toolbar;
    private Realm realm;
    private List<Temps> evals = new ArrayList<>();
    private List<Note> notes = new ArrayList<>();
    private List<Bulletin> bulletins = new ArrayList<>();
    private CardView tempsCard, notesCard, evalCard, infosCard, abscenceCard;
    TextView persoTitle;
    RecyclerView recycler_bulletin;

    //RxJava
   // CompositeDisposable compositeDisposable = new CompositeDisposable();

    IMyAPI mService;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"CutPasteId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tempsCard = findViewById(R.id.temps_card);
        notesCard = findViewById(R.id.notes_card);
        evalCard = findViewById(R.id.eval_card);
        infosCard = findViewById(R.id.infos_card);
        abscenceCard = findViewById(R.id.absc_retard);


        persoTitle = findViewById(R.id.prenom_toolbar);

        //Add Click Listener to the cards
        tempsCard.setOnClickListener(this);
        notesCard.setOnClickListener(this);
        evalCard.setOnClickListener(this);
        infosCard.setOnClickListener(this);
        abscenceCard.setOnClickListener(this);

        toolbar =  findViewById(R.id.toolbarHome);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        ien = getIntent().getStringExtra("ien_enfant");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ien_enfant", ien);
        editor.apply();



        realm = Realm.getDefaultInstance();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //String value = sharedPreferences.getString("ien_enfant", "");
        Enfant enfant = realm.where(Enfant.class).equalTo("ien_eleve" ,ien).findFirst();
        String prenom = enfant.getPrenom_eleve();
        String nom = enfant.getNom_eleve();

        String lettre = prenom.substring(0,1);

       // InfosEleves infosEleves = realm.where(InfosEleves.class).findFirst();
       // String code_classe = infosEleves.getCode_classe();

       //Toast.makeText(this, code_classe, Toast.LENGTH_SHORT).show();

         toolbar.setTitle("Dashboard");
         persoTitle.setText(lettre+"."+nom);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Your code
                finish();
            }
        });






        Realm.init(getApplicationContext());
        assert ien != null;

        if (isNetworkAvailable(this)) {
            getEmploi(ien);
            getInfos(ien);
            getNotes(ien);
            getBulletins(ien);

        }





    }

    private void getBulletins(String ien) {
        //realm = Realm.getDefaultInstance();
        IMyAPI service = ApiClient3.getRetrofit().create(IMyAPI.class);
        service.getBulletins(ien)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<List<Bulletin>>() {
                    @Override
                    public void onNext(List<Bulletin> bulletins) {
                       // realm = Realm.getDefaultInstance();
                        //Toast.makeText(HomeActivity.this, String.valueOf(bulletins.size()), Toast.LENGTH_SHORT).show();

                        try{
                            realm = Realm.getDefaultInstance();
                            realm.executeTransaction(realm1 -> {
                                for (Bulletin bulletin: bulletins){
                                    Bulletin bulletin1 = new Bulletin();
                                    bulletin1.setId_semestre(bulletin.getId_semestre());
                                    bulletin1.setChemin_bulletin(bulletin.getChemin_bulletin());
                                    bulletin1.setLibelle_semestre(bulletin.getLibelle_semestre());


                                    realm.copyToRealmOrUpdate(bulletin1);
                                }
                            });
                        } catch (Exception e){
                            //Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            realm.close();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Veuillez vérifier votre connection internet", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    private void getNotes(String ien) {
        realm = Realm.getDefaultInstance();
        IMyAPI service = ApiClient3.getRetrofit().create(IMyAPI.class);
        service.getNotes(ien)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<List<Note>>() {
                    @Override
                    public void onNext(List<Note> notes) {
                        realm = Realm.getDefaultInstance();
                        //Toast.makeText(HomeActivity.this, String.valueOf(notes.size()), Toast.LENGTH_SHORT).show();

                        try{
                            realm = Realm.getDefaultInstance();
                            realm.executeTransaction(realm1 -> {
                                for (Note note: notes){
                                    Note note1 = new Note();
                                    note1.setId_note(note.getId_note());
                                    note1.setDate_eval(note.getDate_eval());
                                    note1.setDevoir(note.getDevoir());
                                    note1.setLibelle_discipline(note.getLibelle_discipline());
                                    note1.setNote(note.getNote());
                                    realm.copyToRealmOrUpdate(note1);
                                }
                            });
                        } catch (Exception e){
                            realm.close();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Veuillez vérifier votre connection internet", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getEmploi(String ien) {
        realm = Realm.getDefaultInstance();
        IMyAPI service = ApiClient3.getRetrofit().create(IMyAPI.class);
        service.getTemps(ien)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<Temps>>() {
                    @Override
                    public void onNext(List<Temps> temps) {
                        realm = Realm.getDefaultInstance();

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
                        realm.close();


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Veuillez vérifier votre connection internet", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        realm.close();
    }

    private void getInfos(String ien) {
        realm = Realm.getDefaultInstance();
        IMyAPI service = ApiClient3.getRetrofit().create(IMyAPI.class);
        service.getInfosEleves(ien)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<List<InfosEleves>>() {
                    @Override
                    public void onNext(List<InfosEleves> infos) {
                        realm = Realm.getDefaultInstance();

                        try{
                            realm = Realm.getDefaultInstance();
                            realm.executeTransaction(realm1 -> {
                                for (InfosEleves infosEleves: infos){
                                    InfosEleves info1 = new InfosEleves();
                                    info1.setIen(infosEleves.getIen());
                                    info1.setCode_classe(infosEleves.getCode_classe());
                                    info1.setCode_str(infosEleves.getCode_str());
                                    info1.setDate_naissance(infosEleves.getDate_naissance());
                                    info1.setLibelle_classe(infosEleves.getLibelle_classe());
                                    info1.setLibelle_structure(infosEleves.getLibelle_structure());
                                    info1.setLieu_naissance(infosEleves.getLieu_naissance());
                                    info1.setNom_eleve(infosEleves.getNom_eleve());
                                    info1.setPrenom_eleve(infosEleves.getPrenom_eleve());


                                    realm.copyToRealmOrUpdate(info1);
                                }
                            });
                        } catch (Exception e){
                            realm.close();
                        }



                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Veuillez vérifier votre connection internet", Toast.LENGTH_LONG).show();
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
            case R.id.absc_retard : i =
                    new Intent(this, AbscenceActivity.class);
                i.putExtra("ien_bis", ien);
                startActivity(i);
                break;
            default: break;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            assert cm != null;
            NetworkInfo netInfo = cm.getNetworkInfo(0);

            if (netInfo != null
                    && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null
                        && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }
}
