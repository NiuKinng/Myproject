package MyQQ.qqserver;

import MyQQ.Conmmon.Massage;
import MyQQ.Conmmon.MassageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String username;

    public Socket getSocket() {
        return socket;
    }

    public ServerConnectClientThread(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("服务端和客户端" + username + "保持通信，读取数据...");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Massage massage = (Massage) ois.readObject();
                if (massage.getMassageType().equals(MassageType.MASSAGE_GET_OLINE_FRIED)) {
                    System.out.println(massage.getSender() + "要在线用户列表");
                    String Olineusers = ManageClientThreads.getOlineusers();
                    Massage massage1 = new Massage();
                    massage1.setMassageType(MassageType.MASSAGE_RET_OLINE_FRIED);
                    massage1.setContent(Olineusers);
                    massage1.setReceiver(massage.getSender());
                    oos.writeObject(massage1);
                } else if(massage.getMassageType().equals(MassageType.MASSAGE_CLIENT_EXIT)){
                    System.out.println(massage.getSender()+"退出系统。");
                    ManageClientThreads.removeClientThreads(massage.getSender());
                    socket.close();
                    break;
                }else if (massage.getMassageType().equals(MassageType.MASSAGE_COMM_MES)){
                    ServerConnectClientThread t = ManageClientThreads.getClientThread(massage.getReceiver());
                    try {
                        ObjectOutputStream oos1 = new ObjectOutputStream(t.socket.getOutputStream());
                        oos1.writeObject(massage);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }else if(massage.getMassageType().equals(MassageType.MASSAGE_TO_ALL)){
                    HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
                    Set<String> usernames = hm.keySet();
                    for (String s :usernames) {
                        if(s!=username){
                            ObjectOutputStream oos1 = new ObjectOutputStream(ManageClientThreads.getClientThread(s).socket.getOutputStream());
                            oos1.writeObject(massage);
                        }

                    }


                }else if(massage.getMassageType().equals(MassageType.MASSAGE_FEIL_MES)){
                    ObjectOutputStream oos1 = new ObjectOutputStream(ManageClientThreads.getClientThread(massage.getReceiver()).socket.getOutputStream());
                    oos1.writeObject(massage);


                }

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
