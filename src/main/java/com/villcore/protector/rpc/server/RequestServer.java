package com.villcore.protector.rpc.server;

import com.alipay.remoting.rpc.RpcServer;
import com.villcore.protector.command.CommandExecuteHandlers;
import com.villcore.protector.rpc.processor.ActionRequestProcessor;
import com.villcore.protector.rpc.processor.ClientIdentityRequestProcessor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RequestServer {

    private int port;
    private RpcServer rpcServer;
    private ThreadPoolExecutor requestWorkerExecutor;

    private CommandExecuteHandlers commandExecuteHandlers;

    public RequestServer(int port, CommandExecuteHandlers commandExecuteHandlers) {
        this.port = port;
        this.commandExecuteHandlers = commandExecuteHandlers;
        initRpcServer(port, commandExecuteHandlers);
    }

    private void initRpcServer(int port, CommandExecuteHandlers commandExecuteHandlers) {
        this.rpcServer = new RpcServer(port);
        this.requestWorkerExecutor = new ThreadPoolExecutor(1, 10, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));

        this.rpcServer.registerUserProcessor(new ActionRequestProcessor(this.commandExecuteHandlers));
        this.rpcServer.registerUserProcessor(new ClientIdentityRequestProcessor());
    }

    public void startup() {
        this.rpcServer.startup();
    }

    public void shutdown() {
        this.rpcServer.shutdown();
        this.requestWorkerExecutor.shutdownNow();
    }
}
