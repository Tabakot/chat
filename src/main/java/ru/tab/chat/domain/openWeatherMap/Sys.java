package ru.tab.chat.domain.openWeatherMap;

public class Sys {
    public int type;
    public int id;
    public String country;
    public int sunrise;
    public int sunset;

    public int getType() {
        return this.type;
    }

    public int getId() {
        return this.id;
    }

    public String getCountry() {
        return this.country;
    }

    public int getSunrise() {
        return this.sunrise;
    }

    public int getSunset() {
        return this.sunset;
    }
}
