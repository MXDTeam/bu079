package scripting;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import database.DBConPool;
import client.inventory.ItemFlag2;
import client.inventory.ItemFlag;
import gui.进阶BOSS.Mushplotact;
import java.sql.SQLException;
import client.inventory.MapleRing;
import java.util.Map;
import java.util.EnumMap;
import client.MapleStat;
import client.inventory.Item;
import client.inventory.MapleInventory;
import java.util.ArrayList;
import server.RandomRewards;
import client.messages.CommandProcessor;
import constants.ServerConstants.CommandType;
import handling.channel.handler.InterServerHandler;
import gui.进阶BOSS.黑龙BOSS线程;
import server.maps.MapleMapFactory;
import server.maps.SavedLocationType;
import server.events.MapleEventType;
import server.events.MapleEvent;
import server.maps.Event_DojoAgent;
import client.ISkill;
import server.life.MapleMonster;
import tools.packet.PetPacket;
import tools.packet.UIPacket;
import client.SkillFactory;
import java.util.List;
import handling.world.MapleParty;
import handling.world.guild.MapleGuild;
import handling.world.World.Guild;
import handling.world.World.Broadcast;
import client.inventory.MaplePet;
import client.inventory.MapleInventoryIdentifier;
import constants.ItemConstants.类型;
import tools.FileoutputUtil;
import client.inventory.Equip;
import server.maps.MapleMapObject;
import server.quest.MapleQuest;
import client.MapleQuestStatus;
import server.MapleInventoryManipulator;
import client.inventory.IItem;
import server.life.MapleLifeFactory;
import java.awt.geom.Point2D;
import java.awt.Point;
import server.Randomizer;
import handling.channel.ChannelServer;
import constants.GameConstants;
import tools.MaplePacketCreator;
import server.maps.MapleMap;
import server.maps.MapleReactor;
import client.inventory.MapleInventoryType;
import java.util.Iterator;
import handling.world.MaplePartyCharacter;
import client.MapleCharacter;
import server.MapleItemInformationProvider;
import client.MapleClient;

public abstract class AbstractPlayerInteraction
{
    private MapleClient c;
    protected int id;
    protected int id2;
    private static final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
    
    public AbstractPlayerInteraction(final MapleClient c) {
        this.c = c;
    }
    
    public final MapleClient getClient() {
        return this.c;
    }
    
    public final MapleClient getC() {
        return this.c;
    }
    
    public MapleCharacter getChar() {
        return this.getClient().getPlayer();
    }
    
    public int getOneTimeLog(final String bossid) {
        return this.getPlayer().getOneTimeLog(bossid);
    }
    
    public void setOneTimeLog(final String bossid) {
        this.getPlayer().setOneTimeLog(bossid);
    }
    
    public void deleteOneTimeLog(final String bossid) {
        this.getPlayer().deleteOneTimeLog(bossid);
    }
    
    public int getAcLog(final String bossid) {
        return this.getPlayer().getAcLog(bossid);
    }
    
    public int getAcLogS(final String bossid) {
        return this.getPlayer().getAcLogS(bossid);
    }
    
    public final boolean isAllPartyMembersAllowedLevel(final int min, final int max) {
        if (this.getParty() == null) {
            return false;
        }
        for (final MaplePartyCharacter d2 : this.getParty().getMembers()) {
            if (d2.getLevel() >= min && d2.getLevel() <= max) {
                continue;
            }
            return false;
        }
        return true;
    }
    
    public void setAcLog(final String bossid) {
        this.getPlayer().setAcLog(bossid);
    }
    
