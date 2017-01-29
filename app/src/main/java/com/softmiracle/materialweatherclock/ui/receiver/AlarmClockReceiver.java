package com.softmiracle.materialweatherclock.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.softmiracle.materialweatherclock.ui.activity.BootAlarmActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Denys on 24.01.2017.
 */

public class AlarmClockReceiver extends BroadcastReceiver {

    public static final String ALARM_CLOCK = "alarm_clock";

    public AlarmClockReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent bootIntent = new Intent(context, BootAlarmActivity.class);
        bootIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        bootIntent.putExtra(ALARM_CLOCK, intent.getSerializableExtra(ALARM_CLOCK));
        Log.d("TAG", "receiver");
        context .startActivity(bootIntent);
    }
}
