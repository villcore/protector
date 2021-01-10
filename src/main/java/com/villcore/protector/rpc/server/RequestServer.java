package com.villcore.protector.rpc.server;

import com.alipay.remoting.rpc.RpcServer;
import com.villcore.protector.client.ClientManager;
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

    private ClientManager clientManager;
    private CommandExecuteHandlers commandExecuteHandlers;

    public RequestServer(int port, ClientManager clientManager, CommandExecuteHandlers commandExecuteHandlers) {
        this.port = port;
        this.clientManager = clientManager;
        this.commandExecuteHandlers = commandExecuteHandlers;
        initRpcServer(port, clientManager, commandExecuteHandlers);
    }

    private void initRpcServer(int port, ClientManager clientManager, CommandExecuteHandlers commandExecuteHandlers) {
        this.rpcServer = new RpcServer(port);
        this.requestWorkerExecutor = new ThreadPoolExecutor(1, 10, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));

        this.rpcServer.registerUserProcessor(new ActionRequestProcessor(this.commandExecuteHandlers));
        this.rpcServer.registerUserProcessor(new ClientIdentityRequestProcessor(clientManager));
    }

    public void startup() {
        this.rpcServer.startup();
    }

    public void shutdown() {
        this.rpcServer.shutdown();
        this.requestWorkerExecutor.shutdownNow();
    }
}
