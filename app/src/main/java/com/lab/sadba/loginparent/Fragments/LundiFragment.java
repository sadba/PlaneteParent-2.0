package com.lab.sadba.loginparent.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.sadba.loginparent.Adapter.TempsAdapter;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.Model.InfosEleves;
import com.lab.sadba.loginparent.Model.Note;
import com.lab.sadba.loginparent.Model.Temps;
import com.lab.sadba.loginparent.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.annotations.Nullable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class LundiFragment extends Fragment {

    private RecyclerView recycler_lundi;
    private String value;
    private List<Temps> temps = new ArrayList<>();
    private Realm realm;
    private RealmResults<Temps> results;
    private TextView visible;

    View view;

    public LundiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        realm = Realm.getDefaultInstance();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        value = sharedPreferences.getString("ien_enfant", "");

        InfosEleves infosEleves = realm.where(InfosEleves.class).equalTo("ien" ,value).findFirst();
        String code_classe = infosEleves.getCode_classe();

        realm = Realm.getDefaultInstance();
        results = realm.where(Temps.class)
                .equalTo("num_jour", "1")
                .equalTo("code_classe", code_classe)
                .findAllAsync();


        temps = realm.copyFromRealm(results);

        realm.close();


    }

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_lundi, container, false);
        recycler_lundi = view.findViewById(R.id.recycler_temps);

        TempsAdapter adapter = new TempsAdapter(getContext(), temps);
        recycler_lundi.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_lundi.setHasFixedSize(true);
        //recycler_lundi.setItemAnimator(getContext());
        recycler_lundi.setAdapter(adapter);
        return view;

    }

}
