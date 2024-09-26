package vudovenko.dev.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.operations.enums.ConsoleOperationType;
import vudovenko.dev.operations.consoleInput.ConsoleInputService;
import vudovenko.dev.users.controllers.UsersController;

@Component
@RequiredArgsConstructor
public class CreateUserCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.USER_CREATE;

    private final UsersController usersController;
    private final ConsoleInputService consoleInputService;

    @Override
    public void execute() {
        String login = consoleInputService
                .readString("Enter login for new user:");
        usersController.createUser(login);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
