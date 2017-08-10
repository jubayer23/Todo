package com.creative.todo.appdata;

import android.app.Application;

import com.creative.todo.database.SQLiteDb;


public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    //SingleTon instance
    private static AppController mInstance;

    //SQLite open helper instance
    private static SQLiteDb SQLiteDbInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //Open database only when app turns on.
        SQLiteDbInstance = new SQLiteDb(this);
        SQLiteDbInstance.openDB();
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static synchronized SQLiteDb getSQLiteDbInstance() {
        return SQLiteDbInstance;
    }
}