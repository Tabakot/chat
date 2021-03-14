package ru.tab.chat.domain.openWeatherMap;

public class Main {
    public double temp;
    public double feels_like;
    public double temp_min;
    public double temp_max;
    public double pressure;
    public int humidity;

    public double getTemp() {
        return this.temp;
    }

    public double getFeels_like() {
        return this.feels_like;
    }

    public double getTemp_min() {
        return this.temp_min;
    }

    public double getTemp_max() {
        return this.temp_max;
    }

    public double getPressure() {
        return this.pressure;
    }

    public int getHumidity() {
        return this.humidity;
    }
}
