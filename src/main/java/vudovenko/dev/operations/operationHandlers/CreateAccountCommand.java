package vudovenko.dev.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.accounts.controllers.AccountController;
import vudovenko.dev.operations.enums.ConsoleOperationType;
import vudovenko.dev.operations.consoleInput.ConsoleInputService;

@Component
@RequiredArgsConstructor
public class CreateAccountCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.ACCOUNT_CREATE;

    private final AccountController accountController;
    private final ConsoleInputService consoleInputService;

    @Override
    public void execute() {
        Long userId = consoleInputService
                .readLong("Enter the user ID for which to create an account:");
        accountController.createAccount(userId);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
