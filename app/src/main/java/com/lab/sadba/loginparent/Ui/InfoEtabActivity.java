package com.lab.sadba.loginparent.Ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.sadba.loginparent.Adapter.EvalAdapter;
import com.lab.sadba.loginparent.Common.Common2;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.Model.Evaluation;
import com.lab.sadba.loginparent.Model.InfoEtab;
import com.lab.sadba.loginparent.Model.Note;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Remote.ApiCLient4;
import com.lab.sadba.loginparent.Remote.ApiClient3;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoEtabActivity extends AppCompatActivity {

    private Realm realm;
    TextView tel, cycle, etab;
    IMyAPI mService;
    android.support.v7.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_etab);

        mService = Common2.getAPI();
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        String ien = getIntent().getStringExtra("ien_bis");
        Enfant enfant = realm.where(Enfant.class).equalTo("ien_eleve", ien).findFirst();
        String code = enfant.getId_etablissement();

         toolbar =  findViewById(R.id.toolbar_emploi);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Your code
                finish();
            }
        });

        tel = findViewById(R.id.tel);
        cycle = findViewById(R.id.libelle_nom_cycle);
        etab = findViewById(R.id.libelle_etab);

        assert code != null;
        if (isNetworkAvailable(this)) {
            infos(code);
        }else {

            realm = Realm.getDefaultInstance();
            InfoEtab results = realm.where(InfoEtab.class)
                    .findFirst();

            tel.setText(results.getTel_chef_struct());
            cycle.setText(results.getLibelle_type_systeme_ens());
            etab.setText(results.getNom_struct());


        }
        realm.close();


    }

    @SuppressLint("CheckResult")
    private void infos(String code) {
        realm = Realm.getDefaultInstance();
        IMyAPI service = ApiCLient4.getRetrofit().create(IMyAPI.class);
        service.getInfos(code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<InfoEtab>() {
                    @Override
                    public void onNext(InfoEtab infoEtab) {
                        realm = Realm.getDefaultInstance();
                       // Toast.makeText(InfoEtabActivity.this, infoEtab.toString(), Toast.LENGTH_SHORT).show();

                        try{
                            realm = Realm.getDefaultInstance();
                            realm.executeTransaction(realm1 -> {
                                InfoEtab infoEtab1 = new InfoEtab();
                                infoEtab1.setCode(infoEtab.getCode());
                                infoEtab1.setCode_admin(infoEtab.getCode_admin());
                                infoEtab1.setLibelle_type_systeme_ens(infoEtab.getLibelle_type_systeme_ens());
                                infoEtab1.setMessage(infoEtab.getMessage());
                                infoEtab1.setNom_struct(infoEtab.getNom_struct());
                                infoEtab1.setTel_chef_struct(infoEtab.getTel_chef_struct());
                                infoEtab1.setTel_struct(infoEtab.getTel_struct());

                                realm.copyToRealmOrUpdate(infoEtab1);
                            });

                            tel.setText(infoEtab.getTel_chef_struct());
                            cycle.setText(infoEtab.getLibelle_type_systeme_ens());
                            etab.setText(infoEtab.getNom_struct());



                        } catch (Exception e){
                            realm.close();
                        }
                        realm.close();
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



