package com.villcore.protector.utils;

import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetUtil {

    public static List<InetAddress> getAllClassCAddress(InetAddress address) {
        byte[] addressParts = address.getAddress();
        List<InetAddress> classCAddress = new ArrayList<>(256);
        for (int i = 1; i <= 255; i++) {
            byte newAddressPart = (byte) (i & 0xFF);
            try {
                InetAddress inet4Address = Inet4Address.getByAddress(
                                new byte[]{addressParts[0], addressParts[1], addressParts[2], newAddressPart});
                classCAddress.add(inet4Address);
            } catch (UnknownHostException exception) {
                // just ignore
            }
        }
        return classCAddress;
    }

    @Nullable
    public static InetAddress getLocalHostAddress() {
        NetworkInterface availableNetworkInterface = null;
        try {
            // TODO: check os
            NetworkInterface eth0 = NetworkInterface.getByName("en0");
            NetworkInterface wlan0 = NetworkInterface.getByName("wlan0");
            if (eth0 == null && wlan0 == null) {
                throw new IllegalStateException("Can not found available network interface ");
            }
            availableNetworkInterface = eth0 != null ? eth0 : wlan0;
        } catch (SocketException e) {
            // just ignore
        }

        if (availableNetworkInterface == null) {
            return null;
        }

        Enumeration<InetAddress> addressEnumeration = availableNetworkInterface.getInetAddresses();
        if (addressEnumeration.hasMoreElements()) {
            return addressEnumeration.nextElement();
        }
        return null;
    }

    public static boolean reachable(InetAddress inetAddress) {
        try {
            return inetAddress.isReachable(100);
        } catch (IOException e) {
            // just ignore
        }
        return false;
    }

    @Nullable
    public static String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            // just ignore
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getHostname());
    }
}
