package vudovenko.dev.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.operations.enums.ConsoleOperationType;
import vudovenko.dev.users.controllers.UsersController;

@Component
@RequiredArgsConstructor
public class ShowAllUsersCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.SHOW_ALL_USERS;

    private final UsersController usersController;

    @Override
    public void execute() {
        usersController.showAllUsers();
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
