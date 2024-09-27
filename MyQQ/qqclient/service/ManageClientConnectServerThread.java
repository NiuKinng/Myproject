package MyQQ.qqclient.service;

import java.util.HashMap;

public class ManageClientConnectServerThread {
    private static HashMap<String,ClientConnectServiceThread> hm=new HashMap<>();
    public  static void addClientConnectServiceThread(String username,ClientConnectServiceThread ccst){
        hm.put(username,ccst);
    }
    public static ClientConnectServiceThread getClientConnectServiceThread(String username){
        return hm.get(username);
    }


}
