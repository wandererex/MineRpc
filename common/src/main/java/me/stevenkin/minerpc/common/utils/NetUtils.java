package me.stevenkin.minerpc.common.utils;

import java.net.*;
/**
 * IP and Port Helper for RPC
 */
public class NetUtils {
    /**
     * @param hostName
     * @return ip address or hostName if UnknownHostException
     */
    public static String getIpByHost(String hostName) {
        try {
            return InetAddress.getByName(hostName).getHostAddress();
        } catch (UnknownHostException e) {
            return hostName;
        }
    }

}