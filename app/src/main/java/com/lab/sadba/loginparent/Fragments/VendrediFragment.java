package com.lab.sadba.loginparent.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lab.sadba.loginparent.Adapter.TempsAdapter;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.Model.Temps;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Remote.ApiClient2;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendrediFragment extends Fragment {


    private RecyclerView recycler_vendredi;
    private String value;
    private Realm realm;

    View view;

    public VendrediFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        value = sharedPreferences.getString("ien_Parent", "");
        //Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lundi, container, false);
        recycler_vendredi =  view.findViewById(R.id.recycler_temps);

        initUi();
        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
        IMyAPI api = ApiClient2.getInstance()
                .getIMyAPI();

        Enfant enfant = realm.where(Enfant.class).findFirst();

        Observable<List<Temps>> dbObservable = Observable.create(e -> getDBTemps());

        if (isNetworkAvailable(getActivity())){
            api.getTemps(value,"vendredi")
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .map(temp -> {
                        Realm realm = Realm.getDefaultInstance();

                        List<Temps> results = realm.where(Temps.class).findAll();
                        List<String> id_temps_exist = new ArrayList<>();
                        for (Temps tven: results) {
                            id_temps_exist.add(tven.getNum_jour());
                        }
                        for (Temps tvendredi: temp ){
                            if (!id_temps_exist.contains(tvendredi.getNum_jour())){
                                realm.executeTransaction(trRealm->trRealm.copyToRealmOrUpdate(tvendredi));
                                Log.d("ooo",realm.where(Temps.class).findAll().size()+"");
                            }
                        }

                        return temp;
                    })
                    .mergeWith(dbObservable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::setAdapterData);
        } else {

            setAdapterData(getDBTemps());
        }

        return view;
    }

    private List<Temps> getDBTemps(){
        return realm.copyFromRealm(realm.where(Temps.class).equalTo("num_jour", "5").findAll());

    }

    private void initUi(){
        recycler_vendredi = view.findViewById(R.id.recycler_temps);
    }

    void setAdapterData(List<Temps> temps){
        //RealmResults<Temps> results = realm.where(Temps.class).findAll();

        TempsAdapter adapter = new TempsAdapter(getActivity(),temps);
        recycler_vendredi.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recycler_lundi.setItemAnimator();
        recycler_vendredi.setAdapter(adapter);
    }

    private boolean isNetworkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return  info!=null && info.isConnected();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }


}
