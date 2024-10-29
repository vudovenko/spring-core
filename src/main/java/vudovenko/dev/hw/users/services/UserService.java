package vudovenko.dev.hw.users.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import vudovenko.dev.hw.users.models.User;
import vudovenko.dev.hw.utils.transactions.TransactionHelper;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;

    public UserService(
            SessionFactory sessionFactory,
            TransactionHelper transactionHelper
    ) {
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public User createUser(String login) {
        return transactionHelper.executeInTransaction(() -> {
            if (getByLogin(login).isPresent()) {
                throw new IllegalArgumentException("User already exists");
            }

            User user = new User();
            user.setLogin(login);

            getCurrentSession().persist(user);

            return user;
        });
    }

    public Optional<User> getByLogin(String login) {
        return transactionHelper.executeInTransaction(() -> {
            List<User> user = getCurrentSession()
                    .createQuery("select u from User u where u.login = :login", User.class)
                    .setParameter("login", login)
                    .getResultList();

            return user.isEmpty() ? Optional.empty() : Optional.of(user.getFirst());
        });
    }

    public Optional<User> getById(Long id) {
        return transactionHelper.executeInTransaction(() -> {
            User user = getCurrentSession().get(User.class, id);

            return Optional.ofNullable(user);
        });
    }

    public void showAllUsers() {
        transactionHelper.executeInTransaction(() -> {
            System.out.println("List of all users:");
            List<User> users = getCurrentSession()
                    .createQuery("""
                                select distinct u
                                from User u
                                left join fetch u.accounts
                                order by u.id
                            """, User.class)
                    .getResultList();
            users.forEach(System.out::println);
        });
    }
}
