package MyQQ.qqclient.service;

import MyQQ.Conmmon.Massage;
import MyQQ.Conmmon.MassageType;
import MyQQ.Conmmon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class UserClientService {
    private User u = new User();
    private Socket socket;
    private boolean connected = false;

    public void olineFriendList() {
        Massage massage = new Massage();
        massage.setMassageType(MassageType.MASSAGE_GET_OLINE_FRIED);
        massage.setSender(u.getUsername());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServiceThread
                    (u.getUsername()).getSocket().getOutputStream());
            oos.writeObject(massage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUser(String username, String password) {
        u.setUsername(username);
        u.setPassword(password);
        try {

            socket = new Socket(InetAddress.getLocalHost(), 9999);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(u);

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Massage ms = (Massage) objectInputStream.readObject();
            if (ms.getMassageType().equals(MassageType.MASSAGE_LOGIN_SUCCESS)) {
                connected = true;
                ClientConnectServiceThread ccst = new ClientConnectServiceThread(socket);
                ccst.start();
                ManageClientConnectServerThread.addClientConnectServiceThread(u.getUsername(), ccst);

            } else {
                socket.close();
            }


        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return connected;
    }

    public void logout(){
        Massage massage=new Massage();
        massage.setMassageType(MassageType.MASSAGE_CLIENT_EXIT);
        massage.setSender(u.getUsername());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(massage);
            System.out.println(u.getUsername()+"退出了系统");
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
