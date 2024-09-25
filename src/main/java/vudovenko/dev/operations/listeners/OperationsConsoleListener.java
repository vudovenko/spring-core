package vudovenko.dev.operations.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vudovenko.dev.accounts.controllers.AccountController;
import vudovenko.dev.operations.enums.ConsoleOperationType;
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

        while (true) {
            showAllOperations();
            ConsoleOperationType operation = getOperation(scanner);
            if (operation == null) continue;

            switch (operation) {
                case USER_CREATE -> handleUserCreate(scanner);
                case SHOW_ALL_USERS -> usersController.showAllUsers();
                case ACCOUNT_CREATE -> handleAccountCreate(scanner);
                case ACCOUNT_CLOSE -> handleAccountClose(scanner);
                case ACCOUNT_DEPOSIT -> handleAccountDeposit(scanner);
                case ACCOUNT_TRANSFER -> handleAccountTransfer(scanner);
                case ACCOUNT_WITHDRAW -> handleAccountWithdraw(scanner);
            }
        }
    }

    private void handleUserCreate(Scanner scanner) {
        String login = readString("Enter login for new user:", scanner);
        usersController.createUser(login);
    }

    private void handleAccountCreate(Scanner scanner) {
        Long userId = readLong("Enter the user ID for which to create an account:", scanner);
        accountController.createAccount(userId);
    }

    private void handleAccountClose(Scanner scanner) {
        Long accountId = readLong("Enter account ID to close:", scanner);
        accountController.closeAccount(accountId);
    }

    private void handleAccountDeposit(Scanner scanner) {
        Long accountId = readLong("Enter account ID:", scanner);
        Double amount = readDouble("Enter amount to deposit:", scanner);
        accountController.deposit(accountId, amount);
    }

    private void handleAccountTransfer(Scanner scanner) {
        Long sourceAccountId = readLong("Enter source account ID:", scanner);
        Long targetAccountId = readLong("Enter target account ID:", scanner);
        Double amount = readDouble("Enter amount to transfer:", scanner);
        accountController.transfer(sourceAccountId, targetAccountId, amount);
    }

    private void handleAccountWithdraw(Scanner scanner) {
        Long accountId = readLong("Enter account ID:", scanner);
        Double amount = readDouble("Enter amount to withdraw:", scanner);
        accountController.withdraw(accountId, amount);
    }

    private Long readLong(String prompt, Scanner scanner) {
        System.out.println(prompt);
        while (!scanner.hasNextLong()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        Long value = scanner.nextLong();
        scanner.nextLine();
        return value;
    }

    private Double readDouble(String prompt, Scanner scanner) {
        System.out.println(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        Double value = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        return value;
    }

    private String readString(String prompt, Scanner scanner) {
        String input = "";
        System.out.println(prompt);
        while (input.isEmpty()) {
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Строка не может быть пустой. Попробуйте еще раз.");
            }
        }
        return input;
    }

    private void showAllOperations() {
        System.out.println("""
                \nPlease enter one of operation type:
                -SHOW_ALL_USERS
                -USER_CREATE
                -ACCOUNT_CREATE
                -ACCOUNT_CLOSE
                -ACCOUNT_DEPOSIT
                -ACCOUNT_WITHDRAW
                -ACCOUNT_TRANSFER
                """);
    }

    private ConsoleOperationType getOperation(Scanner scanner) {
        ConsoleOperationType operation;
        String consoleOperation = scanner.nextLine();
        try {
            operation = ConsoleOperationType.valueOf(consoleOperation.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong operation type");
            return null;
        }
        return operation;
    }
}
