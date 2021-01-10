package com.villcore.protector.command;

import com.villcore.protector.command.handler.CommandExecuteHandler;
import com.villcore.protector.command.handler.HiddenDiskExecuteHandler;

public class CommandExecuteHandlers {

    private CommandExecuteHandler<Object> hiddenDiskCommand = new HiddenDiskExecuteHandler();

    public void handleCommand(int commandCode) {
        if (commandCode == CommandCode.HIDDEN_DISK_COMMAND) {
            executeHiddenDiskCommand();
        }
    }

    private void executeHiddenDiskCommand() {
        hiddenDiskCommand.execute(new Object());
    }
}


