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
import android.widget.Toast;

import com.lab.sadba.loginparent.Adapter.NoteAdapter;
import com.lab.sadba.loginparent.Model.Enfant;
import com.lab.sadba.loginparent.Model.Note;
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
public class DevoirFragment extends Fragment {


    private RecyclerView recycler_devoir;
    private String value;
    private Realm realm;

    View view;

    public DevoirFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        value = sharedPreferences.getString("ien_enfant", "");
        //Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_devoir, container, false);
        recycler_devoir =  view.findViewById(R.id.recycler_devoir);

        initUi();
        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
        IMyAPI api = ApiClient2.getInstance()
                .getIMyAPI();

        Enfant enfant = realm.where(Enfant.class).findFirst();

        Observable<List<Note>> dbObservable = Observable.create(e -> getDBNote());

        if (isNetworkAvailable(getActivity())){
            api.getNotes("MA1445001","devoir")
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .map(notes -> {
                        Realm realm = Realm.getDefaultInstance();

                        List<Note> results = realm.where(Note.class).findAll();
                        List<String> id_note_exist = new ArrayList<>();
                        for (Note n: results) {
                            id_note_exist.add(n.getId_note());
                        }
                        for (Note n2: notes){
                            if (!id_note_exist.contains(n2.getId_note())){
                                realm.executeTransaction(trRealm->trRealm.copyToRealm(n2));
                                Log.d("ooo",realm.where(Note.class).findAll().size()+"");
                            }
                        }

                        return notes;
                    })
                    .mergeWith(dbObservable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::setAdapterData);
        } else {

            setAdapterData(getDBNote());
        }

        return view;
    }

    private List<Note> getDBNote(){
        return realm.copyFromRealm(realm.where(Note.class).equalTo("devoir", "Devoir").findAll());

    }

    private void initUi(){
        recycler_devoir = view.findViewById(R.id.recycler_devoir);
    }

    void setAdapterData(List<Note> notes){
        NoteAdapter adapter = new NoteAdapter(getActivity(),notes);
        recycler_devoir.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recycler_lundi.setItemAnimator();
        recycler_devoir.setAdapter(adapter);
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
