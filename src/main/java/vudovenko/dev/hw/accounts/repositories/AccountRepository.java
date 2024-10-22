package vudovenko.dev.hw.accounts.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import vudovenko.dev.hw.accounts.models.Account;
import vudovenko.dev.hw.configurations.hibernate.TransactionHelper;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private final TransactionHelper transactionHelper;

    public Account save(Account account) {
        return transactionHelper.executeInTransaction(session -> {
            session.persist(account);

            return account;
        });
    }

    public Optional<Account> findById(Long id) {
        return transactionHelper.executeInTransaction(session -> {
            Account account = session.get(Account.class, id);

            return Optional.ofNullable(account);
        });
    }

    public void delete(Account account) {
        transactionHelper.executeInTransaction(session -> {
            session.remove(session.merge(account));
        });
    }

    public Double deposit(Account account, Double amount) {
        return transactionHelper.executeInTransaction(session -> {
            account.deposit(amount);
            Account mergedAccount = session.merge(account);

            return mergedAccount.getMoneyAmount();
        });
    }

    public Double withdraw(Account account, Double amount) {
        return transactionHelper.executeInTransaction(session -> {
            account.withdraw(amount);
            Account mergedAccount = session.merge(account);

            return mergedAccount.getMoneyAmount();
        });
    }

    public void transfer(
            Long sourceAccountId,
            Long targetAccountId,
            Double amount,
            Double transferCommission
    ) {
        transactionHelper.executeInTransaction(session -> {
            Account sourceAccount = session.get(Account.class, sourceAccountId);
            Account targetAccount = session.get(Account.class, targetAccountId);
            sourceAccount.transfer(targetAccount, amount, transferCommission);
        });
    }
}
