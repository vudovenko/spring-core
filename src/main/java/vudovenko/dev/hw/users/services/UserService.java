package vudovenko.dev.hw.users.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vudovenko.dev.hw.configurations.hibernate.TransactionHelper;
import vudovenko.dev.hw.users.models.User;
import vudovenko.dev.hw.users.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TransactionHelper transactionHelper;

    public User createUser(String login) {
        return transactionHelper.executeInTransaction(() -> {
            if (getByLogin(login).isPresent()) {
                throw new IllegalArgumentException("User already exists");
            }

            User user = User
                    .builder()
                    .login(login)
                    .build();

            userRepository.saveUser(user);

            return user;
        });
    }

    public Optional<User> getByLogin(String login) {
        return transactionHelper.executeInTransaction(() -> userRepository.findByLogin(login));
    }

    public Optional<User> getById(Long id) {
        return transactionHelper.executeInTransaction(() -> userRepository.findById(id));
    }

    public void showAllUsers() {
        transactionHelper.executeInTransaction(() -> {
            System.out.println("List of all users:");
            userRepository.findAll().forEach(System.out::println);
        });
    }
}
