package vudovenko.dev.hw1.operations.operationHandlers;

import vudovenko.dev.hw1.operations.enums.ConsoleOperationType;

public interface OperationCommand {

    void execute();

    ConsoleOperationType getOperationType();
}
