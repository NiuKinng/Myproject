package MyQQ.qqserver;

import MyQQ.Conmmon.Massage;
import MyQQ.Conmmon.MassageType;
import MyQQ.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class sendNewsToAllService implements Runnable {


    @Override
    public void run() {
        while (true) {
            System.out.println("请输入服务器要推送的新闻");
            String news = Utility.readString(100);
            Massage massage = new Massage();
            massage.setMassageType(MassageType.MASSAGE_TO_ALL);
            massage.setSender("服务器");
            massage.setContent(news);
            massage.setSendtime(new Date().toString());
            System.out.println("服务器推送消息给所有人 说：" + news);
            HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
            Set<String> keyset = hm.keySet();
            for (String s : keyset) {
                ServerConnectClientThread serverConnectClientThread = hm.get(s);
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    oos.writeObject(massage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

}
