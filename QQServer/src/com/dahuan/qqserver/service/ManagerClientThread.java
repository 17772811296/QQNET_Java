package com.dahuan.qqserver.service;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理客户端进行通信的线程
 */
@SuppressWarnings({"all"})
public class ManagerClientThread {
    private static ConcurrentHashMap<String, ServerConnectClientThread> hm = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }

    /**
     * 通过userId添加一个Thread
     *
     * @param userId
     * @param thread
     */
    public static void addServerConnectClientThread(String userId, ServerConnectClientThread thread) {
        hm.put(userId, thread);
    }

    /**
     * 通过userId获得一个线程
     *
     * @param userId
     * @return
     */
    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        if (hm.containsKey(userId)) {
            return hm.get(userId);
        } else {
            return null;
        }
    }

    public static String getOnlineUser() {
        StringBuilder sb = new StringBuilder();
        for (String s : hm.keySet()) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString();
    }
    public static ServerConnectClientThread  removeServerConnectClientThread(String userId){
        ServerConnectClientThread scct = null;
        if (hm.containsKey(userId)){
            scct = hm.get(userId);
            hm.remove(userId);
        }else {
            scct = null;
        }
        return scct;
    }
}
