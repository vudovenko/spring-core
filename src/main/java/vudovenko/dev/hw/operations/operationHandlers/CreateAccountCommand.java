package vudovenko.dev.hw.operations.operationHandlers;

import org.springframework.stereotype.Component;
import vudovenko.dev.hw.accounts.services.AccountService;
import vudovenko.dev.hw.operations.consoleInput.ConsoleInputService;
import vudovenko.dev.hw.operations.enums.ConsoleOperationType;
import vudovenko.dev.hw.users.models.User;
import vudovenko.dev.hw.users.services.UserService;

import java.util.Optional;

@Component
public class CreateAccountCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.ACCOUNT_CREATE;

    private final AccountService accountService;
    private final UserService userService;
    private final ConsoleInputService consoleInputService;

    public CreateAccountCommand(
            AccountService accountService,
            UserService userService,
            ConsoleInputService consoleInputService
    ) {
        this.accountService = accountService;
        this.userService = userService;
        this.consoleInputService = consoleInputService;
    }

    @Override
    public void execute() {
        Long userId = consoleInputService
                .readLong("Enter the user ID for which to create an account:");
        Optional<User> userOptional = userService.getById(userId);
        if (userOptional.isPresent()) {
            accountService.createAccount(userOptional.get());
        } else {
            System.out.printf("User with id %d not found\n", userId);
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
