package vudovenko.dev.accounts.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vudovenko.dev.accounts.models.Account;
import vudovenko.dev.users.models.User;
import vudovenko.dev.users.services.UserService;

import java.util.*;

@Service
public class AccountService {

    private static Long idCounter = 1L;

    private final UserService userService;
    private final Double defaultMoneyAmount;
    private final Double transferCommission;

    private final Set<Account> accounts = new HashSet<>();

    public AccountService(UserService userService,
                          @Value("${account.default-amount}") Double defaultMoneyAmount,
                          @Value("${account.transfer-commission}") Double transferCommission) {
        this.userService = userService;
        this.defaultMoneyAmount = defaultMoneyAmount;
        this.transferCommission = transferCommission;
    }

    public Account createAccount(User user) {
        Account account = Account
                .builder()
                .id(idCounter++)
                .userId(user.getId())
                .moneyAmount(defaultMoneyAmount)
                .build();

        accounts.add(account);
        user.getAccountList().add(account);

        System.out.printf("New account created with ID: %d for user: %s\n",
                account.getId(),
                user.getLogin());

        return account;
    }

    public void closeAccount(Long accountId) {
        Account account = getById(accountId);
        User user = userService.getById(account.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User with ID %d not found"
                                .formatted(account.getUserId()))
                );

        List<Account> userAccounts = user.getAccountList();
        if (userAccounts.size() == 1) {
            System.out.println("It is impossible to close this account, " +
                    "as it is the only one.");
        } else {
            Account firstAccount = userAccounts.getFirst();
            if (firstAccount.equals(account)) {
                userAccounts.get(1).deposit(account.getMoneyAmount());
            } else {
                firstAccount.deposit(account.getMoneyAmount());
            }

            accounts.remove(account);
            user.getAccountList().remove(account);
        }
    }

    public void deposit(Long accountId, Double amount) {
        Account account = getById(accountId);
        account.deposit(amount);

        System.out.printf("Amount %.1f deposited to account ID: %d\n",
                amount,
                accountId);
    }

    public Account getById(Long accountId) {
        return accounts.stream()
                .filter(account -> account.getId().equals(accountId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Account with ID %d not found".formatted(accountId)));
    }

    public void transfer(Long sourceAccountId, Long targetAccountId, Double amount) {
        Account sourceAccount = getById(sourceAccountId);
        Account targetAccount = getById(targetAccountId);
        sourceAccount.withdrawn(amount);
        if (!Objects.equals(sourceAccount.getUserId(), targetAccount.getUserId())) {
            amount = (1.0 - transferCommission) * amount;
            targetAccount.deposit(amount);
        } else {
            targetAccount.deposit(amount);
        }
        System.out.printf("Amount %.1f transferred from account ID %d to account ID %d\n",
                amount,
                sourceAccountId,
                targetAccountId);
    }

    public void withdraw(Long accountId, Double amount) {
        Account account = getById(accountId);
        account.withdrawn(amount);

        System.out.printf("Amount %.1f withdrawn from account ID: %d\n",
                amount,
                accountId);
    }
}
