package com.lab.sadba.loginparent;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lab.sadba.loginparent.Adapter.EnseignantAdapter;
import com.lab.sadba.loginparent.Model.Enseignant;
import com.lab.sadba.loginparent.Remote.ApiClient3;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class EnseignantsActivity extends AppCompatActivity {

    private String ien;
    private Realm realm;
    private List<Enseignant> enseignants = new ArrayList<>();
    View view;
    private RecyclerView recycler_enseignants;

    private RealmResults<Enseignant> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enseignants);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ien = sharedPreferences.getString("ien_enfant", "");
        recycler_enseignants = findViewById(R.id.recycler_enseignants);
        getEnseignants(ien);

        realm = Realm.getDefaultInstance();
        results = realm.where(Enseignant.class).findAll();

        enseignants = realm.copyFromRealm(results);

        EnseignantAdapter adapter = new EnseignantAdapter(getApplicationContext(), enseignants);
        recycler_enseignants.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_enseignants.setHasFixedSize(true);
        recycler_enseignants.setAdapter(adapter);

    }

    private void getEnseignants(String ien) {
        IMyAPI service = ApiClient3.getRetrofit().create(IMyAPI.class);
        service.getEnseignants(ien)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<List<Enseignant>>() {
                    @Override
                    public void onNext(List<Enseignant> enseignants) {
                        // realm = Realm.getDefaultInstance();
                        //Toast.makeText(HomeActivity.this, String.valueOf(bulletins.size()), Toast.LENGTH_SHORT).show();

                        try{
                            realm = Realm.getDefaultInstance();
                            realm.executeTransaction(realm1 -> {
                                for (Enseignant enseignant: enseignants){
                                    Enseignant enseignant1 = new Enseignant();
                                    enseignant1.setId_enseignant(enseignant.getId_enseignant());
                                    enseignant1.setIen_enseignant(enseignant.getIen_enseignant());
                                    enseignant1.setNom_complet(enseignant.getNom_complet());
                                    enseignant1.setSpecialite(enseignant.getSpecialite());
                                    enseignant1.setId_enseignant(enseignant.getId_enseignant());


                                    realm.copyToRealmOrUpdate(enseignant1);
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
}
