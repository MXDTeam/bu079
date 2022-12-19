package constants;

import LzjSetting.Game;
import gui.Start;

public class WorldConstants
{
    public static Option WORLD;
    public static boolean ADMIN_ONLY;
    public static boolean JZSD;
    public static boolean WUYANCHI;
    public static boolean LieDetector;
    public static boolean DropItem;
    public static int USER_LIMIT;
    public static int MAX_CHAR_VIEW;
    public static boolean GMITEMS;
    public static boolean CS_ENABLE;
    public static int EXP_RATE;
    public static int MESO_RATE;
    public static int DROP_RATE;
    public static int maxLevel;
    public static int kocmaxlevel;
    public static boolean mxj;
    public static boolean qst;
    public static boolean zs;
    public static byte FLAG;
    public static int CHANNEL_COUNT;
    public static String WORLD_TIP;
    public static String SCROLL_MESSAGE;
    public static boolean AVAILABLE;
    public static final int gmserver = -1;
    public static final byte recommended = -1;
    public static final String recommendedmsg;
    
    public static Option[] values() {
        return ServerConstants.TESPIA ? TespiaWorldOption.values() : WorldOption.values();
    }
    
    public static Option valueOf(final String name) {
        return ServerConstants.TESPIA ? TespiaWorldOption.valueOf(name) : WorldOption.valueOf(name);
    }
    
    public static Option getById(final int g) {
        for (final Option e : values()) {
            if (e.getWorld() == g) {
                return e;
            }
        }
        return null;
    }
    
    public static boolean isExists(final int id) {
        return getById(id) != null;
    }
    
    public static String getNameById(final int serverid) {
        if (getById(serverid) == null) {
            System.err.println("World doesn't exists exception. ID: " + serverid);
            return "";
        }
        return getById(serverid).name();
    }
    
    public static void loadSetting() {
        WorldConstants.ADMIN_ONLY = (((Integer)Start.ConfigValuesMap.get("管理员独占登录")).intValue() > 0);
        WorldConstants.FLAG = Byte.parseByte(String.valueOf(Start.ConfigValuesMap.get("火热程度")));
        WorldConstants.EXP_RATE = Game.经验倍率;
        WorldConstants.MESO_RATE = Game.金币爆率;
        WorldConstants.DROP_RATE = Game.物品爆率;
        WorldConstants.WORLD_TIP = Game.进入游戏小窗口气泡;
        WorldConstants.SCROLL_MESSAGE = Game.黄色滚动广告;
        WorldConstants.CHANNEL_COUNT = Game.频道个数;
        WorldConstants.USER_LIMIT = ((Integer)Start.ConfigValuesMap.get("频道人数")).intValue();
        WorldConstants.MAX_CHAR_VIEW = ((Integer)Start.ConfigValuesMap.get("频道热度")).intValue();
        WorldConstants.GMITEMS = (((Integer)Start.ConfigValuesMap.get("允许玩家使用管理员道具")).intValue() > 0);
        WorldConstants.CS_ENABLE = (((Integer)Start.ConfigValuesMap.get("允许玩家进入商城")).intValue() > 0);
        WorldConstants.maxLevel = ((Integer)Start.ConfigValuesMap.get("角色最大等级")).intValue();
        WorldConstants.kocmaxlevel = ((Integer)Start.ConfigValuesMap.get("角色最大等级")).intValue();
        WorldConstants.mxj = (((Integer)Start.ConfigValuesMap.get("允许创建冒险家")).intValue() > 0);
        WorldConstants.qst = (((Integer)Start.ConfigValuesMap.get("允许创建骑士团")).intValue() > 0);
        WorldConstants.zs = (((Integer)Start.ConfigValuesMap.get("允许创建战神")).intValue() > 0);
    }
    
    static {
        WORLD = WorldOption.雪吉拉;
        ADMIN_ONLY = true;
        JZSD = false;
        WUYANCHI = true;
        LieDetector = false;
        DropItem = true;
        USER_LIMIT = 300;
        MAX_CHAR_VIEW = 20;
        GMITEMS = false;
        CS_ENABLE = true;
        EXP_RATE = 1;
        MESO_RATE = 1;
        DROP_RATE = 1;
        maxLevel = 255;
        kocmaxlevel = 255;
        mxj = true;
        qst = false;
        zs = false;
        FLAG = 3;
        CHANNEL_COUNT = 2;
        WORLD_TIP = "请享受冒险之旅吧!";
        SCROLL_MESSAGE = "";
        AVAILABLE = true;
        recommendedmsg = "";
        loadSetting();
    }
    
    public enum WorldOption implements Option
    {
        泰勒熊(16), 
        神兽(15), 
        皮卡秋(14), 
        鯨鱼号(13), 
        电击象(12), 
        海努斯(11), 
        巴洛古(10), 
        蝴蝶精(9), 
        火独眼兽(8), 
        木妖(7), 
        三眼章鱼(6), 
        绿水灵(5), 
        蓝宝(4), 
        缎带肥肥(3), 
        星光精灵(120), 
        菇菇宝貝(1), 
        雪吉拉(120);
        
        private final int world;
        
        private WorldOption(final int world) {
            this.world = world;
        }
        
        @Override
        public int getWorld() {
            return this.world;
        }
    }
    
    public enum TespiaWorldOption implements Option
    {
        测试机("t0");
        
        private final int world;
        private final String worldName;
        
        private TespiaWorldOption(final String world) {
            this.world = Integer.parseInt(world.replaceAll("t", ""));
            this.worldName = world;
        }
        
        @Override
        public int getWorld() {
            return this.world;
        }
    }
    
    public interface Option
    {
        int getWorld();
        
        String name();
    }
}
