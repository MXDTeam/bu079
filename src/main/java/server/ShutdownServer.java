package server;

import java.util.Set;
import java.util.Iterator;
import handling.cashshop.CashShopServer;
import handling.login.LoginServer;
import handling.world.World.Family;
import handling.world.World.Alliance;
import handling.world.World.Guild;
import client.MapleCharacter;
import handling.channel.ChannelServer;
import handling.world.World;
import tools.FileoutputUtil;

public class ShutdownServer implements Runnable, ShutdownServerMBean{
    private static final ShutdownServer instance = new ShutdownServer();
    public static boolean running = false;
    
    public static ShutdownServer getInstance() {
        return ShutdownServer.instance;
    }
    
    @Override
    public void run() {
        synchronized (this) {
            if (ShutdownServer.running) {
                return;
            }
            ShutdownServer.running = true;
        }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][========================================]");
        World.isShutDown = true;
        int ret = 0;
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            ret += cserv.closeAllMerchant();
        }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:雇佣信息保存成功,共计保存" + ret + "个商店");
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                chr.saveToDB(true, true);
            }
        }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:角色数据保存成功");
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.getPlayerStorage().断所有人(true);
        }
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.getPlayerStorage().断所有人(true);
        }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:断开所有人信息保存成功");
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            ret += cserv.closeAllMerchant();
        }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:共储存了 " + ret + " 个精灵商人");
        ret = 0;
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            ret += cserv.closeAllPlayerShop();
        }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:共储存了 " + ret + " 个个人执照商店");
        Guild.save();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:公会资料储存完毕");
        Alliance.save();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:联盟资料储存完毕");
        Family.save();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:家族资料储存完毕");
        server.Timer.EventTimer.getInstance().stop();
        server.Timer.WorldTimer.getInstance().stop();
        server.Timer.MapTimer.getInstance().stop();
        server.Timer.MobTimer.getInstance().stop();
        server.Timer.BuffTimer.getInstance().stop();
        server.Timer.CloneTimer.getInstance().stop();
        server.Timer.EtcTimer.getInstance().stop();
        server.Timer.PingTimer.getInstance().stop();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:线程关闭完成");
        final Set<Integer> channels = ChannelServer.getAllChannels();
        for (final Integer channel : channels) {
            try {
                final ChannelServer cs = ChannelServer.getInstance(channel.intValue());
                cs.saveAll();
                cs.setPrepareShutdown();
                cs.shutdown();
            }
            catch (Exception e) {
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:频道" + String.valueOf(channel) + " 关闭失败." + e);
            }
        }
        try {
            LoginServer.shutdown();
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:服务器关闭完成.");
        }
        catch (Exception e2) {
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:服务器关闭失败" + e2);
        }
        try {
            CashShopServer.shutdown();
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:购物商城服务器关闭完成.");
        }
        catch (Exception e2) {
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:购物商城服务器关闭失败" + e2);
        }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:服务端已经完全关闭了请点击右上角的xx按钮关闭.");
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][========================================]");
    }
    
    @Override
    public void shutdown() {
        this.run();
    }
}
