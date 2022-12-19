package scripting;

import handling.login.LoginInformationProvider;
import client.MapleCharacterUtil;
import server.life.MapleLifeFactory;
import LzjSetting.Game;
import database.DatabaseScriptAPI;
import server.MaplePortal;
import java.util.LinkedHashSet;
import java.util.Calendar;
import MXDJR.RobotSocket;
import handling.world.World.Find;
import handling.channel.handler.InterServerHandler;
import server.gashapon.GashaponFactory;
import server.gashapon.Gashapon;
import server.shops.HiredMerchant;
import server.life.MonsterGlobalDropEntry;
import gui.Start;
import server.life.MonsterDropEntry;
import client.MapleJob;
import handling.channel.MapleGuildRanking.JobRankingInfo;
import handling.channel.MapleGuildRanking;
import tools.FilePrinter;
import tools.SearchGenerator;
import server.Timer.EventTimer;
import handling.world.World.Family;
import server.Timer.CloneTimer;
import server.StructPotentialItem;
import java.util.Collection;
import java.util.ArrayList;
import handling.world.MapleParty;
import handling.world.World.Alliance;
import server.MapleStatEffect;
import client.inventory.ItemFlag;
import java.util.HashMap;
import server.SpeedRunner;
import server.maps.SpeedRunType;
import tools.Pair;
import java.util.EnumMap;
import server.MapleCarnivalChallenge;
import server.MapleCarnivalParty;
import server.maps.AramiaFireWorks;
import server.maps.Event_PyramidSubway;
import server.maps.Event_DojoAgent;
import tools.packet.PlayerShopPacket;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import tools.FileoutputUtil;
import java.sql.SQLException;
import database.DBConPool;
import server.life.MapleMonster;
import server.maps.MapleMapObject;
import java.util.Arrays;
import server.maps.MapleMapObjectType;
import server.life.MapleMonsterInformationProvider;
import client.inventory.Equip;
import handling.world.guild.MapleGuild;
import handling.world.World.Guild;
import tools.StringUtil;
import server.MapleSquad;
import server.maps.MapleMap;
import handling.channel.ChannelServer;
import handling.world.MaplePartyCharacter;
import client.MapleCharacter;
import client.SkillFactory;
import java.util.Map;
import client.SkillEntry;
import client.ISkill;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.List;
import client.inventory.MapleInventory;
import java.util.LinkedList;
import client.inventory.MapleInventoryType;
import server.quest.MapleQuest;
import constants.GameConstants;
import client.inventory.Item;
import server.RandomRewards;
import client.inventory.IItem;
import handling.world.World.Broadcast;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MapleShopFactory;
import handling.world.World;
import server.Randomizer;
import client.MapleStat;
import tools.MaplePacketCreator;
import java.sql.Connection;
import javax.script.Invocable;
import client.MapleClient;
import tools.packet.MobPacket;

public class NPCConversationManager extends AbstractPlayerInteraction
{
    private final MapleClient c;
    private final int npc;
    private final int questid;
    private final int mode;
    protected String script;
    private String getText;
    private final byte type;
    private byte lastMsg;
    public boolean pendingDisposal;
    private final Invocable iv;
    public Connection con;
    
    public NPCConversationManager(final MapleClient c, final int npc, final int questid, final int mode, final String npcscript, final byte type, final Invocable iv) {
        super(c);
        this.lastMsg = -1;
        this.pendingDisposal = false;
        this.con = null;
        this.c = c;
        this.npc = npc;
        this.questid = questid;
        this.mode = mode;
        this.type = type;
        this.iv = iv;
        this.script = npcscript;
    }
    
    public Invocable getIv() {
        return this.iv;
    }
    
    public int getMode() {
        return this.mode;
    }
    
    public int getNpc() {
        return this.npc;
    }
    
    public int getQuest() {
        return this.questid;
    }
    
    public String getScript() {
        return this.script;
    }
    
    public byte getType() {
        return this.type;
    }
    
    public void safeDispose() {
        this.pendingDisposal = true;
    }
    
    public void dispose() {
        NPCScriptManager.getInstance().dispose(this.c);
    }
    public void 对话结束() {
        NPCScriptManager.getInstance().dispose(c);
    }
    public void askMapSelection(final String sel) {
        if (this.lastMsg > -1) {
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getMapSelection(this.npc, sel));
        this.lastMsg = 13;
    }
    
    public void sendNext(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 01", (byte)0));
        this.lastMsg = 0;
    }
    
