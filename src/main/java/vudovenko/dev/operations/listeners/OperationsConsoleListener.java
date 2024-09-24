package vudovenko.dev.operations.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vudovenko.dev.accounts.controllers.AccountController;
import vudovenko.dev.operations.enums.Operations;
import vudovenko.dev.users.controllers.UsersController;

import java.util.Scanner;

@Service("operationsConsoleListener")
@RequiredArgsConstructor
public class OperationsConsoleListener implements Runnable {

    private final UsersController usersController;
    private final AccountController accountController;

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        Operations operation;
        while (true) {
            showAllOperations();
            operation = getOperation(scanner);
            if (operation == null) continue;

            switch (operation) {
                case USER_CREATE -> {
                    System.out.println("Enter login for new user:");
                    String login = scanner.nextLine();
                    usersController.createUser(login);
                }
                case SHOW_ALL_USERS -> usersController.showAllUsers();
                case ACCOUNT_CREATE -> {
                    System.out.println("Enter the user id for which to create an account:");
                    Long userId = Long.valueOf(scanner.nextLine());
                    accountController.createAccount(userId);
                }
                case ACCOUNT_CLOSE -> {
                    System.out.println("Enter account ID to close:");
                    Long accountId = Long.valueOf(scanner.nextLine());
                    accountController.closeAccount(accountId);
                }
                case ACCOUNT_DEPOSIT -> {
                    System.out.println("Enter account ID:");
                    Long accountId = Long.valueOf(scanner.nextLine());
                    System.out.println("Enter amount to deposit:");
                    Double amount = Double.valueOf(scanner.nextLine());
                    accountController.deposit(accountId, amount);
                }
                case ACCOUNT_TRANSFER -> {
                    System.out.println("Enter source account ID:");
                    Long sourceAccountId = Long.valueOf(scanner.nextLine());
                    System.out.println("Enter target account ID:");
                    Long targetAccountId = Long.valueOf(scanner.nextLine());
                    System.out.println("Enter amount to transfer:");
                    Double amount = Double.valueOf(scanner.nextLine());
                    accountController.transfer(sourceAccountId, targetAccountId, amount);
                }
                case ACCOUNT_WITHDRAW -> {
                    System.out.println("Enter account ID:");
                    Long accountId = Long.valueOf(scanner.nextLine());
                    System.out.println("Enter amount to withdraw:");
                    Double amount = Double.valueOf(scanner.nextLine());
                    accountController.withdraw(accountId, amount);
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

    private Operations getOperation(Scanner scanner) {
        Operations operation;
        String consoleOperation = scanner.nextLine();
        try {
            operation = Operations.valueOf(consoleOperation.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong operation type");
            return null;
        }
        return operation;
    }
}
