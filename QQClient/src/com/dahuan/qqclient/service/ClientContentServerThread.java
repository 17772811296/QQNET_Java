package com.dahuan.qqclient.service;

import qqcommon.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
@SuppressWarnings({"all"})
public class ClientContentServerThread extends Thread {
    private Socket socket;
    public Socket getSocket() {
        return socket;
    }
    public ClientContentServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
        //客户端需要和服务器保持通信实用while循环
        while (true){
            System.out.println("客户端等待读取服务器发送来的消息！");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) ois.readObject();//服务器没有发送消息该处将阻塞！
                System.out.println(msg.getContent());
                //TODO 后期处理消息
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
