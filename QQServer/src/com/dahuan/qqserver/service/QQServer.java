package com.dahuan.qqserver.service;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务端 监听9999，等待客户端的连接，并保持通信
 */
@SuppressWarnings({"all"})
public class QQServer {
    private static ConcurrentHashMap<String,User> validUsers = new ConcurrentHashMap<>();
    static {
        validUsers.put("100",new User("100","123456"));
        validUsers.put("200",new User("200","123456"));
        validUsers.put("300",new User("300","123456"));
        validUsers.put("至尊宝",new User("至尊宝","123456"));
        validUsers.put("紫霞仙子",new User("紫霞仙子","123456"));
        validUsers.put("菩提老祖",new User("菩提老祖","123456"));

    }
    private ServerSocket ss = null;
    public QQServer(){
        System.out.println("服务在9999端口监听.....");
        try {
            ss = new ServerSocket(9999);
            while (true){
                Socket accept = ss.accept();
                ObjectInputStream ois = new ObjectInputStream(accept.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(accept.getOutputStream());
                User user = (User) ois.readObject();
                Message msg = new Message();
                if (checkUser(user.getUserId(),user.getPassward())){
                    msg.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(msg);
                    //创建一个线程socket，用来和客户端保持联系
                    ServerConnectClientThread scct = new ServerConnectClientThread(accept, user.getUserId());
                    scct.start();
                    //统一管理服务端连接到的socket线程
                    ManagerClientThread.addServerConnectClientThread(user.getUserId(),scct);
                }else{
                    System.out.println("用户id"+user.getUserId()+"pwd"+user.getPassward()+"验证失败");
                    msg.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(msg);
                    accept.close();
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                ss.close();
                System.out.println("服务器停止运行...");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean checkUser(String userId,String pwd){
        User user = validUsers.get(userId);
        if (user==null){
            return false;
        }
        if (!user.getPassward().equals(pwd)){
            return false;
        }
        return true;
    }
}
