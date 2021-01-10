package com.villcore.protector.rpc.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AbstractUserProcessor;
import com.villcore.protector.client.ClientManager;
import com.villcore.protector.command.CommandResultCode;
import com.villcore.protector.rpc.request.ClientIdentityRequest;

public class ClientIdentityRequestProcessor extends AbstractUserProcessor<ClientIdentityRequest> {

    private ClientManager clientManager;

    public ClientIdentityRequestProcessor(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, ClientIdentityRequest request) {
        clientManager.touch(request.getClientIdentity());
        asyncCtx.sendResponse(CommandResultCode.SUCCESS);
    }

    @Override
    public Object handleRequest(BizContext bizCtx, ClientIdentityRequest request) throws Exception {
        clientManager.touch(request.getClientIdentity());
        return CommandResultCode.SUCCESS;
    }

    @Override
    public String interest() {
        return ClientIdentityRequest.class.getName();
    }
}
