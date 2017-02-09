package com.softmiracle.materialweatherclock.alarm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Denys on 17.01.2017.
 */

public class AlarmDBHelper extends SQLiteOpenHelper {

    public static final String ALARM_TABLE = "ALARM_CLOCK";
    public static final String CREATE_ALARM = "CREATE TABLE " + ALARM_TABLE + " (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ENABLE BOOLEAN, " +
            "MINUTE INTEGER, " +
            "HOUR INTEGER, " +
            "REPEAT TEXT, " +
            "SUNDAY BOOLEAN, " +
            "MONDAY BOOLEAN, " +
            "TUESDAY BOOLEAN, " +
            "WEDNESDAY BOOLEAN, " +
            "THURSDAY BOOLEAN, " +
            "FRIDAY BOOLEAN, " +
            "SATURDAY BOOLEAN, " +
            "RING_POSITION INTEGER, " +
            "RING TEXT, " +
            "VOLUME INTEGER, " +
            "VIBRATE BOOLEAN, " +
            "REMIND INTEGER, " +
            "WEATHER BOOLEAN " +
            ")";

    public AlarmDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ALARM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}
