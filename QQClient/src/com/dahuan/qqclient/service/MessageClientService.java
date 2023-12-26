package com.dahuan.qqclient.service;

import com.oracle.net.Sdp;
import qqcommon.Message;
import qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 提供消息相关的功能
 */
public class MessageClientService {
    /**
     * 发送私聊消息
     *
     * @param connect
     * @param senderId
     * @param getterId
     */
    public void sendMessageToOne(String connect, String senderId, String getterId) {
        Message msg = new Message();
        msg.setSender(senderId);
        msg.setGetter(getterId);
        msg.setContent(connect);
        msg.setMesType(MessageType.MESSAGE_COMM_MES);
        msg.setSendTime(new Date().toString());
        System.out.println(senderId + " 对 " + getterId + " 说 :");
        System.out.println(connect + "/" + msg.getSendTime());
        ClientContentServerThread ccst = ManagerClientConnectServerThread.getClientConnectServerThread(senderId);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ccst.getSocket().getOutputStream());
            oos.writeObject(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送群聊消息
     * @param connect
     * @param senderId
     */
    public void sendMessageToAll(String connect, String senderId){
        Message msg = new Message();
        msg.setSender(senderId);
        msg.setContent(connect);
        msg.setMesType(MessageType.MESSAGE_TO_ALL_MES);
        msg.setSendTime(new Date().toString());
        System.out.println(senderId + " 对 " + "大家" + " 说 :");
        System.out.println(connect + "   /" + msg.getSendTime());
        ClientContentServerThread ccst = ManagerClientConnectServerThread.getClientConnectServerThread(senderId);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ccst.getSocket().getOutputStream());
            oos.writeObject(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
