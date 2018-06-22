package com.lab.sadba.loginparent.Fragments;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lab.sadba.loginparent.Adapter.BulletinAdapter;
import com.lab.sadba.loginparent.Model.Bulletin;
import com.lab.sadba.loginparent.Model.Note;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Remote.ApiClient3;
import com.lab.sadba.loginparent.Remote.IMyAPI;
import com.lab.sadba.loginparent.Ui.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class BulletinFragment extends Fragment {

    private RecyclerView recycler_bulletin;
    private String ien;
    private Realm realm;
    private RealmResults<Bulletin> results;
    private List<Bulletin> bulletins = new ArrayList<>();
    private TextView visible;

    View view;

    //RxJava
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    IMyAPI mService;


    public BulletinFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        realm = Realm.getDefaultInstance();
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        //value = sharedPreferences.getString("ien_enfant", "");



       realm = Realm.getDefaultInstance();
        results = realm.where(Bulletin.class)
                .findAll();


        bulletins = realm.copyFromRealm(results);
        //Toast.makeText(getContext(), bulletins.toString(), Toast.LENGTH_SHORT).show();

        realm.close();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        ien = sharedPreferences.getString("ien_enfant", "");

       // getBulletins(ien);




    }




    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_bulletin, container, false);


        view = inflater.inflate(R.layout.fragment_bulletin, container, false);
        recycler_bulletin = view.findViewById(R.id.recycler_bulletin);

       // getBulletins(ien);


        BulletinAdapter adapter = new BulletinAdapter(getContext(), bulletins);
        recycler_bulletin.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_bulletin.setHasFixedSize(true);
        //recycler_lundi.setItemAnimator(getContext());
        recycler_bulletin.setAdapter(adapter);
        return view;

    }



}
