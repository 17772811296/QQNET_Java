package com.dahuan.qqclient.view;

import com.dahuan.qqclient.service.UserClientService;
import com.dahuan.qqclient.utils.Utility;

import java.util.Scanner;

/**
 * 客户端界面
 */
@SuppressWarnings({"all"})
public class QQView {
    private boolean isLoop = true;
    private Scanner scanner = new Scanner(System.in);
    private String key = "";
    //创建socke线程，登陆注册等操作
    private UserClientService userClientService = new UserClientService();

    public static void main(String[] args) {
        new QQView().mainMenu();
        System.out.println("客户端退出系统");
    }

    private void mainMenu() {
        while (isLoop) {
            System.out.println("==========欢迎登陆网络服务系统==========");
            System.out.println("\t\t 1 登陆系统");
            System.out.println("\t\t 9 退出系统");
            System.out.println("请输入你的选择：");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.println("请输入用户号：");
                    String userId = Utility.readString(50);
                    System.out.println("请输入密  码：");
                    String passward = Utility.readString(50);
                    if (userClientService.checkUser(userId, passward)) {//假设登陆成功
                        System.out.println("==========欢迎" + userId + "==========");
                        while (isLoop) {
                            System.out.println("==========网络用户系统二级菜单" + userId + "==========");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.println("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    System.out.println("显示在线用户列表");
                                    break;
                                case "2":
                                    System.out.println("群发消息");
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    System.out.println("退出系统");
                                    isLoop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("登陆失败！");
                    }
                    break;
                case "9":
                    System.out.println("退出系统");
                    isLoop = false;
                    break;
            }
        }
    }
}
