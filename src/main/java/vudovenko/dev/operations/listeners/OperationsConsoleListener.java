package vudovenko.dev.operations.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vudovenko.dev.accounts.services.AccountService;
import vudovenko.dev.operations.enums.Operations;
import vudovenko.dev.users.models.User;
import vudovenko.dev.users.services.UserService;

import java.util.Optional;
import java.util.Scanner;

@Service("operationsConsoleListener")
@RequiredArgsConstructor
public class OperationsConsoleListener implements Runnable {

    private final UserService userService;
    private final AccountService accountService;

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            showAllOperations();
            String operation = scanner.nextLine();
            Operations operations = Operations.valueOf(operation.toUpperCase());

            switch (operations) {
                case USER_CREATE -> {
                    System.out.println("Enter login for new user:");
                    String login = scanner.nextLine();
                    createUser(login);
                }
                case SHOW_ALL_USERS -> showAllUsers();
                case ACCOUNT_CREATE -> {
                    System.out.println("Enter the user id for which to create an account:");
                    Long userId = Long.valueOf(scanner.nextLine());
                    createAccount(userId);
                }
                case ACCOUNT_CLOSE -> {
                    System.out.println("Enter account ID to close:");
                    Long accountId = Long.valueOf(scanner.nextLine());
                    closeAccount(accountId);
                }
            }
        }
    }

    private void showAllOperations() {
        System.out.println("""
                                
                Please enter one of operation type:
                -ACCOUNT_CREATE
                -SHOW_ALL_USERS
                -ACCOUNT_CLOSE
                -ACCOUNT_WITHDRAW
                -ACCOUNT_DEPOSIT
                -ACCOUNT_TRANSFER
                -USER_CREATE
                """);
    }

    private void createUser(String login) {
        User user;
        try {
            user = userService.createUser(login);
            accountService.createAccount(user);
            System.out.println("User created: " + user);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showAllUsers() {
        userService.showAllUsers();
    }

    private void createAccount(Long userId) {
        Optional<User> userOptional = userService.getById(userId);
        if (userOptional.isPresent()) {
            accountService.createAccount(userOptional.get());
        } else {
            System.out.println(STR."User with id \{userId} not found");
        }
    }

    private void closeAccount(Long accountId) {
        try {
            accountService.closeAccount(accountId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
