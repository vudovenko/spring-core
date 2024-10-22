package vudovenko.dev.hw.users.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vudovenko.dev.hw.users.models.User;
import vudovenko.dev.hw.users.repositories.UserRepository;

import java.util.ArrayList;
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
                .id(idCounter++)
                .login(login)
                .accounts(new ArrayList<>())
                .build();

        users.add(user);

        return user;
    }

    public Optional<User> getByLogin(String login) {
        return users
                .stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public void showAllUsers() {
        System.out.println("List of all users:");
        users.forEach(System.out::println);
    }
}
