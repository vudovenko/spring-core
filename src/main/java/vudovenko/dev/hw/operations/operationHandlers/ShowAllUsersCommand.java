package vudovenko.dev.hw.operations.operationHandlers;

import org.springframework.stereotype.Component;
import vudovenko.dev.hw.operations.enums.ConsoleOperationType;
import vudovenko.dev.hw.users.services.UserService;

@Component
public class ShowAllUsersCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.SHOW_ALL_USERS;

    private final UserService userService;

    public ShowAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        userService.showAllUsers();
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
