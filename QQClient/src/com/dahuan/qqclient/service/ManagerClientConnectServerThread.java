package com.dahuan.qqclient.service;

import java.util.HashMap;

/**
 * 管理客户端 客户端socket线程
 */
@SuppressWarnings({"all"})
public class ManagerClientConnectServerThread {
    private static HashMap<String, ClientContentServerThread> hm = new HashMap<>();

    /**
     * 添加socket链接线程
     * @param userId
     * @param clientContentServerThread
     */
    public static void addClientConnetServerThread(String userId,ClientContentServerThread clientContentServerThread){
        hm.put(userId,clientContentServerThread);
    }

    /**
     * 获取一个socket线程
     * @param userId
     * @return
     */
    public static ClientContentServerThread getClientConnectServerThread(String userId){
        if (hm.containsKey(userId)){
            return hm.get(userId);
        }else {
            return null;
        }
    }

}
