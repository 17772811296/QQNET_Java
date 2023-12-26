package com.dahuan.qqclient.service;

import com.dahuan.qqclient.utils.StreamUtils;
import qqcommon.Message;
import qqcommon.MessageType;

import java.io.*;

/**
 * 发送文件服务
 */
public class FileClientService {
    /**
     * 发送文件给指定的人
     *
     * @param scr
     * @param dest
     * @param senderId
     * @param getterId
     */
    public void sendFileToOne(String scr, String dest, String senderId, String getterId) {
        Message msg = new Message();
        msg.setMesType(MessageType.MESSAGE_SEND_FILE);
        msg.setDest(dest);
        msg.setSrc(scr);
        msg.setSender(senderId);
        msg.setGetter(getterId);
        msg.setFileLen((int) new File(scr).length());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(scr);
            byte[] bytes = StreamUtils.streamToByteArray(fis);
            msg.setFileBytes(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            assert fis != null;
            try {
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(senderId + ": " + "发送文件给" + getterId + "到：" + dest + "位置");
        ClientContentServerThread ccst = ManagerClientConnectServerThread.getClientConnectServerThread(senderId);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ccst.getSocket().getOutputStream());
            oos.writeObject(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
