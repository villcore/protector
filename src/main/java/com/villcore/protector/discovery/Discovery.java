package com.villcore.protector.discovery;

import java.net.InetAddress;
import java.util.List;

public interface Discovery {

    List<InetAddress> lookupHost();

    void startup();

    void shutdown();
}
