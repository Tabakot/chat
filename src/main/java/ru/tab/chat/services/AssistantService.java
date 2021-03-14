package ru.tab.chat.services;

import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import ru.tab.chat.config.ApiFactoryOpenWeatherMap;
import ru.tab.chat.domain.Message;
import ru.tab.chat.domain.OpenWeatherMap;
import ru.tab.chat.domain.Role;
import ru.tab.chat.domain.User;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

@Service
public class AssistantService {

    private User assistant;
    private String[] timeWord = {"время", "времени", "часов", "час"};
    private String[] dateWord = {"число", "день", "год", "дата"};
    private String[] weatherWord = {"погода", "погоду", "температура", "холодно", "жарко", "градусов"};
    private String[] greetingWord = {"привет", "хай", "hi", "hello", "ку", "здравствуй", "здравствуйте"};

    public AssistantService(){
        User assistant = new User();
        assistant.setUsername("Assistant");
        assistant.setId("Assistant");
        assistant.setRoles(Collections.singleton(Role.ASSISTANT));
        assistant.setActive(true);
        this.assistant = assistant;
    }

    public Message postMessage(Message message) {
        Message answer = new Message();
        answer.setAutor(assistant);
        SimpleDateFormat dateFormat;
        for (String word : message.getText().split(" ")) {
            for (String greeting : greetingWord) {
                if (word.equalsIgnoreCase(greeting)) {
                    answer.setText(String.format("Здравствуйте, %s", message.getAutor().getUsername()));
                    return answer;
                }
            }
            for (String time : timeWord) {
                if (word.equalsIgnoreCase(time)) {
                    dateFormat = new SimpleDateFormat("HH:mm:ss");
                    answer.setText("Сейчас " + dateFormat.format(new Date()));
                    return answer;
                }
            }
            for (String date : dateWord) {
                if (word.equalsIgnoreCase(date)) {
                    dateFormat = new SimpleDateFormat(" EEEE d MMMM yyyy года", new Locale("ru"));
                    answer.setText("Сегодня " + dateFormat.format(new Date()));
                    return answer;
                }
            }
            for (String weather : weatherWord) {
                if (word.equalsIgnoreCase(weather)) {
                    boolean check = false;
                    for (String place : message.getText().split(" ")) {
                        if (place.equals("в")) {
                            check = true;
                        } else if (check) {
                            OpenWeatherMap weatherMap = getWeatherMap(place);
                            String deg;
                            if (weatherMap.getWind().getDeg() >= 23 && weatherMap.getWind().getDeg() <= 67) {
                                deg = "северо-восточный";
                            } else if (weatherMap.getWind().getDeg() >= 68 && weatherMap.getWind().getDeg() <= 112) {
                                deg = "восточный";
                            } else if (weatherMap.getWind().getDeg() >= 113 && weatherMap.getWind().getDeg() <= 157) {
                                deg = "юго-восточный";
                            } else if (weatherMap.getWind().getDeg() >= 158 && weatherMap.getWind().getDeg() <= 202) {
                                deg = "южный";
                            } else if (weatherMap.getWind().getDeg() >= 203 && weatherMap.getWind().getDeg() <= 247) {
                                deg = "юго-западный";
                            } else if (weatherMap.getWind().getDeg() >= 248 && weatherMap.getWind().getDeg() <= 292) {
                                deg = "западный";
                            } else if (weatherMap.getWind().getDeg() >= 293 && weatherMap.getWind().getDeg() <= 337) {
                                deg = "северо-западный";
                            } else {
                                deg = "северный";
                            }
                            String weatherMain = weatherMap.getWeather().get(0).getMain();
                            if (weatherMain.equals("Clouds")) {
                                weatherMain = "Облачно";
                            } else if (weatherMain.equals("Snow")) {
                                weatherMain = "Снег";
                            } else if (weatherMain.equals("Clear")) {
                                weatherMain = "Безоблачно";
                            } else if (weatherMain.equals("Rain")) {
                                weatherMain = "Дождь";
                            }
                            answer.setText(String.format("%s; температура %.0f°C; ветер %s %.0f м/c; влажность %d%%",
                                    weatherMain, weatherMap.getMain().getTemp(),
                                    deg, weatherMap.getWind().getSpeed(),
                                    weatherMap.getMain().getHumidity()));
                            return answer;
                        }
                    }
                }
            }
        }
        answer.setText("Я вас не понял");
        return answer;
    }

    public static OpenWeatherMap getWeatherMap(String city) {
        Call<OpenWeatherMap> weatherCall = ApiFactoryOpenWeatherMap.getService().getWeather(city);
        try {
            Response<OpenWeatherMap> response = weatherCall.execute();
            return response.body();
        } catch (Throwable t) {
            System.out.println(t.getMessage());
        }
        return null;
    }
}
