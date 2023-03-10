package constants;

import LzjSetting.Game;
import gui.Start;
import java.io.File;

public class ServerConfig
{
    public static int nub1;
    public static boolean pvp;
    public static int pvpch;
    public static boolean LOG_MRECHANT;
    public static boolean LOG_CSBUY;
    public static boolean LOG_DAMAGE;
    public static boolean LOG_CHAT;
    public static boolean LOG_MEGA;
    public static boolean LOG_PACKETS;
    public static boolean CHRLOG_PACKETS;
    public static boolean AUTO_REGISTER;
    public static boolean LOCALHOST;
    public static boolean Encoder;
    public static boolean TESPIA;
    public static boolean DISCOUNTED;
    public static String SERVER_NAME;
    public static String TOUDING;
    public static String[] IP;
    public static final byte[][] Gateway_IP;
    public static final byte[] Gateway_IP2;
    private static String EVENTS;
    public static boolean DEBUG_MODE;
    public static boolean NMGB;
    public static boolean PDCS;
    public static int RSGS;
    public static int ExpRate;
    public static int MesoRate;
    public static int DropRate;
    public static int CashRate;
    public static int TraitRate;
    public static int maxLevel;
    public static int kocmaxlevel;
    public static boolean mxj;
    public static boolean qst;
    public static boolean zs;
    
    public static boolean isPvPChannel(final int ch) {
        return ServerConfig.pvp && ch == ServerConfig.pvpch;
    }
    
    public static String[] getEvents(final boolean reLoad) {
        return getEventList(reLoad).split(",");
    }
    
    public static String getEventList(final boolean reLoad) {
        if (ServerConfig.EVENTS == null || reLoad) {
            final File root = new File("scripts/event");
            final File[] files = root.listFiles();
            ServerConfig.EVENTS = "";
            for (final File file : files) {
                if (!file.isDirectory()) {
                    final String[] fileName = file.getName().split("\\.");
                    if (fileName.length > 1 && "js".equals(fileName[fileName.length - 1])) {
                        for (int i = 0; i < fileName.length - 1; ++i) {
                            ServerConfig.EVENTS += fileName[i];
                        }
                        ServerConfig.EVENTS += ",";
                    }
                }
            }
        }
        return ServerConfig.EVENTS;
    }
    
    public static boolean isAutoRegister() {
        return ServerConfig.AUTO_REGISTER;
    }
    
    public static String getVipMedalName(final int lv) {
        String medal = "";
        if (ServerConfig.SERVER_NAME.equals("?????????")) {
            switch (lv) {
                case 1: {
                    medal = " <??????VIP>";
                    break;
                }
                case 2: {
                    medal = " <??????VIP>";
                    break;
                }
                case 3: {
                    medal = " <??????VIP>";
                    break;
                }
                case 4: {
                    medal = " <??????VIP>";
                    break;
                }
                case 5: {
                    medal = " <??????VIP>";
                    break;
                }
                default: {
                    medal = " <VIP" + medal + ">";
                    break;
                }
            }
        }
        else if (ServerConfig.SERVER_NAME.equals("?????????")) {
            switch (lv) {
                case 1: {
                    medal = "???";
                    break;
                }
                case 2: {
                    medal = "??????";
                    break;
                }
                case 3: {
                    medal = "?????????";
                    break;
                }
                case 4: {
                    medal = "????????????";
                    break;
                }
                case 5: {
                    medal = "???????????????";
                    break;
                }
                case 6: {
                    medal = "??????????????????";
                    break;
                }
                case 7: {
                    medal = "?????????????????????";
                    break;
                }
                case 8: {
                    medal = "????????????????????????";
                    break;
                }
                case 9: {
                    medal = "???????????????????????????";
                    break;
                }
                case 10: {
                    medal = "??????????????????????????????";
                    break;
                }
                case 11: {
                    medal = "?????????????????????";
                    break;
                }
                default: {
                    medal = "<VIP" + medal + ">";
                    break;
                }
            }
        }
        return medal;
    }
    
    public static void loadSetting() {
        ServerConfig.LOG_MRECHANT = (((Integer)Start.ConfigValuesMap.get("????????????????????????")).intValue() > 0);
        ServerConfig.LOG_MEGA = (((Integer)Start.ConfigValuesMap.get("????????????????????????")).intValue() > 0);
        ServerConfig.LOG_CSBUY = (((Integer)Start.ConfigValuesMap.get("????????????????????????")).intValue() > 0);
        ServerConfig.LOG_DAMAGE = (((Integer)Start.ConfigValuesMap.get("????????????????????????")).intValue() > 0);
        ServerConfig.LOG_CHAT = (((Integer)Start.ConfigValuesMap.get("????????????????????????")).intValue() > 0);
        ServerConfig.LOG_PACKETS = (((Integer)Start.ConfigValuesMap.get("??????????????????")).intValue() > 0);
        ServerConfig.AUTO_REGISTER = (((Integer)Start.ConfigValuesMap.get("????????????????????????")).intValue() > 0);
        ServerConfig.SERVER_NAME = Game.???????????????;
        ServerConfig.DEBUG_MODE = (((Integer)Start.ConfigValuesMap.get("????????????Debug??????")).intValue() > 0);
    }
    
    static {
        nub1 = 0;
        pvp = false;
        pvpch = 1;
        LOG_MRECHANT = true;
        LOG_CSBUY = true;
        LOG_DAMAGE = true;
        LOG_CHAT = true;
        LOG_MEGA = true;
        LOG_PACKETS = false;
        CHRLOG_PACKETS = false;
        AUTO_REGISTER = true;
        LOCALHOST = false;
        Encoder = false;
        TESPIA = false;
        DISCOUNTED = false;
        SERVER_NAME = "???????????????";
        TOUDING = "???????????????079,?????????!";
        IP = new String[] { "103.205.254.145"};
        Gateway_IP = new byte[][] { { (byte)103, (byte)205, (byte)254, (byte)145 }};
        Gateway_IP2 = new byte[] { (byte)103, (byte)205, (byte)254, (byte)145 };
//        IP = new String[] { "127.0.0.1"};
//        Gateway_IP = new byte[][] { { (byte)127, (byte)0, (byte)0, (byte)1 }};
//        Gateway_IP2 = new byte[] {(byte)127, (byte)0, (byte)0, (byte)1 };
        EVENTS = null;
        DEBUG_MODE = false;
        NMGB = true;
        PDCS = false;
        RSGS = 0;
        ExpRate = 2;
        MesoRate = 1;
        DropRate = 2;
        CashRate = 1;
        TraitRate = 1;
        maxLevel = 255;
        kocmaxlevel = 255;
        mxj = true;
        qst = false;
        zs = false;
        loadSetting();
    }
}
