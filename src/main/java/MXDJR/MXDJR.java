package MXDJR;

public class MXDJR
{
    public static String RunDir;
    public static ConfigObj Config;
    
    public static void startJR() {
        if (RobotSocket.IsRun) {
            System.out.println("机器人已经启动了，本次关闭了机器人。");
            RobotSocket.Stop();
            return;
        }
        MXDJR.RunDir = System.getProperty("user.dir") + "/";
        System.out.println("正在启动群机器人……");
        if (ConfigRead.ReadStart(MXDJR.RunDir)) {
            System.out.println("请修改配置文件后重新点击！");
            return;
        }
        System.out.println("初始化完成,一共获取到" + MXDJR.Config.GJC.size() + "个关键词！");
        RobotSocket.Start();
    }
}
