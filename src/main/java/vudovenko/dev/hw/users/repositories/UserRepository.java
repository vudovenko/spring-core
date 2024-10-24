package vudovenko.dev.hw.users.repositories;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import vudovenko.dev.hw.users.models.User;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public User saveUser(User user) {
        getCurrentSession().persist(user);

        return user;
    }

    public Optional<User> findById(Long id) {
        User user = getCurrentSession().get(User.class, id);

        return Optional.ofNullable(user);
    }

    public Optional<User> findByLogin(String login) {
        List<User> user = getCurrentSession()
                .createQuery("select u from User u where u.login = :login", User.class)
                .setParameter("login", login)
                .getResultList();

        return user.isEmpty() ? Optional.empty() : Optional.of(user.getFirst());
    }

    public List<User> findAll() {
        return getCurrentSession()
                .createQuery("select u from User u", User.class)
                .getResultList();
    }
}
