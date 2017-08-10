package com.creative.todo.appdata;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by jubayer on 8/7/2017.
 */

public class AppConstant {

    public static final int STATUS_NOT_COMPLETED = 0;
    public static final int STATUS_COMPLETED = 1;

    public static final int PRIORITY_HIGH = 2;
    public static final int PRIORITY_MEDIUM = 1;
    public static final int PRIORITY_LOW = 0;

    public static final String priorities[] = {"Low","Medium","High"};

    public static SimpleDateFormat getDateTimeFormat(){
        return new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
    }
    public static SimpleDateFormat getDateFormat(){
        return new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
    }

}
