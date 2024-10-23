package vudovenko.dev.hw.users.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vudovenko.dev.hw.configurations.hibernate.TransactionHelper;
import vudovenko.dev.hw.users.models.User;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final TransactionHelper transactionHelper;

    public User saveUser(User user) {
        return transactionHelper.executeInTransaction(session -> {
            session.persist(user);

            return user;
        });
    }

    public Optional<User> findById(Long id) {
        return transactionHelper.executeInTransaction(session -> {
            User user = session.get(User.class, id);

            return Optional.ofNullable(user);
        });
    }

    public Optional<User> findByLogin(String login) {
        return transactionHelper.executeInTransaction(session -> {
            User user = session
                    .createQuery("select u from User u where u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();

            return Optional.ofNullable(user);
        });
    }

    public List<User> findAll() {
        return transactionHelper.executeInTransaction(session -> {
            return session
                    .createQuery("select u from User u", User.class)
                    .getResultList();
        });
    }
}
