package com.lab.sadba.loginparent.Ui;

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
import android.view.View;
import android.widget.Toast;

import com.lab.sadba.loginparent.Adapter.EvalAdapter;
import com.lab.sadba.loginparent.Model.Evaluation;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Remote.ApiClient3;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class EvalActivity extends AppCompatActivity {

    private RecyclerView recyclerVIewEval;
    android.support.v7.widget.Toolbar toolbar;
    private String value;
    private List<Evaluation> evals = new ArrayList<>();
    private Realm realm;
    private String ien;
    ProgressDialog progressDoalog;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval);

        progressDoalog = new ProgressDialog(EvalActivity.this);
        progressDoalog.setMessage("Chargement des donnees...");
        progressDoalog.show();

        recyclerVIewEval = findViewById(R.id.recycler_eval);

        toolbar =  findViewById(R.id.toolbar_eval);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);


        toolbar.setTitle("Evaluations Programmées");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Your code
                finish();
            }
        });


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
           // Toast.makeText(this, results.toString(), Toast.LENGTH_SHORT).show();
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

                                    progressDoalog.dismiss();
                                }
                            });
                        } catch (Exception e){
                            realm.close();
                        }
                        if (evals.isEmpty()){
                            progressDoalog.dismiss();
                            Toast.makeText(EvalActivity.this, "Pas d'evaluations programmees pour le moment", Toast.LENGTH_LONG).show();
                        } else{
                            EvalAdapter adapter = new EvalAdapter(getApplicationContext(), evals);
                            recyclerVIewEval.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerVIewEval.setItemAnimator(new DefaultItemAnimator());
                            //recycler_lundi.setItemAnimator();
                            recyclerVIewEval.setAdapter(adapter);
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
