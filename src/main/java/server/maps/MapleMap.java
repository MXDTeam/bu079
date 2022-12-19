package server.maps;

import server.ServerProperties;
import server.MapleCarnivalFactory.MCSkill;
import server.MapleCarnivalFactory;
import tools.Pair;
import java.sql.PreparedStatement;
import java.sql.Connection;
import server.SpeedRunner;
import database.DBConPool;
import constants.MapConstants;
import server.life.SpawnPointAreaBoss;
import server.life.SpawnPoint;
import java.awt.Rectangle;
import tools.FileoutputUtil;
import scripting.EventManager;
import java.lang.ref.WeakReference;
import server.events.MapleEvent;
import java.util.Calendar;
import client.MapleBuffStat;
import handling.world.PartyOperation;
import tools.packet.PetPacket;
import server.MapleInventoryManipulator;
import handling.channel.handler.InventoryHandler;
import handling.world.MaplePartyCharacter;
import client.inventory.MaplePet;
import java.util.Random;
import client.MapleClient;
import server.life.MapleNPC;
import java.util.concurrent.RejectedExecutionException;
import gui.进阶BOSS.扎昆BOSS线程;
import java.util.Arrays;
import gui.进阶BOSS.蘑辣插BOSS线程;
import gui.进阶BOSS.Mushplotact;
import server.MapleSquad;
import server.MapleStatEffect;
import tools.StringUtil;
import server.life.MapleLifeFactory;
import gui.进阶BOSS.捉鬼任务线程;
import gui.进阶BOSS.黑龙BOSS线程;
import tools.FilePrinter;
import handling.world.World.Broadcast;
import tools.MaplePacketCreator;
import server.Timer.MapTimer;
import tools.packet.MobPacket;
import client.inventory.IItem;
import client.status.MonsterStatusEffect;
import server.life.MonsterGlobalDropEntry;
import client.inventory.Item;
import client.inventory.Equip;
import client.inventory.MapleInventoryType;
import server.Randomizer;
import server.life.MonsterDropEntry;
import java.util.Collection;
import server.life.MapleMonsterInformationProvider;
import client.status.MonsterStatus;
import handling.world.MapleParty;
import server.MapleItemInformationProvider;
import server.life.MapleMonster;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Iterator;
import handling.channel.ChannelServer;
import java.util.Collections;
import java.util.EnumMap;
import gui.Start;
import constants.GameConstants;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;
import server.MapleSquad.MapleSquadType;
import java.util.concurrent.ScheduledFuture;
import java.util.HashMap;
import server.MaplePortal;
import java.util.concurrent.atomic.AtomicInteger;
import server.life.Spawns;
import java.util.concurrent.locks.Lock;
import client.MapleCharacter;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.LinkedHashMap;
import java.util.Map;
import server.MapleTVEffect;

public final class MapleMap
{
    private final Map<MapleMapObjectType, LinkedHashMap<Integer, MapleMapObject>> mapobjects;
    private final Map<MapleMapObjectType, ReentrantReadWriteLock> mapobjectlocks;
    private final Map<MapleMapObjectType, LinkedHashMap<Integer, MapleMapObject>> mapObjects;
    private final Map<MapleMapObjectType, ReentrantReadWriteLock> mapObjectLocks;
    private final List<MapleCharacter> characters;
    private final ReentrantReadWriteLock charactersLock;
    private int runningOid;
    private final Lock runningOidLock;
    private final Map<String, Integer> environment;
    private final List<Spawns> monsterSpawn;
    private final AtomicInteger spawnedMonstersOnMap;
    private final Map<Integer, MaplePortal> portals;
    private final List<Integer> disconnectedClients;
    private static final Map<Integer, HashMap<String, Integer>> PointsGained;
    private final byte channel;
    private final int mapid;
    private final float monsterRate;
    private float recoveryRate;
    private MapleFootholdTree footholds;
    private MapleMapEffect mapEffect;
    private short decHP;
    private short createMobInterval;
    private int consumeItemCoolTime;
    private int protectItem;
    private int decHPInterval;
    private int returnMapId;
    private int timeLimit;
    private int fieldLimit;
    private int maxRegularSpawn;
    private int fixedMob;
    private int forcedReturnMap;
    private int lvForceMove;
    private int lvLimit;
    private int permanentWeather;
    private boolean town;
    private boolean personalShop;
    private boolean everlast;
    private boolean dropsDisabled;
    private boolean gDropsDisabled;
    private boolean soaring;
    private boolean squadTimer;
    private boolean isSpawns;
    private String mapName;
    private String streetName;
    private String onUserEnter;
    private String onFirstUserEnter;
    private String speedRunLeader;
    private ScheduledFuture<?> squadSchedule;
    private ScheduledFuture<?> MulungDojoLeaveTask;
    private long speedRunStart;
    private long lastSpawnTime;
    private long lastHurtTime;
    private MapleNodes nodes;
    private MapleSquadType squad;
    private boolean clock;
    private boolean boat;
    private boolean docked;
    private boolean PapfightStart;
    private static boolean 地图buf击杀总开关;
    private static boolean 地图buf击杀开关1;
    private static boolean 地图buf击杀开关2;
    private static boolean 地图buf击杀开关3;
    private static boolean 地图buf击杀开关4;
    private static boolean 地图buf击杀开关5;
    private static boolean 地图buf击杀开关6;
    private static boolean 地图buf击杀开关7;
    private static boolean 地图buf击杀开关8;
    private static boolean 地图buf击杀开关9;
    private static int 地图buf击杀个数1;
    private static int 地图buf击杀个数2;
    private static int 地图buf击杀个数3;
    private static int 地图buf击杀个数4;
    private static int 地图buf击杀个数5;
    private static int 地图buf击杀个数6;
    private static int 地图buf击杀个数7;
    private static int 地图buf击杀个数8;
    private static int 地图buf击杀个数9;
    private static boolean 地图buf击杀喇叭开关1;
    private static boolean 地图buf击杀喇叭开关2;
    private static boolean 地图buf击杀喇叭开关3;
    private static boolean 地图buf击杀喇叭开关4;
    private static boolean 地图buf击杀喇叭开关5;
    private static boolean 地图buf击杀喇叭开关6;
    private static boolean 地图buf击杀喇叭开关7;
    private static boolean 地图buf击杀喇叭开关8;
    private static boolean 地图buf击杀喇叭开关9;
    private short top;
    private short bottom;
    private short left;
    private short right;    
    private Point monsterSpawnPos;
    private int spawnCharId = -1;
    public MapleMap(final int mapid, final int channel, final int returnMapId, final float monsterRate) {
        this.characters = (List<MapleCharacter>)new LinkedList();
        this.charactersLock = new ReentrantReadWriteLock();
        this.runningOid = 100000;
        this.runningOidLock = new ReentrantLock();
        this.environment = (Map<String, Integer>)new LinkedHashMap();
        this.monsterSpawn = (List<Spawns>)new ArrayList();
        this.spawnedMonstersOnMap = new AtomicInteger(0);
        this.portals = (Map<Integer, MaplePortal>)new HashMap();
        this.disconnectedClients = (List<Integer>)new ArrayList();
        this.footholds = null;
        this.decHP = 0;
        this.createMobInterval = 9000;
        this.consumeItemCoolTime = 0;
        this.protectItem = 0;
        this.decHPInterval = 10000;
        this.maxRegularSpawn = 0;
        this.forcedReturnMap = 999999999;
        this.lvForceMove = 0;
        this.lvLimit = 0;
        this.permanentWeather = 0;
        this.everlast = false;
        this.dropsDisabled = false;
        this.gDropsDisabled = false;
        this.soaring = false;
        this.squadTimer = false;
        this.isSpawns = true;
        this.speedRunLeader = "";
        this.squadSchedule = null;
        this.MulungDojoLeaveTask = null;
        this.speedRunStart = 0L;
        this.lastSpawnTime = 0L;
        this.lastHurtTime = 0L;
        this.PapfightStart = false;
        this.top = 0;
        this.bottom = 0;
        this.left = 0;
        this.right = 0;
        this.mapid = mapid;
        this.channel = (byte)channel;
        this.returnMapId = returnMapId;
        if (this.returnMapId == 999999999) {
            this.returnMapId = mapid;
        }
        if (GameConstants.isNotToMap(mapid)) {
            this.returnMapId = 211060000;
        }
        this.monsterRate = monsterRate;
        this.createMobInterval = Short.parseShort(String.valueOf(Start.ConfigValuesMap.get("地图刷新频率")));
        final EnumMap<MapleMapObjectType, LinkedHashMap<Integer, MapleMapObject>> objsMap = new EnumMap(MapleMapObjectType.class);
        final EnumMap<MapleMapObjectType, ReentrantReadWriteLock> objlockmap = new EnumMap(MapleMapObjectType.class);
        for (final MapleMapObjectType type : MapleMapObjectType.values()) {
            objsMap.put(type, new LinkedHashMap());
            objlockmap.put(type, new ReentrantReadWriteLock());
        }
        this.mapObjects = Collections.unmodifiableMap((Map<? extends MapleMapObjectType, ? extends LinkedHashMap<Integer, MapleMapObject>>)objsMap);
        this.mapObjectLocks = Collections.unmodifiableMap((Map<? extends MapleMapObjectType, ? extends ReentrantReadWriteLock>)objlockmap);
        this.mapobjects = Collections.unmodifiableMap((Map<? extends MapleMapObjectType, ? extends LinkedHashMap<Integer, MapleMapObject>>)objsMap);
        this.mapobjectlocks = Collections.unmodifiableMap((Map<? extends MapleMapObjectType, ? extends ReentrantReadWriteLock>)objlockmap);
    }
    
    public final void setSpawns(final boolean fm) {
        this.isSpawns = fm;
    }
    
    public final boolean getSpawns() {
        return this.isSpawns;
    }
    
    public final void setFixedMob(final int fm) {
        this.fixedMob = fm;
    }
    
    public final void setForceMove(final int fm) {
        this.lvForceMove = fm;
    }
    
    public final int getForceMove() {
        return this.lvForceMove;
    }
    
    public final void setLevelLimit(final int fm) {
        this.lvLimit = fm;
    }
    
    public final int getLevelLimit() {
        return this.lvLimit;
    }
    
    public final void setReturnMapId(final int rmi) {
        this.returnMapId = rmi;
    }
    
    public final void setSoaring(final boolean b) {
        this.soaring = b;
    }
    
    public final boolean canSoar() {
        return this.soaring;
    }
    
    public final void toggleDrops() {
        this.dropsDisabled = !this.dropsDisabled;
    }
    
    public final void setDrops(final boolean b) {
        this.dropsDisabled = b;
    }
    
    public final void toggleGDrops() {
        this.gDropsDisabled = !this.gDropsDisabled;
    }
    
    public final int getId() {
        return this.mapid;
    }
    
    public final MapleMap getReturnMap() {
        return ChannelServer.getInstance((int)this.channel).getMapFactory().getMap(this.returnMapId);
    }
    
    public final int getReturnMapId() {
        return this.returnMapId;
    }
    
    public final int getForcedReturnId() {
        return this.forcedReturnMap;
    }
    
    public final MapleMap getForcedReturnMap() {
        return ChannelServer.getInstance((int)this.channel).getMapFactory().getMap(this.forcedReturnMap);
    }
    
    public final void setForcedReturnMap(final int map) {
        this.forcedReturnMap = map;
    }
    
    public final float getRecoveryRate() {
        return this.recoveryRate;
    }
    
    public final void setRecoveryRate(final float recoveryRate) {
        this.recoveryRate = recoveryRate;
    }
    
    public final int getFieldLimit() {
        return this.fieldLimit;
    }
    
    public final void setFieldLimit(final int fieldLimit) {
        this.fieldLimit = fieldLimit;
    }
    
    public final void setCreateMobInterval(final short createMobInterval) {
        this.createMobInterval = createMobInterval;
    }
    
    public final void setTimeLimit(final int timeLimit) {
        this.timeLimit = timeLimit;
    }
    
    public final void setMapName(final String mapName) {
        this.mapName = mapName;
    }
    
    public final String getMapName() {
        return this.mapName;
    }
    
    public final String getStreetName() {
        return this.streetName;
    }
    
    public final void setFirstUserEnter(final String onFirstUserEnter) {
        this.onFirstUserEnter = onFirstUserEnter;
    }
    
    public final String getFirstUserEnter() {
        return this.onFirstUserEnter;
    }
    
    public final String getonUserEnter() {
        return this.onUserEnter;
    }
    
    public final void setUserEnter(final String onUserEnter) {
        this.onUserEnter = onUserEnter;
    }
    
    public final boolean hasClock() {
        return this.clock;
    }
    
    public final void setClock(final boolean hasClock) {
        this.clock = hasClock;
    }
    
    private int hasBoat() {
        return this.docked ? 2 : (this.boat ? 1 : 0);
    }
    
    public void setBoat(final boolean hasBoat) {
        this.boat = hasBoat;
    }
    
    public void setDocked(final boolean isDocked) {
        this.docked = isDocked;
    }
    
    public final boolean isTown() {
        return this.town;
    }
    
    public final void setTown(final boolean town) {
        this.town = town;
    }
    
    public final boolean allowPersonalShop() {
        return this.personalShop;
    }
    
    public final void setPersonalShop(final boolean personalShop) {
        this.personalShop = personalShop;
    }
    
    public final void setStreetName(final String streetName) {
        this.streetName = streetName;
    }
    
    public final void setEverlast(final boolean everlast) {
        this.everlast = everlast;
    }
    
    public final boolean getEverlast() {
        return this.everlast;
    }
    
    public final int getHPDec() {
        return this.decHP;
    }
    
    public final void setHPDec(final int delta) {
        if (delta > 0 || this.mapid == 749040100) {
            this.lastHurtTime = System.currentTimeMillis();
        }
        this.decHP = (short)delta;
    }
    
    public final int getHPDecInterval() {
        return this.decHPInterval;
    }
    
    public final void setHPDecInterval(final int delta) {
        this.decHPInterval = delta;
    }
    
    public final int getHPDecProtect() {
        return this.protectItem;
    }
    
    public final void setHPDecProtect(final int delta) {
        this.protectItem = delta;
    }
    
    public final int getCurrentPartyId() {
        this.charactersLock.readLock().lock();
        try {
            for (final MapleCharacter chr : this.characters) {
                if (chr.getPartyId() != -1) {
                    return chr.getPartyId();
                }
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
        return -1;
    }
    
    public final void addMapObject(final MapleMapObject mapobject) {
        this.runningOidLock.lock();
        int newOid;
        try {
            newOid = ++this.runningOid;
        }
        finally {
            this.runningOidLock.unlock();
        }
        mapobject.setObjectId(newOid);
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(mapobject.getType())).writeLock().lock();
        try {
            (this.mapObjects.get(mapobject.getType())).put(Integer.valueOf(newOid), mapobject);
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(mapobject.getType())).writeLock().unlock();
        }
    }
    
    private void spawnAndAddRangedMapObject(final MapleMapObject mapobject, final DelayedPacketCreation packetbakery, final SpawnCondition condition) {
        this.addMapObject(mapobject);
        this.charactersLock.readLock().lock();
        try {
            for (final MapleCharacter chr : this.characters) {
                if ((condition == null || condition.canSpawn(chr)) && !chr.isClone() && chr.getPosition().distanceSq((Point2D)mapobject.getPosition()) <= (double)GameConstants.maxViewRangeSq()) {
                    packetbakery.sendPackets(chr.getClient());
                    chr.addVisibleMapObject(mapobject);
                }
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
    }
    
    public final void removeMapObject(final MapleMapObject obj) {
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(obj.getType())).writeLock().lock();
        try {
            (this.mapObjects.get(obj.getType())).remove(Integer.valueOf(obj.getObjectId()));
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(obj.getType())).writeLock().unlock();
        }
    }
    
    public final Point calcPointBelow(final Point initial) {
        final MapleFoothold fh = this.footholds.findBelow(initial);
        if (fh == null) {
            return null;
        }
        int dropY = fh.getY1();
        final int dropX = (initial.x < this.left + 30) ? (this.left + 30) : ((initial.x > this.right - 30) ? (this.right - 30) : initial.x);
        if (!fh.isWall() && fh.getY1() != fh.getY2()) {
            final double s1 = (double)Math.abs(fh.getY2() - fh.getY1());
            final double s2 = (double)Math.abs(fh.getX2() - fh.getX1());
            if (fh.getY2() < fh.getY1()) {
                dropY = fh.getY1() - (int)(Math.cos(Math.atan(s2 / s1)) * ((double)Math.abs(initial.x - fh.getX1()) / Math.cos(Math.atan(s1 / s2))));
            }
            else {
                dropY = fh.getY1() + (int)(Math.cos(Math.atan(s2 / s1)) * ((double)Math.abs(initial.x - fh.getX1()) / Math.cos(Math.atan(s1 / s2))));
            }
        }
        return new Point(dropX, dropY);
    }
    
    public final Point calcDropPos(final Point initial, final Point fallback) {
        final Point ret = this.calcPointBelow(new Point(initial.x, initial.y - 50));
        if (ret == null) {
            return fallback;
        }
        return ret;
    }
    
