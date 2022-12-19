package MXDJR;

import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import handling.world.World.Broadcast;
import tools.MaplePacketCreator;
import com.alibaba.fastjson.JSON;
import java.util.concurrent.CopyOnWriteArrayList;
import constants.ServerConfig;
import java.util.List;
import java.net.Socket;

public class RobotSocket
{
    private static Socket Socket;
    private static Thread ReadThread;
    private static Thread DoThread;
    public static boolean IsRun;
    public static boolean IsConnect;
    private static List<RobotTask> QueueRead;
    private static List<byte[]> QueueSend;
    private static int Num;
    private static String list;
    private static long[] QQ号;
    private static long[] QQ群号;
    private static long[] QQ群主;
    private static final StartPack PackStart;
    
    public static long QQ号() {
        return RobotSocket.QQ号[ServerConfig.nub1];
    }
    
    public static long QQ群号() {
        return RobotSocket.QQ群号[ServerConfig.nub1];
    }
    
    public static long QQ群主() {
        return RobotSocket.QQ群主[ServerConfig.nub1];
    }
    
    public static void Start() {
        RobotSocket.QueueRead = (List<RobotTask>)new CopyOnWriteArrayList();
        RobotSocket.QueueSend = (List<byte[]>)new CopyOnWriteArrayList();
        RobotSocket.DoThread = new Thread((Runnable)new Runnable() {
            @Override
            public void run() {
                while (RobotSocket.IsRun) {
                    try {
                        if (!RobotSocket.QueueRead.isEmpty()) {
                            final RobotTask task = (RobotTask)RobotSocket.QueueRead.remove(0);
                            switch (task.index) {
                                case 49: {
                                    final GroupMessageEventPack pack = (GroupMessageEventPack)JSON.parseObject(task.data, GroupMessageEventPack.class);
                                    final String item = (String)pack.message.get(1);
                                    if (!item.contains((CharSequence)"{") && !item.contains((CharSequence)"[")) {
                                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[群消息]：" + pack.name + "->" + item));
                                    }
                                    if (item.contains((CharSequence)"增加关键字") && pack.fid == RobotSocket.QQ群主[ServerConfig.nub1]) {
                                        final String 关键字 = item.substring(item.indexOf("字") + 1);
                                        System.out.println("增加游戏内关键字：" + 关键字);
                                        MXDJR.Config.GJC.add(关键字);
                                        ConfigRead.Save();
                                        final List<String> send = Arrays.asList("我已经开通关键字：" + 关键字);
                                        RobotSocket.SendGroupMessage(RobotSocket.QQ号[ServerConfig.nub1], RobotSocket.QQ群号[ServerConfig.nub1], send);
                                        break;
                                    }
                                    if (item.contains((CharSequence)"删除关键字") && pack.fid == RobotSocket.QQ群主[ServerConfig.nub1]) {
                                        final String 关键字 = item.substring(item.indexOf("字") + 1);
                                        System.out.println("删除游戏内关键字：" + 关键字);
                                        for (int num = 0; num < MXDJR.Config.GJC.size(); ++num) {
                                            if (((String)MXDJR.Config.GJC.get(num)).contains((CharSequence)关键字)) {
                                                MXDJR.Config.GJC.remove(num);
                                                ConfigRead.Save();
                                                final List<String> send2 = Arrays.asList("我已经删除关键字：" + 关键字);
                                                RobotSocket.SendGroupMessage(RobotSocket.QQ号[ServerConfig.nub1], RobotSocket.QQ群号[ServerConfig.nub1], send2);
                                                break;
                                            }
                                        }
                                    }
                                    if (item.contains((CharSequence)"查看关键字")) {
                                        String 关键字2 = "";
                                        for (int num2 = 0; num2 < MXDJR.Config.GJC.size(); ++num2) {
                                            关键字2 = 关键字2 + "【" + (String)MXDJR.Config.GJC.get(num2) + "】";
                                        }
                                        final List<String> send2 = Arrays.asList("游戏通告支持关键字：" + 关键字2);
                                        RobotSocket.SendGroupMessage(RobotSocket.QQ号[ServerConfig.nub1], RobotSocket.QQ群号[ServerConfig.nub1], send2);
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                        Thread.sleep(10L);
                    }
                    catch (Exception e) {
                        ServerMain.LogError(e);
                    }
                }
            }
        });
        (RobotSocket.ReadThread = new Thread((Runnable)new Runnable() {
            @Override
            public void run() {
                try {
                    while (!RobotSocket.IsRun) {
                        Thread.sleep(100L);
                    }
                }
                catch (Exception e) {
                    ServerMain.LogError(e);
                }
                RobotSocket.DoThread.start();
                while (RobotSocket.IsRun) {
                    try {
                        if (!RobotSocket.IsConnect) {
                            ReConnect();
                        }
                        else {
                            if (RobotSocket.Socket.getInputStream().available() > 0) {
                                final byte[] data = new byte[RobotSocket.Socket.getInputStream().available()];
                                RobotSocket.Socket.getInputStream().read(data);
                                final byte type = data[data.length - 1];
                                data[data.length - 1] = 0;
                                final String unicode = new String(data, "UTF-8");
                                final String gbk = new String(unicode.getBytes("GBK"));
                                RobotSocket.QueueRead.add(new RobotTask(type, gbk));
                                continue;
                            }
                            if (RobotSocket.Socket.getInputStream().available() < 0) {
                                ServerMain.LogOut("机器人连接中断");
                                RobotSocket.IsConnect = false;
                            }
                            else if (!RobotSocket.QueueSend.isEmpty()) {
                                final byte[] data = (byte[])RobotSocket.QueueSend.remove(0);
                                RobotSocket.Socket.getOutputStream().write(data);
                                RobotSocket.Socket.getOutputStream().flush();
                            }
                        }
                        Thread.sleep(50L);
                    }
                    catch (Exception e2) {
                        ServerMain.LogError("机器人连接失败");
                        ServerMain.LogError(e2);
                        RobotSocket.IsConnect = false;
                        ServerMain.LogError("机器人20秒后重连");
                        try {
                            Thread.sleep(20000L);
                        }
                        catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        ServerMain.LogError("机器人重连中");
                    }
                }
            }
        })).start();
        RobotSocket.IsRun = true;
    }
    
    private static boolean is() {
        try {
            RobotSocket.Socket.sendUrgentData(60);
            return false;
        }
        catch (Exception ex) {
            return true;
        }
    }
    
    private static void ReConnect() {
        try {
            if (RobotSocket.Socket != null) {
                RobotSocket.Socket.close();
            }
            RobotSocket.Socket = new Socket("127.0.0.1", 23333);
            final byte[] data = (JSON.toJSON(RobotSocket.PackStart) + " ").getBytes(StandardCharsets.UTF_8);
            data[data.length - 1] = 0;
            RobotSocket.Socket.getOutputStream().write(data);
            RobotSocket.Socket.getOutputStream().flush();
            RobotSocket.QueueRead.clear();
            RobotSocket.QueueSend.clear();
            ServerMain.LogOut("机器人已连接");
            RobotSocket.IsConnect = true;
        }
        catch (Exception e) {
            ServerMain.LogError("机器人连接失败");
            ServerMain.LogError(e);
        }
    }
    
    public static void SendGroupMessage(final long qq_, final long id_, final List<String> message_) {
        final byte[] data = BuildPack.Build(new SendGroupMessagePack() {}, 52);
        RobotSocket.QueueSend.add(data);
    }
    
    public static void SendGroupPrivateMessage(final long qq_, final long id_, final long fid_, final List<String> message_) {
        final byte[] data = BuildPack.Build(new SendGroupPrivateMessagePack() {}, 53);
        RobotSocket.QueueSend.add(data);
    }
    
    public static void SendFriendMessage(final long qq_, final long id_, final List<String> message_) {
        final byte[] data = BuildPack.Build(new SendFriendMessagePack() {}, 54);
        RobotSocket.QueueSend.add(data);
    }
    
    public static void SendGroupImage(final long qq, final long id, final String img) {
        final byte[] data = BuildPack.BuildImage(qq, id, 0L, img, 61);
        RobotSocket.QueueSend.add(data);
    }
    
    public static void SendGroupPrivateImage(final long qq, final long id, final long fid, final String img) {
        final byte[] data = BuildPack.BuildImage(qq, id, fid, img, 62);
        RobotSocket.QueueSend.add(data);
    }
    
    public static void SendFriendImage(final long qq, final long id, final String img) {
        final byte[] data = BuildPack.BuildImage(qq, id, 0L, img, 63);
        RobotSocket.QueueSend.add(data);
    }
    
    public static void Stop() {
        ServerMain.LogOut("机器人正在断开！");
        RobotSocket.IsRun = false;
        if (RobotSocket.Socket != null) {
            try {
                RobotSocket.Socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        ServerMain.LogOut("机器人已断开！");
    }
    
    public static final void robbatSpeak(final String text) {
        if (RobotSocket.IsConnect) {
            final List<String> send = Arrays.asList(text);
            SendGroupMessage(RobotSocket.QQ号[ServerConfig.nub1], RobotSocket.QQ群号[ServerConfig.nub1], send);
        }
    }
    
    public static final void robbatSpeak1(final String text) {
        if (RobotSocket.IsConnect) {
            final List<String> send = Arrays.asList(text);
            SendFriendMessage(RobotSocket.QQ号[ServerConfig.nub1], RobotSocket.QQ群号[ServerConfig.nub1], send);
        }
    }
    
    static {
        Num = 0;
        QQ号 = new long[] { 401620799L, 1023783247L, 646534022L, 2162107885L };
        QQ群号 = new long[] { 731275948L, 903845L, 942576480L, 964605016L };
        QQ群主 = new long[] { 741998576L, 20759751L, 598000587L, 505246798L };
        PackStart = new StartPack() {
            {
                Name = "MXDJR";
                Reg = new ArrayList<Integer>() {
                    {
                        add(Integer.valueOf(49));
                        add(Integer.valueOf(50));
                        add(Integer.valueOf(51));
                    }
                };
            }
        };
    }
}
