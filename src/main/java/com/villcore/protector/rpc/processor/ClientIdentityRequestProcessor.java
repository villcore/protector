package com.villcore.protector.rpc.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AbstractUserProcessor;
import com.villcore.protector.rpc.request.ClientIdentityRequest;

public class ClientIdentityRequestProcessor extends AbstractUserProcessor<ClientIdentityRequest> {

    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, ClientIdentityRequest request) {
        System.out.println(request);
    }

    @Override
    public Object handleRequest(BizContext bizCtx, ClientIdentityRequest request) throws Exception {
        return null;
    }

    @Override
    public String interest() {
        return ClientIdentityRequest.class.getName();
    }
}
