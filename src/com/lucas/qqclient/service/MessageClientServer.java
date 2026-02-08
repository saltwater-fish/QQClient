package com.lucas.qqclient.service;

import com.lucas.qqcommon.Message;
import com.lucas.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MessageClientServer {
    // 私聊消息
    public void sendToOne(String content, String userId, String getterId){
        // 创建一个message对象，将要发送的消息发送给服务器
        Message message = new Message();
        message.setContent(content);
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        message.setSender(userId);
        message.setGetter(getterId);
        message.setSendTime(new Date().toString());
        System.out.println(userId + " 给 " + getterId + " 说 " + content);

        // 在线程hashmap中找到当前用户对应的线程
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerTread.getClientConnectServerTread(userId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
