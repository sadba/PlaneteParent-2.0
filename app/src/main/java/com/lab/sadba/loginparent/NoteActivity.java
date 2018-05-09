
package com.lab.sadba.loginparent;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

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

public class NoteActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);

        toolbar =  findViewById(R.id.toolbarNote);
        toolbar.setTitle("NOTES ET BULLETINS");


        Bundle bundle = new Bundle();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), bundle, this);
        adapter.AddFragment(new DevoirFragment(), "Devoirs");
        adapter.AddFragment(new CompoFragment(), "Compositions");
        adapter.AddFragment(new BulletinFragment(), "Bulletins");

        //adapter Setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
