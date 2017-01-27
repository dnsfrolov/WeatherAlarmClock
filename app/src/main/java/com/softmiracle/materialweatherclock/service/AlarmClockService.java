package com.softmiracle.materialweatherclock.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.softmiracle.materialweatherclock.alarm.AlarmManagerHelper;
import com.softmiracle.materialweatherclock.alarm.db.AlarmDBUtils;
import com.softmiracle.materialweatherclock.models.alarm.AlarmModel;

import java.util.List;

/**
 * Created by Denys on 23.01.2017.
 */

public class AlarmClockService extends Service {

    private List<AlarmModel> alarmList;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        startTimeTask();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private void startTimeTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                alarmList = AlarmDBUtils.queryAlarmClock(AlarmClockService.this);
                for (AlarmModel alarmModel : alarmList) {
                    if (alarmModel.enable) {
                        AlarmManagerHelper.startAlarmClock(AlarmClockService.this, alarmModel);
                    }
                }
            }
        }).start();
    }
}
