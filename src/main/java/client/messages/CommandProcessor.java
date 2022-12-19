package client.messages;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import client.messages.commands.CommandExecute;
import java.lang.reflect.Modifier;
import client.messages.commands.GodCommand;
import client.messages.commands.AdminCommand;
import client.messages.commands.GMCommand;
import client.messages.commands.InternCommand;
import client.messages.commands.SkilledCommand;
import client.messages.commands.PracticerCommand;
import client.messages.commands.PlayerCommand;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import tools.FilePrinter;
import database.DBConPool;
import client.MapleCharacter;
import handling.world.World.Broadcast;
import tools.MaplePacketCreator;
import constants.PiPiConfig;
import java.util.LinkedList;
import tools.FileoutputUtil;
import constants.ServerConstants.PlayerGMRank;
import constants.ServerConstants.CommandType;
import java.util.Iterator;
import client.MapleClient;
import java.util.List;
import java.util.ArrayList;
import client.messages.commands.CommandObject;
import java.util.HashMap;

public class CommandProcessor{
    private static final HashMap<String, CommandObject> commands = new HashMap();
    private static final HashMap<Integer, ArrayList<String>> NormalCommandList = new HashMap();
    private static final HashMap<Integer, ArrayList<String>> VipCommandList = new HashMap();
    private static final List<String> showcommands = new LinkedList();
    
    public static void dropHelp(final MapleClient c, final int type) {
        final StringBuilder sb = new StringBuilder("指令列表:\r\n ");
        HashMap<Integer, ArrayList<String>> commandList = (HashMap<Integer, ArrayList<String>>)new HashMap();
        int check = 0;
        if (type == 0) {
            commandList = CommandProcessor.NormalCommandList;
            check = c.getPlayer().getGMLevel();
        }
        for (int i = 0; i <= check; ++i) {
            if (commandList.containsKey(Integer.valueOf(i))) {
                sb.append((type == 1) ? "VIP" : "").append("权限等级： ").append(i).append("\r\n");
                for (final String s : commandList.get(Integer.valueOf(i))) {
                    final CommandObject co = (CommandObject)CommandProcessor.commands.get(s);
                    sb.append(co.getMessage());
                    sb.append(" \r\n");
                }
            }
        }
        c.getPlayer().dropNPC(sb.toString());
    }
    
    private static void sendDisplayMessage(final MapleClient c, final String msg, final CommandType type) {
        if (c.getPlayer() == null) {
            return;
        }
        switch (type) {
            case NORMAL: {
                c.getPlayer().dropMessage(6, msg);
                break;
            }
            case TRADE: {
                c.getPlayer().dropMessage(-2, "错误 : " + msg);
                break;
            }
        }
    }
    
