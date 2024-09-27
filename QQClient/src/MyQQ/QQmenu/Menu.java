package MyQQ.QQmenu;

import MyQQ.Utility;
import MyQQ.qqclient.service.FileClientService;
import MyQQ.qqclient.service.MessageClientService;
import MyQQ.qqclient.service.UserClientService;

public class Menu {
    private boolean loop = true;
    private String key = "";
    private UserClientService userClientService = new UserClientService();
    private MessageClientService messageClientService = new MessageClientService();
    private FileClientService fileClientService = new FileClientService();

    public static void main(String[] args) {
        new Menu().showMenu();
    }

    private void showMenu() {
        while (loop) {
            System.out.println("=========================欢迎登录NiuKinng仿制版QQ=========================");
            System.out.println("\t\t1 登录系统");
            System.out.println("\t\t9 退出系统");
            System.out.println("请输入你的选择");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.println("请输入用户名：");
                    String Userid = Utility.readString(50);
                    System.out.println("请输入密码：");
                    String pwd = Utility.readString(50);
                    if (userClientService.checkUser(Userid, pwd)) {
                        System.out.println("登陆成功！");
                        System.out.println("=========================欢迎（用户" + Userid + ")=========================");
                        while (loop) {
                            System.out.println("\n============NiuKinng仿制版QQ二级菜单+(用户" + Userid + ")===============");
                            System.out.println("\t\t1 显示在线用户列表");
                            System.out.println("\t\t2 群发消息");
                            System.out.println("\t\t3 私聊消息");
                            System.out.println("\t\t4 发送文件");
                            System.out.println("\t\t9 退出系统");
                            System.out.println("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    System.out.println("显示在线用户列表:");
                                    userClientService.olineFriendList();
                                    break;
                                case "2":
                                    System.out.println("群发消息");
                                    System.out.println("请输入相对大家说的话：");
                                    String s= Utility.readString(100);
                                    messageClientService.SendMessageToAll(s,Userid);

                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    System.out.println("请输入想要聊天的用户名");
                                    String getid = Utility.readString(50);
                                    System.out.println("请输入想说的话：");
                                    String content = Utility.readString(100);

                                    messageClientService.SendMessageToone(content, Userid, getid);


                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    System.out.println("请输入你想指定的用户：");
                                    String userid = Utility.readString(50);
                                    System.out.println("请输入你想发送的文件路径：");
                                    String string= Utility.readString(100);
                                    System.out.println("请输入你指定对方安装的文件路径：");
                                    String des = Utility.readString(100);
                                    fileClientService.SendFiletoOne(string,des,Userid,userid);

                                    break;
                                case "9":
                                    loop = false;
                                    userClientService.logout();
                                    System.out.println("系统退出.......");
                                    break;

                            }
                        }
                    } else {
                        System.out.println("==========登陆失败========");
                    }

                    break;
                case "9":
                    loop = false;

                    System.out.println("系统退出.......");
                    break;

            }

        }
    }
}
