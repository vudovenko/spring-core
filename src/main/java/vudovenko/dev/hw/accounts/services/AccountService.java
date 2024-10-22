package vudovenko.dev.hw.accounts.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vudovenko.dev.hw.accounts.models.Account;
import vudovenko.dev.hw.accounts.repositories.AccountRepository;
import vudovenko.dev.hw.users.models.User;
import vudovenko.dev.hw.users.services.UserService;

import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final Double defaultMoneyAmount;
    private final Double transferCommission;

    public AccountService(AccountRepository accountRepository,
                          UserService userService,
                          @Value("${account.default-amount}") Double defaultMoneyAmount,
                          @Value("${account.transfer-commission}") Double transferCommission) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.defaultMoneyAmount = defaultMoneyAmount;
        this.transferCommission = transferCommission;
    }

    public Account createAccount(User user) {
        Account account = Account
                .builder()
                .user(user)
                .moneyAmount(defaultMoneyAmount)
                .build();

        accountRepository.save(account);

        System.out.printf("New account created with ID: %d for user: %s\n",
                account.getId(),
                user.getLogin());

        return account;
    }

    public void closeAccount(Long accountId) {
        Account account = getById(accountId);
        User user = userService.getById(account.getUser().getId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User with ID %d not found"
                                .formatted(account.getUser().getId()))
                );

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

            accountRepository.delete(account);
        }
    }

    public void deposit(Long accountId, Double amount) {
        Account account = getById(accountId);
        accountRepository.deposit(account, amount);

        System.out.printf("Amount %.2f deposited to account ID: %d\n",
                amount,
                accountId);
    }

    public Account getById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with ID %d not found".formatted(accountId)));
    }

    public void withdraw(Long accountId, Double amount) {
        Account account = getById(accountId);
        accountRepository.withdraw(account, amount);

        System.out.printf("Amount %.2f withdrawn from account ID: %d\n",
                amount,
                accountId);
    }

    public void transfer(
            Long sourceAccountId,
            Long targetAccountId,
            Double amount
    ) {
        accountRepository.transfer(sourceAccountId, targetAccountId, amount, transferCommission);

        System.out.printf("Amount %.2f transferred from account ID %d to account ID %d\n",
                amount,
                sourceAccountId,
                targetAccountId);
    }
}
