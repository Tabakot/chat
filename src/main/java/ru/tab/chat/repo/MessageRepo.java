package ru.tab.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tab.chat.domain.Message;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findAllByOrderById();
}
