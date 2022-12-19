package server;

import java.io.InputStream;
import java.io.IOException;
import tools.FileoutputUtil;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import tools.StringUtil;
import java.io.FileInputStream;
import java.util.Properties;

public class ServerProperties{
    private static final Properties props = new Properties();
    public static final String[] toload = new String[] { 
        "Load\\LzjMS.ini",
        "Load\\事件载入.ini",
        "Load\\坐骑椅子.ini",
        "Load\\捉鬼任务.ini",
        "Load\\经验倍数.ini",
        "Load\\转生属性.ini",
        "Load\\挂机功能.ini",
        "Load\\宠物属性.ini",
        "Load\\地图效果.ini",
        "Load\\mob\\黑龙线程.ini",
        "Load\\mob\\蘑辣插线程.ini",
        "Load\\mob\\捉鬼线程.ini",
        "Load\\mob\\怪物挑战所需人数.ini",
        "Load\\rpg\\蘑菇活动.ini",
        "Load\\mob\\扎昆线程.ini" };
    public static void loadProperties() {
        for (final String s : ServerProperties.toload) {
            try {
                final InputStream in = new FileInputStream(s);
                final BufferedReader bf = new BufferedReader((Reader)new InputStreamReader(in, StringUtil.codeString(s)));
                ServerProperties.props.load((Reader)bf);
                bf.close();
            }
            catch (IOException ex) {
                FileoutputUtil.logToFile(ex.getLocalizedMessage().split(".ini")[0] + ".ini", "\r\n");
                System.out.println("服务端缺少运行的配置文件,已经自动生成空白配置“" + ex.getLocalizedMessage().split(".ini")[0] + ".ini”文件，请重新启动服务端。");
                System.exit(0);
            }
        }
    }
    
    public static void setProperty(final String prop, final String newInf) {
        ServerProperties.props.setProperty(prop, newInf);
    }
    
    public static void setProperty(final String prop, final boolean newInf) {
        ServerProperties.props.setProperty(prop, String.valueOf(newInf));
    }
    
    public static void setProperty(final String prop, final byte newInf) {
        ServerProperties.props.setProperty(prop, String.valueOf((int)newInf));
    }
    
    public static void setProperty(final String prop, final short newInf) {
        ServerProperties.props.setProperty(prop, String.valueOf((int)newInf));
    }
    
    public static void setProperty(final String prop, final int newInf) {
        ServerProperties.props.setProperty(prop, String.valueOf(newInf));
    }
    
    public static void setProperty(final String prop, final long newInf) {
        ServerProperties.props.setProperty(prop, String.valueOf(newInf));
    }
    
    public static void removeProperty(final String prop) {
        ServerProperties.props.remove(prop);
    }
    
    public static String getProperty(final String s) {
        return ServerProperties.props.getProperty(s);
    }
    
    public static String getProperty(final String s, final String def) {
        return ServerProperties.props.getProperty(s, def);
    }
    
    public static boolean getProperty(final String s, final boolean def) {
        return getProperty(s, def ? "true" : "false").equalsIgnoreCase("true");
    }
    
    public static byte getProperty(final String s, final byte def) {
        final String property = ServerProperties.props.getProperty(s);
        if (property != null) {
            return Byte.parseByte(property);
        }
        return def;
    }
    
    public static short getProperty(final String s, final short def) {
        final String property = ServerProperties.props.getProperty(s);
        if (property != null) {
            return Short.parseShort(property);
        }
        return def;
    }
    
    public static int getProperty(final String s, final int def) {
        final String property = ServerProperties.props.getProperty(s);
        if (property != null) {
            return Integer.parseInt(property);
        }
        return def;
    }
    
    public static long getProperty(final String s, final long def) {
        final String property = ServerProperties.props.getProperty(s);
        if (property != null) {
            return Long.parseLong(property);
        }
        return def;
    }
    
    public static void clear() {
        ServerProperties.props.clear();
        loadProperties();
        clear1();
    }
    
    public static void clear1() {
    }
    
    static {
        loadProperties();
    }
}
