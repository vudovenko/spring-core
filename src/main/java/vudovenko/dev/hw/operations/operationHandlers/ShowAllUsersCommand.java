package vudovenko.dev.hw.operations.operationHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vudovenko.dev.hw.users.services.UserService;
import vudovenko.dev.hw.operations.enums.ConsoleOperationType;

@Component
@RequiredArgsConstructor
public class ShowAllUsersCommand implements OperationCommand {

    private static final ConsoleOperationType operationType = ConsoleOperationType.SHOW_ALL_USERS;

    private final UserService userService;

    @Override
    public void execute() {
        userService.showAllUsers();
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return operationType;
    }
}
