package MyQQ.Conmmon;

import java.io.Serializable;

public class Massage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String Sender;
    private String Receiver;
    private String Content;
    private String Sendtime;
    private String MassageType;
    private byte[] fileBytes;
    private int flieLen;
    private String dest;
    private String src;


    public String getMassageType() {
        return MassageType;
    }

    public void setMassageType(String massageType) {
        MassageType = massageType;
    }

    public Massage() {

    }

    public Massage(String sender, String receiver, String content, String sendtime) {
        Sender = sender;
        Receiver = receiver;
        Content = content;
        Sendtime = sendtime;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getSendtime() {
        return Sendtime;
    }

    public void setSendtime(String sendtime) {
        Sendtime = sendtime;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFlieLen() {
        return flieLen;
    }

    public void setFlieLen(int flieLen) {
        this.flieLen = flieLen;
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
}
