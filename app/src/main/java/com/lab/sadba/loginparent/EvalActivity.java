package com.lab.sadba.loginparent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lab.sadba.loginparent.Adapter.EvalAdapter;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.Model.Evaluation;
import com.lab.sadba.loginparent.Model.User;
import com.lab.sadba.loginparent.Remote.ApiClient2;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class EvalActivity extends AppCompatActivity {

    private RecyclerView recyclerVIewEval;
    private Realm realm;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval);
        initUi();
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        IMyAPI api = ApiClient2.getInstance()
                .getIMyAPI();

        Enfant enfant = realm.where(Enfant.class).findFirst();

        Observable<List<Evaluation>> dbObservable = Observable.create(e -> getDBEval());

        if (isNetworkAvailable()){
            api.getEval("MA1445001")
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .map(eval -> {
                        Realm realm = Realm.getDefaultInstance();
                        realm.executeTransaction(trRealm->trRealm.copyToRealm(eval));
                        Log.d("ooo",realm.where(Evaluation.class).findAll().size()+"");
                        return eval;
                    })
                    .mergeWith(dbObservable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response->setAdapterData(response));
        } else{
            setAdapterData(getDBEval());
        }
    }

    private List<Evaluation> getDBEval(){
        return realm.copyFromRealm(realm.where(Evaluation.class).findAll());
    }

    private void initUi(){
        recyclerVIewEval = findViewById(R.id.recycler_eval);
    }

    void setAdapterData(List<Evaluation> eval){
        EvalAdapter adapter = new EvalAdapter(getApplicationContext(), eval);
        recyclerVIewEval.setLayoutManager(new LinearLayoutManager(this));
        recyclerVIewEval.setItemAnimator(new DefaultItemAnimator());
        recyclerVIewEval.setAdapter(adapter);
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
