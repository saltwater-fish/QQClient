package com.lucas.qqclient.service;

import com.lucas.qqcommon.Message;
import com.lucas.qqcommon.MessageType;

import java.io.FileOutputStream;
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
                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    // 收到群发消息
                    System.out.println(message.getSender() + " 对大家说 " + message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    // 接受到服务器发来的私聊信息
                    System.out.println(message.getSender() + " 对 " + message.getGetter() + " 说 " + message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {
                    // 接收到服务器转发来的文件
                    System.out.println("\n" + message.getSender() + " 给 " + message.getGetter()
                            + " 发文件: " + message.getSrc() + " 到我的电脑的目录 " + message.getDest());
                    // 取出message中的文件字节数组，通过文件输出流写入本地磁盘
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest(), true);
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("\n保存文件成功");
                }
                else {
                    System.out.println("其他类型消息，暂时不做处理");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
