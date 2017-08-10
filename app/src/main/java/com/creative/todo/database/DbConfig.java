package com.creative.todo.database;


public class DbConfig {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    //Database Name
    public static final String DB_NAME = "TODO.db";
    // Database Version
    public static final int DB_VERSION = 1;
    public static final String DB_PASSWORD = "K3eQ71y";//K3eQ71y


    // Table Names
    public static final String TABLE_EVENT = "events";
    public static final String TABLE_TAG = "tags";
    public static final String TABLE_EVENT_TAG = "event_tags";

    // Common column names
    public static final String KEY_ID = "id";
    public static final String KEY_CREATED_AT = "created_at";

    // NOTES Table - column names
    public static final String KEY_NOTE = "note";
    public static final String KEY_STATUS = "status";
    public static final String KEY_PRIORITY = "priority";
    public static final String KEY_EVENT_DATE = "event_date";

    // TAGS Table - column names
    public static final String KEY_TAG_NAME = "tag_name";

    // EVENT_TAGS Table - column names
    public static final String KEY_EVENT_ID = "event_id";
    public static final String KEY_TAG_ID = "tag_id";

    //EVENT table property
    public static final String[] EventColumnName = {KEY_ID, KEY_NOTE, KEY_STATUS,KEY_PRIORITY, KEY_CREATED_AT,KEY_EVENT_DATE};
    public static final String[] EventColumnType = {"INTEGER", "TEXT", "INTEGER","INTEGER", "DATETIME", "DATETIME"};
    public static final String[] EventColumnProperty = {" PRIMARY KEY autoincrement", "", "", "", "",""};

    //TAG table property
    public static final String[] TagColumnName = {KEY_ID, KEY_TAG_NAME};
    public static final String[] TagColumnType = {"INTEGER", "TEXT"};
    public static final String[] TagColumnProperty = {" PRIMARY KEY autoincrement", ""};

    //EVENT_TAG table property
    public static final String[] TagEventColumnName = {KEY_ID, KEY_EVENT_ID, KEY_TAG_ID};
    public static final String[] TagEventColumnType = {"INTEGER", "INTEGER", "INTEGER"};
    public static final String[] TagEventColumnProperty = {" PRIMARY KEY autoincrement", "", ""};


    //Full database structure/property
    public static final String[] TABLE_NAMES = {TABLE_EVENT, TABLE_TAG, TABLE_EVENT_TAG};
    public static final String[][] TABLE_ITEM = {EventColumnName, TagColumnName, TagEventColumnName};
    public static final String[][] TABLE_TYPE = {EventColumnType, TagColumnType, TagEventColumnType};
    public static final String[][] TABLE_PROPERTY = {EventColumnProperty, TagColumnProperty, TagEventColumnProperty};

}
