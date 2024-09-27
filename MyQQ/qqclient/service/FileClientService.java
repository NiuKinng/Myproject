package MyQQ.qqclient.service;

import MyQQ.Conmmon.Massage;
import MyQQ.Conmmon.MassageType;

import java.io.*;

public class FileClientService {
    public void SendFiletoOne(String src, String dest, String senderId, String geterId) {
        Massage massage = new Massage();
        massage.setMassageType(MassageType.MASSAGE_FEIL_MES);
        massage.setSender(senderId);
        massage.setReceiver(geterId);
        massage.setSrc(src);
        massage.setDest(dest);
        byte[] fileBytes = new byte[(int) new File(src).length()];
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(src));
            bis.read(fileBytes);
            massage.setFileBytes(fileBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (bis !=null)
            {
                try {
                    bis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        System.out.println("\n"+senderId+"给"+geterId+"发送文件"+src+"到对方的"+dest);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServiceThread(senderId).getSocket().getOutputStream());
            oos.writeObject(massage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
