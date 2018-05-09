package com.lab.sadba.loginparent;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.lab.sadba.loginparent.Adapter.ViewPagerAdapter;
import com.lab.sadba.loginparent.Fragments.JeudiFragment;
import com.lab.sadba.loginparent.Fragments.LundiFragment;
import com.lab.sadba.loginparent.Fragments.MardiFragment;
import com.lab.sadba.loginparent.Fragments.MercrediFragment;
import com.lab.sadba.loginparent.Fragments.SamediFragment;
import com.lab.sadba.loginparent.Fragments.VendrediFragment;
import com.lab.sadba.loginparent.Model.Enfant;

public class EmploiActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String ien_bis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emploi);

        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);

        toolbar =  findViewById(R.id.toolbarEmploi);
        toolbar.setTitle("EMPLOI DU TEMPS");



       setUpViewPager(viewPager);



    }

    private void setUpViewPager(final ViewPager viewPager){
       // Enfant enfant = new Enfant();

        ien_bis = getIntent().getStringExtra("ien_bis");
        //Toast.makeText(this, ien_bis, Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("ien_enfant", ien_bis);

        LundiFragment lundiFragment = new LundiFragment();
        MardiFragment mardiFragment = new MardiFragment();
        MercrediFragment mercrediFragment = new MercrediFragment();
        JeudiFragment jeudiFragment = new JeudiFragment();
        VendrediFragment vendrediFragment = new VendrediFragment();
        SamediFragment samediFragment = new SamediFragment();



        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), bundle, this);
        adapter.AddFragment(lundiFragment, "LUN");
        adapter.AddFragment(new MardiFragment(), "MAR");
        adapter.AddFragment(new MercrediFragment(), "MER");
        adapter.AddFragment(new JeudiFragment(), "JEU");
        adapter.AddFragment(new VendrediFragment(), "VEN");
        adapter.AddFragment(new SamediFragment(), "SAM");
        //adapter Setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        lundiFragment.setArguments(bundle);
       // return(lundiFragment);

    }
}
