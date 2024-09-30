package vudovenko.dev.operations.listeners;

import org.springframework.stereotype.Service;
import vudovenko.dev.operations.enums.ConsoleOperationType;
import vudovenko.dev.operations.operationHandlers.OperationCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
public class OperationsConsoleListener implements Runnable {

    public final Scanner scanner;
    private final Map<ConsoleOperationType, OperationCommand> commandMap;

    public OperationsConsoleListener(Scanner scanner, List<OperationCommand> commands) {
        this.scanner = scanner;
        commandMap = new HashMap<>();
        commands.forEach(command -> commandMap.put(command.getOperationType(), command));
    }

    @Override
    public void run() {

        while (true) {
            showAllOperations();
            ConsoleOperationType operation = getOperation();
            if (operation == null) continue;

            try {
                commandMap.get(operation).execute();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
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

    private ConsoleOperationType getOperation() {
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
