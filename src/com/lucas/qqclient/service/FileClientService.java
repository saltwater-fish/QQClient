package com.lucas.qqclient.service;

import com.lucas.qqcommon.Message;
import com.lucas.qqcommon.MessageType;

import java.io.*;

public class FileClientService {

    public void sendFileToOne(String src, String dest, String senderId, String getterId){

        // 构建相应的message对象
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);

        // 读取文件
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int)new File(src).length()];

        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);    // 将文件读入到程序的字节数组
            message.setFileBytes(fileBytes);    // 将字节数组赋值给message的属性
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭文件流
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // 提示信息
        System.out.println("\n" + senderId + " 给 " + getterId + " 发送文件：" + src + "到对方电脑的目录" + dest);

        // 发送message到服务器
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerTread.getClientConnectServerTread(message.getSender()).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
