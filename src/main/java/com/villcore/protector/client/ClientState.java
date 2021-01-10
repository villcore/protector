package com.villcore.protector.client;

import java.util.Objects;

public class ClientState {

    private ClientIdentity clientIdentity;
    private ClientStateCode stateCode;
    private Long lastUpdateTimestamp;

    public ClientState(ClientIdentity clientIdentity, ClientStateCode stateCode, Long lastUpdateTimestamp) {
        this.clientIdentity = clientIdentity;
        this.stateCode = stateCode;
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public ClientIdentity getClientIdentity() {
        return clientIdentity;
    }

    public void setClientIdentity(ClientIdentity clientIdentity) {
        this.clientIdentity = clientIdentity;
    }

    public ClientStateCode getStateCode() {
        return stateCode;
    }

    public void setStateCode(ClientStateCode stateCode) {
        this.stateCode = stateCode;
    }

    public Long getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(Long lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientState that = (ClientState) o;
        return clientIdentity.equals(that.clientIdentity) &&
                stateCode == that.stateCode &&
                lastUpdateTimestamp.equals(that.lastUpdateTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientIdentity, stateCode, lastUpdateTimestamp);
    }
}
