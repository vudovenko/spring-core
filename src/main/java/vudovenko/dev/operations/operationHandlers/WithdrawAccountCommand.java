package vudovenko.dev.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.accounts.controllers.AccountController;
import vudovenko.dev.operations.enums.ConsoleOperationType;
import vudovenko.dev.operations.listeners.OperationsConsoleListener;
import vudovenko.dev.operations.utils.OperationReader;

@Component
@RequiredArgsConstructor
public class WithdrawAccountCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.ACCOUNT_WITHDRAW;

    private final AccountController accountController;

    @Override
    public void execute() {
        Long accountId = OperationReader
                .readLong("Enter account ID:",
                        OperationsConsoleListener.scanner);
        Double amount = OperationReader
                .readDouble("Enter amount to withdraw:",
                        OperationsConsoleListener.scanner);
        accountController.withdraw(accountId, amount);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
