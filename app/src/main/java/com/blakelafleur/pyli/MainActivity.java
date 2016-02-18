package com.blakelafleur.pyli;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blakelafleur.pyli.Connections.ConnectionStorage;
import com.blakelafleur.pyli.Fragments.BasicColorSettingFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, BasicColorSettingFragment.OnFragmentInteractionListener {

    public static final String TAG = "PyLi";

    private ViewPager mViewPager;
    private ConnectionStorage mConnections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mConnections = new ConnectionStorage(this.getApplicationContext());
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Basic"));
        tabLayout.addTab(tabLayout.newTab().setText("Effects"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        this.mViewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        this.mViewPager.setAdapter(adapter);
        this.mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);

        if (this.getConnections().size() < 1) {
            Intent intent = new Intent(this, ConnectionHelperActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Gets a list of connections set up by the user
     *
     * @return ArrayList<Connection>
     */
    public ArrayList<Connection> getConnections() {
        return this.mConnections.listConnections();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        this.mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
