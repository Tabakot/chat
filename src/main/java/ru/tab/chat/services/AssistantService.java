package ru.tab.chat.services;

import org.springframework.stereotype.Service;
import ru.tab.chat.domain.Message;
import ru.tab.chat.domain.Role;
import ru.tab.chat.domain.User;

import java.util.Collections;

@Service
public class AssistantService {

    private User assistant;

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

        answer.setText("test");
        return answer;
    }
}
