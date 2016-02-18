package com.blakelafleur.pyli;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.blakelafleur.pyli.Connections.ConnectionStorage;
import com.blakelafleur.pyli.Fragments.BasicColorSettingFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, BasicColorSettingFragment.OnFragmentInteractionListener {

    public static final String TAG = "PyLi";
    public static final int REQUEST_CODE = 0;

    private ViewPager mViewPager;
    private static ConnectionStorage mConnections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConnections = new ConnectionStorage(this.getApplicationContext());
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

        openConnectionList();
    }

    private void openConnectionList() {
        Intent intent = new Intent(this, ConnectionHelperActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * Gets a list of connections set up by the user
     *
     * @return ArrayList<Connection>
     */
    public ArrayList<Connection> getConnections() {
        return mConnections.listConnections();
    }

    public static ConnectionStorage getConnectionStorage() { return mConnections; }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.connections:
                openConnectionList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        String host = intent.getStringExtra("host");
        Log.d(TAG, "Got "+host+" back from connection helper");
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
