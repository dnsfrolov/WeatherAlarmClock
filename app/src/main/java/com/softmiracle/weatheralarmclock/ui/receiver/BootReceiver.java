package com.softmiracle.weatheralarmclock.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.softmiracle.weatheralarmclock.service.AlarmClockService;

/**
 * Created by Denys on 29.01.2017.
 */

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            context.startService(new Intent(context, AlarmClockService.class));
        }
    }
}
