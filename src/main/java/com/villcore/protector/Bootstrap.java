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
        System.out.println("c");

        // start rpc server
        int serverPort = 12345;
        CommandExecuteHandlers commandExecuteHandlers = new CommandExecuteHandlers();
        RequestServer requestServer = new RequestServer(serverPort, clientManager, commandExecuteHandlers);
        requestServer.startup();
        System.out.println("a");

        // start
        RequestClient requestClient = new RequestClient();
        requestClient.startup();
        System.out.println("b");


        IpScanDiscovery discovery = new IpScanDiscovery();
        discovery.startup();
        System.out.println("d");

        ClientSupervisor clientSupervisor = new ClientSupervisor(serverPort, discovery, requestClient, clientManager);
        clientSupervisor.startup();
        System.out.println("3");

        CommandBroadcaster commandBroadcaster = new CommandBroadcaster(serverPort, clientManager, requestClient);
        commandBroadcaster.startup();
        System.out.println("f");

        new MainFrame(clientManager, commandBroadcaster);
    }
}
