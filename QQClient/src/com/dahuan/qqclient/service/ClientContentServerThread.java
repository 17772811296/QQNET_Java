package com.dahuan.qqclient.service;

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

@SuppressWarnings({"all"})
public class ClientContentServerThread extends Thread {
    private Socket socket;

    public ClientContentServerThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        super.run();
        //客户端需要和服务器保持通信实用while循环
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) ois.readObject();//服务器没有发送消息该处将阻塞！
                if (msg.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    String[] userIds = msg.getContent().split(" ");
                    System.out.println("=========当前在线用户列表===========");
                    for (String s : userIds) {
                        System.out.println(s);
                    }
                    System.out.println("=========当前在线用户列表===========");
                } else if (msg.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    if (msg.getContent().equals("0")) {
                        System.out.println(msg.getGetter() + ": 没有登陆！！");
                    } else {
                        System.out.println(msg.getSender() + " 对 " + msg.getSender() + " 说 :");
                        System.out.println(msg.getContent() + "   /" + msg.getSendTime());
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
