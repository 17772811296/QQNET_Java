package qqcommon;

import java.io.Serializable;

/**
 * 消息体
 */
public class Message implements Serializable {
    private static final long seriaVersionUID = 1L;
    private String sender;//发送者
    private String getter;//接受者
    private String content;//消息内容
    private String sendTime;//发送时间
    private String mesType;//消息类型【可以在接口定义消息类型】
    private byte[] fileBytes;//文件内容
    private int fileLen = 0;//文件长度
    private String dest;//文件传输位置
    private String src;//源文件位置

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

}
