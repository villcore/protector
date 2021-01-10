package com.villcore.protector.rpc.request;

import com.villcore.protector.client.ClientIdentity;

import java.io.Serializable;

public class ClientIdentityRequest implements Serializable {

    private ClientIdentity clientIdentity;

    public ClientIdentityRequest(ClientIdentity clientIdentity) {
        this.clientIdentity = clientIdentity;
    }

    public ClientIdentity getClientIdentity() {
        return clientIdentity;
    }

    public void setClientIdentity(ClientIdentity clientIdentity) {
        this.clientIdentity = clientIdentity;
    }
}
