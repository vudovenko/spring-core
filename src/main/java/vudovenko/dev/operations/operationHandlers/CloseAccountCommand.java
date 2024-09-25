package vudovenko.dev.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.accounts.controllers.AccountController;
import vudovenko.dev.operations.enums.ConsoleOperationType;
import vudovenko.dev.operations.listeners.OperationsConsoleListener;
import vudovenko.dev.operations.utils.OperationReader;

@Component
@RequiredArgsConstructor
public class CloseAccountCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.ACCOUNT_CLOSE;

    private final AccountController accountController;

    @Override
    public void execute() {
        Long accountId = OperationReader
                .readLong("Enter account ID to close:",
                        OperationsConsoleListener.scanner);
        accountController.closeAccount(accountId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