    public void sendNextS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 01", type));
        this.lastMsg = 0;
    }
    
    public void sendPrev(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "01 00", (byte)0));
        this.lastMsg = 0;
    }
    
    public void sendPrevS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "01 00", type));
        this.lastMsg = 0;
    }
    
    public void sendNextPrev(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "01 01", (byte)0));
        this.lastMsg = 0;
    }
    
    public void PlayerToNpc(final String text) {
        this.sendNextPrevS(text, (byte)3);
    }
    
    public void sendNextPrevS(final String text) {
        this.sendNextPrevS(text, (byte)3);
    }
    
    public void sendNextPrevS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "01 01", type));
        this.lastMsg = 0;
    }
    
    public void sendOk(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 00", (byte)0));
        this.lastMsg = 0;
    }
    
    public void sendOkS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 00", type));
        this.lastMsg = 0;
    }
    
    public void sendOkS(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimpleS(text, this.type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 00", (byte)2));
        this.lastMsg = 0;
    }
    
    public void sendYesNo(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)1, text, "", (byte)0));
        this.lastMsg = 1;
    }
    
    public void sendYesNoS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimpleS(text, type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)1, text, "", type));
        this.lastMsg = 1;
    }
    
    public void sendYesNoS(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimpleS(text, this.type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)1, text, "", (byte)2));
        this.lastMsg = 1;
    }
    
    public void sendAcceptDecline(final String text) {
        this.askAcceptDecline(text);
    }
    
    public void sendAcceptDeclineNoESC(final String text) {
        this.askAcceptDeclineNoESC(text);
    }
    
    public void askAcceptDecline(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)11, text, "", (byte)0));
        this.lastMsg = 11;
    }
    
    public void askAcceptDeclineNoESC(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)12, text, "", (byte)0));
        this.lastMsg = 12;
    }
    
    public void askAvatar(final String text, final int... args) {
        if (this.lastMsg > -1) {
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalkStyle(this.npc, text, args));
        this.lastMsg = 7;
    }
    
    public void askAvatar(final String text, final int card, final int... args) {
        if (this.lastMsg > -1) {
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalkStyle(this.npc, text, card, args));
        this.lastMsg = 7;
    }
    
    public void sendSimple(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (!text.contains((CharSequence)"#L")) {
            this.sendNext(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)4, text, "", (byte)0));
        this.lastMsg = 4;
    }
    
    public void sendSimple(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (!text.contains((CharSequence)"#L")) {
            this.sendNextS(text, type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)4, text, "", type));
        this.lastMsg = 4;
    }
    
    public void sendSimpleS(final String text, final byte type) {
        if (this.lastMsg > -1) {
            return;
        }
        if (!text.contains((CharSequence)"#L")) {
            this.sendNextS(text, type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)4, text, "", type));
        this.lastMsg = 4;
    }
    
    public void sendSimpleS(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (!text.contains((CharSequence)"#L")) {
            this.sendNextS(text, this.type);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalk(this.npc, (byte)4, text, "", (byte)2));
        this.lastMsg = 4;
    }
    
    public void sendStyle(final String text, final int[] styles) {
        if (this.lastMsg > -1) {
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalkStyle(this.npc, text, styles));
        this.lastMsg = 7;
    }
    
    public void sendStyle(final String text, final int caid, final int[] styles) {
        if (this.lastMsg > -1) {
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalkStyle(this.id, text, caid, styles));
        this.lastMsg = 7;
    }
    
    public void sendSecondStyle(final String text, final int[] styles) {
        if (this.lastMsg > -1) {
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalkStyle(this.id, text, styles));
        this.lastMsg = 7;
    }
    
    public void sendGetNumber(final String text, final int def, final int min, final int max) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalkNum(this.npc, text, def, min, max));
        this.lastMsg = 3;
    }
    
    public void sendGetText(final String text) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.sendPacket(MaplePacketCreator.getNPCTalkText(this.npc, text));
        this.lastMsg = 2;
    }
    
    public void setGetText(final String text) {
        this.getText = text;
    }
    
    public String getText() {
        return this.getText;
    }
    
    public void setHair(final int hair) {
        this.getPlayer().setHair(hair);
        this.getPlayer().updateSingleStat(MapleStat.HAIR, hair);
        this.getPlayer().equipChanged();
    }
    
    public void setFace(final int face) {
        this.getPlayer().setFace(face);
        this.getPlayer().updateSingleStat(MapleStat.FACE, face);
        this.getPlayer().equipChanged();
    }
    
    public void setSkin(final int color) {
        this.getPlayer().setSkinColor((byte)color);
        this.getPlayer().updateSingleStat(MapleStat.SKIN, color);
        this.getPlayer().equipChanged();
    }
    
    public int setRandomAvatar(final int ticket, final int... args_all) {
        if (!this.haveItem(ticket)) {
            return -1;
        }
        this.gainItem(ticket, (short)(-1));
        final int args = args_all[Randomizer.nextInt(args_all.length)];
        if (args < 100) {
            this.c.getPlayer().setSkinColor((byte)args);
            this.c.getPlayer().updateSingleStat(MapleStat.SKIN, args);
        }
        else if (args < 30000) {
            this.c.getPlayer().setFace(args);
            this.c.getPlayer().updateSingleStat(MapleStat.FACE, args);
        }
        else {
            this.c.getPlayer().setHair(args);
            this.c.getPlayer().updateSingleStat(MapleStat.HAIR, args);
        }
        this.c.getPlayer().equipChanged();
        return 1;
    }
    
    public int setAvatar(final int ticket, final int args) {
        if (!this.haveItem(ticket)) {
            return -1;
        }
        this.gainItem(ticket, (short)(-1));
        if (args < 100) {
            this.c.getPlayer().setSkinColor((byte)args);
            this.c.getPlayer().updateSingleStat(MapleStat.SKIN, args);
        }
        else if (args < 30000) {
            this.c.getPlayer().setFace(args);
            this.c.getPlayer().updateSingleStat(MapleStat.FACE, args);
        }
        else {
            this.c.getPlayer().setHair(args);
            this.c.getPlayer().updateSingleStat(MapleStat.HAIR, args);
        }
        this.c.getPlayer().equipChanged();
        return 1;
    }
    
    public int setAvatars(final int args) {
        if (args < 100) {
            this.c.getPlayer().setSkinColor((byte)args);
            this.c.getPlayer().updateSingleStat(MapleStat.SKIN, args);
        }
        else if (args < 30000) {
            this.c.getPlayer().setFace(args);
            this.c.getPlayer().updateSingleStat(MapleStat.FACE, args);
        }
        else {
            this.c.getPlayer().setHair(args);
            this.c.getPlayer().updateSingleStat(MapleStat.HAIR, args);
        }
        this.c.getPlayer().equipChanged();
        return 1;
    }
    
    public void sendStorage() {
        if (this.getPlayer().hasBlockedInventory2(true)) {
            this.c.getPlayer().dropMessage(1, "系统错误，请联系管理员。");
            this.c.sendPacket(MaplePacketCreator.enableActions());
            return;
        }
        if (!World.isShutDown) {
            if (!World.isShopShutDown) {
                this.c.getPlayer().setConversation(4);
                this.c.getPlayer().getStorage().sendStorage(this.c, this.npc);
            }
            else {
                this.c.getPlayer().dropMessage(1, "目前不能使用仓库。");
                this.c.sendPacket(MaplePacketCreator.enableActions());
            }
        }
        else {
            this.c.getPlayer().dropMessage(1, "目前不能使用仓库。");
            this.c.sendPacket(MaplePacketCreator.enableActions());
        }
    }
    
    public void openShop(final int id) {
        MapleShopFactory.getInstance().getShop(id).sendShop(this.c);
    }
    
    public int gainGachaponItem(final int id, final int quantity, final String msg, final byte 概率) {
        try {
            if (!MapleItemInformationProvider.getInstance().itemExists(id)) {
                this.getPlayer().dropMessage(5, "gainGachaponItem itemExists(id) == -1");
                return -1;
            }
            final IItem item = MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short)quantity);
            if (item == null) {
                this.getPlayer().dropMessage(5, "gainGachaponItem item == -1");
                return -1;
            }
            if (概率 > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "]", " : 被玩家 " + this.c.getPlayer().getName() + " 获得！大家一起来恭喜他吧！", item, 概率));
            }
            return item.getItemId();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public int gainGachaponItem(final int id, final int quantity) {
        return this.gainGachaponItem(id, quantity, this.c.getPlayer().getMap().getStreetName() + " - " + this.c.getPlayer().getMap().getMapName());
    }
    
    public int gainGachaponItem(final int id, final int quantity, final String msg) {
        try {
            if (!MapleItemInformationProvider.getInstance().itemExists(id)) {
                return -1;
            }
            final IItem item = MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short)quantity);
            if (item == null) {
                return -1;
            }
            final byte rareness = (byte)(int)Math.floor((double)(item.getItemId() / 1000000));
            if (rareness == 1) {
                Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "]", " : 被玩家 " + this.c.getPlayer().getName() + " 获得！大家一起来恭喜他吧！", item, rareness));
            }
            else if (rareness == 2) {
                Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "]", " : 被玩家 " + this.c.getPlayer().getName() + " 获得！大家一起来恭喜他吧！", item, rareness));
            }
            else if (rareness > 2) {
                Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "]", " : 被玩家 " + this.c.getPlayer().getName() + " 获得！大家一起来恭喜他吧！", item, rareness));
            }
            return item.getItemId();
        }
        catch (Exception ex) {
            return -1;
        }
    }
    
    public int gainGachaponItem2(final int id, final int quantity) {
        return this.gainGachaponItem2(id, quantity, this.c.getPlayer().getMap().getStreetName());
    }
    
    public int gainGachaponItem2(int id, final int quantity, final String msg) {
        try {
            id = RandomRewards.getGoldBoxReward();
            if (!MapleItemInformationProvider.getInstance().itemExists(id)) {
                return -1;
            }
            final Item item = (Item)MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short)quantity);
            if (item == null) {
                return -1;
            }
            final byte rareness = GameConstants.gachaponRareItem(item.getItemId());
            if (rareness > 0) {
                Broadcast.broadcastSmega(MaplePacketCreator.itemMegaphone("高级快乐百宝箱 : " + this.c.getPlayer().getName() + "获得该物品,大家恭喜他(她)吧!", true, this.c.getChannel(), (IItem)item));
            }
            this.c.getSession().write(MaplePacketCreator.getShowItemGain(item.getItemId(), (short)quantity, true));
            return item.getItemId();
        }
        catch (Exception ex) {
            return -1;
        }
    }
    
    public int gainGachaponItemTime(final int id, final int quantity, final long period) {
        return this.gainGachaponItemTime(id, quantity, this.c.getPlayer().getMap().getStreetName() + " - " + this.c.getPlayer().getMap().getMapName(), period);
    }
    
    public int gainGachaponItemTime(final int id, final int quantity, final String msg, final long period) {
        try {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            if (!ii.itemExists(id)) {
                return -1;
            }
            final IItem item = ii.isCash(id) ? MapleInventoryManipulator.addbyId_GachaponTime(this.c, id, (short)quantity, period) : MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short)quantity);
            if (item == null) {
                return -1;
            }
            final byte rareness = GameConstants.gachaponRareItem(item.getItemId());
            if (rareness == 1) {
                if (this.c.getPlayer().getMapId() == 910000000) {
                    Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[自由市场]", " : 恭喜玩家 " + this.c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness));
                }
                else {
                    Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[隐藏地图-转蛋屋]", " : 恭喜玩家 " + this.c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness));
                }
            }
            else if (rareness == 2) {
                Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : 被他成功转到了，大家恭喜他吧！", item, rareness));
            }
            else if (rareness > 2) {
                Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : 被他从枫叶转蛋机转到了，大家恭喜他吧！", item, rareness));
            }
            return item.getItemId();
        }
        catch (Exception ex) {
            return -1;
        }
    }
    
    public void changeJob(final int job) {
        this.c.getPlayer().changeJob(job);
    }
    
    public void startQuest(final int id) {
        MapleQuest.getInstance(id).start(this.getPlayer(), this.npc);
    }
    
    @Override
    public void completeQuest(final int id) {
        MapleQuest.getInstance(id).complete(this.getPlayer(), this.npc);
    }
    
    public void forfeitQuest(final int id) {
        MapleQuest.getInstance(id).forfeit(this.getPlayer());
    }
    
    public void forceStartQuest() {
        MapleQuest.getInstance(this.questid).forceStart(this.getPlayer(), this.getNpc(), null);
    }
    
    @Override
    public void forceStartQuest(final int id) {
        MapleQuest.getInstance(id).forceStart(this.getPlayer(), this.getNpc(), null);
    }
    
    public void forceStartQuest(final String customData) {
        MapleQuest.getInstance(this.questid).forceStart(this.getPlayer(), this.getNpc(), customData);
    }
    
    public void completeQuest() {
        MapleQuest.getInstance(this.questid).forceComplete(this.getPlayer(), this.getNpc());
    }
    
    public void forceCompleteQuest() {
        MapleQuest.getInstance(this.questid).forceComplete(this.getPlayer(), this.getNpc());
    }
    
    @Override
    public void forceCompleteQuest(final int id) {
        MapleQuest.getInstance(id).forceComplete(this.getPlayer(), this.getNpc());
    }
    
    public String getQuestCustomData() {
        return this.c.getPlayer().getQuestNAdd(MapleQuest.getInstance(this.questid)).getCustomData();
    }
    
    public void setQuestCustomData(final String customData) {
        this.getPlayer().getQuestNAdd(MapleQuest.getInstance(this.questid)).setCustomData(customData);
    }
    
    public int getMeso() {
        return this.getPlayer().getMeso();
    }
    
    public void gainAp(final int amount) {
        this.c.getPlayer().gainAp((short)amount);
    }
    
    public void expandInventory(final byte type, final int amt) {
        this.c.getPlayer().expandInventory(type, amt);
    }
    
    public void unequipEverything() {
        final MapleInventory equipped = this.getPlayer().getInventory(MapleInventoryType.EQUIPPED);
        final MapleInventory equip = this.getPlayer().getInventory(MapleInventoryType.EQUIP);
        final List<Short> ids = (List<Short>)new LinkedList();
        for (final IItem item : equipped.list()) {
            ids.add(Short.valueOf(item.getPosition()));
        }
        final Iterator<Short> iterator2 = ids.iterator();
        while (iterator2.hasNext()) {
            final short id = ((Short)iterator2.next()).shortValue();
            MapleInventoryManipulator.unequip(this.getC(), id, equip.getNextFreeSlot());
        }
    }
    
    public final void clearSkills() {
        final Map<ISkill, SkillEntry> skills = this.getPlayer().getSkills();
        for (final Entry<ISkill, SkillEntry> skill : skills.entrySet()) {
            this.getPlayer().changeSkillLevel((ISkill)skill.getKey(), (byte)0, (byte)0);
        }
    }
    
    public String getSkillMenu(final int skillMaster) {
        final StringBuilder sb = new StringBuilder();
        final Map<ISkill, SkillEntry> skills = this.getPlayer().getSkills();
        for (final Entry<ISkill, SkillEntry> skilla : skills.entrySet()) {
            final ISkill skill = SkillFactory.getSkill(((ISkill)skilla.getKey()).getId());
            final int key = ((ISkill)skilla.getKey()).getId();
            if (skill.getMaxLevel() > 10 && ((SkillEntry)skilla.getValue()).masterlevel < skill.getMaxLevel()) {
                if (skillMaster > 20) {
                    if (((SkillEntry)skilla.getValue()).masterlevel >= 30 || ((SkillEntry)skilla.getValue()).masterlevel < 20 || skill.getMaxLevel() <= 20) {
                        continue;
                    }
                    sb.append("\r\n#L").append(((ISkill)skilla.getKey()).getId()).append("##s").append(key).append("##q").append(key).append("##l");
                }
                else {
                    if (((SkillEntry)skilla.getValue()).masterlevel >= 20) {
                        continue;
                    }
                    sb.append("\r\n#L").append(key).append("##s").append(key).append("##q").append(key).append("##l");
                }
            }
        }
        return sb.toString();
    }
    
    public boolean canUseSkillBook(final int skillId, final int masterLevel) {
        if (masterLevel > 0) {
            final ISkill skill = SkillFactory.getSkill(skillId);
            if (this.getPlayer().getSkillLevel(skill) >= skill.getMaxLevel()) {
                return false;
            }
            final int skillLevel = this.getPlayer().getSkillLevel(skill);
            if ((skillLevel >= 5 && masterLevel == 20) || (skillLevel >= 15 && masterLevel == 30)) {
                return true;
            }
        }
        return false;
    }
    
    public void useSkillBook(final int skillId, int masterLevel) {
        final ISkill skill = SkillFactory.getSkill(skillId);
        masterLevel = ((masterLevel > skill.getMaxLevel()) ? skill.getMaxLevel() : masterLevel);
        this.getPlayer().changeSkillLevel(skill, this.getPlayer().getSkillLevel(skill), (byte)masterLevel);
        this.getPlayer().getMap().broadcastMessage(MaplePacketCreator.useSkillBook(this.getPlayer(), 0, 0, true, true));
        this.enableActions();
    }
    
    public void useFailed() {
        this.getMap().broadcastMessage(MaplePacketCreator.useSkillBook(this.getPlayer(), 0, 0, true, false));
        this.enableActions();
    }
    
    public boolean hasSkill(final int skillid) {
        final ISkill theSkill = SkillFactory.getSkill(skillid);
        return theSkill != null && this.c.getPlayer().getSkillLevel(theSkill) > 0;
    }
    
    public boolean hasSkill(final int skillId, final int level) {
        final ISkill theSkill = SkillFactory.getSkill(skillId);
        return theSkill != null && this.c.getPlayer().getSkillLevel(theSkill) >= level;
    }
    
    public void showEffect(final boolean broadcast, final String effect) {
        if (broadcast) {
            this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.showEffect(effect));
        }
        else {
            this.c.sendPacket(MaplePacketCreator.showEffect(effect));
        }
    }
    
    public void playSound(final boolean broadcast, final String sound) {
        if (broadcast) {
            this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.playSound(sound));
        }
        else {
            this.c.sendPacket(MaplePacketCreator.playSound(sound));
        }
    }
    
    public void environmentChange(final boolean broadcast, final String env) {
        if (broadcast) {
            this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.environmentChange(env, 2));
        }
        else {
            this.c.sendPacket(MaplePacketCreator.environmentChange(env, 2));
        }
    }
    
    public void updateBuddyCapacity(final int capacity) {
        this.c.getPlayer().setBuddyCapacity((byte)capacity);
    }
    
    public int getBuddyCapacity() {
        return this.c.getPlayer().getBuddyCapacity();
    }
    
    public int partyMembersInMap() {
        int inMap = 0;
        for (final MapleCharacter char2 : this.getPlayer().getMap().getCharactersThreadsafe()) {
            if (char2.getParty() == this.getPlayer().getParty()) {
                ++inMap;
            }
        }
        return inMap;
    }
    
    public List<MapleCharacter> getPartyMembers() {
        if (this.getPlayer().getParty() == null) {
            return null;
        }
        final List<MapleCharacter> chars = (List<MapleCharacter>)new LinkedList();
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            for (final ChannelServer channel : ChannelServer.getAllInstances()) {
                final MapleCharacter ch = channel.getPlayerStorage().getCharacterById(chr.getId());
                if (ch != null) {
                    chars.add(ch);
                }
            }
        }
        return chars;
    }
    
    public void warpPartyWithExp(final int mapId, final int exp) {
        final MapleMap target = this.getMap(mapId);
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.c.getChannelServer().getPlayerStorage().getCharacterByName(chr.getName());
            if ((curChar.getEventInstance() == null && this.getPlayer().getEventInstance() == null) || curChar.getEventInstance() == this.getPlayer().getEventInstance()) {
                curChar.changeMap(target, target.getPortal(0));
                curChar.gainExp(exp, true, false, true);
            }
        }
    }
    
    public void warpPartyWithExpMeso(final int mapId, final int exp, final int meso) {
        final MapleMap target = this.getMap(mapId);
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.c.getChannelServer().getPlayerStorage().getCharacterByName(chr.getName());
            if ((curChar.getEventInstance() == null && this.getPlayer().getEventInstance() == null) || curChar.getEventInstance() == this.getPlayer().getEventInstance()) {
                curChar.changeMap(target, target.getPortal(0));
                curChar.gainExp(exp, true, false, true);
                curChar.gainMeso(meso, true);
            }
        }
    }
    
    public MapleSquad getSquad(final String type) {
        return this.c.getChannelServer().getMapleSquad(type);
    }
    
    public int getSquadAvailability(final String type) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return -1;
        }
        return squad.getStatus();
    }
    
    public boolean registerSquad(final String type, final int minutes, final String startText) {
        if (this.c.getChannelServer().getMapleSquad(type) == null) {
            final MapleSquad squad = new MapleSquad(this.c.getChannel(), type, this.c.getPlayer(), minutes * 60 * 1000, startText);
            final boolean ret = this.c.getChannelServer().addMapleSquad(squad, type);
            if (ret) {
                final MapleMap map = this.c.getPlayer().getMap();
                map.broadcastMessage(MaplePacketCreator.getClock(minutes * 60));
                map.broadcastMessage(MaplePacketCreator.serverNotice(6, this.c.getPlayer().getName() + startText));
            }
            else {
                squad.clear();
            }
            return ret;
        }
        return false;
    }
    
    public boolean getSquadList(final String type, final byte type_) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return false;
        }
        if (type_ == 0 || type_ == 3) {
            this.sendNext(squad.getSquadMemberString(type_));
        }
        else if (type_ == 1) {
            this.sendSimple(squad.getSquadMemberString(type_));
        }
        else if (type_ == 2) {
            if (squad.getBannedMemberSize() > 0) {
                this.sendSimple(squad.getSquadMemberString(type_));
            }
            else {
                this.sendNext(squad.getSquadMemberString(type_));
            }
        }
        return true;
    }
    
    public byte isSquadLeader(final String type) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return -1;
        }
        if (squad.getLeader() != null && squad.getLeader().getId() == this.c.getPlayer().getId()) {
            return 1;
        }
        return 0;
    }
    
    public boolean reAdd(final String eim, final String squad) {
        final EventInstanceManager eimz = this.getDisconnected(eim);
        final MapleSquad squadz = this.getSquad(squad);
        if (eimz != null && squadz != null) {
            squadz.reAddMember(this.getPlayer());
            eimz.registerPlayer(this.getPlayer());
            return true;
        }
        return false;
    }
    
    public void banMember(final String type, final int pos) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad != null) {
            squad.banMember(pos);
        }
    }
    
    public void acceptMember(final String type, final int pos) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad != null) {
            squad.acceptMember(pos);
        }
    }
    
    public String getReadableMillis(final long startMillis, final long endMillis) {
        return StringUtil.getReadableMillis(startMillis, endMillis);
    }
    
    public int addMember(final String type, final boolean join) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad != null) {
            return squad.addMember(this.c.getPlayer(), join);
        }
        return -1;
    }
    
    public byte isSquadMember(final String type) {
        final MapleSquad squad = this.c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return -1;
        }
        if (squad.getMembers().contains(this.c.getPlayer().getName())) {
            return 1;
        }
        if (squad.isBanned(this.c.getPlayer())) {
            return 2;
        }
        return 0;
    }
    
    public void resetReactors() {
        this.getPlayer().getMap().resetReactors();
    }
    
    public void genericGuildMessage(final int code) {
        this.c.sendPacket(MaplePacketCreator.genericGuildMessage((byte)code));
    }
    
    public void disbandGuild() {
        final int gid = this.c.getPlayer().getGuildId();
        if (gid <= 0 || this.c.getPlayer().getGuildRank() != 1) {
            return;
        }
        Guild.disbandGuild(gid);
    }
    
    public void increaseGuildCapacity() {
        if (this.c.getPlayer().getMeso() < 250000) {
            this.c.sendPacket(MaplePacketCreator.serverNotice(1, "你没有足够的金币."));
            return;
        }
        final int gid = this.c.getPlayer().getGuildId();
        if (gid <= 0) {
            return;
        }
        Guild.increaseGuildCapacity(gid);
        this.c.getPlayer().gainMeso(-250000, true, false, true);
    }
    
    public void displayGuildRanks() {
        MapleGuild.displayGuildRanks(this.getClient(), this.npc);
    }
    
    public void showlvl() {
        MapleGuild.displayLevelRanks(this.getClient(), this.npc);
    }
    
    public void showmeso() {
        MapleGuild.meso(this.getClient(), this.npc);
    }
    
    public void showfame() {
        MapleGuild.fame(this.getClient(), this.npc);
    }
    
    public void ShowMarrageEffect() {
        this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.sendMarrageEffect());
    }
    
    public boolean removePlayerFromInstance() {
        if (this.c.getPlayer().getEventInstance() != null) {
            this.c.getPlayer().getEventInstance().removePlayer(this.c.getPlayer());
            return true;
        }
        return false;
    }
    
    public boolean isPlayerInstance() {
        return this.c.getPlayer().getEventInstance() != null;
    }
    
    public void changeStat(final byte slot, final int type, final short amount) {
        final Equip sel = (Equip)this.c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short)slot);
        switch (type) {
            case 0: {
                sel.setStr(amount);
                break;
            }
            case 1: {
                sel.setDex(amount);
                break;
            }
            case 2: {
                sel.setInt(amount);
                break;
            }
            case 3: {
                sel.setLuk(amount);
                break;
            }
            case 4: {
                sel.setHp(amount);
                break;
            }
            case 5: {
                sel.setMp(amount);
                break;
            }
            case 6: {
                sel.setWatk(amount);
                break;
            }
            case 7: {
                sel.setMatk(amount);
                break;
            }
            case 8: {
                sel.setWdef(amount);
                break;
            }
            case 9: {
                sel.setMdef(amount);
                break;
            }
            case 10: {
                sel.setAcc(amount);
                break;
            }
            case 11: {
                sel.setAvoid(amount);
                break;
            }
            case 12: {
                sel.setHands(amount);
                break;
            }
            case 13: {
                sel.setSpeed(amount);
                break;
            }
            case 14: {
                sel.setJump(amount);
                break;
            }
            case 15: {
                sel.setUpgradeSlots((byte)amount);
                break;
            }
            case 16: {
                sel.setViciousHammer((byte)amount);
                break;
            }
            case 17: {
                sel.setLevel((byte)amount);
                break;
            }
            case 18: {
                sel.setEnhance((byte)amount);
                break;
            }
            case 19: {
                sel.setPotential1(amount);
                break;
            }
            case 20: {
                sel.setPotential2(amount);
                break;
            }
            case 21: {
                sel.setPotential3(amount);
                break;
            }
            case 22: {
                sel.setOwner(this.getText());
                break;
            }
        }
        this.c.getPlayer().equipChanged();
    }
    
    public void cleardrops() {
        MapleMonsterInformationProvider.getInstance().clearDrops();
    }
    
    public void killAllMonsters() {
        final MapleMap map = this.c.getPlayer().getMap();
        final double range = Double.POSITIVE_INFINITY;
        for (final MapleMapObject monstermo : map.getMapObjectsInRange(this.c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
            final MapleMonster mob = (MapleMonster)monstermo;
            if (mob.getStats().isBoss()) {
                map.killMonster(mob, this.c.getPlayer(), false, false, (byte)1);
            }
        }
    }
    
    public Connection getConnection() throws SQLException {
        if (this.con == null) {
            this.con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
        }
        return this.con;
    }
    
    public void giveMerchantMesos() {
        long mesos = 0L;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT mesos FROM hiredmerchants WHERE merchantid = ?");
            ps.setInt(1, this.getPlayer().getId());
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
            }
            else {
                mesos = rs.getLong("mesos");
            }
            rs.close();
            ps.close();
            ps = con.prepareStatement("UPDATE hiredmerchants SET mesos = 0 WHERE merchantid = ?");
            ps.setInt(1, this.getPlayer().getId());
            ps.executeUpdate();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("Error gaining mesos in hired merchant" + ex);
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex);
        }
        this.c.getPlayer().gainMeso((int)mesos, true);
    }
    
    public void dc() {
        final MapleCharacter victim = this.getChannelServer().getPlayerStorage().getCharacterByName(this.getPlayer().getName());
        victim.getClient().getSession().close();
        victim.getClient().disconnect(true, false);
    }
    
    public long getMerchantMesos() {
        long mesos = 0L;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("SELECT mesos FROM hiredmerchants WHERE merchantid = ?")) {
            ps.setInt(1, this.getPlayer().getId());
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mesos = rs.getLong("mesos");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("Error gaining mesos in hired merchant" + ex);
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex);
        }
        return mesos;
    }
    
    public void openDuey() {
        this.c.getPlayer().setConversation(2);
        this.c.sendPacket(MaplePacketCreator.sendDuey((byte)9, null));
    }
    
    public void openMerchantItemStore() {
        if (!World.isShutDown) {
            this.c.getPlayer().setConversation(3);
            this.c.sendPacket(PlayerShopPacket.merchItemStore((byte)34));
        }
        else {
            this.c.getPlayer().dropMessage(1, "目前不能使用精灵商人领取。");
            this.c.sendPacket(MaplePacketCreator.enableActions());
        }
    }
    
    public void sendRepairWindow() {
        this.c.sendPacket(MaplePacketCreator.sendRepairWindow(this.npc));
    }
    
    public final int getDojoPoints() {
        return this.c.getPlayer().getDojo();
    }
    
    public void setDojoPoints(final int point) {
        this.c.getPlayer().setDojo(this.c.getPlayer().getDojo() + point);
    }
    
    public final int getDojoRecord() {
        return this.c.getPlayer().getDojoRecord();
    }
    
    public void setDojoRecord(final boolean reset) {
        this.c.getPlayer().setDojoRecord(reset);
    }
    
    public boolean start_DojoAgent(final boolean dojo, final boolean party) {
        if (dojo) {
            return Event_DojoAgent.warpStartDojo(this.c.getPlayer(), party);
        }
        return Event_DojoAgent.warpStartAgent(this.c.getPlayer(), party);
    }
    
    public boolean start_PyramidSubway(final int pyramid) {
        if (pyramid >= 0) {
            return Event_PyramidSubway.warpStartPyramid(this.c.getPlayer(), pyramid);
        }
        return Event_PyramidSubway.warpStartSubway(this.c.getPlayer());
    }
    
    public boolean bonus_PyramidSubway(final int pyramid) {
        if (pyramid >= 0) {
            return Event_PyramidSubway.warpBonusPyramid(this.c.getPlayer(), pyramid);
        }
        return Event_PyramidSubway.warpBonusSubway(this.c.getPlayer());
    }
    
    public final short getKegs() {
        return AramiaFireWorks.getInstance().getKegsPercentage();
    }
    
    public void giveKegs(final int kegs) {
        AramiaFireWorks.getInstance().giveKegs(this.c.getPlayer(), kegs);
    }
    
    public final short getSunshines() {
        return AramiaFireWorks.getInstance().getSunsPercentage();
    }
    
    public void addSunshines(final int kegs) {
        AramiaFireWorks.getInstance().giveSuns(this.c.getPlayer(), kegs);
    }
    
    public final short getDecorations() {
        return AramiaFireWorks.getInstance().getDecsPercentage();
    }
    
    public void addDecorations(final int kegs) {
        try {
            AramiaFireWorks.getInstance().giveDecs(this.c.getPlayer(), kegs);
        }
        catch (Exception ex) {}
    }
    
    public final MapleInventory getInventory(final int type) {
        return this.c.getPlayer().getInventory(MapleInventoryType.getByType((byte)type));
    }
    
    public final MapleCarnivalParty getCarnivalParty() {
        return this.c.getPlayer().getCarnivalParty();
    }
    
    public final MapleCarnivalChallenge getNextCarnivalRequest() {
        return this.c.getPlayer().getNextCarnivalRequest();
    }
    
    public final MapleCarnivalChallenge getCarnivalChallenge(final MapleCharacter chr) {
        return new MapleCarnivalChallenge(chr);
    }
    
    public void maxStats() {
        final Map<MapleStat, Long> statup = new EnumMap(MapleStat.class);
        this.c.getPlayer().getStat().setStr((short)32767);
        this.c.getPlayer().getStat().setDex((short)32767);
        this.c.getPlayer().getStat().setInt((short)32767);
        this.c.getPlayer().getStat().setLuk((short)32767);
        this.c.getPlayer().getStat().setMaxHp((short)30000);
        this.c.getPlayer().getStat().setMaxMp((short)30000);
        this.c.getPlayer().getStat().setHp(30000);
        this.c.getPlayer().getStat().setMp(30000);
        statup.put(MapleStat.STR, Long.valueOf(32767));
        statup.put(MapleStat.DEX, Long.valueOf(32767));
        statup.put(MapleStat.LUK, Long.valueOf(32767));
        statup.put(MapleStat.INT, Long.valueOf(32767));
        statup.put(MapleStat.HP, Long.valueOf(30000));
        statup.put(MapleStat.MAXHP, Long.valueOf(30000));
        statup.put(MapleStat.MP, Long.valueOf(30000));
        statup.put(MapleStat.MAXMP, Long.valueOf(30000));
        this.c.sendPacket(MaplePacketCreator.updatePlayerStats(statup, this.c.getPlayer()));
    }
    
    public Pair<String, Map<Integer, String>> getSpeedRun(final String typ) {
        final SpeedRunType stype = SpeedRunType.valueOf(typ);
        if (SpeedRunner.getInstance().getSpeedRunData(stype) != null) {
            return SpeedRunner.getInstance().getSpeedRunData(stype);
        }
        return new Pair("", (Map<Integer, String>)new HashMap());
    }
    
    public boolean getSR(final Pair<String, Map<Integer, String>> ma, final int sel) {
        if ((ma.getRight()).get(Integer.valueOf(sel)) == null || ((String)(ma.getRight()).get(Integer.valueOf(sel))).length() <= 0) {
            this.dispose();
            return false;
        }
        this.sendOk((String)(ma.getRight()).get(Integer.valueOf(sel)));
        return true;
    }
    
    public Equip getEquip(final int itemid) {
        return (Equip)MapleItemInformationProvider.getInstance().getEquipById(itemid);
    }
    
    public void setExpiration(final Object statsSel, final long expire) {
        if (statsSel instanceof Equip) {
            ((Equip)statsSel).setExpiration(System.currentTimeMillis() + expire * 24L * 60L * 60L * 1000L);
        }
    }
    
    public void setLock(final Object statsSel) {
        if (statsSel instanceof Equip) {
            final Equip eq = (Equip)statsSel;
            if (eq.getExpiration() == -1L) {
                eq.setFlag((byte)(eq.getFlag() | ItemFlag.LOCK.getValue()));
            }
            else {
                eq.setFlag((byte)(eq.getFlag() | ItemFlag.UNTRADEABLE.getValue()));
            }
        }
    }
    
    public boolean addFromDrop(final Object statsSel) {
        if (statsSel instanceof IItem) {
            final IItem it = (IItem)statsSel;
            return MapleInventoryManipulator.checkSpace(this.getClient(), it.getItemId(), (int)it.getQuantity(), it.getOwner()) && MapleInventoryManipulator.addFromDrop(this.getClient(), it, false);
        }
        return false;
    }
    
    public boolean replaceItem(final int slot, final int invType, final Object statsSel, final int offset, final String type) {
        return this.replaceItem(slot, invType, statsSel, offset, type, false);
    }
    
    public boolean replaceItem(final int slot, final int invType, final Object statsSel, final int offset, final String type, final boolean takeSlot) {
        final MapleInventoryType inv = MapleInventoryType.getByType((byte)invType);
        if (inv == null) {
            return false;
        }
        IItem item = this.getPlayer().getInventory(inv).getItem((short)(byte)slot);
        if (item == null || statsSel instanceof IItem) {
            item = (IItem)statsSel;
        }
        if (offset > 0) {
            if (inv != MapleInventoryType.EQUIP) {
                return false;
            }
            final Equip eq = (Equip)item;
            if (takeSlot) {
                if (eq.getUpgradeSlots() < 1) {
                    return false;
                }
                eq.setUpgradeSlots((byte)(eq.getUpgradeSlots() - 1));
            }
            if (type.equalsIgnoreCase("Slots")) {
                eq.setUpgradeSlots((byte)(eq.getUpgradeSlots() + offset));
            }
            else if (type.equalsIgnoreCase("Level")) {
                eq.setLevel((byte)(eq.getLevel() + offset));
            }
            else if (type.equalsIgnoreCase("Hammer")) {
                eq.setViciousHammer((byte)(eq.getViciousHammer() + offset));
            }
            else if (type.equalsIgnoreCase("STR")) {
                eq.setStr((short)(eq.getStr() + offset));
            }
            else if (type.equalsIgnoreCase("DEX")) {
                eq.setDex((short)(eq.getDex() + offset));
            }
            else if (type.equalsIgnoreCase("INT")) {
                eq.setInt((short)(eq.getInt() + offset));
            }
            else if (type.equalsIgnoreCase("LUK")) {
                eq.setLuk((short)(eq.getLuk() + offset));
            }
            else if (type.equalsIgnoreCase("HP")) {
                eq.setHp((short)(eq.getHp() + offset));
            }
            else if (type.equalsIgnoreCase("MP")) {
                eq.setMp((short)(eq.getMp() + offset));
            }
            else if (type.equalsIgnoreCase("WATK")) {
                eq.setWatk((short)(eq.getWatk() + offset));
            }
            else if (type.equalsIgnoreCase("MATK")) {
                eq.setMatk((short)(eq.getMatk() + offset));
            }
            else if (type.equalsIgnoreCase("WDEF")) {
                eq.setWdef((short)(eq.getWdef() + offset));
            }
            else if (type.equalsIgnoreCase("MDEF")) {
                eq.setMdef((short)(eq.getMdef() + offset));
            }
            else if (type.equalsIgnoreCase("ACC")) {
                eq.setAcc((short)(eq.getAcc() + offset));
            }
            else if (type.equalsIgnoreCase("Avoid")) {
                eq.setAvoid((short)(eq.getAvoid() + offset));
            }
            else if (type.equalsIgnoreCase("Hands")) {
                eq.setHands((short)(eq.getHands() + offset));
            }
            else if (type.equalsIgnoreCase("Speed")) {
                eq.setSpeed((short)(eq.getSpeed() + offset));
            }
            else if (type.equalsIgnoreCase("Jump")) {
                eq.setJump((short)(eq.getJump() + offset));
            }
            else if (type.equalsIgnoreCase("ItemEXP")) {
                eq.setItemEXP(eq.getItemEXP() + offset);
            }
            else if (type.equalsIgnoreCase("Expiration")) {
                eq.setExpiration(eq.getExpiration() + (long)offset);
            }
            else if (type.equalsIgnoreCase("Flag")) {
                eq.setFlag((byte)(eq.getFlag() + offset));
            }
            if (eq.getExpiration() == -1L) {
                eq.setFlag((byte)(eq.getFlag() | ItemFlag.LOCK.getValue()));
            }
            else {
                eq.setFlag((byte)(eq.getFlag() | ItemFlag.UNTRADEABLE.getValue()));
            }
            item = eq.copy();
        }
        MapleInventoryManipulator.removeFromSlot(this.getClient(), inv, (short)slot, item.getQuantity(), false);
        return MapleInventoryManipulator.addFromDrop(this.getClient(), item, false);
    }
    
    public boolean replaceItem(final int slot, final int invType, final Object statsSel, final int upgradeSlots) {
        return this.replaceItem(slot, invType, statsSel, upgradeSlots, "Slots");
    }
    
    @Override
    public boolean isCash(final int itemId) {
        return MapleItemInformationProvider.getInstance().isCash(itemId);
    }
    
    public boolean isOnlyTradeBlock(final int itemId) {
        return MapleItemInformationProvider.getInstance().isOnlyTradeBlock(itemId);
    }
    
    public void buffGuild(final int buff, final int duration, final String msg) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (ii.getItemEffect(buff) != null && this.getPlayer().getGuildId() > 0) {
            final MapleStatEffect mse = ii.getItemEffect(buff);
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharactersThreadSafe()) {
                    if (chr.getGuildId() == this.getPlayer().getGuildId()) {
                        mse.applyTo(chr, chr, true, null, duration);
                        chr.dropMessage(5, "Your guild has gotten a " + msg + " buff.");
                    }
                }
            }
        }
    }
    
    public boolean createAlliance(final String alliancename) {
        final MapleParty pt = this.c.getPlayer().getParty();
        final MapleCharacter otherChar = this.c.getChannelServer().getPlayerStorage().getCharacterById(pt.getMemberByIndex(1).getId());
        if (otherChar == null || otherChar.getId() == this.c.getPlayer().getId()) {
            return false;
        }
        try {
            return Alliance.createAlliance(alliancename, this.c.getPlayer().getId(), otherChar.getId(), this.c.getPlayer().getGuildId(), otherChar.getGuildId());
        }
        catch (Exception re) {
            return false;
        }
    }
    
    public boolean addCapacityToAlliance() {
        try {
            final MapleGuild gs = Guild.getGuild(this.c.getPlayer().getGuildId());
            if (gs != null && this.c.getPlayer().getGuildRank() == 1 && this.c.getPlayer().getAllianceRank() == 1 && Alliance.getAllianceLeader(gs.getAllianceId()) == this.c.getPlayer().getId() && Alliance.changeAllianceCapacity(gs.getAllianceId())) {
                this.gainMeso(-10000000);
                return true;
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public boolean disbandAlliance() {
        try {
            final MapleGuild gs = Guild.getGuild(this.c.getPlayer().getGuildId());
            if (gs != null && this.c.getPlayer().getGuildRank() == 1 && this.c.getPlayer().getAllianceRank() == 1 && Alliance.getAllianceLeader(gs.getAllianceId()) == this.c.getPlayer().getId() && Alliance.disbandAlliance(gs.getAllianceId())) {
                return true;
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public byte getLastMsg() {
        return this.lastMsg;
    }
    
    public final void setLastMsg(final byte last) {
        this.lastMsg = last;
    }
    
    public void setPartyBossLog(final String bossid) {
        final MapleParty party = this.getPlayer().getParty();
        for (final MaplePartyCharacter pc : party.getMembers()) {
            final MapleCharacter chr = World.getStorage(this.getChannelNumber()).getCharacterById(pc.getId());
            if (chr != null) {
                chr.setBossLog(bossid);
            }
        }
    }
    
    public final void maxAllSkills() {
        for (final ISkill skil : SkillFactory.getAllSkills()) {
            if (GameConstants.isApplicableSkill(skil.getId())) {
                this.teachSkill(skil.getId(), skil.getMaxLevel(), skil.getMaxLevel());
            }
        }
    }
    
    public final void resetStats(final int str, final int dex, final int z, final int luk) {
        this.c.getPlayer().resetStats(str, dex, z, luk);
    }
    
    public final boolean dropItem(final int slot, final int invType, final int quantity) {
        final MapleInventoryType inv = MapleInventoryType.getByType((byte)invType);
        return inv != null && MapleInventoryManipulator.drop(this.c, inv, (short)slot, (short)quantity, true);
    }
    
    public final List<Integer> getAllPotentialInfo() {
        return new ArrayList(MapleItemInformationProvider.getInstance().getAllPotentialInfo().keySet());
    }
    
    public final String getPotentialInfo(final int id) {
        final List<StructPotentialItem> potInfo = MapleItemInformationProvider.getInstance().getPotentialInfo(id);
        final StringBuilder builder = new StringBuilder("#b#ePOTENTIAL INFO FOR ID: ");
        builder.append(id);
        builder.append("#n#k\r\n\r\n");
        int minLevel = 1;
        int maxLevel = 10;
        for (final StructPotentialItem item : potInfo) {
            builder.append("#eLevels ");
            builder.append(minLevel);
            builder.append("~");
            builder.append(maxLevel);
            builder.append(": #n");
            builder.append(item.toString());
            minLevel += 10;
            maxLevel += 10;
            builder.append("\r\n");
        }
        return builder.toString();
    }
    
    public final void sendRPS() {
        this.c.sendPacket(MaplePacketCreator.getRPSMode((byte)8, -1, -1, -1));
    }
    
    public final void setQuestRecord(final Object ch, final int questid, final String data) {
        ((MapleCharacter)ch).getQuestNAdd(MapleQuest.getInstance(questid)).setCustomData(data);
    }
    
    public final void doWeddingEffect(final Object ch) {
        final MapleCharacter chr = (MapleCharacter)ch;
        this.getMap().broadcastMessage(MaplePacketCreator.yellowChat(chr.getName() + ", 你愿意承认 " + this.getPlayer().getName() + " 做你的丈夫，诚实遵照上帝的誡命，和他生活在一起，无论在什么环境愿顺服他、爱惜他、安慰他、尊重他保护他，以致奉召归主？？"));
        CloneTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                if (chr == null || NPCConversationManager.this.getPlayer() == null) {
                    NPCConversationManager.this.warpMap(680000500, 0);
                }
                else {
                    NPCConversationManager.this.getMap().broadcastMessage(MaplePacketCreator.yellowChat(NPCConversationManager.this.getPlayer().getName() + ", 你愿意承认接纳 " + chr.getName() + " 做你的妻子，诚实遵照上帝的誡命，和她生活在一起，无论在什么环境，愿意终生養她、爱惜她、安慰她、尊重她、保护她，以至奉召归主？？"));
                }
            }
        }, 10000L);
        CloneTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                if (chr == null || NPCConversationManager.this.getPlayer() == null) {
                    if (NPCConversationManager.this.getPlayer() != null) {
                        NPCConversationManager.this.setQuestRecord(NPCConversationManager.this.getPlayer(), 160001, "3");
                        NPCConversationManager.this.setQuestRecord(NPCConversationManager.this.getPlayer(), 160002, "0");
                    }
                    else if (chr != null) {
                        NPCConversationManager.this.setQuestRecord(chr, 160001, "3");
                        NPCConversationManager.this.setQuestRecord(chr, 160002, "0");
                    }
                    NPCConversationManager.this.warpMap(680000500, 0);
                }
                else {
                    NPCConversationManager.this.setQuestRecord(NPCConversationManager.this.getPlayer(), 160001, "2");
                    NPCConversationManager.this.setQuestRecord(chr, 160001, "2");
                    NPCConversationManager.this.sendNPCText(NPCConversationManager.this.getPlayer().getName() + " 和 " + chr.getName() + "， 我希望你们两个能在此时此刻永远爱著对方！", 9201002);
                    NPCConversationManager.this.getMap().startExtendedMapEffect("那么现在请新娘亲吻 " + NPCConversationManager.this.getPlayer().getName() + "！", 5120006);
                    if (chr.getGuildId() > 0) {
                        Guild.guildPacket(chr.getGuildId(), MaplePacketCreator.sendMarriage(false, chr.getName()));
                    }
                    if (chr.getFamilyId() > 0) {
                        Family.familyPacket(chr.getFamilyId(), MaplePacketCreator.sendMarriage(true, chr.getName()), chr.getId());
                    }
                    if (NPCConversationManager.this.getPlayer().getGuildId() > 0) {
                        Guild.guildPacket(NPCConversationManager.this.getPlayer().getGuildId(), MaplePacketCreator.sendMarriage(false, NPCConversationManager.this.getPlayer().getName()));
                    }
                    if (NPCConversationManager.this.getPlayer().getFamilyId() > 0) {
                        Family.familyPacket(NPCConversationManager.this.getPlayer().getFamilyId(), MaplePacketCreator.sendMarriage(true, chr.getName()), NPCConversationManager.this.getPlayer().getId());
                    }
                }
            }
        }, 20000L);
    }
    
    public void 开启小钢珠(final int type) {
        this.c.sendPacket(MaplePacketCreator.openBeans(this.getPlayer().getBeans(), type));
    }
    
    public void worldMessage(final String text) {
        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, text));
    }
    
    public int getBeans() {
        return this.getClient().getPlayer().getBeans();
    }
    
    public void warpBackWithParty(final int mid, final int retmap, final int time) {
        final MapleMap warpMap = this.c.getChannelServer().getMapFactory().getMap(mid);
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.c.getChannelServer().getPlayerStorage().getCharacterByName(chr.getName());
            if (curChar != null && ((curChar.getEventInstance() == null && this.getPlayer().getEventInstance() == null) || curChar.getEventInstance() == this.getPlayer().getEventInstance())) {
                curChar.changeMap(warpMap, warpMap.getPortal(0));
                curChar.getClient().sendPacket(MaplePacketCreator.getClock(time));
                curChar.set副本地图(mid);
                EventTimer.getInstance().schedule((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        final MapleMap warpMap = NPCConversationManager.this.c.getChannelServer().getMapFactory().getMap(retmap);
                        if (curChar.getMapId() == mid && curChar.getMapId() == curChar.get副本地图()) {
                            curChar.getClient().sendPacket(MaplePacketCreator.stopClock());
                            curChar.changeMap(warpMap, warpMap.getPortal(0));
                            curChar.dropMessage(6, "已经到达目的地了!");
                        }
                    }
                }, (long)(1000 * time));
            }
        }
    }
    
    public void warpBack(final int mid, final int retmap, final int time) {
        final MapleMap warpMap = this.c.getChannelServer().getMapFactory().getMap(mid);
        this.c.getPlayer().changeMap(warpMap, warpMap.getPortal(0));
        this.c.sendPacket(MaplePacketCreator.getClock(time));
        EventTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                final MapleMap warpMap = NPCConversationManager.this.c.getChannelServer().getMapFactory().getMap(retmap);
                if (NPCConversationManager.this.c.getPlayer() != null) {
                    NPCConversationManager.this.c.sendPacket(MaplePacketCreator.stopClock());
                    NPCConversationManager.this.c.getPlayer().changeMap(warpMap, warpMap.getPortal(0));
                    NPCConversationManager.this.c.getPlayer().dropMessage(6, "已经到达目的地了!");
                }
            }
        }, (long)(1000 * time));
    }
    
    public void ChangeName(final String name) {
        this.getPlayer().setName(name);
        this.save();
        this.getPlayer().fakeRelog();
    }
    
    public String searchData(final int type, final String search) {
        return SearchGenerator.searchData(type, search);
    }
    
    public int[] getSearchData(final int type, final String search) {
        final Map<Integer, String> data = SearchGenerator.getSearchData(type, search);
        if (data.isEmpty()) {
            return null;
        }
        final int[] searches = new int[data.size()];
        int i = 0;
        final Iterator<Integer> iterator = data.keySet().iterator();
        while (iterator.hasNext()) {
            final int key = ((Integer)iterator.next()).intValue();
            searches[i] = key;
            ++i;
        }
        return searches;
    }
    
    public boolean foundData(final int type, final String search) {
        return SearchGenerator.foundData(type, search);
    }
    
    public boolean ReceiveMedal() {
        final int acid = this.getPlayer().getAccountID();
        final int id = this.getPlayer().getId();
        final String name = this.getPlayer().getName();
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final int item = 1142475;
        if (!this.getPlayer().canHold(item)) {
            return false;
        }
        if (this.getPlayer().haveItem(item)) {
            return false;
        }
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT id FROM RCmedals WHERE name = ?");
            ps.setString(1, name);
            final ResultSet rs = ps.executeQuery();
            if (!rs.first()) {
                return false;
            }
            rs.close();
            ps.close();
            ps = con.prepareStatement("Update RCmedals set amount = ? Where id = ?");
            ps.setInt(1, 0);
            ps.setInt(2, id);
            ps.execute();
            ps.close();
            con.close();
        }
        catch (Exception ex) {
            FilePrinter.printError("NPCConversationManager.txt", (Throwable)ex, "ReceiveMedal(" + name + ")");
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex);
        }
        final IItem toDrop = ii.randomizeStats((Equip)ii.getEquipById(item));
        toDrop.setGMLog(this.getPlayer().getName() + " 领取勋章");
        MapleInventoryManipulator.addbyItem(this.c, toDrop);
        FileoutputUtil.logToFile("logs/Data/NPC领取勋章.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + this.c.getSession().remoteAddress().toString().split(":")[0] + " 账号: " + this.c.getAccountName() + " 玩家: " + this.c.getPlayer().getName() + " 领取了RC勋章");
        return true;
    }
    
    public String ShowJobRank(final int type) {
        final StringBuilder sb = new StringBuilder();
        final List<JobRankingInfo> Ranking = MapleGuildRanking.getInstance().getJobRank(type);
        if (Ranking != null) {
            int num = 0;
            for (final JobRankingInfo info : Ranking) {
                ++num;
                sb.append("#n#e#k排名:#r ");
                sb.append(num);
                sb.append("\r\n#n#e#k玩家名称:#d ");
                sb.append(StringUtil.getRightPaddedStr(info.getName(), ' ', 13));
                sb.append("\r\n#n#e#k等级:#e#r ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getLevel()), ' ', 3));
                sb.append("\r\n#n#e#k职业:#e#b ");
                sb.append(MapleJob.getName(MapleJob.getById(info.getJob())));
                sb.append("\r\n#n#e#k力量:#e#d ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getStr()), ' ', 4));
                sb.append("\r\n#n#e#k敏捷:#e#d ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getDex()), ' ', 4));
                sb.append("\r\n#n#e#k智力:#e#d ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getInt()), ' ', 4));
                sb.append("\r\n#n#e#k幸运:#e#d ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getLuk()), ' ', 4));
                sb.append("\r\n");
                sb.append("#n#k======================================================\r\n");
            }
        }
        else {
            sb.append("#r查询无任何结果写");
        }
        return sb.toString();
    }
    
    public static boolean hairExists(final int hair) {
        return MapleItemInformationProvider.hairList.containsKey(Integer.valueOf(hair));
    }
    
    public int[] getCanHair(final int[] hairs) {
        final List<Integer> canHair = (List<Integer>)new ArrayList();
        final List<Integer> cantHair = (List<Integer>)new ArrayList();
        for (final int hair : hairs) {
            if (hairExists(hair)) {
                canHair.add(Integer.valueOf(hair));
            }
            else {
                cantHair.add(Integer.valueOf(hair));
            }
        }
        if (cantHair.size() > 0 && this.c.getPlayer().isAdmin()) {
            final StringBuilder sb = new StringBuilder("正在读取的发型裏有");
            sb.append(cantHair.size()).append("个发型客戶端不支持显示，已经被清除：");
            for (int i = 0; i < cantHair.size(); ++i) {
                sb.append(cantHair.get(i));
                if (i < cantHair.size() - 1) {
                    sb.append(",");
                }
            }
            this.playerMessage(sb.toString());
        }
        final int[] getHair = new int[canHair.size()];
        for (int i = 0; i < canHair.size(); ++i) {
            getHair[i] = ((Integer)canHair.get(i)).intValue();
        }
        return getHair;
    }
    
    public static boolean faceExists(final int face) {
        return MapleItemInformationProvider.faceLists.containsKey(Integer.valueOf(face));
    }
    
    public int[] getCanFace(final int[] faces) {
        final List<Integer> canFace = (List<Integer>)new ArrayList();
        final List<Integer> cantFace = (List<Integer>)new ArrayList();
        for (final int face : faces) {
            if (faceExists(face)) {
                canFace.add(Integer.valueOf(face));
            }
            else {
                cantFace.add(Integer.valueOf(face));
            }
        }
        if (cantFace.size() > 0 && this.c.getPlayer().isAdmin()) {
            final StringBuilder sb = new StringBuilder("正在读取的脸型裏有");
            sb.append(cantFace.size()).append("个脸型客戶端不支持显示，已经被清除：");
            for (int i = 0; i < cantFace.size(); ++i) {
                sb.append(cantFace.get(i));
                if (i < cantFace.size() - 1) {
                    sb.append(",");
                }
            }
            this.playerMessage(sb.toString());
        }
        final int[] getFace = new int[canFace.size()];
        for (int i = 0; i < canFace.size(); ++i) {
            getFace[i] = ((Integer)canFace.get(i)).intValue();
        }
        return getFace;
    }
    
    public String checkDrop(final int mobId, final boolean GM) {
        final List<MonsterDropEntry> ranks = MapleMonsterInformationProvider.getInstance().retrieveDrop(mobId);
        if (ranks != null && ranks.size() > 0) {
            int num = 0;
            int itemId = 0;
            int ch = 0;
            int ab = 1;
            final StringBuilder name = new StringBuilder();
            for (int i = 0; i < ranks.size(); ++i) {
                final MonsterDropEntry de = (MonsterDropEntry)ranks.get(i);
                if (de.chance > 0 && (de.questid <= 0 || (de.questid > 0 && MapleQuest.getInstance((int)de.questid).getName().length() > 0))) {
                    itemId = de.itemId;
                    if (num == 0) {
                        name.append("当前怪物 #o" + mobId + "# 的爆率为:\r\n");
                        name.append("当前系统爆率为:" + this.getClient().getChannelServer().getDropRate() * MapleParty.活动爆率倍率 + "\r\n");
                        name.append("--------------------------------------\r\n");
                    }
                    String namez = "#z" + itemId + "#";
                    if (itemId == 0) {
                        itemId = 4031041;
                        if (((Integer)Start.ConfigValuesMap.get("金币全局砍数量")).intValue() > 0) {
                            ab = ((Integer)Start.ConfigValuesMap.get("金币砍全局倍率")).intValue();
                        }
                        namez = de.Minimum * this.getClient().getChannelServer().getMesoRate() * MapleParty.活动金币倍率 / ab + " 到 " + de.Maximum * this.getClient().getChannelServer().getMesoRate() * MapleParty.活动金币倍率 / ab + " 金币";
                    }
                    ch = de.chance * this.getClient().getChannelServer().getDropRate() * MapleParty.活动爆率倍率;
                    if (GM) {
                        name.append(num + 1 + ") #v" + itemId + "#" + namez + " - " + Integer.valueOf((ch >= 999999) ? 1000000 : ch).doubleValue() / 10000.0 + "% 爆率。 " + ((de.questid > 0 && MapleQuest.getInstance((int)de.questid).getName().length() > 0) ? ("需要接受任务 " + MapleQuest.getInstance((int)de.questid).getName() + "") : "") + "\r\n");
                    }
                    else {
                        name.append(num + 1 + ") #v" + itemId + "#" + namez + " " + ((de.questid > 0 && MapleQuest.getInstance((int)de.questid).getName().length() > 0) ? ("需要接受任务 " + MapleQuest.getInstance((int)de.questid).getName() + "") : "") + "\r\n");
                    }
                    ++num;
                }
            }
            if (name.length() > 0) {
                return name.toString();
            }
        }
        return "沒有当前怪物的爆率数据。";
    }
    
    public String checkDrop(final MapleCharacter chr, final int mobId, final boolean GM) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final List<MonsterDropEntry> ranks = MapleMonsterInformationProvider.getInstance().retrieveDrop(mobId);
        if (ranks != null && ranks.size() > 0) {
            int num = 0;
            int itemId = 0;
            int ch = 0;
            int ab = 1;
            final StringBuilder name = new StringBuilder();
            final StringBuilder error = new StringBuilder();
            name.append("【#r#o" + mobId + "##k】掉宝物品查询列表:#b\r\n");
            for (int i = 0; i < ranks.size(); ++i) {
                final MonsterDropEntry de = (MonsterDropEntry)ranks.get(i);
                if (de.chance > 0 && (de.questid <= 0 || (de.questid > 0 && MapleQuest.getInstance((int)de.questid).getName().length() > 0))) {
                    itemId = de.itemId;
                    String namez = "#z" + itemId + "#";
                    if (itemId == 0) {
                        itemId = 4031041;
                        if (((Integer)Start.ConfigValuesMap.get("金币全局砍数量")).intValue() > 0) {
                            ab = ((Integer)Start.ConfigValuesMap.get("金币砍全局倍率")).intValue();
                        }
                        namez = de.Minimum * this.getClient().getChannelServer().getMesoRate() / ab + " to " + de.Maximum * this.getClient().getChannelServer().getMesoRate() / ab + " #b金币#l#k";
                    }
                    else if (itemId != 0 && ii.itemExists(itemId)) {
                        ch = de.chance * this.getClient().getChannelServer().getDropRate() * MapleParty.活动爆率倍率;
                        if (!GM) {
                            name.append("#k" + (num + 1) + ": #v" + itemId + "# " + namez + (chr.isGM() ? ("#d  掉落机率：" + Integer.valueOf((ch >= 999999) ? 1000000 : ch).doubleValue() / 10000.0 + "%\r\n") : "\r\n") + "#b(掉落条件:" + ((de.questid > 0 && MapleQuest.getInstance((int)de.questid).getName().length() > 0) ? ("需要接取任务#r " + MapleQuest.getInstance((int)de.questid).getName() + " #b)\r\n") : "#r无#b)") + "\r\n");
                        }
                        else {
                            name.append("#L" + itemId + "##k" + (num + 1) + ": #v" + itemId + "# " + namez + (chr.isGM() ? ("#d  掉落机率：" + Integer.valueOf((ch >= 999999) ? 1000000 : ch).doubleValue() / 10000.0 + "%(点選更改)\r\n") : "\r\n") + "#b(掉落条件:" + ((de.questid > 0 && MapleQuest.getInstance((int)de.questid).getName().length() > 0) ? ("需要接取任务#r " + MapleQuest.getInstance((int)de.questid).getName() + " #b)\r\n") : "#r无#b)") + "\r\n");
                        }
                        ++num;
                    }
                    else {
                        error.append(itemId + "\r\n");
                    }
                }
            }
            if (GM) {
                name.append("\r\n#L10000##k" + (num + 1) + ": #b我要额外新增掉落物品!");
            }
            if (error.length() > 0) {
                chr.dropMessage(1, "无效的物品ID:\r\n" + error.toString());
            }
            if (name.length() > 0) {
                return name.toString();
            }
        }
        return "该怪物查无任何掉宝数据。";
    }
    
    public String checkMapDrop() {
        final List ranks = new ArrayList((Collection)MapleMonsterInformationProvider.getInstance().getGlobalDrop());
        final int mapid = this.c.getPlayer().getMap().getId();
        final int globalServerRate = 1;
        if (ranks != null && ranks.size() > 0) {
            int num = 0;
            final StringBuilder name = new StringBuilder();
            for (int i = 0; i < ranks.size(); ++i) {
                final MonsterGlobalDropEntry de = (MonsterGlobalDropEntry)ranks.get(i);
                final int itemId = de.itemId;
                final String names = "#z" + itemId + "#";
                final int chance = de.chance * this.getClient().getChannelServer().getDropRate() * MapleParty.活动爆率倍率;
                if (this.getPlayer().isAdmin()) {
                    name.append("#b#v").append(itemId).append("#").append(names).append("#k 爆率: ").append(Integer.valueOf((chance >= 999999) ? 1000000 : chance).doubleValue() / 10000.0).append(" %").append((de.questid > 0 && MapleQuest.getInstance((int)de.questid).getName().length() > 0) ? ("需要接受任务: " + MapleQuest.getInstance((int)de.questid).getName()) : "").append("#k\r\n");
                }
                else {
                    name.append("#b#v").append(itemId).append("#").append(names).append((de.questid > 0 && MapleQuest.getInstance((int)de.questid).getName().length() > 0) ? (" #d需要接受任务: #r" + MapleQuest.getInstance((int)de.questid).getName()) : "").append("#k\r\n");
                }
                ++num;
            }
            if (name.length() > 0) {
                return name.toString();
            }
        }
        return "全局爆物数据为空。";
    }
    
    public void gainBeans(final int s) {
        this.getPlayer().gainBeans(s);
        this.c.getSession().write(MaplePacketCreator.updateBeans(this.c.getPlayer()));
    }
    
    public void openBeans() {
        this.c.getSession().write(MaplePacketCreator.openBeans(this.getPlayer().getBeans(), 0));
        this.c.getPlayer().dropMessage(5, "按住左右鍵可以调整力道,建议调好角度一路给他打,不要按暂停若九宮格卡住沒反应请离开在近来");
    }
    
    public void setMonsterRiding(final int itemid) {
        final short src = this.getClient().getPlayer().haveItemPos(itemid);
        if (src == 100) {
            this.c.getPlayer().dropMessage(5, "你沒有当前坐骑。");
        }
        else {
            MapleInventoryManipulator.equip(this.c, src, (short)(-18));
            this.c.getPlayer().dropMessage(5, "装备坐骑成功。");
        }
    }
    
    public int getRandom(final int... args_all) {
        final int args = args_all[Randomizer.nextInt(args_all.length)];
        return args;
    }
    
    public void OwlAdv(final int point, final int itemid) {
        owlse(this.c, point, itemid);
    }
    
    public static void owlse(final MapleClient c, final int point, final int itemid) {
        final int itemSearch = itemid;
        final List<HiredMerchant> hms = (List<HiredMerchant>)new ArrayList();
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            if (!cserv.searchMerchant(itemSearch).isEmpty()) {
                hms.addAll(cserv.searchMerchant(itemSearch));
            }
        }
        if (hms.size() > 0) {
            if (c.getPlayer().haveItem(5230000, 1)) {
                MapleInventoryManipulator.removeById(c, MapleInventoryType.CASH, 5230000, 1, true, false);
            }
            else if (c.getPlayer().getCSPoints(point) >= 5) {
                c.getPlayer().modifyCSPoints(point, -5, true);
            }
            else {
                c.getPlayer().dropMessage(1, "点数不足，无法查询！");
                if (NPCScriptManager.getInstance().getCM(c) != null) {
                    NPCScriptManager.getInstance().dispose(c);
                    c.sendPacket(MaplePacketCreator.enableActions());
                }
            }
            if (NPCScriptManager.getInstance().getCM(c) != null) {
                NPCScriptManager.getInstance().dispose(c);
            }
            c.sendPacket(MaplePacketCreator.getOwlSearched(itemSearch, hms));
        }
        else {
            if (NPCScriptManager.getInstance().getCM(c) != null) {
                NPCScriptManager.getInstance().dispose(c);
                c.sendPacket(MaplePacketCreator.enableActions());
            }
            c.getPlayer().dropMessage(1, "找不到物品");
        }
    }
    
    public void checkMobs(final MapleCharacter chr) {
        if (this.getMap().getAllMonstersThreadsafe().size() <= 0) {
            this.sendOk("#地图上沒有怪物哦!!。");
            this.dispose();
        }
        String msg = "玩家 #b" + chr.getName() + "#k 此地图怪物掉宝查询:\r\n#r(若有任何掉宝問题,请至社团BUG区回报怪物名称和代码)\r\n#d";
        for (final Object monsterid : this.getMap().getAllUniqueMonsters()) {
            msg = msg + "#L" + monsterid + "##o" + monsterid + "# 代码:" + monsterid + " (查看)#l\r\n";
        }
        this.sendOk(msg);
    }
    
    public void getMobs(final int itemid) {
        final MapleMonsterInformationProvider mi = MapleMonsterInformationProvider.getInstance();
        final List<Integer> mobs = MapleMonsterInformationProvider.getInstance().getMobByItem(itemid);
        String text = "#d这些怪物会掉落您查询的物品#k: \r\n\r\n";
        for (int i = 0; i < mobs.size(); ++i) {
            int quest = 0;
            if (mi.getDropQuest(((Integer)mobs.get(i)).intValue()) > 0) {
                quest = mi.getDropQuest(((Integer)mobs.get(i)).intValue());
            }
            final int chance = mi.getDropChance(((Integer)mobs.get(i)).intValue()) * this.getClient().getChannelServer().getDropRate() * MapleParty.活动爆率倍率;
            text = text + "#r#o" + mobs.get(i) + "##k " + ((quest > 0 && MapleQuest.getInstance(quest).getName().length() > 0) ? ("#b需要进行 " + MapleQuest.getInstance(quest).getName() + " 任务来取得#k") : "") + "\r\n";
        }
        this.sendNext(text);
    }
    
    public void getMob(final int itemid) {
        final MapleMonsterInformationProvider mi = MapleMonsterInformationProvider.getInstance();
        final List<Integer> mobs = MapleMonsterInformationProvider.getInstance().getMobByItem(itemid);
        String text = "#d这些怪物会掉落您查询的物品#k: \r\n\r\n";
        int a = 0;
        for (int i = 0; i < mobs.size(); ++i) {
            int quest = 0;
            if (mi.getDropQuest(((Integer)mobs.get(i)).intValue()) > 0) {
                quest = mi.getDropQuest(((Integer)mobs.get(i)).intValue());
            }
            final int chance = mi.getDropChance(((Integer)mobs.get(i)).intValue()) * this.getClient().getChannelServer().getDropRate() * MapleParty.活动爆率倍率;
            text = text + "#L" + mobs.get(i) + "##r#o" + mobs.get(i) + "##k " + ((quest > 0 && MapleQuest.getInstance(quest).getName().length() > 0) ? ("#b需要进行 " + MapleQuest.getInstance(quest).getName() + " 任务来取得#k") : "") + "\r\n";
            a = 1;
        }
        if (a == 1) {
            this.sendNext(text);
        }
        else {
            text += "不好意思啥都没找到\r\n";
            this.sendOk(text);
            this.dispose();
        }
    }
    
    public Gashapon getGashapon() {
        return GashaponFactory.getInstance().getGashaponByNpcId(this.getNpc());
    }
    
    public void getGachaponMega(final String msg, final Item item, final int quantity) {
        Broadcast.broadcastGashponmega(MaplePacketCreator.getGachaponMega(this.c.getPlayer().getName(), " : x" + quantity + "恭喜玩家 " + this.c.getPlayer().getName() + " 在" + msg + "获得！", (IItem)item, (byte)1, this.c.getPlayer().getClient().getChannel()));
    }
    
    public void EnterCS(final int mod) {
        this.c.getPlayer().setCsMod(mod);
        InterServerHandler.EnterCashShop(this.c, this.c.getPlayer(), false);
    }
    
    public int[] getSavedFaces() {
        return this.getPlayer().getSavedFaces();
    }
    
    public int getSavedFace(final int sel) {
        return this.getPlayer().getSavedFace(sel);
    }
    
    public void setSavedFace(final int sel, final int id) {
        this.getPlayer().setSavedFace(sel, id);
    }
    
    public int[] getSavedHairs() {
        return this.getPlayer().getSavedHairs();
    }
    
    public int getSavedHair(final int sel) {
        return this.getPlayer().getSavedHair(sel);
    }
    
    public void setSavedHair(final int sel, final int id) {
        this.getPlayer().setSavedHair(sel, id);
    }
    
    public void changeJobById(final short job) {
        this.c.getPlayer().changeJob((int)job);
    }
    
    public int getJobId() {
        return this.getPlayer().getJob();
    }
    
    public int getLevel() {
        return this.getPlayer().getLevel();
    }
    
    public int getEquipId(final byte slot) {
        final MapleInventory equip = this.getPlayer().getInventory(MapleInventoryType.EQUIP);
        final Equip eu = (Equip)equip.getItem((short)slot);
        return equip.getItem((short)slot).getItemId();
    }
    
    public int getUseId(final byte slot) {
        final MapleInventory use = this.getPlayer().getInventory(MapleInventoryType.USE);
        return use.getItem((short)slot).getItemId();
    }
    
    public int getSetupId(final byte slot) {
        final MapleInventory setup = this.getPlayer().getInventory(MapleInventoryType.SETUP);
        return setup.getItem((short)slot).getItemId();
    }
    
    public int getCashId(final byte slot) {
        final MapleInventory cash = this.getPlayer().getInventory(MapleInventoryType.CASH);
        return cash.getItem((short)slot).getItemId();
    }
    
    public int getETCId(final byte slot) {
        final MapleInventory etc = this.getPlayer().getInventory(MapleInventoryType.ETC);
        return etc.getItem((short)slot).getItemId();
    }
    
    public String EquipList(final MapleClient c) {
        final StringBuilder str = new StringBuilder();
        final MapleInventory equip = c.getPlayer().getInventory(MapleInventoryType.EQUIP);
        final List<String> stra = (List<String>)new LinkedList();
        for (final IItem item : equip.list()) {
            stra.add("#L" + (int)item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (final String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }
    
    public String UseList(final MapleClient c) {
        final StringBuilder str = new StringBuilder();
        final MapleInventory use = c.getPlayer().getInventory(MapleInventoryType.USE);
        final List<String> stra = (List<String>)new LinkedList();
        for (final IItem item : use.list()) {
            stra.add("#L" + (int)item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (final String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }
    
    public String CashList(final MapleClient c) {
        final StringBuilder str = new StringBuilder();
        final MapleInventory cash = c.getPlayer().getInventory(MapleInventoryType.CASH);
        final List<String> stra = (List<String>)new LinkedList();
        for (final IItem item : cash.list()) {
            stra.add("#L" + (int)item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (final String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }
    
    public String ETCList(final MapleClient c) {
        final StringBuilder str = new StringBuilder();
        final MapleInventory etc = c.getPlayer().getInventory(MapleInventoryType.ETC);
        final List<String> stra = (List<String>)new LinkedList();
        for (final IItem item : etc.list()) {
            stra.add("#L" + (int)item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (final String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }
    
    public String SetupList(final MapleClient c) {
        final StringBuilder str = new StringBuilder();
        final MapleInventory setup = c.getPlayer().getInventory(MapleInventoryType.SETUP);
        final List<String> stra = (List<String>)new LinkedList();
        for (final IItem item : setup.list()) {
            stra.add("#L" + (int)item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (final String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }
    
    public void sendNext(final String text, final int id) {
        if (this.lastMsg > -1) {
            return;
        }
        if (text.contains((CharSequence)"#L")) {
            this.sendSimple(text);
            return;
        }
        this.c.getSession().write(MaplePacketCreator.getNPCTalk(this.npc, (byte)0, text, "00 01", (byte)0));
        this.lastMsg = 0;
    }
    
    public static boolean itemExists(final int itemId) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        for (final Pair<Integer, String> item : ii.getAllItems2()) {
            if (((Integer)item.getLeft()).intValue() == itemId) {
                return true;
            }
        }
        return false;
    }
    
    public int gainGachaponItema(final int id, final int quantity, final String msg) {
        try {
            if (!MapleItemInformationProvider.getInstance().itemExists(id)) {
                return -1;
            }
            final IItem item = MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short)quantity);
            if (item == null) {
                return -1;
            }
            final byte rareness = 1;
            Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : 从抽奖中获得，大家恭喜他（她）吧！！！", item, (byte)0, this.getPlayer().getClient().getChannel()));
            return item.getItemId();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public int gainGachaponItem2(final int id, final int quantity, final String msg, final int 概率) {
        return this.gainGachaponItem2(id, quantity, msg, 概率, 0);
    }
    
    public int gainGachaponItem2(final int id, final int quantity, final String msg, final int 概率, final int 类型) {
        try {
            if (!MapleItemInformationProvider.getInstance().itemExists(id)) {
                return -1;
            }
            final IItem item = MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short)quantity);
            if (概率 > 0 && 类型 > 0) {
                switch (类型) {
                    case 1: {
                        Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : 从抽奖中获得，大家恭喜他（她）吧！！！", item, (byte)0, this.getPlayer().getClient().getChannel()));
                        break;
                    }
                    case 2: {
                        Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : 从抽奖中获得，大家恭喜他（她）吧！！！", item, (byte)0, this.getPlayer().getClient().getChannel()));
                        break;
                    }
                    case 3: {
                        Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : 从抽奖中获得，大家恭喜他（她）吧！！！", item, (byte)0, this.getPlayer().getClient().getChannel()));
                        break;
                    }
                    case 4: {
                        Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : 从抽奖中获得，大家恭喜他（她）吧！！！", item, (byte)0, this.getPlayer().getClient().getChannel()));
                        break;
                    }
                    case 5: {
                        Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : 从抽奖中获得，大家恭喜他（她）吧！！！", item, (byte)0, this.getPlayer().getClient().getChannel()));
                        break;
                    }
                    case 6: {
                        Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : 从抽奖中获得，大家恭喜他（她）吧！！！", item, (byte)0, this.getPlayer().getClient().getChannel()));
                        break;
                    }
                }
            }
            else {
                Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + this.c.getPlayer().getName(), " : 从抽奖中获得，大家恭喜他（她）吧！！！", item, (byte)0, this.getPlayer().getClient().getChannel()));
            }
            return item.getItemId();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public int gainGachaponIte(final int id, final int quantity) {
        return this.gainGachaponItem(id, quantity, this.c.getPlayer().getMap().getStreetName());
    }
    
    public void changeJob(final short job) {
        this.c.getPlayer().changeJob((int)job);
    }
    
    public String getQuestCustomData(final int quest) {
        return this.c.getPlayer().getQuestNAdd(MapleQuest.getInstance(quest)).getCustomData();
    }
    
    public void makeTaintedEquip(final byte slot) {
        final Equip sel = (Equip)this.c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short)slot);
        sel.setStr((short)69);
        sel.setDex((short)69);
        sel.setInt((short)69);
        sel.setLuk((short)69);
        sel.setHp((short)69);
        sel.setMp((short)69);
        sel.setWatk((short)69);
        sel.setMatk((short)69);
        sel.setWdef((short)69);
        sel.setMdef((short)69);
        sel.setAcc((short)69);
        sel.setAvoid((short)69);
        sel.setHands((short)69);
        sel.setSpeed((short)69);
        sel.setJump((short)69);
        sel.setUpgradeSlots((byte)69);
        sel.setViciousHammer((byte)69);
        sel.setEnhance((byte)69);
        this.c.getPlayer().equipChanged();
        this.c.getPlayer().fakeRelog();
    }
    
    public void changeStat(final byte slot, final int type, final int amount) {
        final Equip sel = (Equip)this.c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short)slot);
        switch (type) {
            case 0: {
                sel.setStr((short)amount);
                break;
            }
            case 1: {
                sel.setDex((short)amount);
                break;
            }
            case 2: {
                sel.setInt((short)amount);
                break;
            }
            case 3: {
                sel.setLuk((short)amount);
                break;
            }
            case 4: {
                sel.setHp((short)amount);
                break;
            }
            case 5: {
                sel.setMp((short)amount);
                break;
            }
            case 6: {
                sel.setWatk((short)amount);
                break;
            }
            case 7: {
                sel.setMatk((short)amount);
                break;
            }
            case 8: {
                sel.setWdef((short)amount);
                break;
            }
            case 9: {
                sel.setMdef((short)amount);
                break;
            }
            case 10: {
                sel.setAcc((short)amount);
                break;
            }
            case 11: {
                sel.setAvoid((short)amount);
                break;
            }
            case 12: {
                sel.setHands((short)amount);
                break;
            }
            case 13: {
                sel.setSpeed((short)amount);
                break;
            }
            case 14: {
                sel.setJump((short)amount);
                break;
            }
            case 15: {
                sel.setUpgradeSlots((byte)amount);
                break;
            }
            case 16: {
                sel.setViciousHammer((byte)amount);
                break;
            }
            case 17: {
                sel.setLevel((byte)amount);
                break;
            }
            case 18: {
                sel.setEnhance((byte)amount);
                break;
            }
            case 19: {
                sel.setPotential1((short)amount);
                break;
            }
            case 20: {
                sel.setPotential2((short)amount);
                break;
            }
            case 21: {
                sel.setPotential3((short)amount);
                break;
            }
            case 24: {
                sel.setOwner(this.getText());
                break;
            }
        }
        this.c.getPlayer().equipChanged();
        this.c.getPlayer().fakeRelog();
    }
    
    @Override
    public MapleCharacter getChar() {
        return this.getPlayer();
    }
    
    public static int editEquipById(final MapleCharacter chr, final int max, final int itemid, final String stat, final int newval) {
        return editEquipById(chr, max, itemid, stat, (short)newval);
    }
    
    public static int editEquipById(final MapleCharacter chr, final int max, final int itemid, final String stat, final short newval) {
        if (!MapleItemInformationProvider.getInstance().isEquip(itemid)) {
            return -1;
        }
        final List<IItem> equips = chr.getInventory(MapleInventoryType.EQUIP).listById(itemid);
        final List<IItem> equipped = chr.getInventory(MapleInventoryType.EQUIPPED).listById(itemid);
        if (equips.isEmpty() && equipped.isEmpty()) {
            return 0;
        }
        int edited = 0;
        for (final IItem itm : equips) {
            final Equip e = (Equip)itm;
            if (edited >= max) {
                break;
            }
            ++edited;
            final String s = stat;
            switch (s) {
                case "str": {
                    e.setStr(newval);
                    continue;
                }
                case "dex": {
                    e.setDex(newval);
                    continue;
                }
                case "int": {
                    e.setInt(newval);
                    continue;
                }
                case "luk": {
                    e.setLuk(newval);
                    continue;
                }
                case "watk": {
                    e.setWatk(newval);
                    continue;
                }
                case "matk": {
                    e.setMatk(newval);
                    continue;
                }
                default: {
                    return -2;
                }
            }
        }
        for (final IItem itm : equipped) {
            final Equip e = (Equip)itm;
            if (edited >= max) {
                break;
            }
            ++edited;
            final String s = stat;
            switch (s) {
                case "str": {
                    e.setStr(newval);
                    continue;
                }
                case "dex": {
                    e.setDex(newval);
                    continue;
                }
                case "int": {
                    e.setInt(newval);
                    continue;
                }
                case "luk": {
                    e.setLuk(newval);
                    continue;
                }
                case "watk": {
                    e.setWatk(newval);
                    continue;
                }
                case "matk": {
                    e.setMatk(newval);
                    continue;
                }
                default: {
                    return -2;
                }
            }
        }
        return edited;
    }
    
    public int getzb() {
        return this.c.getPlayer().getSYJF(this.c.getPlayer().getId());
    }
    
    public void gainzb(final int slot) {
        this.c.getPlayer().setSYJF(this.c.getPlayer().getId(), slot);
    }
    
    public int getVPoints() {
        return this.getPlayer().getVPoints();
    }
    
    public void setVPoints(final int vpoints) {
        this.getPlayer().setVPoints(this.getPlayer().getVPoints() + vpoints);
    }
    
    public int getDPoints() {
        return this.getPlayer().getVPoints();
    }
    
    public int getTotalStat(final int itemId) {
        return MapleItemInformationProvider.getInstance().getTotalStat((Equip)MapleItemInformationProvider.getInstance().getEquipById(itemId));
    }
    
    public int getReqLevel(final int itemId) {
        return MapleItemInformationProvider.getInstance().getReqLevel(itemId);
    }
    
    public MapleStatEffect getEffect(final int buff) {
        return MapleItemInformationProvider.getInstance().getItemEffect(buff);
    }
    
    public void killAllMonsters(final int mapid) {
        final MapleMap map = this.c.getChannelServer().getMapFactory().getMap(mapid);
        map.killAllMonsters(false);
    }
    
    public final void killAllMobs() {
        this.c.getPlayer().getMap().killAllMonsters(true);
    }
    
    public final void levelUp() {
        this.c.getPlayer().levelUp();
    }
    
    public void MakeGMItem(final byte slot, final MapleCharacter player) {
        final MapleInventory equip = player.getInventory(MapleInventoryType.EQUIP);
        final Equip eu = (Equip)equip.getItem((short)slot);
        final int item = equip.getItem((short)slot).getItemId();
        final short hand = eu.getHands();
        final byte level = eu.getLevel();
        final Equip nItem = new Equip(item, (short)slot, (byte)0);
        nItem.setStr((short)32767);
        nItem.setDex((short)32767);
        nItem.setInt((short)32767);
        nItem.setLuk((short)32767);
        nItem.setUpgradeSlots((byte)0);
        nItem.setHands(hand);
        nItem.setLevel(level);
        player.getInventory(MapleInventoryType.EQUIP).removeItem((short)slot);
        player.getInventory(MapleInventoryType.EQUIP).addFromDB((IItem)nItem);
    }
    
    public void putKey(final int key, final int type, final int action) {
        this.getPlayer().changeKeybinding(key, (byte)type, action);
        this.getClient().sendPacket(MaplePacketCreator.getKeymap(this.getPlayer().getKeyLayout()));
    }
    
    public int getNaturalStats(final int itemid, final String it) {
        final Map<String, Integer> eqStats = MapleItemInformationProvider.getInstance().getEquipStats(itemid);
        if (eqStats != null && eqStats.containsKey(it)) {
            return ((Integer)eqStats.get(it)).intValue();
        }
        return 0;
    }
    
    public String getLeftPadded(final String in, final char padchar, final int length) {
        return StringUtil.getLeftPaddedStr(in, padchar, length);
    }
    
    public void handleDivorce() {
        if (this.getPlayer().getMarriageId() <= 0) {
            this.sendNext("请确保你有一个婚姻.");
            return;
        }
        final int chz = Find.findChannel(this.getPlayer().getMarriageId());
        if (chz == -1) {
            try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
                PreparedStatement ps = con.prepareStatement("UPDATE queststatus SET customData = ? WHERE characterid = ? AND (quest = ? OR quest = ?)");
                ps.setString(1, "0");
                ps.setInt(2, this.getPlayer().getMarriageId());
                ps.setInt(3, 160001);
                ps.setInt(4, 160002);
                ps.executeUpdate();
                ps.close();
                ps = con.prepareStatement("UPDATE characters SET marriageid = ? WHERE id = ?");
                ps.setInt(1, 0);
                ps.setInt(2, this.getPlayer().getMarriageId());
                ps.executeUpdate();
                ps.close();
                con.close();
            }
            catch (SQLException e) {
                System.err.println("handleDivorce" + e);
                FileoutputUtil.outputFileError("logs/数据库异常.txt", (Throwable)e);
                return;
            }
            this.setQuestRecord(this.getPlayer(), 160001, "0");
            this.setQuestRecord(this.getPlayer(), 160002, "0");
            this.getPlayer().setMarriageId(0);
            this.sendNext("你已经成功离婚了..");
            return;
        }
        if (chz < -1) {
            this.sendNext("请确认您的伴侣已登录.");
            return;
        }
        final MapleCharacter cPlayer = ChannelServer.getInstance(chz).getPlayerStorage().getCharacterById(this.getPlayer().getMarriageId());
        if (cPlayer != null) {
            cPlayer.dropMessage(1, "你的伴侣已经和你离婚了.");
            cPlayer.setMarriageId(0);
            this.setQuestRecord(cPlayer, 160001, "0");
            this.setQuestRecord(this.getPlayer(), 160001, "0");
            this.setQuestRecord(cPlayer, 160002, "0");
            this.setQuestRecord(this.getPlayer(), 160002, "0");
            this.getPlayer().setMarriageId(0);
            this.sendNext("你已经成功离婚了...");
        }
        else {
            this.sendNext("发生错误...");
        }
    }
    
    public void wearEquip(final int itemid, final byte slot) {
        final MapleItemInformationProvider li = MapleItemInformationProvider.getInstance();
        final MapleInventory equip = this.c.getPlayer().getInventory(MapleInventoryType.EQUIPPED);
        final IItem item = li.getEquipById(itemid);
        item.setPosition((short)slot);
        equip.addFromDB(item);
    }
    
    public int parseInt(final String s) {
        return Integer.parseInt(s);
    }
    
    public byte parseByte(final String s) {
        return Byte.parseByte(s);
    }
    
    public short parseShort(final String s) {
        return Short.parseShort(s);
    }
    
    public long parseLong(final String s) {
        return Long.parseLong(s);
    }
    
    public void dragonShoutReward(final int reward) {
        int itemid = 0;
        switch (reward) {
            case 0: {
                itemid = 1102207;
                break;
            }
            case 1: {
                itemid = 1122080;
                break;
            }
            case 2: {
                itemid = 2041213;
                break;
            }
            case 3: {
                itemid = 2022704;
                break;
            }
            default: {
                itemid = 2022704;
                break;
            }
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final MapleInventoryType invtype = GameConstants.getInventoryType(itemid);
        if (!MapleInventoryManipulator.checkSpace(this.c, itemid, 1, "")) {
            return;
        }
        if (invtype.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(itemid) && !GameConstants.isBullet(itemid)) {
            final Equip item = (Equip)(Equip)ii.getEquipById(itemid);
            switch (reward) {
                case 0: {
                    item.setPotential1((short)(-25485));
                    item.setPotential2((short)(-25484));
                    item.setPotential3((short)(-24935));
                    break;
                }
                case 1: {
                    item.setPotential1((short)(-25450));
                    item.setPotential2((short)(-25450));
                    item.setPotential3((short)(-25450));
                    break;
                }
            }
            item.setOwner("Hyperious");
            item.setGMLog("Received from interaction " + this.id + " (" + this.id2 + ") (The Dragon's Shout PQ) on " + FileoutputUtil.CurrentReadable_Time());
            final String name = ii.getName(itemid);
            if (itemid / 10000 == 114 && name != null && name.length() > 0) {
                final String msg = "< " + name + " > has been rewarded.";
                this.c.getPlayer().dropMessage(-1, msg);
                this.c.getPlayer().dropMessage(5, msg);
            }
            MapleInventoryManipulator.addbyItem(this.c, item.copy());
        }
        else {
            MapleInventoryManipulator.addById(this.c, itemid, (short)1, "Hyperious", null, 0L, false, "Received from interaction " + this.id + " (" + this.id2 + ") on " + FileoutputUtil.CurrentReadable_Date());
        }
        this.c.sendPacket(MaplePacketCreator.getShowItemGain(itemid, (short)1, true));
    }
    
    public boolean partyHaveItem(final int itemid, final short quantity) {
        if (this.getPlayer().getParty() == null) {
            return false;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            for (final ChannelServer channel : ChannelServer.getAllInstances()) {
                final MapleCharacter ch = channel.getPlayerStorage().getCharacterById(chr.getId());
                if (ch != null && !ch.haveItem(itemid, (int)quantity)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
    
    public MapleQuest getQuestById(final int questId) {
        return MapleQuest.getInstance(questId);
    }
    
    public int getEquipLevelById(final int itemId) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        return ((Integer)ii.getEquipStats(itemId).get("reqLevel")).intValue();
    }
    
    @Override
    public void 喇叭(final int lx, final String msg) {
        switch (lx) {
            case 1: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(12, this.c.getChannel(), "[全服公告] : " + msg));
                break;
            }
            case 2: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(2, "[全服公告] : " + msg));
                break;
            }
            case 3: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(3, this.c.getChannel(), "[全服公告] : " + msg, true));
                break;
            }
            case 4: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, this.c.getChannel(), "[全服公告] : " + msg, false));
                break;
            }
            case 5: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(11, this.c.getChannel(), "[全服公告] : " + msg));
                break;
            }
        }
    }
    
    @Override
    public void 喇叭(final int lx, final String msg1, final String msg) {
        switch (lx) {
            case 1: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(12, this.c.getChannel(), "[" + msg1 + "] : " + msg));
                break;
            }
            case 2: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(2, "[" + msg1 + "] : " + msg));
                break;
            }
            case 3: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(3, this.c.getChannel(), "[" + msg1 + "] : " + msg, true));
                break;
            }
            case 4: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(9, this.c.getChannel(), "[" + msg1 + "] : " + msg, false));
                break;
            }
            case 5: {
                Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(11, this.c.getChannel(), "[" + msg1 + "] : " + msg));
                break;
            }
        }
    }
    
    public void 机器人喇叭(final String msg) {
        RobotSocket.robbatSpeak(msg);
    }
    
    public void 家族排行榜() {
        this.displayGuildRanks();
    }
    
    public void 等级排行榜() {
        this.showlvl();
    }
    
    public void 金币排行榜() {
        this.showmeso();
    }
    
    public short getSpace(final byte type) {
        return this.getPlayer().getSpace((int)type);
    }
    
    public void removeFromSlot(final int type, final short slot, final short number) {
        this.getPlayer().removeFromSlot(type, slot, number);
    }
    
    public void addFromDrop(final IItem item, final boolean a) {
        this.getPlayer().addFromDrop(item, a);
    }
    
    public void gainEquiPproperty(final int upgr, final int watk, final int matk, final int str, final int dex, final int Int, final int luk, final int hp, final int mp, final int acc, final int avoid) {
        final Equip item = (Equip)this.c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short)1).copy();
        item.setUpgradeSlots((byte)(item.getUpgradeSlots() + upgr));
        item.setWatk((short)(item.getWatk() + watk));
        item.setMatk((short)(item.getMatk() + matk));
        item.setStr((short)(item.getStr() + str));
        item.setDex((short)(item.getDex() + dex));
        item.setInt((short)(item.getInt() + Int));
        item.setLuk((short)(item.getLuk() + luk));
        item.setHp((short)(item.getHp() + hp));
        item.setMp((short)(item.getMp() + mp));
        item.setAcc((short)(byte)(item.getAcc() + acc));
        item.setAvoid((short)(byte)(item.getAvoid() + avoid));
        MapleInventoryManipulator.removeFromSlot(this.getC(), MapleInventoryType.EQUIP, (short)1, (short)1, true);
        MapleInventoryManipulator.addFromDrop(this.getChar().getClient(), (IItem)item, false);
    }
    
    public void 刷新能力值() {
        if (this.c.getPlayer().getRemainingAp() < 0) {
            this.c.getPlayer().setRemainingAp((short)0);
        }
    }
    
    public void 重载事件() {
        final Iterator<ChannelServer> iterator = ChannelServer.getAllInstances().iterator();
        if (iterator.hasNext()) {
            final ChannelServer instance = (ChannelServer)iterator.next();
            instance.reloadEvents();
            return;
        }
    }
    
    public void 重载任务() {
        MapleQuest.clearQuests();
    }
    
    public void 重载商店() {
        MapleShopFactory.getInstance().clear();
    }
    
    public void 重载传送点() {
        PortalScriptManager.getInstance().clearScripts();
    }
    
    public void 重载爆率() {
        MapleMonsterInformationProvider.getInstance().clearDrops();
    }
    
    public void 重载反应堆() {
        ReactorScriptManager.getInstance().clearDrops();
    }
    
    public int getHour() {
        return Calendar.getInstance().get(11);
    }
    
    public int getMin() {
        return Calendar.getInstance().get(12);
    }
    
    public int getSec() {
        return Calendar.getInstance().get(13);
    }
    
    public int 捉鬼任务1() {
        return MapleParty.捉鬼任务地图;
    }
    
    public int 捉鬼任务2() {
        return MapleParty.捉鬼任务频道;
    }
    
    public int 捉鬼任务3() {
        return MapleParty.捉鬼任务坐标X;
    }
    
    public int 捉鬼任务4() {
        return MapleParty.捉鬼任务坐标Y;
    }
    
    public void 即时存档() {
        this.c.getPlayer().saveToDB(true, true);
    }
    
    public final boolean CheckMembersLevel(final int lvl) {
        if (this.getParty() == null) {
            return false;
        }
        for (final MaplePartyCharacter player : this.getParty().getMembers()) {
            if (player.getLevel() <= lvl) {
                continue;
            }
            if (!this.isLeader()) {
                return false;
            }
        }
        return true;
    }
    
    public int 查询在线人数() {
        int count = 0;
        for (final ChannelServer chl : ChannelServer.getAllInstances()) {
            count += chl.getPlayerStorage().getAllCharacters().size();
        }
        return count;
    }
    
    public static int 获取最高玩家等级() {
        int data = 0;
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT MAX(level) as DATA FROM characters WHERE gm = 0");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取最高玩家等级出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }
    
    public static String 获取最高等级玩家名字() {
        String name = "";
        String level = "";
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `level` FROM characters WHERE gm = 0 ORDER BY `level` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    level = rs.getString("level");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取家族名称出错 - 数据库查询失败：" + Ex);
        }
        return String.format("%s", name);
    }
    
    public static String 获取家族名称(final int guildId) {
        String data = "";
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT name as DATA FROM guilds WHERE guildid = ?");
            ps.setInt(1, guildId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取家族名称出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }
    
    public static int 获取最高玩家人气() {
        int data = 0;
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT MAX(fame) as DATA FROM characters WHERE gm = 0");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取最高玩家等级出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }
    
    public static String 获取最高人气玩家名字() {
        String name = "";
        String level = "";
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `fame` FROM characters WHERE gm = 0 ORDER BY `fame` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    level = rs.getString("fame");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取家族名称出错 - 数据库查询失败：" + Ex);
        }
        return String.format("%s", name);
    }
    
    public static int 获取最高玩家金币() {
        int data = 0;
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT MAX(meso) as DATA FROM characters WHERE gm = 0");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取最高玩家等级出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }
    
    public static String 获取最高金币玩家名字() {
        String name = "";
        String level = "";
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT `name`, `meso` FROM characters WHERE gm = 0 ORDER BY `meso` DESC LIMIT 1");
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    level = rs.getString("meso");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取家族名称出错 - 数据库查询失败：" + Ex);
        }
        return String.format("%s", name);
    }
    
    public int 获取自己等级排名() {
        int DATA = 0;
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT rank FROM (SELECT @rownum := @rownum + 1 AS rank, `name`, `level`, `id` FROM characters, (SELECT @rownum := 0) r WHERE gm = 0 ORDER BY `level` DESC) AS T1 WHERE `id` = ?");
            ps.setInt(1, this.c.getPlayer().getId());
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DATA = rs.getInt("rank");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取自己等级排名出错 - 数据库查询失败：" + Ex);
        }
        return DATA;
    }
    
    @Override
    public String getItemName(final int id) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        return ii.getName(id);
    }
    
    public void deleteItem(final int inventorytype) {
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("Select * from inventoryitems where characterid=? and inventorytype=?");
            ps.setInt(1, this.getPlayer().getId());
            ps.setInt(2, inventorytype);
            final ResultSet re = ps.executeQuery();
            MapleInventoryType type = null;
            switch (inventorytype) {
                case 1: {
                    type = MapleInventoryType.EQUIP;
                    break;
                }
                case 2: {
                    type = MapleInventoryType.USE;
                    break;
                }
                case 3: {
                    type = MapleInventoryType.SETUP;
                    break;
                }
                case 4: {
                    type = MapleInventoryType.ETC;
                    break;
                }
                case 5: {
                    type = MapleInventoryType.CASH;
                    break;
                }
            }
            while (re.next()) {
                MapleInventoryManipulator.removeById(this.getC(), type, re.getInt("itemid"), re.getInt("quantity"), true, true);
            }
            re.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {}
    }
    
    public int 获取当前星期() {
        return Calendar.getInstance().get(7);
    }
    
    public int getsg() {
        int sg = 0;
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement limitCheck = con.prepareStatement("SELECT * FROM characters WHERE id=" + cid + "");
            final ResultSet rs = limitCheck.executeQuery();
            if (rs.next()) {
                sg = rs.getInt("sg");
            }
            rs.close();
            limitCheck.close();
            con.close();
        }
        catch (SQLException ex) {}
        return sg;
    }
    
    public void setsg(final int slot) {
        try {
            final int cid = this.getPlayer().getAccountID();
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE characters SET sg =sg+ " + slot + " WHERE id = " + cid + "");
            ps.executeUpdate();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {}
    }
    
    public boolean checkLevelAndItem(final int minLevel, final int maxLevel, final int itemId) {
        return this.checkLevelAndItem(minLevel, maxLevel, itemId, 2);
    }
    
    public boolean checkLevelAndItem(final int minLevel, final int maxLevel, final int itemId, final int minSize) {
        final MapleParty party = this.getPlayer().getParty();
        if (party == null || party.getLeader().getId() != this.getPlayer().getId()) {
            this.getPlayer().dropMessage(5, "您没有队伍 或者 不是队长..");
            return false;
        }
        final int partySize = party.getMembers().size();
        if (partySize < minSize) {
            this.getPlayer().dropMessage(5, "队伍的人数成员不够 必须 " + minSize + " 人才可以开始组队任务，当前队伍人数: " + partySize);
            return false;
        }
        int chrSize = 0;
        for (final MaplePartyCharacter partyPlayer : party.getMembers()) {
            final MapleCharacter player = this.getPlayer().getMap().getCharacterById(partyPlayer.getId());
            if (player == null) {
                this.getPlayer().dropMessage(5, "队伍中的成员 " + partyPlayer.getName() + " 不在线 或者 不在同一地图.");
            }
            else if (player.getLevel() < minLevel || player.getLevel() > maxLevel) {
                this.getPlayer().dropMessage(5, "队伍中的成员 " + partyPlayer.getName() + " 等级不符合要求.等级限制: Lv." + minLevel + "～" + maxLevel);
            }
            else if (!player.haveItem(itemId)) {
                this.getPlayer().dropMessage(5, "队伍中的成员 " + partyPlayer.getName() + " 没有开始组队任务需要的道具.");
            }
            else {
                ++chrSize;
            }
        }
        return partySize == chrSize;
    }
    
    public void deleteAll(final int itemId) {
        MapleInventoryManipulator.removeAllById(this.getClient(), itemId, true);
    }
    
    public MapleCharacter getCharByName(final String name) {
        try {
            return this.getClient().getChannelServer().getPlayerStorage().getCharacterByName(name);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public void SystemOutPrintln(final String text) {
        if (this.c.getPlayer().isGM()) {
            this.c.getPlayer().dropMessage(1, "[Npc_Debug]" + text);
        }
        System.out.println(text);
    }
    
    public void UpdateDropChance(final int chance, final int dropperid, final int itemid) {
        MapleMonsterInformationProvider.getInstance().UpdateDropChance(chance, dropperid, itemid);
    }
    
    public void AddDropData(final int chance, final int dropperid, final int itemid, final int questid) {
        MapleMonsterInformationProvider.getInstance().AddDropData(chance, dropperid, itemid, questid);
    }
    
    public void DeleteDropData(final int dropperid, final int itemid) {
        MapleMonsterInformationProvider.getInstance().DeleteDropData(dropperid, itemid);
    }
    
    public String 显示物品列表种类选择(final int a) {
        final StringBuilder name = new StringBuilder();
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT DISTINCT itemid FROM auctionitems  WHERE inventorytype = " + a + " && world = " + (int)this.c.getPlayer().getWorld() + ";");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final int 物品 = rs.getInt("itemid");
                name.append("#L").append(物品).append("# #v").append(物品).append("# #b#z").append(物品).append("##k#l\r\n");
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("显示物品列表种类选择、出错" + ex.getMessage());
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex2) {}
        }
        name.append("\r\n");
        return name.toString();
    }
    
    public String 显示我上架的物品列表种类选择(final int a) {
        final StringBuilder name = new StringBuilder();
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT DISTINCT itemid FROM  auctionitems  WHERE inventorytype = " + a + " && characterid = " + this.c.getPlayer().getId() + ";");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final int 物品 = rs.getInt("itemid");
                name.append("#L").append(物品).append("# #v").append(物品).append("# #b#t").append(物品).append("##k#l\r\n");
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("显示物品列表种类选择、出错" + ex.getMessage());
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex2) {}
        }
        name.append("\r\n");
        return name.toString();
    }
    
    public String 显示物品列表详细(final int a) {
        final StringBuilder name = new StringBuilder();
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM auctionitems WHERE itemid = " + a + " && world = " + (int)this.c.getPlayer().getWorld() + " order by price asc;");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final int 序号 = rs.getInt("id");
                final int 物品 = rs.getInt("itemid");
                final int 价格 = rs.getInt("price");
                final int 数量 = rs.getInt("quantity");
                if (rs.getInt("inventorytype") == 1) {
                    name.append("#L").append(序号).append("# #v").append(物品).append("# #b#t").append(物品).append("##k 价格 : #r").append(价格).append("#k#l\r\n");
                }
                else {
                    name.append("#L").append(序号).append("# #v").append(物品).append("# #b#z").append(物品).append("##k 价格 : #r").append(价格).append("#k   数量 :  #b").append(数量).append("#k#l\r\n");
                }
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("显示物品列表详细、出错" + ex.getMessage());
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex2) {}
        }
        name.append("\r\n");
        return name.toString();
    }
    
    public String 显示我的物品列表详细(final int a) {
        final StringBuilder name = new StringBuilder();
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM auctionitems WHERE itemid = " + a + " && characterid = " + this.c.getPlayer().getId() + "  &&  world = " + (int)this.c.getPlayer().getWorld() + " order by price asc");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final int 序号 = rs.getInt("id");
                final int 物品 = rs.getInt("itemid");
                final int 价格 = rs.getInt("price");
                final int 数量 = rs.getInt("quantity");
                if (rs.getInt("inventorytype") == 1) {
                    name.append("#L").append(序号).append("# #v").append(物品).append("# #b#t").append(物品).append("##k 价格 : #r").append(价格).append("#k#l\r\n");
                }
                else {
                    name.append("#L" + 序号 + "# #v" + 物品 + "# #b#t" + 物品 + "##k 价格 : #r" + 价格 + "#k   数量 :  #b" + 数量 + "#k#l\r\n");
                }
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("显示物品列表详细、出错" + ex.getMessage());
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex2) {}
        }
        name.append("\r\n");
        return name.toString();
    }
    
    public String 显示物品数据详细(final int a) {
        final StringBuilder name = new StringBuilder();
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM auctionitems WHERE id = " + a + "");
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                final int 物品 = rs.getInt("itemid");
                name.append("#b物品: #v").append(物品).append("# #b#t").append(物品).append("##k\r\n");
                final int 卖家 = rs.getInt("characterid");
                name.append("#b卖家: #r").append(this.取角色名字(卖家)).append("#k\r\n");
                final int 售价 = rs.getInt("price");
                name.append("#b售价: #r").append(售价).append("#k #b#k\r\n");
                final int 数量 = rs.getInt("quantity");
                name.append("#b数量: #r").append(数量).append("#k\r\n");
                final String 上架时间 = rs.getString("shijian");
                name.append("#b上架时间: #r").append(上架时间).append("#k\r\n");
                if (物品 < 2000000) {
                    final int 攻击力 = rs.getInt("watk");
                    if (攻击力 > 0) {
                        name.append("#b攻击力 : #r").append(攻击力).append("#k\r\n");
                    }
                    final int 魔法力 = rs.getInt("matk");
                    if (魔法力 > 0) {
                        name.append("#b魔法力 : #r").append(魔法力).append("#k\r\n");
                    }
                    final int 物理防御 = rs.getInt("wdef");
                    if (物理防御 > 0) {
                        name.append("#b物理防御 : #r").append(物理防御).append("#k\r\n");
                    }
                    final int 魔法防御 = rs.getInt("mdef");
                    if (魔法防御 > 0) {
                        name.append("#b魔法防御 : #r").append(魔法防御).append("#k\r\n");
                    }
                    final int 力量 = rs.getInt("str");
                    if (力量 > 0) {
                        name.append("#b力量 : #r").append(力量).append("#k\r\n");
                    }
                    final int 敏捷 = rs.getInt("dex");
                    if (敏捷 > 0) {
                        name.append("#b敏捷 : #r").append(敏捷).append("#k\r\n");
                    }
                    final int 智力 = rs.getInt("_int");
                    if (智力 > 0) {
                        name.append("#b智力 : #r").append(智力).append("#k\r\n");
                    }
                    final int 运气 = rs.getInt("luk");
                    if (运气 > 0) {
                        name.append("#b运气 : #r").append(运气).append("#k\r\n");
                    }
                    final int 生命值 = rs.getInt("hp");
                    if (生命值 > 0) {
                        name.append("#b生命值 : #r").append(生命值).append("#k\r\n");
                    }
                    final int 魔力值 = rs.getInt("mp");
                    if (魔力值 > 0) {
                        name.append("#b魔力值 : #r").append(魔力值).append("#k\r\n");
                    }
                    final int 命中 = rs.getInt("acc");
                    if (命中 > 0) {
                        name.append("#b命中 : #r").append(命中).append("#k\r\n");
                    }
                    final int 回避 = rs.getInt("avoid");
                    if (回避 > 0) {
                        name.append("#b回避 : #r").append(回避).append("#k\r\n");
                    }
                    final int 手技 = rs.getInt("hands");
                    if (手技 > 0) {
                        name.append("#b手技 : #r").append(手技).append("#k\r\n");
                    }
                    final int 移速 = rs.getInt("speed");
                    if (移速 > 0) {
                        name.append("#b移速 : #r").append(移速).append("#k\r\n");
                    }
                    final int 跳跃 = rs.getInt("jump");
                    if (跳跃 > 0) {
                        name.append("#b跳跃 : #r").append(跳跃).append("#k\r\n");
                    }
                    final int 可升级次数 = rs.getInt("upgradeslots");
                    if (可升级次数 > 0) {
                        name.append("#b可升级次数 : #r").append(可升级次数).append("#k\r\n");
                    }
                    final int 已升级次数 = rs.getInt("level");
                    if (已升级次数 > 0) {
                        name.append("#b已升级次数 : #r").append(已升级次数).append("#k\r\n");
                    }
                    final int 锤子次数 = rs.getInt("ViciousHammer");
                    if (锤子次数 > 0) {
                        name.append("#b锤子次数 : #r").append(锤子次数).append("#k\r\n");
                    }
                    final String 名字 = rs.getString("owner");
                    if (名字 != null) {
                        name.append("#b星星潜能等级 : #r").append(名字).append("#k\r\n");
                    }
                }
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("显示物品列表详细、出错" + ex.getMessage());
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex2) {}
        }
        name.append("\r\n");
        return name.toString();
    }
    
    public void 添加道具到拍卖行(final int a1, final int a2, final int a3, final int a4, final String a5, final String a6, final int a7, final int a8, final int a9, final String a10, final int a11, final int a12, final int a13, final int a14, final int a15, final int a16, final int a17, final int a18, final int a19, final int a20, final int a21, final int a22, final int a23, final int a24, final int a25, final int a26, final int a27, final int a28) {
        MapleItemInformationProvider.getInstance();
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (ii.isQuestItem(a2)) {
            this.c.getPlayer().dropMessage(1, "任务物品，无法上架。");
            this.c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            try (final PreparedStatement ps = con.prepareStatement("INSERT INTO auctionitems (characterid , price ,itemid ,inventorytype ,quantity ,owner ,GM_Log ,uniqueid ,flag ,expiredate ,sender ,upgradeslots ,level ,str ,dex ,_int ,luk ,hp ,mp ,watk ,matk ,wdef ,mdef ,acc ,avoid ,hands ,speed ,jump, world , ViciousHammer) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
                ps.setInt(1, this.getPlayer().getId());
                ps.setInt(2, a1);
                ps.setInt(3, a2);
                ps.setInt(4, a3);
                ps.setInt(5, a4);
                ps.setString(6, a5);
                ps.setString(7, a6);
                ps.setInt(8, a7);
                ps.setByte(9, (byte)a8);
                ps.setLong(10, (long)a9);
                ps.setString(11, a10);
                ps.setInt(12, a11);
                ps.setInt(13, a12);
                ps.setInt(14, a13);
                ps.setInt(15, a14);
                ps.setInt(16, a15);
                ps.setInt(17, a16);
                ps.setInt(18, a17);
                ps.setInt(19, a18);
                ps.setInt(20, a19);
                ps.setInt(21, a20);
                ps.setInt(22, a21);
                ps.setInt(23, a22);
                ps.setInt(24, a23);
                ps.setInt(25, a24);
                ps.setInt(26, a25);
                ps.setInt(27, a26);
                ps.setInt(28, a27);
                ps.setInt(29, (int)this.getPlayer().getWorld());
                ps.setInt(30, a28);
                ps.executeUpdate();
                ps.close();
            }
        }
        catch (SQLException ex) {
            System.out.println("添加道具到拍卖行" + ex);
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex2) {}
        }
    }
    
    public void 购买拍卖行装备(final int a) {
        if (this.物品存在(a) > 0) {
            Connection con = null;
            try {
                con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
                try (final PreparedStatement ps = con.prepareStatement("SELECT * FROM auctionitems WHERE id = " + a + "");
                     final ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        if (rs.getInt("inventorytype") == 1) {
                            final int 物品 = rs.getInt("itemid");
                            final int 攻击力 = rs.getInt("watk");
                            final int 魔法力 = rs.getInt("matk");
                            final int 物理防御 = rs.getInt("wdef");
                            final int 魔法防御 = rs.getInt("mdef");
                            final int 力量 = rs.getInt("str");
                            final int 敏捷 = rs.getInt("dex");
                            final int 智力 = rs.getInt("_int");
                            final int 运气 = rs.getInt("luk");
                            final int 生命值 = rs.getInt("hp");
                            final int 魔力值 = rs.getInt("mp");
                            final int 命中 = rs.getInt("acc");
                            final int 回避 = rs.getInt("avoid");
                            final int 手技 = rs.getInt("hands");
                            final int 移速 = rs.getInt("speed");
                            final int 跳跃 = rs.getInt("jump");
                            final int 可升级次数 = rs.getInt("upgradeslots");
                            final int 已升级次数 = rs.getInt("level");
                            final String 名字 = rs.getString("owner");
                            final int 锤子 = rs.getInt("ViciousHammer");
                            this.给属性装备(物品, 可升级次数, 力量, 敏捷, 智力, 运气, 生命值, 魔力值, 攻击力, 魔法力, 物理防御, 魔法防御, 回避, 命中, 跳跃, 移速, 0L, 已升级次数, 手技, 名字, 锤子);
                        }
                        else {
                            final int 物品 = rs.getInt("itemid");
                            this.gainItem(物品, (short)rs.getInt("quantity"));
                        }
                    }
                    rs.close();
                    ps.close();
                }
                con.close();
            }
            catch (SQLException ex) {
                System.err.println("购买拍卖行装备、出错" + ex.getMessage());
            }
            finally {
                try {
                    if (con != null && !con.isClosed()) {
                        con.close();
                    }
                }
                catch (SQLException ex2) {}
            }
        }
    }
    
    public void 给属性装备(final int id, final int sj, final int str, final int dex, final int Int, final int luk, final int hp, final int mp, final int watk, final int matk, final int wdef, final int mdef, final int hb, final int mz, final int ty, final int yd, final long 给予时间, final int lv, final int hands, final String owner, final int 锤子) {
        MapleItemInformationProvider.getInstance();
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final Equip item = (Equip)(Equip)ii.getEquipById(id);
        item.setUpgradeSlots((byte)(short)sj);
        if (lv > 0) {
            item.setLevel((byte)lv);
        }
        if (hands > 0) {
            item.setHands((short)hands);
        }
        if (str > 0) {
            item.setStr((short)str);
        }
        if (dex > 0) {
            item.setDex((short)dex);
        }
        if (luk > 0) {
            item.setLuk((short)luk);
        }
        if (Int > 0) {
            item.setInt((short)Int);
        }
        if (hp > 0) {
            item.setHp((short)hp);
        }
        if (mp > 0) {
            item.setMp((short)mp);
        }
        if (watk > 0) {
            item.setWatk((short)watk);
        }
        if (matk > 0) {
            item.setMatk((short)matk);
        }
        if (wdef > 0) {
            item.setWdef((short)wdef);
        }
        if (mdef > 0) {
            item.setMdef((short)mdef);
        }
        if (hb > 0) {
            item.setAvoid((short)hb);
        }
        if (mz > 0) {
            item.setAcc((short)mz);
        }
        if (ty > 0) {
            item.setJump((short)ty);
        }
        if (yd > 0) {
            item.setSpeed((short)yd);
        }
        if (给予时间 > 0L) {
            item.setExpiration(System.currentTimeMillis() + 给予时间 * 60L * 60L * 1000L);
        }
        if (owner != null) {
            item.setOwner(owner);
        }
        if (锤子 > 0) {
            item.setViciousHammer((byte)锤子);
        }
        MapleInventoryManipulator.addbyItem(this.c, item.copy());
        this.c.sendPacket(MaplePacketCreator.getShowItemGain(id, (short)1, true));
    }
    
    public void 删除购买的道具(final int a) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT * FROM auctionitems WHERE id = " + a + "");
            rs = ps.executeQuery();
            while (rs.next()) {
                final String sqlstr = " delete from auctionitems where id = '" + a + "' ";
                ps.executeUpdate(sqlstr);
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {}
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex2) {}
        }
    }
    
    public int 物品价格(final int id) {
        int data = 0;
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT price as DATA FROM auctionitems WHERE id = ?");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("物品价格、出错");
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex) {}
        }
        return data;
    }
    
    public int 物品存在(final int id) {
        int data = 0;
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM auctionitems WHERE id = ?");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ++data;
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("物品存在、出错");
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex) {}
        }
        return data;
    }
    
    public int 物品卖家(final int id) {
        int data = 0;
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            try (final PreparedStatement ps = con.prepareStatement("SELECT characterid as DATA FROM auctionitems WHERE id = ?")) {
                ps.setInt(1, id);
                try (final ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        data = rs.getInt("DATA");
                    }
                    rs.close();
                }
                ps.close();
            }
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("物品卖家、出错");
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex) {}
        }
        return data;
    }
    
    public void 初始化拍卖储存(final int a) {
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("INSERT INTO auctionpoint (characterid, point) values (?,?);");
            ps.setInt(1, a);
            ps.setInt(2, 0);
            ps.executeUpdate();
            ps.close();
            con.close();
        }
        catch (Exception ex) {}
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex2) {}
        }
    }
    
    public void 新增拍卖存储金币(final int a, final int b) {
        if (this.判断拍卖存储金币(a) == 0) {
            this.初始化拍卖储存(a);
        }
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            try (final PreparedStatement ps = con.prepareStatement("UPDATE auctionpoint SET point = point + " + b + " WHERE characterid =" + a + "")) {
                ps.executeUpdate();
                ps.close();
            }
            con.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex2) {}
        }
    }
    
    public int 判断拍卖存储金币(final int a) {
        int data = 0;
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT point as DATA FROM auctionpoint WHERE characterid = ?");
            ps.setInt(1, a);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("判断拍卖存储金币、出错");
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex) {}
        }
        return data;
    }
    
    public void 清空拍卖存储金币() {
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            try (final PreparedStatement ps = con.prepareStatement("UPDATE auctionpoint SET point = 0 WHERE characterid =" + this.c.getPlayer().getId() + "")) {
                ps.executeUpdate();
                ps.close();
            }
            con.close();
        }
        catch (Exception ex) {}
    }
    
    public void 增加拍卖出售栏(final int a) {
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            try (final PreparedStatement ps = con.prepareStatement("UPDATE auctionpoint SET selling = selling + " + a + " WHERE characterid =" + this.c.getPlayer().getId() + ";")) {
                ps.executeUpdate();
                ps.close();
            }
            con.close();
        }
        catch (Exception ex) {}
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex2) {}
        }
    }
    
    public int 判断拍卖出售位数量() {
        int data = 0;
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT selling  as DATA FROM auctionpoint WHERE characterid = ?;");
            ps.setInt(1, this.getPlayer().getId());
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data += rs.getInt("DATA");
                }
                else {
                    final PreparedStatement psu = con.prepareStatement("insert into auctionpoint (characterid,point, selling) VALUES (?, ?, ?)");
                    psu.setInt(1, this.getPlayer().getId());
                    psu.setInt(2, 0);
                    psu.setInt(3, 10);
                    psu.executeUpdate();
                    psu.close();
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("判断拍卖出售位数量、出错");
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex) {}
        }
        return data;
    }
    
    public int 已经使用拍卖栏位() {
        int data = 0;
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            try (final PreparedStatement ps = con.prepareStatement("SELECT * FROM auctionitems WHERE characterid = ?")) {
                ps.setInt(1, this.getPlayer().getId());
                final ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ++data;
                }
                rs.close();
            }
            con.close();
        }
        catch (SQLException ex) {}
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex2) {}
        }
        return data;
    }
    
    public String 取角色名字(final int a) {
        return 角色名字(a);
    }
    
    public static String 角色名字(final int id) {
        String data = "";
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT name as DATA FROM characters WHERE id = ?");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("角色名字、出错");
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex) {}
        }
        return data;
    }
    
    public void cleanItemBySlot(final int slot, final int type, final int quantity) {
        MapleInventoryManipulator.removeFromSlot(this.c, MapleInventoryType.getByType((byte)type), (short)slot, (short)quantity, true);
    }
    
    public static String SN取出售(final int id) {
        String data = "";
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? && channel = 1");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex) {}
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }
    
    public static String SN取库存(final int id) {
        String data = "";
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? &&  channel = 2");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex) {}
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }
    
    public void 刷新地图() {
        final boolean custMap = true;
        final int mapid = this.c.getPlayer().getMapId();
        final MapleMap map = custMap ? this.c.getPlayer().getClient().getChannelServer().getMapFactory().getMap(mapid) : this.c.getPlayer().getMap();
        if (this.c.getPlayer().getClient().getChannelServer().getMapFactory().destroyMap(mapid)) {
            final MapleMap newMap = this.c.getPlayer().getClient().getChannelServer().getMapFactory().getMap(mapid);
            final MaplePortal newPor = newMap.getPortal(0);
            final LinkedHashSet<MapleCharacter> mcs = new LinkedHashSet(map.getCharacters());
            for (final MapleCharacter m : mcs) {
                int x = 0;
                while (x < 5) {
                    try {
                        m.changeMap(newMap, newPor);
                    }
                    catch (Throwable t) {
                        ++x;
                        continue;
                    }
                    break;
                }
            }
        }
    }
    
    public void 刷新地图(final int a) {
        final boolean custMap = true;
        final int mapid = a;
        final MapleMap map = custMap ? this.c.getPlayer().getClient().getChannelServer().getMapFactory().getMap(mapid) : this.c.getPlayer().getMap();
        if (this.c.getPlayer().getClient().getChannelServer().getMapFactory().destroyMap(mapid)) {
            final MapleMap newMap = this.c.getPlayer().getClient().getChannelServer().getMapFactory().getMap(mapid);
            final MaplePortal newPor = newMap.getPortal(0);
            final LinkedHashSet<MapleCharacter> mcs = new LinkedHashSet(map.getCharacters());
            for (final MapleCharacter m : mcs) {
                int x = 0;
                while (x < 5) {
                    try {
                        m.changeMap(newMap, newPor);
                    }
                    catch (Throwable t) {
                        ++x;
                        continue;
                    }
                    break;
                }
            }
        }
    }
    
    public static String SN取折扣(final int id) {
        String data = "";
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? &&  channel = 3");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex) {}
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }
    
    public static String SN取限购(final int id) {
        String data = "";
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? &&  channel = 4");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            }
            catch (SQLException ex) {}
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }
    
    public int 物品叠加数量(final int id) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        return ii.getSlotMax(this.c, id);
    }
    
    public static String SN取类型(final int id) {
        String data = "";
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? &&  channel = 5");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
                rs.close();
            }
            
        }
        catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        finally {
            DBConPool.cleanUP(null, null, con);
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }
    
    public int sql_Update(final String sql, final Object... data) {
        return DatabaseScriptAPI.SQLUpdate(sql, data);
    }
    
    public int sql_Insert(final String sql, final Object... data) {
        return DatabaseScriptAPI.SQLInsert(sql, data);
    }
    
    public List<Map<String, Object>> sql_Select(final String sql, final Object... data) {
        return DatabaseScriptAPI.SQLSelect(sql, data);
    }
    
    public List<Integer> getMobInMap(final int mobid) {
        final List<Integer> list = (List<Integer>)new ArrayList();
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("select * from mobinmap where mobid = ?");
            ps.setInt(1, mobid);
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(Integer.valueOf(rs.getInt("mapid")));
                }
                rs.close();
            }
            ps.close();
            con.close();
            return list;
        }
        catch (Exception Ex) {
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)Ex);
            return null;
        }
    }
    
    public static String 角色ID取名字(final int id) {
        String data = "";
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT name as DATA FROM characters WHERE id = ?");
            ps.setInt(1, id);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }
    
    public static int getMRJF(final int id) {
        int jf = 0;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("select * from paymoney1 where characterid =?");
            ps.setInt(1, id);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jf = rs.getInt("mrjf");
            }
            else {
                final PreparedStatement psu = con.prepareStatement("insert into paymoney1 (characterid,mrjf) VALUES (?,?)");
                psu.setInt(1, id);
                psu.setInt(2, 0);
                psu.executeUpdate();
                psu.close();
            }
            ps.close();
            rs.close();
        }
        catch (SQLException ex) {
            System.err.println("每日积分读取发生错误: " + ex);
        }
        return jf;
    }
    
    public static int 角色名字取Id(final String name) {
        int data = 0;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("SELECT id as DATA FROM characters WHERE name = ?");
            ps.setString(1, name);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
                rs.close();
            }
            ps.close();
        }
        catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }
    
    public int getmoneyb() {
        return this.c.getPlayer().getLJJF(this.c.getPlayer().getId());
    }
    
    public void setmoneyb(final int slot) {
        this.c.getPlayer().setLJJF(this.c.getPlayer().getId(), slot);
    }
    
    public String ms() {
        return Game.服务端名字;
    }
    
    public int 召唤扎昆() {
        int i = 0;
        this.c.getPlayer().getMap().spawnFakeMonsterOnGroundBelow(MapleLifeFactory.getMonster(8800000), this.c.getPlayer().getPosition());
        for (i = 8800003; i <= 8800010; ++i) {
            this.c.getPlayer().getMap().spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(i), this.c.getPlayer().getPosition());
        }
        return 1;
    }
    
    public int 召唤黑龙() {
        this.c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.musicChange("Bgm14/HonTale"));
        this.c.getPlayer().getMap().spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(8810026), this.c.getPlayer().getPosition());
        return 1;
    }
    
    public int 召唤闹钟() {
        final MapleMonster mob0 = MapleLifeFactory.getMonster(8500001);
        this.c.getPlayer().getMap().spawnMonsterOnGroundBelow(mob0, this.c.getPlayer().getPosition());
        return 1;
    }
    
    public final void 全服漂浮喇叭(final String msg, final int itemId) {
        int ret = 0;
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (final MapleCharacter mch : cserv.getPlayerStorage().getAllCharacters()) {
                mch.startMapEffect(msg, itemId);
                ++ret;
            }
        }
    }
    
    public boolean isEligibleName(final String t) {
        return MapleCharacterUtil.canCreateChar(t, this.getPlayer().isGM()) && (!LoginInformationProvider.getInstance().isForbiddenName(t) || this.getPlayer().isGM());
    }
    
    public void displayBossLogRanks(final String bossName) {
        this.c.sendPacket(MaplePacketCreator.showBossLogRanks(this.npc, MapleGuildRanking.getInstance().getBossLogRank(bossName, (int)this.c.getPlayer().getWorld())));
    }
    
    public boolean hasgy() {
        return World.hasMerchant(this.c.getPlayer().getAccountID());
    }
    
    public int getFZ4(final String log1) {
        return this.c.getPlayer().getFZ4(log1);
    }
    
    public void setFZ4(final String log1, final int a) {
        this.c.getPlayer().setFZ4(log1, a);
    }
    public void 开启吸怪() {
        MapleMap map = c.getPlayer().getMap();
        if(map.getMonsterSpawnner().right != null){
            if(map.getMonsterSpawnner().left != c.getPlayer().getId()){
                c.getPlayer().getClient().sendPacket(MaplePacketCreator.serverNotice(1, "已经有人在这个地图吸怪了！\r\n请换个地图再试！"));
                return;
            }
        }
        for (final MapleMonster monster : map.getAllMonsters()) {
            if(!monster.getStats().isBoss()){
                map.setMonsterspawn(c.getPlayer().getPosition(),c.getPlayer().getId());
                c.getPlayer().吸怪特权 = 1;
                monster.setPosition(map.getMonsterSpawnner().right);
                c.getPlayer().set吸怪Res(c.getPlayer().getLastRes());
                c.getPlayer().getMap().broadcastMessage(MobPacket.moveMonster(false, 0, 0, monster.getObjectId(), monster.getPosition(), map.getMonsterSpawnner().right, c.getPlayer().get吸怪Res()));
                
            }
        }
    }
    
    public void 关闭吸怪() {
        MapleMap map = c.getPlayer().getMap();
        if(map.getMonsterSpawnner().right != null){
            if(map.getMonsterSpawnner().left == c.getPlayer().getId()){
                c.getPlayer().吸怪特权 = 0;
                c.getPlayer().jishu = 0;
                map.setMonsterspawn(null,-1);
            }else{
                c.getPlayer().getClient().sendPacket(MaplePacketCreator.serverNotice(1, "已经有人在这个地图吸怪了！\r\n不是本人无法关闭！"));
                return;
            }
        }
    }
    public String 查询吸怪信息() {
        MapleMap map = c.getPlayer().getMap();
        int num = 0;
        final StringBuilder name = new StringBuilder();
        if (num == 0) {
            name.append("当前角色:"+ c.getPlayer().getName() +"\r\n");
            name.append("\t\t\t吸怪地图:"+ map.getId()+"["+map.getMapName() +"]\r\n");
            name.append("\t\t\t吸怪坐标:"+ map.getMonsterSpawnner().right+"\r\n");
            name.append("\t\t\t人物坐标:"+ c.getPlayer().getPosition() +"\r\n");
        }
        if (name.length() > 0) {
            return name.toString();
        }else{
            return "暂无吸怪信息！";
        }
    }
}
