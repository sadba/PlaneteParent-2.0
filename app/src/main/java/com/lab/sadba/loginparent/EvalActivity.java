package com.lab.sadba.loginparent;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.lab.sadba.loginparent.Adapter.EvalAdapter;
import com.lab.sadba.loginparent.Adapter.TempsAdapter;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.Model.Evaluation;
import com.lab.sadba.loginparent.Model.Temps;
import com.lab.sadba.loginparent.Model.User;
import com.lab.sadba.loginparent.Remote.ApiClient2;
import com.lab.sadba.loginparent.Remote.ApiClient3;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class EvalActivity extends AppCompatActivity {

    private RecyclerView recyclerVIewEval;
    private String value;
    private List<Evaluation> evals = new ArrayList<>();
    private Realm realm;
    private String ien;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval);
        recyclerVIewEval = findViewById(R.id.recycler_eval);

       /* Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        IMyAPI api = ApiClient2.getInstance()
                .getIMyAPI();*/


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        value = sharedPreferences.getString("ien_enfant", "");

        //Enfant enfant = realm.where(Enfant.class).findFirst();



        //getEvaluations(value);
        assert value != null;
        if (isNetworkAvailable(this)) {
            getEvaluations(value);
        }else {

            realm = Realm.getDefaultInstance();
            RealmResults<Evaluation> results = realm.where(Evaluation.class)
                    .findAll();
            Toast.makeText(this, results.toString(), Toast.LENGTH_SHORT).show();
            evals = realm.copyFromRealm(results);

            EvalAdapter adapter = new EvalAdapter(getApplicationContext(), evals);
            recyclerVIewEval.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerVIewEval.setItemAnimator(new DefaultItemAnimator());
            //recycler_lundi.setItemAnimator();
            recyclerVIewEval.setAdapter(adapter);
        }
}

    private void getEvaluations(String value) {
        realm = Realm.getDefaultInstance();
        IMyAPI service = ApiClient3.getRetrofit().create(IMyAPI.class);
        service.getEval(value)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<List<Evaluation>>() {
                    @Override
                    public void onNext(List<Evaluation> evals) {
                        realm = Realm.getDefaultInstance();
                        //Toast.makeText(EvalActivity.this, String.valueOf(evals.size()), Toast.LENGTH_SHORT).show();

                        try{
                            realm = Realm.getDefaultInstance();
                            realm.executeTransaction(realm1 -> {
                                for (Evaluation eval: evals){
                                    Evaluation eval1 = new Evaluation();
                                    eval1.setId_eval(eval.getId_eval());
                                    eval1.setDate_eval(eval.getDate_eval());
                                    eval1.setLibelle_categorie_eval(eval.getLibelle_categorie_eval());
                                    eval1.setLibelle_discipline(eval.getLibelle_discipline());
                                    eval1.setLibelle_periode_eval(eval.getLibelle_periode_eval());
                                    eval1.setLibelle_type_evaluation(eval.getLibelle_type_evaluation());
                                    realm.copyToRealmOrUpdate(eval1);
                                }
                            });
                        } catch (Exception e){
                            realm.close();
                        }
                        EvalAdapter adapter = new EvalAdapter(getApplicationContext(), evals);
                        recyclerVIewEval.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerVIewEval.setItemAnimator(new DefaultItemAnimator());
                        //recycler_lundi.setItemAnimator();
                        recyclerVIewEval.setAdapter(adapter);
                        //Toast.makeText(EvalActivity.this, evals.toString(), Toast.LENGTH_SHORT).show();
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
