package com.blakelafleur.pyli.DataSenders;

import org.json.JSONObject;

/**
 * Created by blake on 2/18/16.
 */
public interface SenderInterface {
    void send(String address, JSONObject json);
}
