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
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.sadba.loginparent.Model.Abscence;
import com.lab.sadba.loginparent.Model.Bulletin;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.Model.InfosEleves;
import com.lab.sadba.loginparent.Model.Note;
import com.lab.sadba.loginparent.Model.Retard;
import com.lab.sadba.loginparent.Model.Temps;
import com.lab.sadba.loginparent.Model.VerifUser;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Remote.ApiClient3;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private String ien;
    android.support.v7.widget.Toolbar toolbar;
    private Realm realm;
    private List<Temps> evals = new ArrayList<>();
    private List<Note> notes = new ArrayList<>();
    private List<Bulletin> bulletins = new ArrayList<>();
    private CardView tempsCard, notesCard, evalCard, infosCard, abscenceCard;
    TextView persoTitle, txt_name, txt_ien_parent;
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

        toolbar =  findViewById(R.id.toolbarHome);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbar);
        collapsingToolbar.setTitle("DASHBOARD");

        ien = getIntent().getStringExtra("ien_enfant");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ien_enfant", ien);
        editor.apply();
        //toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        //Drawer layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        txt_name = headerView.findViewById(R.id.txt_name_nav);
        txt_ien_parent = headerView.findViewById(R.id.txt_ien_nav);

        realm = Realm.getDefaultInstance();
        VerifUser verifUser = realm.where(VerifUser.class).findFirst();
        txt_name.setText(verifUser.getPrenom_parent() + " " + verifUser.getNom_parent());
        txt_ien_parent.setText(verifUser.getIen_parent());
        //SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       // String value = sharedPreferences1.getString("ien_Parent", "");
       // Toast.makeText(this, verifUser.getPrenom_parent(), Toast.LENGTH_SHORT).show();


        persoTitle = findViewById(R.id.prenom_toolbar);

        //Add Click Listener to the cards
        tempsCard.setOnClickListener(this);
        notesCard.setOnClickListener(this);
        evalCard.setOnClickListener(this);
        infosCard.setOnClickListener(this);
        abscenceCard.setOnClickListener(this);







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

        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Your code
                finish();
            }
        }); */






        Realm.init(getApplicationContext());
        assert ien != null;

        if (isNetworkAvailable(this)) {
            getEmploi(ien);
            getInfos(ien);
            getNotes(ien);
            getBulletins(ien);
            getAbscences(ien);
            getRetards(ien);

        }





    }

    private void getRetards(String ien) {
        //realm = Realm.getDefaultInstance();
        IMyAPI service = ApiClient3.getRetrofit().create(IMyAPI.class);
        service.getRetards(ien)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<List<Retard>>() {
                    @Override
                    public void onNext(List<Retard> retards) {
                        // realm = Realm.getDefaultInstance();
                        //Toast.makeText(HomeActivity.this, String.valueOf(bulletins.size()), Toast.LENGTH_SHORT).show();

                        try{
                            realm = Realm.getDefaultInstance();
                            realm.executeTransaction(realm1 -> {
                                for (Retard retard: retards){
                                    Retard retard1 = new Retard();
                                    retard1.setId_retard(retard.getId_retard());
                                    retard1.setDate_absence(retard.getDate_absence());
                                    retard1.setDiscipline(retard.getDiscipline());
                                    retard1.setDuree(retard.getDuree());
                                    retard1.setHeure_debut_cours(retard.getHeure_debut_cours());
                                    retard1.setJour(retard.getJour());
                                    retard1.setMotif(retard.getMotif());


                                    realm.copyToRealmOrUpdate(retard1);
                                }
                            });
                        } catch (Exception e){
                            //Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            realm.close();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Veuillez vérifier votre connection internet1", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getAbscences(String ien) {
        //realm = Realm.getDefaultInstance();
        IMyAPI service = ApiClient3.getRetrofit().create(IMyAPI.class);
        service.getAbscences(ien)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<List<Abscence>>() {
                    @Override
                    public void onNext(List<Abscence> abscences) {
                        // realm = Realm.getDefaultInstance();
                        //Toast.makeText(HomeActivity.this, String.valueOf(bulletins.size()), Toast.LENGTH_SHORT).show();

                        try{
                            realm = Realm.getDefaultInstance();
                            realm.executeTransaction(realm1 -> {
                                for (Abscence abscence: abscences){
                                    Abscence abscence1 = new Abscence();
                                    abscence1.setId_absence(abscence.getId_absence());
                                    abscence1.setDate_absence(abscence.getDate_absence());
                                    abscence1.setDiscipline(abscence.getDiscipline());
                                    abscence1.setHeure_d(abscence.getHeure_d());
                                    abscence1.setHeure_f(abscence.getHeure_f());
                                    abscence1.setJour(abscence.getJour());
                                    abscence1.setMotif(abscence.getMotif());


                                    realm.copyToRealmOrUpdate(abscence1);
                                }
                            });
                        } catch (Exception e){
                            //Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            realm.close();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Veuillez vérifier votre connection internet2", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
   // @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dossier_eleve) {
            // Handle the camera action
            Intent intent = new Intent(this, DossierActivity.class);
            startActivity(intent);
        } else if (id == R.id.profil_parent) {
            Intent intent = new Intent(this, DossierActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                        Toast.makeText(getApplicationContext(), "Veuillez vérifier votre connection internet3", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), "Veuillez vérifier votre connection internet4", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), "Veuillez vérifier votre connection internet5", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), "Veuillez vérifier votre connection internet6", Toast.LENGTH_LONG).show();
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
