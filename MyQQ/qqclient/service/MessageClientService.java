package MyQQ.qqclient.service;

import MyQQ.Conmmon.Massage;
import MyQQ.Conmmon.MassageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MessageClientService {
    public MessageClientService() {
    }

    public void SendMessageToone(String content, String sennderid, String geterid) {
        Massage massage = new Massage();
        massage.setMassageType(MassageType.MASSAGE_COMM_MES);
        massage.setSender(sennderid);
        massage.setReceiver(geterid);
        massage.setContent(content);
        massage.setSendtime(new Date().toString());
        System.out.println(sennderid+"对"+geterid+"说"+content);
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServiceThread(sennderid).getSocket().getOutputStream());
            oos.writeObject(massage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void SendMessageToAll(String content, String sennderid) {
        Massage massage = new Massage();
        massage.setMassageType(MassageType.MASSAGE_TO_ALL);
        massage.setSender(sennderid);
        massage.setContent(content);
        massage.setSendtime(new Date().toString());
        System.out.println(sennderid+"说"+content);
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServiceThread(sennderid).getSocket().getOutputStream());
            oos.writeObject(massage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
