package client.messages.commands;

import java.util.Iterator;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import client.LoginCrypto;
import database.DBConPool;
import handling.login.handler.AutoRegister;
import server.maps.MapleMap;
import client.MapleCharacter;
import handling.world.World.Broadcast;
import tools.MaplePacketCreator;
import tools.FileoutputUtil;
import handling.channel.ChannelServer;
import handling.world.World.Find;
import tools.StringUtil;
import client.MapleClient;
import constants.ServerConstants.PlayerGMRank;

public class PracticerCommand
{
    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.新实习生;
    }
    
    public static class Ban extends CommandExecute
    {
        protected boolean hellban;
        
        public Ban() {
            this.hellban = false;
        }
        
        private String getCommand() {
            return "Ban";
        }
        
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            final StringBuilder sb = new StringBuilder(c.getPlayer().getName());
            sb.append(" 封锁 ").append(splitted[1]).append(": ").append(StringUtil.joinStringFrom(splitted, 2));
            boolean offline = false;
            boolean ban = false;
            String name = "";
            String input = "null";
            try {
                name = splitted[1];
                input = splitted[2];
            }
            catch (Exception ex) {}
            final int ch = Find.findChannel(name);
            if (ch <= 0) {
                if (!c.getPlayer().OfflineBanByName(name, sb.toString())) {
                    c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] 封锁失败 " + splitted[1]);
                    return true;
                }
                c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] 成功离线封锁 " + splitted[1] + ".");
                ban = true;
                offline = true;
            }
            else {
                final MapleCharacter target = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
                if (target != null) {
                    if (c.getPlayer().getGMLevel() < target.getGMLevel()) {
                        c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] 无法封锁GMs...");
                        return true;
                    }
                    sb.append(" (IP: ").append(target.getClient().getSessionIPAddress()).append(")");
                    if (!target.ban(sb.toString(), c.getPlayer().hasGmLevel(5), false, this.hellban)) {
                        c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] 封锁失败.");
                        return true;
                    }
                    ban = true;
                    c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] 成功封锁 " + target.getName() + ".");
                    target.getClient().getSession().close();
                }
            }
            FileoutputUtil.logToFile("logs/Hack/指令封锁名单.txt", "\r\n " + FileoutputUtil.NowTime() + " " + c.getPlayer().getName() + " 封锁了 " + splitted[1] + " 原因: " + sb.toString() + " 是否离线封锁: " + offline);
            final String reason = "null".equals(input) ? "使用违法程式练功" : StringUtil.joinStringFrom(splitted, 2);
            Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + splitted[1] + " 因为" + reason + "而被管理员永久停权。"));
            final String msg = "[GM 密语] GM " + c.getPlayer().getName() + "  封锁了 " + splitted[1] + " 是否离线封锁 " + offline + " 原因：" + reason;
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, msg));
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!ban <玩家> <原因> - 封锁玩家").toString();
        }
    }
    
    public static class WarpHere extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (victim != null) {
                victim.changeMap(c.getPlayer().getMap(), c.getPlayer().getMap().findClosestSpawnpoint(c.getPlayer().getPosition()));
            }
            else {
                final int ch = Find.findChannel(splitted[1]);
                if (ch < 0) {
                    c.getPlayer().dropMessage(5, "找不到");
                }
                else {
                    victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(splitted[1]);
                    c.getPlayer().dropMessage(5, "正在把玩家传到这来");
                    victim.dropMessage(5, "正在传送到GM那边");
                    if (victim.getMapId() != c.getPlayer().getMapId()) {
                        final MapleMap mapp = victim.getClient().getChannelServer().getMapFactory().getMap(c.getPlayer().getMapId());
                        victim.changeMap(mapp, mapp.getPortal(0));
                    }
                    victim.changeChannel(c.getChannel());
                }
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!warphere 把玩家传送到这里").toString();
        }
    }
    
    public static class Warp extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            String input = "";
            try {
                input = splitted[1];
            }
            catch (Exception ex2) {}
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(input);
            if (victim != null) {
                if (splitted.length == 2) {
                    c.getPlayer().changeMap(victim.getMap(), victim.getMap().findClosestSpawnpoint(victim.getPosition()));
                }
                else {
                    MapleMap target = null;
                    try {
                        target = ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(Integer.parseInt(splitted[2]));
                    }
                    catch (Exception ex3) {}
                    if (target == null) {
                        c.getPlayer().dropMessage(6, "地图不存在");
                    }
                    else {
                        victim.changeMap(target, target.getPortal(0));
                    }
                }
            }
            else {
                final int ch = Find.findChannel(input);
                if (ch < 0) {
                    Integer map = null;
                    MapleMap target2 = null;
                    try {
                        map = Integer.valueOf(Integer.parseInt(input));
                        target2 = c.getChannelServer().getMapFactory().getMap(map.intValue());
                    }
                    catch (Exception ex) {
                        if (map == null || target2 == null) {
                            c.getPlayer().dropMessage(6, "地图不存在");
                            return true;
                        }
                    }
                    if (target2 == null) {
                        c.getPlayer().dropMessage(6, "地图不存在");
                    }
                    else {
                        c.getPlayer().changeMap(target2, target2.getPortal(0));
                    }
                }
                else {
                    victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(input);
                    if (victim != null) {
                        if (victim.getMapId() != c.getPlayer().getMapId()) {
                            final MapleMap mapp = c.getChannelServer().getMapFactory().getMap(victim.getMapId());
                            c.getPlayer().changeMap(mapp, mapp.getPortal(0));
                        }
                        c.getPlayer().dropMessage(6, "正在改变频道请等待");
                        c.getPlayer().changeChannel(ch);
                    }
                    else {
                        c.getPlayer().dropMessage(6, "角色不存在");
                    }
                }
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!warp [玩家名称] <地图ID> - 移动到某个地图或某个玩家所在的地方").toString();
        }
    }
    
    public static class 注册 extends register
    {
    }
    
    public static class register extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            String acc = null;
            String password = null;
            try {
                acc = splitted[1];
                password = splitted[2];
            }
            catch (Exception ex3) {}
            if (acc == null || password == null) {
                c.getPlayer().dropMessage("账号或密码异常");
                return false;
            }
            final boolean ACCexist = AutoRegister.getAccountExists(acc);
            if (ACCexist) {
                c.getPlayer().dropMessage("帐号已被使用");
                return false;
            }
            if (acc.length() >= 12) {
                c.getPlayer().dropMessage("密码长度过长");
                return false;
            }
            Connection con;
            try {
                con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            }
            catch (Exception ex) {
                System.out.println(ex);
                return false;
            }

            try (final PreparedStatement ps = con.prepareStatement("INSERT INTO accounts (name, password) VALUES (?, ?)")) {
                ps.setString(1, acc);
                ps.setString(2, LoginCrypto.hexSha1(password));
                ps.executeUpdate();
                ps.close();
            }
            catch (SQLException ex2) {
                System.out.println(ex2);
                return false;
            }
            c.getPlayer().dropMessage("[注册完成]账号: " + acc + " 密码: " + password);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!注册 [账号] <密码> - 注册账号").toString();
        }
    }
    
    public static class 线上玩家 extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            int total = 0;
            c.getPlayer().dropMessage(6, "---------------------------------------------------------------------------------------");
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                final int curConnected = cserv.getConnectedClients();
                c.getPlayer().dropMessage(6, "频道: " + cserv.getChannel() + " 在线人数: " + curConnected);
                total += curConnected;
                for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                    if (chr != null && c.getPlayer().getGMLevel() >= chr.getGMLevel()) {
                        final StringBuilder ret = new StringBuilder();
                        ret.append(StringUtil.getRightPaddedStr(chr.getName(), ' ', 13));
                        ret.append(" ID: ");
                        ret.append(chr.getId());
                        ret.append(" 等级: ");
                        ret.append(StringUtil.getRightPaddedStr(String.valueOf((int)chr.getLevel()), ' ', 3));
                        if (chr.getMap() != null) {
                            ret.append(" 地图: ");
                            ret.append(chr.getMapId());
                            ret.append(" - ");
                            ret.append(chr.getMap().getMapName());
                        }
                        c.getPlayer().dropMessage(6, ret.toString());
                    }
                }
            }
            c.getPlayer().dropMessage(6, "当前服务器总计在线: " + total);
            c.getPlayer().dropMessage(6, "---------------------------------------------------------------------------------------");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("!线上玩家 - 查看线上玩家").toString();
        }
    }
}
