package vudovenko.dev.accounts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.accounts.services.AccountService;
import vudovenko.dev.users.models.User;
import vudovenko.dev.users.services.UserService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    public void createAccount(Long userId) {
        Optional<User> userOptional = userService.getById(userId);
        if (userOptional.isPresent()) {
            accountService.createAccount(userOptional.get());
        } else {
            System.out.printf("User with id %d not found\n", userId);
        }
    }

    public void closeAccount(Long accountId) {
        try {
            accountService.closeAccount(accountId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deposit(Long accountId, Double amount) {
        try {
            accountService.deposit(accountId, amount);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void transfer(Long sourceAccountId, Long targetAccountId, Double amount) {
        try {
            accountService.transfer(sourceAccountId, targetAccountId, amount);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void withdraw(Long accountId, Double amount) {
        try {
            accountService.withdraw(accountId, amount);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
