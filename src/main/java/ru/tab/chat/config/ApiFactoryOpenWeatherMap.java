package ru.tab.chat.config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.tab.chat.repo.ApiServiceOpenWeatherMap;

public class ApiFactoryOpenWeatherMap {
    private static final String WEATHERMAP_URL = "http://api.openweathermap.org/data/2.5/";

    static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(WEATHERMAP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiServiceOpenWeatherMap getService() {
        return buildRetrofit().create(ApiServiceOpenWeatherMap.class);
    }
}
