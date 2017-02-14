package com.softmiracle.weatheralarmclock.models.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Denys on 02.02.2017.
 */

public class Main {

    @SerializedName("temp")
    private String temp;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
