package vudovenko.dev.hw.accounts.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vudovenko.dev.hw.accounts.models.Account;
import vudovenko.dev.hw.users.models.User;
import vudovenko.dev.hw.users.services.UserService;
import vudovenko.dev.hw.utils.math.MathUtils;
import vudovenko.dev.hw.utils.transactions.TransactionHelper;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService {

    private final UserService userService;
    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;

    private final Double defaultMoneyAmount;
    private final Double transferCommission;

    public AccountService(
            UserService userService,
            TransactionHelper transactionHelper,
            SessionFactory sessionFactory,
            @Value("${account.default-amount}") Double defaultMoneyAmount,
            @Value("${account.transfer-commission}") Double transferCommission
    ) {
        this.userService = userService;
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
        this.defaultMoneyAmount = defaultMoneyAmount;
        this.transferCommission = transferCommission;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void createAccount(User user) {
        transactionHelper.executeInTransaction(() -> {
            Account account = new Account();
            account.setUser(user);
            account.setMoneyAmount(defaultMoneyAmount);

            getCurrentSession().persist(account);

            System.out.printf("New account created with ID: %d for user: %s\n",
                    account.getId(),
                    user.getLogin());

            return account;
        });
    }

    public void closeAccount(Long accountId) {
        transactionHelper.executeInTransaction(() -> {
            Account account = getById(accountId);
            User user = userService.getById(account.getUser().getId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("User with ID %d not found"
                                    .formatted(account.getUser().getId()))
                    );

            closeAccountAndTransferFunds(account, user);
        });
    }

    private void closeAccountAndTransferFunds(Account account, User user) {
        List<Account> userAccounts = user.getAccounts();
        if (userAccounts.size() == 1) {
            System.out.println("It is impossible to close this account, " +
                    "as it is the only one.");
        } else {
            Account firstAccount = userAccounts.getFirst();
            if (firstAccount.equals(account)) {
                deposit(userAccounts.get(1).getId(), account.getMoneyAmount());
            } else {
                deposit(firstAccount.getId(), account.getMoneyAmount());
            }

            getCurrentSession().remove(account);
        }
    }

    public void deposit(Long accountId, Double amount) {
        transactionHelper.executeInTransaction(() -> {
            Account account = getById(accountId);
            checkAmount(amount);
            account.setMoneyAmount(MathUtils
                    .round(account.getMoneyAmount() + amount, 2));

            System.out.printf("Amount %.2f deposited to account ID: %d\n",
                    amount,
                    accountId);
        });
    }

    public Account getById(Long accountId) {
        return transactionHelper.executeInTransaction(() -> {
            Account account = getCurrentSession().get(Account.class, accountId);

            if (account != null) {
                return account;
            }

            throw new IllegalArgumentException("Account with ID %d not found"
                    .formatted(accountId));
        });
    }

    public void withdraw(Long accountId, Double amount) {
        transactionHelper.executeInTransaction(() -> {
            Account account = getById(accountId);
            checkAmount(amount);
            checkForSufficientFunds(account, amount);
            account.setMoneyAmount(MathUtils
                    .round(account.getMoneyAmount() - amount, 2));

            System.out.printf("Amount %.2f withdrawn from account ID: %d\n",
                    amount,
                    accountId);
        });
    }

    public void transfer(
            Long sourceAccountId,
            Long targetAccountId,
            Double amount
    ) {
        transactionHelper.executeInTransaction(() -> {
            Account sourceAccount = getById(sourceAccountId);
            Account targetAccount = getById(targetAccountId);

            withdraw(sourceAccount.getId(), amount);
            if (!Objects.equals(sourceAccount.getUser(), targetAccount.getUser())) {
                Double amountWithCommission = (1.0 - transferCommission) * amount;
                deposit(targetAccount.getId(), amountWithCommission);
                System.out.printf("The commission was %.2f\n",
                        amount - amountWithCommission);
            } else {
                deposit(targetAccount.getId(), amount);
            }
        });
    }

    private static void checkAmount(Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private static void checkForSufficientFunds(Account account, Double amount) {
        if (account.getMoneyAmount() < amount) {
            throw new IllegalArgumentException("Not enough money. On account with ID %d is only %.2f"
                    .formatted(account.getId(), account.getMoneyAmount()));
        }
    }
}
