package vudovenko.dev.operations.operationHandlers;

import vudovenko.dev.operations.enums.ConsoleOperationType;

public interface OperationCommand {

    void execute();

    ConsoleOperationType getOperationType();
}
