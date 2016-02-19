package com.blakelafleur.pyli.Connections;

/**
 * Created by blake on 2/17/16.
 */
public class Connection {

    private int id;
    private String host;

    public Connection(int id, String host) {
        this.id = id;
        this.host = host;

    }

    public int getId() {
        return id;
    }

    public String getHost() {
        return host;
    }
}
