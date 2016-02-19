package com.blakelafleur.pyli.DataSenders;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by blake on 2/18/16.
 */
public class BasicHttpSender extends AsyncTask<String, Integer, Integer> implements SenderInterface {
    private static final int ADDRESS = 0;
    private static final int JSON = 1;

    private View view;

    public BasicHttpSender(View view) {
        this.view = view;
    }

    public void send(String address, JSONObject json) {
        execute(address, json.toString());
    }

    @Override
    protected Integer doInBackground(String... params) {
        HttpURLConnection conn;
        try {
            String address = params[ADDRESS];
            String json = params[JSON];
            conn = (HttpURLConnection) new URL(address).openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(json);
            writer.flush();
            writer.close();
            return conn.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    protected void onPostExecute(Integer resp) {
        if (200 != resp) {
            Toast.makeText(this.view.getContext(), String.format("There was an error sending your request. Code: %s", resp), Toast.LENGTH_LONG);
        }
    }

}
