package com.villcore.protector.client;

import java.io.Serializable;
import java.util.Objects;

public final class ClientIdentity implements Serializable {

    private String username;
    private String host;
    private String address;

    public ClientIdentity(String username, String host, String address) {
        this.username = username;
        this.host = host;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientIdentity that = (ClientIdentity) o;
        return username.equals(that.username) &&
                host.equals(that.host) &&
                address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, host, address);
    }
}
