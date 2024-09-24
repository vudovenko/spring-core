package vudovenko.dev.accounts.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vudovenko.dev.accounts.models.Account;
import vudovenko.dev.users.models.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class AccountService {

    private static Long idCounter = 1L;

    @Value("${account.moneyAmount}") // Получаем значение из application.properties
    private Long defaultMoneyAmount;

    private final Set<Account> accounts = new HashSet<>();


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
}
