package ru.tab.chat.domain.openWeatherMap;

public class Weather {
    public int id;
    public String main;
    public String description;
    public String icon;

    public int getId() {
        return this.id;
    }

    public String getMain() {
        return this.main;
    }

    public String getDescription() {
        return this.description;
    }

    public String getIcon() {
        return this.icon;
    }
}
