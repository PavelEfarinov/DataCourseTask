package ru.aicontest.service;

import org.springframework.stereotype.Service;
import ru.aicontest.domain.BaseUserEntity;
import ru.aicontest.form.login.UserRegisterCredentials;
import ru.aicontest.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BaseUserEntity findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public void register(UserRegisterCredentials userRegisterCredentials) {
        BaseUserEntity user = new BaseUserEntity();
        user.setLogin(userRegisterCredentials.getLogin());
        user.setEmail(userRegisterCredentials.getEmail());
        user.setPassword(userRegisterCredentials.getPassword());
        userRepository.createBaseUser(user.getLogin(), user.getPassword(), user.getEmail());
    }

    public List<BaseUserEntity> findAll() {
        return userRepository.findAllByOrderByLogin();
    }


    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

}