    private void dropFromMonster(final MapleCharacter chr, final MapleMonster mob) {
        if (mob == null || chr == null || ChannelServer.getInstance((int)this.channel) == null || this.dropsDisabled || mob.dropsDisabled() || chr.getPyramidSubway() != null) {
            return;
        }//韩修改 无尽之塔
        if (chr.getMapId() == 450013831){   //450013831   910000040
            return;
        }
        
        if (((Integer)Start.ConfigValuesMap.get("爆物上线开关")).intValue() > 0 && (this.mapObjects.get(MapleMapObjectType.ITEM)).size() >= ((Integer)Start.ConfigValuesMap.get("爆物上线数量")).intValue()) {
            this.removeDrops();
            chr.dropMessage(6, "[系统提示] : 当前地图物品数量已经达到限制，现在已被清除。");
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final byte droptype = (byte)(mob.getStats().isExplosiveReward() ? 3 : (mob.getStats().isFfaLoot() ? 2 : ((chr.getParty() != null) ? 1 : 0)));
        final int mobpos = mob.getPosition().x;
        final int cmServerrate = ChannelServer.getInstance((int)this.channel).getMesoRate() * MapleParty.活动金币倍率;
        final int chServerrate = ChannelServer.getInstance((int)this.channel).getDropRate() * MapleParty.活动爆率倍率;
        byte d = 1;
        final Point pos = new Point(0, mob.getPosition().y);
        double showdown = 100.0;
        final MonsterStatusEffect mse = mob.getBuff(MonsterStatus.SHOWDOWN);
        if (mse != null) {
            showdown += (double)mse.getX().intValue();
        }
        final MapleMonsterInformationProvider mi = MapleMonsterInformationProvider.getInstance();
        final List<MonsterDropEntry> drops = new ArrayList(mi.retrieveDrop(mob.getId()));
        if (drops == null) {
            return;
        }
        final List<MonsterDropEntry> dropEntry = new ArrayList(mi.retrieveDrop(mob.getId()));
        if (chr.getDebugMessage()) {
            chr.dropMessage("怪物: " + mob.getId());
            chr.dropMessage("掉宝如下: ");
            for (final MonsterDropEntry de : dropEntry) {
                chr.dropMessage(" 道具: " + de.itemId + " 机率: " + (double)(de.chance * chServerrate * chr.getDropMod()) * chr.getDropm() * ((double)chr.getVipExpRate() / 100.0 + 1.0) * ((chr.getStat().realDropBuff - 100.0 <= 0.0) ? 100.0 : (chr.getStat().realDropBuff - 100.0)) / 100.0 * (showdown / 100.0) + " 最大/小掉落量: " + de.Maximum + "/" + de.Minimum);
            }
        }
        boolean mesoDropped = false;
        for (final MonsterDropEntry de2 : dropEntry) {
            if (de2.itemId == mob.getStolen()) {
                continue;
            }
            if (Randomizer.nextInt(999999) >= (int)((double)((long)(de2.chance * chServerrate) * Math.round((double)chr.getDropMod() * chr.getStat().dropBuff / 100.0)) * (showdown / 100.0) / (double)((Integer)Start.ConfigValuesMap.get("砍爆率")).intValue())) {
                continue;
            }
            if (mesoDropped && droptype != 3 && de2.itemId == 0) {
                continue;
            }
            if (de2.questid > 0 && chr.getQuestStatus((int)de2.questid) != 1) {
                continue;
            }
            if (de2.itemId / 10000 == 238 && !mob.getStats().isBoss() && chr.getMonsterBook().getLevelByCard(ii.getCardMobId(de2.itemId)) >= 2) {
                continue;
            }
            if (droptype == 3) {
                pos.x = mobpos + ((d % 2 == 0) ? (40 * (d + 1) / 2) : (-(40 * (d / 2))));
            }
            else {
                pos.x = mobpos + ((d % 2 == 0) ? (25 * (d + 1) / 2) : (-(25 * (d / 2))));
            }
            if (de2.itemId == 0) {
                final int mesos = Randomizer.nextInt(1 + Math.abs(de2.Maximum - de2.Minimum)) + de2.Minimum;
                int mmeos = (int)((double)mesos * (chr.getStat().mesoBuff / 100.0) * (double)chr.getDropMod());
                if (((Integer)Start.ConfigValuesMap.get("金币重置")).intValue() < 1 && mmeos > 0) {
                    if (((Integer)Start.ConfigValuesMap.get("金币全局砍数量")).intValue() > 0) {
                        mmeos /= ((Integer)Start.ConfigValuesMap.get("金币砍全局倍率")).intValue();
                    }
                    if (mmeos < 1) {
                        mmeos = 1;
                    }
                    this.spawnMobMesoDrop(mmeos, this.calcDropPos(pos, mob.getTruePosition()), (MapleMapObject)mob, chr, false, droptype);
                    mesoDropped = true;
                }
            }
            else if (GameConstants.getInventoryType(de2.itemId) == MapleInventoryType.EQUIP) {
                if (mob.getStats().isBoss()) {
                    if (Randomizer.nextInt(10000) <= ((Integer)Start.ConfigValuesMap.get("BOSS出装备概率")).intValue()) {
                        final IItem idrop = ii.randomizeStats((Equip)ii.getEquipById(de2.itemId));
                        if ((chr.get刷钱模式() == 1 && idrop.getItemId() != 0) || (chr.get刷钱模式() == 2 && idrop.getItemId() / 1000000 == 1)) {
                            double price;
                            if (GameConstants.isThrowingStar(idrop.getItemId()) || GameConstants.isBullet(idrop.getItemId())) {
                                price = (double)ii.getWholePrice(idrop.getItemId());
                            }
                            else {
                                price = ii.getPrice(idrop.getItemId());
                            }
                            final int recvMesos = (int)Math.max(Math.ceil(price), 0.0);
                            if (price != -1.0 && recvMesos > 0) {
                                chr.gainMeso(recvMesos, true);
                                if (recvMesos >= 10000) {
                                    chr.dropMessage(6, "掉落物品名称:" + MapleItemInformationProvider.getInstance().getName(idrop.getItemId()) + " 自动为你售卖掉获得:" + recvMesos + "金币");
                                }
                            }
                        }
                        else {
                            this.spawnMobDrop(idrop, this.calcDropPos(pos, mob.getPosition()), mob, chr, droptype, de2.questid);
                        }
                    }
                }
                else if (Randomizer.nextInt(10000) <= ((Integer)Start.ConfigValuesMap.get("出装备概率")).intValue()) {
                    final IItem idrop = ii.randomizeStats((Equip)ii.getEquipById(de2.itemId));
                    if ((chr.get刷钱模式() == 1 && idrop.getItemId() != 0) || (chr.get刷钱模式() == 2 && idrop.getItemId() / 1000000 == 1)) {
                        double price;
                        if (GameConstants.isThrowingStar(idrop.getItemId()) || GameConstants.isBullet(idrop.getItemId())) {
                            price = (double)ii.getWholePrice(idrop.getItemId());
                        }
                        else {
                            price = ii.getPrice(idrop.getItemId());
                        }
                        final int recvMesos = (int)Math.max(Math.ceil(price), 0.0);
                        if (price != -1.0 && recvMesos > 0) {
                            chr.gainMeso(recvMesos, true);
                            if (recvMesos >= 10000) {
                                chr.dropMessage(6, "掉落物品名称:" + MapleItemInformationProvider.getInstance().getName(idrop.getItemId()) + " 自动为你售卖掉获得:" + recvMesos + "金币");
                            }
                        }
                    }
                    else {
                        this.spawnMobDrop(idrop, this.calcDropPos(pos, mob.getPosition()), mob, chr, droptype, de2.questid);
                    }
                }
            }
            else {
                final int range = Math.abs(de2.Maximum - de2.Minimum);
                final IItem idrop = new Item(de2.itemId, (short)0, (short)((de2.Maximum != 1) ? (Randomizer.nextInt((range <= 0) ? 1 : range) + de2.Minimum) : 1), (byte)0);
                this.spawnMobDrop(idrop, this.calcDropPos(pos, mob.getPosition()), mob, chr, droptype, de2.questid);
            }
            ++d;
        }
        if (((Integer)Start.ConfigValuesMap.get("金币重置")).intValue() > 0 && chr.getEventInstance() == null) {
            pos.x = Math.min(Math.max(mobpos - 25 * (d / 2), this.footholds.getMinDropX() + 25), this.footholds.getMaxDropX() - d * 25);
            int mesos2 = Randomizer.nextInt(mob.getLevel()) + mob.getLevel();
            if (mesos2 > 0 && mob.getId() != 9300175) {
                final double lastMeso = (chr.getStat().realMesoBuff - 100.0 <= 0.0) ? 100.0 : (chr.getStat().realMesoBuff - 100.0);
                final int[] id = { 9420510, 6110301, 8140002, 8140101, 9420517, 8150201, 8190003, 8200012, 8610014, 9600088 };
                final int[] meso = { 1300, 1500, 1700, 1900, 2100, 2300, 2500, 2700, 2900, 3100 };
                for (int a = 0; a < id.length; ++a) {
                    if (mob.getId() == id[a]) {
                        mesos2 = meso[a];
                        break;
                    }
                }
                this.spawnMobMesoDrop((int)((double)mesos2 * (lastMeso / 100.0) * ((double)(chr.getVipExpRate() / 100) + 1.0) * (double)chr.getDropMod() * chr.getDropm() * (double)cmServerrate), this.calcDropPos(pos, mob.getTruePosition()), (MapleMapObject)mob, chr, false, droptype);
            }
        }
        if (chr.getEventInstance() == null) {
            final List<MonsterGlobalDropEntry> globalEntry = new ArrayList(mi.getGlobalDrop());
            Collections.shuffle(globalEntry);
            final int cashz = (mob.getStats().isBoss() && mob.getStats().getHPDisplayType() == 0) ? 20 : 1;
            final int cashModifier = (int)(mob.getStats().isBoss() ? 0L : ((long)(mob.getMobExp() / 1000) + mob.getMobMaxHp() / 10000L));
            for (final MonsterGlobalDropEntry de3 : globalEntry) {
                if (Randomizer.nextInt(999999) < (int)((double)((long)(de3.chance * chServerrate) * Math.round((double)chr.getDropMod() * chr.getStat().dropBuff / 100.0)) * (showdown / 100.0) / (double)((Integer)Start.ConfigValuesMap.get("砍爆率")).intValue()) && (de3.continent < 0 || (de3.continent < 10 && this.mapid / 100000000 == de3.continent) || (de3.continent < 100 && this.mapid / 10000000 == de3.continent) || (de3.continent < 1000 && this.mapid / 1000000 == de3.continent))) {
                    if (droptype == 3) {
                        pos.x = mobpos + ((d % 2 == 0) ? (40 * (d + 1) / 2) : (-(40 * (d / 2))));
                    }
                    else {
                        pos.x = mobpos + ((d % 2 == 0) ? (25 * (d + 1) / 2) : (-(25 * (d / 2))));
                    }
                    if (de3.itemId == 0) {
                        continue;
                    }
                    if (this.gDropsDisabled) {
                        continue;
                    }
                    IItem idrop;
                    if (GameConstants.getInventoryType(de3.itemId) == MapleInventoryType.EQUIP) {
                        idrop = ii.randomizeStats((Equip)ii.getEquipById(de3.itemId));
                    }
                    else {
                        idrop = new Item(de3.itemId, (short)0, (short)((de3.Maximum != 1) ? (Randomizer.nextInt(de3.Maximum - de3.Minimum) + de3.Minimum) : 1), (byte)0);
                    }
                    if (mob.getId() != 9300175) {
                        this.spawnMobDrop(idrop, this.calcDropPos(pos, mob.getPosition()), mob, chr, (byte)(de3.onlySelf ? 0 : droptype), de3.questid);
                    }
                    ++d;
                }
            }
        }
    }
    
    public void removeMonster(final MapleMonster monster) {
        if (monster == null) {
            return;
        }
        this.spawnedMonstersOnMap.decrementAndGet();
        this.broadcastMessage(MobPacket.killMonster(monster.getObjectId(), 0));
        this.removeMapObject((MapleMapObject)monster);
        monster.killed();
    }
    
    private void killMonster(final MapleMonster monster) {
        this.spawnedMonstersOnMap.decrementAndGet();
        monster.setHp(0L);
        monster.spawnRevives(this);
        this.broadcastMessage(MobPacket.killMonster(monster.getObjectId(), 1));
        this.removeMapObject((MapleMapObject)monster);
    }
    
    public final void killMonster(final MapleMonster monster, final MapleCharacter chr, final boolean withDrops, final boolean second, final byte animation) {
        this.killMonster(monster, chr, withDrops, second, animation, 0);
    }
    
    public final void killMonster(final MapleMonster monster, final MapleCharacter chr, final boolean withDrops, final boolean second, byte animation, final int lastSkill) {
        if ((monster.getId() == 8810122 || monster.getId() == 8810018) && !second) {
            MapTimer.getInstance().schedule((Runnable)new Runnable() {
                @Override
                public void run() {
                    MapleMap.this.killMonster(monster, chr, true, true, (byte)1);
                    MapleMap.this.killAllMonsters(true);
                }
            }, 3000L);
            return;
        }
        if (monster.getId() == 8820014) {
            this.killMonster(8820000);
        }
        else if (monster.getId() == 9300166) {
            animation = 2;
        }
        this.spawnedMonstersOnMap.decrementAndGet();
        this.removeMapObject((MapleMapObject)monster);
        final int dropOwner = monster.killBy(chr, lastSkill);
        this.broadcastMessage(MobPacket.killMonster(monster.getObjectId(), (int)animation));
        if (monster.getBuffToGive() > -1) {
            final int buffid = monster.getBuffToGive();
            final MapleStatEffect buff = MapleItemInformationProvider.getInstance().getItemEffect(buffid);
            this.charactersLock.readLock().lock();
            try {
                for (final MapleCharacter mc : this.characters) {
                    if (mc.isAlive()) {
                        buff.applyTo(mc);
                        switch (monster.getId()) {
                            case 8810018:
                            case 8810122:
                            case 8820001: {
                                mc.getClient().sendPacket(MaplePacketCreator.showOwnBuffEffect(buffid, 11));
                                this.broadcastMessage(mc, MaplePacketCreator.showBuffeffect(mc.getId(), buffid, 11), false);
                                continue;
                            }
                        }
                    }
                }
            }
            finally {
                this.charactersLock.readLock().unlock();
            }
        }
        this.地图杀怪(monster, chr);
        this.地图杀怪1(monster, chr);
        this.地图杀怪2(monster, chr);
        final int mobid = monster.getId();
        SpeedRunType type = SpeedRunType.NULL;
        final MapleSquad sqd = this.getSquadByMap();
        if (mobid == 8810018 && this.mapid == 240060200 && !chr.isGM()) {
            Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "经过无数次的挑战，" + chr.getName() + "所带领的队伍终于击破了闇黑龙王的远征队！你们才是龙之林的真正英雄~"));
            FilePrinter.print("HorntailLog.txt", this.MapDebug_Log());
            if (this.speedRunStart > 0L) {
                type = SpeedRunType.Horntail;
            }
            if (sqd != null) {
                this.doShrine(true);
            }
            黑龙BOSS线程.关闭进阶BOSS线程();
        }
        else if (mobid == 7130400 && this.mapid == 261040000) {
            捉鬼任务线程.关闭进阶BOSS线程();
            MapleParty.捉鬼任务频道 = 0;
        }
        else if (mobid == 7130401 && this.mapid == 261040000) {
            捉鬼任务线程.关闭进阶BOSS线程();
            MapleParty.捉鬼任务频道 = 0;
        }
        else if (mobid == 7130402 && this.mapid == 261040000) {
            捉鬼任务线程.关闭进阶BOSS线程();
            MapleParty.捉鬼任务频道 = 0;
        }
        else if (mobid == 8500002 && this.mapid == 220080001) {
            if (this.speedRunStart > 0L) {
                type = SpeedRunType.Papulatus;
            }
        }
        else if (mobid == 9400266 && this.mapid == 802000111) {
            if (this.speedRunStart > 0L) {
                type = SpeedRunType.Nameless_Magic_Monster;
            }
            if (sqd != null) {
                this.doShrine(true);
            }
        }
        else if (mobid == 9400265 && this.mapid == 802000211) {
            if (this.speedRunStart > 0L) {
                type = SpeedRunType.Vergamot;
            }
            if (sqd != null) {
                this.doShrine(true);
            }
        }
        else if (mobid == 9400270 && this.mapid == 802000411) {
            if (this.speedRunStart > 0L) {
                type = SpeedRunType.Dunas;
            }
            if (sqd != null) {
                this.doShrine(true);
            }
        }
        else if (mobid == 9400273 && this.mapid == 802000611) {
            if (this.speedRunStart > 0L) {
                type = SpeedRunType.Nibergen;
            }
            if (sqd != null) {
                this.doShrine(true);
            }
        }
        else if (mobid == 9400294 && this.mapid == 802000711) {
            if (this.speedRunStart > 0L) {
                type = SpeedRunType.Dunas_2;
            }
            if (sqd != null) {
                this.doShrine(true);
            }
        }
        else if (mobid == 9400296 && this.mapid == 802000803) {
            if (this.speedRunStart > 0L) {
                type = SpeedRunType.Core_Blaze;
            }
            if (sqd != null) {
                this.doShrine(true);
            }
        }
        else if (mobid == 9400289 && this.mapid == 802000821) {
            if (this.speedRunStart > 0L) {
                type = SpeedRunType.Aufhaven;
            }
            if (sqd != null) {
                this.doShrine(true);
            }
        }
        else if ((mobid == 9420549 || mobid == 9420544) && this.mapid == 551030200) {
            if (this.speedRunStart > 0L) {
                if (mobid == 9420549) {
                    type = SpeedRunType.Scarlion;
                }
                else {
                    type = SpeedRunType.Targa;
                }
            }
        }
        else if (mobid == 8820001 && this.mapid == 270050100) {
            Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, chr.getName() + " 经过带领的队伍经过无数次的挑战，终于击破了时间的宠兒－皮卡丘的远征队！你们才是时间神殿的真正英雄~"));
            if (this.speedRunStart > 0L) {
                type = SpeedRunType.Pink_Bean;
            }
            if (sqd != null) {
                this.doShrine(true);
            }
            FilePrinter.print("PinkbeanLog.txt", this.MapDebug_Log());
        }
        else if (mobid == 8800002 && this.mapid == 280030000) {
            FilePrinter.print("ZakumLog.txt", this.MapDebug_Log());
            if (this.speedRunStart > 0L) {
                type = SpeedRunType.Zakum;
            }
            if (sqd != null) {
                this.doShrine(true);
            }
        }
        else if (mobid == 8800102 && this.mapid == 280030001) {
            FilePrinter.print("ZakumLog.txt", this.MapDebug_Log());
            if (this.speedRunStart > 0L) {
                type = SpeedRunType.Chaos_Zakum;
            }
            if (sqd != null) {
                this.doShrine(true);
            }
        }
        else if (mobid >= 8800003 && mobid <= 8800010) {
            boolean makeZakReal = true;
            final Collection<MapleMonster> monsters = this.getAllMonstersThreadsafe();
            for (final MapleMonster mons : monsters) {
                if (mons.getId() >= 8800003 && mons.getId() <= 8800010) {
                    makeZakReal = false;
                    break;
                }
            }
            if (makeZakReal) {
                for (final MapleMapObject object : monsters) {
                    final MapleMonster mons2 = (MapleMonster)object;
                    if (mons2.getId() == 8800000) {
                        final Point pos = mons2.getPosition();
                        this.killAllMonsters(true);
                        this.spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(8800000), pos);
                        break;
                    }
                }
            }
        }
        else if (mobid >= 8800103 && mobid <= 8800110) {
            boolean makeZakReal = true;
            final Collection<MapleMonster> monsters = this.getAllMonstersThreadsafe();
            for (final MapleMonster mons : monsters) {
                if (mons.getId() >= 8800103 && mons.getId() <= 8800110) {
                    makeZakReal = false;
                    break;
                }
            }
            if (makeZakReal) {
                for (final MapleMonster mons : monsters) {
                    if (mons.getId() == 8800100) {
                        final Point pos2 = mons.getPosition();
                        this.killAllMonsters(true);
                        this.spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(8800100), pos2);
                        break;
                    }
                }
            }
        }
        if (type != SpeedRunType.NULL && this.speedRunStart > 0L && this.speedRunLeader.length() > 0) {
            final long endTime = System.currentTimeMillis();
            final String time = StringUtil.getReadableMillis(this.speedRunStart, endTime);
            this.broadcastMessage(MaplePacketCreator.serverNotice(5, this.speedRunLeader + "'远征队花了 " + time + " 时间打败了 " + type + "!"));
            this.getRankAndAdd(this.speedRunLeader, time, type, endTime - this.speedRunStart, (Collection<String>)((sqd == null) ? null : sqd.getMembers()));
            this.endSpeedRun();
        }
        if (mobid == 8820008) {
            for (final MapleMapObject mmo : this.getAllMonstersThreadsafe()) {
                final MapleMonster mons3 = (MapleMonster)mmo;
                if (mons3.getLinkOid() != monster.getObjectId()) {
                    this.killMonster(mons3, chr, false, false, animation);
                }
            }
        }
        else if (mobid >= 8820010 && mobid <= 8820014) {
            for (final MapleMapObject mmo : this.getAllMonstersThreadsafe()) {
                final MapleMonster mons3 = (MapleMonster)mmo;
                if (mons3.getId() != 8820000 && mons3.getId() != 8820001 && mons3.getObjectId() != monster.getObjectId() && mons3.getLinkOid() != monster.getObjectId()) {
                    this.killMonster(mons3, chr, false, false, animation);
                }
            }
        }
        else if (mobid == 8820108) {
            for (final MapleMapObject mmo : this.getAllMonstersThreadsafe()) {
                final MapleMonster mons3 = (MapleMonster)mmo;
                if (mons3.getLinkOid() != monster.getObjectId()) {
                    this.killMonster(mons3, chr, false, false, animation);
                }
            }
        }
        else if (mobid >= 8820300 && mobid <= 8820304) {
            for (final MapleMapObject mmo : this.getAllMonstersThreadsafe()) {
                final MapleMonster mons3 = (MapleMonster)mmo;
                if (mons3.getId() != 8820100 && mons3.getId() != 8820212 && mons3.getObjectId() != monster.getObjectId() && mons3.getLinkOid() == monster.getObjectId()) {
                    this.killMonster(mons3, chr, false, false, animation);
                }
            }
        }
        if (withDrops) {
            MapleCharacter drop = null;
            if (dropOwner <= 0) {
                drop = chr;
            }
            else {
                drop = this.getCharacterById(dropOwner);
                if (drop == null) {
                    drop = chr;
                }
            }
            this.dropFromMonster(drop, monster);
        }
    }
    
    public final void 地图杀怪1(final MapleMonster monster, final MapleCharacter chr) {
        final int mobid = monster.getId();
        if (((Integer)Start.ConfigValuesMap.get("boss击杀记录")).intValue() > 0) {
            switch (mobid) {
                case 8800002: {
                    chr.setPartyAccountidLog(chr, "击杀普通扎昆", 1);
                    break;
                }
                case 8800102: {
                    chr.setPartyAccountidLog(chr, "击杀进阶扎昆", 1);
                    break;
                }
                case 8810018: {
                    chr.setPartyAccountidLog(chr, "击杀普通黑龙", 1);
                    break;
                }
                case 8810121: {
                    chr.setPartyAccountidLog(chr, "击杀进阶黑龙", 1);
                    break;
                }
                case 8820001: {
                    chr.setPartyAccountidLog(chr, "击杀品克缤", 1);
                    break;
                }
                case 8820212: {
                    chr.setPartyAccountidLog(chr, "击杀混沌品克缤", 1);
                    break;
                }
                case 8840000: {
                    chr.setPartyAccountidLog(chr, "击杀狮子王", 1);
                    break;
                }
                case 8840007: {
                    chr.setPartyAccountidLog(chr, "击杀普通狮子王", 1);
                    break;
                }
                case 8870000: {
                    chr.setPartyAccountidLog(chr, "击杀希拉", 1);
                    break;
                }
                case 8870100: {
                    chr.setPartyAccountidLog(chr, "击杀困难希拉", 1);
                    break;
                }
                case 8850011: {
                    chr.setPartyAccountidLog(chr, "击杀希纳斯", 1);
                    break;
                }
                case 8860000: {
                    chr.setPartyAccountidLog(chr, "击杀阿卡伊勒", 1);
                    break;
                }
                case 9600086: {
                    chr.setPartyAccountidLog(chr, "击杀钻机", 1);
                    break;
                }
                case 9600087: {
                    chr.setPartyAccountidLog(chr, "击杀进阶钻机", 1);
                    break;
                }
                case 9390600: {
                    chr.setPartyAccountidLog(chr, "击杀贝勒德", 1);
                    break;
                }
                case 8920100: {
                    chr.setPartyAccountidLog(chr, "击杀普通女王", 1);
                    break;
                }
                case 8920000: {
                    chr.setPartyAccountidLog(chr, "击杀进阶女王", 1);
                    break;
                }
                case 8900100: {
                    chr.setPartyAccountidLog(chr, "击杀普通小丑", 1);
                    break;
                }
                case 8900000: {
                    chr.setPartyAccountidLog(chr, "击杀进阶小丑", 1);
                    break;
                }
                case 8910100: {
                    chr.setPartyAccountidLog(chr, "击杀普通半半", 1);
                    break;
                }
                case 8910000: {
                    chr.setPartyAccountidLog(chr, "击杀进阶半半", 1);
                    break;
                }
                case 8880010: {
                    chr.setPartyAccountidLog(chr, "击杀暴君", 1);
                    break;
                }
                case 8880002: {
                    chr.setPartyAccountidLog(chr, "击杀进阶暴君", 1);
                    break;
                }
            }
        }
    }
    
    public final void 地图杀怪2(final MapleMonster monster, final MapleCharacter chr) {
        final int mobid = monster.getId();
         if (chr.getMapId() == 450013831){
           chr.openPartyNpc(chr, 9900004, "无尽之塔");
           return;
        }
        if (((Integer)Start.ConfigValuesMap.get("击杀boss打开npc")).intValue() > 0 && monster.getStats().isBoss() && (mobid < 9800000 || mobid > 9800250)) {
            chr.openPartyNpc(chr, 9900004, "击杀" + mobid);
        }//韩增加

    }
    
    public final void 地图杀怪(final MapleMonster monster, final MapleCharacter chr) {
        final int mobid = monster.getId();
        if (mobid == 9600000 && Mushplotact.getStartState() && !Mushplotact.getThrStartState() && Mushplotact.getEggspawn() <= 5) {
            if (Mushplotact.getEggspawn() < 5) {
                Mushplotact.setEggwhere();
                Mushplotact.setMolacha(1);
            }
            Mushplotact.setEggspawn(1);
            Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[蘑菇活动]: " + chr.getName() + "击破了蘑插辣的魔力源泉，新的信息已经告知了王子"));
        }
        if (mobid == 3300008 && Mushplotact.getStartState() && Mushplotact.getThrStartState()) {
            Mushplotact.setMushroomStage("蘑菇剧情阶段", 1);
            Mushplotact.setStartState(false);
            Mushplotact.setOneStartState(false);
            Mushplotact.setOneTimeState(0L);
            Mushplotact.setSecTimeState(0L);
            Mushplotact.setThrTimeState(0L);
            蘑辣插BOSS线程.关闭进阶BOSS线程();
            Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[蘑菇活动]: 蘑菇大臣蘑辣插已经被各位勇士击杀！感谢你们拯救了蘑菇王国！"));
        }
        if (mobid == 2220000 && this.mapid == 104000400) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在海岸草丛III击杀了红蜗牛王"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("每日击杀红蜗牛王");
            chr.setBossLog("击杀高级怪物");
        }
        else if (mobid == 3220000 && this.mapid == 101030404) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在东部岩山Ⅴ击杀了树妖王"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀树妖王");
        }
        else if (mobid == 5220001 && this.mapid == 110040000) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在阳光沙滩击杀了巨居蟹"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀巨居蟹");
        }
        else if (mobid == 7220000 && this.mapid == 250010304) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在流浪熊的地盘击杀了肯德熊"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀肯德熊");
        }
        else if (mobid == 8220000 && this.mapid == 200010300) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在天空楼梯Ⅱ击杀了艾利杰"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀艾利杰");
        }
        else if (mobid == 7220002 && this.mapid == 250010503) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在妖怪森林击杀了妖怪禅师"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀妖怪禅师");
        }
        else if (mobid == 7220001 && this.mapid == 222010310) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在月岭击杀了九尾狐"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀九尾狐");
        }
        else if (mobid == 6220000 && this.mapid == 107000300) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在鳄鱼潭Ⅰ击杀了多尔"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀多尔");
        }
        else if (mobid == 5220002 && this.mapid == 100040105) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在巫婆森林Ⅰ击杀了浮士德"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀浮士德");
        }
        else if (mobid == 5220003 && this.mapid == 220050100) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在时间漩涡击杀了提莫"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀提莫");
        }
        else if (mobid == 6220001 && this.mapid == 221040301) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在哥雷草原击杀了朱诺"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀朱诺");
        }
        else if (mobid == 8220003 && this.mapid == 240040401) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在大海兽 峡谷击杀了大海兽"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀大海兽");
        }
        else if (mobid == 3220001 && this.mapid == 260010201) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在仙人掌爸爸沙漠击杀了大宇"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀大宇");
        }
        else if (mobid == 8220002 && this.mapid == 261030000) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在研究所地下秘密通道击杀了吉米拉"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀吉米拉");
        }
        else if (mobid == 4220000 && this.mapid == 230020100) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在海草之塔击杀了歇尔夫"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀歇尔夫");
        }
        else if (mobid == 6130101 && this.mapid == 100000005) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在铁甲猪公园3击杀了蘑菇王"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀蘑菇王");
        }
        else if (mobid == 6300005 && this.mapid == 105070002) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在蘑菇王之墓击杀了僵尸蘑菇王"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀僵尸蘑菇王");
        }
        else if (mobid == 8130100 && this.mapid == 105090900) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在被诅咒的寺院击杀了蝙蝠怪"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀蝙蝠怪");
        }
        else if (mobid == 9400205 && this.mapid == 800010100) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在天皇殿堂击杀了蓝蘑菇王"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀蓝蘑菇王");
        }
        else if (mobid == 9400120 && this.mapid == 801030000) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在昭和内部街道3击杀了老板"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀老板");
        }
        else if (mobid == 8220001 && this.mapid == 211040101) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在雪人谷击杀了驮狼雪人"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀驮狼雪人");
        }
        else if (mobid == 8180000 && this.mapid == 240020401) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在喷火龙栖息地击杀了火焰龙"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀火焰龙");
        }
        else if (mobid == 8180001 && this.mapid == 240020101) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在格瑞芬多森林击杀了天鹰"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀天鹰");
        }
        else if (mobid == 8220006 && this.mapid == 270030500) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在忘却之路5击杀了雷卡"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀雷卡");
        }
        else if (mobid == 8220005 && this.mapid == 270020500) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在后悔之路5击杀了玄冰独角兽"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀玄冰独角兽");
        }
        else if (mobid == 8220004 && this.mapid == 270010500) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在追忆之路5击杀了多多"));
            }
            chr.击杀野外BOSS特效();
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀多多");
        }
        else if (mobid == 8220004) {
            chr.setBossLog("蜈蚣");
            chr.击杀野外BOSS特效();
        }
        else if (mobid == 8500002 && this.mapid == 220080001) {
            if (((Integer)Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0) {
                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(4, "[野外boss]: " + chr.getName() + " 在时间塔的本源击杀了帕普拉图斯"));
            }
            chr.setBossLog("击杀高级怪物");
            chr.setBossLog("每日击杀帕普拉图斯");
        }
        else if (mobid != 9300003 || this.mapid != 103000804) {
            if (mobid == 8830000 && this.mapid == 105100300) {
                final MapleMap map = chr.getMap();
                final boolean drop = false;
                final double range = Double.POSITIVE_INFINITY;
                final List<MapleMapObject> monsters = map.getMapObjectsInRange(chr.getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER));
                for (final MapleMapObject monstermo : map.getMapObjectsInRange(chr.getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
                    final MapleMonster mob = (MapleMonster)monstermo;
                    map.killMonster(mob, chr, drop, false, (byte)1);
                }
                for (final MapleMapObject monstermo : map.getMapObjectsInRange(chr.getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
                    final MapleMonster mob = (MapleMonster)monstermo;
                    map.killMonster(mob, chr, drop, false, (byte)1);
                }
            }
            else if (mobid == 8800002 && this.mapid == 280030000) {
                final MapleMap map = chr.getMap();
                final boolean drop = false;
                final double range = Double.POSITIVE_INFINITY;
                final List<MapleMapObject> monsters = map.getMapObjectsInRange(chr.getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER));
                for (final MapleMapObject monstermo : map.getMapObjectsInRange(chr.getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
                    final MapleMonster mob = (MapleMonster)monstermo;
                    map.killMonster(mob, chr, drop, false, (byte)1);
                }
                for (final MapleMapObject monstermo : map.getMapObjectsInRange(chr.getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
                    final MapleMonster mob = (MapleMonster)monstermo;
                    map.killMonster(mob, chr, drop, false, (byte)1);
                }
                扎昆BOSS线程.关闭进阶BOSS线程();
            }
        }
    }
    
    public List<MapleReactor> getAllReactor() {
        return this.getAllReactorsThreadsafe();
    }
    
    public List<MapleReactor> getAllReactorsThreadsafe() {
        final ArrayList<MapleReactor> ret = (ArrayList<MapleReactor>)new ArrayList();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().lock();
        try {
            for (final MapleMapObject mmo : (this.mapObjects.get(MapleMapObjectType.REACTOR)).values()) {
                ret.add((MapleReactor)mmo);
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().unlock();
        }
        return ret;
    }
    
    public List<MapleMapObject> getAllDoor() {
        return this.getAllDoorsThreadsafe();
    }
    
    public List<MapleMapObject> getAllDoorsThreadsafe() {
        final ArrayList<MapleMapObject> ret = (ArrayList<MapleMapObject>)new ArrayList();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.DOOR)).readLock().lock();
        try {
            for (final MapleMapObject mmo : (this.mapObjects.get(MapleMapObjectType.DOOR)).values()) {
                ret.add(mmo);
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.DOOR)).readLock().unlock();
        }
        return ret;
    }
    
    public List<MapleMapObject> getAllMerchant() {
        return this.getAllHiredMerchantsThreadsafe();
    }
    
    public List<MapleMapObject> getAllHiredMerchantsThreadsafe() {
        final ArrayList<MapleMapObject> ret = (ArrayList<MapleMapObject>)new ArrayList();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.HIRED_MERCHANT)).readLock().lock();
        try {
            for (final MapleMapObject mmo : (this.mapObjects.get(MapleMapObjectType.HIRED_MERCHANT)).values()) {
                ret.add(mmo);
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.HIRED_MERCHANT)).readLock().unlock();
        }
        return ret;
    }
    
    public List<MapleMonster> getAllMonster() {
        return this.getAllMonstersThreadsafe();
    }
    
    public List<MapleMonster> getAllMonstersThreadsafe() {
        final ArrayList<MapleMonster> ret = (ArrayList<MapleMonster>)new ArrayList();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.MONSTER)).readLock().lock();
        try {
            for (final MapleMapObject mmo : (this.mapObjects.get(MapleMapObjectType.MONSTER)).values()) {
                ret.add((MapleMonster)mmo);
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.MONSTER)).readLock().unlock();
        }
        return ret;
    }
    
    public List<Integer> getAllUniqueMonsters() {
        final ArrayList<Integer> ret = (ArrayList<Integer>)new ArrayList();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.MONSTER)).readLock().lock();
        try {
            for (final MapleMapObject mmo : (this.mapObjects.get(MapleMapObjectType.MONSTER)).values()) {
                final int theId = ((MapleMonster)mmo).getId();
                if (!ret.contains(Integer.valueOf(theId))) {
                    ret.add(Integer.valueOf(theId));
                }
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.MONSTER)).readLock().unlock();
        }
        return ret;
    }
    
    public Collection<MapleCharacter> getNearestPvpChar(final Point attacker, final double maxRange, final double maxHeight, final Collection<MapleCharacter> chr) {
        final Collection<MapleCharacter> character = (Collection<MapleCharacter>)new LinkedList();
        for (final MapleCharacter a : this.characters) {
            if (chr.contains(a.getClient().getPlayer())) {
                final Point attackedPlayer = a.getPosition();
                final MaplePortal Port = a.getMap().findClosestSpawnpoint(a.getPosition());
                final Point nearestPort = Port.getPosition();
                final double safeDis = attackedPlayer.distance((Point2D)nearestPort);
                final double distanceX = attacker.distance(attackedPlayer.getX(), attackedPlayer.getY());
                if (MaplePvp.isLeft && attacker.x > attackedPlayer.x && distanceX < maxRange && distanceX > 2.0 && (double)attackedPlayer.y >= (double)attacker.y - maxHeight && (double)attackedPlayer.y <= (double)attacker.y + maxHeight && safeDis > 2.0) {
                    character.add(a);
                }
                if (!MaplePvp.isRight || attacker.x >= attackedPlayer.x || distanceX >= maxRange || distanceX <= 2.0 || (double)attackedPlayer.y < (double)attacker.y - maxHeight || (double)attackedPlayer.y > (double)attacker.y + maxHeight || safeDis <= 2.0) {
                    continue;
                }
                character.add(a);
            }
        }
        return character;
    }
    
    public final void KillFk(final boolean animate) {
        final List<MapleMapObject> monsters = this.getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.MONSTER));
        for (final MapleMapObject monstermo : monsters) {
            final MapleMonster monster = (MapleMonster)monstermo;
            if (monster.getId() == 3230300 || monster.getId() == 3230301) {
                this.spawnedMonstersOnMap.decrementAndGet();
                monster.setHp(0L);
                this.broadcastMessage(MobPacket.killMonster(monster.getObjectId(), (int)(animate ? 1 : 0)));
                this.removeMapObject((MapleMapObject)monster);
                monster.killed();
            }
        }
        this.broadcastMessage(MaplePacketCreator.serverNotice(6, "由于受诅咒的岩石被摧残，然而被诅咒的蝴蝶精消失了。"));
    }
    
    public final void killAllMonsters(final boolean animate) {
        for (final MapleMapObject monstermo : this.getAllMonstersThreadsafe()) {
            final MapleMonster monster = (MapleMonster)monstermo;
            this.spawnedMonstersOnMap.decrementAndGet();
            monster.setHp(0L);
            this.broadcastMessage(MobPacket.killMonster(monster.getObjectId(), (int)(animate ? 1 : 0)));
            this.removeMapObject((MapleMapObject)monster);
            monster.killed();
        }
    }
    
    public final void killMonster(final int monsId) {
        for (final MapleMapObject mmo : this.getAllMonstersThreadsafe()) {
            if (((MapleMonster)mmo).getId() == monsId) {
                this.spawnedMonstersOnMap.decrementAndGet();
                this.removeMapObject(mmo);
                this.broadcastMessage(MobPacket.killMonster(mmo.getObjectId(), 1));
                break;
            }
        }
    }
    
    private String MapDebug_Log() {
        final StringBuilder sb = new StringBuilder("击败时间 : ");
        sb.append(FilePrinter.getLocalDateString());
        sb.append(" | 地图代码 : ").append(this.mapid);
        this.charactersLock.readLock().lock();
        try {
            sb.append(" 玩家 [").append(this.characters.size()).append("] | ");
            for (final MapleCharacter mc : this.characters) {
                sb.append(mc.getName()).append(", ");
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
        return sb.toString();
    }
    
    public final void limitReactor(final int rid, final int num) {
        final List<MapleReactor> toDestroy = (List<MapleReactor>)new ArrayList();
        final Map<Integer, Integer> contained = (Map<Integer, Integer>)new LinkedHashMap();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().lock();
        try {
            for (final MapleMapObject obj : (this.mapObjects.get(MapleMapObjectType.REACTOR)).values()) {
                final MapleReactor mr = (MapleReactor)obj;
                if (contained.containsKey(Integer.valueOf(mr.getReactorId()))) {
                    if (((Integer)contained.get(Integer.valueOf(mr.getReactorId()))).intValue() >= num) {
                        toDestroy.add(mr);
                    }
                    else {
                        contained.put(Integer.valueOf(mr.getReactorId()), Integer.valueOf(((Integer)contained.get(Integer.valueOf(mr.getReactorId()))).intValue() + 1));
                    }
                }
                else {
                    contained.put(Integer.valueOf(mr.getReactorId()), Integer.valueOf(1));
                }
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().unlock();
        }
        for (final MapleReactor mr2 : toDestroy) {
            this.destroyReactor(mr2.getObjectId());
        }
    }
    
    public final void destroyReactors(final int first, final int last) {
        final List<MapleReactor> toDestroy = (List<MapleReactor>)new ArrayList();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().lock();
        try {
            for (final MapleMapObject obj : (this.mapObjects.get(MapleMapObjectType.REACTOR)).values()) {
                final MapleReactor mr = (MapleReactor)obj;
                if (mr.getReactorId() >= first && mr.getReactorId() <= last) {
                    toDestroy.add(mr);
                }
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().unlock();
        }
        for (final MapleReactor mr2 : toDestroy) {
            this.destroyReactor(mr2.getObjectId());
        }
    }
    
    public final void destroyReactor(final int oid) {
        final MapleReactor reactor = this.getReactorByOid(oid);
        this.broadcastMessage(MaplePacketCreator.destroyReactor(reactor));
        reactor.setAlive(false);
        this.removeMapObject((MapleMapObject)reactor);
        reactor.setTimerActive(false);
        if (reactor.getDelay() > 0) {
            try {
                MapTimer.getInstance().schedule((Runnable)new Runnable() {
                    @Override
                    public final void run() {
                        MapleMap.this.respawnReactor(reactor);
                    }
                }, (long)reactor.getDelay());
            }
            catch (RejectedExecutionException ex) {}
        }
    }
    
    public final void reloadReactors() {
        final List<MapleReactor> toSpawn = (List<MapleReactor>)new ArrayList();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().lock();
        try {
            for (final MapleMapObject obj : (this.mapObjects.get(MapleMapObjectType.REACTOR)).values()) {
                final MapleReactor reactor = (MapleReactor)obj;
                this.broadcastMessage(MaplePacketCreator.destroyReactor(reactor));
                reactor.setAlive(false);
                reactor.setTimerActive(false);
                toSpawn.add(reactor);
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().unlock();
        }
        for (final MapleReactor r : toSpawn) {
            this.removeMapObject((MapleMapObject)r);
            if (r.getReactorId() != 9980000 && r.getReactorId() != 9980001) {
                this.respawnReactor(r);
            }
        }
    }
    
    public final void resetReactors() {
        this.setReactorState((byte)0);
    }
    
    public final void setReactorState() {
        this.setReactorState((byte)1);
    }
    
    public final void setReactorState(final byte state) {
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().lock();
        try {
            for (final MapleMapObject obj : (this.mapObjects.get(MapleMapObjectType.REACTOR)).values()) {
                ((MapleReactor)obj).forceHitReactor(state);
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().unlock();
        }
    }
    
    public final void shuffleReactors() {
        this.shuffleReactors(0, 9999999);
    }
    
    public final void shuffleReactors(final int first, final int last) {
        final List<Point> points = (List<Point>)new ArrayList();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().lock();
        try {
            for (final MapleMapObject obj : (this.mapObjects.get(MapleMapObjectType.REACTOR)).values()) {
                final MapleReactor mr = (MapleReactor)obj;
                if (mr.getReactorId() >= first && mr.getReactorId() <= last) {
                    points.add(mr.getPosition());
                }
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().unlock();
        }
        Collections.shuffle(points);
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().lock();
        try {
            for (final MapleMapObject obj : (this.mapObjects.get(MapleMapObjectType.REACTOR)).values()) {
                final MapleReactor mr = (MapleReactor)obj;
                if (mr.getReactorId() >= first && mr.getReactorId() <= last) {
                    mr.setPosition((Point)points.remove(points.size() - 1));
                }
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().unlock();
        }
    }
    
    public final void updateMonsterController(final MapleMonster monster) {
        if (!monster.isAlive()) {
            return;
        }
        if (monster.getController() != null) {
            if (monster.getController().getMap() == this) {
                return;
            }
            monster.getController().stopControllingMonster(monster);
        }
        int mincontrolled = -1;
        MapleCharacter newController = null;
        this.charactersLock.readLock().lock();
        try {
            for (final MapleCharacter chr : this.characters) {
                if (!chr.isHidden() && !chr.isClone() && (chr.getControlledSize() < mincontrolled || mincontrolled == -1)) {
                    mincontrolled = chr.getControlledSize();
                    newController = chr;
                }
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
        if (newController != null) {
            if (monster.isFirstAttack()) {
                newController.controlMonster(monster, true);
                monster.setControllerHasAggro(true);
                monster.setControllerKnowsAboutAggro(true);
            }
            else {
                newController.controlMonster(monster, false);
            }
        }
    }
    
    public final MapleMapObject getMapObject(final int oid, final MapleMapObjectType type) {
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(type)).readLock().lock();
        try {
            return (MapleMapObject)(this.mapObjects.get(type)).get(Integer.valueOf(oid));
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(type)).readLock().unlock();
        }
    }
    
    public final boolean containsNPC(final int npcid) {
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.NPC)).readLock().lock();
        try {
            for (final MapleNPC n : (Collection<MapleNPC>)(Collection)(this.mapObjects.get(MapleMapObjectType.NPC)).values()) {
                if (n.getId() == npcid) {
                    return true;
                }
            }
            return false;
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.NPC)).readLock().unlock();
        }
    }
    
    public MapleNPC getNPCById(final int id) {
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.NPC)).readLock().lock();
        try {
            for (final MapleNPC n : (Collection<MapleNPC>)(Collection)(this.mapObjects.get(MapleMapObjectType.NPC)).values()) {
                if (n.getId() == id) {
                    return n;
                }
            }
            return null;
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.NPC)).readLock().unlock();
        }
    }
    
    public MapleMonster getMonsterById(final int id) {
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.MONSTER)).readLock().lock();
        try {
            MapleMonster ret = null;
            for (final MapleMonster n : (Collection<MapleMonster>)(Collection)(this.mapObjects.get(MapleMapObjectType.MONSTER)).values()) {
                if (n.getId() == id) {
                    ret = n;
                    break;
                }
            }
            return ret;
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.MONSTER)).readLock().unlock();
        }
    }
    
    public int countMonsterById(final int id) {
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.MONSTER)).readLock().lock();
        try {
            int ret = 0;
            for (final MapleMonster n :  (Collection<MapleMonster>)(Collection)(this.mapObjects.get(MapleMapObjectType.MONSTER)).values()) {
                if (n.getId() == id) {
                    ++ret;
                }
            }
            return ret;
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.MONSTER)).readLock().unlock();
        }
    }
    
    public MapleReactor getReactorById(final int id) {
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().lock();
        try {
            MapleReactor ret = null;
            for (final MapleReactor n : (Collection<MapleReactor>)(Collection)(this.mapObjects.get(MapleMapObjectType.REACTOR)).values()) {
                if (n.getReactorId() == id) {
                    ret = n;
                    break;
                }
            }
            return ret;
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().unlock();
        }
    }
    
    public final MapleMonster getMonsterByOid(final int oid) {
        final MapleMapObject mmo = this.getMapObject(oid, MapleMapObjectType.MONSTER);
        if (mmo == null) {
            return null;
        }
        return (MapleMonster)mmo;
    }
    
    public final MapleNPC getNPCByOid(final int oid) {
        final MapleMapObject mmo = this.getMapObject(oid, MapleMapObjectType.NPC);
        if (mmo == null) {
            return null;
        }
        return (MapleNPC)mmo;
    }
    
    public final MapleReactor getReactorByOid(final int oid) {
        final MapleMapObject mmo = this.getMapObject(oid, MapleMapObjectType.REACTOR);
        if (mmo == null) {
            return null;
        }
        return (MapleReactor)mmo;
    }
    
    public final MapleReactor getReactorByName(final String name) {
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().lock();
        try {
            for (final MapleMapObject obj : (this.mapObjects.get(MapleMapObjectType.REACTOR)).values()) {
                final MapleReactor mr = (MapleReactor)obj;
                if (mr.getName().equalsIgnoreCase(name)) {
                    return mr;
                }
            }
            return null;
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().unlock();
        }
    }
    
    public final void spawnNpc(final int id, final Point pos) {
        final MapleNPC npc = MapleLifeFactory.getNPC(id);
        npc.setPosition(pos);
        npc.setCy(pos.y);
        npc.setRx0(pos.x + 50);
        npc.setRx1(pos.x - 50);
        npc.setFh(this.getFootholds().findBelow(pos).getId());
        npc.setCustom(true);
        this.addMapObject((MapleMapObject)npc);
        this.broadcastMessage(MaplePacketCreator.spawnNPC(npc, true));
    }
    
    public final void removeNpc_(final int npcid) {
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.NPC)).writeLock().lock();
        try {
            final Iterator<MapleMapObject> itr = (this.mapObjects.get(MapleMapObjectType.NPC)).values().iterator();
            while (itr.hasNext()) {
                final MapleNPC npc = (MapleNPC)itr.next();
                if (npcid == -1 || npc.getId() == npcid) {
                    this.broadcastMessage(MaplePacketCreator.removeNPCController(npc.getObjectId()));
                    this.broadcastMessage(MaplePacketCreator.removeNPC(npc.getObjectId()));
                    itr.remove();
                }
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.NPC)).writeLock().unlock();
        }
    }
    
    public final void removeNpc(final int npcid) {
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.NPC)).writeLock().lock();
        try {
            final Iterator<MapleMapObject> itr = (this.mapObjects.get(MapleMapObjectType.NPC)).values().iterator();
            while (itr.hasNext()) {
                final MapleNPC npc = (MapleNPC)itr.next();
                if (npc.isCustom() && (npcid == -1 || npc.getId() == npcid)) {
                    this.broadcastMessage(MaplePacketCreator.removeNPCController(npc.getObjectId()));
                    this.broadcastMessage(MaplePacketCreator.removeNPC(npc.getObjectId()));
                    itr.remove();
                }
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.NPC)).writeLock().unlock();
        }
    }
    
    public final void spawnMonster_sSack(final MapleMonster mob, final Point pos, final int spawnType) {
        final Point spos = this.calcPointBelow(new Point(pos.x, pos.y - 1));
        mob.setPosition(spos);
        this.spawnMonster(mob, spawnType);
    }
    
    public final void spawnMonster_sSack(final MapleMonster mob, final Point pos, final int spawnType, final long hp) {
        final Point spos = this.calcPointBelow(new Point(pos.x, pos.y - 1));
        mob.setPosition(spos);
        mob.setHp(hp);
        this.spawnMonster(mob, spawnType);
    }
    
    public final void spawnMonsterOnGroundBelow(final MapleMonster mob, final Point pos, final long hp) {
        this.spawnMonster_sSack(mob, pos, -2, hp);
    }
    
    public final void spawnMonsterOnGroundBelow(final MapleMonster mob, final Point pos) {
        this.spawnMonster_sSack(mob, pos, -2);
    }
    
    public final int spawnMonsterWithEffectBelow(final MapleMonster mob, final Point pos, final int effect) {
        final Point spos = this.calcPointBelow(new Point(pos.x, pos.y - 1));
        return this.spawnMonsterWithEffect(mob, effect, spos);
    }
    
    public final void spawnZakum(final int x, final int y) {
        final Point pos = new Point(x, y);
        final MapleMonster mainb = MapleLifeFactory.getMonster(8800000);
        final Point spos = this.calcPointBelow(new Point(pos.x, pos.y - 1));
        mainb.setPosition(spos);
        mainb.setFake(true);
        this.spawnFakeMonster(mainb);
        final int[] array;
        final int[] zakpart = array = new int[] { 8800003, 8800004, 8800005, 8800006, 8800007, 8800008, 8800009, 8800010 };
        for (final int i : array) {
            final MapleMonster part = MapleLifeFactory.getMonster(i);
            part.setPosition(spos);
            this.spawnMonster(part, -2);
        }
        if (this.squadSchedule != null) {
            this.cancelSquadSchedule();
            this.broadcastMessage(MaplePacketCreator.stopClock());
        }
        扎昆BOSS线程.关闭进阶BOSS线程();
        扎昆BOSS线程.开启进阶BOSS线程();
    }
    
    public final void spawnChaosZakum(final int x, final int y) {
        final Point pos = new Point(x, y);
        final int[] zakpart = { 8800103, 8800104, 8800105, 8800106, 8800107, 8800108, 8800109, 8800110 };
        final Point spos = this.calcPointBelow(new Point(pos.x, pos.y - 1));
        for (final int i : zakpart) {
            final MapleMonster part = MapleLifeFactory.getMonster(i);
            part.setPosition(spos);
            this.spawnMonster(part, -2);
        }
        final MapleMonster mainb = MapleLifeFactory.getMonster(8800100);
        mainb.setPosition(spos);
        mainb.setFake(true);
        this.spawnFakeMonster(mainb);
        if (this.squadSchedule != null) {
            this.cancelSquadSchedule();
            this.broadcastMessage(MaplePacketCreator.stopClock());
        }
        扎昆BOSS线程.关闭进阶BOSS线程();
        扎昆BOSS线程.开启进阶BOSS线程();
    }
    
    public List<MapleMist> getAllMistsThreadsafe() {
        final ArrayList<MapleMist> ret = (ArrayList<MapleMist>)new ArrayList();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.MIST)).readLock().lock();
        try {
            for (final MapleMapObject mmo : (this.mapObjects.get(MapleMapObjectType.MIST)).values()) {
                ret.add((MapleMist)mmo);
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.MIST)).readLock().unlock();
        }
        return ret;
    }
    
    public final void spawnFakeMonsterOnGroundBelow(final MapleMonster mob, final Point pos) {
        final Point calcPointBelow;
        final Point spos = calcPointBelow = this.calcPointBelow(new Point(pos.x, pos.y - 1));
        --calcPointBelow.y;
        mob.setPosition(spos);
        this.spawnFakeMonster(mob);
    }
    
    private void checkRemoveAfter(final MapleMonster monster) {
        final int ra = monster.getStats().getRemoveAfter();
        if (ra > 0) {
            MapTimer.getInstance().schedule((Runnable)new Runnable() {
                @Override
                public final void run() {
                    if (monster != null && monster == MapleMap.this.getMapObject(monster.getObjectId(), monster.getType())) {
                        MapleMap.this.killMonster(monster);
                    }
                }
            }, (long)(ra * 1000));
        }
    }
    
    public final void spawnRevives(final MapleMonster monster, final int oid) {
        monster.setMap(this);
        this.checkRemoveAfter(monster);
        monster.setLinkOid(oid);
        this.spawnAndAddRangedMapObject((MapleMapObject)monster, (DelayedPacketCreation)new DelayedPacketCreation() {
            @Override
            public final void sendPackets(final MapleClient c) {
                c.sendPacket(MobPacket.spawnMonster(monster, -2, 0, oid));
            }
        }, null);
        this.updateMonsterController(monster);
        this.spawnedMonstersOnMap.incrementAndGet();
    }
    
    public final void spawnMonster(final MapleMonster monster, final int spawnType) {
        monster.setMap(this);
        this.checkRemoveAfter(monster);
        if (monster.getId() == 9300166) {
            MapTimer.getInstance().schedule((Runnable)new Runnable() {
                @Override
                public void run() {
                    MapleMap.this.broadcastMessage(MobPacket.killMonster(monster.getObjectId(), 2));
                }
            }, (long)new Random().nextInt(5000));
        }
        this.spawnAndAddRangedMapObject((MapleMapObject)monster, (DelayedPacketCreation)new DelayedPacketCreation() {
            @Override
            public final void sendPackets(final MapleClient c) {
                c.sendPacket(MobPacket.spawnMonster(monster, spawnType, 0, 0));
            }
        }, null);
        this.updateMonsterController(monster);
        this.spawnedMonstersOnMap.incrementAndGet();
    }
    
    public final int spawnMonsterWithEffect(final MapleMonster monster, final int effect, final Point pos) {
        try {
            monster.setMap(this);
            monster.setPosition(pos);
            this.spawnAndAddRangedMapObject((MapleMapObject)monster, (DelayedPacketCreation)new DelayedPacketCreation() {
                @Override
                public final void sendPackets(final MapleClient c) {
                    c.sendPacket(MobPacket.spawnMonster(monster, -2, effect, 0));
                }
            }, null);
            this.updateMonsterController(monster);
            this.spawnedMonstersOnMap.incrementAndGet();
            return monster.getObjectId();
        }
        catch (Exception e) {
            return -1;
        }
    }
    
    public final void spawnFakeMonster(final MapleMonster monster) {
        monster.setMap(this);
        monster.setFake(true);
        this.spawnAndAddRangedMapObject((MapleMapObject)monster, (DelayedPacketCreation)new DelayedPacketCreation() {
            @Override
            public final void sendPackets(final MapleClient c) {
                c.sendPacket(MobPacket.spawnMonster(monster, -2, 252, 0));
            }
        }, null);
        this.updateMonsterController(monster);
        this.spawnedMonstersOnMap.incrementAndGet();
    }
    
    public final void spawnReactor(final MapleReactor reactor) {
        reactor.setMap(this);
        this.spawnAndAddRangedMapObject((MapleMapObject)reactor, (DelayedPacketCreation)new DelayedPacketCreation() {
            @Override
            public final void sendPackets(final MapleClient c) {
                c.sendPacket(MaplePacketCreator.spawnReactor(reactor));
            }
        }, null);
    }
    
    private void respawnReactor(final MapleReactor reactor) {
        reactor.setState((byte)0);
        reactor.setAlive(true);
        this.spawnReactor(reactor);
    }
    
    public final void spawnDoor(final MapleDoor door) {
        this.spawnAndAddRangedMapObject((MapleMapObject)door, (DelayedPacketCreation)new DelayedPacketCreation() {
            @Override
            public final void sendPackets(final MapleClient c) {
                door.sendSpawnData(c);
                c.sendPacket(MaplePacketCreator.enableActions());
            }
        }, (SpawnCondition)new SpawnCondition() {
            @Override
            public final boolean canSpawn(final MapleCharacter chr) {
                return door.getTarget().getId() == chr.getMapId() || door.getOwnerId() == chr.getId() || (door.getOwner() != null && door.getOwner().getParty() != null && door.getOwner().getParty().getMemberById(chr.getId()) != null);
            }
        });
    }
    
    public final void spawnSummon(final MapleSummon summon) {
        summon.updateMap(this);
        this.spawnAndAddRangedMapObject((MapleMapObject)summon, (DelayedPacketCreation)new DelayedPacketCreation() {
            @Override
            public void sendPackets(final MapleClient c) {
                if (c != null && c.getPlayer() != null && summon != null && (!summon.isChangedMap() || summon.getOwnerId() == c.getPlayer().getId())) {
                    c.sendPacket(MaplePacketCreator.spawnSummon(summon, true));
                }
            }
        }, null);
    }
    
    public final void spawnMist(final MapleMist mist, final int duration, final boolean fake) {
        this.spawnAndAddRangedMapObject((MapleMapObject)mist, (DelayedPacketCreation)new DelayedPacketCreation() {
            @Override
            public void sendPackets(final MapleClient c) {
                mist.sendSpawnData(c);
            }
        }, null);
        final MapTimer tMan = MapTimer.getInstance();
        final ScheduledFuture<?> poisonSchedule;
        switch (mist.isPoisonMist()) {
            case 1: {
                final MapleCharacter owner = this.getCharacterById(mist.getOwnerId());
                poisonSchedule = tMan.register((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        for (final MapleMapObject mo : MapleMap.this.getMapObjectsInRect(mist.getBox(), Collections.singletonList(MapleMapObjectType.MONSTER))) {
                            if (mist.makeChanceResult() && !((MapleMonster)mo).isBuffed(MonsterStatus.POISON)) {
                                ((MapleMonster)mo).applyStatus(owner, new MonsterStatusEffect(MonsterStatus.POISON, Integer.valueOf(1), mist.getSourceSkill().getId(), null, false), true, (long)duration, ((MapleMonster)mo).getStats().isBoss(), mist.getSource());
                            }
                        }
                    }
                }, 2000L, 2500L);
                break;
            }
            case 2: {
                poisonSchedule = tMan.register((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        for (final MapleMapObject mo : MapleMap.this.getMapObjectsInRect(mist.getBox(), Collections.singletonList(MapleMapObjectType.PLAYER))) {
                            if (mist.makeChanceResult()) {
                                final MapleCharacter chr = (MapleCharacter)mo;
                                chr.addMP((int)((double)mist.getSource().getX() * ((double)chr.getStat().getMaxMp() / 100.0)));
                            }
                        }
                    }
                }, 2000L, 2500L);
                break;
            }
            default: {
                poisonSchedule = null;
                break;
            }
        }
        try {
            tMan.schedule((Runnable)new Runnable() {
                @Override
                public void run() {
                    MapleMap.this.broadcastMessage(MaplePacketCreator.removeMist(mist.getObjectId(), false));
                    MapleMap.this.removeMapObject((MapleMapObject)mist);
                    if (poisonSchedule != null) {
                        poisonSchedule.cancel(false);
                    }
                }
            }, (long)duration);
        }
        catch (RejectedExecutionException ex) {}
    }
    
    public final void disappearingItemDrop(final MapleMapObject dropper, final MapleCharacter owner, final IItem item, final Point pos) {
        final Point droppos = this.calcDropPos(pos, pos);
        final MapleMapItem drop = new MapleMapItem(item, droppos, dropper, owner, (byte)1, false);
        this.broadcastMessage(MaplePacketCreator.dropItemFromMapObject(drop, dropper.getPosition(), droppos, (byte)3), drop.getPosition());
    }
    
    public final void spawnMesoDrop(final int meso, final Point position, final MapleMapObject dropper, final MapleCharacter owner, final boolean playerDrop, final byte droptype) {
        final Point droppos = this.calcDropPos(position, position);
        final MapleMapItem mdrop = new MapleMapItem(meso, droppos, dropper, owner, droptype, playerDrop);
        this.spawnAndAddRangedMapObject((MapleMapObject)mdrop, (DelayedPacketCreation)new DelayedPacketCreation() {
            @Override
            public void sendPackets(final MapleClient c) {
                c.sendPacket(MaplePacketCreator.dropItemFromMapObject(mdrop, dropper.getPosition(), droppos, (byte)1));
            }
        }, null);
        if (((Integer)Start.ConfigValuesMap.get("特殊宠物吸取开关")).intValue() > 0 && ((Integer)Start.ConfigValuesMap.get("特殊宠物吸金开关")).intValue() > 0 && owner.getEventInstance() == null) {
            boolean 吸物状态 = false;
            int 宠物数据库ID = 0;
            if (owner.getId() == mdrop.character_ownerid) {
                for (final MaplePet pet : owner.getSummonedPets()) {
                    if (GameConstants.isNewMesoPet(pet.getPetItemId()) && pet.getPetItemId() != 0) {
                        宠物数据库ID = pet.getUniqueId();
                        吸物状态 = true;
                        break;
                    }
                }
                for (final MaplePet pet : owner.getSummonedPets()) {
                    if (pet != null && ((Integer)Start.ConfigValuesMap.get("特殊全宠物吸金开关")).intValue() > 0) {
                        吸物状态 = true;
                    }  
                }
                if (owner.getItemQuantity(((Integer)Start.ConfigValuesMap.get("自动吃药道具")).intValue(), false) > 0) {
                    吸物状态 = true;
                }
                if (吸物状态 && mdrop.getMeso() > 0) {
                    if (owner.getParty() != null && mdrop.getOwner() == owner.getId()) {
                        final List<MapleCharacter> toGive = (List<MapleCharacter>)new LinkedList();
                        final int splitMeso = mdrop.getMeso() * 40 / 100;
                        for (final MaplePartyCharacter z : owner.getParty().getMembers()) {
                            final MapleCharacter m = owner.getMap().getCharacterById(z.getId());
                            if (m != null && m.getId() != owner.getId()) {
                                toGive.add(m);
                            }
                        }
                        for (final MapleCharacter i : toGive) {
                            i.gainMeso(splitMeso / toGive.size() + (i.getStat().hasPartyBonus ? ((int)((double)mdrop.getMeso() / 20.0)) : 0), true);
                        }
                        owner.gainMeso(mdrop.getMeso() - splitMeso, true);
                    }
                    else {
                        owner.gainMeso(mdrop.getMeso(), true);
                    }
                    final byte petz = owner.getPetIndex(宠物数据库ID);
                    InventoryHandler.removeItemPet(owner, mdrop, 宠物数据库ID);
                }
            }
        }
        if (!this.everlast) {
            mdrop.registerExpire(120000L);
            if (droptype == 0 || droptype == 1) {
                mdrop.registerFFA(30000L);
            }
        }
    }
    
    public final void spawnMobMesoDrop(final int meso, final Point position, final MapleMapObject dropper, final MapleCharacter owner, final boolean playerDrop, final byte droptype) {
        final MapleMapItem mdrop = new MapleMapItem(meso, position, dropper, owner, droptype, playerDrop);
        this.spawnAndAddRangedMapObject((MapleMapObject)mdrop, (DelayedPacketCreation)new DelayedPacketCreation() {
            @Override
            public void sendPackets(final MapleClient c) {
                c.sendPacket(MaplePacketCreator.dropItemFromMapObject(mdrop, dropper.getPosition(), position, (byte)1));
            }
        }, null);
        if (((Integer)Start.ConfigValuesMap.get("特殊宠物吸取开关")).intValue() > 0 && ((Integer)Start.ConfigValuesMap.get("特殊宠物吸金开关")).intValue() > 0 && owner.getEventInstance() == null) {
            boolean 吸物状态 = false;
            int 宠物数据库ID = 0;
            if (owner.getId() == mdrop.character_ownerid) {
                for (final MaplePet pet : owner.getSummonedPets()) {
                    if (GameConstants.isNewMesoPet(pet.getPetItemId()) && pet.getPetItemId() != 0) {
                        宠物数据库ID = pet.getUniqueId();
                        吸物状态 = true;
                        break;
                    }
                }
                for (final MaplePet pet : owner.getSummonedPets()) {
                    if (pet != null && ((Integer)Start.ConfigValuesMap.get("特殊全宠物吸金开关")).intValue() > 0) {
                        吸物状态 = true;
                    }
                }
                if (((Integer)Start.ConfigValuesMap.get("道具强行宠吸开关")).intValue() > 0 && owner.getItemQuantity(((Integer)Start.ConfigValuesMap.get("宠吸道具")).intValue(), false) > 0) {
                    吸物状态 = true;
                }
                if (吸物状态 && mdrop.getMeso() > 0) {
                    if (owner.getParty() != null && mdrop.getOwner() == owner.getId()) {
                        final List<MapleCharacter> toGive = (List<MapleCharacter>)new LinkedList();
                        final int splitMeso = mdrop.getMeso() * 40 / 100;
                        for (final MaplePartyCharacter z : owner.getParty().getMembers()) {
                            final MapleCharacter m = owner.getMap().getCharacterById(z.getId());
                            if (m != null && m.getId() != owner.getId()) {
                                toGive.add(m);
                            }
                        }
                        for (final MapleCharacter i : toGive) {
                            i.gainMeso(splitMeso / toGive.size() + (i.getStat().hasPartyBonus ? ((int)((double)mdrop.getMeso() / 20.0)) : 0), true);
                        }
                        owner.gainMeso(mdrop.getMeso() - splitMeso, true);
                    }
                    else {
                        owner.gainMeso(mdrop.getMeso(), true);
                    }
                    final byte petz = owner.getPetIndex(宠物数据库ID);
                    InventoryHandler.removeItemPet(owner, mdrop, 宠物数据库ID);
                }
            }
        }
        mdrop.registerExpire(120000L);
        if (droptype == 0 || droptype == 1) {
            mdrop.registerFFA(30000L);
        }
    }
    
    public final void spawnMobDrop(final IItem idrop, final Point dropPos, final MapleMonster mob, final MapleCharacter chr, final byte droptype, final short questid) {
        final MapleMapItem mdrop = new MapleMapItem(idrop, dropPos, (MapleMapObject)mob, chr, droptype, false, (int)questid);
        this.spawnAndAddRangedMapObject((MapleMapObject)mdrop, (DelayedPacketCreation)new DelayedPacketCreation() {
            @Override
            public void sendPackets(final MapleClient c) {
                if (questid <= 0 || c.getPlayer().getQuestStatus((int)questid) == 1) {
                    c.sendPacket(MaplePacketCreator.dropItemFromMapObject(mdrop, mob.getPosition(), dropPos, (byte)1));
                }
            }
        }, null);
        if (((Integer)Start.ConfigValuesMap.get("特殊宠物吸取开关")).intValue() > 0 && ((Integer)Start.ConfigValuesMap.get("特殊宠物吸物开关")).intValue() > 0 && chr.getEventInstance() == null && !mob.getStats().isBoss()) {
            boolean 吸物状态 = false;
            int 宠物数据库ID = 0;
            if (chr.getId() == mdrop.character_ownerid) {
                for (final MaplePet pet : chr.getSummonedPets()) {
                    if (GameConstants.isNewDropPet(pet.getPetItemId()) && pet.getPetItemId() != 0) {
                        宠物数据库ID = pet.getUniqueId();
                        吸物状态 = true;
                        break;
                    }
                }
                for (final MaplePet pet : chr.getSummonedPets()) {
                    if (pet != null && ((Integer)Start.ConfigValuesMap.get("特殊全宠物吸物开关")).intValue() > 0) {
                        吸物状态 = true;
                    }
                }
                if (((Integer)Start.ConfigValuesMap.get("道具强行宠吸开关")).intValue() > 0 && chr.getItemQuantity(((Integer)Start.ConfigValuesMap.get("宠吸道具")).intValue(), false) > 0) {
                    吸物状态 = true;
                }
                for (final MaplePet pet : chr.getSummonedPets()) {
                    final List excluded = pet.getExcluded();
                    if (excluded.size() > 0) {
                        for (final Object excluded2 : excluded) {
                            if (((Integer)excluded2).intValue() == mdrop.getItemId()) {
                                吸物状态 = false;
                                break;
                            }
                        }
                    }
                }
                if (吸物状态 && ((Integer)Start.ConfigValuesMap.get("特殊宠物吸物无法使用地图开关")).intValue() > 0) {
                    for (int i = 0; i < Start.宠物不参与地图表.size(); ++i) {
                        if (this.mapid == Integer.parseInt((String)Start.宠物不参与地图表.get(i))) {
                            吸物状态 = false;
                            break;
                        }
                    }
                }
                if (吸物状态 && mdrop.getItem().getItemId() != 0 && MapleInventoryManipulator.checkSpace(chr.getClient(), mdrop.getItemId(), (int)mdrop.getItem().getQuantity(), mdrop.getItem().getOwner())) {
                    final byte petz = chr.getPetIndex(宠物数据库ID);
                    InventoryHandler.removeItemPet(chr, mdrop, (int)petz);
                    MapleInventoryManipulator.addFromDrop(chr.getClient(), mdrop.getItem(), true, mdrop.getDropper() instanceof MapleMonster, true);
                }
            }
        }
        mdrop.registerExpire(120000L);
        if (droptype == 0 || droptype == 1) {
            mdrop.registerFFA(30000L);
        }
        this.activateItemReactors(mdrop, chr.getClient());
    }
    
    public final void spawnRandDrop() {
        if (this.mapid != 910000000 || this.channel != 1) {
            return;
        }
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.ITEM)).readLock().lock();
        try {
            for (final MapleMapObject o : (this.mapObjects.get(MapleMapObjectType.ITEM)).values()) {
                if (((MapleMapItem)o).isRandDrop()) {
                    return;
                }
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.ITEM)).readLock().unlock();
        }
        MapTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                final Point pos = new Point(Randomizer.nextInt(800) + 531, -806);
                final int theItem = Randomizer.nextInt(1000);
                int itemid = 0;
                if (theItem < 950) {
                    itemid = GameConstants.normalDrops[Randomizer.nextInt(GameConstants.normalDrops.length)];
                }
                else if (theItem < 990) {
                    itemid = GameConstants.rareDrops[Randomizer.nextInt(GameConstants.rareDrops.length)];
                }
                else {
                    itemid = GameConstants.superDrops[Randomizer.nextInt(GameConstants.superDrops.length)];
                }
                MapleMap.this.spawnAutoDrop(itemid, pos);
            }
        }, 20000L);
    }
    
    public final void spawnAutoDrop(final int itemid, final Point pos) {
        IItem idrop = null;
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (GameConstants.getInventoryType(itemid) == MapleInventoryType.EQUIP) {
            idrop = ii.randomizeStats((Equip)ii.getEquipById(itemid));
        }
        else {
            idrop = new Item(itemid, (short)0, (short)1, (byte)0);
        }
        final MapleMapItem mdrop = new MapleMapItem(pos, idrop);
        this.spawnAndAddRangedMapObject((MapleMapObject)mdrop, (DelayedPacketCreation)new DelayedPacketCreation() {
            @Override
            public void sendPackets(final MapleClient c) {
                c.sendPacket(MaplePacketCreator.dropItemFromMapObject(mdrop, pos, pos, (byte)1));
            }
        }, null);
        this.broadcastMessage(MaplePacketCreator.dropItemFromMapObject(mdrop, pos, pos, (byte)0));
        mdrop.registerExpire(120000L);
    }
    
    public final void spawnItemDrop(final MapleMapObject dropper, final MapleCharacter owner, final IItem item, final Point pos, final boolean ffaDrop, final boolean playerDrop) {
        final Point droppos = this.calcDropPos(pos, pos);
        final MapleMapItem drop = new MapleMapItem(item, droppos, dropper, owner, (byte)2, playerDrop);
        this.spawnAndAddRangedMapObject((MapleMapObject)drop, (DelayedPacketCreation)new DelayedPacketCreation() {
            @Override
            public void sendPackets(final MapleClient c) {
                c.sendPacket(MaplePacketCreator.dropItemFromMapObject(drop, dropper.getPosition(), droppos, (byte)1));
            }
        }, null);
        this.broadcastMessage(MaplePacketCreator.dropItemFromMapObject(drop, dropper.getPosition(), droppos, (byte)0));
        if (!this.everlast) {
            drop.registerExpire(120000L);
            this.activateItemReactors(drop, owner.getClient());
        }
    }
    
    private void activateItemReactors(final MapleMapItem drop, final MapleClient c) {
        final IItem item = drop.getItem();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().lock();
        try {
            for (final MapleMapObject o : (this.mapObjects.get(MapleMapObjectType.REACTOR)).values()) {
                final MapleReactor react = (MapleReactor)o;
                if (react.getReactorType() == 100 && GameConstants.isCustomReactItem(react.getReactorId(), item.getItemId(), ((Integer)react.getReactItem().getLeft()).intValue()) && ((Integer)react.getReactItem().getRight()).intValue() == item.getQuantity() && react.getArea().contains(drop.getPosition()) && !react.isTimerActive()) {
                    MapTimer.getInstance().schedule((Runnable)new ActivateItemReactor(drop, react, c), 5000L);
                    react.setTimerActive(true);
                    break;
                }
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.REACTOR)).readLock().unlock();
        }
    }
    
    public int getItemsSize() {
        return (this.mapObjects.get(MapleMapObjectType.ITEM)).size();
    }
    
    public int getMobsSize() {
        return (this.mapObjects.get(MapleMapObjectType.MONSTER)).size();
    }
    
    public List<MapleMapItem> getAllItems() {
        return this.getAllItemsThreadsafe();
    }
    
    public List<MapleMapItem> getAllItemsThreadsafe() {
        final ArrayList<MapleMapItem> ret = (ArrayList<MapleMapItem>)new ArrayList();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.ITEM)).readLock().lock();
        try {
            for (final MapleMapObject mmo : (this.mapObjects.get(MapleMapObjectType.ITEM)).values()) {
                ret.add((MapleMapItem)mmo);
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.ITEM)).readLock().unlock();
        }
        return ret;
    }
    
    public final void returnEverLastItem(final MapleCharacter chr) {
        for (final MapleMapObject o : this.getAllItemsThreadsafe()) {
            final MapleMapItem item = (MapleMapItem)o;
            if (item.getOwner() == chr.getId()) {
                item.setPickedUp(true);
                this.broadcastMessage(MaplePacketCreator.removeItemFromMap(item.getObjectId(), 2, chr.getId()), item.getPosition());
                if (item.getMeso() > 0) {
                    chr.gainMeso(item.getMeso(), false);
                }
                else {
                    MapleInventoryManipulator.addFromDrop(chr.getClient(), item.getItem(), false);
                }
                this.removeMapObject((MapleMapObject)item);
            }
        }
        this.spawnRandDrop();
    }
    
    public final void talkMonster(final String msg, final int itemId, final int objectid) {
        if (itemId > 0) {
            this.startMapEffect(msg, itemId, false);
        }
        this.broadcastMessage(MobPacket.talkMonster(objectid, itemId, msg));
        this.broadcastMessage(MobPacket.removeTalkMonster(objectid));
    }
    
    public final void startMapEffect(final String msg, final int itemId) {
        this.startMapEffect(msg, itemId, false);
    }
    
    public final void startMapEffect(final String msg, final int itemId, final boolean jukebox) {
        if (this.mapEffect != null) {
            return;
        }
        (this.mapEffect = new MapleMapEffect(msg, itemId)).setJukebox(jukebox);
        this.broadcastMessage(this.mapEffect.makeStartData());
        MapTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                MapleMap.this.broadcastMessage(MapleMap.this.mapEffect.makeDestroyData());
                MapleMap.this.mapEffect = null;
            }
        }, jukebox ? 300000L : 30000L);
    }
    
    public final void startExtendedMapEffect(final String msg, final int itemId) {
        this.broadcastMessage(MaplePacketCreator.startMapEffect(msg, itemId, true));
        MapTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                MapleMap.this.broadcastMessage(MaplePacketCreator.removeMapEffect());
                MapleMap.this.broadcastMessage(MaplePacketCreator.startMapEffect(msg, itemId, false));
            }
        }, 60000L);
    }
    
    public final void startJukebox(final String msg, final int itemId) {
        this.startMapEffect(msg, itemId, true);
    }
    
    public final void addPlayer(final MapleCharacter chr) {
        final List<MapleCharacter> players = this.getAllPlayersThreadsafe();
        for (final MapleCharacter c : players) {
            if (c.getId() == chr.getId()) {
                this.removePlayer(c);
            }
        }
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.PLAYER)).writeLock().lock();
        try {
            (this.mapObjects.get(MapleMapObjectType.PLAYER)).put(Integer.valueOf(chr.getObjectId()), chr);
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.PLAYER)).writeLock().unlock();
        }
        this.charactersLock.writeLock().lock();
        try {
            this.characters.add(chr);
        }
        finally {
            this.charactersLock.writeLock().unlock();
        }
        chr.setChangeTime(true);
        if (this.mapid == 109080000 || this.mapid == 109080001 || this.mapid == 109080002 || this.mapid == 109080003 || this.mapid == 109080010 || this.mapid == 109080011 || this.mapid == 109080012) {
            chr.setCoconutTeam((int)(this.getAndSwitchTeam() ? 0 : 1));
        }
        final byte[] packet = MaplePacketCreator.spawnPlayerMapobject(chr);
        if (!chr.isHidden()) {
            this.broadcastMessage(chr, packet, false);
            if (chr.isGM() && this.speedRunStart > 0L) {
                this.endSpeedRun();
                this.broadcastMessage(MaplePacketCreator.serverNotice(5, "The speed run has ended."));
            }
        }
        else {
            this.broadcastGMMessage(chr, packet, false);
        }
        if (((Integer)Start.ConfigValuesMap.get("地图名称")).intValue() > 0) {
            chr.startMapEffect(chr.getMap().getMapName(), 5120023);
        }
        chr.set副本地图(0);
        if (!chr.isClone()) {
            if (!this.onFirstUserEnter.equals("") && this.getCharactersSize() == 1) {
                MapScriptMethods.startScript_FirstUser(chr.getClient(), this.onFirstUserEnter);
            }
            this.sendObjectPlacement(chr);
            chr.getClient().sendPacket(MaplePacketCreator.spawnPlayerMapobject(chr));
            if (!this.onUserEnter.equals("")) {
                MapScriptMethods.startScript_User(chr.getClient(), this.onUserEnter);
            }
            switch (this.mapid) {
                case 109030001:
                case 109040000:
                case 109060001:
                case 109080000:
                case 109080010: {
                    chr.getClient().sendPacket(MaplePacketCreator.showEventInstructions());
                    break;
                }
                case 809000101:
                case 809000201: {
                    chr.getClient().sendPacket(MaplePacketCreator.showEquipEffect());
                    break;
                }
            }
        }
        for (final MaplePet pet : chr.getSummonedPets()) {
            if (pet.getSummoned()) {
                this.broadcastMessage(chr, PetPacket.showPet(chr, pet, false, false), false);
                chr.getClient().sendPacket(PetPacket.showPet(chr, pet, false, false));
                chr.getClient().sendPacket(PetPacket.petStatUpdate(chr));
                chr.getClient().sendPacket(PetPacket.loadExceptionList(chr, pet));
            }
        }
        if (chr.getParty() != null && !chr.isClone()) {
            chr.silentPartyUpdate();
            chr.getClient().sendPacket(MaplePacketCreator.updateParty(chr.getClient().getChannel(), chr.getParty(), PartyOperation.SILENT_UPDATE, null));
            chr.updatePartyMemberHP();
            chr.receivePartyMemberHP();
        }
        final MapleStatEffect stat = chr.getStatForBuff(MapleBuffStat.SUMMON);
        if (stat != null && !chr.isClone()) {
            final MapleSummon summon = (MapleSummon)chr.getSummons().get(Integer.valueOf(stat.getSourceId()));
            summon.setPosition(chr.getPosition());
            try {
                summon.setFh(this.getFootholds().findBelow(chr.getPosition()).getId());
            }
            catch (NullPointerException e) {
                summon.setFh(0);
            }
            chr.addVisibleMapObject((MapleMapObject)summon);
            this.spawnSummon(summon);
        }
        if (this.mapEffect != null) {
            this.mapEffect.sendStartData(chr.getClient());
        }
        if (this.timeLimit > 0 && this.getForcedReturnMap() != null && !chr.isClone()) {
            chr.startMapTimeLimitTask(this.timeLimit, this.getForcedReturnMap());
        }
        if (chr.getBuffedValue(MapleBuffStat.MONSTER_RIDING) != null && FieldLimitType.Mount.check(this.fieldLimit)) {
            chr.cancelBuffStats(MapleBuffStat.MONSTER_RIDING);
        }
        if (this.hasBoat() == 2) {
            chr.getClient().sendPacket(MaplePacketCreator.boatPacket(true));
        }
        else if (this.hasBoat() == 1 && (chr.getMapId() != 200090000 || chr.getMapId() != 200090010)) {
            chr.getClient().sendPacket(MaplePacketCreator.boatPacket(false));
        }
        if (!chr.isClone()) {
            if (chr.getEventInstance() != null && chr.getEventInstance().isTimerStarted() && !chr.isClone()) {
                chr.getClient().sendPacket(MaplePacketCreator.getClock((int)(chr.getEventInstance().getTimeLeft() / 1000L)));
            }
            if (this.hasClock()) {
                final Calendar cal = Calendar.getInstance();
                chr.getClient().sendPacket(MaplePacketCreator.getClockTime(cal.get(11), cal.get(12), cal.get(13)));
            }
            if (chr.getCarnivalParty() != null && chr.getEventInstance() != null) {
                chr.getEventInstance().onMapLoad(chr);
            }
            MapleEvent.mapLoad(chr, (int)this.channel);
            if (this.getSquadBegin() != null && this.getSquadBegin().getTimeLeft() > 0L && this.getSquadBegin().getStatus() == 1) {
                chr.getClient().sendPacket(MaplePacketCreator.getClock((int)(this.getSquadBegin().getTimeLeft() / 1000L)));
            }
            if (this.mapid != 280030000 && this.mapid != 240060000 && this.mapid != 240060100 && this.mapid != 240060200 && this.mapid != 270050100 && this.mapid != 551030200 && this.mapid / 1000 != 105100 && this.mapid / 100 != 8020003 && this.mapid / 100 != 8020008) {
                final MapleSquad sqd = this.getSquadByMap();
                if (!this.squadTimer && sqd != null && chr.getName().equals(sqd.getLeaderName()) && !chr.isClone()) {
                    this.doShrine(false);
                    this.squadTimer = true;
                }
            }
            for (final WeakReference<MapleCharacter> chrz : chr.getClones()) {
                if (chrz.get() != null) {
                    ((MapleCharacter)chrz.get()).setPosition(new Point(chr.getPosition()));
                    ((MapleCharacter)chrz.get()).setMap(this);
                    this.addPlayer((MapleCharacter)chrz.get());
                }
            }
            if (this.mapid == 914000000) {
                chr.getClient().sendPacket(MaplePacketCreator.temporaryStats_Aran());
            }
            else if (this.mapid == 105100300 && chr.getLevel() >= 91) {
                chr.getClient().sendPacket(MaplePacketCreator.temporaryStats_Balrog(chr));
            }
            else if (this.mapid == 140090000 || this.mapid == 105100301 || this.mapid == 105100100) {
                chr.getClient().sendPacket(MaplePacketCreator.temporaryStats_Reset());
            }
        }
        if (this.permanentWeather > 0) {
            chr.getClient().sendPacket(MaplePacketCreator.startMapEffect("", this.permanentWeather, false));
        }
        if (this.getPlatforms().size() > 0) {
            chr.getClient().sendPacket(MaplePacketCreator.getMovingPlatforms(this));
        }
        if (this.environment.size() > 0) {
            chr.getClient().sendPacket(MaplePacketCreator.getUpdateEnvironment(this));
        }
        chr.cancelBuffStats(MapleBuffStat.MONSTER_RIDING);
        chr.saveToDB(false, false);
        if(MapleTVEffect.delay != 0){
           MapleTVEffect.sendTV(); 
        }
        
    }
    
    public int getNumItems() {
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.ITEM)).readLock().lock();
        try {
            return (this.mapObjects.get(MapleMapObjectType.ITEM)).size();
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.ITEM)).readLock().unlock();
        }
    }
    
    public int getNumMonsters() {
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.MONSTER)).readLock().lock();
        try {
            return (this.mapObjects.get(MapleMapObjectType.MONSTER)).size();
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.MONSTER)).readLock().unlock();
        }
    }
    
    public void doShrine(final boolean spawned) {
        if (this.squadSchedule != null) {
            this.cancelSquadSchedule();
        }
        final int mode = (this.mapid == 280030000) ? 1 : ((this.mapid == 280030001) ? 2 : ((this.mapid == 240060200 || this.mapid == 240060201) ? 3 : 0));
        final MapleSquad sqd = this.getSquadByMap();
        final EventManager em = this.getEMByMap();
        if (sqd != null && em != null && this.getCharactersSize() > 0) {
            final String leaderName = sqd.getLeaderName();
            final String state = em.getProperty("state");
            MapleMap returnMapa = this.getForcedReturnMap();
            if (returnMapa == null || returnMapa.getId() == this.mapid) {
                returnMapa = this.getReturnMap();
            }
            if (mode == 1) {
                this.broadcastMessage(MaplePacketCreator.showZakumShrine(spawned, 5));
            }
            else if (mode == 2) {
                this.broadcastMessage(MaplePacketCreator.showChaosZakumShrine(spawned, 5));
            }
            else if (mode == 3) {
                this.broadcastMessage(MaplePacketCreator.showChaosHorntailShrine(spawned, 5));
            }
            else {
                this.broadcastMessage(MaplePacketCreator.showHorntailShrine(spawned, 5));
            }
            if (mode == 1 || spawned) {
                this.broadcastMessage(MaplePacketCreator.getClock(300));
            }
            final MapleMap returnMapz = returnMapa;
            Runnable run;
            if (!spawned) {
                final List<MapleMonster> monsterz = this.getAllMonstersThreadsafe();
                final List<Integer> monsteridz = (List<Integer>)new ArrayList();
                for (final MapleMapObject m : monsterz) {
                    monsteridz.add(Integer.valueOf(m.getObjectId()));
                }
                run = new Runnable() {
                    @Override
                    public void run() {
                        final MapleSquad sqnow = MapleMap.this.getSquadByMap();
                        if (MapleMap.this.getCharactersSize() > 0 && MapleMap.this.getNumMonsters() == monsterz.size() && sqnow != null && sqnow.getStatus() == 2 && sqnow.getLeaderName().equals(leaderName) && MapleMap.this.getEMByMap().getProperty("state").equals(state)) {
                            boolean passed = monsterz.isEmpty();
                            for (final MapleMapObject m : MapleMap.this.getAllMonstersThreadsafe()) {
                                final Iterator<Integer> iterator2 = monsteridz.iterator();
                                while (iterator2.hasNext()) {
                                    final int i = ((Integer)iterator2.next()).intValue();
                                    if (m.getObjectId() == i) {
                                        passed = true;
                                        break;
                                    }
                                }
                                if (passed) {
                                    break;
                                }
                            }
                            if (passed) {
                                byte[] packet;
                                if (mode == 1) {
                                    packet = MaplePacketCreator.showZakumShrine(spawned, 0);
                                }
                                else if (mode == 2) {
                                    packet = MaplePacketCreator.showChaosZakumShrine(spawned, 0);
                                }
                                else {
                                    packet = MaplePacketCreator.showHorntailShrine(spawned, 0);
                                }
                                for (final MapleCharacter chr : MapleMap.this.getCharactersThreadsafe()) {
                                    chr.getClient().sendPacket(packet);
                                    chr.changeMap(returnMapz, returnMapz.getPortal(0));
                                }
                                MapleMap.this.checkStates("");
                                MapleMap.this.resetFully();
                            }
                        }
                    }
                };
            }
            else {
                run = new Runnable() {
                    @Override
                    public void run() {
                        final MapleSquad sqnow = MapleMap.this.getSquadByMap();
                        if (MapleMap.this.getCharactersSize() > 0 && sqnow != null && sqnow.getStatus() == 2 && sqnow.getLeaderName().equals(leaderName) && MapleMap.this.getEMByMap().getProperty("state").equals(state)) {
                            byte[] packet;
                            if (mode == 1) {
                                packet = MaplePacketCreator.showZakumShrine(spawned, 0);
                            }
                            else if (mode == 2) {
                                packet = MaplePacketCreator.showChaosZakumShrine(spawned, 0);
                            }
                            else {
                                packet = MaplePacketCreator.showHorntailShrine(spawned, 0);
                            }
                            for (final MapleCharacter chr : MapleMap.this.getCharactersThreadsafe()) {
                                chr.getClient().sendPacket(packet);
                                chr.changeMap(returnMapz, returnMapz.getPortal(0));
                            }
                            MapleMap.this.checkStates("");
                            MapleMap.this.resetFully();
                        }
                    }
                };
            }
            this.squadSchedule = MapTimer.getInstance().schedule(run, 300000L);
        }
    }
    
    public final MapleSquad getSquadByMap() {
        MapleSquadType zz = null;
        switch (this.mapid) {
            case 105100300: {
                zz = MapleSquadType.bossbalrog;
                break;
            }
            case 280030000: {
                zz = MapleSquadType.zak;
                break;
            }
            case 280030001: {
                zz = MapleSquadType.chaoszak;
                break;
            }
            case 240060000:
            case 240060100:
            case 240060200: {
                zz = MapleSquadType.horntail;
                break;
            }
            case 240060201: {
                zz = MapleSquadType.chaosht;
                break;
            }
            case 270050100: {
                zz = MapleSquadType.pinkbean;
                break;
            }
            case 802000111: {
                zz = MapleSquadType.nmm_squad;
                break;
            }
            case 802000211: {
                zz = MapleSquadType.vergamot;
                break;
            }
            case 802000411: {
                zz = MapleSquadType.dunas;
                break;
            }
            case 802000611: {
                zz = MapleSquadType.nibergen_squad;
                break;
            }
            case 802000711: {
                zz = MapleSquadType.dunas2;
                break;
            }
            case 802000801:
            case 802000802:
            case 802000803: {
                zz = MapleSquadType.core_blaze;
                break;
            }
            case 802000821:
            case 802000823: {
                zz = MapleSquadType.aufheben;
                break;
            }
            case 211070100:
            case 211070101:
            case 211070110: {
                zz = MapleSquadType.vonleon;
                break;
            }
            case 551030200: {
                zz = MapleSquadType.scartar;
                break;
            }
            case 271040100: {
                zz = MapleSquadType.cygnus;
                break;
            }
            default: {
                return null;
            }
        }
        return ChannelServer.getInstance((int)this.channel).getMapleSquad(zz);
    }
    
    public final MapleSquad getSquadBegin() {
        if (this.squad != null) {
            return ChannelServer.getInstance((int)this.channel).getMapleSquad(this.squad);
        }
        return null;
    }
    
    public final EventManager getEMByMap() {
        String em = null;
        switch (this.mapid) {
            case 105100300: {
                em = "BossBalrog";
                break;
            }
            case 280030000: {
                em = "ZakumBattle";
                break;
            }
            case 240060000:
            case 240060100:
            case 240060200: {
                em = "HorntailBattle";
                break;
            }
            case 280030001: {
                em = "ChaosZakum";
                break;
            }
            case 240060201: {
                em = "ChaosHorntail";
                break;
            }
            case 270050100: {
                em = "PinkBeanBattle";
                break;
            }
            case 802000111: {
                em = "NamelessMagicMonster";
                break;
            }
            case 802000211: {
                em = "Vergamot";
                break;
            }
            case 802000311: {
                em = "tokyo_2095";
                break;
            }
            case 802000411: {
                em = "Dunas";
                break;
            }
            case 802000611: {
                em = "Nibergen";
                break;
            }
            case 802000711: {
                em = "Dunas2";
                break;
            }
            case 802000801:
            case 802000802:
            case 802000803: {
                em = "CoreBlaze";
                break;
            }
            case 802000821:
            case 802000823: {
                em = "Aufhaven";
                break;
            }
            case 211070100:
            case 211070101:
            case 211070110: {
                em = "VonLeonBattle";
                break;
            }
            case 551030200: {
                em = "ScarTarBattle";
                break;
            }
            case 271040100: {
                em = "CygnusBattle";
                break;
            }
            case 262030300: {
                em = "HillaBattle";
                break;
            }
            case 262031300: {
                em = "DarkHillaBattle";
                break;
            }
            case 272020110:
            case 272030400: {
                em = "ArkariumBattle";
                break;
            }
            case 955000100:
            case 955000200:
            case 955000300: {
                em = "AswanOffSeason";
                break;
            }
            case 280030100: {
                em = "ZakumBattle";
                break;
            }
            case 272020200: {
                em = "Akayile";
                break;
            }
            case 689013000: {
                em = "PinkZakum";
                break;
            }
            case 703200400: {
                em = "0AllBoss";
                break;
            }
            default: {
                return null;
            }
        }
        return ChannelServer.getInstance((int)this.channel).getEventSM().getEventManager(em);
    }
    
    public final void removePlayer3(final MapleCharacter chr) {
        this.removeMapObject((MapleMapObject)chr);
        this.broadcastMessage(MaplePacketCreator.removePlayerFromMap(chr.getId()));
    }
    
    public final void removePlayer(final MapleCharacter chr) {
        if(chr.getMap().getMonsterSpawnner().right != null){
            if(chr.getMap().getMonsterSpawnner().left == chr.getId()){
                chr.getMap().setMonsterspawn(null,-1);
            }
        }
        chr.jishu = 0;
        chr.吸怪特权 = 0;
        if (this.everlast) {
            this.returnEverLastItem(chr);
        }        
        this.charactersLock.writeLock().lock();
        try {
            this.characters.remove(chr);
        }
        catch (Exception ex) {
            System.err.println("移除CHR失败" + ex);
            FileoutputUtil.outputFileError("logs/移除CHR失败.txt", (Throwable)ex);
        }
        finally {
            this.charactersLock.writeLock().unlock();
        }
        this.removeMapObject((MapleMapObject)chr);
        chr.checkFollow();
        if (chr.getMapId() == 220080001 && chr.getMap().playerCount() <= 0) {
            final MapleMap map = chr.getClient().getChannelServer().getMapFactory().getMap(220080000);
            map.EndPapfight();
            map.resetReactors();
        }
        this.broadcastMessage(MaplePacketCreator.removePlayerFromMap(chr.getId()));
        if (!chr.isClone()) {
            chr.leaveMap();
            this.checkStates(chr.getName());
            if (this.mapid == 109020001) {
                chr.canTalk(true);
            }
            for (final WeakReference<MapleCharacter> chrz : chr.getClones()) {
                if (chrz.get() != null) {
                    this.removePlayer((MapleCharacter)chrz.get());
                }
            }
        }
        chr.cancelEffectFromBuffStat(MapleBuffStat.PUPPET);
        boolean cancelSummons = false;
        for (final MapleSummon summon : chr.getSummons().values()) {
            if (summon.getMovementType() == SummonMovementType.STATIONARY || summon.getMovementType() == SummonMovementType.CIRCLE_STATIONARY || summon.getMovementType() == SummonMovementType.WALK_STATIONARY) {
                cancelSummons = true;
            }
            else {
                summon.setChangedMap(true);
                this.removeMapObject((MapleMapObject)summon);
            }
        }
        if (cancelSummons) {
            chr.cancelEffectFromBuffStat(MapleBuffStat.SUMMON);
        }
    }
    
    public final void removePlayer2(final MapleCharacter chr) {
        if (this.everlast) {
            this.returnEverLastItem(chr);
        }
        this.charactersLock.writeLock().lock();
        try {
            this.characters.remove(chr);
        }
        catch (Exception ex) {
            System.err.println("移除CHR失败" + ex);
            FileoutputUtil.outputFileError("logs/移除CHR失败.txt", (Throwable)ex);
        }
        finally {
            this.charactersLock.writeLock().unlock();
        }
        chr.updateOfflineTime();
        chr.checkFollow();
        if (chr.getMapId() == 220080001 && chr.getMap().playerCount() <= 0) {
            final MapleMap map = chr.getClient().getChannelServer().getMapFactory().getMap(220080000);
            map.EndPapfight();
            map.resetReactors();
        }
        if (!chr.isClone()) {
            chr.leaveMap();
            this.checkStates(chr.getName());
            if (this.mapid == 109020001) {
                chr.canTalk(true);
            }
            for (final WeakReference<MapleCharacter> chrz : chr.getClones()) {
                if (chrz.get() != null) {
                    this.removePlayer((MapleCharacter)chrz.get());
                }
            }
        }
        chr.cancelEffectFromBuffStat(MapleBuffStat.PUPPET);
        boolean cancelSummons = false;
        for (final MapleSummon summon : chr.getSummons().values()) {
            if (summon.getMovementType() == SummonMovementType.STATIONARY || summon.getMovementType() == SummonMovementType.CIRCLE_STATIONARY || summon.getMovementType() == SummonMovementType.WALK_STATIONARY) {
                cancelSummons = true;
            }
            else {
                summon.setChangedMap(true);
                this.removeMapObject((MapleMapObject)summon);
            }
        }
        if (cancelSummons) {
            chr.cancelEffectFromBuffStat(MapleBuffStat.SUMMON);
        }
    }
    
    public void broadcastNONGMMessage(final MapleCharacter source, final byte[] packet, final boolean repeatToSource) {
        this.broadcastNONGMMessage(repeatToSource ? null : source, packet);
    }
    
    private void broadcastNONGMMessage(final MapleCharacter source, final byte[] packet) {
        this.charactersLock.readLock().lock();
        try {
            if (source == null) {
                for (final MapleCharacter chr : this.characters) {
                    if (!chr.isStaff()) {
                        chr.getClient().getSession().writeAndFlush(packet);
                    }
                }
            }
            else {
                for (final MapleCharacter chr : this.characters) {
                    if (chr != source && chr.getGMLevel() < 3) {
                        chr.getClient().getSession().writeAndFlush(packet);
                    }
                }
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
    }
    
    public final void broadcastMessage(final byte[] packet) {
        this.broadcastMessage(null, packet, Double.POSITIVE_INFINITY, null);
    }
    
    public final void broadcastMessage(final MapleCharacter source, final byte[] packet, final boolean repeatToSource) {
        this.broadcastMessage(repeatToSource ? null : source, packet, Double.POSITIVE_INFINITY, source.getPosition());
    }
    
    public final int playerCount() {
        final List<MapleMapObject> players = this.getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.PLAYER));
        return players.size();
    }
    
    public final int mobCount() {
        final List<MapleMapObject> mobsCount = this.getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.MONSTER));
        return mobsCount.size();
    }
    
    public final void broadcastMessage(final byte[] packet, final Point rangedFrom) {
        this.broadcastMessage(null, packet, (double)GameConstants.maxViewRangeSq(), rangedFrom);
    }
    
    public final void broadcastMessage(final MapleCharacter source, final byte[] packet, final Point rangedFrom) {
        this.broadcastMessage(source, packet, (double)GameConstants.maxViewRangeSq(), rangedFrom);
    }
    
    private void broadcastMessage(final MapleCharacter source, final byte[] packet, final double rangeSq, final Point rangedFrom) {
        this.charactersLock.readLock().lock();
        try {
            for (final MapleCharacter chr : this.characters) {
                if (chr != source) {
                    if (rangeSq < Double.POSITIVE_INFINITY) {
                        if (rangedFrom.distanceSq((Point2D)chr.getPosition()) > rangeSq) {
                            continue;
                        }
                        chr.getClient().sendPacket(packet);
                    }
                    else {
                        chr.getClient().sendPacket(packet);
                    }
                }
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
    }
    
    private void sendObjectPlacement(final MapleCharacter c) {
        if (c == null || c.isClone()) {
            return;
        }
        for (final MapleMapObject o : this.getAllMonstersThreadsafe()) {
            this.updateMonsterController((MapleMonster)o);
        }
        for (final MapleMapObject o : this.getMapObjectsInRange(c.getPosition(), (double)GameConstants.maxViewRangeSq(), GameConstants.rangedMapobjectTypes)) {
            if (o.getType() == MapleMapObjectType.REACTOR && !((MapleReactor)o).isAlive()) {
                continue;
            }
            o.sendSpawnData(c.getClient());
            c.addVisibleMapObject(o);
        }
    }
    
    public final List<MapleMapObject> getMapObjectsInRange(final Point from, final double rangeSq) {
        final List<MapleMapObject> ret = (List<MapleMapObject>)new ArrayList();
        for (final MapleMapObjectType type : MapleMapObjectType.values()) {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(type)).readLock().lock();
            try {
                for (final MapleMapObject mmo : (this.mapObjects.get(type)).values()) {
                    if (from.distanceSq((Point2D)mmo.getPosition()) <= rangeSq) {
                        ret.add(mmo);
                    }
                }
            }
            finally {
                ((ReentrantReadWriteLock)this.mapObjectLocks.get(type)).readLock().unlock();
            }
        }
        return ret;
    }
    
    public List<MapleMapObject> getItemsInRange(final Point from, final double rangeSq) {
        return this.getMapObjectsInRange(from, rangeSq, Arrays.asList(MapleMapObjectType.ITEM));
    }
    
    public final List<MapleMapObject> getMapObjectsInRange(final Point from, final double rangeSq, final List<MapleMapObjectType> MapObject_types) {
        final List<MapleMapObject> ret = (List<MapleMapObject>)new ArrayList();
        for (final MapleMapObjectType type : MapObject_types) {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(type)).readLock().lock();
            try {
                for (final MapleMapObject mmo : (this.mapObjects.get(type)).values()) {
                    if (from.distanceSq((Point2D)mmo.getPosition()) <= rangeSq) {
                        ret.add(mmo);
                    }
                }
            }
            finally {
                ((ReentrantReadWriteLock)this.mapObjectLocks.get(type)).readLock().unlock();
            }
        }
        return ret;
    }
    
    public final List<MapleMapObject> getMapObjectsInRect(final Rectangle box, final List<MapleMapObjectType> MapObject_types) {
        final List<MapleMapObject> ret = (List<MapleMapObject>)new ArrayList();
        for (final MapleMapObjectType type : MapObject_types) {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(type)).readLock().lock();
            try {
                for (final MapleMapObject mmo : (this.mapObjects.get(type)).values()) {
                    if (box.contains(mmo.getPosition())) {
                        ret.add(mmo);
                    }
                }
            }
            finally {
                ((ReentrantReadWriteLock)this.mapObjectLocks.get(type)).readLock().unlock();
            }
        }
        return ret;
    }
    
    public List<MapleCharacter> getAllPlayersThreadsafe() {
        final List<MapleCharacter> ret = (List<MapleCharacter>)new LinkedList();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.PLAYER)).readLock().lock();
        try {
            for (final MapleMapObject chr : (this.mapObjects.get(MapleMapObjectType.PLAYER)).values()) {
                ret.add((MapleCharacter)chr);
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.PLAYER)).readLock().unlock();
        }
        return ret;
    }
    
    public final List<MapleCharacter> getPlayersInRectThreadsafe(final Rectangle box, final List<MapleCharacter> chrList) {
        final List<MapleCharacter> character = (List<MapleCharacter>)new LinkedList();
        this.charactersLock.readLock().lock();
        try {
            for (final MapleCharacter a : this.characters) {
                if (chrList.contains(a) && box.contains(a.getPosition())) {
                    character.add(a);
                }
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
        return character;
    }
    
    public final void addPortal(final MaplePortal myPortal) {
        this.portals.put(Integer.valueOf(myPortal.getId()), myPortal);
    }
    
    public final MaplePortal getPortal(final String portalname) {
        for (final MaplePortal port : this.portals.values()) {
            if (port.getName().equals(portalname)) {
                return port;
            }
        }
        return null;
    }
    
    public final MaplePortal getPortal(final int portalid) {
        return (MaplePortal)this.portals.get(Integer.valueOf(portalid));
    }
    
    public final void resetPortals() {
        for (final MaplePortal port : this.portals.values()) {
            port.setPortalState(true);
        }
    }
    
    public final void setFootholds(final MapleFootholdTree footholds) {
        this.footholds = footholds;
    }
    
    public final MapleFootholdTree getFootholds() {
        return this.footholds;
    }
    
    public final void loadMonsterRate(final boolean first) {
        final int spawnSize = this.monsterSpawn.size();
        this.maxRegularSpawn = Math.round((float)spawnSize * this.monsterRate);
        if (this.maxRegularSpawn < 2) {
            this.maxRegularSpawn = 2;
        }
        else if (this.maxRegularSpawn > spawnSize) {
            this.maxRegularSpawn = spawnSize - spawnSize / 15;
        }
        if (this.fixedMob > 0) {
            this.maxRegularSpawn = this.fixedMob;
        }
        final Collection<Spawns> newSpawn = (Collection<Spawns>)new LinkedList();
        final Collection<Spawns> newBossSpawn = (Collection<Spawns>)new LinkedList();
        for (final Spawns s : this.monsterSpawn) {
            if (s.getCarnivalTeam() >= 2) {
                continue;
            }
            if (s.getMonster().getStats().isBoss()) {
                newBossSpawn.add(s);
            }
            else {
                newSpawn.add(s);
            }
        }
        this.monsterSpawn.clear();
        this.monsterSpawn.addAll(newBossSpawn);
        this.monsterSpawn.addAll(newSpawn);
        if (first && spawnSize > 0) {
            this.lastSpawnTime = System.currentTimeMillis();
            if (GameConstants.isForceRespawn(this.mapid)) {
                this.createMobInterval = 15000;
            }
        }
    }
    
    public final SpawnPoint addMonsterSpawn(final MapleMonster monster, final int mobTime, final byte carnivalTeam, final String msg) {
        final Point calcPointBelow;
        final Point newpos = calcPointBelow = this.calcPointBelow(monster.getPosition());
        --calcPointBelow.y;
        final SpawnPoint sp = new SpawnPoint(monster, newpos, mobTime, carnivalTeam, msg);
        if (carnivalTeam > -1) {
            this.monsterSpawn.add(0, sp);
        }
        else {
            this.monsterSpawn.add(sp);
        }
        return sp;
    }
    
    public final void addAreaMonsterSpawn(final MapleMonster monster, Point pos1, Point pos2, Point pos3, final int mobTime, final String msg) {
        pos1 = this.calcPointBelow(pos1);
        pos2 = this.calcPointBelow(pos2);
        pos3 = this.calcPointBelow(pos3);
        if (pos1 != null) {
            final Point point = pos1;
            --point.y;
        }
        if (pos2 != null) {
            final Point point2 = pos2;
            --point2.y;
        }
        if (pos3 != null) {
            final Point point3 = pos3;
            --point3.y;
        }
        if (pos1 == null && pos2 == null && pos3 == null) {
            System.err.println("警告: 地图 " + this.mapid + ", 怪物代码 " + monster.getId() + " 召唤失败. (pos1 == null && pos2 == null && pos3 == null)");
            return;
        }
        if (pos1 != null) {
            if (pos2 == null) {
                pos2 = new Point(pos1);
            }
            if (pos3 == null) {
                pos3 = new Point(pos1);
            }
        }
        else if (pos2 != null) {
            if (pos1 == null) {
                pos1 = new Point(pos2);
            }
            if (pos3 == null) {
                pos3 = new Point(pos2);
            }
        }
        else if (pos3 != null) {
            if (pos1 == null) {
                pos1 = new Point(pos3);
            }
            if (pos2 == null) {
                pos2 = new Point(pos3);
            }
        }
        this.monsterSpawn.add(new SpawnPointAreaBoss(monster, pos1, pos2, pos3, mobTime, msg));
    }
    
    public final List<MapleCharacter> getCharacters() {
        return this.getCharactersThreadsafe();
    }
    
    public final List<MapleCharacter> getCharactersThreadsafe() {
        final List<MapleCharacter> chars = (List<MapleCharacter>)new ArrayList();
        this.charactersLock.readLock().lock();
        try {
            for (final MapleCharacter mc : this.characters) {
                chars.add(mc);
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
        return chars;
    }
    
    public MapleCharacter getCharacterByName(final String id) {
        this.charactersLock.readLock().lock();
        try {
            for (final MapleCharacter mc : this.characters) {
                if (mc.getName().equalsIgnoreCase(id)) {
                    final MapleCharacter localMapleCharacter1 = mc;
                    return localMapleCharacter1;
                }
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
        return null;
    }
    
    public final MapleCharacter getCharacterById_InMap(final int id) {
        return this.getCharacterById(id);
    }
    
    public final MapleCharacter getCharacterById(final int id) {
        this.charactersLock.readLock().lock();
        try {
            for (final MapleCharacter mc : this.characters) {
                if (mc.getId() == id) {
                    return mc;
                }
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
        return null;
    }
    
    public final void updateMapObjectVisibility(final MapleCharacter chr, final MapleMapObject mo) {
        if (chr == null || chr.isClone()) {
            return;
        }
        if (!chr.isMapObjectVisible(mo)) {
            if (mo.getType() == MapleMapObjectType.SUMMON || mo.getPosition().distanceSq((Point2D)chr.getPosition()) <= (double)GameConstants.maxViewRangeSq()) {
                chr.addVisibleMapObject(mo);
                mo.sendSpawnData(chr.getClient());
            }
        }
        else if (mo.getType() != MapleMapObjectType.SUMMON && mo.getPosition().distanceSq((Point2D)chr.getPosition()) > (double)GameConstants.maxViewRangeSq()) {
            chr.removeVisibleMapObject(mo);
            mo.sendDestroyData(chr.getClient());
        }
    }
    
    public void moveMonster(final MapleMonster monster, final Point reportedPos) {
        monster.setPosition(reportedPos);
        this.charactersLock.readLock().lock();
        try {
            for (final MapleCharacter mc : this.characters) {
                this.updateMapObjectVisibility(mc, (MapleMapObject)monster);
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
    }
    
    public void movePlayer(final MapleCharacter player, final Point newPosition) {
        player.setPosition(newPosition);
        if (!player.isClone()) {
            try {
                final Collection<MapleMapObject> visibleObjects = player.getAndWriteLockVisibleMapObjects();
                final ArrayList<MapleMapObject> copy = new ArrayList(visibleObjects);
                for (final MapleMapObject mo : copy) {
                    if (mo != null && this.getMapObject(mo.getObjectId(), mo.getType()) == mo) {
                        this.updateMapObjectVisibility(player, mo);
                    }
                    else {
                        if (mo == null) {
                            continue;
                        }
                        visibleObjects.remove(mo);
                    }
                }
                for (final MapleMapObject mo2 : this.getMapObjectsInRange(player.getPosition(), (double)GameConstants.maxViewRangeSq())) {
                    if (mo2 != null && !player.isMapObjectVisible(mo2)) {
                        mo2.sendSpawnData(player.getClient());
                        visibleObjects.add(mo2);
                    }
                }
            }
            finally {
                player.unlockWriteVisibleMapObjects();
            }
        }
    }
    
    public MaplePortal findClosestSpawnpoint(final Point from) {
        MaplePortal closest = null;
        double shortestDistance = Double.POSITIVE_INFINITY;
        for (final MaplePortal portal : this.portals.values()) {
            final double distance = portal.getPosition().distanceSq((Point2D)from);
            if (portal.getType() >= 0 && portal.getType() <= 2 && distance < shortestDistance && portal.getTargetMapId() == 999999999) {
                closest = portal;
                shortestDistance = distance;
            }
        }
        return closest;
    }
    
    public MaplePortal findClosestPortal(final Point from) {
        MaplePortal closest = this.getPortal(0);
        double shortestDistance = Double.POSITIVE_INFINITY;
        for (final MaplePortal portal : this.portals.values()) {
            final double distance = portal.getPosition().distanceSq((Point2D)from);
            if (distance < shortestDistance) {
                closest = portal;
                shortestDistance = distance;
            }
        }
        return closest;
    }
    
    public String spawnDebug() {
        final StringBuilder sb = new StringBuilder("Mapobjects in map : ");
        sb.append(this.getMapObjectSize());
        sb.append(" spawnedMonstersOnMap: ");
        sb.append(this.spawnedMonstersOnMap);
        sb.append(" spawnpoints: ");
        sb.append(this.monsterSpawn.size());
        sb.append(" maxRegularSpawn: ");
        sb.append(this.maxRegularSpawn);
        sb.append(" actual monsters: ");
        sb.append(this.getNumMonsters());
        return sb.toString();
    }
    
    public int characterSize() {
        return this.characters.size();
    }
    
    public final int getMapObjectSize() {
        return this.mapObjects.size() + this.getCharactersSize() - this.characters.size();
    }
    
    public final int getCharactersSize() {
        int ret = 0;
        this.charactersLock.readLock().lock();
        try {
            for (final MapleCharacter chr : this.characters) {
                if (!chr.isClone()) {
                    ++ret;
                }
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
        return ret;
    }
    
    public Collection<MaplePortal> getPortals() {
        return Collections.unmodifiableCollection(this.portals.values());
    }
    
    public int getSpawnedMonstersOnMap() {
        return this.spawnedMonstersOnMap.get();
    }
    
    public final void spawnKite(final MapleKite Kite) {
        this.addMapObject((MapleMapObject)Kite);
        this.broadcastMessage(Kite.makeSpawnData());
        MapTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                MapleMap.this.broadcastMessage(Kite.makeDestroyData());
                MapleMap.this.removeMapObject((MapleMapObject)Kite);
            }
        }, 3600000L);
    }
    
    public void respawn(final boolean force) {
        this.lastSpawnTime = System.currentTimeMillis();
        if (force) {
            final int numShouldSpawn = this.monsterSpawn.size() * MapConstants.isMonsterSpawn(this) - this.spawnedMonstersOnMap.get();
            if (numShouldSpawn > 0) {
                int spawned = 0;
                for (final Spawns spawnPoint : this.monsterSpawn) {
                    spawnPoint.spawnMonster(this);
                    if (++spawned >= numShouldSpawn) {
                        break;
                    }
                }
            }
        }
        else {
            final int numShouldSpawn = this.monsterSpawn.size() * MapConstants.isMonsterSpawn(this) - this.spawnedMonstersOnMap.get();
            if (numShouldSpawn > 0) {
                int spawned = 0;
                final List<Spawns> randomSpawn = new ArrayList(this.monsterSpawn);
                Collections.shuffle(randomSpawn);
                for (final Spawns spawnPoint2 : randomSpawn) {
                    if (spawnPoint2.shouldSpawn() || MapConstants.isForceRespawn(this.mapid)) {
                        for (int i = 0; i < MapConstants.isMonsterSpawn(this); ++i) {
                            spawnPoint2.spawnMonster(this);
                            ++spawned;
                        }
                    }
                    if (spawned >= numShouldSpawn && !GameConstants.isCarnivalMaps(this.mapid)) {
                        break;
                    }
                }
            }
        }
    }
    
    public String getSnowballPortal() {
        final int[] teamss = new int[2];
        for (final MapleCharacter chr : this.getCharactersThreadsafe()) {
            if (chr.getPosition().y > -80) {
                final int[] array = teamss;
                final int n = 0;
                ++array[n];
            }
            else {
                final int[] array2 = teamss;
                final int n2 = 1;
                ++array2[n2];
            }
        }
        if (teamss[0] > teamss[1]) {
            return "st01";
        }
        return "st00";
    }
    
    public boolean isDisconnected(final int id) {
        return this.disconnectedClients.contains(Integer.valueOf(id));
    }
    
    public void addDisconnected(final int id) {
        this.disconnectedClients.add(Integer.valueOf(id));
    }
    
    public void resetDisconnected() {
        this.disconnectedClients.clear();
    }
    
    public void startSpeedRun() {
        final MapleSquad squad = this.getSquadByMap();
        if (squad != null) {
            for (final MapleCharacter chr : this.getCharactersThreadsafe()) {
                if (chr.getName().equals(squad.getLeaderName())) {
                    this.startSpeedRun(chr.getName());
                    return;
                }
            }
        }
    }
    
    public void startSpeedRun(final String leader) {
        this.speedRunStart = System.currentTimeMillis();
        this.speedRunLeader = leader;
    }
    
    public void endSpeedRun() {
        this.speedRunStart = 0L;
        this.speedRunLeader = "";
    }
    
    public boolean getPapfight() {
        return this.PapfightStart;
    }
    
    public void Papfight() {
        this.PapfightStart = true;
    }
    
    public void EndPapfight() {
        this.PapfightStart = false;
    }
    
    public static int getMerchantMap(final MapleCharacter chr) {
        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            final int map = cs.getMerchantMap(chr);
            if (map != -1) {
                return map;
            }
        }
        return -1;
    }
    
    public static int getMerchantChannel(final MapleCharacter chr) {
        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            final int map = cs.getMerchantMap(chr);
            if (map != -1) {
                return cs.getChannel();
            }
        }
        return -1;
    }
    
    public void getRankAndAdd(final String leader, final String time, final SpeedRunType type, final long timz, final Collection<String> squad) {
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            final StringBuilder rett = new StringBuilder();
            if (squad != null) {
                for (final String chr : squad) {
                    rett.append(chr);
                    rett.append(",");
                }
            }
            String z = rett.toString();
            if (squad != null) {
                z = z.substring(0, z.length() - 1);
            }
            final PreparedStatement ps = con.prepareStatement("INSERT INTO speedruns(`type`, `leader`, `timestring`, `time`, `members`) VALUES (?,?,?,?,?)");
            ps.setString(1, type.name());
            ps.setString(2, leader);
            ps.setString(3, time);
            ps.setLong(4, timz);
            ps.setString(5, z);
            ps.executeUpdate();
            ps.close();
            if (SpeedRunner.getInstance().getSpeedRunData(type) == null) {
                SpeedRunner.getInstance().addSpeedRunData(type, SpeedRunner.getInstance().addSpeedRunData(new StringBuilder("#rThese are the speedrun times for " + type + ".#k\r\n\r\n"), new HashMap(), z, leader, 1, time));
            }
            else {
                SpeedRunner.getInstance().removeSpeedRunData(type);
                SpeedRunner.getInstance().loadSpeedRunData(type);
            }
            con.close();
        }
        catch (Exception e) {
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
            e.printStackTrace();
        }
    }
    
    public long getSpeedRunStart() {
        return this.speedRunStart;
    }
    
    public final void disconnectAll(final MapleCharacter chr) {
        for (final MapleCharacter chrs : this.getCharactersThreadsafe()) {
            if (chrs.getGMLevel() < chr.getGMLevel()) {
                chrs.getClient().disconnect(true, false);
                chrs.getClient().getSession().close();
            }
        }
    }
    
    public final void disconnectAll() {
        for (final MapleCharacter chr : this.getCharactersThreadsafe()) {
            if (!chr.isGM()) {
                chr.getClient().disconnect(true, false);
                chr.getClient().getSession().close();
            }
        }
    }
    
    public List<MapleNPC> getAllNPCs() {
        return this.getAllNPCsThreadsafe();
    }
    
    public List<MapleNPC> getAllNPCsThreadsafe() {
        final ArrayList<MapleNPC> ret = (ArrayList<MapleNPC>)new ArrayList();
        ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.NPC)).readLock().lock();
        try {
            for (final MapleMapObject mmo : (this.mapObjects.get(MapleMapObjectType.NPC)).values()) {
                ret.add((MapleNPC)mmo);
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapObjectLocks.get(MapleMapObjectType.NPC)).readLock().unlock();
        }
        return ret;
    }
    public final List<MapleMapObject> getMonsters() {
        return getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.MONSTER));
    }
     public final List<MapleMonster> getAllMonsters() {
        List<MapleMonster> list = new LinkedList<>();
        for (MapleMapObject mmo : getMonsters()) {
            list.add((MapleMonster) mmo);
        }
        
        return list;
    }
     public void setMonsterspawn (Point position, int chrId){
        monsterSpawnPos = position;
        spawnCharId = chrId;
    }
    public static Map<Integer, Integer> 吸怪地图 = new LinkedHashMap<>();
    
    public Pair<Integer, Point> getMonsterSpawnner () {
        return new Pair<Integer,Point>(spawnCharId,monsterSpawnPos);
    }
    public final void resetNPCs() {
        this.removeNpc(-1);
    }
    
    public final void resetFully() {
        this.resetFully(true);
    }
    
    public final void resetFully(final boolean respawn) {
        this.killAllMonsters(false);
        this.reloadReactors();
        this.removeDrops();
        this.resetNPCs();
        this.resetSpawns();
        this.resetDisconnected();
        this.endSpeedRun();
        this.cancelSquadSchedule();
        this.resetPortals();
        this.environment.clear();
        if (this.MulungDojoLeaveTask != null && !this.MulungDojoLeaveTask.isCancelled()) {
            this.MulungDojoLeaveTask.cancel(true);
            this.MulungDojoLeaveTask = null;
        }
        if (respawn) {
            this.respawn(true);
        }
    }
    
    public void setMulungDojoLeaveTask(final ScheduledFuture<?> task) {
        this.MulungDojoLeaveTask = task;
    }
    
    public final void cancelSquadSchedule() {
        this.squadTimer = false;
        if (this.squadSchedule != null) {
            this.squadSchedule.cancel(false);
            this.squadSchedule = null;
        }
    }
    
    public final void removeDrops() {
        final List<MapleMapItem> items = this.getAllItemsThreadsafe();
        for (final MapleMapItem i : items) {
            i.expire(this);
        }
    }
    
    public final void resetAllSpawnPoint(final int mobid, final int mobTime) {
        final Collection<Spawns> sss = new LinkedList(this.monsterSpawn);
        this.resetFully();
        this.monsterSpawn.clear();
        for (final Spawns s : sss) {
            final MapleMonster newMons = MapleLifeFactory.getMonster(mobid);
            final MapleMonster oldMons = s.getMonster();
            newMons.setCy(oldMons.getCy());
            newMons.setF(oldMons.getF());
            newMons.setFh(oldMons.getFh());
            newMons.setRx0(oldMons.getRx0());
            newMons.setRx1(oldMons.getRx1());
            newMons.setPosition(new Point(oldMons.getPosition()));
            newMons.setHide(oldMons.isHidden());
            this.addMonsterSpawn(newMons, mobTime, (byte)(-1), null);
        }
        this.loadMonsterRate(true);
    }
    
    public final void resetSpawns() {
        boolean changed = false;
        final Iterator<Spawns> sss = this.monsterSpawn.iterator();
        while (sss.hasNext()) {
            if (((Spawns)sss.next()).getCarnivalId() > -1) {
                sss.remove();
                changed = true;
            }
        }
        this.setSpawns(true);
        if (changed) {
            this.loadMonsterRate(true);
        }
    }
    
    public final boolean makeCarnivalSpawn(final int team, final MapleMonster newMons, final int num) {
        server.maps.MapleNodes.MonsterPoint ret = null;
        for (final server.maps.MapleNodes.MonsterPoint mp : this.nodes.getMonsterPoints()) {
            if (mp.team == team || mp.team == -1) {
                final Point calcPointBelow;
                final Point newpos = calcPointBelow = this.calcPointBelow(new Point(mp.x, mp.y));
                --calcPointBelow.y;
                boolean found = false;
                for (final Spawns s : this.monsterSpawn) {
                    if (s.getCarnivalId() > -1 && (mp.team == -1 || s.getCarnivalTeam() == mp.team) && s.getPosition().x == newpos.x && s.getPosition().y == newpos.y) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    ret = mp;
                    break;
                }
                continue;
            }
        }
        if (ret != null) {
            newMons.setCy(ret.cy);
            newMons.setF(0);
            newMons.setFh(ret.fh);
            newMons.setRx0(ret.x + 50);
            newMons.setRx1(ret.x - 50);
            newMons.setPosition(new Point(ret.x, ret.y));
            newMons.setHide(false);
            newMons.setCarnivalTeam((byte)team);
            final SpawnPoint sp = this.addMonsterSpawn(newMons, 1, (byte)team, null);
            sp.setCarnival(num);
        }
        return ret != null;
    }
    
    public final boolean makeCarnivalReactor(final int team, final int num) {
        final MapleReactor old = this.getReactorByName(team + "" + num);
        if (old != null && old.getState() < 5) {
            return false;
        }
        Point guardz = null;
        final List<MapleReactor> react = this.getAllReactorsThreadsafe();
        for (final Pair<Point, Integer> guard : this.nodes.getGuardians()) {
            if (((Integer)guard.right).intValue() == team || ((Integer)guard.right).intValue() == -1) {
                boolean found = false;
                for (final MapleReactor r : react) {
                    if (r.getPosition().x == ((Point)guard.left).x && r.getPosition().y == ((Point)guard.left).y && r.getState() < 5) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    guardz = (Point)guard.left;
                    break;
                }
                continue;
            }
        }
        if (guardz != null) {
            final MapleReactorStats stats = MapleReactorFactory.getReactor(9980000 + team);
            final MapleReactor my = new MapleReactor(stats, 9980000 + team);
            stats.setFacingDirection((byte)0);
            my.setPosition(guardz);
            my.setState((byte)1);
            my.setDelay(0);
            my.setName(team + "" + num);
            this.spawnReactor(my);
            final MCSkill skil = MapleCarnivalFactory.getInstance().getGuardian(num);
            if (skil != null && skil.getMobSkill() != null) {
                for (final MapleMonster mons : this.getAllMonstersThreadsafe()) {
                    if (mons.getCarnivalTeam() == team) {
                        skil.getMobSkill().applyEffect(null, mons, false);
                    }
                }
            }
        }
        return guardz != null;
    }
    
    public final void blockAllPortal() {
        for (final MaplePortal p : this.portals.values()) {
            p.setPortalState(false);
        }
    }
    
    public boolean getAndSwitchTeam() {
        return this.getCharactersSize() % 2 != 0;
    }
    
    public void setSquad(final MapleSquadType s) {
        this.squad = s;
    }
    
    public int getChannel() {
        return this.channel;
    }
    
    public int getConsumeItemCoolTime() {
        return this.consumeItemCoolTime;
    }
    
    public void setConsumeItemCoolTime(final int ciit) {
        this.consumeItemCoolTime = ciit;
    }
    
    public void setPermanentWeather(final int pw) {
        this.permanentWeather = pw;
    }
    
    public int getPermanentWeather() {
        return this.permanentWeather;
    }
    
    public void checkStates(final String chr) {
        final MapleSquad sqd = this.getSquadByMap();
        final EventManager em = this.getEMByMap();
        final int size = this.getCharactersSize();
        if (sqd != null) {
            sqd.removeMember(chr);
            if (em != null) {
                if (sqd.getLeaderName().equals(chr)) {
                    em.setProperty("leader", "false");
                }
                if (chr.equals("") || size == 0) {
                    sqd.clear();
                    em.setProperty("state", "0");
                    em.setProperty("leader", "true");
                    this.cancelSquadSchedule();
                }
            }
        }
        if (em != null && em.getProperty("state") != null && size == 0) {
            em.setProperty("state", "0");
            if (em.getProperty("leader") != null) {
                em.setProperty("leader", "true");
            }
        }
        if (this.speedRunStart > 0L && this.speedRunLeader.equalsIgnoreCase(chr)) {
            if (size > 0) {
                this.broadcastMessage(MaplePacketCreator.serverNotice(5, "由于远征队队长离开了，所以远征队任务失败。"));
            }
            this.endSpeedRun();
        }
    }
    
    public void setNodes(final MapleNodes mn) {
        this.nodes = mn;
    }
    
    public final List<server.maps.MapleNodes.MaplePlatform> getPlatforms() {
        return this.nodes.getPlatforms();
    }
    
    public Collection<server.maps.MapleNodes.MapleNodeInfo> getNodes() {
        return this.nodes.getNodes();
    }
    
    public server.maps.MapleNodes.MapleNodeInfo getNode(final int index) {
        return this.nodes.getNode(index);
    }
    
    public final List<Rectangle> getAreas() {
        return this.nodes.getAreas();
    }
    
    public final Rectangle getArea(final int index) {
        return this.nodes.getArea(index);
    }
    
    public final void changeEnvironment(final String ms, final int type) {
        this.broadcastMessage(MaplePacketCreator.environmentChange(ms, type));
    }
    
    public final void toggleEnvironment(final String ms) {
        if (this.environment.containsKey(ms)) {
            this.moveEnvironment(ms, (((Integer)this.environment.get(ms)).intValue() == 1) ? 2 : 1);
        }
        else {
            this.moveEnvironment(ms, 1);
        }
    }
    
    public final void moveEnvironment(final String ms, final int type) {
        this.broadcastMessage(MaplePacketCreator.environmentMove(ms, type));
        this.environment.put(ms, Integer.valueOf(type));
    }
    
    public final Map<String, Integer> getEnvironment() {
        return this.environment;
    }
    
    public final int getNumPlayersInArea(final int index) {
        int ret = 0;
        this.charactersLock.readLock().lock();
        try {
            final Iterator<MapleCharacter> ltr = this.characters.iterator();
            while (ltr.hasNext()) {
                if (this.getArea(index).contains(((MapleCharacter)ltr.next()).getPosition())) {
                    ++ret;
                }
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
        return ret;
    }
    
    public void broadcastGMMessage(final MapleCharacter source, final byte[] packet, final boolean repeatToSource) {
        this.broadcastGMMessage(repeatToSource ? null : source, packet, Double.POSITIVE_INFINITY, (source == null) ? new Point(0, 0) : source.getPosition(), (source == null) ? 1 : source.getGMLevel());
    }
    
    private void broadcastGMMessage(final MapleCharacter source, final byte[] packet, final double rangeSq, final Point rangedFrom, final int lowestLevel) {
        this.charactersLock.readLock().lock();
        try {
            for (final MapleCharacter chr : this.characters) {
                if (chr != source && chr.getGMLevel() >= lowestLevel) {
                    chr.getClient().sendPacket(packet);
                }
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
    }
    
    public void Killdpm(final boolean animate) {
        final List<MapleMapObject> monsters = this.getMapObjectsInRange(new Point(0, 0), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.MONSTER));
        for (final MapleMapObject monstermo : monsters) {
            final MapleMonster monster = (MapleMonster)monstermo;
            if (monster.getId() == 9001007) {
                this.spawnedMonstersOnMap.decrementAndGet();
                monster.setHp(0L);
                this.broadcastMessage(MobPacket.killMonster(monster.getObjectId(), (int)(animate ? 1 : 0)));
                this.removeMapObject((MapleMapObject)monster);
                monster.killed();
            }
        }
    }
    
    public final List<Pair<Integer, Integer>> getMobsToSpawn() {
        return this.nodes.getMobsToSpawn();
    }
    
    public final List<Integer> getSkillIds() {
        return this.nodes.getSkillIds();
    }
    
    public final boolean canSpawn() {
        return this.lastSpawnTime > 0L && this.isSpawns && this.lastSpawnTime + (long)this.createMobInterval < System.currentTimeMillis();
    }
    
    public final boolean canHurt() {
        if (this.lastHurtTime > 0L && this.lastHurtTime + (long)this.decHPInterval < System.currentTimeMillis()) {
            this.lastHurtTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }
    
    public short getTop() {
        return this.top;
    }
    
    public short getBottom() {
        return this.bottom;
    }
    
    public short getLeft() {
        return this.left;
    }
    
    public short getRight() {
        return this.right;
    }
    
    public void setTop(final int ii) {
        this.top = (short)ii;
    }
    
    public void setBottom(final int ii) {
        this.bottom = (short)ii;
    }
    
    public void setLeft(final int ii) {
        this.left = (short)ii;
    }
    
    public void setRight(final int ii) {
        this.right = (short)ii;
    }
    
    public void AutoNxmht(final int dy) {
        for (final MapleCharacter chr : this.characters) {
            if (((Integer)Start.ConfigValuesMap.get("泡点开关")).intValue() > 0) {
                int 经验 = 0;
                int 点卷数量 = 0;
                int 抵用卷数量 = 0;
                int 金币 = 0;
                if (((Integer)Start.ConfigValuesMap.get("泡点开关")).intValue() > 0) {
                    经验 = ((((Integer)Start.ConfigValuesMap.get("泡点等级开关")).intValue() < 1) ? ((Integer)Start.ConfigValuesMap.get("泡点经验")).intValue() : (chr.getLevel() * ((Integer)Start.ConfigValuesMap.get("泡点经验")).intValue()));
                    点卷数量 = ((((Integer)Start.ConfigValuesMap.get("泡点等级开关")).intValue() < 1) ? ((Integer)Start.ConfigValuesMap.get("泡点点券")).intValue() : (chr.getLevel() * ((Integer)Start.ConfigValuesMap.get("泡点点券")).intValue()));
                    抵用卷数量 = ((((Integer)Start.ConfigValuesMap.get("泡点等级开关")).intValue() < 1) ? ((Integer)Start.ConfigValuesMap.get("泡点抵用")).intValue() : (chr.getLevel() * ((Integer)Start.ConfigValuesMap.get("泡点抵用")).intValue()));
                    金币 = ((((Integer)Start.ConfigValuesMap.get("泡点等级开关")).intValue() < 1) ? ((Integer)Start.ConfigValuesMap.get("泡点金币")).intValue() : (chr.getLevel() * ((Integer)Start.ConfigValuesMap.get("泡点金币")).intValue()));
                }
                chr.gainExp(经验, true, false, true);
                chr.gainMeso(金币, true, false, true);
                chr.modifyCSPoints(2, 抵用卷数量, true);
                chr.modifyCSPoints(1, 点卷数量, true);
                chr.getClient().getSession().write(MaplePacketCreator.serverNotice(5, "[系统奖励] 恭喜你挂机获得[" + 点卷数量 + "] 点点卷 、[" + 抵用卷数量 + "] 点抵用卷 、[" + 金币 + "] 点金币 、[" + 经验 + "] 点经验 !"));
            }
        }
    }
    
    public int getNumPlayersItemsInArea(final int index) {
        return this.getNumPlayersItemsInRect(this.getArea(index));
    }
    
    public final int getNumPlayersItemsInRect(final Rectangle rect) {
        int ret = this.getNumPlayersInRect(rect);
        ((ReentrantReadWriteLock)this.mapobjectlocks.get(MapleMapObjectType.ITEM)).readLock().lock();
        try {
            for (final MapleMapObject mmo : (this.mapobjects.get(MapleMapObjectType.ITEM)).values()) {
                if (rect.contains(mmo.getPosition())) {
                    ++ret;
                }
            }
        }
        finally {
            ((ReentrantReadWriteLock)this.mapobjectlocks.get(MapleMapObjectType.ITEM)).readLock().unlock();
        }
        return ret;
    }
    
    public int getNumPlayersInRect(final Rectangle rect) {
        int ret = 0;
        this.charactersLock.readLock().lock();
        try {
            final Iterator ltr = this.characters.iterator();
            while (ltr.hasNext()) {
                if (rect.contains(((MapleCharacter)ltr.next()).getTruePosition())) {
                    ++ret;
                }
            }
        }
        finally {
            this.charactersLock.readLock().unlock();
        }
        return ret;
    }
    
    static {
        PointsGained = new HashMap();
        地图buf击杀总开关 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀总开关"));
        地图buf击杀开关1 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀开关1"));
        地图buf击杀开关2 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀开关2"));
        地图buf击杀开关3 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀开关3"));
        地图buf击杀开关4 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀开关4"));
        地图buf击杀开关5 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀开关5"));
        地图buf击杀开关6 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀开关6"));
        地图buf击杀开关7 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀开关7"));
        地图buf击杀开关8 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀开关8"));
        地图buf击杀开关9 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀开关9"));
        地图buf击杀个数1 = Integer.parseInt(ServerProperties.getProperty("Lzj.地图buf击杀个数1"));
        地图buf击杀个数2 = Integer.parseInt(ServerProperties.getProperty("Lzj.地图buf击杀个数2"));
        地图buf击杀个数3 = Integer.parseInt(ServerProperties.getProperty("Lzj.地图buf击杀个数3"));
        地图buf击杀个数4 = Integer.parseInt(ServerProperties.getProperty("Lzj.地图buf击杀个数4"));
        地图buf击杀个数5 = Integer.parseInt(ServerProperties.getProperty("Lzj.地图buf击杀个数5"));
        地图buf击杀个数6 = Integer.parseInt(ServerProperties.getProperty("Lzj.地图buf击杀个数6"));
        地图buf击杀个数7 = Integer.parseInt(ServerProperties.getProperty("Lzj.地图buf击杀个数7"));
        地图buf击杀个数8 = Integer.parseInt(ServerProperties.getProperty("Lzj.地图buf击杀个数8"));
        地图buf击杀个数9 = Integer.parseInt(ServerProperties.getProperty("Lzj.地图buf击杀个数9"));
        地图buf击杀喇叭开关1 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀喇叭开关1"));
        地图buf击杀喇叭开关2 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀喇叭开关2"));
        地图buf击杀喇叭开关3 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀喇叭开关3"));
        地图buf击杀喇叭开关4 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀喇叭开关4"));
        地图buf击杀喇叭开关5 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀喇叭开关5"));
        地图buf击杀喇叭开关6 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀喇叭开关6"));
        地图buf击杀喇叭开关7 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀喇叭开关7"));
        地图buf击杀喇叭开关8 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀喇叭开关8"));
        地图buf击杀喇叭开关9 = Boolean.parseBoolean(ServerProperties.getProperty("Lzj.地图buf击杀喇叭开关9"));
    }
    
    private class ActivateItemReactor implements Runnable
    {
        private MapleMapItem mapitem;
        private MapleReactor reactor;
        private MapleClient c;
        
        public ActivateItemReactor(final MapleMapItem mapitem, final MapleReactor reactor, final MapleClient c) {
            this.mapitem = mapitem;
            this.reactor = reactor;
            this.c = c;
        }
        
        @Override
        public void run() {
            if (this.mapitem != null && this.mapitem == MapleMap.this.getMapObject(this.mapitem.getObjectId(), this.mapitem.getType())) {
                if (this.mapitem.isPickedUp()) {
                    this.reactor.setTimerActive(false);
                    return;
                }
                this.mapitem.expire(MapleMap.this);
                this.reactor.hitReactor(this.c);
                this.reactor.setTimerActive(false);
                if (this.reactor.getDelay() > 0) {
                    MapTimer.getInstance().schedule((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            ActivateItemReactor.this.reactor.forceHitReactor((byte)0);
                        }
                    }, (long)this.reactor.getDelay());
                }
            }
            else {
                this.reactor.setTimerActive(false);
            }
        }
    }
    
    private interface SpawnCondition
    {
        boolean canSpawn(final MapleCharacter p0);
    }
    
    private interface DelayedPacketCreation
    {
        void sendPackets(final MapleClient p0);
    }
}
