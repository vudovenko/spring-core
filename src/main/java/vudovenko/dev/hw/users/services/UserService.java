package vudovenko.dev.hw.users.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vudovenko.dev.hw.users.models.User;
import vudovenko.dev.hw.users.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(String login) {
        if (getByLogin(login).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = User
                .builder()
                .login(login)
                .build();

        userRepository.saveUser(user);

        return user;
    }

    public Optional<User> getByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public void showAllUsers() {
        System.out.println("List of all users:");
        userRepository.findAll().forEach(System.out::println);
    }
}
