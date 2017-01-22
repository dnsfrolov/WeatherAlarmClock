package com.softmiracle.materialweatherclock.alarm;

import com.softmiracle.materialweatherclock.models.alarm.AlarmModel;

/**
 * Created by Denys on 17.01.2017.
 */
public class AlarmClockLab extends AlarmModel {

    private static AlarmClockLab clockLab = null;

    protected AlarmClockLab(int id, AlarmClockBuilder builder) {
        super(id, builder);
    }

    public static AlarmClockLab getClockLab(int id, AlarmClockBuilder builder) {
        if (clockLab == null) {
            synchronized (AlarmClockLab.class) {
                if (clockLab == null) {
                    clockLab = new AlarmClockLab(id, builder);
                }
            }
        }
        return clockLab;
    }

    public void setId(int id) {
        this.id = id;
    }
}
