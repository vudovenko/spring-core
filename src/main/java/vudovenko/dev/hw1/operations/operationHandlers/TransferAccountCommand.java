package vudovenko.dev.hw1.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.hw1.accounts.services.AccountService;
import vudovenko.dev.hw1.operations.consoleInput.ConsoleInputService;
import vudovenko.dev.hw1.operations.enums.ConsoleOperationType;

@Component
@RequiredArgsConstructor
public class TransferAccountCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.ACCOUNT_TRANSFER;

    private final AccountService accountService;
    private final ConsoleInputService consoleInputService;

    @Override
    public void execute() {
        Long sourceAccountId = consoleInputService
                .readLong("Enter source account ID:");
        Long targetAccountId = consoleInputService
                .readLong("Enter target account ID:");
        Double amount = consoleInputService
                .readDouble("Enter amount to transfer:");
        accountService.transfer(sourceAccountId, targetAccountId, amount);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
