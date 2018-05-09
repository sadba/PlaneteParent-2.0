package com.lab.sadba.loginparent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.sadba.loginparent.Common.Common;
import com.lab.sadba.loginparent.Common.Common2;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.Model.InfoEtab;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoEtabActivity extends AppCompatActivity {

    private Realm realm;
    TextView tel, cycle, etab;
    IMyAPI mService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_etab);

        //Init Service
        mService = Common2.getAPI();

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        Enfant enfant = realm.where(Enfant.class).findFirst();
        String code = enfant.getId_etablissement();


        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String code_etab = preferences.getString("code_etab", "");*/
        Toast.makeText(this, code, Toast.LENGTH_SHORT).show();

        tel = findViewById(R.id.tel);
        cycle = findViewById(R.id.libelle_nom_cycle);
        etab = findViewById(R.id.libelle_etab);

        mService.getInfos(code)
                .enqueue(new Callback<InfoEtab>() {
                    @Override
                    public void onResponse(Call<InfoEtab> call, Response<InfoEtab> response) {

                       /* InfoEtab info = null;
                        if (response.code() == 200) {
                            info = new InfoEtab();
                            realm = Realm.getDefaultInstance();
                            InfoEtab inf = realm.where(InfoEtab.class).findFirst();
                            tel.setText(inf.getTel_chef_struct());
                            cycle.setText(inf.getLibelle_type_systeme_ens());
                            etab.setText(inf.getNom_struct());
                        }


                            *//*tel.setText(result.getTel_chef_struct());
                            cycle.setText(result.getLibelle_type_systeme_ens());
                            etab.setText(result.getNom_struct());*//*

                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(info);
                        realm.commitTransaction();
                    }
*/
                    }
                    @Override
                    public void onFailure(Call<InfoEtab> call, Throwable t) {
                        Toast.makeText(InfoEtabActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //getInfosEtab(code);


    }
}

   /* private void getInfosEtab(String code) {
        mService.getInfos(code)
                .enqueue(new Callback<InfoEtab>() {
                    @Override
                    public void onResponse(Call<InfoEtab> call, Response<InfoEtab> response) {


                        realm = Realm.getDefaultInstance();
                        InfoEtab inf = realm.where(InfoEtab.class).findFirst();
                        tel.setText(inf.getTel_chef_struct());
                        cycle.setText(inf.getLibelle_type_systeme_ens());
                        etab.setText(inf.getNom_struct());

                            *//*tel.setText(result.getTel_chef_struct());
                            cycle.setText(result.getLibelle_type_systeme_ens());
                            etab.setText(result.getNom_struct());*//*

                           *//* realm.beginTransaction();
                            realm.copyToRealmOrUpdate(infoEtab);
                            realm.commitTransaction();*//*
                        }


                    @Override
                    public void onFailure(Call<InfoEtab> call, Throwable t) {
                        Toast.makeText(InfoEtabActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/

