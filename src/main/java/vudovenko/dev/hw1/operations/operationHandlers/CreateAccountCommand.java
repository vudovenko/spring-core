package vudovenko.dev.hw1.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.hw1.accounts.services.AccountService;
import vudovenko.dev.hw1.operations.consoleInput.ConsoleInputService;
import vudovenko.dev.hw1.users.models.User;
import vudovenko.dev.hw1.users.services.UserService;
import vudovenko.dev.hw1.operations.enums.ConsoleOperationType;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateAccountCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.ACCOUNT_CREATE;

    private final AccountService accountService;
    private final UserService userService;
    private final ConsoleInputService consoleInputService;

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
