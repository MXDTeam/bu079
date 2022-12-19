package gui.进阶BOSS;

import server.ServerProperties;
import server.life.MapleMonster;
import server.maps.MapleMap;
import java.awt.Point;
import server.life.OverrideMonsterStats;
import server.life.MapleLifeFactory;
import server.Timer.WorldTimer;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import tools.FileoutputUtil;
import database.DBConPool;
import java.util.Iterator;
import tools.MaplePacketCreator;
import client.MapleCharacter;
import handling.channel.ChannelServer;
import java.util.concurrent.ScheduledFuture;

public class Mushplotact
{
    private static ScheduledFuture<?> 蘑菇大军进程;
    private static ScheduledFuture<?> 蘑辣插进程;
    private static boolean StartState;
    private static boolean OneStartState;
    private static boolean SecStartState;
    private static boolean ThrStartState;
    private static boolean FouStartState;
    private static long OneTimeState;
    private static long SecTimeState;
    private static long ThrTimeState;
    private static long FouTimeState;
    private static int Mobspawnnumb;
    private static int Mobspawnnumb1;
    private static boolean Mobspawnrun;
    private static final int OneTimeCd;
    private static final int SecTimeCd;
    private static final int ThrTimeCd;
    private static final int FouTimeCd;
    private static String[] 怪物代码;
    private static String[] 怪物代码血量;
    private static String[] 怪物代码经验;
    private static final int 总波数;
    private static final int 怪物个数;
    private static boolean 怪物攻城入场bgm次数;
    private static boolean 怪物攻城进攻bgm次数;
    private static final String 怪物攻城入场bgm = "Bgm09/TheyMenacingYou";
    private static final String 怪物攻城进攻bgm = "Bgm09/TimeAttack";
    private static int 蘑菇金蛋代码;
    private static long 蘑菇金蛋血量;
    private static int 蘑菇金蛋经验;
    private static int 蘑辣插代码;
    private static long 蘑辣插血量;
    private static int 蘑辣插经验;
    private static int 蘑菇金蛋;
    private static int 蘑菇金蛋题库;
    private static int 蘑菇金蛋地图;
    private static int 蘑菇金蛋地图X坐标;
    private static int 蘑菇金蛋地图Y坐标;
    private static String 蘑菇金蛋提示a;
    private static String 蘑菇金蛋提示b;
    private static String 蘑菇金蛋提示c;
    private static int 蘑辣插的魔力源泉;
    
    public static void changeInitialization(final boolean 状态, final long 时长) {
        setStartState(状态);
        setOneTimeState(时长);
        setMobspawnrun(true);
    }
    
