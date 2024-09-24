package vudovenko.dev.users.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.accounts.services.AccountService;
import vudovenko.dev.users.models.User;
import vudovenko.dev.users.services.UserService;

@Component
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;
    private final AccountService accountService;

    public void createUser(String login) {
        User user;
        try {
            user = userService.createUser(login);
            accountService.createAccount(user);
            System.out.println("User created: " + user);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showAllUsers() {
        userService.showAllUsers();
    }
}
