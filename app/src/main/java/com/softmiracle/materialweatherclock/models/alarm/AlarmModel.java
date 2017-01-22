package com.softmiracle.materialweatherclock.models.alarm;

import com.softmiracle.materialweatherclock.alarm.AlarmClockBuilder;

import java.io.Serializable;

/**
 * Created by Denys on 17.01.2017.
 */

public class AlarmModel implements Serializable {

    public int id;
    public boolean enable;
    public int hour, minute;
    public String repeat;
    public boolean sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    public int ringPosition;
    public String ring;
    public int volume;
    public boolean vibrate;
    public int remind;


    public AlarmModel(int id, AlarmClockBuilder builder) {
        this.id = id;
        this.enable = builder.enable;
        this.hour = builder.hour;
        this.minute = builder.minute;
        this.repeat = builder.repeat;
        this.sunday = builder.sunday;
        this.monday = builder.monday;
        this.tuesday = builder.tuesday;
        this.wednesday = builder.wednesday;
        this.thursday = builder.thursday;
        this.friday = builder.friday;
        this.saturday = builder.saturday;
        this.ringPosition = builder.ringPosition;
        this.ring = builder.ring;
        this.volume = builder.volume;
        this.vibrate = builder.vibrate;
        this.remind = builder.remind;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public void setRingPosition(int ringPosition) {
        this.ringPosition = ringPosition;
    }

    public void setRing(String ring) {
        this.ring = ring;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }
}
