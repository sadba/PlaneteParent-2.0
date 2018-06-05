
package com.lab.sadba.loginparent.Ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.lab.sadba.loginparent.Adapter.EvalAdapter;
import com.lab.sadba.loginparent.Adapter.NoteAdapter;
import com.lab.sadba.loginparent.Adapter.ViewPagerAdapter;
import com.lab.sadba.loginparent.Fragments.BulletinFragment;
import com.lab.sadba.loginparent.Fragments.CompoFragment;
import com.lab.sadba.loginparent.Fragments.DevoirFragment;
import com.lab.sadba.loginparent.Fragments.JeudiFragment;
import com.lab.sadba.loginparent.Fragments.LundiFragment;
import com.lab.sadba.loginparent.Fragments.MardiFragment;
import com.lab.sadba.loginparent.Fragments.MercrediFragment;
import com.lab.sadba.loginparent.Fragments.SamediFragment;
import com.lab.sadba.loginparent.Fragments.VendrediFragment;
import com.lab.sadba.loginparent.Model.Evaluation;
import com.lab.sadba.loginparent.Model.Note;
import com.lab.sadba.loginparent.R;
import com.lab.sadba.loginparent.Remote.ApiClient3;
import com.lab.sadba.loginparent.Remote.IMyAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class NoteActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Note> notes = new ArrayList<>();
    private Realm realm;
    private String ien_note;
    private RecyclerView recycler_devoir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);

        toolbar = findViewById(R.id.toolbarNote);
        toolbar.setTitle("NOTES ET BULLETINS");


        Bundle bundle = new Bundle();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), bundle, this);
        adapter.AddFragment(new DevoirFragment(), "Devoirs");
        adapter.AddFragment(new CompoFragment(), "Compositions");
        adapter.AddFragment(new BulletinFragment(), "Bulletins");

        //adapter Setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        setUpViewPager(viewPager);


    }



    private void setUpViewPager(final ViewPager viewPager) {
        // Enfant enfant = new Enfant();

        //ien_note = getIntent().getStringExtra("ien_bis");
        //Toast.makeText(this, ien_bis, Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        //bundle.putString("ien_enfant", ien_note);

        DevoirFragment devoirFragment = new DevoirFragment();

        //Bundle bundle = new Bundle();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), bundle, this);
        adapter.AddFragment(new DevoirFragment(), "Devoirs");
        adapter.AddFragment(new CompoFragment(), "Compositions");
        adapter.AddFragment(new BulletinFragment(), "Bulletins");

        //adapter Setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        devoirFragment.setArguments(bundle);
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
