package com.softmiracle.materialweatherclock.alarm;

import com.softmiracle.materialweatherclock.models.alarm.AlarmModel;

/**
 * Created by Denys on 17.01.2017.
 */

public class AlarmClockBuilder {

    public boolean enable;
    public int hour, minute;
    public String repeat;
    public boolean sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    public int ringPosition;
    public String ring;
    public int volume;
    public boolean vibrate;
    public int remind;
    public boolean weather;

    public AlarmClockBuilder() {
    }

    public AlarmClockBuilder enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public AlarmClockBuilder hour(int hour) {
        this.hour = hour;
        return this;
    }

    public AlarmClockBuilder minute(int minute) {
        this.minute = minute;
        return this;
    }

    public AlarmClockBuilder repeat(String repeat) {
        this.repeat = repeat;
        return this;
    }


    public AlarmClockBuilder sunday(boolean sunday) {
        this.sunday = sunday;
        return this;
    }

    public AlarmClockBuilder monday(boolean monday) {
        this.monday = monday;
        return this;
    }

    public AlarmClockBuilder tuesday(boolean tuesday) {
        this.tuesday = tuesday;
        return this;
    }

    public AlarmClockBuilder wednesday(boolean wednesday) {
        this.wednesday = wednesday;
        return this;
    }

    public AlarmClockBuilder thursday(boolean thursday) {
        this.thursday = thursday;
        return this;
    }

    public AlarmClockBuilder friday(boolean friday) {
        this.friday = friday;
        return this;
    }

    public AlarmClockBuilder saturday(boolean saturday) {
        this.saturday = saturday;
        return this;
    }

    public AlarmClockBuilder ringPosition(int ringPosition) {
        this.ringPosition = ringPosition;
        return this;
    }

    public AlarmClockBuilder ring(String ring) {
        this.ring = ring;
        return this;
    }

    public AlarmClockBuilder volume(int volume) {
        this.volume = volume;
        return this;
    }

    public AlarmClockBuilder vibrate(boolean vibrate) {
        this.vibrate = vibrate;
        return this;
    }

    public AlarmClockBuilder remind(int remind) {
        this.remind = remind;
        return this;
    }

    public AlarmClockBuilder weather(boolean weather) {
        this.weather = weather;
        return this;
    }

    public AlarmModel builder(int id) {
        return new AlarmModel(id, this);
    }

    public AlarmClockLab builderLab(int id) {
        return AlarmClockLab.getClockLab(id, this);
    }
}
