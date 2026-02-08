package com.lucas.qqclient.service;

import com.lucas.qqcommon.Message;
import com.lucas.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerTread extends Thread {
    private Socket socket;

    // 带参构造器
    public ClientConnectServerTread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        // 因为Thread需要在后台和服务器通讯，因此这里while循环
        while (true) {

            System.out.println("客户端线程，等待读取从服务器端发送的消息");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n==================当前在线用户列表==================");
                    for (String onlineUser : onlineUsers) {
                        System.out.println("用户：" + onlineUser);
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    // 接受到服务器发来的私聊信息
                    System.out.println(message.getSender() + " 对 " + message.getGetter() + " 说 " + message.getContent());
                } else {
                    System.out.println("其他类型消息，暂时不做处理");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
