package com.dahuan.qqclient.service;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 该类完成用户登陆和用户注册等等
 */
public class UserClientService {
    private User user = new User();
    private Socket socket;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Socket getSocket() {
        return socket;
    }

    /**
     * 根据用户账号密码验证用户是否合法
     * @param userId 用户名
     * @param pwd    用户密码
     * @return
     */
    public boolean checkUser(String userId, String pwd) {
        user.setUserId(userId);
        user.setPassward(pwd);
        //链接服务器
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);//发送对象到服务器
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message msg = (Message) ois.readObject();
            if (msg.getMesType() == MessageType.MESSAGE_LOGIN_SUCCEED){
                //启动一个线程和服务器保持通信
                ClientContentServerThread ccst = new ClientContentServerThread(socket);
                ccst.start();
                //为了客户端的扩展 线程通过集合管理
                ManagerClientConnectServerThread.addClientConnetServerThread(user.getUserId(),ccst);
                return true;
            }else {
                socket.close();
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
