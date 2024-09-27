package MyQQ.qqserver;

import MyQQ.Conmmon.Massage;
import MyQQ.Conmmon.MassageType;
import MyQQ.Conmmon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class QQserver {
    private ServerSocket serverSocket = null;
    private static HashMap<String,User>validUsers=new HashMap<>();
    static {
        validUsers.put("100",new User("100","123456"));
        validUsers.put("200",new User("200","123456"));
        validUsers.put("300",new User("300","123456"));
        validUsers.put("奕星帅男冯开冰",new User("奕星帅男冯开冰","123456"));
        validUsers.put("枪男牛新元",new User("枪男牛新元","123456"));
    }
    private boolean checkUser(String username,String password){
        User user=validUsers.get(username);
        if (user==null){
            System.out.println("用户不存在，请注册用户");
            return false;
        }
        if( !user.getPassword().equals(password)){
            System.out.println("密码错误");
            return false;
        }
        return true;

    }

    public QQserver() {
        System.out.println("服务器在9999端口监听");
        new Thread(new sendNewsToAllService()).start();
        try {
            serverSocket = new ServerSocket(9999);
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                User u = (User) ois.readObject();
                Massage massage = new Massage();

                if (checkUser(u.getUsername(),u.getPassword())) {
                    massage.setMassageType(MassageType.MASSAGE_LOGIN_SUCCESS);
                    oos.writeObject(massage);
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, u.getUsername());
                    serverConnectClientThread.start();
                    ManageClientThreads.addClientThread(u.getUsername(), serverConnectClientThread);

                } else {
                    massage.setMassageType(MassageType.MASSAGE_LOGIN_FAIL);
                    oos.writeObject(massage);
                    socket.close();
                }

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

