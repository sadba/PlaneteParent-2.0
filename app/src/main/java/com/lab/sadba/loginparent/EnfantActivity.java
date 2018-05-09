package com.lab.sadba.loginparent;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.lab.sadba.loginparent.Adapter.EnfantAdapter;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.Model.User;
import com.lab.sadba.loginparent.Remote.ApiClient;
import com.lab.sadba.loginparent.Remote.ApiUtils;
import com.lab.sadba.loginparent.Remote.IMyAPI;
import com.lab.sadba.loginparent.Remote.RetrofitClient;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnfantActivity extends AppCompatActivity {

    private RecyclerView recyclerEnfant;
    private Realm realm;
    ProgressDialog progressDoalog;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enfant);
        progressDoalog = new ProgressDialog(EnfantActivity.this);
        progressDoalog.setMessage("Chargement des donnees...");
        progressDoalog.show();
        initUI();
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        IMyAPI api = ApiClient.getInstance()
                .getIMyAPI();

        User user = realm.where(User.class).findFirst();

        Enfant enf = new Enfant();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ien_Enfant", enf.getIen_eleve());
        editor.putString("code_etab", enf.getId_etablissement());
        editor.apply();

        Observable<List<Enfant>> dbObservable =  Observable.create(e -> getDBEnfants());

        if (isNetworkAvailable()){
            api.getEnfants(user.getIen())
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
        EnfantAdapter adapter = new EnfantAdapter(getApplicationContext(), enfants);
        recyclerEnfant.setLayoutManager(new LinearLayoutManager(this));
        recyclerEnfant.setItemAnimator(new DefaultItemAnimator());
        recyclerEnfant.setAdapter(adapter);
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return  info!=null && info.isConnected();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
