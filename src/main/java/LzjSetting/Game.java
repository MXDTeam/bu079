package LzjSetting;

import server.ServerProperties;

public class Game
{
    public static String 服务端名称;
    public static String 初始ip;
    public static short 服务端端口;
    public static short 商城端口;
    public static int 频道端口;
    public static int 频道个数;
    public static String 服务端名字;
    public static String 进入游戏小窗口气泡;
    public static String 黄色滚动广告;
    public static String 数据库名字;
    public static String 数据库IP;
    public static String 数据库端口;
    public static String 数据库用户名;
    public static String 数据库密码;
    public static int 经验倍率;
    public static int 金币爆率;
    public static int 物品爆率;
    public static Boolean 双倍频道开关;
    public static int 双倍频道;
    public static int 服务端启动中;
    
    public static boolean ismyboss(final int bossid){
        switch (bossid) {
            case 8800400:
            case 8220010:
            case 9600086:
            case 6160003:
            case 8220015:
            case 9601014:
            case 8240099:
            case 9600318:
            case 8880303:
            case 9601015:
            case 8880010:
            case 8220011:
           // case 9601015:
                return true;
            default:
                return false;
        }
    }
    static {
        服务端名称 = "梦缘工作室出品 - 服务端";
        初始ip = ServerProperties.getProperty("Lzj.IPAddress");
        服务端端口 = Short.parseShort(ServerProperties.getProperty("Lzj.LoginPort"));
        商城端口 = Short.parseShort(ServerProperties.getProperty("Lzj.CashPort"));
        频道端口 = Integer.parseInt(ServerProperties.getProperty("Lzj.ChannelPort"));
        频道个数 = Integer.parseInt(ServerProperties.getProperty("Lzj.Channel.count"));
        服务端名字 = ServerProperties.getProperty("Lzj.serverName", "");
        进入游戏小窗口气泡 = ServerProperties.getProperty("Lzj.eventMessage", "");
        黄色滚动广告 = ServerProperties.getProperty("Lzj.serverMessage", "");
        数据库名字 = ServerProperties.getProperty("Lzj.DBName");
        数据库IP = ServerProperties.getProperty("Lzj.SQLAddress");
        数据库端口 = ServerProperties.getProperty("Lzj.SQLPort");
        数据库用户名 = ServerProperties.getProperty("Lzj.SQLUser");
        数据库密码 = ServerProperties.getProperty("Lzj.SQLPassword");
        经验倍率 = Integer.parseInt(ServerProperties.getProperty("Lzj.expRate"));
        金币爆率 = Integer.parseInt(ServerProperties.getProperty("Lzj.mesoRate"));
        物品爆率 = Integer.parseInt(ServerProperties.getProperty("Lzj.dropRate"));
        双倍频道开关 = Boolean.valueOf(Boolean.parseBoolean(ServerProperties.getProperty("Lzj.DD")));
        双倍频道 = Integer.parseInt(ServerProperties.getProperty("Lzj.DDChannel"));
        服务端启动中 = 0;
    }
    
}
