package com.blakelafleur.pyli.Activies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blakelafleur.pyli.Connections.Connection;
import com.blakelafleur.pyli.Connections.ConnectionStorage;
import com.blakelafleur.pyli.Fragments.MainActivityFragmentInteractionListener;
import com.blakelafleur.pyli.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, MainActivityFragmentInteractionListener {

    public static final String TAG = "PyLi";
    public static final int REQUEST_CODE = 0;
    private static Connection mConnection;
    private static ConnectionStorage mConnections;
    private ViewPager mViewPager;
    private Toolbar mToolbar;

    public static Connection getActiveConnection() {
        return mConnection;
    }

    public static ConnectionStorage getConnectionStorage() {
        return mConnections;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConnections = new ConnectionStorage(this.getApplicationContext());
        setContentView(R.layout.activity_main);

        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.mToolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Basic"));
        tabLayout.addTab(tabLayout.newTab().setText("Fade"));
        tabLayout.addTab(tabLayout.newTab().setText("Blink"));
        tabLayout.addTab(tabLayout.newTab().setText("Extras"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        this.mViewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        this.mViewPager.setAdapter(adapter);
        this.mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //We require an active connection. If there isn't one, prompt the user to select one.
        if (null == getActiveConnection()) {
            openConnectionList();
        }
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

        if (requestCode == REQUEST_CODE && intent != null) {
            String host = intent.getStringExtra("host");
            int id = intent.getIntExtra("connection_id", -1);
            mConnection = new Connection(id, host);
            mToolbar.setTitle(host);
        }
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
    public void onFragmentMessage(String TAG, Object data) {

    }
}
