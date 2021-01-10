package com.villcore.protector.discovery;

import com.villcore.protector.utils.NamedThreadFactory;
import com.villcore.protector.utils.NetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class IpScanDiscovery implements Discovery {

    private static final Logger log = LoggerFactory.getLogger(IpScanDiscovery.class);

    private final ScheduledExecutorService scheduler;
    private final Set<InetAddress> hostSet;

    public IpScanDiscovery() {
        this.scheduler = Executors.newScheduledThreadPool(2, new NamedThreadFactory("ip-scan-discovery-scheduler"));
        this.hostSet = new CopyOnWriteArraySet<>();
    }

    @Override
    public List<InetAddress> lookupHost() {
        return new ArrayList<>(hostSet);
    }

    @Override
    public void startup() {
        this.scheduler.scheduleAtFixedRate(this::safeAddAvailableHost, 30L, 60L, TimeUnit.SECONDS);
        log.info("Start up IpScanDiscovery");
    }

    @Override
    public void shutdown() {
        scheduler.shutdownNow();
        log.info("Shutdown IpScanDiscovery");
    }

    private void safeAddAvailableHost() {
        long startTimeMillis = System.currentTimeMillis();
        addAvailableHost();
        log.info("Scan all available host use time {} ms", System.currentTimeMillis() - startTimeMillis);
    }

    private void addAvailableHost() {
        hostSet.addAll(scanAllAvailableHost());
    }

    private List<InetAddress> scanAllAvailableHost() {
        InetAddress localHostAddress = NetUtil.getLocalHostAddress();
        if (localHostAddress != null) {
            return NetUtil.getAllClassCAddress(localHostAddress).stream()
                    .filter(NetUtil::reachable)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
