package com.villcore.protector;

import com.villcore.protector.client.ClientManager;
import com.villcore.protector.client.ClientSupervisor;
import com.villcore.protector.client.command.CommandBroadcaster;
import com.villcore.protector.command.CommandExecuteHandlers;
import com.villcore.protector.discovery.IpScanDiscovery;
import com.villcore.protector.rpc.client.RequestClient;
import com.villcore.protector.rpc.server.RequestServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Bootstrap {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        int serverPort = 12345;

        // start rpc server
        CommandExecuteHandlers commandExecuteHandlers = new CommandExecuteHandlers();
        RequestServer requestServer = new RequestServer(serverPort, commandExecuteHandlers);
        requestServer.startup();

        // start
        RequestClient requestClient = new RequestClient();
        requestClient.startup();

        ClientManager clientManager = new ClientManager();
        clientManager.startup();

        IpScanDiscovery discovery = new IpScanDiscovery();
        discovery.startup();

        ClientSupervisor clientSupervisor = new ClientSupervisor(serverPort, discovery, requestClient, clientManager);
        clientSupervisor.startup();

        CommandBroadcaster commandBroadcaster = new CommandBroadcaster(serverPort, clientManager, requestClient);
        commandBroadcaster.startup();
    }
}
