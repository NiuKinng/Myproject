package MyQQ.qqserver;

import java.util.HashMap;

public class ManageClientThreads {
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap();

    public static HashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }

    public static void addClientThread(String username, ServerConnectClientThread s) {
        hm.put(username, s);
    }

    public static ServerConnectClientThread getClientThread(String username) {
        return hm.get(username);
    }

    public static void removeClientThreads(String u)
    {
        hm.remove(u);
    }
    public static String getOlineusers() {
        String rt = "";
        for (String s : hm.keySet()) {
            rt = rt + s + " ";
        }
        return rt;
    }
}
