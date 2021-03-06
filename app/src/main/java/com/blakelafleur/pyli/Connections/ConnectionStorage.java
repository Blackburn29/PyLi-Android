package com.blakelafleur.pyli.Connections;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by blake on 2/17/16.
 */
public class ConnectionStorage extends SQLiteOpenHelper implements BaseColumns {
    public static final String TABLE_NAME = "connections";
    public static final String COLUMN_HOST = "host";
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "Connections.db";

    public ConnectionStorage(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String[] columns = {_ID, COLUMN_HOST};
        String[] attr = {"INTEGER PRIMARY KEY", "STRING"};
        String stmt = null;
        try {
            stmt = this.createTableStatement(TABLE_NAME, columns, attr);
            db.execSQL(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public boolean addConnection(Connection c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOST, c.getHost());

        return db.insert(TABLE_NAME, null, values) > 0;
    }

    public ArrayList<Connection> listConnections() {
        ArrayList<Connection> conn = new ArrayList<Connection>();

        String[] columns = {_ID, COLUMN_HOST};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(
                TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );

        if (null == c || !c.moveToFirst()) {
            return conn;
        }

        do {
            Connection connection = new Connection(
                    c.getInt(c.getColumnIndex(_ID)),
                    c.getString(c.getColumnIndex(COLUMN_HOST))
            );
            conn.add(connection);
        } while(c.moveToNext());

        c.close();

        return conn;
    }

    private String createTableStatement(String table, String[] columns, String[] attributes) throws Exception {
        if (columns.length != attributes.length) {
            throw new Exception("Columns and attributes are invalid");
        }

        String sql = "CREATE TABLE " + table + " (";

        for(int i = 0; i < columns.length; i++) {
            sql += columns[i] + " " + attributes[i];

            if (i < columns.length - 1) {
                sql += ", ";
            }
        }

        return sql + " )";

    }
}
