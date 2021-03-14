package ru.tab.chat.repo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import ru.tab.chat.domain.OpenWeatherMap;

public interface ApiServiceOpenWeatherMap {
    @Headers("Content-Type: application/json")
    @GET("weather?units=metric&appid=1531b6579a082fa61bcec9fb5b88ecb1")
    Call<OpenWeatherMap> getWeather(@Query("q") String city);
}
