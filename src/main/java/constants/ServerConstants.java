package constants;

import server.ServerProperties;

public class ServerConstants
{
    public static boolean TESPIA;
    public static final boolean PollEnabled = false;
    public static final String Poll_Question = "Are you mudkiz?";
    public static final String[] Poll_Answers;
    public static final short MAPLE_VERSION = 79;
    public static final String MAPLE_PATCH = "1";
    public static MapleType MAPLE_TYPE;
    public static boolean Use_Fixed_IV;
    public static final int MIN_MTS = 110;
    public static final int MTS_BASE = 100;
    public static final int MTS_TAX = 10;
    public static final int MTS_MESO = 5000;
    public static final int CHANNEL_COUNT = 200;
    public static final String CashShop_Key = "a;!%dfb_=*-a123d9{P~";
    public static final String Login_Key = "pWv]xq:SPTCtk^LGnU9F";
    public static int[] hot_sell;
    public static boolean MobDropMPoint;
    public static int MobDropMPointRate;
    public static int MobDropMPointLimit;
    public static int MobDropMPointMin;
    public static int MopDropMPointMax;
    public static String SQL_PORT;
    public static String SQL_DATABASE;
    public static String SQL_USER;
    public static String SQL_PASSWORD;
    public static String SQL_IP;
    public static int SQL_INITIALSIZE;
    public static int SQL_MINIDLE;
    public static int SQL_MAXACTIVE;
    public static long SQL_MAXWAIT;
    public static long SQL_TIMEBETWEENEVICTIONRUNSMILLIS;
    public static long SQL_MINEVICTABLEIDLETIMEMILLIS;
    public static String SQL_VALIDATIONQUERY;
    public static boolean SQL_TESTWHILEIDLE;
    public static boolean SQL_TESTONBORROW;
    public static boolean SQL_TESTONRETURN;
    public static boolean SQL_POOLPREPAREDSTATEMENTS;
    public static int SQL_MAXPOOLPREPAREDSTATEMENTPERCONNECTIONSIZE;
    public static boolean SQL_USEUNFAIRLOCK;
    public static boolean SQL_REMOVEABANDONED;
    public static int SQL_REMOVEABANDONEDTIMEOUT;
    public static boolean SQL_LOGABANDONED;
    
    public static void loadSetting() {
        final String[] x = ServerProperties.getProperty("Lzj.cashshop.HotSell", "10000007, 10000008, 10000009, 10000010, 10000011").split(",");
        final int[] y = new int[x.length];
        for (int i = 0; i < x.length; ++i) {
            y[i] = Integer.parseInt(x[i].replace((CharSequence)" ", (CharSequence)""));
        }
        ServerConstants.hot_sell = y;
        ServerConstants.TESPIA = ServerProperties.getProperty("Lzj.tespia", ServerConstants.TESPIA);
        ServerConstants.Use_Fixed_IV = ServerProperties.getProperty("Lzj.crypt", ServerConstants.Use_Fixed_IV);
    }
    
    static {
        TESPIA = false;
        Poll_Answers = new String[] { "test1", "test2", "test3" };
        MAPLE_TYPE = MapleType.中国;
        Use_Fixed_IV = false;
        hot_sell = new int[] { 10000007, 10000008, 10000009, 10000010, 10000011 };
        MobDropMPoint = true;
        MobDropMPointRate = 30;
        MobDropMPointLimit = 2500;
        MobDropMPointMin = 1;
        MopDropMPointMax = 2;
        SQL_PORT = "3306";
        SQL_DATABASE = "v079";
        SQL_USER = "root";
        SQL_PASSWORD = "root";
        SQL_IP = "127.0.0.1";
        SQL_INITIALSIZE = 0;
        SQL_MINIDLE = 100;
        SQL_MAXACTIVE = 20000;
        SQL_MAXWAIT = 60000L;
        SQL_TIMEBETWEENEVICTIONRUNSMILLIS = 60000L;
        SQL_MINEVICTABLEIDLETIMEMILLIS = 60000L;
        SQL_VALIDATIONQUERY = "SELECT 'x'";
        SQL_TESTWHILEIDLE = true;
        SQL_TESTONBORROW = false;
        SQL_TESTONRETURN = false;
        SQL_POOLPREPAREDSTATEMENTS = false;
        SQL_MAXPOOLPREPAREDSTATEMENTPERCONNECTIONSIZE = 20;
        SQL_USEUNFAIRLOCK = true;
        SQL_REMOVEABANDONED = false;
        SQL_REMOVEABANDONEDTIMEOUT = 180;
        SQL_LOGABANDONED = false;
        loadSetting();
    }
    
    public enum PlayerGMRank
    {
        普通玩家(0), 
        新实习生(1), 
        老实习生(2), 
        巡逻者(3), 
        领导者(4), 
        超级管理员(5), 
        神(100);
        
        private final char commandPrefix;
        private final int level;
        
        private PlayerGMRank(final int level) {
            this.commandPrefix = ((level > 0) ? '!' : '@');
            this.level = level;
        }
        
        public char getCommandPrefix() {
            return this.commandPrefix;
        }
        
        public int getLevel() {
            return this.level;
        }
    }
    
    public enum CommandType
    {
        NORMAL(0), 
        TRADE(1);
        
        private final int level;
        
        private CommandType(final int level) {
            this.level = level;
        }
        
        public int getType() {
            return this.level;
        }
    }
    
    public enum MapleType
    {
        UNKNOWN(-1, "UTF-8"), 
        韩国(1, "EUC_KR"), 
        韩国_TEST(1, "EUC_KR"), 
        日本(3, "Shift_JIS"), 
        中国(4, "GBK"), 
        TESPIA(5, "UTF-8"), 
        台湾(6, "BIG5"), 
        SEA(7, "UTF-8"), 
        GLOBAL(8, "UTF-8"), 
        BRAZIL(9, "UTF-8");
        
        byte type;
        final String ANSI;
        
        private MapleType(final int type, final String ANSI) {
            this.type = (byte)type;
            this.ANSI = ANSI;
        }
        
        public byte getType() {
            if (!ServerConstants.TESPIA) {
                return this.type;
            }
            if (this == MapleType.韩国 || this == MapleType.韩国_TEST) {
                return 2;
            }
            return 5;
        }
        
        public String getANSI() {
            return this.ANSI;
        }
        
        public void setType(final int type) {
            this.type = (byte)type;
        }
        
        public static MapleType getByType(final byte type) {
            for (final MapleType l : values()) {
                if (l.getType() == type) {
                    return l;
                }
            }
            return MapleType.UNKNOWN;
        }
    }
}
