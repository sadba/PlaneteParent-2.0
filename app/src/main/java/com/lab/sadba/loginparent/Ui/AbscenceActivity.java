package com.lab.sadba.loginparent.Ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lab.sadba.loginparent.Adapter.ViewPagerAdapter;
import com.lab.sadba.loginparent.Fragments.AbscenceFragment;
import com.lab.sadba.loginparent.Fragments.RetardFragment;
import com.lab.sadba.loginparent.R;

public class AbscenceActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abscence);

        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);

        toolbar =  findViewById(R.id.toolbar_emploi);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);


        toolbar.setTitle("Abscences et Retards");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Your code
                finish();
            }
        });

        setUpViewPager(viewPager);
    }

    private void setUpViewPager(final ViewPager viewPager){


        Bundle bundle = new Bundle();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), bundle, this);

        //ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), bundle, this);
        adapter.AddFragment(new AbscenceFragment(), "ABSCENCES");
        adapter.AddFragment(new RetardFragment(), "RETARDS");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
