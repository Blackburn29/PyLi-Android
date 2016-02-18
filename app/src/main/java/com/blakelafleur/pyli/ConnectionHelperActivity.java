package com.blakelafleur.pyli;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ConnectionHelperActivity extends AppCompatActivity implements OnConnectionAddedInterface, AdapterView.OnItemClickListener {

    public static final int RESULT_CODE = 1;

    private Pattern hostPattern;
    private OnConnectionAddedInterface createdCallback;
    private ArrayList<Connection> mConnections;
    private ConnectionListAdapter mConnectionAdapter;
    private ListView mConnectionList;

    public ConnectionHelperActivity() {
        this.mConnections = MainActivity.getConnectionStorage().listConnections();
        Log.d(MainActivity.TAG, mConnections.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hostPattern = Pattern.compile("^[a-z\\.\\-0-9]+:[0-9]+$");
        setContentView(R.layout.activity_connection_helper);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mConnectionList = (ListView)findViewById(R.id.connection_list);
        this.mConnectionAdapter = new ConnectionListAdapter(this, mConnections);
        mConnectionList.setOnItemClickListener(this);
        this.mConnectionList.setAdapter(mConnectionAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.connections_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_create_connection:
                Dialog dialog = showCreateConnectionDialog();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Dialog showCreateConnectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.create_connection, null);

        builder.setView(view);
        builder.setTitle(R.string.new_connection_title);
        builder.setMessage(R.string.new_connection_text);

        final EditText nameField = (EditText)view.findViewById(R.id.connection);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameField.getText().toString();
                if (!hostPattern.matcher(name).matches()) {
                    Toast.makeText(getApplicationContext(), "Invalid hostname. Please try again", Toast.LENGTH_LONG).show();
                } else {
                    Connection c = new Connection(0, name);
                    MainActivity.getConnectionStorage().addConnection(c);
                    Toast.makeText(getApplicationContext(), "Connection created successfully", Toast.LENGTH_LONG).show();
                    onConnectionAdded();
                }
            }
        });

        builder.setNegativeButton(R.string.cancel, null);

        return builder.create();
    }

    @Override
    public void onConnectionAdded() {
        this.mConnections = MainActivity.getConnectionStorage().listConnections();
        this.mConnectionAdapter.setConnections(this.mConnections);
        this.mConnectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Connection c = (Connection)parent.getAdapter().getItem(position);
        Intent intent = new Intent();
        intent.putExtra("host", c.getHost());
        setResult(RESULT_CODE, intent);
        finish();
    }
}
