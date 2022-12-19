package handling.login;

import java.util.Iterator;
import java.util.Map;
import server.Timer.PingTimer;
import constants.WorldConstants;
import handling.world.World;
import java.util.Map.Entry;
import handling.channel.ChannelServer;
import tools.packet.LoginPacket;
import tools.MaplePacketCreator;
import gui.Start;
import client.MapleClient;

public class LoginWorker
{
    private static long lastUpdate = 0L;
    
    public static void registerClient(final MapleClient c) {
        if (((Integer)Start.ConfigValuesMap.get("玩家登录")).intValue() < 1 && !c.isGm()) {
            c.sendPacket(MaplePacketCreator.serverNotice(1, "服务器目前正在维修中.\r\n目前管理员正在测试物品.\r\n请稍后等待维修。"));
            c.sendPacket(LoginPacket.getLoginFailed(1));
            return;
        }
        if (((Integer)Start.ConfigValuesMap.get("管理员独占登录")).intValue() > 0 && !c.isGm()) {
            c.sendPacket(MaplePacketCreator.serverNotice(1, "服务器目前正在维修中.\r\n目前管理员正在测试物品.\r\n请稍后等待维修。"));
            c.sendPacket(LoginPacket.getLoginFailed(1));
            return;
        }
        if (!c.isGm() && (c.hasBannedMac() || c.hasBannedIP())) {
            c.sendPacket(LoginPacket.getLoginFailed(3));
            return;
        }
        if (System.currentTimeMillis() - LoginWorker.lastUpdate > 600000L) {
            LoginWorker.lastUpdate = System.currentTimeMillis();
            final Map<Integer, Integer> load = ChannelServer.getChannelLoad();
            int usersOn = 0;
            if (load == null || load.size() <= 0) {
                LoginWorker.lastUpdate = 0L;
                c.sendPacket(LoginPacket.getLoginFailed(7));
                return;
            }
            final double loadFactor = 1200.0 / ((double)((Integer)Start.ConfigValuesMap.get("频道人数")).intValue() / (double)load.size());
            for (final Entry<Integer, Integer> entry : load.entrySet()) {
                usersOn += ((Integer)entry.getValue()).intValue();
                load.put(entry.getKey(), Integer.valueOf(Math.min(1200, (int)((double)((Integer)entry.getValue()).intValue() * loadFactor))));
            }
            LoginServer.setLoad(load, usersOn);
            LoginWorker.lastUpdate = System.currentTimeMillis();
        }
        if (c.finishLogin() == 0) {
            if (c.getSecondPassword() == null) {
                c.sendPacket(LoginPacket.getGenderNeeded(c));
            }
            else {
                World.clearChannelChangeDataByAccountId(c.getAccID());
                LoginServer.forceRemoveClient(c);
                ChannelServer.forceRemovePlayerByAccId(c, c.getAccID());
                LoginServer.getClientStorage().registerAccount(c);
                c.sendPacket(LoginPacket.getAuthSuccessRequest(c));
                c.getSession().writeAndFlush(LoginPacket.getServerList(WorldConstants.WORLD));
                c.sendPacket(LoginPacket.getEndOfServerList());
            }
            c.setIdleTask(PingTimer.getInstance().schedule((Runnable)new Runnable() {
                @Override
                public void run() {
                    c.getSession().close();
                }
            }, 6000000L));
        }
        else {
            c.sendPacket(LoginPacket.getLoginFailed(7));
        }
    }
}
