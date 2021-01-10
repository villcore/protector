package com.villcore.protector.command.handler;

public interface CommandExecuteHandler<T> {

    void execute(T context);
}
