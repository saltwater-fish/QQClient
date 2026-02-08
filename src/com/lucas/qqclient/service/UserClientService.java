package com.lucas.qqclient.service;

import com.lucas.qqcommon.Message;
import com.lucas.qqcommon.MessageType;
import com.lucas.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class UserClientService {
    private User u = new User();
    private Socket socket;

    public UserClientService() {

    }

    public boolean checkUser(String userId, String pwd) {
        boolean b = false;
        // 创建User对象
        u.setUserId(userId);
        u.setPasswd(pwd);

        try {
            // 连接到服务器端，发送u对象
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            // 得到ObjectOutputStream对象
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(u);// 发送User对象

            // 读取从服务器回复的Message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if (ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {    // 登录验证成功
                // 建立一个和服务器端保持通讯的线程
                ClientConnectServerTread clientConnectServerTread = new ClientConnectServerTread(socket);
                // 启动客户端的线程
                clientConnectServerTread.start();
                // 将线程放入集合管理
                ManageClientConnectServerTread.addClientConnectServerTread(userId, clientConnectServerTread);
                b = true;
            } else {
                // 登录失败，关闭线程
                socket.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return b;
    }

    public void onlineFriendList() {
        // 准备message对象，发送到服务器端
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId());

        try {
            // 得到用户对应的客户端线程
            ClientConnectServerTread clientConnectServerTread = ManageClientConnectServerTread.getClientConnectServerTread(u.getUserId());
            // 通过线程得到对应的socket
            Socket socket = clientConnectServerTread.getSocket();
            // 得到当前线程socket对应的ObjectOutputStream对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // 发送一个Message对象，向服务端请求在线用户列表
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
