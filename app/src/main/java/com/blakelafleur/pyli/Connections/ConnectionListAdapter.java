package com.blakelafleur.pyli.Connections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blakelafleur.pyli.R;

import java.util.ArrayList;

/**
 * Created by Blake on 2/17/2016.
 */
public class ConnectionListAdapter extends BaseAdapter {

    private ArrayList<Connection> mConnections;
    private LayoutInflater mInflater;

    public ConnectionListAdapter(Context c, ArrayList<Connection> conn) {
        this.mConnections = conn;
        this.mInflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return this.mConnections.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mConnections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        TextView host;

        if (null == view) {
            view = this.mInflater.inflate(R.layout.connection_list_item, parent, false);
            view.setTag(R.id.host, view.findViewById(R.id.host));
        }

        host = (TextView) view.getTag(R.id.host);
        host.setText(mConnections.get(position).getHost());

        return view;
    }

    public void setConnections(ArrayList<Connection> connections) {
        this.mConnections = connections;
    }
}
