package com.villcore.protector.rpc.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AbstractUserProcessor;
import com.villcore.protector.command.CommandExecuteHandlers;
import com.villcore.protector.command.CommandResultCode;
import com.villcore.protector.rpc.request.ActionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionRequestProcessor extends AbstractUserProcessor<ActionRequest> {

    private static final Logger log = LoggerFactory.getLogger(ActionRequestProcessor.class);

    private CommandExecuteHandlers commandExecuteHandlers;

    public ActionRequestProcessor(CommandExecuteHandlers commandExecuteHandlers) {
        this.commandExecuteHandlers = commandExecuteHandlers;
    }

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, ActionRequest request) {
        asyncCtx.sendResponse(handleCommand(request));
    }

    @Override
    public Object handleRequest(BizContext bizCtx, ActionRequest request) throws Exception {
        return handleCommand(request);
    }

    @Override
    public String interest() {
        return ActionRequest.class.getName();
    }

    private Integer handleCommand(ActionRequest request) {
        int commandResultCode = CommandResultCode.SUCCESS;
        try {
            this.commandExecuteHandlers.handleCommand(request.getCommandCode());
        } catch (Exception e) {
            commandResultCode = CommandResultCode.FAIL;
            log.error("Handle action request error, command code: {}, request: {}", request.getCommandCode(), request);
        }
        return commandResultCode;
    }
}
