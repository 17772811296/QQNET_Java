package com.dahuan.qqclient.service;

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
                    //获取在线用户列表
                    String[] userIds = msg.getContent().split(" ");
                    System.out.println("=========当前在线用户列表===========");
                    for (String s : userIds) {
                        System.out.println(s);
                    }
                    System.out.println("=========当前在线用户列表===========");
                } else if (msg.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    //私聊消息
                    if (msg.getContent().equals("0")) {
                        System.out.println(msg.getGetter() + ": 没有登陆！！");
                    } else {
                        System.out.println(msg.getSender() + " 对 " + msg.getSender() + " 说 :");
                        System.out.println(msg.getContent() + "   /" + msg.getSendTime());
                    }
                } else if (msg.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    //群发消息
                    System.out.println(msg.getSender() + " 对 " + "大家" + " 说 :");
                    System.out.println(msg.getContent() + "   /" + msg.getSendTime());
                } else if (msg.getMesType().equals(MessageType.MESSAGE_SEND_FILE)) {
                    //发送文件
                    if (msg.getFileLen()==0) {
                        System.out.println(msg.getGetter() + ": 没有登陆！！");
                        System.out.println(msg.getGetter() + ": 没有登陆！！");
                    }else {
                        //TODO 用户可以自己选择路径
                        System.out.println(msg.getSender()+"给"+msg.getGetter()+"发送文件到"+msg.getDest());
                        FileOutputStream fos = new FileOutputStream(msg.getDest());
                        fos.write(msg.getFileBytes());
                        fos.close();
                        System.out.println("文件保存成功");
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
