package com.lab.sadba.loginparent.Ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.sadba.loginparent.Common.Common2;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.Model.InfoEtab;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import io.realm.Realm;
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

        mService = Common2.getAPI();
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        String ien = getIntent().getStringExtra("ien_bis");
        Enfant enfant = realm.where(Enfant.class).equalTo("ien_eleve", ien).findFirst();
        String code = enfant.getId_etablissement();

        tel = findViewById(R.id.tel);
        cycle = findViewById(R.id.libelle_nom_cycle);
        etab = findViewById(R.id.libelle_etab);

        infos(code);
        realm.close();


    }

    private void infos(String code) {
        mService.getInfos(code)
                .enqueue(new Callback<InfoEtab>() {
                    @Override
                    public void onResponse(Call<InfoEtab> call, Response<InfoEtab> response) {
                        Toast.makeText(InfoEtabActivity.this, code, Toast.LENGTH_SHORT).show();

                        realm = Realm.getDefaultInstance();
                        if (response.body() != null) {
                            //InfoEtab infoEtab = new InfoEtab();
                            etab.setText(response.body().getNom_struct());
                            cycle.setText(response.body().getLibelle_type_systeme_ens());
                            tel.setText(response.body().getTel_chef_struct());
                        }
                        //realm.copyToRealm(InfoEtab);

                    }

                    @Override
                    public void onFailure(Call<InfoEtab> call, Throwable t) {

                    }
                });
    }
}



