package vudovenko.dev.hw.utils.transactions;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class TransactionHelper {

    private final SessionFactory sessionFactory;

    public TransactionHelper(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void executeInTransaction(Runnable runnable) {
        executeSessionActionInTransaction(() -> {
            runnable.run();

            return null;
        });
    }

    public <T> T executeInTransaction(Supplier<T> supplier) {
        return executeSessionActionInTransaction(supplier);
    }

    private <T> T executeSessionActionInTransaction(Supplier<T> supplier) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();

        if (!transaction.isActive()) {
            try {
                transaction.begin();
                T result = supplier.get();
                transaction.commit();

                return result;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            } finally {
                session.close();
            }
        }

        return supplier.get();
    }
}
