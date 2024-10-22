package vudovenko.dev.hw.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.hw.accounts.services.AccountService;
import vudovenko.dev.hw.operations.consoleInput.ConsoleInputService;
import vudovenko.dev.hw.operations.enums.ConsoleOperationType;
import vudovenko.dev.hw.users.models.User;
import vudovenko.dev.hw.users.services.UserService;

@Component
@RequiredArgsConstructor
public class CreateUserCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.USER_CREATE;

    private final UserService userService;
    private final AccountService accountService;
    private final ConsoleInputService consoleInputService;

    @Override
    public void execute() {
        String login = consoleInputService
                .readString("Enter login for new user:");
        User user = userService.createUser(login);
        accountService.createAccount(user);
        System.out.println("User created: " + user);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}