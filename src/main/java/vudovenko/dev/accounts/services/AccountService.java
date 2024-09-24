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

    @Value("${account.default-amount}") // Получаем значение из application.properties
    private Double defaultMoneyAmount;
    @Value("${account.transfer-commission}")
    private Double transferCommission;

    private final UserService userService;

    private final Set<Account> accounts = new HashSet<>();

    public AccountService(UserService userService) {
        this.userService = userService;
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

        System.out.printf("\nNew account created with ID: %d for user: %s",
                account.getId(),
                user.getLogin());

        return account;
    }

    // todo отрефакторить
    public void closeAccount(Long accountId) {
        Optional<Account> accountOptional = accounts
                .stream()
                .filter(account -> account.getId().equals(accountId))
                .findFirst();
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Optional<User> userOptional = userService.getById(account.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
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
            } else {
                throw new IllegalArgumentException("User with ID " + account.getUserId() + " not found");
            }
        } else {
            throw new IllegalArgumentException("Account with ID " + accountId + " not found");
        }
    }

    public void deposit(Long accountId, Double amount) {
        checkAmount(amount);
        Account account = findById(accountId);
        account.deposit(amount);

        System.out.printf("\nAmount %.1f deposited to account ID: %d",
                amount,
                accountId);
    }

    public Account findById(Long accountId) {
        return accounts.stream()
                .filter(account -> account.getId().equals(accountId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Account with ID " + accountId + " not found"));
    }

    public void transfer(Long sourceAccountId, Long targetAccountId, Double amount) {
        checkAmount(amount);
        Account sourceAccount = findById(sourceAccountId);
        Account targetAccount = findById(targetAccountId);
        if (sourceAccount.getMoneyAmount() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        sourceAccount.withdrawn(amount);
        if (!Objects.equals(sourceAccount.getUserId(), targetAccount.getUserId())) {
            amount = (1.0 - transferCommission) * amount;
            targetAccount.deposit(amount);
        } else {
            targetAccount.deposit(amount);
        }
        System.out.printf("\nAmount %.1f transferred from account ID %d to account ID %d",
                amount,
                sourceAccountId,
                targetAccountId);
    }

    public void withdraw(Long accountId, Double amount) {
        checkAmount(amount);
        Account account = findById(accountId);
        account.withdrawn(amount);

        System.out.printf("\nAmount %.1f withdrawn from account ID: %d",
                amount,
                accountId);
    }

    private static void checkAmount(Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}
