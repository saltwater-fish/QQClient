package com.lucas.qqclient.service;

import java.util.HashMap;

public class ManageClientConnectServerTread {

    private static HashMap<String, ClientConnectServerTread> hm = new HashMap<>();

    // 将某个线程加入集合
    public static void addClientConnectServerTread(String userId, ClientConnectServerTread clientConnectServerTread) {
        hm.put(userId, clientConnectServerTread);
    }
    // 通过userId取出对应的线程
    public static ClientConnectServerTread getClientConnectServerTread(String userId) {
        return hm.get(userId);
    }

}
