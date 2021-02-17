package ru.tab.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tab.chat.domain.User;

public interface UserDetailsRepo extends JpaRepository<User, String> {
    User findByUsername(String username);
}
