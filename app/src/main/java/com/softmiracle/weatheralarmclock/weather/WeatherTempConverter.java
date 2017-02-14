package com.softmiracle.weatheralarmclock.weather;

/**
 * Created by Denys on 02.02.2017.
 */

public class WeatherTempConverter {

    public static Double convertToCelsius(String kelvin) throws NumberFormatException {
        double inKelvin;
        try {
            inKelvin = Double.parseDouble(kelvin);
        } catch (NumberFormatException e) {
            throw e;
        }
        return inKelvin - 273.15;
    }

    public static Double convertToFahrenheit(String kelvin) throws NumberFormatException {
        double inKelvin;
        try {
            inKelvin = Double.parseDouble(kelvin);
        } catch (NumberFormatException e) {
            throw e;
        }
        return (inKelvin - 273.15) * 1.8000 + 32.00;
    }
}
