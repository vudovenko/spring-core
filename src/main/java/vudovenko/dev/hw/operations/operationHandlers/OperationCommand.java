package vudovenko.dev.hw.operations.operationHandlers;

import vudovenko.dev.hw.operations.enums.ConsoleOperationType;

public interface OperationCommand {

    void execute();

    ConsoleOperationType getOperationType();
}
