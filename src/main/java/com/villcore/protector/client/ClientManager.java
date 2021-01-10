package com.villcore.protector.client;

import com.villcore.protector.utils.NetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class ClientManager {

    private final ConcurrentMap<String, ClientState> clientState;

    public ClientManager() {
        this.clientState = new ConcurrentHashMap<>(32);
    }

    public void addClient(ClientIdentity clientIdentity) {
        String address = clientIdentity.getAddress();
        ClientState curClientState = clientState.getOrDefault(address, new ClientState(clientIdentity, ClientStateCode.ONLINE, System.currentTimeMillis()));
        clientState.put(address, curClientState);
    }

    public void removeClient(ClientIdentity clientIdentity) {
       clientState.remove(clientIdentity.getAddress());
    }

    public void touch(ClientIdentity clientIdentity) {
        String address = clientIdentity.getAddress();
        ClientState curClientState = clientState.getOrDefault(address, new ClientState(clientIdentity, ClientStateCode.ONLINE, System.currentTimeMillis()));
        curClientState.setLastUpdateTimestamp(System.currentTimeMillis());
        clientState.put(address, curClientState);
    }

    public List<ClientState> listAllClientState() {
        return new ArrayList<>(clientState.values());
    }

    public List<ClientState> listOnlineClientState() {
        return listAllClientState().stream()
                .filter(clientState -> clientState.getStateCode() == ClientStateCode.ONLINE)
                .collect(Collectors.toList());
    }

    public List<ClientState> listOffLineClientState() {
        return listAllClientState().stream()
                .filter(clientState -> clientState.getStateCode() == ClientStateCode.OFFLINE)
                .collect(Collectors.toList());
    }

    public ClientIdentity localClientIdentity() {
        String hostname = NetUtil.getHostname();
        String hostAddress = NetUtil.getLocalHostAddress().getHostAddress();
        return new ClientIdentity(null, hostname, hostAddress);
    }

    public void expireClient() {
        clientState.values().forEach(state -> {
            if (state == null) {
                return;
            }

            if (System.currentTimeMillis() - state.getLastUpdateTimestamp() > 1.5 * 60* 1000L) {
                state.setStateCode(ClientStateCode.OFFLINE);
            }
        });
    }

    public void startup() {}

    public void shutdown() {}
}
