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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

@Service
public class AssistantService {

    private User assistant;
    private String[] timeWord = {"время", "времени", "часов", "час"};
    private String[] dateWord = {"число", "день", "год", "дата"};
    private String[] weatherWord = {"погода", "температура", "холодно", "жарко", "градусов"};

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
            for (String time : timeWord) {
                if (word.equalsIgnoreCase(time)) {
                    dateFormat = new SimpleDateFormat("HH:mm:ss");
                    answer.setText("Сейчас " + dateFormat.format(new Date()));
                }
            }
            for (String date : dateWord) {
                if (word.equalsIgnoreCase(date)) {
                    dateFormat = new SimpleDateFormat(" EEEE d MMMM yyyy года", new Locale("ru"));
                    answer.setText("Сегодня " + dateFormat.format(new Date()));
                }
            }
            for (String weather : weatherWord) {
                if (word.equalsIgnoreCase(weather)) {
                    boolean check = false;
                    for (String place : message.getText().split(" ")) {
                        if (place.equals("в")) {
                            check = true;
                        } else if (check) {
                            answer.setText(String.valueOf(getWeatherMap(place).getMain().getTemp()));
                            check = false;
                            return answer;
                        }
                    }
                }
            }
        }
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
