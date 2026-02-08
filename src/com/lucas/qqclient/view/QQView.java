package com.lucas.qqclient.view;

import com.lucas.qqclient.service.UserClientService;
import com.lucas.qqclient.utils.Utility;

import java.security.spec.RSAOtherPrimeInfo;

public class QQView {
    private boolean loop = true;
    private String key = "";
    private UserClientService userClientService = new UserClientService();

    public static void main(String[] args) {
        new QQView().mainView();
        System.out.println("客户端退出系统......");
    }

    // 显示主菜单
    public void mainView(){

        while (loop){

            System.out.println("========================欢迎登录网络通信系统========================");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择：");
            key = Utility.readString(1);

            // 根据用户的不同输入，处理不同逻辑
            switch (key){
                case "1":   //登录系统
                    System.out.print("请输入用户ID：");
                    String userId = Utility.readString(50);
                    System.out.print("请输入密码：");
                    String pwd = Utility.readString(50);
                    // 到服务端去验证
                    if (userClientService.checkUser(userId, pwd)) {  // 验证成功
                        System.out.println("==========================欢迎（用户" + userId + "登录成功）============================");
                        while (loop) {
                            System.out.println("=========================网络通信系统二级菜单（用户ID" + userId + "）===========================");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key){
                                case "1":
                                    // 显示在线用户列表
                                    System.out.println("显示在线用户列表");
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    // TODO 群发消息
                                    System.out.println("群发消息");
                                    break;
                                case "3":
                                    // TODO 私聊消息
                                    System.out.println("私聊消息");
                                    break;
                                case "4":
                                    // TODO 发送文件
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    // TODO 退出系统
                                    System.out.println("退出系统");
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("=======================登录失败========================");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }

        }


    }


}
