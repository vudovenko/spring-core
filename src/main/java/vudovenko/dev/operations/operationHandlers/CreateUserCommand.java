package vudovenko.dev.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.operations.enums.ConsoleOperationType;
import vudovenko.dev.operations.listeners.OperationsConsoleListener;
import vudovenko.dev.operations.utils.OperationReader;
import vudovenko.dev.users.controllers.UsersController;

@Component
@RequiredArgsConstructor
public class CreateUserCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.USER_CREATE;

    private final UsersController usersController;

    @Override
    public void execute() {
        String login = OperationReader
                .readString("Enter login for new user:",
                        OperationsConsoleListener.scanner);
        usersController.createUser(login);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
