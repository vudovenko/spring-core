package vudovenko.dev.hw.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.hw.accounts.services.AccountService;
import vudovenko.dev.hw.operations.consoleInput.ConsoleInputService;
import vudovenko.dev.hw.operations.enums.ConsoleOperationType;

@Component
@RequiredArgsConstructor
public class CloseAccountCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.ACCOUNT_CLOSE;

    private final AccountService accountService;
    private final ConsoleInputService consoleInputService;

    @Override
    public void execute() {
        Long accountId = consoleInputService
                .readLong("Enter account ID to close:");
        accountService.closeAccount(accountId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