    public static boolean processCommand(final MapleClient c, final String line, final CommandType type) {
        if (c != null) {
            final char commandPrefix = line.charAt(0);
            for (final PlayerGMRank prefix : PlayerGMRank.values()) {
                if (line.startsWith(String.valueOf(prefix.getCommandPrefix() + prefix.getCommandPrefix()))) {
                    return false;
                }
            }
            if (commandPrefix == PlayerGMRank.普通玩家.getCommandPrefix()) {
                final String[] splitted = line.split(" ");
                splitted[0] = splitted[0].toLowerCase();
                final CommandObject co = (CommandObject)CommandProcessor.commands.get(splitted[0]);
                if (co == null || co.getType() != type) {
                    sendDisplayMessage(c, "沒有这个指令,可以使用 @help 来查看指令.", type);
                    return true;
                }
                try {
                    final boolean ret = co.execute(c, splitted);
                    if (!ret) {
                        c.getPlayer().dropMessage("指令错误，用法： " + co.getMessage());
                    }
                }
                catch (Exception e) {
                    sendDisplayMessage(c, "有错误.", type);
                    if (c.getPlayer().isGM()) {
                        sendDisplayMessage(c, "错误: " + e, type);
                    }
                    FileoutputUtil.outputFileError("logs\\Except\\Log_Command_Except.txt", (Throwable)e);
                    FileoutputUtil.logToFile("logs\\Except\\Log_Command_Except.txt", FileoutputUtil.NowTime() + c.getPlayer().getName() + "(" + c.getPlayer().getId() + ")使用了指令 " + line + " ---在地图「" + c.getPlayer().getMapId() + "」频道：" + c.getChannel() + " \r\n");
                }
                return true;
            }
            else if (c.getPlayer().getGMLevel() > PlayerGMRank.普通玩家.getLevel()) {
                final String[] splitted = line.split(" ");
                splitted[0] = splitted[0].toLowerCase();
                final List<String> show = (List<String>)new LinkedList();
                for (final String com : CommandProcessor.showcommands) {
                    if (com.contains((CharSequence)splitted[0])) {
                        show.add(com);
                    }
                }
                if (show.isEmpty()) {
                    final StringBuilder sb = new StringBuilder();
                    final int iplength = splitted[0].length();
                    for (final String com2 : CommandProcessor.showcommands) {
                        final int sclength = com2.length();
                        final String[] next = new String[sclength];
                        for (int i = 0; i < next.length; ++i) {
                            next[i] = "false";
                        }
                        if (iplength == sclength) {
                            for (int i = 0; i < sclength; ++i) {
                                final String st = com2.substring(i, i + 1);
                                for (int r = 0; r < iplength; ++r) {
                                    final String it = splitted[0].substring(r, r + 1);
                                    if (st.equals(it)) {
                                        next[i] = "true";
                                    }
                                }
                            }
                            boolean last = true;
                            for (int j = 0; j < next.length; ++j) {
                                if ("false".equals(next[j])) {
                                    last = false;
                                }
                            }
                            if (!last || !show.isEmpty()) {
                                continue;
                            }
                            show.add(com2);
                        }
                    }
                }
                if (show.size() == 1 && !splitted[0].equals(show.get(0))) {
                    sendDisplayMessage(c, "自动識別关联指令[" + (String)show.get(0) + "].", type);
                    splitted[0] = (String)show.get(0);
                }
                if (line.charAt(0) == '!') {
                    final CommandObject co2 = (CommandObject)CommandProcessor.commands.get(splitted[0]);
                    if (co2 == null || co2.getType() != type) {
                        if (splitted[0].equals(line.charAt(0) + "help")) {
                            dropHelp(c, 0);
                            return true;
                        }
                        sendDisplayMessage(c, "沒有这个指令.", type);
                        return true;
                    }
                    else {
                        boolean CanUseCommand = false;
                        if (c.getPlayer().getGMLevel() >= co2.getReqGMLevel()) {
                            CanUseCommand = true;
                        }
                        if (!CanUseCommand) {
                            sendDisplayMessage(c, "你沒有权限可以使用指令.", type);
                            return true;
                        }
                        if (PiPiConfig.getCommandLock() && !c.getPlayer().isGod()) {
                            sendDisplayMessage(c, "目前无法使用指令.", type);
                            return true;
                        }
                        if (c.getPlayer() != null) {
                            boolean ret2 = false;
                            try {
                                ret2 = co2.execute(c, splitted);
                                if (ret2) {
                                    logGMCommandToDB(c.getPlayer(), line);
                                    FileoutputUtil.logToFile("logs/Data/管理员命令.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用管理员命令:" + line);
                                    ShowMsg(c, line, type);
                                }
                                else {
                                    c.getPlayer().dropMessage("指令错误，用法： " + co2.getMessage());
                                }
                            }
                            catch (Exception e2) {
                                FileoutputUtil.outputFileError("logs\\Except\\Log_Command_Except.txt", (Throwable)e2);
                                String output = FileoutputUtil.NowTime();
                                if (c != null && c.getPlayer() != null) {
                                    output = output + c.getPlayer().getName() + "(" + c.getPlayer().getId() + ")使用了指令 " + line + " ---在地图「" + c.getPlayer().getMapId() + "」频道：" + c.getChannel();
                                }
                                FileoutputUtil.logToFile("logs\\Except\\Log_Command_Except.txt", output + " \r\n");
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private static void ShowMsg(final MapleClient c, final String line, final CommandType type) {
        if (c.getPlayer() != null) {
            if (!c.getPlayer().isGod() && !line.toLowerCase().startsWith("!cngm")) {
                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM密语] " + c.getPlayer().getName() + "(" + c.getPlayer().getId() + ")使用了指令 " + line + " ---在地图「" + c.getPlayer().getMapId() + "」频道：" + c.getChannel()));
            }
            if (c.getPlayer().getGMLevel() == 5) {
                System.out.println("＜超级管理员＞ " + c.getPlayer().getName() + " 使用了指令: " + line);
            }
            else if (c.getPlayer().getGMLevel() == 4) {
                System.out.println("＜领导者＞ " + c.getPlayer().getName() + " 使用了指令: " + line);
            }
            else if (c.getPlayer().getGMLevel() == 3) {
                System.out.println("＜巡逻者＞ " + c.getPlayer().getName() + " 使用了指令: " + line);
            }
            else if (c.getPlayer().getGMLevel() == 2) {
                System.out.println("＜老实习生＞ " + c.getPlayer().getName() + " 使用了指令: " + line);
            }
            else if (c.getPlayer().getGMLevel() == 1) {
                System.out.println("＜新实习生＞ " + c.getPlayer().getName() + " 使用了指令: " + line);
            }
            else if (c.getPlayer().getGMLevel() != 100) {
                sendDisplayMessage(c, "你沒有权限可以使用指令.", type);
            }
        }
    }
    
    private static void logGMCommandToDB(final MapleCharacter player, final String command) {
        if (player == null) {
            return;
        }
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("INSERT INTO gmlog (cid, command, mapid) VALUES (?, ?, ?)");
            ps.setInt(1, player.getId());
            ps.setString(2, command);
            ps.setInt(3, player.getMap().getId());
            ps.executeUpdate();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            FilePrinter.printError("CommandProccessor.txt", (Throwable)ex, "logGMCommandToDB");
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex);
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
                FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
            }
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e2) {
                FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e2);
            }
        }
    }
    
    private static void DoNormalCommand() {
        final Class[] array;
        final Class<?>[] CommandFiles = (Class<?>[])(array = new Class[] { PlayerCommand.class, PracticerCommand.class, SkilledCommand.class, InternCommand.class, GMCommand.class, AdminCommand.class, GodCommand.class });
        for (final Class<?> clasz : array) {
            try {
                final PlayerGMRank rankNeeded = (PlayerGMRank)clasz.getMethod("getPlayerLevelRequired", new Class[0]).invoke(null, (Object[])null);
                final Class<?>[] commandClasses = clasz.getDeclaredClasses();
                final ArrayList<String> cL = (ArrayList<String>)new ArrayList();
                for (final Class<?> c : commandClasses) {
                    try {
                        if (!Modifier.isAbstract(c.getModifiers()) && !c.isSynthetic()) {
                            final Object o = c.newInstance();
                            boolean enabled;
                            try {
                                enabled = c.getDeclaredField("enabled").getBoolean(c.getDeclaredField("enabled"));
                            }
                            catch (NoSuchFieldException ex3) {
                                enabled = true;
                            }
                            if (o instanceof CommandExecute && enabled) {
                                cL.add(rankNeeded.getCommandPrefix() + c.getSimpleName().toLowerCase());
                                CommandProcessor.commands.put(rankNeeded.getCommandPrefix() + c.getSimpleName().toLowerCase(), new CommandObject(rankNeeded.getCommandPrefix() + c.getSimpleName().toLowerCase(), (CommandExecute)o, rankNeeded.getLevel()));
                                CommandProcessor.showcommands.add(rankNeeded.getCommandPrefix() + c.getSimpleName().toLowerCase());
                            }
                        }
                    }
                    catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException ex6) {
                        FilePrinter.printError("CommandProccessor.txt", (Throwable)ex6);
                    }
                }
                Collections.sort((List<String>)cL);
                CommandProcessor.NormalCommandList.put(Integer.valueOf(rankNeeded.getLevel()), cL);
            }
            catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex7) {
                FilePrinter.printError("CommandProccessor.txt", (Throwable)ex7);
            }
        }
    }
    
    static {
        DoNormalCommand();
    }
}
