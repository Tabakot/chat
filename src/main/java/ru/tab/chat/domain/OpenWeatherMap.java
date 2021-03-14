package ru.tab.chat.domain;

import ru.tab.chat.domain.openWeatherMap.*;

import java.util.List;

public class OpenWeatherMap {
    public Coord coord;
    public List<Weather> weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;

    public Coord getCoord() {
        return this.coord;
    }

    public List<Weather> getWeather() {
        return this.weather;
    }

    public String getBase() {
        return this.base;
    }

    public Main getMain() {
        return this.main;
    }

    public int getVisibility() {
        return this.visibility;
    }

    public Wind getWind() {
        return this.wind;
    }

    public Clouds getClouds() {
        return this.clouds;
    }

    public int getDt() {
        return this.dt;
    }

    public Sys getSys() {
        return this.sys;
    }

    public int getTimezone() {
        return this.timezone;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getCod() {
        return this.cod;
    }
}

