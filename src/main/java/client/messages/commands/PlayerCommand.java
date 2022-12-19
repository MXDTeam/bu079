package client.messages.commands;

import scripting.EventManager;
import handling.world.MaplePartyCharacter;
import client.inventory.MapleInventory;
import server.MapleInventoryManipulator;
import client.inventory.MapleInventoryType;
import tools.FileoutputUtil;
import handling.world.World.Broadcast;
import constants.PiPiConfig;
import tools.StringUtil;
import java.util.Iterator;
import server.life.MapleMonster;
import server.maps.MapleMapObject;
import java.util.Arrays;
import server.maps.MapleMapObjectType;
import client.MapleCharacter;
import gui.Start;
import tools.MaplePacketCreator;
import client.MapleStat;
import constants.GameConstants;
import scripting.NPCScriptManager;
import client.MapleClient;
import constants.ServerConstants.PlayerGMRank;

public class PlayerCommand
{
    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.普通玩家;
    }
    
    public static class 爆率 extends Mobdrop
    {
    }
    
    public static class 全局爆率 extends Mapdrop
    {
    }
    
    public static class 帮助 extends help
    {
        @Override
        public String getMessage() {
            return new StringBuilder().append("@帮助 - 帮助").toString();
        }
    }
    
    public static class help extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().start(c, 9010000, "玩家指令查询");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@help - 帮助").toString();
        }
    }
    
    public abstract static class OpenNPCCommand extends CommandExecute
    {
        protected int npc;
        private static final int[] npcs = new int[] { 9010017, 9000001, 9000058, 9330082, 9209002 };
        
        public OpenNPCCommand() {
            this.npc = -1;
        }
        
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (this.npc != 1 && c.getPlayer().getMapId() != 910000000) {
                for (final int i : GameConstants.blockedMaps) {
                    if (c.getPlayer().getMapId() == i) {
                        c.getPlayer().dropMessage(1, "你不能在这里使用指令.");
                        return true;
                    }
                }
                if (this.npc != 2 && c.getPlayer().getLevel() < 10) {
                    c.getPlayer().dropMessage(1, "你的等级必须是10等.");
                    return true;
                }
                if (c.getPlayer().getMap().getSquadByMap() != null || c.getPlayer().getEventInstance() != null || c.getPlayer().getMap().getEMByMap() != null || c.getPlayer().getMapId() >= 990000000) {
                    c.getPlayer().dropMessage(1, "你不能在这里使用指令.");
                    return true;
                }
                if ((c.getPlayer().getMapId() >= 680000210 && c.getPlayer().getMapId() <= 680000502) || (c.getPlayer().getMapId() / 1000 == 980000 && c.getPlayer().getMapId() != 980000000) || c.getPlayer().getMapId() / 100 == 1030008 || c.getPlayer().getMapId() / 100 == 922010 || c.getPlayer().getMapId() / 10 == 13003000) {
                    c.getPlayer().dropMessage(1, "你不能在这里使用指令.");
                    return true;
                }
            }
            NPCScriptManager.getInstance().start(c, OpenNPCCommand.npcs[this.npc]);
            return true;
        }
    }
    
    public static class 丟装 extends DropCash
    {
        @Override
        public String getMessage() {
            return new StringBuilder().append("@丟装 - 呼叫清除现金道具npc").toString();
        }
    }
    
    public static class DropCash extends OpenNPCCommand
    {
        public DropCash() {
            this.npc = 0;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@dropbash - 呼叫清除现金道具npc").toString();
        }
    }
    
    public static class event extends OpenNPCCommand
    {
        public event() {
            this.npc = 1;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@event - 呼叫活动npc").toString();
        }
    }
    
    public static class expfix extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setExp(0);
            c.getPlayer().updateSingleStat(MapleStat.EXP, c.getPlayer().getExp());
            c.getPlayer().dropMessage(5, "经验修复完成");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@expfix - 经验归零").toString();
        }
    }
    
    public static class TSmega extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setSmega();
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@TSmega - 开/关闭广播").toString();
        }
    }
    
    public static class Gashponmega extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setGashponmega();
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@Gashponmega - 开/关闭转蛋广播").toString();
        }
    }
    
    public static class 解卡 extends ea
    {
        @Override
        public String getMessage() {
            return new StringBuilder().append("@解卡 - 解卡").toString();
        }
    }
    
    public static class 查看 extends ea
    {
        @Override
        public String getMessage() {
            return new StringBuilder().append("@查看 - 解卡").toString();
        }
    }
    
    public static class ea extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().dispose(c);
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().dropMessage(1, "假死已处理完毕.");
            c.getPlayer().dropMessage(6, "当前延迟 " + c.getPlayer().getClient().getLatency() + " 毫秒");
            if (c.getPlayer().isAdmin()) {
                c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.boatEffect(1034));
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@ea - 解卡").toString();
        }
    }
    
    public static class 怪物 extends mob
    {
        @Override
        public String getMessage() {
            return new StringBuilder().append("@怪物 - 查看怪物状态").toString();
        }
    }
    
    public static class 刷钱模式 extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (c.getPlayer().getBossLog("刷钱模式介绍") == 0) {
                c.getPlayer().dropMessage(6, "刷钱模式总共2种模式通过 @刷钱模式 进行切换");
                c.getPlayer().dropMessage(6, "1.全部材料和装备都会自动帮你售卖为金币  2.只会自动帮你售卖掉落的装备、子弹、标");
                c.getPlayer().setBossLog("刷钱模式介绍");
            }
            if (c.getPlayer().getItemQuantity(((Integer)Start.ConfigValuesMap.get("自动刷钱道具")).intValue(), true) < 1) {
                return false;
            }
            final MapleCharacter player = c.getPlayer();
            if (player.get刷钱模式() == 0) {
                player.set刷钱模式(1);
                player.dropMessage(6, "刷钱模式已经开启 - 全部材料和装备都会自动帮你售卖为金币");
            }
            else if (player.get刷钱模式() == 1) {
                player.set刷钱模式(2);
                player.dropMessage(6, "刷钱模式已经开启 - 只会自动帮你售卖掉落的装备、子弹、标");
            }
            else {
                player.set刷钱模式(0);
                player.dropMessage(6, "刷钱模式已经关闭.");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@刷钱模式 - 开启自动出售道具").toString();
        }
    }
    
    public static class mob extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            MapleMonster monster = null;
            for (final MapleMapObject monstermo : c.getPlayer().getMap().getMapObjectsInRange(c.getPlayer().getPosition(), 100000.0, Arrays.asList(MapleMapObjectType.MONSTER))) {
                monster = (MapleMonster)monstermo;
                if (monster.isAlive()) {
                    if (monster.getStats().isBoss() && !c.getPlayer().isGM() && ((Integer)Start.ConfigValuesMap.get("怪物血量显示开关")).intValue() < 1) {
                        c.getPlayer().dropMessage(6, "[领主怪物]:由于强大的力量，领主的血量被保护了起来 ");
                    }
                    else {
                        c.getPlayer().dropMessage(6, "怪物 " + monster.toString());
                    }
                }
            }
            if (monster == null) {
                c.getPlayer().dropMessage(6, "找不到地图上的怪物");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@mob - 查看怪物状态").toString();
        }
    }
    
    public static class CGM extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            boolean autoReply = false;
            if (splitted.length < 2) {
                return false;
            }
            final String talk = StringUtil.joinStringFrom(splitted, 1);
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage(6, "因为你自己是GM所以无法使用此指令,可以尝试!cngm <讯息> 来建立GM聊天频道~");
            }
            else if (!c.getPlayer().getCheatTracker().GMSpam(100000, 1)) {
                boolean fake = false;
                boolean showmsg = true;
                if (PiPiConfig.getBlackList().containsKey(Integer.valueOf(c.getAccID()))) {
                    fake = true;
                }
                if (talk.contains((CharSequence)"抢") && talk.contains((CharSequence)"图")) {
                    c.getPlayer().dropMessage(1, "抢图自行解決！！");
                    fake = true;
                    showmsg = false;
                }
                else if ((talk.contains((CharSequence)"被") && talk.contains((CharSequence)"骗")) || (talk.contains((CharSequence)"点") && talk.contains((CharSequence)"骗"))) {
                    c.getPlayer().dropMessage(1, "被骗请自行解決");
                    fake = true;
                    showmsg = false;
                }
                else if (talk.contains((CharSequence)"刪") && (talk.contains((CharSequence)"角") || talk.contains((CharSequence)"脚")) && talk.contains((CharSequence)"错")) {
                    c.getPlayer().dropMessage(1, "刪错角色请自行解決");
                    fake = true;
                    showmsg = false;
                }
                else if (talk.contains((CharSequence)"乱") && talk.contains((CharSequence)"名") && talk.contains((CharSequence)"声")) {
                    c.getPlayer().dropMessage(1, "请自行解決");
                    fake = true;
                    showmsg = false;
                }
                if (talk.toUpperCase().contains((CharSequence)"VIP") && (talk.contains((CharSequence)"领") || talk.contains((CharSequence)"获")) && talk.contains((CharSequence)"取")) {
                    c.getPlayer().dropMessage(1, "VIP将会于储值后一段时间后自行发放，请耐心等待");
                    autoReply = true;
                }
                else if (talk.contains((CharSequence)"贡献") || talk.contains((CharSequence)"666") || ((talk.contains((CharSequence)"取") || talk.contains((CharSequence)"拿") || talk.contains((CharSequence)"发") || talk.contains((CharSequence)"领")) && (talk.contains((CharSequence)"勋") || talk.contains((CharSequence)"徽") || talk.contains((CharSequence)"勛")) && talk.contains((CharSequence)"章"))) {
                    c.getPlayer().dropMessage(1, "勋章请去点拍卖NPC案领取勋章\r\n如尚未被加入清单请耐心等候GM。");
                    autoReply = true;
                }
                else if ((talk.contains((CharSequence)"商人") && talk.contains((CharSequence)"吃")) || (talk.contains((CharSequence)"商店") && talk.contains((CharSequence)"补偿"))) {
                    c.getPlayer().dropMessage(1, "目前精灵商人装备和金币有机率被吃\r\n如被吃了请务必将当时的情況完整描述给管理员\r\n\r\nPS: 不会补偿任何物品");
                    autoReply = true;
                }
                else if (talk.contains((CharSequence)"档") && talk.contains((CharSequence)"案") && talk.contains((CharSequence)"受") && talk.contains((CharSequence)"损")) {
                    c.getPlayer().dropMessage(1, "档案受损请重新解压縮主程式写");
                    autoReply = true;
                }
                else if ((talk.contains((CharSequence)"缺") || talk.contains((CharSequence)"少")) && ((talk.contains((CharSequence)"技") && talk.contains((CharSequence)"能") && talk.contains((CharSequence)"点")) || talk.toUpperCase().contains((CharSequence)"SP"))) {
                    c.getPlayer().dropMessage(1, "缺少技能点请重练，沒有其他方法了写");
                    autoReply = true;
                }
                if (showmsg) {
                    c.getPlayer().dropMessage(6, "讯息已经寄送给GM了!");
                }
                if (!fake) {
                    Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[管理员帮帮忙]频道 " + c.getPlayer().getClient().getChannel() + " 玩家 [" + c.getPlayer().getName() + "] (" + c.getPlayer().getId() + "): " + talk + (autoReply ? " -- (系统已自动回复)" : "")));
                }
                FileoutputUtil.logToFile("logs/data/管理员帮帮忙.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家[" + c.getPlayer().getName() + "] 账号[" + c.getAccountName() + "]: " + talk + (autoReply ? " -- (系统已自动回复)" : "") + "\r\n");
            }
            else {
                c.getPlayer().dropMessage(6, "为了防止对GM刷屏所以每1分钟只能发一次.");
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@cgm - 跟GM回报").toString();
        }
    }
    
    public static class 清除道具 extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 4) {
                return false;
            }
            String Column = "null";
            int start = -1;
            int end = -1;
            try {
                Column = splitted[1];
                start = Integer.parseInt(splitted[2]);
                end = Integer.parseInt(splitted[3]);
            }
            catch (Exception ex) {}
            if (start == -1 || end == -1) {
                c.getPlayer().dropMessage("@清除道具 <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数>");
                return true;
            }
            if (start < 1) {
                start = 1;
            }
            if (end > 96) {
                end = 96;
            }
            final String s = Column;
            MapleInventoryType type = null;
            switch (s) {
                case "装备栏": {
                    type = MapleInventoryType.EQUIP;
                    break;
                }
                case "消耗栏": {
                    type = MapleInventoryType.USE;
                    break;
                }
                case "装饰栏": {
                    type = MapleInventoryType.SETUP;
                    break;
                }
                case "其他栏": {
                    type = MapleInventoryType.ETC;
                    break;
                }
                case "特殊栏": {
                    type = MapleInventoryType.CASH;
                    break;
                }
                default: {
                    type = null;
                    break;
                }
            }
            if (type == null) {
                c.getPlayer().dropMessage("@清除道具 <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数>");
                return true;
            }
            final MapleInventory inv = c.getPlayer().getInventory(type);
            for (int i = start; i <= end; ++i) {
                if (inv.getItem((short)i) != null) {
                    MapleInventoryManipulator.removeFromSlot(c, type, (short)i, inv.getItem((short)i).getQuantity(), true);
                }
            }
            FileoutputUtil.logToFile("logs/Data/玩家指令.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了指令 " + StringUtil.joinStringFrom(splitted, 0));
            c.getPlayer().dropMessage(6, "您已经清除了第 " + start + " 格到 " + end + "格的" + Column + "道具");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@清除道具 <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数>").toString();
        }
    }
    
    public static class jk_hm extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().RemoveHired();
            c.getPlayer().dropMessage("卡精灵商人已经解除");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@jk_hm - 卡精灵商人解除").toString();
        }
    }
    
    public static class jcds extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            int gain = c.getPlayer().getMP();
            if (gain <= 0) {
                c.getPlayer().dropMessage("目前沒有任何在线点数写。");
                return true;
            }
            if (splitted.length < 2) {
                c.getPlayer().dropMessage("目前抵用: " + c.getPlayer().getCSPoints(2));
                c.getPlayer().dropMessage("目前在线点数已经累积: " + gain + " 点，若要领取请输入 @jcds true");
            }
            else if ("true".equals(splitted[1])) {
                gain = c.getPlayer().getMP();
                c.getPlayer().modifyCSPoints(2, gain, true);
                c.getPlayer().setMP(0);
                c.getPlayer().saveToDB(false, false);
                c.getPlayer().dropMessage("领取了 " + gain + " 点在线点数, 目前抵用: " + c.getPlayer().getCSPoints(2));
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@jcds - 领取在线点数").toString();
        }
    }
    
    public static class 在线点数 extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            int gain = c.getPlayer().getMP();
            if (gain <= 0) {
                c.getPlayer().dropMessage("目前沒有任何在线点数写。");
                return true;
            }
            if (splitted.length < 2) {
                c.getPlayer().dropMessage("目前抵用: " + c.getPlayer().getCSPoints(2));
                c.getPlayer().dropMessage("目前在线点数已经累积: " + gain + " 点，若要领取请输入 @在线点数 是");
            }
            else if ("是".equals(splitted[1])) {
                gain = c.getPlayer().getMP();
                c.getPlayer().modifyCSPoints(2, gain, true);
                c.getPlayer().setMP(0);
                c.getPlayer().saveToDB(false, false);
                c.getPlayer().dropMessage("领取了 " + gain + " 点在线点数, 目前抵用: " + c.getPlayer().getCSPoints(2));
            }
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@在线点数 - 领取在线点数").toString();
        }
    }
    
    public static class 丟弃点装 extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.sendPacket(MaplePacketCreator.enableActions());
            NPCScriptManager.getInstance().start(c, 9010000, "丟弃点装");
            return true;
        }
        
        @Override
        public String getMessage() {
            return "@" + this.getClass().getSimpleName().toLowerCase() + "丟弃点装 [点装在装备栏的位置]";
        }
    }
    
    public static class Mobdrop extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().start(c, 9900006);
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@Mobdrop").toString();
        }
    }
    
    public static class Mapdrop extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().start(c, 9900006, "全局爆率");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@Mapdrop").toString();
        }
    }
    
    public abstract static class DistributeStatCommands extends CommandExecute
    {
        protected MapleStat stat;
        
        public DistributeStatCommands() {
            this.stat = null;
        }
        
        private void setStat(final MapleCharacter player, final int amount) {
            switch (this.stat) {
                case STR: {
                    player.getStat().setStr((short)amount);
                    player.updateSingleStat(MapleStat.STR, (int)player.getStat().getStr());
                    break;
                }
                case DEX: {
                    player.getStat().setDex((short)amount);
                    player.updateSingleStat(MapleStat.DEX, (int)player.getStat().getDex());
                    break;
                }
                case INT: {
                    player.getStat().setInt((short)amount);
                    player.updateSingleStat(MapleStat.INT, (int)player.getStat().getInt());
                    break;
                }
                case LUK: {
                    player.getStat().setLuk((short)amount);
                    player.updateSingleStat(MapleStat.LUK, (int)player.getStat().getLuk());
                    break;
                }
            }
        }
        
        private int getStat(final MapleCharacter player) {
            switch (this.stat) {
                case STR: {
                    return player.getStat().getStr();
                }
                case DEX: {
                    return player.getStat().getDex();
                }
                case INT: {
                    return player.getStat().getInt();
                }
                case LUK: {
                    return player.getStat().getLuk();
                }
                default: {
                    throw new RuntimeException();
                }
            }
        }
        
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(5, "输入的数字无效.");
                return false;
            }
            int change = 0;
            try {
                change = Integer.parseInt(splitted[1]);
            }
            catch (NumberFormatException nfe) {
                c.getPlayer().dropMessage(5, "输入的数字无效.");
                return false;
            }
            if (change <= 0) {
                c.getPlayer().dropMessage(5, "您必须输入一个大于 0 的数字.");
                return false;
            }
            if (c.getPlayer().getRemainingAp() < change) {
                c.getPlayer().dropMessage(5, "您的能力点不足.");
                return false;
            }
            final int number = this.getStat(c.getPlayer()) + change;
            if (number >= 32767) {
                return false;
            }
            this.setStat(c.getPlayer(), this.getStat(c.getPlayer()) + change);
            c.getPlayer().setRemainingAp((short)(c.getPlayer().getRemainingAp() - change));
            c.getPlayer().updateSingleStat(MapleStat.AVAILABLEAP, (int)c.getPlayer().getRemainingAp());
            c.getPlayer().dropMessage(5, "加点成功您的 " + StringUtil.makeEnumHumanReadable(this.stat.name()) + " 提高了 " + change + " 点.");
            return true;
        }
    }
    
    public static class 力量 extends DistributeStatCommands
    {
        public 力量() {
            this.stat = MapleStat.STR;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@力量 - 力量").toString();
        }
    }
    
    public static class 敏捷 extends DistributeStatCommands
    {
        public 敏捷() {
            this.stat = MapleStat.DEX;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@敏捷 - 敏捷").toString();
        }
    }
    
    public static class 智力 extends DistributeStatCommands
    {
        public 智力() {
            this.stat = MapleStat.INT;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@智力 - 智力").toString();
        }
    }
    
    public static class 运气 extends DistributeStatCommands
    {
        public 运气() {
            this.stat = MapleStat.LUK;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@运气 - 运气").toString();
        }
    }
    
    public static class jkzd extends 解卡组队
    {
    }
    
    public static class 解卡组队 extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setParty(null);
            c.getPlayer().dropMessage(1, "解卡组队成功，赶紧去组队把。");
            return true;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@解卡组队-游戏无法进行组队的时候输入").toString();
        }
    }
    
    public static class 副本拉人 extends CommandExecute
    {
        @Override
        public boolean execute(final MapleClient c, final String[] splitted) {
            if (c.getPlayer().getEventInstance() != null) {
                if (c.getChannelServer().getEventSMA().getEventManager(c.getPlayer().getEventInstance().getName()) != null) {
                    final EventManager em = c.getChannelServer().getEventSMA().getEventManager(c.getPlayer().getEventInstance().getName());
                }
                else if (c.getChannelServer().getEventSMB().getEventManager(c.getPlayer().getEventInstance().getName()) != null) {
                    c.getChannelServer().getEventSMB().getEventManager(c.getPlayer().getEventInstance().getName());
                }
                EventManager em;
                if (c.getChannelServer().getEventSMC().getEventManager(c.getPlayer().getEventInstance().getName()) != null) {
                    em = c.getChannelServer().getEventSMC().getEventManager(c.getPlayer().getEventInstance().getName());
                }
                else {
                    if (c.getChannelServer().getEventSM().getEventManager(c.getPlayer().getEventInstance().getName()) == null) {
                        c.getPlayer().dropMessage(1, "你这是什么鬼副本找都没找到");
                        return false;
                    }
                    em = c.getChannelServer().getEventSM().getEventManager(c.getPlayer().getEventInstance().getName());
                }
                final EventManager eim = em;
                for (final MaplePartyCharacter chr : c.getPlayer().getParty().getMembers()) {
                    final MapleCharacter curChar = c.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
                    if (eim.getProperty("joinid").indexOf("" + curChar.getId()) == -1) {
                        c.getPlayer().dropMessage(1, "偷鸡么,你想拉谁？搞事情啊？把偷鸡的人给我t了!");
                        return false;
                    }
                    final int Map = c.getPlayer().getMapId();
                    if (curChar.getMapId() == Map) {
                        continue;
                    }
                    curChar.dropMessage(1, "你的队友拉你回副本，稍后就会传送至队友身边。");
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(3000L);
                                if (curChar != null) {
                                    eim.startInstancea(curChar);
                                }
                            }
                            catch (InterruptedException ex) {}
                        }
                    }.start();
                }
                return true;
            }
            c.getPlayer().dropMessage(1, "我拉你个pp虾,都不在副本拉拉,去厕所把！！");
            return false;
        }
        
        @Override
        public String getMessage() {
            return new StringBuilder().append("@副本拉人-副本或者boss击杀过程中有人掉线可以拉回").toString();
        }
    }
}
