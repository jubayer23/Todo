package com.creative.todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.creative.todo.model.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by jubayer on 7/8/2015.
 */
public class SQLiteDb extends SQLiteOpenHelper {


    //Instance of the SQLite database
    SQLiteDatabase db;


    /**
     * This constructor needed to tell the super class which Database and database version we will use
     * */
    public SQLiteDb(Context context) {
        super(context, DbConfig.DB_NAME, null, DbConfig.DB_VERSION);
    }

    /**
     * onCreate is called automatically by android OS. Note that, this method is called only when user first time install the app.
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, DbConfig.TABLE_NAMES, DbConfig.TABLE_ITEM, DbConfig.TABLE_TYPE, DbConfig.TABLE_PROPERTY);
    }

    /**
     * The onUpgrade method is also automatically called by android OS and it is called only when the database version change
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int current_version) {
        //Log.d("DEBUG_OLD", String.valueOf(old_version));
       // Log.d("DEBUG_CURNT", String.valueOf(current_version));
        switch (old_version) {
            case 1:
                //upgrade logic from version 1 to 2
            case 2:
                //upgrade logic from version 2 to 3
            case 3:
                //upgrade logic from version 3 to 4
                break;
            default:
                throw new IllegalStateException(
                        "onUpgrade() with unknown oldVersion " + old_version);
        }
    }


    //open database
    public void openDB() {
        db = this.getWritableDatabase();
    }

    // closing database
    public void closeDB() {
        if (db != null && db.isOpen())
            db.close();
    }

    public void executeQuery(String query) {
        db.execSQL(query);
    }


    /******************************************
     * EVENT table operations
     ******************************************/


    public long addEvent(Event event) {
        if (db == null) {
            db = getWritableDatabase();
        }

        ContentValues values = new ContentValues();
        values.put(DbConfig.KEY_NOTE, event.getNote());
        values.put(DbConfig.KEY_STATUS, event.getStatus());
        values.put(DbConfig.KEY_CREATED_AT, event.getCreated_at());
        values.put(DbConfig.KEY_PRIORITY, event.getPriority());
        values.put(DbConfig.KEY_EVENT_DATE, event.getEvent_date());
        long id = db.insert(DbConfig.TABLE_EVENT, null, values);
        return id;
    }

    public List<Event> getAllEvents() {
        if (db == null) {
            db = getWritableDatabase();
        }
        List<Event> list = new ArrayList<>();
        Event event = null;
        Cursor c = db.rawQuery("select * from " + DbConfig.TABLE_EVENT, null);
        if (c != null && c.moveToFirst()) {
            do {
                event = new Event();
                event.setId(c.getInt(c.getColumnIndex(DbConfig.KEY_ID)));
                event.setNote(c.getString(c.getColumnIndex(DbConfig.KEY_NOTE)));
                event.setStatus(c.getInt(c.getColumnIndex(DbConfig.KEY_STATUS)));
                event.setPriority(c.getInt(c.getColumnIndex(DbConfig.KEY_PRIORITY)));
                event.setCreated_at(c.getString(c.getColumnIndex(DbConfig.KEY_CREATED_AT)));
                event.setEvent_date(c.getString(c.getColumnIndex(DbConfig.KEY_EVENT_DATE)));
                list.add(event);
            } while (c.moveToNext());
        }
        return list;
    }

    public void updateEventStatus(int event_id, int status) {
        if (db == null) {
            db = getReadableDatabase();
        }

        ContentValues cv = new ContentValues();
        cv.put(DbConfig.KEY_STATUS, status); //These Fields should be your String values of actual column names
        db.update(DbConfig.TABLE_EVENT, cv, DbConfig.KEY_ID +  "=" + event_id, null);

    }

    public boolean deleteEvent(int id) {
        if (db == null) {
            db = getReadableDatabase();
        }
        return db.delete(DbConfig.TABLE_EVENT, DbConfig.KEY_ID + "=" + id, null) > 0;
    }

    /******************************************
     * TAG table operations
     ******************************************/



    /******************************************
     * EVENT_TAG table operations
     ******************************************/



    /******************************************
     * All table operations
     ******************************************/

    public void truncateTable(String table_name) {
        if (db == null) {
            db = getReadableDatabase();
        }
        String sql = "Delete from " + table_name;
        db.execSQL(sql);
        db.execSQL("VACUUM");
        sql = "DELETE FROM SQLITE_SEQUENCE WHERE name=" + "'" + table_name + "'";
        db.execSQL(sql);
    }


    /******************************************
     * Operations for creating tables
     ******************************************/

    public void createTable(SQLiteDatabase db, String[] TableName, String[][] tableItem, String[][] tableType, String[][] tableProperty) {
        String[] keyWithType = new String[TableName.length];
        for (int k = 0; k < TableName.length; k++) {
            for (int l = 0; l < tableItem[k].length; l++) {
                if (l != 0) {
                    keyWithType[k] = keyWithType[k] + ", " + tableItem[k][l] + " " + tableType[k][l] + tableProperty[k][l];
                } else {
                    keyWithType[k] = tableItem[k][l] + " " + tableType[k][l] + tableProperty[k][l];
                }
            }
        }
        for (int i = 0; i < TableName.length; i++) {
            String createTableQuery = "CREATE TABLE " + TableName[i] + " (" + keyWithType[i] + ")";
            db.execSQL(createTableQuery);
        }
    }

}

