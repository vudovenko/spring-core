package vudovenko.dev.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.accounts.controllers.AccountController;
import vudovenko.dev.operations.enums.ConsoleOperationType;
import vudovenko.dev.operations.listeners.OperationsConsoleListener;
import vudovenko.dev.operations.utils.OperationReader;

@Component
@RequiredArgsConstructor
public class CreateAccountCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.ACCOUNT_CREATE;

    private final AccountController accountController;

    @Override
    public void execute() {
        Long userId = OperationReader
                .readLong("Enter the user ID for which to create an account:",
                        OperationsConsoleListener.scanner);
        accountController.createAccount(userId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
