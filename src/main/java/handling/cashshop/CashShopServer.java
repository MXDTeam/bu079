package handling.cashshop;

import server.ServerProperties;
import tools.FileoutputUtil;
import LzjSetting.Game;
import handling.channel.PlayerStorage;
import handling.mina.ServerConnection;
import java.net.InetSocketAddress;

public class CashShopServer
{
    private static String ip;
    private static InetSocketAddress InetSocketadd;
    private static int port = 15555;
    private static ServerConnection acceptor;
    private static PlayerStorage players;
    private static PlayerStorage playersMTS;
    private static boolean finishedShutdown = false;
    private static String 初始ip = ServerProperties.getProperty("Lzj.IPAddress");
    
    public static final void setup() {
        CashShopServer.port = Game.商城端口;
        CashShopServer.ip = CashShopServer.初始ip + ":" + CashShopServer.port;
        CashShopServer.players = new PlayerStorage(-10);
        CashShopServer.playersMTS = new PlayerStorage(-20);
        (CashShopServer.acceptor = new ServerConnection(CashShopServer.port, 0, -10)).run();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:购物商城   - 监听端口: " + CashShopServer.port);
    }
    
    public static final String getIP() {
        return CashShopServer.ip;
    }
    
    public static final PlayerStorage getPlayerStorage() {
        return CashShopServer.players;
    }
    
    public static final PlayerStorage getPlayerStorageMTS() {
        return CashShopServer.playersMTS;
    }
    
    public static final void shutdown() {
        if (CashShopServer.finishedShutdown) {
            return;
        }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:[购物商城] 准备关闭中");
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:[购物商城] 储存资料中");
        CashShopServer.players.disconnectAll();
        CashShopServer.playersMTS.disconnectAll();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:[购物商城] 解除綁定端口");
        CashShopServer.acceptor.close();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:[购物商城] 关闭完成");
    }
    
    public static boolean isShutdown() {
        return CashShopServer.finishedShutdown;
    }
}
