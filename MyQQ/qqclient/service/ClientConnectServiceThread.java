package MyQQ.qqclient.service;

import MyQQ.Conmmon.Massage;
import MyQQ.Conmmon.MassageType;

import java.io.*;
import java.net.Socket;

public class ClientConnectServiceThread extends Thread {
    private Socket socket;

    public ClientConnectServiceThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("客户端线程，等待读取从服务端发送的消息");
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Massage massage=(Massage)objectInputStream.readObject();
                if(massage.getMassageType().equals(MassageType.MASSAGE_RET_OLINE_FRIED)){
                    String[] onlinUsers = massage.getContent().split(" ");
                    System.out.println("========当前在线用户=========");
                    for(int i=0;i<onlinUsers.length;i++){
                        System.out.println("用户："+onlinUsers[i]);
                    }

                }else if (massage.getMassageType().equals(MassageType.MASSAGE_COMM_MES)){
                    System.out.println("\n"+massage.getSender()+"说："+massage.getContent());
                }else if (massage.getMassageType().equals(MassageType.MASSAGE_TO_ALL)){
                    System.out.println("\n"+massage.getSender()+"对大家说"+massage.getContent());
                }else if(massage.getMassageType().equals(MassageType.MASSAGE_FEIL_MES)){
                    System.out.println("\n"+massage.getSender()+"给"+massage.getReceiver()+"发文件："+massage.getSrc()+"到"+massage.getDest());
                    BufferedOutputStream bos=null;
                    try {
                        bos = new BufferedOutputStream(new FileOutputStream(massage.getDest()));
                        bos.write(massage.getSrc().getBytes());
                        System.out.println("\n保存文件成功");

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }finally {
                        {
                            if(bos!=null){
                                bos.close();
                            }

                        }
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
