package vudovenko.dev.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.accounts.controllers.AccountController;
import vudovenko.dev.operations.enums.ConsoleOperationType;
import vudovenko.dev.operations.listeners.OperationsConsoleListener;
import vudovenko.dev.operations.utils.OperationReader;

@Component
@RequiredArgsConstructor
public class TransferAccountCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.ACCOUNT_TRANSFER;

    private final AccountController accountController;

    @Override
    public void execute() {
        Long sourceAccountId = OperationReader
                .readLong("Enter source account ID:",
                        OperationsConsoleListener.scanner);
        Long targetAccountId = OperationReader
                .readLong("Enter target account ID:",
                        OperationsConsoleListener.scanner);
        Double amount = OperationReader
                .readDouble("Enter amount to transfer:",
                        OperationsConsoleListener.scanner);
        accountController.transfer(sourceAccountId, targetAccountId, amount);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
