package com.lab.sadba.loginparent.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lab.sadba.loginparent.Adapter.AbscenceAdapter;
import com.lab.sadba.loginparent.Model.Abscence;
import com.lab.sadba.loginparent.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class AbscenceFragment extends Fragment {

    private RecyclerView recycler_abscence;
    private List<Abscence> abscences = new ArrayList<>();
    private Realm realm;
    private RealmResults<Abscence> results;
    View view;


    public AbscenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        realm = Realm.getDefaultInstance();
        results = realm.where(Abscence.class).findAll();
        abscences = realm.copyFromRealm(results);
        realm.close();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_abscence, container, false);

        recycler_abscence = view.findViewById(R.id.recycler_abscence);

        AbscenceAdapter adapter = new AbscenceAdapter(getContext(), abscences);
        recycler_abscence.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_abscence.setHasFixedSize(true);
        //recycler_lundi.setItemAnimator(getContext());
        recycler_abscence.setAdapter(adapter);
        return view;

    }

}
