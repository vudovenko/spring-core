package vudovenko.dev.hw.operations.operationHandlers;

import org.springframework.stereotype.Component;
import vudovenko.dev.hw.accounts.services.AccountService;
import vudovenko.dev.hw.operations.consoleInput.ConsoleInputService;
import vudovenko.dev.hw.operations.enums.ConsoleOperationType;

@Component
public class TransferAccountCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.ACCOUNT_TRANSFER;

    private final AccountService accountService;
    private final ConsoleInputService consoleInputService;

    public TransferAccountCommand(
            AccountService accountService,
            ConsoleInputService consoleInputService
    ) {
        this.accountService = accountService;
        this.consoleInputService = consoleInputService;
    }

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
