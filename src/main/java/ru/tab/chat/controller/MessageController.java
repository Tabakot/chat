package ru.tab.chat.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ru.tab.chat.domain.Message;
import ru.tab.chat.repo.MessageRepo;
import ru.tab.chat.services.AssistantService;

import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepo messageRepo;
    private final AssistantService assistantService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MessageController(MessageRepo messageRepo, AssistantService assistantService, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageRepo = messageRepo;
        this.assistantService = assistantService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping
    public List<Message> list() {
        return messageRepo.findAllByOrderById();
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @GetMapping("Get_Command")
    public Message getCommand() {
        Message cmd = new Message();
        for (Message message:list()) {
            if (message.getText().charAt(0) == '/')
                cmd = message;
        }
        return cmd;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFromDb, @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageFromDb, "id");
        return messageRepo.save(message);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepo.delete(message);
    }

    @MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    public Message change(Message message) {
        return messageRepo.save(message);
    }

    @MessageMapping("/botMessage")
    @SendTo("/topic/activity")
    public Message botMessage(Message message) throws InterruptedException {
        Thread.sleep(500);
        return messageRepo.save(assistantService.postMessage(message));
    }
}