    public void getEquipBySlot(final int slot) {
        this.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short)slot);
    }
    
    public int getBossLog(final String bossid) {
        return this.getPlayer().getBossLog(bossid);
    }
    
    public int getBossLog1(final String bossid) {
        return this.getPlayer().getBossLog1(bossid);
    }
    
    public int getBossLog1(final String bossid, final int number) {
        return this.getPlayer().getBossLog1(bossid, number);
    }
    
    public void setBossLog1(final String bossid) {
        this.getPlayer().setBossLog1(bossid);
    }
    
    public void 断线() {
        this.c.getSession().close();
    }
    
    public void setBossLog1(final String bossid, final int number) {
        this.getPlayer().setBossLog1(bossid, number);
    }
    
    public int getCombat() {
        return this.getPlayer().getCombat();
    }
    
    public boolean isCash(final int Itemid) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        return ii.isCash(Itemid);
    }
    
    public void forceTrigger(final int mapId, final int reactorId) {
        final MapleMap map = this.getClient().getChannelServer().getMapFactory().getMap(mapId);
        for (final MapleReactor reactor : map.getAllReactorsThreadsafe()) {
            if (reactor.getReactorId() == reactorId) {
                reactor.setState((byte)1);
                reactor.forceTrigger();
                break;
            }
        }
    }
    
    public void openWeb(final String web) {
        this.c.getSession().write(MaplePacketCreator.openWeb(web));
    }
    
    public void setBossLog(final String bossid) {
        this.getPlayer().setBossLog(bossid);
    }
    
    public void setAccountidBossLog(final String bossid) {
        this.getPlayer().setAccountidBossLog(bossid);
    }
    
    public void setAccountidLog(final String bossid) {
        this.getPlayer().setAccountidLog(bossid);
    }
    
    public void setAccountidBossLog(final String bossid, final int number) {
        this.getPlayer().setAccountidBossLog(bossid, number);
    }
    
    public void setAccountidLog(final String bossid, final int number) {
        this.getPlayer().setAccountidLog(bossid, number);
    }
    
    public int getExpNeededForLevel(final int level) {
        return GameConstants.getExpNeededForLevel(level);
    }
    
    public final void givePartyBossLog(final String bossid) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.setBossLog(bossid);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.setBossLog(bossid);
            }
        }
    }
    
    public final void givePartyAccountidBossLog(final String bossid) {
        this.givePartyAccountidBossLog(bossid, 1);
    }
    
    public final void givePartyAccountidLog(final String bossid) {
        this.givePartyAccountidLog(bossid, 1);
    }
    
    public final void givePartyAccountidBossLog(final String bossid, final int number) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.getPlayer().setAccountidBossLog(bossid, number);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.setAccountidBossLog(bossid, number);
            }
        }
    }
    
    public final void givePartyAccountidLog(final String bossid, final int number) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.getPlayer().setAccountidLog(bossid, number);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.setAccountidLog(bossid, number);
            }
        }
    }
    
    public final void gainMembersPQ(final String pqName, final int num) {
        if (this.getParty() == null) {
            return;
        }
        for (final MaplePartyCharacter partyCharacter : this.getParty().getMembers()) {
            final MapleCharacter player = this.getChannelServer().getPlayerStorage().getCharacterById(partyCharacter.getId());
            if (player == null) {
                continue;
            }
            player.setBossLog(pqName);
        }
    }
    
    public final boolean getPartyBossLog(final String bossid, final int num) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            final int bossnum = this.getBossLog(bossid);
            return bossnum <= num;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                final int bossnum2 = curChar.getBossLog(bossid);
                if (bossnum2 > num) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    public int getBosslog(final String bossid) {
        return this.getPlayer().getBossLog(bossid);
    }
    
    public void setBosslog(final String bossid) {
        this.getPlayer().setBossLog(bossid);
    }
    
    public boolean beibao(final int A) {
        return (this.c.getPlayer().getInventory(MapleInventoryType.EQUIP).getNextFreeSlot() > -1 && A == 1) || (this.c.getPlayer().getInventory(MapleInventoryType.USE).getNextFreeSlot() > -1 && A == 2) || (this.c.getPlayer().getInventory(MapleInventoryType.SETUP).getNextFreeSlot() > -1 && A == 3) || (this.c.getPlayer().getInventory(MapleInventoryType.ETC).getNextFreeSlot() > -1 && A == 4) || (this.c.getPlayer().getInventory(MapleInventoryType.CASH).getNextFreeSlot() > -1 && A == 5);
    }
    
    public boolean beibao(final int A, final int kw) {
        return (this.c.getPlayer().getInventory(MapleInventoryType.EQUIP).getNextFreeSlot() > kw && A == 1) || (this.c.getPlayer().getInventory(MapleInventoryType.USE).getNextFreeSlot() > kw && A == 2) || (this.c.getPlayer().getInventory(MapleInventoryType.SETUP).getNextFreeSlot() > kw && A == 3) || (this.c.getPlayer().getInventory(MapleInventoryType.ETC).getNextFreeSlot() > kw && A == 4) || (this.c.getPlayer().getInventory(MapleInventoryType.CASH).getNextFreeSlot() > kw && A == 5);
    }
    
    public final ChannelServer getChannelServer() {
        return this.getClient().getChannelServer();
    }
    
    public final MapleCharacter getPlayer() {
        return this.getClient().getPlayer();
    }
    
    public final EventManager getEventManager(final String event) {
        return this.getClient().getChannelServer().getEventSM().getEventManager(event);
    }
    
    public final EventInstanceManager getEventInstance() {
        return this.getClient().getPlayer().getEventInstance();
    }
    
    public final void warp(final int map) {
        final MapleMap mapz = this.getWarpMap(map);
        try {
            this.getClient().getPlayer().changeMap(mapz, mapz.getPortal(Randomizer.nextInt(mapz.getPortals().size())));
        }
        catch (Exception e) {
            this.getClient().getPlayer().changeMap(mapz, mapz.getPortal(0));
        }
    }
    
    public final void warp_Instanced(final int map) {
        final MapleMap mapz = this.getMap_Instanced(map);
        try {
            this.getClient().getPlayer().changeMap(mapz, mapz.getPortal(Randomizer.nextInt(mapz.getPortals().size())));
        }
        catch (Exception e) {
            this.getClient().getPlayer().changeMap(mapz, mapz.getPortal(0));
        }
    }
    
    public final void instantMapWarp(final int map, final int portal) {
        final MapleMap mapz = this.getWarpMap(map);
        if (portal != 0 && map == this.c.getPlayer().getMapId()) {
            final Point portalPos = new Point(this.c.getPlayer().getMap().getPortal(portal).getPosition());
            this.c.getSession().writeAndFlush(MaplePacketCreator.instantMapWarp((byte)portal));
            this.c.getPlayer().checkFollow();
            this.c.getPlayer().getMap().movePlayer(this.c.getPlayer(), portalPos);
        }
        else {
            this.c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
        }
    }
    
    public final void warp(final int map, final int portal) {
        final MapleMap mapz = this.getWarpMap(map);
        if (portal != 0 && map == this.getClient().getPlayer().getMapId()) {
            final Point portalPos = new Point(this.c.getPlayer().getMap().getPortal(portal).getPosition());
            if (portalPos.distanceSq((Point2D)this.getPlayer().getPosition()) < 90000.0) {
                this.getClient().sendPacket(MaplePacketCreator.instantMapWarp((byte)portal));
                this.getClient().getPlayer().checkFollow();
                this.getClient().getPlayer().getMap().movePlayer(this.c.getPlayer(), portalPos);
            }
            else {
                this.getClient().getPlayer().changeMap(mapz, mapz.getPortal(portal));
            }
        }
        else {
            this.getClient().getPlayer().changeMap(mapz, mapz.getPortal(portal));
        }
    }
    
    public final void warpS(final int map, final int portal) {
        final MapleMap mapz = this.getWarpMap(map);
        this.getClient().getPlayer().changeMap(mapz, mapz.getPortal(portal));
    }
    
    public final void warp(final int map, String portal) {
        final MapleMap mapz = this.getWarpMap(map);
        if (map == 109060000 || map == 109060002 || map == 109060004) {
            portal = mapz.getSnowballPortal();
        }
        if (map == this.getClient().getPlayer().getMapId()) {
            final Point portalPos = new Point(this.c.getPlayer().getMap().getPortal(portal).getPosition());
            if (portalPos.distanceSq((Point2D)this.getPlayer().getPosition()) < 90000.0) {
                this.getClient().getPlayer().checkFollow();
                this.getClient().sendPacket(MaplePacketCreator.instantMapWarp((byte)this.getClient().getPlayer().getMap().getPortal(portal).getId()));
                this.getClient().getPlayer().getMap().movePlayer(this.c.getPlayer(), new Point(this.c.getPlayer().getMap().getPortal(portal).getPosition()));
            }
            else {
                this.getClient().getPlayer().changeMap(mapz, mapz.getPortal(portal));
            }
        }
        else {
            this.getClient().getPlayer().changeMap(mapz, mapz.getPortal(portal));
        }
    }
    
    public final void warpS(final int map, String portal) {
        final MapleMap mapz = this.getWarpMap(map);
        if (map == 109060000 || map == 109060002 || map == 109060004) {
            portal = mapz.getSnowballPortal();
        }
        this.getClient().getPlayer().changeMap(mapz, mapz.getPortal(portal));
    }
    
    public final void warpMap(final int mapid, final String portal) {
        final MapleMap map = this.getMap(mapid);
        for (final MapleCharacter chr : this.getClient().getPlayer().getMap().getCharactersThreadsafe()) {
            chr.changeMap(map, map.getPortal(portal));
        }
    }
    
    public final void warpMap(final int mapid, final int portal) {
        final MapleMap map = this.getMap(mapid);
        for (final MapleCharacter chr : this.getClient().getPlayer().getMap().getCharactersThreadsafe()) {
            chr.changeMap(map, map.getPortal(portal));
        }
    }
    
    public final void playPortalSE() {
        this.getClient().sendPacket(MaplePacketCreator.showOwnBuffEffect(0, 8));
    }
    
    private final MapleMap getWarpMap(final int map) {
        return ChannelServer.getInstance(this.c.getChannel()).getMapFactory().getMap(map);
    }
    
    public final MapleMap getMap() {
        return this.getClient().getPlayer().getMap();
    }
    
    public final MapleMap getMap(final int map) {
        return this.getWarpMap(map);
    }
    
    public final MapleMap getMap_Instanced(final int map) {
        return (this.getClient().getPlayer().getEventInstance() == null) ? this.getMap(map) : this.getClient().getPlayer().getEventInstance().getMapInstance(map);
    }
    
    public void spawnMonster(final int id, final int qty) {
        this.spawnMob(id, qty, new Point(this.c.getPlayer().getPosition()));
    }
    
    public final void spawnMobOnMap(final int id, final int qty, final int x, final int y, final int map) {
        for (int i = 0; i < qty; ++i) {
            this.getMap(map).spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(id), new Point(x, y));
        }
    }
    
    public final void spawnMobOnMap(final int id, final int qty, final int x, final int y, final int map, final long hp) {
        for (int i = 0; i < qty; ++i) {
            this.getMap(map).spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(id), new Point(x, y), hp);
        }
    }
    
    public final void spawnMob(final int id, final int qty, final int x, final int y) {
        this.spawnMob(id, qty, new Point(x, y));
    }
    
    public final void spawnMob(final int id, final int x, final int y) {
        this.spawnMob(id, 1, new Point(x, y));
    }
    
    private void spawnMob(final int id, final int qty, final Point pos) {
        for (int i = 0; i < qty; ++i) {
            this.getClient().getPlayer().getMap().spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(id), pos);
        }
    }
    
    public final void killMob(final int ids) {
        this.getClient().getPlayer().getMap().killMonster(ids);
    }
    
    public final void killAllMob() {
        this.getClient().getPlayer().getMap().killAllMonsters(true);
    }
    
    public final void addHP(final int delta) {
        this.getClient().getPlayer().addHP(delta);
    }
    
    public final int getPlayerStat(final String type) {
        if (type.equals("LVL")) {
            return this.getClient().getPlayer().getLevel();
        }
        if (type.equals("STR")) {
            return this.getClient().getPlayer().getStat().getStr();
        }
        if (type.equals("DEX")) {
            return this.getClient().getPlayer().getStat().getDex();
        }
        if (type.equals("INT")) {
            return this.getClient().getPlayer().getStat().getInt();
        }
        if (type.equals("LUK")) {
            return this.getClient().getPlayer().getStat().getLuk();
        }
        if (type.equals("HP")) {
            return this.getClient().getPlayer().getStat().getHp();
        }
        if (type.equals("MP")) {
            return this.getClient().getPlayer().getStat().getMp();
        }
        if (type.equals("MAXHP")) {
            return this.getClient().getPlayer().getStat().getMaxHp();
        }
        if (type.equals("MAXMP")) {
            return this.getClient().getPlayer().getStat().getMaxMp();
        }
        if (type.equals("RAP")) {
            return this.getClient().getPlayer().getRemainingAp();
        }
        if (type.equals("RSP")) {
            return this.getClient().getPlayer().getRemainingSp();
        }
        if (type.equals("GID")) {
            return this.getClient().getPlayer().getGuildId();
        }
        if (type.equals("GRANK")) {
            return this.getClient().getPlayer().getGuildRank();
        }
        if (type.equals("ARANK")) {
            return this.getClient().getPlayer().getAllianceRank();
        }
        if (type.equals("GM")) {
            return this.getClient().getPlayer().isGM() ? 1 : 0;
        }
        if (type.equals("ADMIN")) {
            return this.getClient().getPlayer().hasGmLevel(5) ? 1 : 0;
        }
        if (type.equals("GENDER")) {
            return this.getClient().getPlayer().getGender();
        }
        if (type.equals("FACE")) {
            return this.getClient().getPlayer().getFace();
        }
        if (type.equals("HAIR")) {
            return this.getClient().getPlayer().getHair();
        }
        return -1;
    }
    
    public final String getName() {
        return this.getClient().getPlayer().getName();
    }
    
    public final boolean haveItemTime(final int itemid) {
        if (this.haveItem(itemid)) {
            final MapleInventoryType type = GameConstants.getInventoryType(itemid);
            for (final IItem item : this.getChar().getInventory(type)) {
                if (item.getItemId() == itemid) {
                    return item.getExpiration() == -1L;
                }
            }
            return false;
        }
        return false;
    }
    
    public final boolean haveItemTimeNo(final int itemid) {
        if (this.haveItem(itemid)) {
            final MapleInventoryType type = GameConstants.getInventoryType(itemid);
            for (final IItem item : this.getChar().getInventory(type)) {
                if (item.getItemId() == itemid) {
                    return item.getExpiration() > 0L;
                }
            }
            return false;
        }
        return false;
    }
    
    public final boolean haveItem(final int itemid) {
        return this.haveItem(itemid, 1);
    }
    
    public final boolean haveItem(final int itemid, final int quantity) {
        return this.haveItem(itemid, quantity, false, true);
    }
    
    public final boolean haveItem(final int itemid, final int quantity, final boolean checkEquipped, final boolean greaterOrEquals) {
        return this.getClient().getPlayer().haveItem(itemid, quantity, checkEquipped, greaterOrEquals);
    }
    
    public final boolean canHold() {
        for (int i = 1; i <= 5; ++i) {
            if (this.c.getPlayer().getInventory(MapleInventoryType.getByType((byte)i)).getNextFreeSlot() <= -1) {
                return false;
            }
        }
        return true;
    }
    
    public final boolean canHoldByType(final byte bytype, final int num) {
        return this.c.getPlayer().getInventory(MapleInventoryType.getByType(bytype)).getSlotLimit() - (this.c.getPlayer().getInventory(MapleInventoryType.getByType(bytype)).getNumSlotLimit() + 1) > num;
    }
    
    public final boolean canHoldByTypea(final byte bytype, final int num) {
        return this.c.getPlayer().getInventory(MapleInventoryType.getByType(bytype)).getSlotLimit() - (this.c.getPlayer().getInventory(MapleInventoryType.getByType(bytype)).getNextFreeSlot() - 1) > num;
    }
    
    public final boolean canHold(final int itemid) {
        return this.getClient().getPlayer().getInventory(GameConstants.getInventoryType(itemid)).getNextFreeSlot() > -1;
    }
    
    public final boolean canHold(final int itemid, final int quantity) {
        return MapleInventoryManipulator.checkSpace(this.c, itemid, quantity, "");
    }
    
    public final MapleQuestStatus getQuestRecord(final int id) {
        return this.getClient().getPlayer().getQuestNAdd(MapleQuest.getInstance(id));
    }
    
    public final byte getQuestStatus(final int id) {
        return this.getClient().getPlayer().getQuestStatus(id);
    }
    
    public final boolean isQuestActive(final int id) {
        return this.getQuestStatus(id) == 1;
    }
    
    public final boolean isQuestFinished(final int id) {
        return this.getQuestStatus(id) == 2;
    }
    
    public final void showQuestMsg(final String msg) {
        this.getClient().sendPacket(MaplePacketCreator.showQuestMsg(msg));
    }
    
    public final void forceStartQuest(final int id, final String data) {
        MapleQuest.getInstance(id).forceStart(this.c.getPlayer(), 0, data);
    }
    
    public final void forceStartQuest(final int id, final int data, final boolean filler) {
        MapleQuest.getInstance(id).forceStart(this.c.getPlayer(), 0, filler ? String.valueOf(data) : null);
    }
    
    public void forceStartQuest(final int id) {
        MapleQuest.getInstance(id).forceStart(this.c.getPlayer(), 0, null);
    }
    
    public void forceCompleteQuest(final int id) {
        MapleQuest.getInstance(id).forceComplete(this.getPlayer(), 0);
    }
    
    public void completeQuest(final int id) {
        this.getClient().getPlayer().setQuestAdd(id);
    }
    
    public void spawnNpc(final int npcId) {
        this.getClient().getPlayer().getMap().spawnNpc(npcId, this.getClient().getPlayer().getPosition());
    }
    
    public final void spawnNpc(final int npcId, final int x, final int y) {
        this.getClient().getPlayer().getMap().spawnNpc(npcId, new Point(x, y));
    }
    
    public final void spawnNpc(final int npcId, final Point pos) {
        this.getClient().getPlayer().getMap().spawnNpc(npcId, pos);
    }
    
    public final void spawnNpc(final int mapid, final int npcId, final Point pos) {
        this.getClient().getChannelServer().getMapFactory().getMap(mapid).spawnNpc(npcId, pos);
    }
    
    public final void removeNpc(final int mapid, final int npcId) {
        this.getClient().getChannelServer().getMapFactory().getMap(mapid).removeNpc(npcId);
    }
    
    public void removeNpc(final int npcId) {
        this.getClient().getPlayer().getMap().removeNpc(npcId);
    }
    
    public final void forceStartReactor(final int mapid, final int id) {
        final MapleMap map = this.getClient().getChannelServer().getMapFactory().getMap(mapid);
        for (final MapleMapObject remo : map.getAllReactorsThreadsafe()) {
            final MapleReactor react = (MapleReactor)remo;
            if (react.getReactorId() == id) {
                react.forceStartReactor(this.c);
                break;
            }
        }
    }
    
    public final void destroyReactor(final int mapid, final int id) {
        final MapleMap map = this.getClient().getChannelServer().getMapFactory().getMap(mapid);
        for (final MapleMapObject remo : map.getAllReactorsThreadsafe()) {
            final MapleReactor react = (MapleReactor)remo;
            if (react.getReactorId() == id) {
                react.hitReactor(this.c);
                break;
            }
        }
    }
    
    public final void hitReactor(final int mapid, final int id) {
        final MapleMap map = this.getClient().getChannelServer().getMapFactory().getMap(mapid);
        for (final MapleMapObject remo : map.getAllReactorsThreadsafe()) {
            final MapleReactor react = (MapleReactor)remo;
            if (react.getReactorId() == id) {
                react.hitReactor(this.c);
                break;
            }
        }
    }
    
    public final int getJob() {
        return this.getClient().getPlayer().getJob();
    }
    
    public final void gainPotion(final int type, final int amount) {
        this.getClient().getPlayer().modifyCSPoints(type, amount, true);
    }
    
    public final int getPotion(final int type) {
        return this.getClient().getPlayer().getCSPoints(type);
    }
    
    public final void gainNX(final int amount) {
        this.gainPotion(1, amount);
    }
    
    public final int getNX() {
        return this.getPotion(1);
    }
    
    public final void gainDJ(final int amount) {
        this.c.getPlayer().modifyCSPoints(1, amount, true);
    }
    
    public final void gainDY(final int amount) {
        this.c.getPlayer().modifyCSPoints(2, amount, true);
    }
    
    public final void gainMaplePoint(final int amount) {
        this.gainPotion(2, amount);
    }
    
    public final int getMaplePoint() {
        return this.getPotion(2);
    }
    
    public final void gainItemPeriod(final int id, final short quantity, final int period) {
        this.gainItem(id, quantity, false, (long)period, false, -1, "");
    }
    
    public final void gainItemPeriod(final int id, final short quantity, final long period, final String owner) {
        this.gainItem(id, quantity, false, period, false, -1, owner);
    }
    
    public final void gainItemPeriod(final int id, final short quantity, final int period, final boolean hours) {
        this.gainItem(id, quantity, false, (long)period, hours, -1, "");
    }
    
    public final void gainItemPeriod(final int id, final short quantity, final long period, final boolean hours, final String owner) {
        this.gainItem(id, quantity, false, period, hours, -1, owner);
    }
    
    public final void gainItem(final int id, final short quantity) {
        this.gainItem(id, quantity, false, 0L, false, -1, "");
    }
    
    public final void gainItemSilent(final int id, final short quantity) {
        this.gainItem(id, quantity, false, 0L, false, -1, "", this.c, false);
    }
    
    public final void gainItem(final int id, final short quantity, final boolean randomStats) {
        this.gainItem(id, quantity, randomStats, 0L, false, -1, "");
    }
    
    public final void gainItem(final int id, final short quantity, final boolean randomStats, final int slots) {
        this.gainItem(id, quantity, randomStats, 0L, false, slots, "");
    }
    
    public final void gainItem(final int id, final short quantity, final long period) {
        this.gainItem(id, quantity, false, period, false, -1, "");
    }
    
    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period, final int slots) {
        this.gainItem(id, quantity, randomStats, period, false, slots, "");
    }
    
    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period, final boolean hours, final int slots, final String owner) {
        this.gainItem(id, quantity, randomStats, period, hours, slots, owner, this.c);
    }
    
    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period, final boolean hours, final int slots, final String owner, final MapleClient cg) {
        this.gainItem(id, quantity, randomStats, period, hours, slots, owner, cg, true);
    }
    
    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period, final boolean hours, final int slots, final String owner, final MapleClient cg, final boolean show) {
        if (quantity >= 0) {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(id);
            if (!MapleInventoryManipulator.checkSpace(cg, id, (int)quantity, "")) {
                return;
            }
            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(id) && !GameConstants.isBullet(id)) {
                final Equip item = (Equip)(Equip)(randomStats ? ii.randomizeStats((Equip)ii.getEquipById(id)) : ii.getEquipById(id));
                if (period > 0L) {
                    item.setExpiration(System.currentTimeMillis() + period * 60L * 60L * 1000L);
                }
                if (slots > 0) {
                    item.setUpgradeSlots((byte)(item.getUpgradeSlots() + slots));
                }
                if (owner != null) {
                    item.setOwner(owner);
                }
                item.setGMLog("Received from interaction " + this.id + " (" + this.id2 + ") on " + FileoutputUtil.CurrentReadable_Time());
                final String name = ii.getName(id);
                if (id / 10000 == 114 && name != null && name.length() > 0) {
                    final String msg = "< " + name + " > 你已获得称号.";
                    cg.getPlayer().dropMessage(-1, msg);
                    cg.getPlayer().dropMessage(5, msg);
                }
                MapleInventoryManipulator.addbyItem(cg, item.copy());
            }
            else {
                MapleInventoryManipulator.addById(cg, id, quantity, (owner == null) ? "" : owner, null, period, hours, "Received from interaction " + this.id + " (" + this.id2 + ") on " + FileoutputUtil.CurrentReadable_Date());
            }
        }
        else {
            MapleInventoryManipulator.removeById(cg, GameConstants.getInventoryType(id), id, -quantity, true, false);
        }
        if (show) {
            cg.getSession().write(MaplePacketCreator.getShowItemGain(id, quantity, true));
        }
    }
    
    public final void gainItem(final int id, final int str, final int dex, final int luk, final int Int, final int hp, final int mp, final int watk, final int matk, final int wdef, final int mdef, final int hb, final int mz, final int ty, final int yd) {
        this.gainItemS(id, str, dex, luk, Int, hp, mp, watk, matk, wdef, mdef, hb, mz, ty, yd, this.c);
    }
    
    public final void gainItem(final int id, final int str, final int dex, final int luk, final int Int, final int hp, final int mp, final int watk, final int matk, final int wdef, final int mdef, final int hb, final int mz, final int ty, final int yd, final int time) {
        this.gainItemS(id, str, dex, luk, Int, hp, mp, watk, matk, wdef, mdef, hb, mz, ty, yd, this.c, time);
    }
    
    public final void gainItemTime(final int id, final short quantity, final long period) {
        if (MapleItemInformationProvider.getInstance().isCash(id)) {
            this.gainItem(id, quantity, false, period, -1, "");
        }
        else {
            this.gainItem(id, quantity, false, 0L, -1, "");
        }
    }
    
    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner) {
        this.gainItem(id, quantity, randomStats, period, slots, owner, this.c);
    }
    
    public final void gainItemS(final int id, final int str, final int dex, final int luk, final int Int, final int hp, final int mp, final int watk, final int matk, final int wdef, final int mdef, final int hb, final int mz, final int ty, final int yd, final MapleClient cg) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final MapleInventoryType type = GameConstants.getInventoryType(id);
        if (!MapleInventoryManipulator.checkSpace(cg, id, 1, "")) {
            return;
        }
        if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(id) && !GameConstants.isBullet(id)) {
            final Equip item = (Equip)(Equip)ii.getEquipById(id);
            final String name = ii.getName(id);
            if (id / 10000 == 114 && name != null && name.length() > 0) {
                final String msg = "你已获得称号 <" + name + ">";
                cg.getPlayer().dropMessage(5, msg);
                cg.getPlayer().dropMessage(5, msg);
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
            MapleInventoryManipulator.addbyItem(cg, item.copy());
        }
        else {
            MapleInventoryManipulator.addById(this.c, id, (short)1);
        }
        cg.getSession().write(MaplePacketCreator.getShowItemGain(id, (short)1, true));
    }
    
    public final void gainItemS(final int id, final int str, final int dex, final int luk, final int Int, final int hp, final int mp, final int watk, final int matk, final int wdef, final int mdef, final int hb, final int mz, final int ty, final int yd, final MapleClient cg, final int time) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final MapleInventoryType type = GameConstants.getInventoryType(id);
        if (!MapleInventoryManipulator.checkSpace(cg, id, 1, "")) {
            return;
        }
        if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(id) && !GameConstants.isBullet(id)) {
            final Equip item = (Equip)(Equip)ii.getEquipById(id);
            final String name = ii.getName(id);
            if (id / 10000 == 114 && name != null && name.length() > 0) {
                final String msg = "你已获得称号 <" + name + ">";
                cg.getPlayer().dropMessage(5, msg);
                cg.getPlayer().dropMessage(5, msg);
            }
            if (time > 0) {
                item.setExpiration(System.currentTimeMillis() + (long)(time * 60 * 60 * 1000));
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
            MapleInventoryManipulator.addbyItem(cg, item.copy());
        }
        else {
            MapleInventoryManipulator.addById(cg, id, (short)1);
        }
        cg.getSession().write(MaplePacketCreator.getShowItemGain(id, (short)1, true));
    }
    
    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner, final MapleClient cg) {
        if (quantity >= 0) {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(id);
            if (!MapleInventoryManipulator.checkSpace(cg, id, (int)quantity, "")) {
                return;
            }
            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(id) && !GameConstants.isBullet(id)) {
                final Equip item = (Equip)(Equip)(randomStats ? ii.randomizeStats((Equip)ii.getEquipById(id)) : ii.getEquipById(id));
                if (period > 0L) {
                    item.setExpiration(System.currentTimeMillis() + period * 24L * 60L * 60L * 1000L);
                }
                if (slots > 0) {
                    item.setUpgradeSlots((byte)(item.getUpgradeSlots() + slots));
                }
                if (owner != null) {
                    item.setOwner(owner);
                }
                final String name = ii.getName(id);
                if (id / 10000 == 114 && name != null && name.length() > 0) {
                    final String msg = "你已获得称号 <" + name + ">";
                    cg.getPlayer().dropMessage(-1, msg);
                    cg.getPlayer().dropMessage(5, msg);
                }
                MapleInventoryManipulator.addbyItem(cg, item.copy());
            }
            else {
                MaplePet pet;
                if (类型.宠物(id)) {
                    pet = MaplePet.createPet(id, MapleInventoryIdentifier.getInstance());
                }
                else {
                    pet = null;
                }
                MapleInventoryManipulator.addById(cg, id, quantity, (owner == null) ? "" : owner, pet, period);
            }
        }
        else {
            MapleInventoryManipulator.removeById(cg, GameConstants.getInventoryType(id), id, -quantity, true, false);
        }
        cg.sendPacket(MaplePacketCreator.getShowItemGain(id, quantity, true));
    }
    
    public final void gainItemStatus(final int id, final short quantity) {
        this.gainItemStatus(id, quantity, false, 0L, -1, "");
    }
    
    public final void gainItemStatus(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner) {
        this.gainItemStatus(id, quantity, randomStats, period, slots, owner, this.c);
    }
    
    public final void gainItemStatus(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner, final MapleClient cg) {
        if (quantity >= 0) {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(id);
            if (!MapleInventoryManipulator.checkSpace(cg, id, (int)quantity, "")) {
                return;
            }
            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(id) && !GameConstants.isBullet(id)) {
                final Equip item = (Equip)(Equip)(randomStats ? ii.randomizeStats((Equip)ii.getEquipById(id)) : ii.getEquipById(id));
                if (period > 0L) {
                    item.setExpiration(System.currentTimeMillis() + period * 24L * 60L * 60L * 1000L);
                }
                if (slots > 0) {
                    item.setUpgradeSlots((byte)(item.getUpgradeSlots() + slots));
                }
                if (owner != null) {
                    item.setOwner(owner);
                }
                item.setStr((short)1);
                item.setDex((short)1);
                item.setInt((short)1);
                item.setLuk((short)1);
                final String name = ii.getName(id);
                if (id / 10000 == 114 && name != null && name.length() > 0) {
                    final String msg = "你已获得称号 <" + name + ">";
                    cg.getPlayer().dropMessage(-1, msg);
                    cg.getPlayer().dropMessage(5, msg);
                }
                MapleInventoryManipulator.addbyItem(cg, item.copy());
            }
            else {
                MaplePet pet;
                if (类型.宠物(id)) {
                    pet = MaplePet.createPet(id, MapleInventoryIdentifier.getInstance());
                }
                else {
                    pet = null;
                }
                MapleInventoryManipulator.addById(cg, id, quantity, (owner == null) ? "" : owner, pet, period);
            }
        }
        else {
            MapleInventoryManipulator.removeById(cg, GameConstants.getInventoryType(id), id, -quantity, true, false);
        }
        cg.sendPacket(MaplePacketCreator.getShowItemGain(id, quantity, true));
    }
    
    public final void changeMusic(final String songName) {
        this.getPlayer().getMap().broadcastMessage(MaplePacketCreator.musicChange(songName));
    }
    
    public final void worldMessage(final int type, final String message) {
        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(type, message));
    }
    
    public final void playerMessage(final String message) {
        this.playerMessage(5, message);
    }
    
    public final void mapMessage(final String message) {
        this.mapMessage(5, message);
    }
    
    public final void guildMessage(final String message) {
        this.guildMessage(5, message);
    }
    
    public final void playerMessage(final int type, final String message) {
        this.getClient().sendPacket(MaplePacketCreator.serverNotice(type, message));
    }
    
    public final void mapMessage(final int type, final String message) {
        this.getClient().getPlayer().getMap().broadcastMessage(MaplePacketCreator.serverNotice(type, message));
    }
    
    public final void guildMessage(final int type, final String message) {
        if (this.getPlayer().getGuildId() > 0) {
            Guild.guildPacket(this.getPlayer().getGuildId(), MaplePacketCreator.serverNotice(type, message));
        }
    }
    
    public final MapleGuild getGuild() {
        return this.getGuild(this.getPlayer().getGuildId());
    }
    
    public final MapleGuild getGuild(final int guildid) {
        return Guild.getGuild(guildid);
    }
    
    public final MapleParty getParty() {
        return this.getClient().getPlayer().getParty();
    }
    
    public final int getCurrentPartyId(final int mapid) {
        return this.getMap(mapid).getCurrentPartyId();
    }
    
    public final boolean isLeader() {
        return this.getParty() != null && this.getParty().getLeader().getId() == this.getClient().getPlayer().getId();
    }
    
    public final boolean isAllPartyMembersAllowedJob(final int job) {
        if (this.c.getPlayer().getParty() == null) {
            return false;
        }
        for (final MaplePartyCharacter mem : this.getClient().getPlayer().getParty().getMembers()) {
            if (mem.getJobId() / 100 != job) {
                return false;
            }
        }
        return true;
    }
    
    public final boolean isAllPartyMembersAllowedPQ(final String pqName, final int times) {
        return this.isAllPartyMembersAllowedPQ(pqName, times, 1);
    }
    
    public final boolean isAllPartyMembersAllowedPQ(final String pqName, final int times, final int day) {
        if (this.getParty() != null) {
            for (final MaplePartyCharacter partyCharacter : this.getParty().getMembers()) {
                final MapleCharacter player = this.getChannelServer().getPlayerStorage().getCharacterById(partyCharacter.getId());
                if (player != null && player.getBossLog(pqName) >= times) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public final boolean isAllPartyMembersCombat(final int a) {
        if (this.getParty() != null) {
            for (final MaplePartyCharacter partyCharacter : this.getParty().getMembers()) {
                final MapleCharacter player = this.getChannelServer().getPlayerStorage().getCharacterById(partyCharacter.getId());
                if (player != null && player.getCombat() < a) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public final MaplePartyCharacter getNotAllMembersCombatName(final int a) {
        if (this.getParty() == null) {
            return null;
        }
        for (final MaplePartyCharacter partyCharacter : this.getParty().getMembers()) {
            final MapleCharacter player = this.getChannelServer().getPlayerStorage().getCharacterById(partyCharacter.getId());
            if (player != null && player.getCombat() < a) {
                return partyCharacter;
            }
        }
        return null;
    }
    
    public final String getNotAllPartyMembersCombatName(final int a) {
        if (this.getNotAllMembersCombatName(a) != null) {
            return this.getNotAllMembersCombatName(a).getName();
        }
        return null;
    }
    
    public final boolean isAllPartyMembersReinNumber(final int a) {
        if (this.getParty() != null) {
            for (final MaplePartyCharacter partyCharacter : this.getParty().getMembers()) {
                final MapleCharacter player = this.getChannelServer().getPlayerStorage().getCharacterById(partyCharacter.getId());
                if (player != null && player.getReinNumber() < a) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public final MaplePartyCharacter getNotAllMembersReinNumberName(final int a) {
        if (this.getParty() == null) {
            return null;
        }
        for (final MaplePartyCharacter partyCharacter : this.getParty().getMembers()) {
            final MapleCharacter player = this.getChannelServer().getPlayerStorage().getCharacterById(partyCharacter.getId());
            if (player != null && player.getReinNumber() < a) {
                return partyCharacter;
            }
        }
        return null;
    }
    
    public final String getNotAllPartyMembersReinNumberName(final int a) {
        if (this.getNotAllMembersReinNumberName(a) != null) {
            return this.getNotAllMembersReinNumberName(a).getName();
        }
        return null;
    }
    
    public final MaplePartyCharacter getNotAllowedPQMember(final String pqName, final int times, final int day) {
        if (this.getParty() == null) {
            return null;
        }
        for (final MaplePartyCharacter partyCharacter : this.getParty().getMembers()) {
            final MapleCharacter player = this.getChannelServer().getPlayerStorage().getCharacterById(partyCharacter.getId());
            if (player != null && player.getBossLog(pqName) >= times) {
                return partyCharacter;
            }
        }
        return null;
    }
    
    public final String getNotAllowedPQMemberName(final String pqName, final int times) {
        return this.getNotAllowedPQMemberName(pqName, times, 1);
    }
    
    public final String getNotAllowedPQMemberName(final String string, final int times, final int day) {
        if (this.getNotAllowedPQMember(string, times, day) != null) {
            return this.getNotAllowedPQMember(string, times, day).getName();
        }
        return null;
    }
    
    public final boolean allMembersHere() {
        if (this.c.getPlayer().getParty() == null) {
            return false;
        }
        for (final MaplePartyCharacter mem : this.getClient().getPlayer().getParty().getMembers()) {
            final MapleCharacter chr = this.getClient().getPlayer().getMap().getCharacterById(mem.getId());
            if (chr == null) {
                return false;
            }
        }
        return true;
    }
    
    public int getPQLog(final String pqName) {
        return this.getPlayer().getBossLog(pqName);
    }
    
    public void setPQLog(final String pqName) {
        this.getPlayer().setBossLog(pqName);
    }
    
    public final void warpParty(final int mapId) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.warp(mapId, 0);
            return;
        }
        final MapleMap target = this.getMap(mapId);
        final int cMap = this.getPlayer().getMapId();
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == this.getPlayer().getEventInstance())) {
                curChar.changeMap(target, target.getPortal(0));
            }
        }
    }
    
    public final void warpParty(final int mapId, final int portal) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            if (portal < 0) {
                this.warp(mapId);
            }
            else {
                this.warp(mapId, portal);
            }
            return;
        }
        final boolean rand = portal < 0;
        final MapleMap target = this.getMap(mapId);
        final int cMap = this.getPlayer().getMapId();
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == this.getPlayer().getEventInstance())) {
                if (rand) {
                    try {
                        curChar.changeMap(target, target.getPortal(Randomizer.nextInt(target.getPortals().size())));
                    }
                    catch (Exception e) {
                        curChar.changeMap(target, target.getPortal(0));
                    }
                }
                else {
                    curChar.changeMap(target, target.getPortal(portal));
                }
            }
        }
    }
    
    public final void warpParty_Instanced(final int mapId) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.warp_Instanced(mapId);
            return;
        }
        final MapleMap target = this.getMap_Instanced(mapId);
        final int cMap = this.getPlayer().getMapId();
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == this.getPlayer().getEventInstance())) {
                curChar.changeMap(target, target.getPortal(0));
            }
        }
    }
    
    public void gainMeso(final int gain) {
        this.getClient().getPlayer().gainMeso(gain, true, false, true);
    }
    
    public void gainExp(final int gain) {
        this.getClient().getPlayer().gainExp(gain, true, true, true);
    }
    
    public void gainExpR(final int gain) {
        this.getClient().getPlayer().gainExp(gain * this.getClient().getChannelServer().getExpRate(), true, true, true);
    }
    
    public final void givePartyItems(final int id, final short quantity, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            if (quantity >= 0) {
                MapleInventoryManipulator.addById(chr.getClient(), id, quantity);
            }
            else {
                MapleInventoryManipulator.removeById(chr.getClient(), GameConstants.getInventoryType(id), id, -quantity, true, false);
            }
            chr.getClient().sendPacket(MaplePacketCreator.getShowItemGain(id, quantity, true));
        }
    }
    
    public final void givePartyItems(final int id, final short quantity) {
        this.givePartyItems(id, quantity, false);
    }
    
    public final void givePartyItems(final int id, final short quantity, final long time) {
        this.givePartyItems1(id, quantity, false, time);
    }
    
    public final boolean canPartyHold() {
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                for (int i = 1; i <= 5; ++i) {
                    if (curChar.getInventory(MapleInventoryType.getByType((byte)i)).getNextFreeSlot() <= -1) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    public final boolean isAllPartyMembersHaveItem(final int itemId, final int quantity) {
        if (this.getParty() == null) {
            return false;
        }
        for (final MaplePartyCharacter partyCharacter : this.getParty().getMembers()) {
            final MapleCharacter player = this.getChannelServer().getPlayerStorage().getCharacterById(partyCharacter.getId());
            if (player != null && player.getItemQuantity(itemId) >= quantity) {
                continue;
            }
            return false;
        }
        return true;
    }
    
    public final MaplePartyCharacter getNotAllPartyMembersHaveItem(final int itemId, final int quantity) {
        if (this.getParty() == null) {
            return null;
        }
        for (final MaplePartyCharacter partyCharacter : this.getParty().getMembers()) {
            final MapleCharacter player = this.getChannelServer().getPlayerStorage().getCharacterById(partyCharacter.getId());
            if (player != null && player.getItemQuantity(itemId) >= quantity) {
                continue;
            }
            return partyCharacter;
        }
        return null;
    }
    
    public final String getNotAllPartyMembersHaveItemName(final int itemId, final int quantity) {
        if (this.getNotAllPartyMembersHaveItem(itemId, quantity) != null) {
            return this.getNotAllPartyMembersHaveItem(itemId, quantity).getName();
        }
        return null;
    }
    
    public final void givePartyItems(final int id, final short quantity, final boolean removeAll) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.gainItem(id, (short)(removeAll ? (-this.getPlayer().itemQuantity(id)) : quantity));
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                this.gainItem(id, (short)(removeAll ? (-curChar.itemQuantity(id)) : quantity), false, 0L, 0, "", curChar.getClient());
            }
        }
    }
    
    public final boolean havePartyItems(final int id, final short quantity) {
        boolean abc = true;
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            if (this.getPlayer().getItemQuantity(id, false) < quantity) {
                abc = false;
            }
        }
        else {
            for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
                final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
                if (curChar != null && curChar.getItemQuantity(id, false) < quantity) {
                    abc = false;
                }
            }
        }
        return abc;
    }
    
    public final boolean havePartyMeso(final int id) {
        boolean abc = true;
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            if (this.getPlayer().getMeso() < id) {
                abc = false;
            }
        }
        else {
            for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
                final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
                if (curChar != null && curChar.getMeso() < id) {
                    abc = false;
                }
            }
        }
        return abc;
    }
    
    public final void givePartyItems1(final int id, final short quantity, final boolean removeAll, final long time) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.gainItem(id, (short)(removeAll ? (-this.getPlayer().itemQuantity(id)) : quantity), false, time, 0, "", this.getPlayer().getClient());
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                this.gainItem(id, (short)(removeAll ? (-curChar.itemQuantity(id)) : quantity), false, time, 0, "", curChar.getClient());
            }
        }
    }
    
    public final void givePartyExp(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.gainExp(amount * this.getClient().getChannelServer().getExpRate(), true, true, true);
        }
    }
    
    public final void givePartyExp(final int amount) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.gainExp(amount * this.getClient().getChannelServer().getExpRate());
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.gainExp(amount * this.getClient().getChannelServer().getExpRate(), true, true, true);
            }
        }
    }
    
    public final void givePartyDY(final int amount) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.gainDY(amount);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.modifyCSPoints(2, amount, true);
            }
        }
    }
    
    public final void givePartyMeso(final int amount) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.gainMeso(amount);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.gainMeso(amount, true);
            }
        }
    }
    
    public final void givePartyNX(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.modifyCSPoints(1, amount, true);
        }
    }
    
    public final void givePartyNX(final int amount) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.gainNX(amount);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.modifyCSPoints(1, amount, true);
            }
        }
    }
    
    public final void endPartyQuest(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.endPartyQuest(amount);
        }
    }
    
    public final void endPartyQuest(final int amount) {
        if (this.getPlayer().getParty() == null || this.getPlayer().getParty().getMembers().size() == 1) {
            this.getPlayer().endPartyQuest(amount);
            return;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = this.getMap().getCharacterById(chr.getId());
            if (curChar != null) {
                curChar.endPartyQuest(amount);
            }
        }
    }
    
    public final void removeFromParty(final int id, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            final int possesed = chr.getInventory(GameConstants.getInventoryType(id)).countById(id);
            if (possesed > 0) {
                MapleInventoryManipulator.removeById(this.c, GameConstants.getInventoryType(id), id, possesed, true, false);
                chr.getClient().sendPacket(MaplePacketCreator.getShowItemGain(id, (short)(-possesed), true));
            }
        }
    }
    
    public final void removeFromParty(final int id) {
        this.givePartyItems(id, (short)0, true);
    }
    
    public final void useSkill(final int skill, final int level) {
        if (level <= 0) {
            return;
        }
        SkillFactory.getSkill(skill).getEffect(level).applyTo(this.c.getPlayer());
    }
    
    public final void useItem(final int id) {
        MapleItemInformationProvider.getInstance().getItemEffect(id).applyTo(this.c.getPlayer());
        this.getClient().sendPacket(UIPacket.getStatusMsg(id));
    }
    
    public final void useItemEffect(final int id) {
        MapleItemInformationProvider.getInstance().getItemEffect(id).applyTo(this.c.getPlayer());
        this.getClient().sendPacket(MaplePacketCreator.enableActions());
    }
    
    public final void cancelItem(final int id) {
        this.getClient().getPlayer().cancelEffect(MapleItemInformationProvider.getInstance().getItemEffect(id), false, -1L);
    }
    
    public final int getMorphState() {
        return this.getClient().getPlayer().getMorphState();
    }
    
    public final void removeAll(final int id) {
        this.getClient().getPlayer().removeAll(id, true);
    }
    
    public final void gainCloseness(final int closeness, final int index) {
        final MaplePet pet = this.getPlayer().getPet(index);
        if (pet != null) {
            pet.setCloseness(pet.getCloseness() + closeness);
            this.getClient().sendPacket(PetPacket.updatePet(pet, this.getPlayer().getInventory(MapleInventoryType.CASH).getItem((short)(byte)pet.getInventoryPosition())));
        }
    }
    
    public final void gainClosenessAll(final int closeness) {
        for (final MaplePet pet : this.getPlayer().getPets()) {
            if (pet != null) {
                pet.setCloseness(pet.getCloseness() + closeness);
                this.getClient().sendPacket(PetPacket.updatePet(pet, this.getPlayer().getInventory(MapleInventoryType.CASH).getItem((short)(byte)pet.getInventoryPosition())));
            }
        }
    }
    
    public final void resetMap(final int mapid) {
        this.getMap(mapid).resetFully();
    }
    
    public void serverNotice(final String Text) {
        this.getClient().getChannelServer().broadcastPacket(MaplePacketCreator.serverNotice(6, Text));
    }
    
    public final void openNpc(final int id) {
        this.openNpc(id, null);
    }
    
    public final void openNpc(final int id, final int mode) {
        this.openNpc(this.getClient(), id, mode, null);
    }
    
    public final void openNpc(final MapleClient cg, final int id) {
        NPCScriptManager.getInstance().dispose(cg);
        this.openNpc(cg, id, 0, null);
    }
    
    public final void openNpc(final int id, final String script) {
        this.openNpc(this.getClient(), id, script);
    }
    
    public final void openNpc(final MapleClient cg, final int id, final String script) {
        this.openNpc(this.getClient(), id, 0, script);
    }
    
    public final void openNpc(final MapleClient cg, final int id, final int mode, final String script) {
        cg.removeClickedNPC();
        NPCScriptManager.getInstance().dispose(cg);
        NPCScriptManager.getInstance().start(cg, id, mode, script);
    }
    
    public final int getMapId() {
        return this.getClient().getPlayer().getMapId();
    }
    
    public final boolean haveMonster(final int mobid) {
        for (final MapleMapObject obj : this.getClient().getPlayer().getMap().getAllMonstersThreadsafe()) {
            final MapleMonster mob = (MapleMonster)obj;
            if (mob.getId() == mobid) {
                return true;
            }
        }
        return false;
    }
    
    public final int getChannelNumber() {
        return this.getClient().getChannel();
    }
    
    public final int getMonsterCount(final int mapid) {
        return this.getClient().getChannelServer().getMapFactory().getMap(mapid).getNumMonsters();
    }
    
    public final void teachSkill(final int id, final byte level, final byte masterlevel) {
        this.getPlayer().changeSkillLevel(SkillFactory.getSkill(id), level, masterlevel);
    }
    
    public final void teachSkill(final int id, byte level) {
        final ISkill skil = SkillFactory.getSkill(id);
        if (this.getPlayer().getSkillLevel(skil) > level) {
            level = this.getPlayer().getSkillLevel(skil);
        }
        this.getPlayer().changeSkillLevel(skil, level, skil.getMaxLevel());
    }
    
    public final byte getSkillMaxLevel(final int id) {
        final ISkill skil = SkillFactory.getSkill(id);
        return skil.getMaxLevel();
    }
    
    public final int getPlayerCount(final int mapid) {
        return this.getClient().getChannelServer().getMapFactory().getMap(mapid).getCharactersSize();
    }
    
    public final void dojo_getUp() {
        this.getClient().sendPacket(MaplePacketCreator.updateInfoQuest(1207, "pt=1;min=4;belt=1;tuto=1"));
        this.getClient().getSession().write(MaplePacketCreator.dojoWarpUp());
    }
    
    public final boolean dojoAgent_NextMap(final boolean dojo, final boolean fromresting) {
        if (dojo) {
            return Event_DojoAgent.warpNextMap(this.c.getPlayer(), fromresting);
        }
        return Event_DojoAgent.warpNextMap_Agent(this.c.getPlayer(), fromresting);
    }
    
    public final int dojo_getPts() {
        return this.getClient().getPlayer().getDojo();
    }
    
    public final MapleEvent getEvent(final String loc) {
        return this.getClient().getChannelServer().getEvent(MapleEventType.valueOf(loc));
    }
    
    public final int getSavedLocation(final String loc) {
        final Integer ret = Integer.valueOf(this.getClient().getPlayer().getSavedLocation(SavedLocationType.fromString(loc)));
        if (ret == null || ret.intValue() == -1) {
            return 100000000;
        }
        return ret.intValue();
    }
    
    public final void saveLocation(final String loc) {
        this.getClient().getPlayer().saveLocation(SavedLocationType.fromString(loc));
    }
    
    public final void saveReturnLocation(final String loc) {
        this.getClient().getPlayer().saveLocation(SavedLocationType.fromString(loc), this.getClient().getPlayer().getMap().getReturnMap().getId());
    }
    
    public final void clearSavedLocation(final String loc) {
        this.getClient().getPlayer().clearSavedLocation(SavedLocationType.fromString(loc));
    }
    
    public final void summonMsg(final String msg) {
        if (!this.c.getPlayer().hasSummon()) {
            this.playerSummonHint(true);
        }
        this.getClient().sendPacket(UIPacket.summonMessage(msg));
    }
    
    public final void summonMsg(final int type) {
        if (!this.c.getPlayer().hasSummon()) {
            this.playerSummonHint(true);
        }
        this.getClient().sendPacket(UIPacket.summonMessage(type));
    }
    
    public final void showInstruction(final String msg, final int width, final int height) {
        this.getClient().sendPacket(MaplePacketCreator.sendHint(msg, width, height));
    }
    
    public final void playerSummonHint(final boolean summon) {
        this.getClient().getPlayer().setHasSummon(summon);
        this.getClient().sendPacket(UIPacket.summonHelper(summon));
    }
    
    public final String getInfoQuest(final int id) {
        return this.getClient().getPlayer().getInfoQuest(id);
    }
    
    public final void updateInfoQuest(final int id, final String data) {
        this.getClient().getPlayer().updateInfoQuest(id, data);
    }
    
    public final boolean getEvanIntroState(final String data) {
        return this.getInfoQuest(22013).equals(data);
    }
    
    public final void updateEvanIntroState(final String data) {
        this.updateInfoQuest(22013, data);
    }
    
    public final void Aran_Start() {
        this.getClient().sendPacket(UIPacket.Aran_Start());
    }
    
    public final void evanTutorial(final String data, final int v1) {
        this.getClient().sendPacket(MaplePacketCreator.getEvanTutorial(data));
    }
    
    public final void AranTutInstructionalBubble(final String data) {
        this.getClient().sendPacket(UIPacket.AranTutInstructionalBalloon(data));
    }
    
    public final void ShowWZEffect(final String data) {
        this.getClient().sendPacket(UIPacket.AranTutInstructionalBalloon(data));
    }
    
    public final void showWZEffect(final String data) {
        this.getClient().sendPacket(UIPacket.ShowWZEffect(data));
    }
    
    public final void EarnTitleMsg(final String data) {
        this.getClient().sendPacket(UIPacket.EarnTitleMsg(data));
    }
    
    public final void MovieClipIntroUI(final boolean enabled) {
        this.getClient().sendPacket(UIPacket.IntroDisableUI(enabled));
        this.getClient().sendPacket(UIPacket.IntroLock(enabled));
    }
    
    public MapleInventoryType getInvType(final int i) {
        return MapleInventoryType.getByType((byte)i);
    }
    
    public String getItemName(final int id) {
        return MapleItemInformationProvider.getInstance().getName(id);
    }
    
    public String getMapName(final int id) {
        final MapleMapFactory mapFactory = ChannelServer.getInstance(this.c.getChannel()).getMapFactory();
        return mapFactory.getMap(id).getMapName();
    }
    
    public void gainPet(final int id, final String name, final int level, final int closeness, final int fullness) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        this.gainPet(id, name, level, closeness, fullness, (long)ii.getPetLife(id), ii.getPetFlagInfo(id));
    }
    
    public void gainPet(final int id, final String name, final int level, final int closeness, final int fullness, final int period) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        this.gainPet(id, name, level, closeness, fullness, (long)period, ii.getPetFlagInfo(id));
    }
    
    public void gainPet(int id, final String name, int level, int closeness, int fullness, final long period, final short flags) {
        if (id > 5010000 || id < 5000000) {
            id = 5000000;
        }
        if (level > 30) {
            level = 30;
        }
        if (closeness > 30000) {
            closeness = 30000;
        }
        if (fullness > 100) {
            fullness = 100;
        }
        try {
            MapleInventoryManipulator.addById(this.c, id, (short)1, "", MaplePet.createPet(id, name, level, closeness, fullness, MapleInventoryIdentifier.getInstance(), (id == 5000054) ? ((int)period) : 0, flags), period);
        }
        catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
    
    public void removeSlot(final int invType, final byte slot, final short quantity) {
        MapleInventoryManipulator.removeFromSlot(this.c, this.getInvType(invType), (short)slot, quantity, true);
    }
    
    public void gainGP(final int gp) {
        if (this.getPlayer().getGuildId() <= 0) {
            return;
        }
        Guild.gainGP(this.getPlayer().getGuildId(), gp);
    }
    
    public int getGP() {
        if (this.getPlayer().getGuildId() <= 0) {
            return 0;
        }
        return Guild.getGP(this.getPlayer().getGuildId());
    }
    
    public void showMapEffect(final String path) {
        this.getClient().sendPacket(UIPacket.MapEff(path));
    }
    
    public int itemQuantity(final int itemid) {
        return this.getPlayer().itemQuantity(itemid);
    }
    
    public EventInstanceManager getDisconnected(final String event) {
        final EventManager em = this.getEventManager(event);
        if (em == null) {
            return null;
        }
        for (final EventInstanceManager eim : em.getInstances()) {
            if (eim.isDisconnected(this.c.getPlayer()) && eim.getPlayerCount() > 0) {
                return eim;
            }
        }
        return null;
    }
    
    public boolean isAllReactorState(final int reactorId, final int state) {
        boolean ret = false;
        for (final MapleReactor r : this.getMap().getAllReactorsThreadsafe()) {
            if (r.getReactorId() == reactorId) {
                ret = (r.getState() == state);
            }
        }
        return ret;
    }
    
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
    
    public void spawnMonster(final int id) {
        this.spawnMonster(id, 1, new Point(this.getPlayer().getPosition()));
    }
    
    public void spawnMonster(final int id, final int x, final int y) {
        this.spawnMonster(id, 1, new Point(x, y));
    }
    
    public void spawnMonster(final int id, final int qty, final int x, final int y) {
        this.spawnMonster(id, qty, new Point(x, y));
    }
    
    public void spawnMonster(final int id, final int qty, final Point pos) {
        if (id == 8810026) {
            黑龙BOSS线程.关闭进阶BOSS线程();
            黑龙BOSS线程.开启进阶BOSS线程();
        }
        for (int i = 0; i < qty; ++i) {
            this.getMap().spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(id), pos);
        }
    }
    
    public void sendNPCText(final String text, final int npc) {
        this.getMap().broadcastMessage(MaplePacketCreator.getNPCTalk(npc, (byte)0, text, "00 00", (byte)0));
    }
    
    public void warpAllPlayer(final int from, final int to) {
        final MapleMap tomap = this.getMapFactory().getMap(to);
        final MapleMap frommap = this.getMapFactory().getMap(from);
        final List<MapleCharacter> list = frommap.getCharactersThreadsafe();
        if (tomap != null && frommap != null && list != null && frommap.getCharactersSize() > 0) {
            for (final MapleMapObject mmo : list) {
                ((MapleCharacter)mmo).changeMap(tomap, tomap.getPortal(0));
            }
        }
    }
    
    public MapleMapFactory getMapFactory() {
        return this.getChannelServer().getMapFactory();
    }
    
    public void enterMTS() {
        InterServerHandler.EnterCashShop(this.c, this.c.getPlayer(), true);
    }
    
    public int getChannelOnline() {
        return this.getClient().getChannelServer().getConnectedClients();
    }
    
    public int getTotalOnline() {
        return ((Integer)ChannelServer.getAllInstances().stream().map(cserv -> Integer.valueOf(cserv.getConnectedClients())).reduce(Integer.valueOf(0), Integer::sum)).intValue();
    }
    
    public int getMP() {
        return this.getPlayer().getMP();
    }
    
    public void setMP(final int x) {
        this.getPlayer().setMP(x);
    }
    
    public int save(final boolean dc, final boolean fromcs) {
        try {
            return this.getPlayer().saveToDB(dc, fromcs);
        }
        catch (UnsupportedOperationException ex) {
            return 0;
        }
    }
    
    public void save() {
        this.save(false, false);
    }
    
    public boolean hasSquadByMap() {
        return this.getPlayer().getMap().getSquadByMap() != null;
    }
    
    public boolean hasEventInstance() {
        return this.getPlayer().getEventInstance() != null;
    }
    
    public boolean hasEMByMap() {
        return this.getPlayer().getMap().getEMByMap() != null;
    }
    
    public void processCommand(final String line) {
        CommandProcessor.processCommand(this.getClient(), line, CommandType.NORMAL);
    }
    
    public void warpPlayer(final int from, final int to) {
        final MapleMap mapto = this.c.getChannelServer().getMapFactory().getMap(to);
        final MapleMap mapfrom = this.c.getChannelServer().getMapFactory().getMap(from);
        for (final MapleCharacter chr : mapfrom.getCharactersThreadsafe()) {
            chr.changeMap(mapto, mapto.getPortal(0));
        }
    }
    
    public void isVipMedalName() {
        if (this.getOneTimeLog("关闭VIP星星数显示") < 1) {
            this.setOneTimeLog("关闭VIP星星数显示");
            this.c.getPlayer().dropMessage(5, "关闭VIP星星数显示。");
        }
        else {
            this.deleteOneTimeLog("关闭VIP星星数显示");
            this.c.getPlayer().dropMessage(5, "开启VIP星星数显示。");
        }
    }
    
    public int getVip() {
        return this.getPlayer().getVip();
    }
    
    public void getItemLog(final String mob, final String itemmob) {
        FileoutputUtil.logToFile("logs/Data/" + mob + ".txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + this.c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + this.c.getAccountName() + " 账号ID " + this.c.getAccID() + " 角色名 " + this.c.getPlayer().getName() + " 角色ID " + this.c.getPlayer().getId() + " " + itemmob);
    }
    
    public int getAccNewTime(final String time) {
        return this.getPlayer().getAccNewTime(time);
    }
    
    public int getQianDaoTime(final String time) {
        return this.getPlayer().getQianDaoTime(time);
    }
    
    public int getQianDaoAcLog(final String time) {
        return this.getPlayer().getQianDaoAcLog(time);
    }
    
    public void giveEventPrize() {
        RandomRewards.getInstance();
        final int reward = RandomRewards.getEventReward();
        if (reward == 0) {
            this.getPlayer().gainMeso(66666, true, false, false);
            this.getPlayer().dropMessage(5, "你获得 66666 金币");
        }
        else if (reward == 1) {
            this.getPlayer().gainMeso(399999, true, false, false);
            this.getPlayer().dropMessage(5, "你获得 399999 金币");
        }
        else if (reward == 2) {
            this.getPlayer().gainMeso(666666, true, false, false);
            this.getPlayer().dropMessage(5, "你获得 666666 金币");
        }
        else if (reward == 3) {
            this.getPlayer().addFame(10);
            this.getPlayer().dropMessage(5, "你获得 10 名声");
        }
        else {
            int max_quantity = 1;
            switch (reward) {
                case 5062000: {
                    max_quantity = 3;
                    break;
                }
                case 5220000: {
                    max_quantity = 25;
                    break;
                }
                case 4031307:
                case 5050000: {
                    max_quantity = 5;
                    break;
                }
                case 2022121: {
                    max_quantity = 10;
                    break;
                }
            }
            final int quantity = ((max_quantity > 1) ? Randomizer.nextInt(max_quantity) : 0) + 1;
            if (MapleInventoryManipulator.checkSpace(this.getPlayer().getClient(), reward, quantity, "")) {
                MapleInventoryManipulator.addById(this.getPlayer().getClient(), reward, (short)quantity);
                this.getPlayer().dropMessage(5, "恭喜获得" + MapleItemInformationProvider.getInstance().getName(reward));
            }
            else {
                this.getPlayer().gainMeso(100000, true, false, false);
                this.getPlayer().dropMessage(5, "参加奖 100000 金币");
            }
        }
    }
    
    public List<IItem> getMonsterRidinglist() {
        final MapleInventory Equip = this.c.getPlayer().getInventory(MapleInventoryType.EQUIP);
        final List<IItem> ret = (List<IItem>)new ArrayList();
        for (final IItem tep : Equip) {
            if (tep.getItemId() >= 1930000 && tep.getItemId() <= 1992050) {
                ret.add(tep);
            }
        }
        return ret;
    }
    
    public String getCharacterNameById(final int id) {
        this.c.getPlayer();
        final String name = MapleCharacter.getCharacterNameById(id);
        return name;
    }
    
    public final int getCharacterIdByName(final String name) {
        this.c.getPlayer();
        final int id = MapleCharacter.getCharacterIdByName(name);
        return id;
    }
    
    public int getCharacterByNameLevel(final String name) {
        this.c.getPlayer();
        final int level = MapleCharacter.getCharacterByName(name).getLevel();
        return level;
    }
    
    public List<IItem> getCsEquipList() {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final MapleInventory Equip = this.c.getPlayer().getInventory(MapleInventoryType.EQUIP);
        final List<IItem> ret = (List<IItem>)new ArrayList();
        for (final IItem tep : Equip) {
            if (ii.isCash(tep.getItemId())) {
                ret.add(tep);
            }
        }
        return ret;
    }
    
    public Equip getEquipStat(final byte slot) {
        final Equip sel = (Equip)this.c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short)slot);
        return sel;
    }
    
    public void dropCs(final byte type, final short src, final short quantity) {
        MapleInventoryManipulator.dropCs(this.c, MapleInventoryType.getByType(type), src, quantity);
    }
    
    public final boolean canwncs() {
        for (final int i : GameConstants.blockedMaps) {
            if (this.c.getPlayer().getMapId() == i) {
                this.c.getPlayer().dropMessage(5, "当前地图无法使用.");
                return false;
            }
        }
        if (this.c.getPlayer().getMapId() == 749060605 || this.c.getPlayer().getMapId() == 229010000 || this.c.getPlayer().getMapId() == 910000000) {
            this.c.getPlayer().dropMessage(5, "当前地图无法使用.");
            return false;
        }
        if (this.c.getPlayer().getLevel() < 10 && this.c.getPlayer().getJob() != 200) {
            this.c.getPlayer().dropMessage(5, "你的等级不足10级无法使用.");
            return false;
        }
        if (this.c.getPlayer().hasBlockedInventory(true) || this.c.getPlayer().getMap().getSquadByMap() != null || this.c.getPlayer().getEventInstance() != null || this.c.getPlayer().getMap().getEMByMap() != null || this.c.getPlayer().getMapId() >= 990000000) {
            this.c.getPlayer().dropMessage(5, "请稍后再试");
            return false;
        }
        if ((this.c.getPlayer().getMapId() >= 680000210 && this.c.getPlayer().getMapId() <= 680000502) || (this.c.getPlayer().getMapId() / 1000 == 980000 && this.c.getPlayer().getMapId() != 980000000) || this.c.getPlayer().getMapId() / 100 == 1030008 || this.c.getPlayer().getMapId() / 100 == 922010 || this.c.getPlayer().getMapId() / 10 == 13003000) {
            this.c.getPlayer().dropMessage(5, "请稍后再试.");
            return false;
        }
        return true;
    }
    
    public int getEquipItemType(final int itemid) {
        if (类型.帽子(itemid)) {
            return 1;
        }
        if (类型.脸饰(itemid)) {
            return 2;
        }
        if (类型.眼饰(itemid)) {
            return 3;
        }
        if (类型.耳环(itemid)) {
            return 4;
        }
        if (类型.上衣(itemid)) {
            return 5;
        }
        if (类型.套服(itemid)) {
            return 6;
        }
        if (类型.裤裙(itemid)) {
            return 7;
        }
        if (类型.鞋子(itemid)) {
            return 8;
        }
        if (类型.手套(itemid)) {
            return 9;
        }
        if (类型.盾牌(itemid)) {
            return 9;
        }
        if (类型.披风(itemid)) {
            return 10;
        }
        if (类型.戒指(itemid)) {
            return 11;
        }
        if (类型.坠饰(itemid)) {
            return 12;
        }
        if (类型.腰带(itemid)) {
            return 13;
        }
        if (类型.勋章(itemid)) {
            return 15;
        }
        if (类型.武器(itemid)) {
            return 16;
        }
        if (类型.副手(itemid)) {
            return 17;
        }
        return 0;
    }
    
    public void forceReAddItem(final Item item, final byte type) {
        this.c.getPlayer().forceReAddItem_Flag((IItem)item, MapleInventoryType.getByType(type));
        this.c.getPlayer().equipChanged();
    }
    
    public void addItem(final Item item) {
        MapleInventoryManipulator.addFromDrop(this.c, (IItem)item, false);
    }
    
    public void additem(final Item item) {
        MapleInventoryManipulator.addFromDrop(this.c, (IItem)item, false);
    }
    
    public void StatsZs() {
        final Map<MapleStat, Long> statups = new EnumMap(MapleStat.class);
        this.c.getPlayer().setLevel((short)1);
        this.c.getPlayer().levelUp();
        if (this.c.getPlayer().getExp() < 0) {
            this.c.getPlayer().gainExp(-this.c.getPlayer().getExp(), false, false, true);
        }
        this.c.getPlayer().getStat().str = 4;
        this.c.getPlayer().getStat().dex = 4;
        this.c.getPlayer().getStat().int_ = 4;
        this.c.getPlayer().getStat().luk = 4;
        this.c.getPlayer().setHpMpApUsed((short)0);
        this.c.getPlayer().setRemainingAp((short)13);
        this.c.getPlayer().setRemainingSp(0);
        this.c.getSession().write(MaplePacketCreator.updateSp(this.c.getPlayer(), false));
        statups.put(MapleStat.STR, Long.valueOf((int)this.c.getPlayer().getStat().getStr()));
        statups.put(MapleStat.DEX, Long.valueOf((int)this.c.getPlayer().getStat().getDex()));
        statups.put(MapleStat.LUK, Long.valueOf((int)this.c.getPlayer().getStat().getLuk()));
        statups.put(MapleStat.INT, Long.valueOf((int)this.c.getPlayer().getStat().getInt()));
        statups.put(MapleStat.HP, Long.valueOf((int)this.c.getPlayer().getStat().getHp()));
        statups.put(MapleStat.MAXHP, Long.valueOf((int)this.c.getPlayer().getStat().getMaxHp()));
        statups.put(MapleStat.MP, Long.valueOf((int)this.c.getPlayer().getStat().getMp()));
        statups.put(MapleStat.MAXMP, Long.valueOf((int)this.c.getPlayer().getStat().getMaxMp()));
        statups.put(MapleStat.AVAILABLEAP, Long.valueOf((int)this.c.getPlayer().getRemainingAp()));
        this.c.getPlayer().getStat().recalcLocalStats();
        this.c.getSession().write(MaplePacketCreator.updatePlayerStats(statups, this.c.getPlayer()));
        this.c.getPlayer().fakeRelog();
    }
    
    public void maxSkillsByJob() {
        this.c.getPlayer().maxSkillsByJob();
    }
    
    public void LearnSkillsByJob() {
        this.c.getPlayer().LearnSkillsByJob(this.c.getPlayer());
    }
    
    public int getGamePoints() {
        return this.c.getPlayer().getGamePoints();
    }
    
    public void gainGamePoints(final int amount) {
        this.c.getPlayer().gainGamePoints(amount);
    }
    
    public void resetGamePoints() {
        this.c.getPlayer().resetGamePoints();
    }
    
    public int getGamePointsPD() {
        return this.c.getPlayer().getGamePointsPD();
    }
    
    public void gainGamePointsPD(final int amount) {
        this.c.getPlayer().gainGamePointsPD(amount);
    }
    
    public void resetGamePointsPD() {
        this.c.getPlayer().resetGamePointsPD();
    }
    
    public int getPS() {
        return this.c.getPlayer().getGamePointsPS();
    }
    
    public void gainPS(final int amount) {
        this.c.getPlayer().gainGamePointsPS(amount);
    }
    
    public void resetPS() {
        this.c.getPlayer().resetGamePointsPS();
    }
    
    public int getskillzq() {
        return this.c.getPlayer().getSkillzq();
    }
    
    public void setskillzq(final int amount) {
        this.c.getPlayer().setSkillzq(amount);
    }
    
    public int MarrageChecking() {
        if (this.getPlayer().getParty() == null) {
            return -1;
        }
        if (this.getPlayer().getMarriageId() > 0) {
            return 0;
        }
        if (this.getPlayer().getParty().getMembers().size() != 2) {
            return 1;
        }
        if (this.getPlayer().getGender() == 0 && !this.getPlayer().haveItem(1050121) && !this.getPlayer().haveItem(1050122) && !this.getPlayer().haveItem(1050113)) {
            return 5;
        }
        if (this.getPlayer().getGender() == 1 && !this.getPlayer().haveItem(1051129) && !this.getPlayer().haveItem(1051130) && !this.getPlayer().haveItem(1051114)) {
            return 5;
        }
        if (!this.getPlayer().haveItem(1112001) && !this.getPlayer().haveItem(1112012) && !this.getPlayer().haveItem(1112002) && !this.getPlayer().haveItem(1112007)) {
            return 6;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            if (chr.getId() == this.getPlayer().getId()) {
                continue;
            }
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar == null) {
                return 2;
            }
            if (curChar.getMarriageId() > 0) {
                return 3;
            }
            if (curChar.getGender() == this.getPlayer().getGender()) {
                return 4;
            }
            if (curChar.getGender() == 0 && !curChar.haveItem(1050121) && !curChar.haveItem(1050122) && !curChar.haveItem(1050113)) {
                return 5;
            }
            if (curChar.getGender() == 1 && !curChar.haveItem(1051129) && !curChar.haveItem(1051130) && !curChar.haveItem(1051114)) {
                return 5;
            }
            if (!curChar.haveItem(1112001) && !curChar.haveItem(1112012) && !curChar.haveItem(1112002) && !curChar.haveItem(1112007)) {
                return 6;
            }
        }
        return 9;
    }
    
    public int getPartyFormID() {
        int curCharID = -1;
        if (this.getPlayer().getParty() == null) {
            curCharID = -1;
        }
        else if (this.getPlayer().getMarriageId() > 0) {
            curCharID = -2;
        }
        else if (this.getPlayer().getParty().getMembers().size() != 2) {
            curCharID = -3;
        }
        for (final MaplePartyCharacter chr : this.getPlayer().getParty().getMembers()) {
            if (chr.getId() == this.getPlayer().getId()) {
                continue;
            }
            final MapleCharacter curChar = this.getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar == null) {
                curCharID = -4;
            }
            else {
                curCharID = chr.getId();
            }
        }
        return curCharID;
    }
    
    public void makeRing(final int itemID, final String Name) {
        final int itemId = itemID;
        if (!GameConstants.isEffectRing(itemId)) {
            this.c.getPlayer().dropMessage(6, "Invalid itemID.");
        }
        else {
            final MapleCharacter fff = this.c.getChannelServer().getPlayerStorage().getCharacterByName(Name);
            if (fff == null) {
                this.c.getPlayer().dropMessage(6, "玩家必须在线");
            }
            else {
                final int[] ringID = { MapleInventoryIdentifier.getInstance(), MapleInventoryIdentifier.getInstance() };
                try {
                    final MapleCharacter[] chrz = { fff, this.c.getPlayer() };
                    for (int i = 0; i < chrz.length; ++i) {
                        final Equip eq = (Equip)MapleItemInformationProvider.getInstance().getEquipById(itemId, ringID[i]);
                        if (eq == null) {
                            this.c.getPlayer().dropMessage(6, "Invalid itemID.");
                            return;
                        }
                        MapleInventoryManipulator.addbyItem(chrz[i].getClient(), eq.copy());
                        chrz[i].dropMessage(6, "和 " + chrz[i == 0?1 : 0].getName() + "结婚成功");
                    }
                    MapleRing.addToDB(itemId, this.c.getPlayer(), fff.getName(), fff.getId(), ringID);
                }
                catch (SQLException ex) {}
            }
        }
    }
    
    public boolean getStartState() {
        return Mushplotact.getStartState();
    }
    
    public void setStartState(final boolean 状态) {
        Mushplotact.setStartState(状态);
    }
    
    public boolean getOneStartState() {
        return Mushplotact.getOneStartState();
    }
    
    public void setOneStartState(final boolean 状态) {
        Mushplotact.setOneStartState(状态);
    }
    
    public long getOneTimeState() {
        return Mushplotact.getOneTimeState();
    }
    
    public void setOneTimeState(final long 状态) {
        Mushplotact.setOneTimeState(状态);
    }
    
    public int getOneTimeCd() {
        return Mushplotact.getOneTimeCd();
    }
    
    public int getMushroomStage(final String 文本) {
        return Mushplotact.getMushroomStage(文本);
    }
    
    public void setMushroomStage(final String 文本, final int 次数) {
        Mushplotact.setMushroomStage(文本, 次数);
    }
    
    public String checkLastonetime() {
        return Mushplotact.checkLastonetime();
    }
    
    public boolean getSecStartState() {
        return Mushplotact.getSecStartState();
    }
    
    public void setSecStartState(final boolean 状态) {
        Mushplotact.setSecStartState(状态);
    }
    
    public long getSecTimeState() {
        return Mushplotact.getSecTimeState();
    }
    
    public void setSecTimeState(final long 状态) {
        Mushplotact.setSecTimeState(状态);
    }
    
    public int getSecTimeCd() {
        return Mushplotact.getSecTimeCd();
    }
    
    public String checkLastSectime() {
        return Mushplotact.checkLastSectime();
    }
    
    public boolean getThrStartState() {
        return Mushplotact.getThrStartState();
    }
    
    public void setThrStartState(final boolean 状态) {
        Mushplotact.setThrStartState(状态);
    }
    
    public long getThrTimeState() {
        return Mushplotact.getThrTimeState();
    }
    
    public void setThrTimeState(final long 状态) {
        Mushplotact.setThrTimeState(状态);
    }
    
    public int getThrTimeCd() {
        return Mushplotact.getThrTimeCd();
    }
    
    public String checkLastThrtime() {
        return Mushplotact.checkLastThrtime();
    }
    
    public boolean getFouStartState() {
        return Mushplotact.getFouStartState();
    }
    
    public void setFouStartState(final boolean 状态) {
        Mushplotact.setFouStartState(状态);
    }
    
    public long getFouTimeState() {
        return Mushplotact.getFouTimeState();
    }
    
    public void setFouTimeState(final long 状态) {
        Mushplotact.setFouTimeState(状态);
    }
    
    public int getFouTimeCd() {
        return Mushplotact.getFouTimeCd();
    }
    
    public String checkLastFoutime() {
        return Mushplotact.checkLastFoutime();
    }
    
    public void finishOneState() {
        Mushplotact.finishOneState();
    }
    
    public void setEggspawn(final int 状态) {
        Mushplotact.setEggspawn(状态);
    }
    
    public int getEggspawn() {
        return Mushplotact.getEggspawn();
    }
    
    public String getNumera() {
        return Mushplotact.getNumera();
    }
    
    public String getNumerb() {
        return Mushplotact.getNumerb();
    }
    
    public String getNumerc() {
        return Mushplotact.getNumerc();
    }
    
    public int getEggmap() {
        return Mushplotact.getEggmap();
    }
    
    public int getEggmapx() {
        return Mushplotact.getEggmapx();
    }
    
    public int getEggmapy() {
        return Mushplotact.getEggmapy();
    }
    
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
    
    public void enableActions() {
        this.getClient().getSession().write(MaplePacketCreator.enableActions());
    }
    
    public void 道具喇叭(final int itemId, final String msg) {
        IItem item;
        if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
            item = (Equip)AbstractPlayerInteraction.ii.getEquipById(itemId);
            item.setPosition((short)1);
        }
        else {
            item = new Item(itemId, (short)1, (short)1, (byte)0, -1);
        }
        this.道具喇叭(item, msg);
    }
    
    public void 道具喇叭(final IItem item, final String msg) {
        final StringBuilder sb = new StringBuilder();
        final IItem medal = this.c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-26));
        if (medal != null) {
            sb.append("<");
            sb.append(AbstractPlayerInteraction.ii.getName(medal.getItemId()));
            sb.append("> ");
        }
        sb.append(this.c.getPlayer().getName());
        sb.append(" : ");
        sb.append(msg);
        Broadcast.broadcastSmega(MaplePacketCreator.itemMegaphone(sb.toString(), true, this.c.getChannel(), item));
    }
    
    public final void sljyh(final int itemid) {
        this.c.sendPacket(MaplePacketCreator.getjyhss(itemid, this.c.getChannel() - 1));
    }
    
    public int getwzcz() {
        return this.c.getPlayer().getwzcz(this.c.getPlayer().getId());
    }
    
    public final void sljyha(final int itemid) {
        this.c.getSession().write(MaplePacketCreator.getOwlMessage(itemid));
    }
    
    public void youlog(final String 保存名字, final String 内容) {
        FileoutputUtil.logToFile_chr(this.getPlayer(), 保存名字, 内容);
    }
    
    public final boolean checkNOJY(final short amount) {
        return ItemFlag.UNTRADEABLE.check((int)amount) || ItemFlag.LOCK.check((int)amount);
    }
    
    public final boolean checkNOJY2(final short amount) {
        return ItemFlag2.notSale.check((int)amount) || ItemFlag2.only.check((int)amount) || ItemFlag2.tradeBlock.check((int)amount);
    }
    
    public void itemlaba(final String txt, final Item item) {
        Broadcast.broadcastMessage(MaplePacketCreator.getItemMega(this.getPlayer().getClient().getChannel(), this.getPlayer().getName() + " : " + txt, item, true));
    }
    
    public void itemlaba(final String name, final String txt, final Item item, final int labaid) {
        if (labaid == 16) {
            Broadcast.broadcastSmega(MaplePacketCreator.itemMegaphone(name + " : " + txt, true, this.c.getChannel(), item.copy()));
        }
        else {
            Broadcast.broadcastMessage(MaplePacketCreator.getItemMega(this.getPlayer().getClient().getChannel(), name + " : " + txt, item, true));
        }
    }
    
    public final boolean ditem(final int 编号) {
        int itemid = 0;
        int quantity = 0;
        int inventorytype = -1;
        Equip equip = null;
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM `jyh` WHERE `charactersid` = ? and `tid` = ?");
            ps.setInt(1, this.c.getPlayer().getId());
            ps.setInt(2, 编号);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (!this.canHold(rs.getInt("itemid"), rs.getInt("quantity"))) {
                    this.c.getPlayer().dropMessage(1, "背包格子不足，不能取回");
                    return false;
                }
                inventorytype = rs.getInt("inventorytype");
                itemid = rs.getInt("itemid");
                quantity = rs.getInt("quantity");
                if (inventorytype == 1) {
                    equip = new Equip(rs.getInt("itemid"), (short)0);
                    equip.setQuantity((short)1);
                    equip.setOwner(rs.getString("owner"));
                    equip.setExpiration(rs.getLong("expiredate"));
                    equip.setUpgradeSlots(rs.getByte("upgradeslots"));
                    equip.setLevel(rs.getByte("level"));
                    equip.setStr(rs.getShort("str"));
                    equip.setDex(rs.getShort("dex"));
                    equip.setInt(rs.getShort("int"));
                    equip.setLuk(rs.getShort("luk"));
                    equip.setHp(rs.getShort("hp"));
                    equip.setMp(rs.getShort("mp"));
                    equip.setWatk(rs.getShort("watk"));
                    equip.setMatk(rs.getShort("matk"));
                    equip.setWdef(rs.getShort("wdef"));
                    equip.setMdef(rs.getShort("mdef"));
                    equip.setAcc(rs.getShort("acc"));
                    equip.setAvoid(rs.getShort("avoid"));
                    equip.setHands(rs.getShort("hands"));
                    equip.setSpeed(rs.getShort("speed"));
                    equip.setJump(rs.getShort("jump"));
                    equip.setViciousHammer(rs.getByte("ViciousHammer"));
                    equip.setItemEXP(rs.getInt("itemEXP"));
                    equip.setGMLog(rs.getString("GM_Log"));
                    equip.setDurability(rs.getInt("durability"));
                    equip.setEnhance(rs.getByte("enhance"));
                    equip.setPotential1(rs.getShort("potential1"));
                    equip.setPotential2(rs.getShort("potential2"));
                    equip.setPotential3(rs.getShort("potential3"));
                    equip.setHpR(rs.getShort("hpR"));
                    equip.setMpR(rs.getShort("mpR"));
                    equip.setGiftFrom(rs.getString("sender"));
                    equip.setIncSkill(rs.getInt("incSkill"));
                    equip.setPVPDamage(rs.getShort("pvpDamage"));
                    equip.setCharmEXP(rs.getShort("charmEXP"));
                }
                else {
                    itemid = rs.getInt("itemid");
                    quantity = rs.getInt("quantity");
                }
                if (!this.deleitem(编号)) {
                    this.c.getPlayer().dropMessage(1, "下架失败，可能改物品已经售出");
                    return false;
                }
                if (inventorytype == 1) {
                    MapleInventoryManipulator.addFromDrop(this.c, (IItem)equip, false);
                    this.c.getPlayer().dropMessage(6, "下架成功，东西已经塞进您的背包");
                    FileoutputUtil.logToFile_chr(this.c.getPlayer(), "交易行下架.txt", " 物品id：" + itemid + " 数量：" + quantity);
                    return true;
                }
                this.gainItem(itemid, (short)quantity);
                this.c.getPlayer().dropMessage(6, "下架成功，东西已经塞进您的背包");
                FileoutputUtil.logToFile_chr(this.c.getPlayer(), "交易行下架.txt", " 物品id：" + itemid + " 数量：" + quantity);
                return true;
            }
            else {
                rs.close();
                ps.close();
                con.close();
            }
        }
        catch (Exception Ex) {
            System.out.println("ditem" + Ex);
            return false;
        }
        return false;
    }
    
    public final boolean upitem(final Item items, final int 消耗类型, final int 消耗数量) {
        if (this.c.getPlayer().getInventory(MapleItemInformationProvider.getInstance().getInventoryType(items.getItemId())).getItem((short)1) == null) {
            this.c.getPlayer().ban(this.c.getPlayer().getName() + "交易行上架异常a" + items.getItemId(), true, true, false);
            this.c.getPlayer().getClient().getSession().close();
            return false;
        }
        if (this.c.getPlayer().getInventory(MapleItemInformationProvider.getInstance().getInventoryType(items.getItemId())).getItem((short)1).getItemId() != items.getItemId() || this.c.getPlayer().getInventory(MapleItemInformationProvider.getInstance().getInventoryType(items.getItemId())).getItem((short)1).getQuantity() != items.getQuantity()) {
            this.c.getPlayer().ban(this.c.getPlayer().getName() + "交易行上架异常b" + items.getItemId(), true, true, false);
            this.c.getPlayer().getClient().getSession().close();
            return false;
        }
        try {
            final Connection con1 = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con1.prepareStatement("insert into jyh (moneytype,money,beizhu,itemid,quantity,inventorytype,GM_Log,expiredate,flag,uniqueid,owner,sender,upgradeslots,level,str,dex, `int`,luk,hp,mp,watk,matk,wdef,mdef,acc,avoid,hands,speed,jump,ViciousHammer, `itemEXP`,durability,enhance,potential1,potential2,potential3,hpR,mpR,incSkill,charmEXP,pvpDamage,charactersid) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, 消耗类型);
            ps.setInt(2, 消耗数量);
            ps.setString(3, MapleItemInformationProvider.getInstance().getName(items.getItemId()));
            ps.setInt(4, items.getItemId());
            ps.setInt(5, (int)items.getQuantity());
            if (items.getQuantity() > 30000) {
                this.c.getPlayer().dropMessage(1, "上架物品最大3w");
                return false;
            }
            if (items.getType() == 1) {
                final Equip item = (Equip)items;
                ps.setInt(6, (int)item.getType());
                ps.setString(7, item.getGMLog());
                ps.setLong(8, item.getExpiration());
                ps.setInt(9, (int)item.getFlag());
                ps.setInt(10, item.getUniqueId());
                ps.setString(11, item.getOwner());
                ps.setString(12, "");
                ps.setInt(13, (int)item.getUpgradeSlots());
                ps.setInt(14, (int)item.getLevel());
                ps.setInt(15, (int)item.getStr());
                ps.setInt(16, (int)item.getDex());
                ps.setInt(17, (int)item.getInt());
                ps.setInt(18, (int)item.getLuk());
                ps.setInt(19, (int)item.getHp());
                ps.setInt(20, (int)item.getMp());
                ps.setInt(21, (int)item.getWatk());
                ps.setInt(22, (int)item.getMatk());
                ps.setInt(23, (int)item.getWdef());
                ps.setInt(24, (int)item.getMdef());
                ps.setInt(25, (int)item.getAcc());
                ps.setInt(26, (int)item.getAvoid());
                ps.setInt(27, (int)item.getHands());
                ps.setInt(28, (int)item.getSpeed());
                ps.setInt(29, (int)item.getJump());
                ps.setInt(30, (int)item.getViciousHammer());
                ps.setLong(31, item.getItemEXP());
                ps.setInt(32, item.getDurability());
                ps.setInt(33, (int)item.getEnhance());
                ps.setShort(34, item.getPotential1());
                ps.setShort(35, item.getPotential2());
                ps.setShort(36, item.getPotential3());
                ps.setInt(37, (int)item.getHpR());
                ps.setInt(38, (int)item.getMpR());
                ps.setInt(39, item.getIncSkill());
                ps.setInt(40, (int)item.getCharmEXP());
                ps.setInt(41, (int)item.getPVPDamage());
            }
            else {
                ps.setInt(6, (int)MapleItemInformationProvider.getInstance().getInventoryType(items.getItemId()).getType());
                ps.setString(7, "");
                ps.setLong(8, 0L);
                ps.setInt(9, 0);
                ps.setInt(10, 0);
                ps.setString(11, "");
                ps.setString(12, "");
                ps.setInt(13, 0);
                ps.setInt(14, 0);
                ps.setInt(15, 0);
                ps.setInt(16, 0);
                ps.setInt(17, 0);
                ps.setInt(18, 0);
                ps.setInt(19, 0);
                ps.setInt(20, 0);
                ps.setInt(21, 0);
                ps.setInt(22, 0);
                ps.setInt(23, 0);
                ps.setInt(24, 0);
                ps.setInt(25, 0);
                ps.setInt(26, 0);
                ps.setInt(27, 0);
                ps.setInt(28, 0);
                ps.setInt(29, 0);
                ps.setInt(30, 0);
                ps.setInt(31, 0);
                ps.setInt(32, 0);
                ps.setInt(33, 0);
                ps.setInt(34, 0);
                ps.setInt(35, 0);
                ps.setInt(36, 0);
                ps.setInt(37, 0);
                ps.setInt(38, 0);
                ps.setInt(39, 0);
                ps.setInt(40, 0);
                ps.setInt(41, 0);
            }
            ps.setInt(42, this.c.getPlayer().getId());
            ps.executeUpdate();
            ps.close();
            con1.close();
            FileoutputUtil.logToFile_chr(this.c.getPlayer(), "交易行上架.txt", " 物品id：" + items.getItemId() + " 数量：" + (int)items.getQuantity() + " 消费类型：" + 消耗类型 + " 金额：" + 消耗数量);
            return true;
        }
        catch (Exception Ex) {
            System.out.println("upitem" + Ex);
            return false;
        }
    }
    
    public final boolean deleitem(final int 编号) {
        try {
            final Connection con1 = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con1.prepareStatement("DELETE FROM `jyh` WHERE `charactersid` = ? and `tid` = ?");
            ps.setInt(1, this.c.getPlayer().getId());
            ps.setInt(2, 编号);
            ps.executeUpdate();
            ps.close();
            con1.close();
            return true;
        }
        catch (Exception Ex) {
            System.out.println("deleitem错误");
            return false;
        }
    }
}
