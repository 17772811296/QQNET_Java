package com.dahuan.qqserver.service;

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"all"})
public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userId;

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            System.out.println("服务器端与客户端" + userId + "保持连接");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) ois.readObject();
                if (msg.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    //获取当前在线用户列表
                    Message retMsg = new Message();
                    retMsg.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    retMsg.setContent(ManagerClientThread.getOnlineUser());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(retMsg);
                } else if (msg.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    //客户端关闭系统
                    System.out.println(msg.getSender() + ":退出客户端");
                    ServerConnectClientThread scct = ManagerClientThread.removeServerConnectClientThread(msg.getSender());
                    socket.close();
                    break;
                } else if (msg.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    //私聊消息
                    ServerConnectClientThread getterCCST = ManagerClientThread.removeServerConnectClientThread(msg.getGetter());
                    if (getterCCST == null) {
                        //TODO 如果用户没有登陆可以将消息存储到数据库重，等用户登陆了再转发
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        msg.setContent("0");
                        oos.writeObject(msg);
                    } else {
                        ObjectOutputStream oos = new ObjectOutputStream(getterCCST.getSocket().getOutputStream());
                        oos.writeObject(msg);
                    }
                } else if (msg.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    //群发消息
                    ConcurrentHashMap<String, ServerConnectClientThread> hm = ManagerClientThread.getHm();
                    Set<String> strings = hm.keySet();
                    for (String s : strings) {
                        if (!s.equals(userId)) {
                            ServerConnectClientThread getterCCST = ManagerClientThread.removeServerConnectClientThread(s);
                            ObjectOutputStream oos = new ObjectOutputStream(getterCCST.getSocket().getOutputStream());
                            oos.writeObject(msg);
                        }
                    }
                } else if (msg.getMesType().equals(MessageType.MESSAGE_SEND_FILE)) {
                    ServerConnectClientThread getterCCST = ManagerClientThread.removeServerConnectClientThread(msg.getGetter());
                    if (getterCCST == null) {
                        //TODO 如果用户没有登陆可以将消息存储到数据库重，等用户登陆了再转发
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        msg.setFileLen(0);
                        oos.writeObject(msg);
                    } else {
                        ObjectOutputStream oos = new ObjectOutputStream(getterCCST.getSocket().getOutputStream());
                        oos.writeObject(msg);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                if (!(e instanceof EOFException)) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
