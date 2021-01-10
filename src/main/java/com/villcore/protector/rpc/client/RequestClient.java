package com.villcore.protector.rpc.client;

import com.alipay.remoting.rpc.RpcClient;
import com.villcore.protector.rpc.request.ClientIdentityRequest;

public class RequestClient {

    private final RpcClient rpcClient;

    public RequestClient() {
        this.rpcClient = new RpcClient();
    }

    @SuppressWarnings("unchecked")
    public <T> T touch(String address, ClientIdentityRequest clientIdentityRequest) throws Exception {
        return (T) this.rpcClient.invokeSync(address, clientIdentityRequest, 5000);
    }

    @SuppressWarnings("unchecked")
    public <T> T invoke(String address, Object obj) throws Exception {
        return (T) this.rpcClient.invokeSync(address, obj, 5000);
    }

    public void startup() {}

    public void shutdown() {}
}
