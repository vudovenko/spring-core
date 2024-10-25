package vudovenko.dev.hw.accounts.repositories;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import vudovenko.dev.hw.accounts.models.Account;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private final SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public Account save(Account account) {
        getCurrentSession().persist(account);

        return account;
    }

    public Optional<Account> findById(Long id) {
        Account account = getCurrentSession().get(Account.class, id);

        return Optional.ofNullable(account);
    }

    public void delete(Account account) {
        account = getCurrentSession().merge(account);
        getCurrentSession().remove(account);
    }

    public Double deposit(Account account, Double amount) {
        account.deposit(amount);
        Account mergedAccount = getCurrentSession().merge(account);

        return mergedAccount.getMoneyAmount();
    }

    public Double withdraw(Account account, Double amount) {
        account.withdraw(amount);
        Account mergedAccount = getCurrentSession().merge(account);

        return mergedAccount.getMoneyAmount();
    }
}
