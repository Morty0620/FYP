package com.zqh.fyp.Util.Net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtil {

    public static boolean isConnectInterNet(){
        try {
            InetAddress.getAllByName("zhangqihang.host");
            return true;

        } catch (UnknownHostException e) {
            return false;
        }
    }
}
