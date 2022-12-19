package gui.进阶BOSS;

import tools.FileoutputUtil;
import MXDJR.RobotSocket;
import handling.world.World.Broadcast;
import tools.MaplePacketCreator;
import java.awt.Point;
import server.maps.MapleMap;
import handling.channel.ChannelServer;
import handling.world.MapleParty;
import server.ServerProperties;

public class 活动捉鬼任务
{
    public static int 捉鬼任务;
    public static int 刷新时间;
    
    public static void 启动捉鬼任务() {
        随机商人出现条件();
    }
    
    public static void 随机商人出现条件() {
        int pind = (int)Math.ceil(Math.random() * (double)Integer.parseInt(ServerProperties.getProperty("Lzj.Channel.count")));
        if (pind == 0) {
            ++pind;
        }
        final int[][] 坐标 = { { 106010000, 488, 215 }, { 240010000, 905, -298 }, { 240010100, 2098, -508 }, { 240010200, 2920, -688 }, { 240010200, 409, -688 }, { 110000000, -64, 151 }, { 110000000, 379, -143 }, { 110010000, -1594, -113 }, { 110010000, 1204, -473 }, { 104040000, 1059, -687 }, { 104040000, 48, -685 }, { 104020000, 1418, -1345 }, { 104010000, 2329, -115 }, { 104010002, -1401, -25 }, { 100000002, -16, -475 }, { 100000002, 214, -475 }, { 100030000, -3652, -205 }, { 100040000, 349, 1752 }, { 105050000, 2282, 1619 }, { 105090301, 928, -923 }, { 105040305, 1245, 2295 }, { 100030000, -2977, -1465 }, { 100000006, -705, 215 }, { 600000000, 5682, -632 }, { 200070000, -132, -715 }, { 222010201, 120, -1047 }, { 100040104, 66, 812 }, { 260010400, 199, -85 }, { 103010000, -1088, 232 }, { 230010201, -50, -17 }, { 240020100, -889, -508 }, { 221020000, 11, 2162 }, { 220070201, 811, 1695 }, { 541010040, 1075, -1695 }, { 261000001, -47, 64 } };
        final int rand = (int)Math.floor(Math.random() * (double)坐标.length);
        MapleParty.捉鬼任务频道 = pind;
        MapleParty.捉鬼任务地图 = 坐标[rand][0];
        MapleParty.捉鬼任务坐标X = 坐标[rand][1];
        MapleParty.捉鬼任务坐标Y = 坐标[rand][2];
        final ChannelServer channelServer = ChannelServer.getInstance(MapleParty.捉鬼任务频道);
        final MapleMap mapleMap = channelServer.getMapFactory().getMap(MapleParty.捉鬼任务地图);
        召唤捉鬼任务();
    }
    
    public static void 召唤捉鬼任务() {
        final ChannelServer channelServer = ChannelServer.getInstance(MapleParty.捉鬼任务频道);
        MapleMap mapleMap = channelServer.getMapFactory().getMap(MapleParty.捉鬼任务地图);
        if (mapleMap != null) {
            mapleMap.spawnNpc(活动捉鬼任务.捉鬼任务, new Point(MapleParty.捉鬼任务坐标X, MapleParty.捉鬼任务坐标Y));
        }
        else {
            mapleMap = channelServer.getMapFactory().getMap(106010000);
            mapleMap.spawnNpc(活动捉鬼任务.捉鬼任务, new Point(488, 215));
        }
        final String 信息 = "[捉鬼任务] : 一个神秘的鬼差出现在 " + MapleParty.捉鬼任务频道 + " 频道" + mapleMap.getMapName() + "。";
        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, 信息));
        RobotSocket.robbatSpeak(信息);
        RobotSocket.robbatSpeak(信息);
        RobotSocket.robbatSpeak(信息);
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][服务端] : " + 信息);
        final String 信息2 = "[捉鬼任务] : 下一次出现的时间约为" + 活动捉鬼任务.刷新时间 + "分钟之后";
        RobotSocket.robbatSpeak(信息2);
        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, 信息2));
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][服务端] : " + 信息2);
        捉鬼任务线程.开启进阶BOSS线程();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep((long)(60000 * 活动捉鬼任务.刷新时间));
                    if (ChannelServer.getInstance(MapleParty.捉鬼任务频道) != null) {
                        活动捉鬼任务.删除捉鬼任务();
                    }
                    活动捉鬼任务.启动捉鬼任务();
                }
                catch (InterruptedException ex) {}
            }
        }.start();
    }
    
    public static void 删除捉鬼任务() {
        final ChannelServer channelServer = ChannelServer.getInstance(MapleParty.捉鬼任务频道);
        final MapleMap mapleMap = (channelServer.getMapFactory().getMap(MapleParty.捉鬼任务地图) == null) ? channelServer.getMapFactory().getMap(10000) : channelServer.getMapFactory().getMap(MapleParty.捉鬼任务地图);
        mapleMap.removeNpc(活动捉鬼任务.捉鬼任务);
        MapleParty.捉鬼任务 = 0;
    }
    
    static {
        捉鬼任务 = Integer.parseInt(ServerProperties.getProperty("Lzj.捉鬼任务NPC"));
        刷新时间 = Integer.parseInt(ServerProperties.getProperty("Lzj.捉鬼任务刷新时间"));
    }
}
