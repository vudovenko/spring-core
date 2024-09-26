package vudovenko.dev.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.accounts.controllers.AccountController;
import vudovenko.dev.operations.enums.ConsoleOperationType;
import vudovenko.dev.operations.consoleInput.ConsoleInputService;

@Component
@RequiredArgsConstructor
public class TransferAccountCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.ACCOUNT_TRANSFER;

    private final AccountController accountController;
    private final ConsoleInputService consoleInputService;

    @Override
    public void execute() {
        Long sourceAccountId = consoleInputService
                .readLong("Enter source account ID:");
        Long targetAccountId = consoleInputService
                .readLong("Enter target account ID:");
        Double amount = consoleInputService
                .readDouble("Enter amount to transfer:");
        accountController.transfer(sourceAccountId, targetAccountId, amount);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
