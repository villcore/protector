package com.villcore.protector.client;

import com.villcore.protector.discovery.Discovery;
import com.villcore.protector.rpc.client.RequestClient;
import com.villcore.protector.rpc.request.ClientIdentityRequest;
import com.villcore.protector.utils.NamedThreadFactory;
import com.villcore.protector.utils.NetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientSupervisor {

    private static final Logger log = LoggerFactory.getLogger(ClientSupervisor.class);

    private final int serverPort;
    private final Discovery hostDiscovery;
    private final RequestClient requestClient;
    private final ClientManager clientManager;
    private final ScheduledExecutorService scheduler;

    public ClientSupervisor(int serverPort,
                            Discovery hostDiscovery,
                            RequestClient requestClient,
                            ClientManager clientManager) {
        this.serverPort = serverPort;
        this.hostDiscovery = hostDiscovery;
        this.requestClient = requestClient;
        this.clientManager = clientManager;
        this.scheduler = Executors.newScheduledThreadPool(3, new NamedThreadFactory("client-supervisor"));
    }

    public void startup() {
        scheduler.scheduleAtFixedRate(this::safeReportSelfClientState, 60L, 60L, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::expireClient, 60L, 2 * 60L, TimeUnit.SECONDS);
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }

    private void safeReportSelfClientState() {
        try {
            reportSelfClientState();
        } catch (Exception e) {
            log.error("Report self client state error ", e);
        }
    }

    private void reportSelfClientState() {
        // list all client
        hostDiscovery.lookupHost().stream()
                .filter(inetAddress -> !inetAddress.getHostAddress().equals(NetUtil.getLocalHostAddress().getHostAddress()))
                .forEach(inetAddress -> {
                    try {
                        requestClient.touch(getServerAddress(inetAddress), new ClientIdentityRequest(clientManager.localClientIdentity()));
                    } catch (Exception e) {
                        log.error("Invoke touch client server error ", e);
                    }
                }
        );
    }

    private String getServerAddress(InetAddress inetAddress) {
        return inetAddress.getHostAddress() + ":" + serverPort;
    }

    private void expireClient() {
        clientManager.expireClient();
    }
}
