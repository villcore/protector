package com.villcore.protector.command.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiddenDiskExecuteHandler implements CommandExecuteHandler<Object> {

    private static final Logger log = LoggerFactory.getLogger(HiddenDiskExecuteHandler.class);

    @Override
    public void execute(Object context) {
        log.info("Execute hidden disk command.");
    }
}
