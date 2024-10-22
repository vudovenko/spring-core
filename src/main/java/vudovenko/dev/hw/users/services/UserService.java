package vudovenko.dev.hw.users.services;

import org.springframework.stereotype.Service;
import vudovenko.dev.hw.users.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private static Long idCounter = 1L;

    private final Set<User> users = new HashSet<>();

    public User createUser(String login) {
        if (getByLogin(login).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = User
                .builder()
                .id(idCounter++)
                .login(login)
                .accountList(new ArrayList<>())
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
        return users
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public void showAllUsers() {
        System.out.println("List of all users:");
        users.forEach(System.out::println);
    }
}
