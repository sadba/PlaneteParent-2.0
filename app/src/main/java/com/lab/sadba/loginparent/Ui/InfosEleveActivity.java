package com.lab.sadba.loginparent.Ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.lab.sadba.loginparent.Model.Evaluation;
import com.lab.sadba.loginparent.Model.InfosEleves;
import com.lab.sadba.loginparent.Remote.ApiClient3;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class InfosEleveActivity extends AppCompatActivity {


    private String value;
    private List<InfosEleves> infos = new ArrayList<>();
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        value = sharedPreferences.getString("ien_enfant", "");


        Realm.init(getApplicationContext());
        //getEvaluations(value);
        assert value != null;
        if (isNetworkAvailable(this)) {
            getInfos(value);
        } else {

            realm = Realm.getDefaultInstance();
            RealmResults<InfosEleves> results = realm.where(InfosEleves.class)
                    .findAll();
            // Toast.makeText(this, results.toString(), Toast.LENGTH_SHORT).show();
            infos = realm.copyFromRealm(results);

        }
    }

    private void getInfos(String value) {
        realm = Realm.getDefaultInstance();
        IMyAPI service = ApiClient3.getRetrofit().create(IMyAPI.class);
        service.getInfosEleves(value)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<List<InfosEleves>>() {
                    @Override
                    public void onNext(List<InfosEleves> infos) {
                        realm = Realm.getDefaultInstance();
                        //Toast.makeText(EvalActivity.this, String.valueOf(evals.size()), Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
