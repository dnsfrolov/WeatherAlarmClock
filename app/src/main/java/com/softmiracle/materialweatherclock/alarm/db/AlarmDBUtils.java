package com.softmiracle.materialweatherclock.alarm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.softmiracle.materialweatherclock.alarm.AlarmClockBuilder;
import com.softmiracle.materialweatherclock.models.alarm.AlarmModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denys on 17.01.2017.
 */

public class AlarmDBUtils {
    public static final String DB_ALARM = "AlarmClock.db";
    public static final int DB_VERSION = 1;

    public static SQLiteDatabase openAlarmClock(Context context) {
        AlarmDBHelper alarmDBHelper = new AlarmDBHelper(context, DB_ALARM, null, DB_VERSION);
        return alarmDBHelper.getWritableDatabase();
    }

    public static ContentValues getContentValues(AlarmModel alarm) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ENABLE", alarm.enable);
        contentValues.put("MINUTE", alarm.minute);
        contentValues.put("HOUR", alarm.hour);
        contentValues.put("REPEAT", alarm.repeat);
        contentValues.put("SUNDAY", alarm.sunday);
        contentValues.put("MONDAY", alarm.monday);
        contentValues.put("TUESDAY", alarm.tuesday);
        contentValues.put("WEDNESDAY", alarm.wednesday);
        contentValues.put("THURSDAY", alarm.thursday);
        contentValues.put("FRIDAY", alarm.friday);
        contentValues.put("SATURDAY", alarm.saturday);
        contentValues.put("RING_POSITION", alarm.ringPosition);
        contentValues.put("RING", alarm.ring);
        contentValues.put("VOLUME", alarm.volume);
        contentValues.put("VIBRATE", alarm.vibrate);
        contentValues.put("REMIND", alarm.remind);
        return contentValues;
    }

    public static void insertAlarmClock(Context context, AlarmModel alarm) {
        ContentValues contentValues = getContentValues(alarm);

        openAlarmClock(context).insert(AlarmDBHelper.ALARM_TABLE, null, contentValues);
        contentValues.clear();
    }

    public static void updateAlarmClock(Context context, AlarmModel alarm) {
        ContentValues contentValues = getContentValues(alarm);
        openAlarmClock(context).update(AlarmDBHelper.ALARM_TABLE, contentValues, "ID=" + alarm.id, null);
        contentValues.clear();
    }

    public static void deleteAlarmClock(Context context, int id) {
        openAlarmClock(context).delete(AlarmDBHelper.ALARM_TABLE, "ID=" + id, null);
    }

    public static List<AlarmModel> queryAlarmClock(Context context) {
        List<AlarmModel> alarmList = new ArrayList<>();
        Cursor cursor = openAlarmClock(context).query(AlarmDBHelper.ALARM_TABLE, null, null, null, null, null, "ID DESC");
        while (cursor.moveToNext()) {
            AlarmModel alarm;
            AlarmClockBuilder builder = new AlarmClockBuilder();
            alarm = builder.enable(valueOf(cursor.getInt(cursor.getColumnIndex("ENABLE"))))
                    .minute(cursor.getInt(cursor.getColumnIndex("MINUTE")))
                    .hour(cursor.getInt(cursor.getColumnIndex("HOUR")))
                    .repeat(cursor.getString(cursor.getColumnIndex("REPEAT")))
                    .sunday(valueOf(cursor.getInt(cursor.getColumnIndex("SUNDAY"))))
                    .monday(valueOf(cursor.getInt(cursor.getColumnIndex("MONDAY"))))
                    .tuesday(valueOf(cursor.getInt(cursor.getColumnIndex("TUESDAY"))))
                    .wednesday(valueOf(cursor.getInt(cursor.getColumnIndex("WEDNESDAY"))))
                    .thursday(valueOf(cursor.getInt(cursor.getColumnIndex("THURSDAY"))))
                    .friday(valueOf(cursor.getInt(cursor.getColumnIndex("FRIDAY"))))
                    .saturday(valueOf(cursor.getInt(cursor.getColumnIndex("SATURDAY"))))
                    .ringPosition(cursor.getInt(cursor.getColumnIndex("RING_POSITION")))
                    .ring(cursor.getString(cursor.getColumnIndex("RING")))
                    .volume(cursor.getInt(cursor.getColumnIndex("VOLUME")))
                    .vibrate(valueOf(cursor.getInt(cursor.getColumnIndex("VIBRATE"))))
                    .remind(cursor.getInt(cursor.getColumnIndex("REMIND")))
                    .builder(cursor.getInt(cursor.getColumnIndex("ID")));
            alarmList.add(alarm);
        }
        return alarmList;
    }

    public static boolean valueOf(int i) {
        return i == 1;
    }
}