    public static void finishOneState() {
        if (getSecTimeState() == 0L) {
            setSecTimeState(System.currentTimeMillis());
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    mch.startMapEffect("【大型活动】:第二阶段 毒蘑菇大军迎亲大队! 即将在" + getSecTimeCd() + "秒后到达", 5121009);
                }
            }
            if (Mushplotact.怪物攻城入场bgm次数) {
                ChannelServer.getInstance(1).getMapFactory().getMap(106021600).broadcastMessage(MaplePacketCreator.musicChange("Bgm09/TheyMenacingYou"));
                Mushplotact.怪物攻城入场bgm次数 = false;
            }
        }
        else {
            final long Time = (long)Mushplotact.SecTimeCd - (System.currentTimeMillis() - Mushplotact.SecTimeState) / 1000L;
            for (final ChannelServer cserv2 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch2 : cserv2.getPlayerStorage().getAllCharacters()) {
                    mch2.startMapEffect("【大型活动】:第二阶段 毒蘑菇大军迎亲大队!即将在" + Time + "秒后到达", 5121009);
                }
            }
        }
        if (Mushplotact.Mobspawnnumb == 0) {
            召唤怪物大军();
            Mushplotact.Mobspawnnumb = 1;
        }
    }
    
    public static String getNumera() {
        return Mushplotact.蘑菇金蛋提示a;
    }
    
    public static String getNumerb() {
        return Mushplotact.蘑菇金蛋提示b;
    }
    
    public static String getNumerc() {
        return Mushplotact.蘑菇金蛋提示c;
    }
    
    public static int getEggmap() {
        return Mushplotact.蘑菇金蛋地图;
    }
    
    public static int getEggmapx() {
        return Mushplotact.蘑菇金蛋地图X坐标;
    }
    
    public static int getEggmapy() {
        return Mushplotact.蘑菇金蛋地图Y坐标;
    }
    
    public static int getMolacha() {
        return Mushplotact.蘑辣插的魔力源泉;
    }
    
    public static void setMolacha(final int 状态) {
        Mushplotact.蘑辣插的魔力源泉 += 状态;
    }
    
    public static int getEggspawn() {
        return Mushplotact.蘑菇金蛋;
    }
    
    public static void setEggspawn(final int 状态) {
        Mushplotact.蘑菇金蛋 += 状态;
    }
    
    public static boolean getMobspawnrun() {
        return Mushplotact.Mobspawnrun;
    }
    
    public static void setMobspawnrun(final boolean 状态) {
        Mushplotact.Mobspawnrun = 状态;
    }
    
    public static boolean getStartState() {
        return Mushplotact.StartState;
    }
    
    public static void setStartState(final boolean 状态) {
        Mushplotact.StartState = 状态;
    }
    
    public static boolean getOneStartState() {
        return Mushplotact.OneStartState;
    }
    
    public static void setOneStartState(final boolean 状态) {
        Mushplotact.OneStartState = 状态;
    }
    
    public static long getOneTimeState() {
        return Mushplotact.OneTimeState;
    }
    
    public static void setOneTimeState(final long 状态) {
        Mushplotact.OneTimeState = 状态;
    }
    
    public static int getOneTimeCd() {
        return Mushplotact.OneTimeCd;
    }
    
    public static boolean getSecStartState() {
        return Mushplotact.SecStartState;
    }
    
    public static void setSecStartState(final boolean 状态) {
        Mushplotact.SecStartState = 状态;
    }
    
    public static long getSecTimeState() {
        return Mushplotact.SecTimeState;
    }
    
    public static void setSecTimeState(final long 状态) {
        Mushplotact.SecTimeState = 状态;
    }
    
    public static int getSecTimeCd() {
        return Mushplotact.SecTimeCd;
    }
    
    public static boolean getThrStartState() {
        return Mushplotact.ThrStartState;
    }
    
    public static void setThrStartState(final boolean 状态) {
        Mushplotact.ThrStartState = 状态;
    }
    
    public static long getThrTimeState() {
        return Mushplotact.ThrTimeState;
    }
    
    public static void setThrTimeState(final long 状态) {
        Mushplotact.ThrTimeState = 状态;
    }
    
    public static int getThrTimeCd() {
        return Mushplotact.ThrTimeCd;
    }
    
    public static boolean getFouStartState() {
        return Mushplotact.FouStartState;
    }
    
    public static void setFouStartState(final boolean 状态) {
        Mushplotact.FouStartState = 状态;
    }
    
    public static long getFouTimeState() {
        return Mushplotact.FouTimeState;
    }
    
    public static void setFouTimeState(final long 状态) {
        Mushplotact.FouTimeState = 状态;
    }
    
    public static int getFouTimeCd() {
        return Mushplotact.FouTimeCd;
    }
    
    public static int getMushroomStage(final String log1) {
        int 阶段 = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("select * from mogulog where log = ?");
            ps.setString(1, log1);
            rs = ps.executeQuery();
            if (rs.next()) {
                阶段 = rs.getInt("sz1");
            }
            else {
                final PreparedStatement psu = con.prepareStatement("insert into mogulog (log, sz1) VALUES (?, ?)");
                psu.setString(1, log1);
                psu.setInt(2, 1);
                psu.executeUpdate();
                psu.close();
                阶段 = 1;
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (Exception Ex) {
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)Ex);
            return -1;
        }
        finally {
            DBConPool.cleanUP(rs, ps, con);
        }
        return 阶段;
    }
    
    public static void setMushroomStage(final String 文本, final int 次数) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("UPDATE mogulog SET sz1 = ? where log = ?");
            ps.setInt(1, 次数);
            ps.setString(2, 文本);
            ps.executeUpdate();
            ps.close();
            con.close();
        }
        catch (Exception Ex) {
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)Ex);
        }
        finally {
            DBConPool.cleanUP(null, ps, con);
        }
    }
    
    public static String checkLastonetime() {
        if (System.currentTimeMillis() - Mushplotact.OneTimeState >= (long)(1000 * Mushplotact.OneTimeCd)) {
            final String 文本 = "[蘑菇PPG活动] ：活动已经开始了";
            if (!getOneStartState()) {
                setOneStartState(true);
            }
            return 文本;
        }
        final String 文本 = "[蘑菇PPG活动] : 请你等待 " + ((long)Mushplotact.OneTimeCd - (System.currentTimeMillis() - Mushplotact.OneTimeState) / 1000L) + " 秒后再尝试打开npc。";
        return 文本;
    }
    
    public static String checkLastSectime() {
        if (System.currentTimeMillis() - Mushplotact.SecTimeState >= (long)(1000 * Mushplotact.SecTimeCd)) {
            final String 文本 = "[蘑菇PPG活动] ：活动已经开始了";
            if (!getSecStartState()) {
                setSecStartState(true);
            }
            return 文本;
        }
        final String 文本 = "[蘑菇PPG活动] : 请你等待 " + ((long)Mushplotact.SecTimeCd - (System.currentTimeMillis() - Mushplotact.SecTimeState) / 1000L) + " 秒后再尝试打开npc。";
        return 文本;
    }
    
    public static String checkLastThrtime() {
        if (getThrStartState()) {
            final String 文本 = "[蘑菇PPG活动] ：蘑插辣已经到达!";
            return 文本;
        }
        if (System.currentTimeMillis() - Mushplotact.ThrTimeState >= (long)(1000 * Mushplotact.ThrTimeCd)) {
            final String 文本 = "[蘑菇PPG活动] ：蘑插辣已经到达!";
            return 文本;
        }
        final String 文本 = "[蘑菇PPG活动] : 勇士你所剩时间已经不多了, \r\n" + ((long)Mushplotact.ThrTimeCd - (System.currentTimeMillis() - Mushplotact.ThrTimeState) / 1000L) + " 秒后蘑插辣就要来临。";
        return 文本;
    }
    
    public static String checkLastFoutime() {
        if (System.currentTimeMillis() - Mushplotact.FouTimeState >= (long)(1000 * Mushplotact.FouTimeCd)) {
            final String 文本 = "[蘑菇PPG活动] ：活动已经开始了";
            if (!getFouStartState()) {
                setFouStartState(true);
            }
            return 文本;
        }
        final String 文本 = "[蘑菇PPG活动] : 请你等待 " + ((long)Mushplotact.FouTimeCd - (System.currentTimeMillis() - Mushplotact.FouTimeState) / 1000L) + " 秒，毒蘑菇的大军正在前进。";
        return 文本;
    }
    
    public static void 关闭蘑菇大军进程() {
        if (Mushplotact.蘑菇大军进程 != null) {
            Mushplotact.蘑菇大军进程.cancel(false);
            Mushplotact.蘑菇大军进程 = null;
        }
    }
    
    public static void 关闭蘑辣插进程() {
        if (Mushplotact.蘑辣插进程 != null) {
            Mushplotact.蘑辣插进程.cancel(false);
            Mushplotact.蘑辣插进程 = null;
        }
    }
    
    public static void 召唤蘑辣插() {
        if (Mushplotact.蘑辣插进程 == null) {
            Mushplotact.蘑辣插进程 = WorldTimer.getInstance().register((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - Mushplotact.ThrTimeState >= (long)(1000 * Mushplotact.ThrTimeCd)) {
                        if (!Mushplotact.getThrStartState()) {
                            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                                    mch.startMapEffect("【大型活动】:蘑辣插已经来到现场", 5121009);
                                }
                            }
                            final MapleMap map = ChannelServer.getInstance(1).getMapFactory().getMap(106021600);
                            final MapleMonster mob = MapleLifeFactory.getMonster(Mushplotact.蘑辣插代码);
                            final long newhp = Mushplotact.蘑辣插血量;
                            final OverrideMonsterStats overrideStats = new OverrideMonsterStats(newhp, mob.getMobMaxMp(), Mushplotact.蘑辣插经验, false);
                            mob.setHp(newhp);
                            mob.setOverrideStats(overrideStats);
                            map.spawnMonsterOnGroundBelow(mob, new Point(-17, 87));
                            Mushplotact.setMushroomStage("蘑菇剧情阶段", 4);
                            Mushplotact.setThrStartState(true);
                            蘑辣插BOSS线程.开启进阶BOSS线程();
                            Mushplotact.关闭蘑辣插进程();
                        }
                    }
                    else {
                        for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                            for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                                mch.startMapEffect("【大型活动】:距离蘑辣插到来只剩下:" + ((long)Mushplotact.ThrTimeCd - (System.currentTimeMillis() - Mushplotact.ThrTimeState) / 1000L) + "秒了", 5121009);
                            }
                        }
                    }
                }
            }, 60000L);
        }
    }
    
    public static void 召唤怪物大军() {
        if (Mushplotact.蘑菇大军进程 == null) {
            Mushplotact.蘑菇大军进程 = WorldTimer.getInstance().register((Runnable)new Runnable() {
                @Override
                public void run() {
                    final long Time = ((long)Mushplotact.SecTimeCd - (System.currentTimeMillis() - Mushplotact.SecTimeState) / 1000L <= 0L) ? 0L : ((long)Mushplotact.SecTimeCd - (System.currentTimeMillis() - Mushplotact.SecTimeState) / 1000L);
                    final int a = 1;
                    if (Time == 0L && !Mushplotact.getSecStartState()) {
                        Mushplotact.setSecStartState(true);
                    }
                    if (!Mushplotact.getSecStartState()) {
                        for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                            for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                                mch.startMapEffect("【大型活动】:第二阶段 蘑菇大军迎亲大队!即将在" + Time + "秒后到达", 5121009);
                            }
                        }
                    }
                    else if (Mushplotact.Mobspawnnumb1 < Mushplotact.总波数) {
                        if (Mushplotact.怪物攻城进攻bgm次数) {
                            Mushplotact.怪物攻城进攻bgm次数 = false;
                            ChannelServer.getInstance(1).getMapFactory().getMap(106021600).broadcastMessage(MaplePacketCreator.musicChange("Bgm09/TimeAttack"));
                        }
                        for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                            for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                                mch.startMapEffect("【大型活动】:第" + (Mushplotact.Mobspawnnumb1 + 1) + "阶段敌军已经到达现场,请碾碎他们!", 5121009);
                            }
                        }
                        final MapleMap map = ChannelServer.getInstance(1).getMapFactory().getMap(106021600);
                        for (int i = 0; i < Mushplotact.怪物个数; ++i) {
                            final MapleMonster mob = MapleLifeFactory.getMonster(Integer.parseInt(Mushplotact.怪物代码[Mushplotact.Mobspawnnumb1]));
                            final long newhp = Long.parseLong(Mushplotact.怪物代码血量[Mushplotact.Mobspawnnumb1]);
                            final int newexp = Integer.parseInt(Mushplotact.怪物代码经验[Mushplotact.Mobspawnnumb1]);
                            final OverrideMonsterStats overrideStats = new OverrideMonsterStats(newhp, mob.getMobMaxMp(), newexp, false);
                            mob.setHp(newhp);
                            mob.setOverrideStats(overrideStats);
                            map.spawnMonsterOnGroundBelow(mob, new Point(-17, 87));
                        }
                        Mushplotact.Mobspawnnumb1++;
                    }
                    else {
                        Mushplotact.setMushroomStage("蘑菇剧情阶段", 3);
                        Mushplotact.setEggwhere();
                        Mushplotact.ThrTimeState = System.currentTimeMillis();
                        Mushplotact.关闭蘑菇大军进程();
                        Mushplotact.召唤蘑辣插();
                    }
                }
            }, 60000L);
        }
    }
    
    public static void setEggwhere() {
        final int[][] 蘑菇金蛋总地图 = { { 100000001, -11, 245 }, { 120000301, 41, 377 }, { 103000004, -24, 95 }, { 101000003, -199, -252 }, { 102000000, 1974, -536 }, { 104010001, 258, 155 }, { 101010102, -912, 119 }, { 101030112, 43, -1074 }, { 102020100, -1090, 2805 } };
        final String[][] 蘑菇金蛋地图提示 = { { "在一个蘑菇房子里", "病恹恹的妹子", "金银岛" }, { "有很多的海豚在玩耍", "哇,外面漏水了", "金银岛" }, { "有五个风扇", "神经病", "金银岛" }, { "有很多的书", "白胡子老头", "金银岛" }, { "看,是飞机", "岩石峭壁", "金银岛" }, { "猪的栖息地", "偶尔混进来几只钢铁猪", "隐藏地图" }, { "都是树", "上层是个无止境的悬崖,据说从这里跳下去可以到林中", "野外地图" }, { "骷髅兵", "军营", "野外地图" }, { "木妖栖息地", "北部山丘", "野外地图" } };
        final int 随机数 = (int)Math.floor(Math.random() * (double)蘑菇金蛋总地图.length);
        Mushplotact.蘑菇金蛋地图 = 蘑菇金蛋总地图[随机数][0];
        Mushplotact.蘑菇金蛋地图X坐标 = 蘑菇金蛋总地图[随机数][1];
        Mushplotact.蘑菇金蛋地图Y坐标 = 蘑菇金蛋总地图[随机数][2];
        Mushplotact.蘑菇金蛋提示a = 蘑菇金蛋地图提示[随机数][0];
        Mushplotact.蘑菇金蛋提示b = 蘑菇金蛋地图提示[随机数][1];
        Mushplotact.蘑菇金蛋提示c = 蘑菇金蛋地图提示[随机数][2];
        final MapleMap map = ChannelServer.getInstance(1).getMapFactory().getMap(Mushplotact.蘑菇金蛋地图);
        final MapleMonster mob = MapleLifeFactory.getMonster(Mushplotact.蘑菇金蛋代码);
        final long newhp = Mushplotact.蘑菇金蛋血量;
        final OverrideMonsterStats overrideStats = new OverrideMonsterStats(newhp, mob.getMobMaxMp(), Mushplotact.蘑菇金蛋经验, false);
        mob.setHp(newhp);
        mob.setOverrideStats(overrideStats);
        map.spawnMonsterOnGroundBelow(mob, new Point(Mushplotact.蘑菇金蛋地图X坐标, Mushplotact.蘑菇金蛋地图Y坐标));
    }
    
    static {
        蘑菇大军进程 = null;
        蘑辣插进程 = null;
        StartState = false;
        OneStartState = false;
        SecStartState = false;
        ThrStartState = false;
        FouStartState = false;
        OneTimeState = 0L;
        SecTimeState = 0L;
        ThrTimeState = 0L;
        FouTimeState = 0L;
        Mobspawnnumb = 0;
        Mobspawnnumb1 = 0;
        Mobspawnrun = false;
        OneTimeCd = Integer.parseInt(ServerProperties.getProperty("Lzj.蘑菇第一阶段开启时间"));
        SecTimeCd = Integer.parseInt(ServerProperties.getProperty("Lzj.蘑菇第二阶段开启时间"));
        ThrTimeCd = Integer.parseInt(ServerProperties.getProperty("Lzj.蘑菇第三阶段开启时间"));
        FouTimeCd = Integer.parseInt(ServerProperties.getProperty("Lzj.蘑菇第四阶段开启时间"));
        怪物代码 = ServerProperties.getProperty("Lzj.蘑菇怪物代码").split(",");
        怪物代码血量 = ServerProperties.getProperty("Lzj.蘑菇代码血量").split(",");
        怪物代码经验 = ServerProperties.getProperty("Lzj.蘑菇代码经验").split(",");
        总波数 = Integer.parseInt(ServerProperties.getProperty("Lzj.蘑菇大军次数"));
        怪物个数 = Integer.parseInt(ServerProperties.getProperty("Lzj.蘑菇大军个数"));
        怪物攻城入场bgm次数 = true;
        怪物攻城进攻bgm次数 = true;
        蘑菇金蛋代码 = Integer.parseInt(ServerProperties.getProperty("Lzj.蘑菇金蛋代码"));
        蘑菇金蛋血量 = Long.parseLong(ServerProperties.getProperty("Lzj.蘑菇金蛋代码"));
        蘑菇金蛋经验 = Integer.parseInt(ServerProperties.getProperty("Lzj.蘑菇金蛋经验"));
        蘑辣插代码 = Integer.parseInt(ServerProperties.getProperty("Lzj.蘑辣插代码"));
        蘑辣插血量 = Long.parseLong(ServerProperties.getProperty("Lzj.蘑辣插血量"));
        蘑辣插经验 = Integer.parseInt(ServerProperties.getProperty("Lzj.蘑辣插经验"));
        蘑菇金蛋 = 1;
        蘑菇金蛋题库 = 0;
        蘑菇金蛋地图 = 0;
        蘑菇金蛋地图X坐标 = 0;
        蘑菇金蛋地图Y坐标 = 0;
        蘑菇金蛋提示a = null;
        蘑菇金蛋提示b = null;
        蘑菇金蛋提示c = null;
        蘑辣插的魔力源泉 = 1;
    }
}
