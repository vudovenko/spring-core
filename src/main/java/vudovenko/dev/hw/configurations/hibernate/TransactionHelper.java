package vudovenko.dev.hw.configurations.hibernate;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class TransactionHelper {

    private final SessionFactory sessionFactory;

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

        if (transaction.getStatus().equals(TransactionStatus.NOT_ACTIVE)) {
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
