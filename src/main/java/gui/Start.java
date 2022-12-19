package gui;

import LzjSetting.Game;
import client.LoginCrypto;
import client.MapleCharacter;
import client.SkillFactory;
import client.inventory.Equip;
import client.inventory.IItem;
import client.inventory.OnlyID;
import constants.AuthServerNamecpu;
import constants.ServerConfig;
import constants.tzjc;
import database.DBConPool;
import gui.进阶BOSS.活动捉鬼任务;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.channel.MapleGuildRanking;
import handling.login.LoginInformationProvider;
import handling.login.LoginServer;
import handling.world.World;
import handling.world.World.Broadcast;
import handling.world.family.MapleFamilyBuff;
import server.*;
import server.Timer.*;
import server.events.MapleOxQuizFactory;
import server.life.MapleLifeFactory;
import server.life.PlayerNPC;
import server.maps.MapleMapFactory;
import server.quest.MapleQuest;
import server.shops.MaplePlayerShopItem;
import tools.FileoutputUtil;
import tools.MacAddressTool;
import tools.MaplePacketCreator;
import tools.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Start {
    public static final Start instance;
    public static Map<String, Integer> ConfigValuesMap;
    public static ArrayList<String> 公告列表;
    public static List<Pair<String, Integer>> exptable;
    public static List<Pair<String, Integer>> 经验加成表;
    public static List<Pair<Integer, Pair<String, Pair<String, Integer>>>> 套装加成表;
    public static List<BossInMap> 野外boss刷新;
    public static List<String> 不参与叠加道具;
    public static List<SkillType> SkillType;
    public static List<String> 子弹列表;
    public static List<Pair<Integer, Integer>> 坐骑列表;
    public static List<String> 特殊组队经验加成表;
    public static List<String> mobmaptable;
    public static List<String> 宠物不参与地图表;
    public static List<String> 宠物吸金表;
    public static List<String> 宠物吸物表;
    private static long lastCheckjyhtime;
    public static int 世界等级;
    private static boolean 活动事件每日自动启动开关;
    public static List<MaplePlayerShopItem> jyhItem;
    public static Map<Integer, gui.jyhss.jyhwpss> jyhshu;
    public static boolean 交易行;
    public static Map<Integer, Boolean> jyhdq;
    private static Boolean isClearBossLog;
    private static int 记录在线时间;
    private static Calendar calendar;
    private static int 时;
    private static int 分;
    private static int 星期;
    private static int 年;
    private static int 月;
    private static int 日;
    private static int 秒;
    private static Boolean isClearZXSJ;
    public static int 捉鬼任务初始召唤时间;
    private static int 捉鬼任务初始化;
    private static int 回收内存;
    public static int 公告;
    public static int 公告数量;

    private static void resetAllLoginState() {
        final String name = null;
        final int id = 0;
        final int vip = 0;
        final int size = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("UPDATE accounts SET loggedin = 0");
            ps.executeUpdate();
            DBConPool.cleanUP(null, ps, con);
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable) ex);
            DBConPool.cleanUP(null, ps, con);
            throw new RuntimeException("【错误】 请确认资料库是否正确连接");
        } finally {
            DBConPool.cleanUP(null, ps, con);
        }
    }

    public static void 记录在线时间(final int time) {
        WorldTimer.getInstance().register((Runnable) new Runnable() {
            @Override
            public void run() {
                if (Start.记录在线时间 > 0) {
                    final Calendar calendar = Calendar.getInstance();
                    final int 时 = Calendar.getInstance().get(11);
                    final int 分 = Calendar.getInstance().get(12);
                    final int 星期 = Calendar.getInstance().get(7);
                    final int 日 = Calendar.getInstance().get(5);
                    if (时 == 0 && !Start.isClearBossLog.booleanValue()) {
                        try {
                            try (final PreparedStatement ps = DBConPool.getInstance().getDataSource().getConnection().prepareStatement("UPDATE accounts_info SET gamePointspd = 0")) {
                                ps.executeUpdate();
                                ps.close();
                            }
                            try (final PreparedStatement ps = DBConPool.getInstance().getDataSource().getConnection().prepareStatement("UPDATE accounts_info SET gamePointsps = 0")) {
                                ps.executeUpdate();
                                ps.close();
                            }
                            try (final PreparedStatement ps = DBConPool.getInstance().getDataSource().getConnection().prepareStatement("UPDATE accounts_info SET gamePoints = 0")) {
                                ps.executeUpdate();
                                ps.close();
                            }
                            try (final PreparedStatement ps = DBConPool.getInstance().getDataSource().getConnection().prepareStatement("UPDATE bosslog SET sz1 = 0")) {
                                ps.executeUpdate();
                                ps.close();
                            }
                            try (final PreparedStatement ps = DBConPool.getInstance().getDataSource().getConnection().prepareStatement("UPDATE accountidbosslog SET sz1 = 0")) {
                                ps.executeUpdate();
                                ps.close();
                            }
                            try (final PreparedStatement ps = DBConPool.getInstance().getDataSource().getConnection().prepareStatement("UPDATE paymoney1 SET mrjf = 0")) {
                                ps.executeUpdate();
                                ps.close();
                            }
                            if (日 == 1) {
                                try (final PreparedStatement ps = DBConPool.getInstance().getDataSource().getConnection().prepareStatement("UPDATE qiandao SET sz1 = 0")) {
                                    ps.executeUpdate();
                                    ps.close();
                                }
                            }
                            if (星期 == 2) {
                                try (final PreparedStatement ps = DBConPool.getInstance().getDataSource().getConnection().prepareStatement("UPDATE onetimelogd SET log = 0")) {
                                    ps.executeUpdate();
                                    ps.close();
                                }
                            }
                        } catch (SQLException ex) {
                            System.err.println("[服务端] : 服务端处理每日数据出错 × " + ex.getMessage());
                        }
                        Start.isClearBossLog = Boolean.valueOf(true);
                    } else if (时 == 23) {
                        Start.isClearBossLog = Boolean.valueOf(false);
                    }
                    if (Boolean.parseBoolean(ServerProperties.getProperty("Lzj.捉鬼任务启动"))) {
                        if (Start.捉鬼任务初始化 == 3) {
                            活动捉鬼任务.启动捉鬼任务();
                            Start.捉鬼任务初始化++;
                        } else {
                            Start.捉鬼任务初始化++;
                        }
                    }
                    for (final ChannelServer chan : ChannelServer.getAllInstances()) {
                        for (final MapleCharacter chr : chan.getPlayerStorage().getAllCharacters()) {
                            if (chr == null) {
                                continue;
                            }
                            chr.gainGamePoints(1);
                            if (时 == 0 && !Start.isClearZXSJ.booleanValue()) {
                                chr.resetGamePointsPS();
                                chr.resetGamePointsPD();
                                Start.isClearZXSJ = Boolean.valueOf(true);
                            } else {
                                if (时 != 23) {
                                    continue;
                                }
                                Start.isClearZXSJ = Boolean.valueOf(false);
                            }
                        }
                    }
                } else {
                    Start.记录在线时间++;
                }
            }
        }, (long) (60000 * time));
    }

    public static void 在线存档(final int time) {
        WorldTimer.getInstance().register((Runnable) new Runnable() {
            @Override
            public void run() {
                int p = 0;
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        ++p;
                        chr.saveToDB(true, true);
                    }
                }
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:保存数据每十分钟:" + p + "个玩家数据已经被正常储存");
            }
        }, (long) (60000 * time));
    }

    public static void 最高在线人数() {
        WorldTimer.getInstance().register((Runnable) new Runnable() {
            @Override
            public void run() {
                int p = 0;
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        ++p;
                    }
                }
                LZJMS.人数更新(p);
            }
        }, 1000L);
    }

    public static String getCPUSerial() {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_Processor\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.ProcessorId \n    exit for  ' do the first cpu only! \nNext \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = input.readLine()) != null) {
                result = result + line;
            }
            input.close();
            file.delete();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        if ((result.trim().length() < 1) || (result == null)) {
            result = "无机器码被读取";
        }
        return result.trim();
    }

    public static String getHardDiskSerialNumber(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"" + drive + "\")\n" + "Wscript.Echo objDrive.SerialNumber";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result = result + line;
            }
            input.close();
        } catch (Exception e) {
        }
        return result.trim();
    }

    public static String returnSerialNumber() {
        String cpu = getCPUSerial();
        String disk = getHardDiskSerialNumber("C");

        int newdisk = Integer.parseInt(disk);

        String s = cpu + newdisk;
        String newStr = s.substring(8, s.length());
        return newStr;
    }

    public static boolean 授权验证() {
        String mac = MacAddressTool.getMacAddress(false);
        String num = returnSerialNumber();
        String localMac = LoginCrypto.hexSha1(num + mac);//本机机器码
        if (localMac != null) {
            for (AuthServerNamecpu auth : AuthServerNamecpu.values()) {
                if (auth.getMac().equals(localMac)) {
                    return true;
                }
            }
        } else {
        }
        FileoutputUtil.outputFileKey(localMac);
        return false;

    }

    public static final void startServer(final String[] args) {
        System.out.println("○ 正在验证授权...");
        //if (授权验证()) {
        //  System.out.println("○ 服务端授权验证成功...");
        //  } else {
        //   System.out.println("○ 服务端授权验证失败...请联系作者QQ1669422092");
        //   System.exit(0);
        //   }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][========================================]");
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化配置表内容");
        初始化配置表();
        初始化表单();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化加载配置表内容");
        GetConfigValues();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化阶段经验表内容");
        GetExpTable();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化多倍怪物地图表内容");
        GetMobMapTable();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化宠吸无法使用地图列表内容");
        GetPetNoMapTable();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化宠物吸物列表内容");
        GetPetItemTable();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化宠物吸金列表内容");
        GetPetMesoTable();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化不叠加道具表内容");
        GetNoSlotMaxTable();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化子弹列表内容");
        GetRechargeTable();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化组队职业经验加成内容");
        GetSpecialJobTable();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化道具加成经验");
        GetItemExpTable();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化套装加成经验");
        GetSuitDamTable();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化坐骑列表");
        GetRidMobTable();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化野外boss刷新");
        GetMobInMapTable();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化技能检测信息");
        GetSkillTable();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:IP效验正在载入");
        boolean ip验证 = false;
        for (int a = 0; a < ServerConfig.IP.length; ++a) {
            if (Game.初始ip.equals(ServerConfig.IP[a])) {
                ip验证 = true;
                ServerConfig.nub1 = 0;
            }
        }
        {
            ip验证 = true;
            ServerConfig.nub1 = 0;
        }
        if (!ip验证) {
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:当前IP不在允许使用范围");
        } else {
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:IP效验成功");
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化所有玩家在线情况");
            resetAllLoginState();
            if (((Integer) Start.ConfigValuesMap.get("管理员独占登录")).intValue() > 0) {
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:开启仅管理员登录游戏");
            }
            if (((Integer) Start.ConfigValuesMap.get("是否开启自动注册")).intValue() > 0) {
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:开启自动注冊");
            }
            if (((Integer) Start.ConfigValuesMap.get("允许玩家使用管理员道具")).intValue() > 0) {
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:开启允许玩家使用管理员物品");
            }
            ServerConfig.loadSetting();
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:开启时间线程");
            ServerConfig.loadSetting();
            World.init();
            WorldTimer.getInstance().start();
            EtcTimer.getInstance().start();
            MapTimer.getInstance().start();
            MobTimer.getInstance().start();
            CloneTimer.getInstance().start();
            EventTimer.getInstance().start();
            BuffTimer.getInstance().start();
            PingTimer.getInstance().start();
            服务器状态监控();
            最高在线人数();
            LoginInformationProvider.getInstance();
            FishingRewardFactory.getInstance();
            MapleQuest.initQuests();
            MapleLifeFactory.loadQuestCounts();
            MapleOxQuizFactory.getInstance().initialize();
            MapleItemInformationProvider.getInstance().load();
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化卡罗牌信息");
            PredictCardFactory.getInstance().initialize();
            CashItemFactory.getInstance().initialize();
            RandomRewards.getInstance();
            SkillFactory.LoadSkillInformaion();
            MapleCarnivalFactory.getInstance();
            MapleGuildRanking.getInstance().getGuildRank();
            MapleGuildRanking.getInstance().getJobRank(1);
            MapleGuildRanking.getInstance().getJobRank(2);
            MapleGuildRanking.getInstance().getJobRank(3);
            MapleGuildRanking.getInstance().getJobRank(4);
            MapleGuildRanking.getInstance().getJobRank(5);
            MapleGuildRanking.getInstance().getJobRank(6);
            MapleFamilyBuff.getBuffEntry();
            LoginServer.setup();
            ChannelServer.startAllChannels();
            CashShopServer.setup();
            CheatTimer.getInstance().register((Runnable) AutobanManager.getInstance(), 60000L);
            Runtime.getRuntime().addShutdownHook(new Thread((Runnable) ShutdownServer.getInstance()));
            SpeedRunner.getInstance().loadSpeedRuns();
            World.registerRespawn();
            PlayerNPC.loadAll();
            LoginServer.setOn();
            MapleMapFactory.loadCustomLife();
            记录在线时间(1);
            回收内存(120);
            公告初始化();
            公告(((Integer) Start.ConfigValuesMap.get("公告间隔时间")).intValue());
            World.isShutDown = false;
            OnlyID.getInstance();
            Game.服务端启动中 = 1;
            LZJMS.配置同步到界面();
            tzjc.sr_tz();
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]服务器端已经启动完毕。");
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][========================================]");
        }
    }

    public static void 回收内存(final int time) {
        WorldTimer.getInstance().register((Runnable) new Runnable() {
            @Override
            public void run() {
                if (Start.回收内存 > 0) {
                    System.gc();
                    System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + " ][信息]服务器端成功回收内存。");
                } else {
                    Start.回收内存++;
                }
            }
        }, (long) (60000 * time));
    }

    public static void 服务器状态监控() {
        WorldTimer.getInstance().register((Runnable) new Runnable() {
            @Override
            public void run() {
                LZJMS.UI信息更新();
            }
        }, 1000L);
    }

    public static void GetConfigValues() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT name, val FROM ConfigValues");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String name = rs.getString("name");
                final int val = rs.getInt("val");
                Start.ConfigValuesMap.put(name, Integer.valueOf(val));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取动态数据库出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void GetExpTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT name, numb FROM exptable");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String name = rs.getString("name");
                final int val = rs.getInt("numb");
                Start.exptable.add(new Pair(name, Integer.valueOf(val)));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取不同阶段经验表出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void GetSkillTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("select * from skilltest");
            rs = ps.executeQuery();
            while (rs.next()) {
                final int skillid = rs.getInt("skillid");
                final int attackcount = rs.getInt("attackcount");
                final int mobcount = rs.getInt("mobcount");
                Start.SkillType.add(new SkillType(skillid, mobcount, attackcount));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取技能表出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void GetMobInMapTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT mobid, map,x,y,msg,time FROM bossmobinmap");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String msg = rs.getString("msg");
                final int mobid = rs.getInt("mobid");
                final int map = rs.getInt("map");
                final int x = rs.getInt("x");
                final int y = rs.getInt("y");
                final int time = rs.getInt("time");
                Start.野外boss刷新.add(new BossInMap(mobid, map, x, y, msg, time));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取道具经验表出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void GetItemExpTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT name, numb FROM itemexptable");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String name = rs.getString("name");
                final int val = rs.getInt("numb");
                Start.经验加成表.add(new Pair(name, Integer.valueOf(val)));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取道具经验表出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void GetSuitDamTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT name, numb,proportion,proname FROM suitdamtable");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String name = rs.getString("name");
                final String name2 = rs.getString("proname");
                final int val = rs.getInt("numb");
                final int vol = rs.getInt("proportion");
                Start.套装加成表.add(new Pair(Integer.valueOf(vol), new Pair(name2, new Pair(name, Integer.valueOf(val)))));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("套装加成表出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void GetRidMobTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT characterid, numb FROM riddingmob");
            rs = ps.executeQuery();
            while (rs.next()) {
                final int id = rs.getInt("characterid");
                final int val = rs.getInt("numb");
                Start.坐骑列表.add(new Pair(Integer.valueOf(id), Integer.valueOf(val)));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取坐骑列表出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void GetMobMapTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT name FROM mobmaptable");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String name = rs.getString("name");
                Start.mobmaptable.add(name);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取多倍怪物地图列表出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void GetPetNoMapTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT name FROM petnomaptable");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String name = rs.getString("name");
                Start.宠物不参与地图表.add(name);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取宠物不参与吸取地图出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void GetPetMesoTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT name FROM petmesotable");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String name = rs.getString("name");
                Start.宠物吸金表.add(name);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取宠物吸金宠物列表出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void GetPetItemTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT name FROM petitemtable");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String name = rs.getString("name");
                Start.宠物吸物表.add(name);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取宠物吸物宠物列表出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void GetNoSlotMaxTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT name FROM noslotmax");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String name = rs.getString("name");
                Start.不参与叠加道具.add(name);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取不参与叠加列表出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void GetRechargeTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT name FROM rechargeableItems");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String name = rs.getString("name");
                Start.子弹列表.add(name);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取子弹列表出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void GetSpecialJobTable() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT name FROM specialjobtable");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String name = rs.getString("name");
                Start.特殊组队经验加成表.add(name);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取组队经验加成出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void 公告初始化() {
        Start.公告列表.clear();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT * FROM messages");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String name = rs.getString("message");
                Start.公告列表.add(name);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("读取动态数据库出错：" + ex.getMessage());
        } finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }

    public static void 公告(final int time) {
        WorldTimer.getInstance().register((Runnable) new Runnable() {
            @Override
            public void run() {
                if (Start.公告列表.size() > 0) {
                    String 公告信息 = null;
                    公告信息 = Start.输出公告((int) Math.floor(Math.random() * (double) Start.公告列表.size()));
                    if (公告信息 != null) {
                        Broadcast.broadcastMessage(MaplePacketCreator.yellowChat("[悠悠冒险岛 帮助]" + 公告信息));
                    }
                } else {
                    Start.公告初始化();
                }
            }
        }, (long) (time * 1000 * 60));
    }

    public static String 输出公告(final int a) {
        return (String) Start.公告列表.get(a);
    }

    public static int getFZ9(final String log1) {
        int jf = 0;
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("select * from fz9 where log = ?");
            ps.setString(1, log1);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jf = rs.getInt("sz1");
            } else {
                final PreparedStatement psu = con.prepareStatement("insert into fz9 (log, sz1) VALUES (?, ?)");
                psu.setString(1, log1);
                jf = 50;
                psu.setInt(2, 50);
                psu.executeUpdate();
                psu.close();
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("FZ9读取发生错误: " + ex);
        }
        return jf;
    }

    public static void 初始化配置表() {
        final String[] 内容 = {"仅允许管理员登录", "火热程度", "登录自动注册开关", "角色个数", "泡点时间间隔", "频道人数", "频道热度", "允许玩家使用管理员道具", "允许玩家进入商城", "角色最大等级", "允许创建冒险家", "允许创建骑士团", "允许创建战神", "同IP多开", "同MAC多开", "IP多开上限", "MAC多开上限", "是否打印雇佣记录", "是否打印喇叭聊天", "是否打印商城购买", "是否打印伤害修正", "是否打印普通聊天", "是否打印封包", "是否开启自动注册", "是否开启Debug模式", "雇佣持续时间", "雇佣自动回收", "泡点金币", "泡点经验", "泡点抵用", "泡点点券", "雇佣泡点时间间隔", "雇佣泡点金币", "雇佣泡点点券", "经验结婚加成", "经验网吧加成", "经验吊坠加成", "经验人气加成", "蓝蜗牛", "蘑菇仔", "绿水灵", "星精灵", "胖企鹅", "白雪人", "大海龟", "章鱼怪", "顽皮猴", "漂漂猪", "小青蛇", "管理员隐身", "红螃蟹", "石头人", "紫色猫", "大灰狼", "青鳄鱼", "花蘑菇", "火野猪", "小白兔", "喷火龙", "玩家登录", "管理员独占登录", "记录登录信息", "欢迎弹窗", "玩家交易", "丢出金币", "丢出物品", "上线提示", "升级提示", "领主提示", "游戏喇叭", "怪物BUFF", "越级打怪", "全服决斗", "雇佣商人", "整点重载", "地图名称", "玩家指令", "游戏仓库", "指令通知", "管理员加速", "商城检测", "段数检测", "攻速检测", "全屏检测", "吸怪检测", "吸物检测", "伤害检测", "丢失伤害", "伤害上限", "服务器容错范围", "击杀作弊者", "全服通告", "封停掉落", "封停账号", "封停IP", "封停MAC", "物品掉落持续时间", "物品地图数量上限", "怪物刷新频率", "魔族突袭", "魔族攻城", "OX答题", "幸运职业", "全服双倍", "野外通缉", "喜从天降", "神秘商人", "半价福利", "金银送货", "全局血量等级", "全局血量", "商城扩充价格", "MAC注册上限", "账号角色上限", "雇佣经验加成开关", "记录登录信息", "阶段经验开关", "雇佣经验加成比例", "泡点开关", "泡点等级开关", "泡点地图", "离线泡点金币", "离线泡点经验", "离线泡点等级开关", "离线泡点点券", "离线泡点抵用", "离线泡点开关", "离线泡点地图", "离线给在线时间开关", "怪物多倍地图倍率", "怪物刷新频率设定", "怪物多倍地图开关", "怪物地图多倍怪物开关", "爆物上线开关", "等级连升开关", "爆物上线数量", "等级范围", "自定义箱子代码", "所有显示开关", "突破显示开关", "伤害突破开关", "装备卡破功开关", "怪物血量显示开关", "怪物减伤开关", "世界等级开关", "金币重置", "战力修正", "宠物自动吃药开关", "坐骑恢复开关", "叠加开关", "叠加上线", "突破上线", "砍爆率", "出装备概率", "自动吃药道具", "坐骑恢复频率", "坐骑恢复道具", "不参与叠加开关", "上线喇叭", "升级群消息通知", "成就还原上卷记录开关", "成就上卷加七记录开关", "成就上卷加三记录开关", "道具经验开关", "野外boss击杀广播", "boss击杀记录", "击杀boss打开npc", "子弹扩充开关", "特殊组队经验加成", "原始组队经验加成", "修正组队经验加成", "玩家聊天", "金锤子使用开关", "金锤子使用概率", "GM固伤开关", "GM固伤伤害", "个数检测", "点券比例", "BOSS出装备概率", "金币全局砍数量", "金币砍全局倍率", "特殊宠物吸取开关", "特殊宠物吸物开关", "特殊宠物吸金开关", "特殊宠物吸物无法使用地图开关", "宠吸道具", "道具强行宠吸开关", "伤害修正", "重置技能范围开关", "重置技能总范围", "越级带人开关", "越级带人道具开关", "越级带人道具", "套装属性加成开关", "套装个数", "宠物不饥饿开关", "坐骑不饥饿开关", "特殊全宠物吸物开关", "修正队员分配经验", "表单卡破功开关", "玩家升级喇叭", "无限BUFF", "地图刷新频率", "自动刷钱道具", "克隆基础伤害", "邀请人百分比获取", "自定义伤害加成开关", "道具加成自定义伤害开关", "伤害高于次数值", "自定义伤害气泡显示", "自定义伤害段数显示", "自定义伤害加成道具代码", "自定义力量加成比例", "自定义敏捷加成比例", "自定义智力加成比例", "自定义运气加成比例", "自定义物攻加成比例", "自定义魔攻加成比例", "自定义物防加成比例", "自定义魔防加成比例", "自定义血量加成比例", "自定义魔量加成比例", "自定义伤害黄字喇叭显示", "扣除21E伤害", "公告间隔时间", "特殊全宠物吸金开关"};
        for (int a = 0; a < 内容.length; ++a) {
            数据库读取(内容[a]);
        }
    }

    public static void 初始化表单() {
        表单读取("suitdamtable", "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name varchar(255), numb int(15), proportion int(15), proname varchar(255)");
        表单读取("petitemtable", "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name varchar(255)");
        表单读取("petmesotable", "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name varchar(255)");
        表单读取("petnomaptable", "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name varchar(255)");
    }

    public static void 表单读取(final String a, final String b) {
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            final ResultSet rs = con.getMetaData().getTables(null, null, a, null);
            if (!rs.next()) {
                final PreparedStatement psu = con.prepareStatement("create table " + a + "(" + b + ")");
                psu.execute();
                psu.close();
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("新建表单出现异常读取发生错误: " + ex);
        }
    }

    public static void 数据库读取(final String a) {
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("select * from configvalues where name = ?");
            ps.setString(1, a);
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                final PreparedStatement psu = con.prepareStatement("insert into configvalues (name, Val) VALUES (?, ?)");
                psu.setString(1, a);
                psu.setInt(2, 0);
                psu.executeUpdate();
                psu.close();
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("configvalues读取发生错误: " + ex);
        }
    }

    public static boolean canCheckjyh() {
        if (Start.lastCheckjyhtime + 600000L > System.currentTimeMillis()) {
            return false;
        }
        Start.lastCheckjyhtime = System.currentTimeMillis();
        return true;
    }

    public static void jyhshucc(final int id, final int sl, final String beizhu, final int inventorytype) {
        if (Start.jyhshu.containsKey(Integer.valueOf(id))) {
            Start.jyhshu.put(Integer.valueOf(id), new gui.jyhss.jyhwpss(((gui.jyhss.jyhwpss) Start.jyhshu.get(Integer.valueOf(id))).getsl() + sl, beizhu, inventorytype));
            if (((gui.jyhss.jyhwpss) Start.jyhshu.get(Integer.valueOf(id))).getsl() <= 0) {
                Start.jyhshu.remove(Integer.valueOf(id));
            }
        } else if (sl > 0) {
            Start.jyhshu.put(Integer.valueOf(id), new gui.jyhss.jyhwpss(sl, beizhu, inventorytype));
        }
    }

    public static boolean buyjyh(final MapleCharacter chr, final int 编号) {
        int moneyLX = -1;
        int moneyL = -1;
        if (编号 < 0) {
            chr.dropMessage(1, "装备编号不存在");
            return false;
        }
        if (!chr.canHoldSlots(1)) {
            chr.dropMessage(1, "背包格子不足，不能操作");
            return false;
        }
        for (final MaplePlayerShopItem itemss : Start.jyhItem) {
            if (itemss.jyhid == 编号) {
                moneyLX = itemss.lx;
                moneyL = itemss.price;
                break;
            }
        }
        switch (moneyLX) {
            case 0: {
                if (chr.getMeso() < moneyL) {
                    chr.dropMessage(1, "a金币不足");
                    return false;
                }
                break;
            }
            case 1: {
                if (chr.getCSPoints(1) < moneyL) {
                    chr.dropMessage(1, "a点卷不足");
                    return false;
                }
                break;
            }
            case 2: {
                if (chr.getCSPoints(2) < moneyL) {
                    chr.dropMessage(1, "a抵用券不足");
                    return false;
                }
            }
            case 3: {
                if (chr.getCSPoints(5) < moneyL) {
                    chr.dropMessage(1, "a交易币不足");
                    return false;
                }
                break;
            }
        }
        int itemid = 0;
        int quantity = 0;
        int inventorytype = -1;
        int charactersid = -1;
        Equip equip = null;
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM `jyh` WHERE  `tid` = ?");
            ps.setInt(1, 编号);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                inventorytype = rs.getInt("inventorytype");
                charactersid = rs.getInt("charactersid");
                moneyLX = rs.getInt("moneytype");
                moneyL = rs.getInt("money");
                itemid = rs.getInt("itemid");
                quantity = rs.getInt("quantity");
                if (inventorytype == 1) {
                    equip = new Equip(rs.getInt("itemid"), (short) 0);
                    equip.setQuantity((short) 1);
                    equip.setOwner(rs.getString("owner"));
                    equip.setExpiration(rs.getLong("expiredate"));
                    equip.setUpgradeSlots(rs.getByte("upgradeslots"));
                    equip.setLevel(rs.getByte("level"));
                    equip.setStr(rs.getShort("str"));
                    equip.setDex(rs.getShort("dex"));
                    equip.setInt(rs.getShort("int"));
                    equip.setLuk(rs.getShort("luk"));
                    equip.setHp(rs.getShort("hp"));
                    equip.setMp(rs.getShort("mp"));
                    equip.setWatk(rs.getShort("watk"));
                    equip.setMatk(rs.getShort("matk"));
                    equip.setWdef(rs.getShort("wdef"));
                    equip.setMdef(rs.getShort("mdef"));
                    equip.setAcc(rs.getShort("acc"));
                    equip.setAvoid(rs.getShort("avoid"));
                    equip.setHands(rs.getShort("hands"));
                    equip.setSpeed(rs.getShort("speed"));
                    equip.setJump(rs.getShort("jump"));
                    equip.setViciousHammer(rs.getByte("ViciousHammer"));
                    equip.setItemEXP(rs.getInt("itemEXP"));
                    equip.setGMLog(rs.getString("GM_Log"));
                    equip.setDurability(rs.getInt("durability"));
                    equip.setEnhance(rs.getByte("enhance"));
                    equip.setPotential1(rs.getShort("potential1"));
                    equip.setPotential2(rs.getShort("potential2"));
                    equip.setPotential3(rs.getShort("potential3"));
                    equip.setHpR(rs.getShort("hpR"));
                    equip.setMpR(rs.getShort("mpR"));
                    equip.setGiftFrom(rs.getString("sender"));
                    equip.setIncSkill(rs.getInt("incSkill"));
                    equip.setPVPDamage(rs.getShort("pvpDamage"));
                    equip.setCharmEXP(rs.getShort("charmEXP"));
                } else {
                    itemid = rs.getInt("itemid");
                    quantity = rs.getInt("quantity");
                }
                if (!deleitem(编号)) {
                    chr.dropMessage(1, "购买失败，可能改物品已经售出");
                    return false;
                }
                switch (moneyLX) {
                    case 0: {
                        if (chr.getMeso() < moneyL) {
                            chr.dropMessage(1, "b金币不足");
                            return false;
                        }
                        chr.gainMeso(-moneyL, true, true);
                        chr.gainjyhyh(0, charactersid, moneyL);
                        break;
                    }
                    case 1: {
                        if (chr.getCSPoints(1) < moneyL) {
                            chr.dropMessage(1, "b点卷不足");
                            return false;
                        }
                        chr.modifyCSPoints(1, -moneyL, true);
                        chr.gainjyhyh(1, charactersid, moneyL);
                        break;
                    }
                    case 2: {
                        if (chr.getCSPoints(2) < moneyL) {
                            chr.dropMessage(1, "b抵用券不足");
                            return false;
                        }
                        chr.modifyCSPoints(2, -moneyL, true);
                        chr.gainjyhyh(2, charactersid, moneyL);
                        break;
                    }
                    case 3: {
                        if (chr.getCSPoints(5) < moneyL) {
                            chr.dropMessage(1, "b交易币不足");
                            return false;
                        }
                        chr.modifyCSPoints(5, -moneyL, true);
                        chr.gainjyhyh(3, charactersid, moneyL);
                        break;
                    }
                }
                if (inventorytype == 1) {
                    MapleInventoryManipulator.addFromDrop(chr.getClient(), (IItem) equip, false);
                    chr.dropMessage(6, "购买成功，东西已经塞进您的背包");
                    chr.killjyhitem(编号);
                    FileoutputUtil.logToFile_chr(chr, "交易行购买.txt", " 物品id：" + itemid + " 数量：" + quantity + " 消费类型：" + moneyLX + " 金额：" + moneyL);
                    return true;
                }
                chr.gainItem(itemid, (int) (short) quantity);
                chr.dropMessage(6, "购买成功，东西已经塞进您的背包");
                chr.killjyhitem(编号);
                FileoutputUtil.logToFile_chr(chr, "交易行购买.txt", " 物品id：" + itemid + " 数量：" + quantity + " 消费类型：" + moneyLX + " 金额：" + moneyL);
                return true;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception Ex) {
            System.out.println("jyh购买物品失败" + Ex);
            return false;
        }
        return false;
    }

    public static boolean deleitem(final int 编号) {
        try {
            final Connection con1 = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con1.prepareStatement("DELETE FROM `jyh` WHERE `tid` = ?");
            ps.setInt(1, 编号);
            ps.executeUpdate();
            ps.close();
            con1.close();
            return true;
        } catch (Exception Ex) {
            System.out.println("交易购买后删除物品");
            return false;
        }
    }

    static {
        instance = new Start();
        ConfigValuesMap = (Map<String, Integer>) new HashMap();
        公告列表 = (ArrayList<String>) new ArrayList();
        exptable = (List<Pair<String, Integer>>) new ArrayList();
        经验加成表 = (List<Pair<String, Integer>>) new ArrayList();
        套装加成表 = (List<Pair<Integer, Pair<String, Pair<String, Integer>>>>) new ArrayList();
        野外boss刷新 = (List<BossInMap>) new ArrayList();
        不参与叠加道具 = (List<String>) new ArrayList();
        SkillType = (List<SkillType>) new ArrayList();
        子弹列表 = (List<String>) new ArrayList();
        坐骑列表 = (List<Pair<Integer, Integer>>) new ArrayList();
        特殊组队经验加成表 = (List<String>) new ArrayList();
        mobmaptable = (List<String>) new ArrayList();
        宠物不参与地图表 = (List<String>) new ArrayList();
        宠物吸金表 = (List<String>) new ArrayList();
        宠物吸物表 = (List<String>) new ArrayList();
        lastCheckjyhtime = 0L;
        世界等级 = getFZ9("世界等级");
        活动事件每日自动启动开关 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.活动事件每日自动启动开关"));
        jyhItem = (List<MaplePlayerShopItem>) new LinkedList();
        jyhshu = (Map<Integer, gui.jyhss.jyhwpss>) new HashMap();
        交易行 = true;
        jyhdq = (Map<Integer, Boolean>) new HashMap();
        isClearBossLog = Boolean.valueOf(false);
        记录在线时间 = 0;
        calendar = Calendar.getInstance();
        时 = Calendar.getInstance().get(11);
        分 = Calendar.getInstance().get(12);
        星期 = Calendar.getInstance().get(7);
        年 = Calendar.getInstance().get(1);
        月 = Calendar.getInstance().get(2) + 1;
        日 = Calendar.getInstance().get(5);
        秒 = Calendar.getInstance().get(13);
        isClearZXSJ = Boolean.valueOf(false);
        捉鬼任务初始召唤时间 = Integer.parseInt(ServerProperties.getProperty("Lzj.捉鬼任务初始召唤时间"));
        捉鬼任务初始化 = 0;
        回收内存 = 0;
        公告 = 0;
        公告数量 = 0;
    }
}
