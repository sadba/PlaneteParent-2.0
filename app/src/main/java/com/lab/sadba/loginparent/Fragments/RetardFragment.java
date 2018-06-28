package com.lab.sadba.loginparent.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lab.sadba.loginparent.Adapter.RetardAdapter;
import com.lab.sadba.loginparent.Model.Retard;
import com.lab.sadba.loginparent.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetardFragment extends Fragment {

    private RecyclerView recycler_retard;
    private List<Retard> retards = new ArrayList<>();
    private Realm realm;
    private RealmResults<Retard> results;
    View view;


    public RetardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);
        realm = Realm.getDefaultInstance();
        results = realm.where(Retard.class).findAll();
        retards = realm.copyFromRealm(results);
        realm.close();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_retard, container, false);

        recycler_retard = view.findViewById(R.id.recycler_retard);

        RetardAdapter adapter = new RetardAdapter(getContext(), retards);
        recycler_retard.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_retard.setHasFixedSize(true);
        //recycler_lundi.setItemAnimator(getContext());
        recycler_retard.setAdapter(adapter);
        return view;
    }

}
