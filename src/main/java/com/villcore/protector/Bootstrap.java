package com.villcore.protector;

import com.villcore.protector.client.ClientManager;
import com.villcore.protector.client.ClientSupervisor;
import com.villcore.protector.client.command.CommandBroadcaster;
import com.villcore.protector.command.CommandExecuteHandlers;
import com.villcore.protector.discovery.IpScanDiscovery;
import com.villcore.protector.gui.MainFrame;
import com.villcore.protector.rpc.client.RequestClient;
import com.villcore.protector.rpc.server.RequestServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Bootstrap {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) throws InterruptedException {
        ClientManager clientManager = new ClientManager();
        clientManager.startup();

        // start rpc server
        int serverPort = 12345;
        CommandExecuteHandlers commandExecuteHandlers = new CommandExecuteHandlers();
        RequestServer requestServer = new RequestServer(serverPort, clientManager, commandExecuteHandlers);
        requestServer.startup();

        // start
        RequestClient requestClient = new RequestClient();
        requestClient.startup();

        IpScanDiscovery discovery = new IpScanDiscovery();
        discovery.startup();

        ClientSupervisor clientSupervisor = new ClientSupervisor(serverPort, discovery, requestClient, clientManager);
        clientSupervisor.startup();

        CommandBroadcaster commandBroadcaster = new CommandBroadcaster(serverPort, clientManager, requestClient);
        commandBroadcaster.startup();

        new MainFrame(clientManager, commandBroadcaster);
    }
}
