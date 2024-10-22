package vudovenko.dev.hw.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.hw.accounts.services.AccountService;
import vudovenko.dev.hw.operations.consoleInput.ConsoleInputService;
import vudovenko.dev.hw.operations.enums.ConsoleOperationType;

@Component
@RequiredArgsConstructor
public class WithdrawAccountCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.ACCOUNT_WITHDRAW;

    private final AccountService accountService;
    private final ConsoleInputService consoleInputService;

    @Override
    public void execute() {
        Long accountId = consoleInputService
                .readLong("Enter account ID:");
        Double amount = consoleInputService
                .readDouble("Enter amount to withdraw:");
        accountService.withdraw(accountId, amount);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}