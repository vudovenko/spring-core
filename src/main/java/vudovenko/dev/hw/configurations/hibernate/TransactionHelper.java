package vudovenko.dev.hw.configurations.hibernate;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TransactionHelper {

    private final SessionFactory sessionFactory;

    public void executeInTransaction(Consumer<Session> action) {
        executeSessionActionInTransaction(session -> {
            action.accept(session);

            return null;
        });
    }

    public <T> T executeInTransaction(Function<Session, T> action) {
        return executeSessionActionInTransaction(action::apply);
    }

    private <T> T executeSessionActionInTransaction(SessionAction<T> sessionAction) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                T result = sessionAction.apply(session);
                transaction.commit();

                return result;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @FunctionalInterface
    private interface SessionAction<T> {

        T apply(Session session);
    }
}
