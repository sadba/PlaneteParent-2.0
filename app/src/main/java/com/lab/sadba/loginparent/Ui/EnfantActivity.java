package com.lab.sadba.loginparent.Ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lab.sadba.loginparent.Adapter.EnfantAdapter;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.Model.User;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Remote.ApiClient;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class EnfantActivity extends AppCompatActivity {

    private RecyclerView recyclerEnfant;
    private Realm realm;
    ProgressDialog progressDoalog;
    android.support.v7.widget.Toolbar toolbar;
    SharedPreferences sp;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater menuInflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        sp = getSharedPreferences("btn_login", MODE_PRIVATE);
        if (sp.getBoolean("logged", true)){
            goToMainActivity();
        }

        int id = item.getItemId();

        if (id == R.id.action_logout) {
            sp.edit().putBoolean("logged", false).apply();
            SharedPreferences.Editor editor = sp.edit();
            editor.remove("logged");
            editor.commit();
            finish();
            //Intent intent = new Intent(EnfantActivity.this, MainActivity.class);
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        //close(view);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enfant);

        toolbar =  findViewById(R.id.toolbar_emploi);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Liste des enfants");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Your code
                finish();
            }
        });


        progressDoalog = new ProgressDialog(EnfantActivity.this);
        progressDoalog.setMessage("Chargement des donnees...");
        progressDoalog.show();
        initUI();
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        IMyAPI api = ApiClient.getInstance()
                .getIMyAPI();

        User user = realm.where(User.class).findFirst();
        Toast.makeText(this, user.getIen(), Toast.LENGTH_SHORT).show();

        Enfant enf = new Enfant();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ien_enfant", enf.getIen_eleve());
        editor.putString("code_etab", enf.getId_etablissement());
        editor.apply();

        Observable<List<Enfant>> dbObservable =  Observable.create(e -> getDBEnfants());

        if (isNetworkAvailable(getApplicationContext())){
            api.getEnfants("W6WDBX5Q")
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .map(enfants ->{
                        Realm realm = Realm.getDefaultInstance();
                        List<Enfant> results = realm.where(Enfant.class).findAll();
                        List<String> ien_eleve = new ArrayList<>();
                        for (Enfant t: results) {
                            ien_eleve.add(t.getIen_eleve());
                        }
                        for (Enfant t2: enfants) {
                            if (!ien_eleve.contains(t2.getIen_eleve())){
                                realm.executeTransaction(trRealm->trRealm.copyToRealmOrUpdate(enfants));
                                Log.d("ooo",realm.where(Enfant.class).findAll().size()+"");
                            }

                        }
                        return enfants;



            })
                    .mergeWith(dbObservable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response->setAdapterData(response));
        } else {
            setAdapterData(getDBEnfants());
        }


    }



    private List<Enfant> getDBEnfants(){
        return realm.copyFromRealm(realm.where(Enfant.class).findAll());
    }

    private void initUI(){
        recyclerEnfant = findViewById(R.id.recycler_enfant);

    }

    void setAdapterData(List<Enfant> enfants){
        progressDoalog.dismiss();
        EnfantAdapter adapter = new EnfantAdapter(EnfantActivity.this, enfants);
        recyclerEnfant.setLayoutManager(new LinearLayoutManager(this));
        recyclerEnfant.setItemAnimator(new DefaultItemAnimator());
        recyclerEnfant.setAdapter(adapter);
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
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
