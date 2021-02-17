package ru.tab.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tab.chat.domain.User;
import ru.tab.chat.repo.UserDetailsRepo;

@Service
public class UserService {
    @Autowired
    private UserDetailsRepo userDetailsRepo;


    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFindByUsername = userDetailsRepo.findByUsername(username);

        if (userFindByUsername != null) {
            return userFindByUsername;
        }
        return null;
    }
}
