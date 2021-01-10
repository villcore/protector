package com.villcore.protector.client.command;

import com.villcore.protector.client.ClientManager;
import com.villcore.protector.rpc.client.RequestClient;
import com.villcore.protector.rpc.request.ActionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandBroadcaster {

    private static final Logger log = LoggerFactory.getLogger(CommandBroadcaster.class);

    private int serverPort;
    private ClientManager clientManager;
    private RequestClient requestClient;

    public CommandBroadcaster(int serverPort, ClientManager clientManager, RequestClient requestClient) {
        this.serverPort = serverPort;
        this.clientManager = clientManager;
        this.requestClient = requestClient;
    }

    public void startup() {}

    public void shutdown() {}

    public void broadcastCommand(int commandCode) {
        clientManager.listAllClientState().forEach(clientState -> {
            String address = clientState.getClientIdentity().getAddress();
            try {
                requestClient.invoke(getServerAddress(address), new ActionRequest(commandCode, ""));
            } catch (Exception e) {
                log.error("BroadCast command to address {} error ", e);
            }
        });
    }

    private String getServerAddress(String address) {
        return address + ":" + serverPort;
    }
}
