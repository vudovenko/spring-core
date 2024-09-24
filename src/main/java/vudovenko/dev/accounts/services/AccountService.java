package vudovenko.dev.accounts.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vudovenko.dev.accounts.models.Account;
import vudovenko.dev.users.models.User;
import vudovenko.dev.users.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountService {

    private static Long idCounter = 1L;

    @Value("${account.moneyAmount}") // Получаем значение из application.properties
    private Long defaultMoneyAmount;

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

        System.out.printf("New account created with ID: %d for user: %s",
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
                        userAccounts.get(1).addMoney(account.getMoneyAmount());
                    } else {
                        firstAccount.addMoney(account.getMoneyAmount());
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
}
